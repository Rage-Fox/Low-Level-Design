// The adapter pattern is a structural pattern that converts the interface of a class into another interface to make it compatible with additional clients.
// It allows classes to work together that couldn't otherwise because of incompatible interfaces.

interface JsonLogger {
  void logMessage(String message);
}

class XmlLogger {
  public void log(String xmlMessage) {
    System.out.println(xmlMessage);
  }
}

class LoggerAdapter implements JsonLogger {
  private final XmlLogger xmlLogger;

  public LoggerAdapter(XmlLogger xmlLogger) {
    this.xmlLogger = xmlLogger;
  }

  @Override
  public void logMessage(String message) {
    xmlLogger.log(message);
  }
}

class Main {
  public static void main(String[] args) {
    JsonLogger logger = new LoggerAdapter(new XmlLogger());
    logger.logMessage("<message>hello</message>");
  }
}
