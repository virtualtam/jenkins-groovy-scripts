import org.jvnet.hudson.plugins.groovypostbuild.GroovyPostbuildAction
import org.jvnet.hudson.plugins.groovypostbuild.GroovyPostbuildSummaryAction

/**
 * Displays custom build information
 *
 * In Jenkins, a new Summary item is created in the Build Info view.
 */
class DisplayBuildInfo {
    /**
     * Adds the BuildInfo and issue summary entries to a Jenkins build info page
     */
    static def run(manager) {
        if (manager.build.project.name.contains("android-sdk")) {
            addChildInfoToParentBuild(
                manager,
                "plugin.png",
                "Android",
                "info/build_info.html"
            )

        } else if (manager.build.project.name.contains("ios-sdk")) {
            addChildInfoToParentBuild(
                manager,
                "plugin.png",
                "iOS",
                "info/build_info.html"
            )

        } else if (manager.build.project.name.contains("sanity")) {
            addChildInfoToParentBuild(manager, "terminal.png", "Sanity")

        } else if (manager.build.project.name.contains("smoke")) {
            def buildType = manager.build.project.name.split("-")[-1]
            def buildNumber = manager.build.number
            def briefFile = "pub/brief_build_report-" + buildType + "-" + buildNumber + ".html"

            addChildInfoToParentBuild(
                manager,
                "terminal.png",
                "Smoke",
                briefFile
            )
        }

        for (cause in manager.build.causes) {
            addUserCauseBadge(manager, cause)
        }

        addNodeBadge(manager)

        addSummary(manager, "notepad.png", "info/ci_build_info.html")
        addSummary(manager, "package.png", "info/build_info.html")
        addSummary(manager, "notepad.png", "info/issues.html")
        addSummary(manager, "notepad.png", "info/patches.html")
    }

    /**
     * Adds a badge and summary entry to a parent build
     */
    static def addChildInfoToParentBuild(manager, icon, title, contentFile) {
        for (cause in manager.build.causes) {
            if (! cause.class.toString().contains("UpstreamCause")) {
                continue
            }

            def friendlyName = manager.build.getProject().getDisplayName()
            friendlyName += ' ' + manager.build.getDisplayName()

            def summaryAction = new GroovyPostbuildSummaryAction(icon)
            summaryAction.appendText(
                title + ' build: <a href="'
                + manager.hudson.getRootUrl() + manager.build.getUrl() + '">'
                + friendlyName + '</a>',
                false
            )

            if (contentFile != null) {
                summaryAction.appendText(
                    "<br/>" + readRemoteFile(manager, contentFile),
                    false
                )
            }
            cause.getUpstreamRun().getActions().add(summaryAction)

            def badgeAction = new GroovyPostbuildAction(null, 'dummy')
            cause.getUpstreamRun().getActions().add(
                badgeAction.createBadge(
                    icon,
                    friendlyName,
                    manager.build.getAbsoluteUrl()
                )
            )
        }
    }

    /**
     * Adds a badge and summary entry to a parent build
     */
    static def addChildInfoToParentBuild(manager, icon, title) {
        addChildInfoToParentBuild(manager, icon, title, null)
    }

    /**
     * Adds a badge with the user's name to the current build
     *
     * @deprecated moved to net.flibidi.ci.AddUserCauseBadge
     */
    static def addUserCauseBadge(manager, cause) {
        if (cause.class.toString().contains("UpstreamCause")) {
            for (upCause in cause.upstreamCauses) {
                addUserCauseBadge(manager, upCause)
            }
        } else if (cause.class.toString().contains("UserIdCause")) {
            manager.addShortText(
                cause.getUserId(),
                "grey", "transparent", "0px", "transparent"
            )
        }
    }

    /**
     * Adds a badge with Jenkins node to the current build
     *
     * @deprecated moved to net.flibidi.ci.AddNodeBadge
     */
    static def addNodeBadge(manager) {
        manager.addBadge(
            "computer.png",
            manager.build.getBuiltOnStr(),
            manager.hudson.getRootUrl() + "computer/" + manager.build.getBuiltOnStr()
        )
    }

    /**
     * Reads a file located on a remote (build node) workspace
     */
    static def readRemoteFile(manager, contentFile) {
        def channel = null

        if (manager.build.workspace.isRemote()) {
            channel = manager.build.workspace.channel
        }

        def remoteFile = new hudson.FilePath(\
          channel, manager.build.workspace.toString() + "/" + contentFile )

        if (remoteFile.exists() && remoteFile.length() > 0) {
            return remoteFile.readToString()
        }
        return ""
    }

    /**
     * Adds a summary entry to a Jenkins build info page
     */
    static def addSummary(manager, icon, contentFile) {
        def remoteContent = readRemoteFile(manager, contentFile)

        if (remoteContent.length() > 0) {
            def summary = manager.createSummary(icon)
            summary.appendText(remoteContent, false)
        }
    }
}
