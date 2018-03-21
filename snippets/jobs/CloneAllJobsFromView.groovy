// Clone all jobs from a view
//
// Example:
// - <View> myproject-v2
//   - <Job> myproject-v2-build
//   - <Job> myproject-v2-test
//   - <Job> myproject-v2-deploy
//
// Will be cloned as:
// - <View> myproject-v3
//   - <Job> myproject-v3-build
//   - <Job> myproject-v3-test
//   - <Job> myproject-v3-deploy
import hudson.model.*

def str_view = "myproject-v2"
def str_search = "v2"
def str_replace = "v3"
 
def view = Hudson.instance.getView(str_view)
 
for (item in view.getItems()) {
  newName = item.getName().replace(str_search, str_replace)
  
  // clone and save the job
  def job = Hudson.instance.copy(item, newName)
  job.disabled = true
  job.save()
  
  // replace the SpyCI product in the Shell task
  for (builder in job.getBuilders()) {
    if (! (builder instanceof hudson.tasks.Shell)) {
      continue
    }
    builder = new hudson.tasks.Shell(builder.getContents().replace(str_search, str_replace))
  }
  job.save()

  println(" $item.name copied as $newName")
}
