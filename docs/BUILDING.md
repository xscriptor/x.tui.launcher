<h1 align="center">Building and Deployment</h1>

<div align="left">
  <h2>Contents</h2>
  <ul>
    <li><a href="#prerequisites">Prerequisites</a></li>
    <li><a href="#building-the-apk">Building the APK</a></li>
    <li><a href="#build-variants">Build Variants</a></li>
    <li><a href="#installing">Installing on Device</a></li>
    <li><a href="#debugging">Debugging</a></li>
  </ul>
</div>


<h2 id="prerequisites" align="center">Prerequisites</h2>

<table>
  <tr>
    <td><strong>JDK</strong></td>
    <td>21 (Eclipse Adoptium recommended)</td>
  </tr>
  <tr>
    <td><strong>Android SDK</strong></td>
    <td>Build-tools for API 35</td>
  </tr>
  <tr>
    <td><strong>Gradle</strong></td>
    <td>9.3.1 (included via wrapper)</td>
  </tr>
  <tr>
    <td><strong>AGP</strong></td>
    <td>9.1.1</td>
  </tr>
</table>

Set `JAVA_HOME` to your JDK 21 installation if it is not in your system PATH:

```bash
export JAVA_HOME=/path/to/jdk-21
```

On Windows (PowerShell):

```powershell
$env:JAVA_HOME = 'C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot'
```


<h2 id="building-the-apk" align="center">Building the APK</h2>

<h3>Debug build (F-Droid variant)</h3>

```bash
chmod +x gradlew
./gradlew assembleFdroidDebug
```

Output: `app/build/outputs/apk/fdroid/debug/app-fdroid-debug.apk`

<h3>Release build</h3>

Release builds require a signing keystore. Configure it in `local.properties`:

```properties
storeFile=keystore.jks
storePassword=your_password
keyAlias=tui-alias
keyPassword=your_key_password
```

Then build:

```bash
./gradlew assembleFdroidRelease
```


<h2 id="build-variants" align="center">Build Variants</h2>

<table>
  <tr>
    <th>Flavor</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>fdroid</code></td>
    <td>Full feature set including SMS permissions</td>
  </tr>
  <tr>
    <td><code>playstore</code></td>
    <td>Restricted variant for Play Store compliance</td>
  </tr>
</table>

Both flavors support `debug` and `release` build types.


<h2 id="installing" align="center">Installing on Device</h2>

<h3>Via ADB</h3>

```bash
adb wait-for-device
adb install -r app/build/outputs/apk/fdroid/debug/app-fdroid-debug.apk
```

<h3>Via emulator</h3>

```bash
emulator -avd Your_AVD_Name -gpu host -accel on &
adb wait-for-device
adb install -r app/build/outputs/apk/fdroid/debug/app-fdroid-debug.apk
```


<h2 id="debugging" align="center">Debugging</h2>

<h3>Real-time logs</h3>

```bash
adb logcat | grep x.tui
```

<h3>Uninstall via ADB</h3>

```bash
adb uninstall x.tui.launcher
```

<h3>Push files to app storage</h3>

```bash
adb push local_file.txt /data/user/0/x.tui.launcher/files/
```
