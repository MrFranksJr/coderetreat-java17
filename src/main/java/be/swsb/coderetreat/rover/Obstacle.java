package be.swsb.coderetreat.rover;

import java.util.Optional;

public record Obstacle(String name) {

    public String asString() {
        return String.format("There's a %s! Ignoring other commands.", name);
    }

    public static Obstacle of(String name) {
        return new Obstacle(name);
    }
}
