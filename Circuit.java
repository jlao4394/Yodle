public class Circuit extends ScoredObject {
    private final Juggler[] jugglers;
    private final int[] scores;
    private int index = -1;
    private int worstScore = Integer.MAX_VALUE;
    private Juggler worstJuggler;

    public Circuit(int number, int h, int e, int p, int capacity) {
        super(number, h, e, p);
        this.jugglers = new Juggler[capacity];
        this.scores = new int[capacity];
    }

    public void addJuggler(Juggler juggler) {
        index++;
        jugglers[index] = juggler;
        juggler.setCircuit(this);

        int matchScore = this.getMatchScore(juggler);
        scores[index] = matchScore;
        if (matchScore < worstScore) {
            worstScore = matchScore;
            worstJuggler = juggler;
        }
    }

    public void replaceJuggler(Juggler oldJuggler, Juggler newJuggler, int newMatchScore) {
        for (int i = 0; i <= index; i++) {
            if (jugglers[i].equals(oldJuggler)) {
                jugglers[i] = newJuggler;
                scores[i] = newMatchScore;
                newJuggler.setCircuit(this);

                if (worstJuggler.equals(oldJuggler)) {
                    worstScore = Integer.MAX_VALUE;
                    for (int z = 0; z <= index; z++) {
                        int score = scores[z];
                        if (score < worstScore) {
                            worstScore = score;
                            worstJuggler = jugglers[z];
                        }
                    }
                }

                return;
            }
        }
    }

    public int getWorstScore() {
        return worstScore;
    }

    public Juggler getWorstJuggler() {
        return worstJuggler;
    }

    public boolean isFull() {
        return index == jugglers.length - 1;
    }

    @Override
    public boolean equals(final Object object) {
        return object instanceof Circuit && ((Circuit) object).getNumber() == this.getNumber();
    }

    @Override
    public String toString() {
        String s = "C" + this.getNumber() + " ";

        for (int i = 0; i <= index; i++) {
            s += jugglers[i].toString();

            if (i != index) {
                s += ", ";
            }
        }

        return s;
    }
}
