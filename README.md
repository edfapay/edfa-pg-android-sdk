![](https://jitpack.io/v/edfapay/edfapay-android-sdk.svg) | [View SDK Wiki](https://github.com/EdfaPgSa/edfapay-android-sdk/wiki) | [Report new issue](https://github.com/EdfaPgSa/edfapay-android-sdk/issues/new)

# EdfaPg Android SDK

EdfaPg is a white-label payment software provider. Thanks to our 15+ years of experience in the payment industry, we’ve developed a state-of-the-art white-label payment system that ensures smooth and uninterrupted payment flow for merchants across industries.

<p align="center">
  <a href="https://edfapay.com">
      <img src="/media/header.jpg" alt="EdfaPg" width="400px"/>
  </a>
</p>

EdfaPg Android SDK was developed and designed with one purpose: to help the Android developers easily integrate the EdfaPg API Payment Platform for a specific merchant. 

The main aspects of the EdfaPg Android SDK:

- [Kotlin](https://developer.android.com/kotlin) is the main language
- [Retrofit](http://square.github.io/retrofit/) is the API machine 
- [KDoc](https://kotlinlang.org/docs/reference/kotlin-doc.html) code coverage
- API debug [logging](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
- Minimum SDK 16+
- Sample Application

To properly set up the SDK, read [Wiki](https://github.com/EdfaPgSa/edfapay-android-sdk/wiki) first.
To get used to the SDK, download a [sample app](https://github.com/EdfaPgSa/edfapay-android-sdk/tree/master/sample).

## Setup

Add to the root build.gradle:

```groovy
allprojects {
    repositories {
        ...
        maven {
            url "https://jitpack.io"
            credentials { username "jp_cppv4mkessjq12i4kqihrnjqvo" }
        }
    }
 }
```

Add to the package build.gradle:

```groovy
dependencies {
    implementation 'com.github.EdfaPgSa:edfapay-android-sdk-code:1.0.4'
}
```

Latest version is: ![Release](https://badgen.net/badge/jitpack/1.0.4/green)


## Quick Payment Implementation
[**Card Payment**](https://github.com/EdfaPgSa/edfapay-android-sdk/wiki/Edfa-Quick-Card-Payment)
Start the card payment with one click, easy and short line of codes. It will help the developer to easily implement the payment using card in thier application. click the [link](https://github.com/EdfaPgSa/edfapay-android-sdk/wiki/Edfa-Quick-Card-Payment) for easy steps to start payments.


## Sample

| Txn Types | Card Pay | Sale | Recurring Sale |
|-|-|-|-|
| ![](/media/txn-types.png) | ![](/media/card-pay.png) | ![](/media/sales.png) | ![](/media/recurring-sale.png) |

| Capture | Creditvoid | Get Trans Status | Get Trans Details |
|-|-|-|-|
| ![](/media/capture.png) | ![](/media/creditvoid.png) | ![](/media/get-trans-status.png) | ![](/media/get-trans-details.png) |

## Getting help

To report a specific issue or feature request, open a [new issue](https://github.com/EdfaPgSa/edfapay-android-sdk/issues/new).

Or write a direct letter to the [support@edfapay.com](mailto:support@edfapay.com).

## License

MIT License. See the [LICENSE](https://github.com/edfapay/edfa-pg-android-sdk-sample/blob/main/LICENSE) file for more details.

## Contacts

![](/media/footer.jpg)

Website: https://edfapay.com  
Phone: [+966 920033633](tel:+966920033633)  
Email: [support@edfapay.com](mailto:support@edfapay.com)  
Address: EdfaPg, Olaya Street, Riyadh, Saudi Arabia 

© 2022 - 2023 EdfaPg. All rights reserved.
