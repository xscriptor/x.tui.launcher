<h1 align="center">Upgrade Notes</h1>

<blockquote>
This document has been superseded by the <a href="./CHANGELOG.md">Changelog</a>. It is preserved here as a technical reference for the upgrade decisions made during the initial modernization effort.
</blockquote>

<h2 id="context" align="center">Context</h2>

- Objective: upgrade the project toolchain and SDK targets to the highest stable versions without breaking core functionality
- Start date: 2026-04-22
- Strategy: large version jumps with fast validation to save time
- Target distribution: Git/sideload (no Play Store), prioritizing functionality including root features

<h2 id="base-state" align="center">Base State (Before Upgrade)</h2>

<table>
  <tr><td><strong>AGP</strong></td><td>8.2.0</td></tr>
  <tr><td><strong>Gradle</strong></td><td>8.2</td></tr>
  <tr><td><strong>compileSdk</strong></td><td>34</td></tr>
  <tr><td><strong>targetSdk</strong></td><td>34</td></tr>
  <tr><td><strong>minSdk</strong></td><td>21</td></tr>
</table>

<h2 id="final-state" align="center">Final State (After Upgrade)</h2>

<table>
  <tr><td><strong>AGP</strong></td><td>9.1.1</td></tr>
  <tr><td><strong>Gradle</strong></td><td>9.3.1</td></tr>
  <tr><td><strong>compileSdk</strong></td><td>35</td></tr>
  <tr><td><strong>targetSdk</strong></td><td>35</td></tr>
  <tr><td><strong>minSdk</strong></td><td>21</td></tr>
  <tr><td><strong>Java</strong></td><td>17</td></tr>
  <tr><td><strong>Package</strong></td><td>x.tui.launcher</td></tr>
</table>

<h2 id="functional-risks" align="center">Known Functional Risks</h2>

- <code>wifi</code> uses <code>setWifiEnabled</code> which is restricted in modern Android. Mitigated with root and Settings fallback.
- <code>bluetooth</code> uses direct <code>enable/disable</code>. Mitigated with modern runtime permissions and fallback strategy.
- Storage depends on <code>MANAGE_EXTERNAL_STORAGE</code> and <code>requestLegacyExternalStorage</code>.

<h2 id="compatibility" align="center">Compatibility Decisions</h2>

- Root support is preserved and used as the primary fallback for restricted system toggles
- When Android blocks a direct toggle and root is unavailable, the command opens system settings to preserve UX
- The launcher does not crash when non-critical permissions are denied; affected features degrade gracefully
