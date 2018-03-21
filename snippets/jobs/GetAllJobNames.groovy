// Get the name of all jobs
println(Hudson.instance.items.size() + ' jobs: ')
for (job in Hudson.instance.items) {
  println(' - ' + job.getName())
}
