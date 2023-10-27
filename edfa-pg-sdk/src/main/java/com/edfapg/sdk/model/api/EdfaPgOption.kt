/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sdk.model.api

import com.google.gson.annotations.SerializedName

/**
 * "Y" or "N" value holder.
 *
 * @property option the option value.
 */
enum class EdfaPgOption(val option: String) {
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
         * Maps the [boolean] to the [EdfaPgOption].
         *
         * @param boolean value to map.
         */
        fun map(boolean: Boolean): EdfaPgOption = if (boolean) YES else NO
    }
}
