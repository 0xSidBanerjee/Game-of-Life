package com.conway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTest {

    private Cell liveCell;
    private Cell deadCell;

    @BeforeEach
    void setUp() {
        liveCell = new Cell(true);
        deadCell = new Cell(false);
    }
    @DisplayName("A live cell with fewer than two live neighbour dies")
    @Test
    public void testLiveCellWithFewerThanTwoLiveNeighbourShouldDie() {
        int liveNeighbors=1;
        liveCell.update(liveNeighbors);
        assertFalse(liveCell.isAlive());

    }

    @DisplayName("A live cell with two or three live neighbors will live")
    @ParameterizedTest(name = "A live cell with {0} live neighbors should survive")
    @ValueSource(ints = {2,3})
    public void testLiveCellWithTwoOrMoreThreeLiveNeighborsWillLive(int liveNeighbors) {
        liveCell.update(liveNeighbors);
        assertTrue(liveCell.isAlive());
    }

    @DisplayName("A live cell with more than three live neighbors will die")
    @ParameterizedTest(name = "A live cell with {0} live neighbors should survive")
    @ValueSource(ints={4,5,6,7,8})
    public void testLiveWithMoreThanThreeLiveNeighborsWillDie(int liveNeighbors) {
        liveCell.update(liveNeighbors);
        assertFalse(liveCell.isAlive());
    }

    @DisplayName("A dead cell with exactly three live neighbors should become alive")
    @Test
    public void testDeadCellWithExactlyThreeLiveNeighborsWillBeAlive() {
        int liveNeighbors=3;
        deadCell.update(liveNeighbors);
        assertTrue(deadCell.isAlive());
    }
}
