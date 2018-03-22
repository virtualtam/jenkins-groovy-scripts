package net.flibidi.ci

/**
 * Adds a badge with the user's name to manually triggered builds
 */
class AddUserCauseBadge {

    /**
     * Adds a badge with the user's name to the current build
     *
     * @param manager Groovy Postbuild manager
     */
    static def run(manager) {
        for (cause in manager.build.causes) {
            addUserCauseBadge(manager, cause)
        }
    }

    /**
     * Adds a badge with the user's name to the current build
     *
     * The user id is searched for recursively, in case the build has been
     * triggered by an upstream job.
     *
     * @param manager Groovy Postbuild manager
     * @param cause   One of the causes that has triggered the build
     */
    static def addUserCauseBadge(manager, cause) {
        if (cause.class.toString().contains("UpstreamCause")) {
            for (upCause in cause.upstreamCauses) {
                addUserCauseBadge(manager, upCause)
            }
        } else if (cause.class.toString().contains("UserIdCause")) {
            manager.addShortText(
                cause.getUserId(),
                "grey",
                "transparent",
                "0px",
                "transparent"
            )
        }
    }
}
