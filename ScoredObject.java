public abstract class ScoredObject {
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
        return this.h * scoredObject.h + this.e * scoredObject.e + this.m * scoredObject.m;
    }
}
