abstract class Coffee {
    public abstract double getCost();
}

class SimpleCoffee extends Coffee {
    @Override
    public double getCost() {
        return 1.1;
    }
}

abstract class CoffeeDecorator extends Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    public double getCost() {
        return decoratedCoffee.getCost();
    }
}

class MilkDecorator extends CoffeeDecorator {
    // Implement the Milk decorator
    public MilkDecorator(Coffee coffee){
        super(coffee);
    }
    @Override
    public double getCost(){
        return super.getCost()+0.5;
    }
}

class SugarDecorator extends CoffeeDecorator {
    // Implement the Sugar decorator
    public SugarDecorator(Coffee coffee){
        super(coffee);
    }
    @Override
    public double getCost(){
        return super.getCost()+0.2;
    }
}

class CreamDecorator extends CoffeeDecorator {
    // Implement the Cream decorator
    public CreamDecorator(Coffee coffee){
        super(coffee);
    }
    @Override
    public double getCost(){
        return super.getCost()+0.7;
    }
}

/*
Coffee coffee = new SimpleCoffee();
coffee.getCost();    // 1.1

coffee = new MilkDecorator(coffee);
coffee.getCost();    // 1.6

coffee = new SugarDecorator(coffee);
coffee.getCost();    // 1.8

coffee = new CreamDecorator(coffee);
coffee.getCost();    // 2.5
*/