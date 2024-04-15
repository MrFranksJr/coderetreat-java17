package be.swsb.coderetreat;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

record Coordinate(int x, int y){
    Set<Coordinate> getNeighbours() {
        return Set.of(
                new Coordinate(x + 1, y + 1),
                new Coordinate(x + 1, y),
                new Coordinate(x + 1, y - 1),
                new Coordinate(x, y + 1),
                new Coordinate(x, y - 1),
                new Coordinate(x - 1, y + 1),
                new Coordinate(x - 1, y ),
                new Coordinate(x - 1, y - 1)
        );
    }
}

public class World {
    Set<Coordinate> aliveCells = new HashSet<>();

    private Set<Coordinate> getCellToOperateOn() {
        return aliveCells.stream()
                .map(aliveCell -> aliveCell.getNeighbours())
                .reduce((acc, value) -> {acc.addAll(value); return acc;}, new HashSet<>()).stream().collect(Collectors.toSet());
    }

}
