package be.swsb.coderetreat;

class Rule {
    public static boolean overPopulated(boolean alive, int livingNeighbors) {
        return alive && livingNeighbors <= 3;
    }
    public static boolean underPopulated(boolean alive, int livingNeighbors) {
        return alive && livingNeighbors >= 2;
    }
    public static boolean reproducing(boolean alive, int livingNeighbors) {
        return alive || livingNeighbors == 3;
    }

    public static boolean applyAll(boolean alive, int livingNeighbors) {
        return overPopulated(underPopulated(reproducing(alive, livingNeighbors),livingNeighbors), livingNeighbors);
    }
}