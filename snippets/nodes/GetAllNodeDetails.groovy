// Display detailed information about all nodes
for (agent in hudson.model.Hudson.instance.slaves) {
  println('====================');
  println('Name: ' + agent.name);
  println('getLabelString: ' + agent.getLabelString());
  println('getNumExectutors: ' + agent.getNumExecutors());
  println('getRemoteFS: ' + agent.getRemoteFS());
  println('getMode: ' + agent.getMode());
  println('getRootPath: ' + agent.getRootPath());
  println('getDescriptor: ' + agent.getDescriptor());
  println('getComputer: ' + agent.getComputer());
  println('\tcomputer.isAcceptingTasks: ' + agent.getComputer().isAcceptingTasks());
  println('\tcomputer.isLaunchSupported: ' + agent.getComputer().isLaunchSupported());
  println('\tcomputer.getConnectTime: ' + agent.getComputer().getConnectTime());
  println('\tcomputer.getDemandStartMilliseconds: ' + agent.getComputer().getDemandStartMilliseconds());
  println('\tcomputer.isOffline: ' + agent.getComputer().isOffline());
  println('\tcomputer.countBusy: ' + agent.getComputer().countBusy());
  println('\tcomputer.getLog: ' + agent.getComputer().getLog());
  println('\tcomputer.getBuilds: ' + agent.getComputer().getBuilds());
}
