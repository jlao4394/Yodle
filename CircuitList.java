public class CircuitList {
    private final Circuit[] circuits;

    public CircuitList(int capacity) {
        circuits = new Circuit[capacity];
    }

    public void set(int index, Circuit circuit) {
        circuits[index] = circuit;
    }

    public Circuit getCircuitByNumber(int number) {
        for (Circuit circuit : circuits) {
            if (circuit.getNumber() == number) {
                return circuit;
            }
        }

        return null;
    }

    public void assignToImmediateBest(Juggler juggler) {
        for (Circuit circuit : juggler.getPreferences()) {
            if (!circuit.isFull()) {
                circuit.addJuggler(juggler);
                return;
            }
        }

        //If unable to put into preferred, put into random
        for (Circuit circuit : circuits) {
            if (!circuit.isFull()) {
                circuit.addJuggler(juggler);
                return;
            }
        }
    }

    @Override
    public String toString() {
        String s = "";

        for (int i = circuits.length - 1; i >= 0; i--) {
            s += circuits[i].toString() + "\n";
        }

        return s;
    }
}
