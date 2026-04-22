<h1 align="center">Security</h1>

<div align="left">
  <h2>Contents</h2>
  <ul>
    <li><a href="#overview">Overview</a></li>
    <li><a href="#data-storage">Data Storage and Privacy</a></li>
    <li><a href="#network">Network Communication</a></li>
    <li><a href="#platform">Platform Interaction</a></li>
    <li><a href="#code-quality">Code Quality and Build Settings</a></li>
    <li><a href="#busybox-security">BusyBox Binary Verification</a></li>
    <li><a href="#reporting">Reporting Vulnerabilities</a></li>
  </ul>
</div>


<h2 id="overview" align="center">Overview</h2>

This project has been audited and hardened following the <strong>OWASP Mobile Application Security Verification Standard (MASVS)</strong>. The security improvements apply to data storage, network transport, platform interactions, and code quality.


<h2 id="data-storage" align="center">Data Storage and Privacy</h2>

<p><em>MASVS-STORAGE</em></p>

<table>
  <tr>
    <th>Measure</th>
    <th>Implementation</th>
  </tr>
  <tr>
    <td>Scoped Storage</td>
    <td>Application data resides in app-private directories via <code>Context.getExternalFilesDir()</code>. This prevents other applications from accessing T-UI configuration and logs.</td>
  </tr>
  <tr>
    <td>Backup Protection</td>
    <td><code>android:allowBackup="false"</code> prevents sensitive data extraction via ADB backups.</td>
  </tr>
  <tr>
    <td>Secure File Sharing</td>
    <td>Uses <code>FileProvider</code> with content URIs instead of vulnerable <code>file://</code> URIs for inter-app file sharing.</td>
  </tr>
</table>


<h2 id="network" align="center">Network Communication</h2>

<p><em>MASVS-NETWORK</em></p>

<table>
  <tr>
    <th>Measure</th>
    <th>Implementation</th>
  </tr>
  <tr>
    <td>Enforced TLS</td>
    <td><code>android:usesCleartextTraffic="false"</code> disables plaintext HTTP globally. All connections use TLS 1.2+.</td>
  </tr>
  <tr>
    <td>Secure Endpoints</td>
    <td>All internal service endpoints (weather API, connectivity checks) have been upgraded to HTTPS.</td>
  </tr>
</table>


<h2 id="platform" align="center">Platform Interaction</h2>

<p><em>MASVS-PLATFORM</em></p>

<table>
  <tr>
    <th>Measure</th>
    <th>Implementation</th>
  </tr>
  <tr>
    <td>Signature-Level Permissions</td>
    <td>Custom permission <code>x.tui.launcher.permission.RECEIVE_CMD</code> with <code>protectionLevel="signature"</code> ensures only apps signed with the same key can send commands to the launcher.</td>
  </tr>
  <tr>
    <td>Immutable Intents</td>
    <td>All system-bound <code>PendingIntents</code> use <code>FLAG_IMMUTABLE</code> to prevent intent redirection attacks (Android 12+ requirement).</td>
  </tr>
  <tr>
    <td>Receiver Security</td>
    <td>All Broadcast Receivers use explicit export flags (<code>RECEIVER_EXPORTED</code> or <code>RECEIVER_NOT_EXPORTED</code>) to prevent unauthorized external triggers.</td>
  </tr>
</table>


<h2 id="code-quality" align="center">Code Quality and Build Settings</h2>

<p><em>MASVS-CODE / MASVS-RESILIENCE</em></p>

<table>
  <tr>
    <th>Measure</th>
    <th>Implementation</th>
  </tr>
  <tr>
    <td>Minification</td>
    <td>Release builds use R8/ProGuard (<code>minifyEnabled true</code>) for code shrinking and obfuscation.</td>
  </tr>
  <tr>
    <td>Resource Shrinking</td>
    <td><code>shrinkResources true</code> removes unused resources from release builds.</td>
  </tr>
  <tr>
    <td>Foreground Services</td>
    <td>Foreground service types (<code>specialUse</code>, <code>mediaPlayback</code>) comply with Android 14 requirements.</td>
  </tr>
</table>


<h2 id="busybox-security" align="center">BusyBox Binary Verification</h2>

The built-in BusyBox installer verifies downloaded binaries before installation:

1. Binaries are sourced from the trusted EXALAB repository
2. Downloads are performed exclusively over HTTPS
3. Each binary is verified against a hardcoded SHA-256 hash specific to the CPU architecture
4. If the hash does not match, the binary is discarded and installation is aborted

The verification logic is implemented in `x/tui/launcher/tuils/BusyBoxInstaller.java`.


<h2 id="reporting" align="center">Reporting Vulnerabilities</h2>

If you discover a security vulnerability, please report it by opening an issue on the GitHub repository. Do not include exploit details in public issues. Use a private disclosure if the repository supports it.
