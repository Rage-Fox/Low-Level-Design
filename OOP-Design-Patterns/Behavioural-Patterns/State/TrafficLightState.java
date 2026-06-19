// The state pattern is a behavioral design pattern that allows an object to alter its behavior based on its internal state.
// Each state behavior is encapsulated inside of a class, which adheres to a State interface.
// The goal of this pattern is to achieve variable behavior without the need for complex if/else blocks or switch statements.

/**
 * Client class
 */
class Client {

  public static void main(String[] args) {
    TrafficLight lightSystem = new TrafficLight();

    lightSystem.change(); // Red - Stop!
    lightSystem.change(); // Yellow (from Red to Green) - caution!
    lightSystem.change(); // Green - go!
    lightSystem.change(); // Yellow (from Green to Red) - caution!
    lightSystem.change(); // Red - Stop!
    lightSystem.change(); // Yellow (from Red to Green) - caution!
    lightSystem.change(); // Green - go!
  }
}

/**
 * State
 */
interface TrafficLightState {
  void changeState(TrafficLight trafficLight);
}

/**
 * Concrete State
 */
class GreenState implements TrafficLightState {

  @Override
  public void changeState(TrafficLight light) {
    System.out.println("Green - go!");
    light.setState(new YellowState());
  }
}

/**
 * Concrete State
 */
class YellowState implements TrafficLightState {

  @Override
  public void changeState(TrafficLight light) {
    if (light.getPrevState() instanceof RedState) {
      System.out.println("Yellow (from Red to Green) - caution!");
      light.setState(new GreenState());
    } else {
      System.out.println("Yellow (from Green to Red) - caution!");
      light.setState(new RedState());
    }
  }
}

/**
 * Concrete State
 */
class RedState implements TrafficLightState {

  @Override
  public void changeState(TrafficLight light) {
    System.out.println("Red - Stop!");
    light.setState(new YellowState());
  }
}

class TrafficLight {

  private TrafficLightState state;
  private TrafficLightState prevState;

  TrafficLight() {
    this.state = new RedState();
    this.prevState = null;
  }

  void setState(TrafficLightState state) {
    this.prevState = this.state;
    this.state = state;
  }

  TrafficLightState getPrevState() {
    return this.prevState;
  }

  void change() {
    this.state.changeState(this);
  }
}