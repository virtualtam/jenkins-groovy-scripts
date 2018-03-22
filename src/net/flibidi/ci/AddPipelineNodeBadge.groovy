package net.flibidi.ci

/**
 * Adds a badge with Jenkins Pipeline node to the current build
 */
class AddPipelineNodeBadge {

    /**
     * Adds a badge with Jenkins Pipeline node to the current build
     *
     * @param manager Groovy Postbuild manager
     * @param env     Pipeline environment
     */
    static def run(manager, env) {
        manager.addBadge(
            "computer.png",
            env.NODE_NAME,
            manager.hudson.getRootUrl() + "computer/" + env.NODE_NAME
        )
    }
}
