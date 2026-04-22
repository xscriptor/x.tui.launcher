<h1 align="center">Commands Reference</h1>

<div align="left">
  <h2>Contents</h2>
  <ul>
    <li><a href="#launcher-commands">Launcher Commands</a></li>
    <li><a href="#theme-commands">Theme Commands</a></li>
    <li><a href="#busybox-commands">BusyBox Commands</a></li>
    <li><a href="#file-commands">File Commands</a></li>
    <li><a href="#system-commands">System Commands</a></li>
    <li><a href="#notes-commands">Notes Commands</a></li>
    <li><a href="#network-commands">Network Commands</a></li>
  </ul>
</div>


<h2 id="launcher-commands" align="center">Launcher Commands</h2>

<table>
  <tr>
    <th>Command</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>tui -open</code></td>
    <td>Open the app settings page for x.tui launcher</td>
  </tr>
  <tr>
    <td><code>tui -reset</code></td>
    <td>Reset the launcher to its default settings</td>
  </tr>
  <tr>
    <td><code>restart</code></td>
    <td>Reload the launcher UI</td>
  </tr>
  <tr>
    <td><code>username [user] [device]</code></td>
    <td>Set the terminal prompt username and device name</td>
  </tr>
  <tr>
    <td><code>clear</code></td>
    <td>Clear the terminal output</td>
  </tr>
  <tr>
    <td><code>devutils</code></td>
    <td>Show diagnostic information about the launcher</td>
  </tr>
</table>


<h2 id="theme-commands" align="center">Theme Commands</h2>

<table>
  <tr>
    <th>Command</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>theme -preset [name]</code></td>
    <td>Apply a named color preset</td>
  </tr>
  <tr>
    <td><code>theme -apply</code></td>
    <td>Apply current theme settings</td>
  </tr>
  <tr>
    <td><code>theme -revert</code></td>
    <td>Revert to the previous theme</td>
  </tr>
  <tr>
    <td><code>theme -standard</code></td>
    <td>Reset to the default theme</td>
  </tr>
</table>

Available presets: `blue`, `red`, `green`, `pink`, `bw`, `cyberpunk`.


<h2 id="busybox-commands" align="center">BusyBox Commands</h2>

<table>
  <tr>
    <th>Command</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>bbman -install</code></td>
    <td>Download and install BusyBox for your CPU architecture</td>
  </tr>
  <tr>
    <td><code>bbman -remove</code></td>
    <td>Remove the installed BusyBox binary</td>
  </tr>
</table>

Once installed, 300+ Linux utilities become available directly in the terminal: `ls`, `grep`, `awk`, `sed`, `vi`, `ping`, `top`, `wget`, and more.


<h2 id="file-commands" align="center">File Commands</h2>

<table>
  <tr>
    <th>Command</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>open [file/app]</code></td>
    <td>Open a file or launch an application</td>
  </tr>
  <tr>
    <td><code>tuixt [file]</code></td>
    <td>Open a file in the built-in text editor</td>
  </tr>
  <tr>
    <td><code>search [name]</code></td>
    <td>Search for files by name</td>
  </tr>
  <tr>
    <td><code>cp [source] [dest]</code></td>
    <td>Copy a file</td>
  </tr>
  <tr>
    <td><code>mv [source] [dest]</code></td>
    <td>Move or rename a file</td>
  </tr>
</table>


<h2 id="system-commands" align="center">System Commands</h2>

<table>
  <tr>
    <th>Command</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>status</code></td>
    <td>Show device status (battery, wifi, bluetooth, etc.)</td>
  </tr>
  <tr>
    <td><code>wifi</code></td>
    <td>Toggle wifi on/off</td>
  </tr>
  <tr>
    <td><code>bluetooth</code></td>
    <td>Toggle bluetooth on/off</td>
  </tr>
  <tr>
    <td><code>data</code></td>
    <td>Toggle mobile data</td>
  </tr>
  <tr>
    <td><code>flash</code></td>
    <td>Toggle flashlight</td>
  </tr>
  <tr>
    <td><code>location</code></td>
    <td>Display current GPS coordinates</td>
  </tr>
  <tr>
    <td><code>call [number/contact]</code></td>
    <td>Place a phone call</td>
  </tr>
  <tr>
    <td><code>music -play/-next/-prev</code></td>
    <td>Control music playback</td>
  </tr>
  <tr>
    <td><code>volume [value]</code></td>
    <td>Set system volume</td>
  </tr>
  <tr>
    <td><code>uninstall [app]</code></td>
    <td>Uninstall an application</td>
  </tr>
</table>


<h2 id="notes-commands" align="center">Notes Commands</h2>

<table>
  <tr>
    <th>Command</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>notes -add [text]</code></td>
    <td>Add a new note</td>
  </tr>
  <tr>
    <td><code>notes -rm [id]</code></td>
    <td>Remove a note by its ID</td>
  </tr>
  <tr>
    <td><code>notes -ls</code></td>
    <td>List all notes</td>
  </tr>
  <tr>
    <td><code>notes -clear</code></td>
    <td>Remove all notes</td>
  </tr>
</table>


<h2 id="network-commands" align="center">Network Commands</h2>

<table>
  <tr>
    <th>Command</th>
    <th>Description</th>
  </tr>
  <tr>
    <td><code>htmlextract [url]</code></td>
    <td>Extract and display content from a web page</td>
  </tr>
  <tr>
    <td><code>weather</code></td>
    <td>Display current weather information</td>
  </tr>
</table>
