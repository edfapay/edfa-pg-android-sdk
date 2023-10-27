/* Developer: Zohaib Kambrani (a2zzuhaib@gmai.com) */

package com.edfapg.sdk.views.edfacardpay.creditcardview.models

internal data class TouchGrid(
    val rows: Int,
    val columns: Int,
    val callback: com.edfapg.sdk.views.edfacardpay.creditcardview.GridClickListener
)