package be.swsb.coderetreat;


import be.swsb.coderetreat.rover.Rover;
import be.swsb.coderetreat.vector.Vector;
import org.junit.jupiter.api.Test;

import static be.swsb.coderetreat.rover.Orientation.*;
import static be.swsb.coderetreat.rover.Orientation.EAST;
import static org.assertj.core.api.Assertions.assertThat;

public class RoverTest {

    @Test
    void aDefaultRoverIsPositionedAt00AndIsFacingNorth() {
        final var rover = Rover.defaultRover();

        final var defaultRover = Rover.initialRover(new Vector(0, 0), NORTH);

        assertThat(rover).isEqualTo(defaultRover);
    }

    @Test
    void aRoverExecutesCommandsAsLongAsItUnderstandsThem() {
        final var rover = Rover.defaultRover();

        final var errorredRover = rover.receive("r,r,snarf,r");
        assertThat(errorredRover).isEqualTo(Rover.initialRover(new Vector(0, 0), WEST));
        assertThat(errorredRover.report()).isEqualTo("Could not parse [snarf] as a known command");
    }

    @Test
    void aRoverCanReportMultipleUnknownCommands() {
        final var rover = Rover.defaultRover();

        final var errorredRover = rover.receive("r,r,snarf,r,lion-o");
        assertThat(errorredRover).isEqualTo(Rover.initialRover(new Vector(0, 0), WEST));
        assertThat(errorredRover.report())
                .contains("Could not parse [snarf] as a known command")
                .contains("Could not parse [lion-o] as a known command");
    }

    @Test
    void roverCanTurnRight() {
        final var rover = Rover.defaultRover();

        assertThat(rover.receive("r")).isEqualTo(Rover.initialRover(new Vector(0, 0), EAST));
        assertThat(rover.receive("r,r")).isEqualTo(Rover.initialRover(new Vector(0, 0), SOUTH));
        assertThat(rover.receive("r,r,r")).isEqualTo(Rover.initialRover(new Vector(0, 0), WEST));
        assertThat(rover.receive("r,r,r,r")).isEqualTo(Rover.initialRover(new Vector(0, 0), NORTH));
    }

    @Test
    void roverCanTurnLeft() {
        final var rover = Rover.defaultRover();

        assertThat(rover.receive("l")).isEqualTo(Rover.initialRover(new Vector(0, 0), WEST));
        assertThat(rover.receive("l,l")).isEqualTo(Rover.initialRover(new Vector(0, 0), SOUTH));
        assertThat(rover.receive("l,l,l")).isEqualTo(Rover.initialRover(new Vector(0, 0), EAST));
        assertThat(rover.receive("l,l,l,l")).isEqualTo(Rover.initialRover(new Vector(0, 0), NORTH));
    }

    @Test
    void roverCanMoveForwards() {
        final var rover = Rover.initialRover(new Vector(0,0), NORTH);

        assertThat(rover.receive("f")).isEqualTo(Rover.initialRover(new Vector(0,1), NORTH));
        assertThat(rover.receive("f,f")).isEqualTo(Rover.initialRover(new Vector(0,2), NORTH));

        assertThat(rover.receive("r,f")).isEqualTo(Rover.initialRover(new Vector(1,0), EAST));
        assertThat(rover.receive("r,r,f")).isEqualTo(Rover.initialRover(new Vector(0,-1), SOUTH));
        assertThat(rover.receive("l,f")).isEqualTo(Rover.initialRover(new Vector(-1,0), WEST));
    }

    @Test
    void roverCanMoveBackwards() {
        final var rover = Rover.initialRover(new Vector(0,0), NORTH);

        assertThat(rover.receive("b")).isEqualTo(Rover.initialRover(new Vector(0,-1), NORTH));
        assertThat(rover.receive("b,b")).isEqualTo(Rover.initialRover(new Vector(0,-2), NORTH));

        assertThat(rover.receive("r,b")).isEqualTo(Rover.initialRover(new Vector(-1,0), EAST));
        assertThat(rover.receive("r,r,b")).isEqualTo(Rover.initialRover(new Vector(0,1), SOUTH));
        assertThat(rover.receive("l,b")).isEqualTo(Rover.initialRover(new Vector(1,0), WEST));
    }

}

