static class Singleton {
    private String value = null;
    private static volatile Singleton unqInst = null;
    private Singleton() {
        value = null;
    }

    public static Singleton getInstance() {
        if (unqInst == null) {
            synchronized (Singleton.class) {
                if (unqInst == null) {
                    unqInst = new Singleton();
                }
            }
        }
        return unqInst;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

/*
Singleton s = Singleton.getInstance();

s.getValue(); // null

s.setValue("a value string");
s.getValue(); // "a value string"

Singleton s2 = Singleton.getInstance();

s2.getValue(); // "a value string"
*/
