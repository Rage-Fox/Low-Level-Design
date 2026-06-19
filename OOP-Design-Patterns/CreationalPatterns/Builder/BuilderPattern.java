class Meal {

    private double cost;
    private boolean takeOut;
    private String main;
    private String drink;

    double getCost() {
        return this.cost;
    }

    boolean getTakeOut() {
        return this.takeOut;
    }

    String getMain() {
        return this.main;
    }

    String getDrink() {
        return this.drink;
    }

    void setCost(double cost) {
        this.cost = cost;
    }

    void setTakeOut(boolean takeOut) {
        this.takeOut = takeOut;
    }

    void setMain(String main) {
        this.main = main;
    }

    void setDrink(String drink) {
        this.drink = drink;
    }
}

class MealBuilder {
    private Meal meal;
    public MealBuilder() {
        this.meal=new Meal();
    }

    public MealBuilder addCost(double cost) {
        this.meal.setCost(cost);
        return this;
    }

    public MealBuilder addTakeOut(boolean takeOut) {
        this.meal.setTakeOut(takeOut);
        return this;
    }

    public MealBuilder addMainCourse(String main) {
        this.meal.setMain(main);
        return this;
    }

    public MealBuilder addDrink(String drink) {
        this.meal.setDrink(drink);
        return this;
    }

    Meal build() {
        return this.meal;
    }
}
/*
MealBuilder builder = new MealBuilder();
Meal myMeal = builder.addCost(15.99)
                     .addTakeOut(true)
                     .addMainCourse("Burger")
                     .addDrink("Coke")
                     .build();

myMeal.getCost();       // Cost: 15.99
myMeal.getTakeOut();    // TakeOut: true
myMeal.getMain();       // Main: "Burger"
myMeal.getDrink();      // Drink: "Coke"
*/
