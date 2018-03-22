package net.flibidi.ci

/**
 * Wraps the 'bat' command with timestamps and XTerm colors
 *
 * @param command Batch command to run
 */
def colorBat(command) {
    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
        wrap([$class: 'TimestamperBuildWrapper']) {
            bat command
        }
    }
}

/**
 * Wraps the 'sh' command with timestamps and XTerm colors
 *
 * @param command Shell command to run
 */
def colorSh(command) {
    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
        wrap([$class: 'TimestamperBuildWrapper']) {
            sh command
        }
    }
}

/**
 * Adds build metadata (issues, patches, artifacts) as summary entries
 */
def addBuildSummaries() {
    addBuildSummary("notepad.png", "info/ci_build_info.html")
    addBuildSummary("package.png", "info/build_info.html")
    addBuildSummary("notepad.png", "info/issues.html")
    addBuildSummary("notepad.png", "info/patches.html")
}

/**
 * Adds a summary entry to a Jenkins build info page
 *
 * @param icon        Summary icon (see Jenkins sources for available images)
 * @param contentFile File to read from the workspace
 */
def addBuildSummary(icon, contentFile) {
    def content = ""
    try {
        content = readFile(contentFile)
    } catch (err) {
        // skip missing HTML files
        return
    }

    if (content.length() > 0) {
        manager.createSummary(icon).appendText(content, false)
    }
}
