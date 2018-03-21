// Set the label for nodes matching a given pattern
nodePattern = "myproject-build-"
newLabel = "mylabel"

for (agent in hudson.model.Hudson.instance.slaves) {
  if (! agent.name.contains(nodePattern)) {
    continue
  }
  println(agent.name)
  println(' - before: ' + agent.getLabelString())
  agent.setLabelString(newLabel)
  println(' - after: ' + agent.getLabelString())
}
