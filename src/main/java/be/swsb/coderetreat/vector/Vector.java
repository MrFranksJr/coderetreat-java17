package be.swsb.coderetreat.vector;

public record Vector(int x, int y) {

    public Vector plus(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    public Vector reversed() {
        return new Vector(this.x * -1, this.y * -1);
    }
}
