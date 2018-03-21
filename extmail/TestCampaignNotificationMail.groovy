<%
/**
 * Generates a message summarizing the results of a test campaign.
 *
 * Information is retrieved from the Jenkins build and environment variables.
 *
 * USAGE
 * This template has to be copied to / symlinked from the following location:
 * $JENKINS_HOME/email-templates
 *
 * DEPENDENCIES
 * - Jenkins Email Ext (extended e-mail) plugin
 * - Jenkins user e-mail (eg. resolver from the LDAP plugin)
 */
def message = "Dear"
if (build.envVars.PROJECT) {
  message += " ${build.envVars.PROJECT}"
}

// user-started vs. timer / event-triggered job
if (build.envVars.SANITY_RECIPIENTS) {
  message += " team"
} else {
  message += " developer"
}
message += ",\n\n"

switch (build.envVars.BUILD_CAUSE) {
case "MANUALTRIGGER":
  message += "A test campaign has been run for the following images:\n"

  build.envVars.urls.tokenize(" ").each { url->
    message += "- ${url}\n"
  }
  break

case "UPSTREAMTRIGGER":
  build_info = build.envVars.UPSTREAM_BUILD_URL.tokenize("/")

  message += "A test campaign has been run for "
  message += "${build_info[-2]} #${build_info[-1]}.\n\n"
  break
}
%>
${message}
Campaign result: ${build.result}
Test job URL: ${rooturl}${build.url}
Project: ${project.name}
Start time: ${it.timestampString}
Duration: ${build.durationString}

Regards,

The Intertubes
