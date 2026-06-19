/*
A Facade is an outward appearance that is maintained to conceal reality.
In programming, though, the Facade is a structural pattern that presents a complicated collection of classes in a simplified manner.
This allows clients easy access to the functionality of these classes.
We use the facade design pattern when we:
    -> Desire to encapsulate and abstract the underlying implementation details of classes.
    -> Desire a single access point to a module, layer, or library.
*/

package facade.java;

enum Brightness {
  UNKNOWN,
  BRIGHT,
  DIM
}

enum Service {
  UNKNOWN,
  HULU,
  NETFLIX,
  HBO
}

class SmartHomeSubSystem {

  private Brightness brightness;
  private int temperature;
  private boolean isSecurityArmed;
  private Service streamingService;

  public SmartHomeSubSystem() {
    this.brightness = Brightness.UNKNOWN;
    this.temperature = 19;
    this.isSecurityArmed = false;
    this.streamingService = Service.UNKNOWN;
  }

  public void setBrightness(Brightness brightness) {
    this.brightness = brightness;
  }

  public void setTemperature(int temperature) {
    this.temperature = temperature;
  }

  public void setIsSecurityArmed(boolean isSecurityArmed) {
    this.isSecurityArmed = isSecurityArmed;
  }

  public void setStreamingService(Service streamingService) {
    this.streamingService = streamingService;
  }

  private void enableMotionSensors() {
    // ...
  }

  private void updateFirmware() {
    // ...
  }
}

class SmartHomeFacade {

  private SmartHomeSubSystem smartHome;

  public SmartHomeFacade(SmartHomeSubSystem smartHome) {
    this.smartHome = smartHome;
  }

  public void setMovieMode() {
    smartHome.setBrightness(Brightness.DIM);
    smartHome.setTemperature(21);
    smartHome.setIsSecurityArmed(false);
    smartHome.setStreamingService(Service.NETFLIX);
  }

  public void setFocusMode() {
    smartHome.setBrightness(Brightness.BRIGHT);
    smartHome.setTemperature(22);
    smartHome.setIsSecurityArmed(true);
    smartHome.setStreamingService(Service.UNKNOWN);
  }
}

class Client {

  public static void main(String[] args) {
    SmartHomeFacade f = new SmartHomeFacade(new SmartHomeSubSystem());
    f.setMovieMode();
    f.setFocusMode();
  }
}
