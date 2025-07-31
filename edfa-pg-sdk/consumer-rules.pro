# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile


# Keep all classes and members in the main package and subpackages
-keep class com.edfapg.** {
  public protected private *;
}

#-keep class com.edfapg.sdk.model.response.sale.** { *; }
# Gson uses generic type information stored in a class file when working with fields.
-keepattributes Signature
-keepattributes *Annotation*

# Gson specific classes
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.** { *; }
-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
-dontwarn okhttp3.**