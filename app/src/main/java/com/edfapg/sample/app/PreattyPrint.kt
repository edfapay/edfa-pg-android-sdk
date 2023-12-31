/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sample.app

import java.lang.reflect.Modifier
import java.math.BigDecimal
import java.math.BigInteger
import java.text.BreakIterator
import java.util.*

/**
 * Pretty print function. https://github.com/snowe2010/pretty-print
 *
 * Prints any object in a pretty format for easy debugging/reading
 *
 * @param [obj] the object to pretty print
 * @param [indent] optional param that specifies the number of spaces to use to indent. Defaults to 2.
 * @param [writeTo] optional param that specifies the [Appendable] to output the pretty print to.
 * @param [wrappedLineWidth] optional param that specifies how many characters of a string should be on a line.
 */
fun <T> T.preattyPrint(
    indent: Int = 2,
    writeTo: Appendable = StringBuilder(),
    wrappedLineWidth: Int = 80
): String = PrettyPrinter(indent, writeTo, wrappedLineWidth).pp(this)

/**
 * Class for performing pretty print operations on any object with customized indentation, target output, and line wrapping
 * width for long strings.
 *
 * @param [tabSize] How much more to indent each level of nesting.
 * @param [writeTo] Where to write a pretty printed object.
 * @param [wrappedLineWidth] How long a String needs to be before it gets transformed into a multiline String.
 */
private class PrettyPrinter(
    private val tabSize: Int,
    private val writeTo: Appendable,
    private val wrappedLineWidth: Int
) {
    private val lineInstance = BreakIterator.getLineInstance()

    //    private val logger = KotlinLogging.logger {}
    private val visited = mutableSetOf<Int>()
    private val revisited = mutableSetOf<Int>()

    /**
     * Pretty prints the given object with this printer.
     *
     * @param [obj] The object to pretty print.
     */
    fun pp(obj: Any?): String {
        ppAny(obj)
        writeLine()

        return writeTo.toString()
    }

    /**
     * The core pretty print method. Delegates to the appropriate pretty print method based on the object's type. Handles
     * cyclic references. `collectionElementPad` and `objectFieldPad` are generally the same. A specific case in which they
     * differ is to handle the difference in alignment of different types of fields in an object, as seen in `ppPlainObject(...)`.
     *
     * @param [obj] The object to pretty print.
     * @param [collectionElementPad] How much to indent the elements of a collection.
     * @param [objectFieldPad] How much to indent the field of an object.
     */
    private fun ppAny(
        obj: Any?,
        collectionElementPad: String = "",
        objectFieldPad: String = collectionElementPad,
        staticMatchesParent: Boolean = false
    ) {
        val id = System.identityHashCode(obj)

        if (obj != null && staticMatchesParent) {
            val className = obj.javaClass.simpleName
            write("$className.<static cyclic class reference>")
            return
        }

        if (!obj.isAtomic() && visited[id]) {
            write("cyclic reference detected for $id")
            revisited.add(id)
            return
        }

        visited.add(id)
        when {
            obj is Iterable<*> -> ppIterable(obj, collectionElementPad)
            obj is Map<*, *> -> ppMap(obj, collectionElementPad)
            obj is String -> ppString(obj, collectionElementPad)
            obj is Enum<*> -> ppEnum(obj)
            obj.isAtomic() -> ppAtomic(obj)
            obj is Any -> ppPlainObject(obj, objectFieldPad)
        }
        visited.remove(id)

        if (revisited[id]) {
            write("[\$id=$id]")
            revisited -= id
        }
    }

    /**
     * Pretty prints the contents of the Iterable receiver. The given function is applied to each element. The result
     * of an application to each element is on its own line, separated by a separator. `currentDepth` specifies the
     * indentation level of any closing bracket.
     */
    private fun <T> Iterable<T>.ppContents(currentDepth: String, separator: String = "", f: (T) -> Unit) {
        val list = this.toList()

        if (!list.isEmpty()) {
            f(list.first())
            list.drop(1).forEach {
                writeLine(separator)
                f(it)
            }
            writeLine()
        }

        write(currentDepth)
    }

    private fun ppPlainObject(obj: Any, currentDepth: String) {
        val increasedDepth = deepen(currentDepth)
        val className = obj.javaClass.simpleName

        writeLine("$className(")
        obj.javaClass.declaredFields
            .filterNot { it.isSynthetic }
            .ppContents(currentDepth) {
                val staticMatchesParent = Modifier.isStatic(it.modifiers) && it.type == obj.javaClass

                it.isAccessible = true
                write("$increasedDepth${it.name} = ")
                val extraIncreasedDepth = deepen(increasedDepth, it.name.length + 3) // 3 is " = ".length in prev line
                val fieldValue = it.get(obj)

                ppAny(fieldValue, extraIncreasedDepth, increasedDepth, staticMatchesParent)
            }
        write(')')
    }

    private fun ppIterable(obj: Iterable<*>, currentDepth: String) {
        val increasedDepth = deepen(currentDepth)

        writeLine('[')
        obj.ppContents(currentDepth, ",") {
            write(increasedDepth)
            ppAny(it, increasedDepth)
        }
        write(']')
    }

    private fun ppMap(obj: Map<*, *>, currentDepth: String) {
        val increasedDepth = deepen(currentDepth)

        writeLine('{')
        obj.entries.ppContents(currentDepth, ",") {
            write(increasedDepth)
            ppAny(it.key, increasedDepth)
            write(" -> ")
            ppAny(it.value, increasedDepth)
        }
        write('}')
    }

    private fun ppString(s: String, currentDepth: String) {
        if (s.length > wrappedLineWidth) {
            val tripleDoubleQuotes = "\"\"\""
            writeLine(tripleDoubleQuotes)
            writeLine(wordWrap(s, currentDepth))
            write("$currentDepth$tripleDoubleQuotes")
        } else {
            write("\"$s\"")
        }
    }

    private fun ppEnum(enum: Enum<*>) {
        write("${enum.javaClass.simpleName}.${enum.toString()}")
    }

    private fun ppAtomic(obj: Any?) {
        write(obj.toString())
    }

    /**
     * Writes to the writeTo with a new line and adds logging
     */
    private fun writeLine(str: Any? = "") {
        writeTo.append(str.toString()).appendLine()
    }

    /**
     * Writes to the writeTo and adds logging
     */
    private fun write(str: Any?) {
        writeTo.append(str.toString())
    }

    private fun wordWrap(text: String, padding: String): String {
        lineInstance.setText(text)
        var start = lineInstance.first()
        var end = lineInstance.next()
        val breakableLocations = mutableListOf<String>()
        while (end != BreakIterator.DONE) {
            val substring = text.substring(start, end)
            breakableLocations.add(substring)
            start = end
            end = lineInstance.next()
        }
        val arr = mutableListOf(mutableListOf<String>())
        var index = 0
        arr[index].add(breakableLocations[0])
        breakableLocations.drop(1).forEach {
            val currentSize = arr[index].joinToString(separator = "").length
            if (currentSize + it.length <= wrappedLineWidth) {
                arr[index].add(it)
            } else {
                arr.add(mutableListOf(it))
                index += 1
            }
        }
        return arr.flatMap { listOf("$padding${it.joinToString(separator = "")}") }.joinToString("\n")
    }

    private fun deepen(currentDepth: String, size: Int = tabSize): String = " ".repeat(size) + currentDepth
}

/**
 * Determines if this object should not be broken down further for pretty printing.
 */
private fun Any?.isAtomic(): Boolean =
    this == null
            || this is Char || this is Number || this is Boolean || this is BigInteger || this is BigDecimal || this is UUID

// For syntactic sugar
operator fun <T> Set<T>.get(x: T): Boolean = this.contains(x)
