package be.swsb.coderetreat;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameOfLifeTest {

    @ParameterizedTest
    @MethodSource("overPopulationArguments")
    void recognizesOverPopulation(boolean isAlive, int livingNeighbors, boolean expected) {
        assertEquals(expected, Rule.overPopulated(isAlive, livingNeighbors));
    }

    @ParameterizedTest
    @MethodSource("underPopulationArguments")
    void recognizesUnderPopulation(boolean isAlive, int livingNeighbors, boolean expected) {
        assertEquals(expected, Rule.underPopulated(isAlive, livingNeighbors));
    }

    @ParameterizedTest
    @MethodSource("reproduceArguments")
    void recognizesReproducing(boolean isAlive, int livingNeighbors, boolean expected) {
        assertEquals(expected, Rule.reproducing(isAlive, livingNeighbors));
    }

    @ParameterizedTest
    @MethodSource("combined")
    void composedRules(boolean isAlive, int livingNeighbours, boolean expected) {
        assertEquals(expected, Rule.applyAll(isAlive, livingNeighbours));
    }
    static Stream<Arguments> combined() {
        return Stream.of(
                Arguments.of(true, 8, false),
                Arguments.of(true, 7, false),
                Arguments.of(true, 6, false),
                Arguments.of(true, 5, false),
                Arguments.of(true, 4, false),
                Arguments.of(true, 3, true),
                Arguments.of(true, 2, true),
                Arguments.of(true, 1, false),
                Arguments.of(true, 0, false),
                Arguments.of(false, 8, false),
                Arguments.of(false, 7, false),
                Arguments.of(false, 6, false),
                Arguments.of(false, 5, false),
                Arguments.of(false, 4, false),
                Arguments.of(false, 3, true),
                Arguments.of(false, 2, false),
                Arguments.of(false, 1, false),
                Arguments.of(false, 0, false)
        );
    }
    static Stream<Arguments> overPopulationArguments() {
        return Stream.of(
                Arguments.of(true, 8, false),
                Arguments.of(true, 7, false),
                Arguments.of(true, 6, false),
                Arguments.of(true, 5, false),
                Arguments.of(true, 4, false),
                Arguments.of(true, 3, true),
                Arguments.of(true, 2, true),
                Arguments.of(true, 1, true),
                Arguments.of(true, 0, true),
                Arguments.of(false, 8, false),
                Arguments.of(false, 7, false),
                Arguments.of(false, 6, false),
                Arguments.of(false, 5, false),
                Arguments.of(false, 4, false),
                Arguments.of(false, 3, false),
                Arguments.of(false, 2, false),
                Arguments.of(false, 1, false),
                Arguments.of(false, 0, false)
        );
    }

    static Stream<Arguments> underPopulationArguments() {
        return Stream.of(
                Arguments.of(true, 8, true),
                Arguments.of(true, 7, true),
                Arguments.of(true, 6, true),
                Arguments.of(true, 5, true),
                Arguments.of(true, 4, true),
                Arguments.of(true, 3, true),
                Arguments.of(true, 2, true),
                Arguments.of(true, 1, false),
                Arguments.of(true, 0, false),
                Arguments.of(false, 8, false),
                Arguments.of(false, 7, false),
                Arguments.of(false, 6, false),
                Arguments.of(false, 5, false),
                Arguments.of(false, 4, false),
                Arguments.of(false, 3, false),
                Arguments.of(false, 2, false),
                Arguments.of(false, 1, false),
                Arguments.of(false, 0, false)
        );
    }

    static Stream<Arguments> reproduceArguments() {
        return Stream.of(
                Arguments.of(true, 8, true),
                Arguments.of(true, 7, true),
                Arguments.of(true, 6, true),
                Arguments.of(true, 5, true),
                Arguments.of(true, 4, true),
                Arguments.of(true, 3, true),
                Arguments.of(true, 2, true),
                Arguments.of(true, 1, true),
                Arguments.of(true, 0, true),
                Arguments.of(false, 8, false),
                Arguments.of(false, 7, false),
                Arguments.of(false, 6, false),
                Arguments.of(false, 5, false),
                Arguments.of(false, 4, false),
                Arguments.of(false, 3, true),
                Arguments.of(false, 2, false),
                Arguments.of(false, 1, false),
                Arguments.of(false, 0, false)
        );
    }
}
