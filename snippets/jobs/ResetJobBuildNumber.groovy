// Reset a job's build history and next build number
item = Jenkins.instance.getItemByFullName("your-job-name-here")
nextBuildNumber = 1

// HERE BE DRAGONS: THIS WILL REMOVE ALL BUILD HISTORY!
item.builds.each() { build ->
  build.delete()
}

item.updateNextBuildNumber(nextBuildNumber)
