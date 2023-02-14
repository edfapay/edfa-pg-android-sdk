/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.api

import com.google.gson.annotations.SerializedName

/**
 * "Y" or "N" value holder.
 *
 * @property option the option value.
 */
enum class ExpresspayOption(val option: String) {
    /**
     * "Y" value.
     */
    @SerializedName("Y")
    YES("Y"),

    /**
     * "N" value.
     */
    @SerializedName("N")
    NO("N");

    companion object {
        /**
         * Maps the [boolean] to the [ExpresspayOption].
         *
         * @param boolean value to map.
         */
        fun map(boolean: Boolean): ExpresspayOption = if (boolean) YES else NO
    }
}
