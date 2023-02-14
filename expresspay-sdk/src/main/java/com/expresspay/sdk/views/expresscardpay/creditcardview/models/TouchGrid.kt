/* Developer: Zohaib Kambrani (a2zzuhaib@gmai.com) */

package com.expresspay.sdk.views.expresscardpay.creditcardview.models

import com.expresspay.sdk.views.expresscardpay.creditcardview.GridClickListener

internal data class TouchGrid(
    val rows: Int,
    val columns: Int,
    val callback: com.expresspay.sdk.views.expresscardpay.creditcardview.GridClickListener
)