// THIS WILL FORCE-STOP ALL MATCHING THREADS
// May be required for recursive loops
Thread.getAllStackTraces().keySet().each() {
  t -> if (t.getName().contains("PATTERN") ) { t.stop(); }
}
