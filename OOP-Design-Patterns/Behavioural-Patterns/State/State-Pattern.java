interface State {
    void handleRequest(Document doc);
}

class Document {
    private State state;
    private boolean isApproved;

    public Document() {
        this.state = new Draft();
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void publish() {
        this.state.handleRequest(this);
    }

    public void setApproval(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public boolean isApproved() {
        return this.isApproved;
    }
}

class Draft implements State {
    @Override
    public void handleRequest(Document doc) {
        // Write your code here
        doc.setState(new Review());
    }
}

class Review implements State {
    @Override
    public void handleRequest(Document doc) {
        // Write your code here
        if (doc.isApproved()) {
            doc.setState(new Published());
        } else {
            doc.setState(new Draft());
        }
    }
}

class Published implements State {
    @Override
    public void handleRequest(Document doc) {
        // Write your code here
        // Final state, no action needed
    }
}

/*
Document document = new Document();
document.getState() instanceof Draft; // true

document.publish();
document.getState() instanceof Review; // true

document.publish();
document.getState() instanceof Draft; // true

document.setApproval(true);
document.publish(); // Draft -> Review
document.publish(); // Review -> Published
document.getState() instanceof Published; // true

document.publish();
document.getState() instanceof Published; // true
*/