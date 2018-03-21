// Show all freestyle jobs calling a given publisher class
lookupClass = "org.jvnet.hudson.plugins.groovypostbuild.GroovyPostbuildRecorder"

for (job in Hudson.instance.items) {
  if (! job.class.name.equals("hudson.model.FreeStyleProject")) {
    continue
  }
  def publishers = job.getPublishersList()

  for (publisher in publishers) {
    if (! publisher.class.name.equals(lookupClass)) {
      continue
    }
    println(job.name + ":\n" + publisher.script.script + "\n\n")
  }
}
