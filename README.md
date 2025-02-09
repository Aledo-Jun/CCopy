# CCopy: C++ Includes Expander

**CCopy** is a powerful CLion plugin designed specifically for competitive programming. It automatically expands local `#include` directives in your C++ code by inlining the actual header code, creating a fully self-contained source file ready for submission on online judges.

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/Aledo-Jun/CCopy)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![GitHub issues](https://img.shields.io/github/issues-raw/Aledo-Jun/CCopy)](https://github.com/Aledo-Jun/CCopy/issues)

---

## Overview

Competitive programmers often struggle with managing multiple source files and headers 
when submitting code to online judges. **CCopy** solves this problem by automatically replacing local 
`#include` directives with the actual code from those header files. 
This means you can copy your code with a single shortcut, paste it, 
and be sure that all necessary code is included—without the clutter of redundant include guards or duplicate includes.

---

## Features

- **Automatic Include Expansion**  
  Recursively processes local include directives to inline the corresponding header files.

- **Dependency Management**  
  Builds an include tree to ensure the correct order and prevent duplicate inclusion, while intelligently removing unnecessary include guards.

- **Selective Processing**  
  Leaves system headers (e.g., `<iostream>`, `<algorithm>`) untouched, processing only user-defined or non-standard includes.

- **Customizable Behavior**  
  Toggle the expansion feature on or off and configure a custom shortcut for the copy command directly from the plugin settings.

- **Ideal for Competitive Programming**  
  Create self-contained, submission-ready code with minimal effort.

---

## Installation

1. **Clone the Repository**  
   Here’s how to clone the repository via the command line:

       git clone https://github.com/Aledo-Jun/CCopy.git

2. **Open the Project**  
   Open the project in CLion.

3. **Build the Plugin**  
   Use the built-in build tools to compile the plugin.

4. **Install the Plugin**  
   In your IDE, go to `File > Settings > Plugins > Install Plugin from Disk` and select the generated plugin artifact.

---

## Usage

1. **Copy Your Code**  
   When you copy code from a C++ source file containing local include directives, the plugin intercepts the copy action.

2. **Automatic Expansion**  
   The plugin automatically replaces local `#include` statements with the full content of the referenced header files, generating a single, self-contained code snippet.

3. **Paste and Submit**  
   Paste the expanded code into your online judge submission page, ensuring that all necessary code is included without any dependency issues.

---

## Configuration

- **Toggle Feature On/Off**  
  Navigate to `Settings > Tools > C++ Includes Expander` to enable or disable the automatic include expansion.

---

## Contributing

Contributions, bug reports, and feature requests are always welcome!

1. Fork the repository.
2. Create your feature branch:

       git checkout -b feature/my-new-feature

3. Commit your changes:

       git commit -am 'Add new feature'

4. Push to the branch:

       git push origin feature/my-new-feature

5. Open a pull request.

For any major changes, please open an issue first to discuss what you would like to change.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contact

Developed and maintained by [Aledo-Jun](https://github.com/Aledo-Jun).

For any questions or support, feel free to open an issue or contact me directly via GitHub.

---

Enjoy coding and happy competitive programming!
