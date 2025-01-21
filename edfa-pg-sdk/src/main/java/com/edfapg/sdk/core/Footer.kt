import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.edfapg.sdk.R

@Composable
fun Footer(){
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth(),

            Alignment.Center

        ) {
            Image(

                painter = painterResource(id = R.drawable.pci_dss),
                contentDescription = "PCI DSS Compliant",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .width(71.dp)
                    .height(33.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            val paymentSchemeWidth = 51.dp
            val paymentSchemeHeight = 22.dp
            Box(
                modifier = Modifier
                    .width(paymentSchemeWidth)
                    .height(paymentSchemeHeight),
                Alignment.Center

            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.mada), // Replace with your mada image
                    contentDescription = "mada"
                )
            }

            Box(
                modifier = Modifier
                    .width(paymentSchemeWidth)
                    .height(paymentSchemeHeight),
                Alignment.Center

            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.mastercard), // Replace with your mada image
                    contentDescription = "mastercard"
                )
            }
            Box(
                modifier = Modifier
                    .width(paymentSchemeWidth)
                    .height(paymentSchemeHeight),
                Alignment.Center

            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.visa),
                    contentDescription = "visa"
                )
            }
            Box(
                modifier = Modifier
                    .width(paymentSchemeWidth)
                    .height(paymentSchemeHeight),
                Alignment.Center

            ) {
                Image(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = R.drawable.amex),
                    contentDescription = "amex"
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Powered by",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.edfapay_logo),
                contentDescription = "EdfaPay Logo"
            )
        }
    }
}