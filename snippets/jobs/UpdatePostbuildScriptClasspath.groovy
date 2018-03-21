// Update the script classpath for all jobs using a postbuild step
//
// Useful when packaging Groovy scripts in a JAR for use with the Groovy
// Postbuild Plugin
//
// Quite hackish though, use with care!
import hudson.tasks.Publisher
import org.jvnet.hudson.plugins.groovypostbuild.GroovyPostbuildRecorder
import org.jenkinsci.plugins.scriptsecurity.sandbox.groovy.SecureGroovyScript
import org.jenkinsci.plugins.scriptsecurity.scripts.ClasspathEntry

def createGroovyPostbuildRecorder(groovyScript) {
    List<ClasspathEntry> classpathEntries = new ArrayList<ClasspathEntry>()
    classpathEntries.add(new ClasspathEntry("/path/to/custom-groovy.jar"))
    SecureGroovyScript script = new SecureGroovyScript(groovyScript, false, classpathEntries)
    return new GroovyPostbuildRecorder(script, 0, false)
}

def updateJob(job) {
  def publishers = job.getPublishersList()
  def index = -1

  for (publisher in publishers) {
    index += 1
    if (! publisher.class.name.equals("org.jvnet.hudson.plugins.groovypostbuild.GroovyPostbuildRecorder")) {
      continue
    }
    currentGroovyScript = publisher.script.script
    // publishers.replace(publishers.get(index), createGroovyPostbuildRecorder("DisplayBuildInfo.run(manager)"))
    publishers.replace(publishers.get(index), createGroovyPostbuildRecorder(currentGroovyScript))
    println(job.name + ": updated")
    job.save()
  }
}

for (job in Hudson.instance.items) {
  if (! job.class.name.equals("hudson.model.FreeStyleProject")) {
    println(job.name + ": Not a FreeStyleProject")
    continue
  }
  updateJob(job)
}
