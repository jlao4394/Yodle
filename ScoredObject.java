public abstract class ScoredObject {
    //Precomputed multiplication and addition tables shaves off a decent amount (50ms -> 20ms for assigning)
    private static final int[][] multiplicationTable = new int[11][11];
    private static final int[][] additionTable = new int[201][101];

    static {
        for (int i = multiplicationTable.length - 1; i >= 0; i--) {
            for (int x = multiplicationTable[i].length - 1; x >= 0; x--) {
                multiplicationTable[i][x] = i * x;
            }
        }

        for (int i = additionTable.length - 1; i >= 0; i--) {
            for (int x = additionTable[i].length - 1; x >= 0; x--) {
                additionTable[i][x] = i + x;
            }
        }
    }

    private final int number;
    private final int h;
    private final int e;
    private final int p;

    public ScoredObject(int number, int h, int e, int p) {
        this.number = number;
        this.h = h;
        this.e = e;
        this.p = p;
    }

    public int getNumber() {
        return number;
    }

    //Uses precomputed numbers
    public int getMatchScore(ScoredObject scoredObject) {
        int mH = multiplicationTable[h][scoredObject.h];
        int mE = multiplicationTable[e][scoredObject.e];
        int mP = multiplicationTable[p][scoredObject.p];
        int aHE = additionTable[mH][mE];
        return additionTable[aHE][mP];
    }
}
