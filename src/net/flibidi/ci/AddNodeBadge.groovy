package net.flibidi.ci

/**
 * Adds a badge with Jenkins node to the current build
 */
class AddNodeBadge {

    /**
     * Adds a badge with Jenkins node to the current build
     *
     * @param manager Groovy Postbuild manager
     */
    static def run(manager) {
        manager.addBadge(
            "computer.png",
            manager.build.getBuiltOnStr(),
            manager.hudson.getRootUrl() + "computer/" + manager.build.getBuiltOnStr()
        )
    }
}
