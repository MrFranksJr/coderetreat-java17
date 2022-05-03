package be.swsb.coderetreat.rover;

import java.util.Optional;

public record Obstacle(String name) {

    public static Obstacle of(String name) {
        return new Obstacle(name);
    }

    public static Optional<String> asString(Obstacle obstacle) {
        return Optional.ofNullable(obstacle)
                .map(o -> String.format("There's a %s! Ignoring other commands", o.name));
    }
}
