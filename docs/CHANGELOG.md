<h1 align="center">Changelog</h1>

<div align="left">
  <h2>Contents</h2>
  <ul>
    <li><a href="#v6151">v6.15.1-updated-v2</a></li>
  </ul>
</div>


<h2 id="v6151" align="center">v6.15.1-updated-v2</h2>

<p><em>2026-04-22</em></p>

<h3>Package Rename</h3>

- Application package renamed from <code>ohi.andre.consolelauncher</code> to <code>x.tui.launcher</code>
- New <code>applicationId</code>: <code>x.tui.launcher</code>
- Requires fresh install (uninstall previous version first)

<h3>New Icon</h3>

- Replaced the launcher icon with new x.tui terminal design across all density buckets

<h3>Header Feature</h3>

- Fixed critical array sizing bug (9 to 10) that prevented UIManager initialization when all status labels were active
- Fixed <code>INPUT_BGCOLOR_INDEX</code> and related constants shifted by +1 to account for the new header label
- Fixed <code>outlineColors</code> arraycopy using hardcoded offset instead of array length variable
- Header file lookup now supports both <code>header</code> and <code>header.txt</code> filenames
- Added null-guard for <code>Tuils.getFolder()</code> in HeaderRunnable

<h3>Toolchain Upgrade</h3>

- AGP upgraded from 8.2.0 to 9.1.1
- Gradle upgraded from 8.2 to 9.3.1
- <code>compileSdk</code> and <code>targetSdk</code> upgraded from 34 to 35
- Java source/target compatibility set to 17
- Dependencies updated: appcompat 1.7.0, material 1.12.0, okhttp 5.3.2, jsonpath 2.10.0, jsoup 1.18.3
- Removed deprecated <code>android.enableJetifier=true</code>
- Migrated <code>build.gradle</code> syntax to <code>prop = value</code> for Gradle 10 compatibility

<h3>Security Hardening</h3>

- OWASP MASVS compliance audit and hardening
- Custom permission <code>RECEIVE_CMD</code> with signature-level protection
- All PendingIntents use <code>FLAG_IMMUTABLE</code>
- Cleartext traffic disabled globally
- All endpoints migrated to HTTPS
- Foreground service types comply with Android 14 requirements

<h3>New Commands</h3>

- <code>username [user] [device]</code> -- customize terminal prompt
- <code>theme -preset [name]</code> -- quick theme switching with 6 presets
- <code>bbman</code> -- built-in BusyBox manager with SHA-256 verification

<h3>Platform Compatibility</h3>

- Full AndroidX migration
- <code>wifi</code> and <code>bluetooth</code> commands migrated to API, root, Settings fallback strategy
- Modern runtime permissions for Bluetooth, notifications, and storage
- <code>registerReceiver</code> migrated to <code>ContextCompat.registerReceiver()</code>
- <code>data</code> command recovered for modern APIs
- App no longer crashes when non-critical permissions are denied
