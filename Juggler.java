public class Juggler extends ScoredObject {
    //Store all preferences for output
    private final Circuit[] preferences;
    private int front = 0;
    private Circuit circuit;

    public Juggler(int number, int h, int e, int p, Circuit[] preferences) {
        super(number, h, e, p);
        this.preferences = preferences;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public Circuit[] getPreferences() {
        return preferences;
    }

    public Circuit pollPreferences() {
        if (front == preferences.length) {
            return null;
        } else return preferences[front++];
    }

    @Override
    public String toString() {
        String s = "J" + this.getNumber() + " ";

        for (int i = 0; i < preferences.length; i++) {
            s += "C" + preferences[i].getNumber() + ":" + preferences[i].getMatchScore(this);

            if (i != preferences.length - 1) {
                s += " ";
            }
        }

        return s;
    }

    @Override
    public boolean equals(final Object object) {
        return object instanceof Juggler && ((Juggler) object).getNumber() == this.getNumber();
    }
}
