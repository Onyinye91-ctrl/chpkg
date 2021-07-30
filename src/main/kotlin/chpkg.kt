import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.options.convert

class Chpkg: CliktCommand() {

    /**
     * The fully-qualified old package name or a segment of it,
     * so, it can be: "com.example.app" or just "com" if you want to substitute the first segment only.
     */
    val from by option("--from", help = "The fully-qualified old package name")
        .convert {
            // for more flexibility, allow pkg name to have a suffix of only 1 "."
            // so, users can in practice use "com.example.app" & "com.example.app." interchangeably
            it.trimLastDot()
        }
        .required()
        .check(badPkgName()) {
            it.isPackageName()
        }

    /**
     * The fully-qualified new package name or a segment of it,
     * so, it can be: "com.example.app" or just "com" if you want to substitute the first segment only.
     */
    val to by option("--to", help = "The fully-qualified new package name")
        .convert {
            it.trimLastDot()
        }
        .required()
        .validate {
            require(it != from) {
                "--from value is exactly the same as --to value"
            }
            require(it.isPackageName()) {
                badPkgName()
            }
        }

    override fun run() {
        echo("Welcome to Chpkg tool!")
    }

    private fun badPkgName() = "Make sure to specify a valid package name"
}

class Config: CliktCommand(help = "Configure chpkg global options, like the default project type.") {

    override fun run() {

    }
}