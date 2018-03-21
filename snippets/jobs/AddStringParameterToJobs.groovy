// Add a String parameter to all freestyle jobs matching a list of filters
import hudson.model.*

jobFilters = [
    "filter1",
    "filter2"
]

def filterJobs(jobs, filters) {
    filteredJobs = []
    for(job in jobs) {
        for (filter in filters) {
            if (job.getName().contains(filter)) {
                filteredJobs.add(job)
                continue
            }
        }
    }
    return filteredJobs
}

for(job in filterJobs(Hudson.instance.items, jobFilters)) {
    println("[ " + job.name + " ] setting " + key + "=" + value)

    newParam = new StringParameterDefinition(key, value, desc)
    paramDef = job.getProperty(ParametersDefinitionProperty.class)

    if (paramDef == null) {
        println(" - not parameterized: adding parameter definitions")
        newArrList = new ArrayList<ParameterDefinition>(1)
        newArrList.add(newParam)
        newParamDef = new ParametersDefinitionProperty(newArrList)
        job.addProperty(newParamDef)

    } else {
        println(" - already parameterized: checking parameter definitions")
        if (paramDef.parameterDefinitions.find{ it.name == key }) {
            println(" - already has a '"+key+"' parameter!")
            continue
        }
        println(" - adding new parameter: '" + key + "'")
        paramDef.parameterDefinitions.add(newParam)
    }
    println(" - saving configuration")
    job.save()
}
