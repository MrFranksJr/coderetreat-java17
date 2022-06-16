package be.swsb.coderetreat.rover;

import be.swsb.coderetreat.vector.Vector;

import java.util.*;
import java.util.function.Function;

import static be.swsb.coderetreat.rover.Orientation.NORTH;
import static java.util.Collections.emptyList;

public class Rover {
    private final Vector position;
    private final Orientation orientation;
    private final List<String> errors;
    private final Function<Rover, Optional<String>> obstacleScanner;
    private final Obstacle obstacle;

    private Rover(Vector position,
                  Orientation orientation,
                  List<String> errors,
                  Function<Rover, Optional<String>> scanner, //TODO turn into functional interface
                  Obstacle obstacle) {
        this.position = position;
        this.orientation = orientation;
        this.errors = List.copyOf(errors);
        this.obstacleScanner = scanner;
        this.obstacle = obstacle;
    }

    public static Rover initialRover(Vector position, Orientation orientation) {
        return new Rover(position, orientation, emptyList(), r -> Optional.empty(), null);
    }

    public static Rover defaultRover() {
        return new Rover(new Vector(0, 0), NORTH, emptyList(), r -> Optional.empty(), null);
    }

    public static Rover aDefaultRoverWithScanner(Function<Rover, Optional<String>> scanner) {
        return new Rover(new Vector(0, 0), NORTH, emptyList(), scanner, null);
    }

    private static Rover error(Rover rover, String error) {
        final var errors = new ArrayList<>(rover.errors);
        errors.add(error);
        return new Rover(rover.position, rover.orientation, errors, rover.obstacleScanner, rover.obstacle);
    }

    private static Rover turn(Rover rover, Orientation orientation) {
        return new Rover(rover.position, orientation, rover.errors, rover.obstacleScanner, rover.obstacle);
    }

    private static Rover move(Rover rover, Vector position) {
        return new Rover(position, rover.orientation, rover.errors, rover.obstacleScanner, rover.obstacle);
    }

    private static Rover stop(Rover rover, String obstacle) {
        final var error = error(rover, Obstacle.of(obstacle).asString());
        return new Rover(error.position, error.orientation, error.errors, error.obstacleScanner, Obstacle.of(obstacle));
    }

    public Rover receive(String commands) {
        return Arrays.stream(commands.split(","))
                .reduce(this,
                        (rover, cmd) -> rover.receive(Command.parseToCommand(cmd)),
                        (r, r2) -> r2);
    }

    public String report() {
        final var messages = new ArrayList<>(errors);
        return String.join("\n", messages);
    }

    public Optional<String> scan() {
        return obstacleScanner.apply(this);
    }

    private Rover receive(Command command) {
        return (this.obstacle == null)
                ? command.execute(this)
                : new Ignored(command).execute(this);
    }

    @Override
    public String toString() {
        return "Rover{" +
                "position=" + position +
                ", orientation=" + orientation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rover rover = (Rover) o;
        return Objects.equals(position, rover.position) && orientation == rover.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, orientation);
    }

    sealed static abstract class Command {

        abstract Rover execute(Rover rover);

        protected Vector asVector(Orientation orientation) {
            return switch (orientation) {
                case NORTH -> new Vector(0, 1);
                case EAST -> new Vector(1, 0);
                case SOUTH -> new Vector(0, -1);
                case WEST -> new Vector(-1, 0);
            };
        }

        static Command parseToCommand(String cmd) {
            return switch (cmd.toLowerCase()) {
                case "r" -> new TurnRight();
                case "l" -> new TurnLeft();
                case "f" -> new Forwards();
                case "b" -> new Backwards();
                default -> new UnknownCommand(cmd);
            };
        }

        protected abstract String asString();
    }

    final static class UnknownCommand extends Command {
        private final String error;

        UnknownCommand(String unknownCmd) {
            this.error = "Could not parse [" + unknownCmd + "] as a known command";
        }

        Rover execute(Rover rover) {
            return error(rover, error);
        }

        @Override
        protected String asString() {
            return "unknown";
        }
    }

    final static class TurnRight extends Command {
        Rover execute(Rover rover) {
            return turn(rover, rover.orientation.turnRight());
        }

        @Override
        protected String asString() {
            return "right";
        }
    }

    final static class TurnLeft extends Command {
        Rover execute(Rover rover) {
            return turn(rover, rover.orientation.turnLeft());
        }

        @Override
        protected String asString() {
            return "left";
        }
    }

    final static class Forwards extends Command {
        Rover execute(Rover rover) {
            return rover.scan()
                    .map(obstacle -> stop(rover, obstacle))
                    .orElse(move(rover, rover.position.plus(asVector(rover.orientation))));
        }

        @Override
        protected String asString() {
            return "forwards";
        }
    }

    final static class Backwards extends Command {
        Rover execute(Rover rover) {
            return move(rover, rover.position.plus(asVector(rover.orientation).reversed()));
        }

        @Override
        protected String asString() {
            return "backwards";
        }
    }

    final static class Ignored extends Command {
        private final Command ignoredCommand;

        Ignored(final Command ignoredCommand) {
            this.ignoredCommand = ignoredCommand;
        }

        Rover execute(Rover rover) {
            return error(rover, this.asString());
        }

        @Override
        protected String asString() {
            return String.format("Ignored %s.", ignoredCommand.asString());
        }
    }
}
