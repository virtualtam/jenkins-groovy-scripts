// THIS WILL SEND AN INTERRUPT TO ALL MATCHING THREADS
Thread.getAllStackTraces().keySet().each() {
  t -> if (t.getName().contains("PATTERN") ) { t.interrupt(); }
}
