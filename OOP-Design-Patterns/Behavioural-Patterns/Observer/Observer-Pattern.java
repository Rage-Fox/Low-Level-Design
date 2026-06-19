interface Observer {
    void notify(String itemName);
}

class Customer implements Observer {
    private String name;
    private int notifications;

    public Customer(String name) {
        this.name = name;
        this.notifications = 0;
    }

    public void notify(String itemName) {
        this.notifications += 1;
    }

    public int countNotifications() {
        return this.notifications;
    }
}

class OnlineStoreItem {
    private String itemName;
    private int stock;
    private List<Observer> observers;

    public OnlineStoreItem(String itemName, int stock) {
        this.itemName = itemName;
        this.stock = stock;
        this.observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void updateStock(int newStock) {
        int oldStock = this.stock;
        this.stock = newStock;

        if (oldStock == 0 && newStock > 0) {
            notifyObservers();
        }
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.notify(itemName);
        }
    }
}

/*
OnlineStoreItem item1 = new OnlineStoreItem("Awesome Gadget", 0);

Customer customer1 = new Customer("John Doe");
Customer customer2 = new Customer("Jane Doe");

item1.subscribe(customer1);
item1.subscribe(customer2);
item1.updateStock(5); // customer1 and customer2 are notified

item1.unsubscribe(customer1);

item1.updateStock(0); // No one is notified
item1.updateStock(3); // Only customer2 is notified this time
item1.updateStock(2); // No one is notified

customer1.countNotifications(); // 1
customer2.countNotifications(); // 2
*/