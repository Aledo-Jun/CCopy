plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij.platform") version "2.3.0"
}

group = "com.ccopy"
version = "1.3"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        clion("2024.3.3")
    }
}

intellijPlatform {
    pluginConfiguration {
        name = "CCopy"
        description = """
            <h2>CCopy: C++ Includes Expander</h2>
            <p>
              <strong>CCopy</strong> is a powerful CLion plugin designed to streamline competitive programming on online judges. 
              When you copy C++ code containing local include directives, the plugin automatically replaces them with the actual code from the referenced files.
            </p>
            <p>
              This makes your copied code fully self-contained and ready to compile, eliminating the hassle of managing multiple source files.
            </p>
            <h3>Key Features</h3>
            <ul>
              <li><strong>Automatic Include Expansion:</strong> Recursively processes local include statements to inline their contents, ensuring that your code compiles as a standalone unit.</li>
              <li><strong>Dependency Management:</strong> Constructs an include tree to prevent duplicate inclusions and maintain the correct order, while intelligently stripping out redundant include guards.</li>
              <li><strong>Selective Processing:</strong> System headers (e.g., <code>&lt;iostream&gt;</code>, <code>&lt;algorithm&gt;</code>) are left untouched, while known local headers are handled appropriately.</li>
              <li><strong>Customizable Behavior:</strong> Easily toggle the functionality on/off through the plugin settings.</li>
            </ul>
            <p>
              This tool is ideal for competitive programmers who need to quickly generate single-file submissions for online judges.
            </p>
            <p>
              For more information, visit: 
              <a href="https://github.com/Aledo-Jun/CCopy" target="_blank">
                https://github.com/Aledo-Jun/CCopy
              </a>
            </p>
            <p>
              If you like this plugin, 
              <a href="https://www.buymeacoffee.com/june0501">
                Buy me a Coffee!
              </a>
            </p>
        """.trimIndent()

        changeNotes = """
            <ul>
                <li><b>Bug Fixed:</b> Now recursive includes expansion works properly</li>
                <li><b>IDE Compatibility:</b> Now supports the latest versions of Intellij IDE</li>
            </ul>
        """.trimIndent()

        ideaVersion {
            sinceBuild = "241"
            untilBuild = "251.*"
        }
    }
    signing {
        certificateChain = System.getenv("CERTIFICATE_CHAIN")
        privateKey = System.getenv("PRIVATE_KEY")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
    }
    publishing {
        token = System.getenv("PUBLISH_TOKEN")
    }

    buildSearchableOptions = false
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}
