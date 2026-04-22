-keep public class x.tui.launcher.commands.main.raw.** { *; }
-keep public class x.tui.launcher.commands.main.specific.** { *; }
-keep public class x.tui.launcher.commands.tuixt.raw.** { *; }
-keep public class x.tui.launcher.tuils.GenericFileProvider { *; }
-keep public class x.tui.launcher.tuils.PrivateIOReceiver { *; }
-keep public class x.tui.launcher.tuils.PublicIOReceiver { *; }
-keep class x.tui.launcher.managers.** { *; }
-keep class x.tui.launcher.tuils.libsuperuser.**
-keep class x.tui.launcher.managers.suggestions.HideSuggestionViewValues
-keep public class it.andreuzzi.comparestring2.**

-dontwarn x.tui.launcher.commands.main.raw.**

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-dontwarn org.htmlcleaner.**
-dontwarn com.jayway.jsonpath.**
-dontwarn org.slf4j.**

-dontwarn org.jdom2.**