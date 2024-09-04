package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTest {
    //A live cell with fewer than two live neighbour dies
    @DisplayName("A live cell with fewer than two live neighbour dies")
    @Test
    public void testLiveCellWithFewerThanTwoLiveNeighbourShouldDie(){
        int liveNeighbors=1;
        Cell cell = new Cell(true);
        cell.update(liveNeighbors);
        assertFalse(cell.isAlive());

    }
    //A live cell with two or three live neighbors will live
    @DisplayName("A live cell with two or three live neighbors will live")
    @ParameterizedTest(name = "A live cell with {0} live neighbors should survive")
    @ValueSource(ints = {2,3})
    public void testLiveCellWithTwoOrMoreThreeLiveNeighborsWillLive(int liveNeighbors){
        Cell cell = new Cell(true);
        cell.update(liveNeighbors);
        assertTrue(cell.isAlive());
    }

    //A live cell with more than three live neighbors will die
    @DisplayName("A live cell with more than three live neighbors will die")
    @ParameterizedTest(name = "A live cell with {0} live neighbors should survive")
    @ValueSource(ints={4,5,6,7,8})
    public void testLiveWithMoreThanThreeLiveNeighborsWillDie(int liveNeighbors){
        Cell cell = new Cell(true);
        cell.update(liveNeighbors);
        assertFalse(cell.isAlive());
    }

    //A dead cell with exactly three live neighbors
    @DisplayName("A dead cell with exactly three live neighbors should become alive")
    @Test
    public void testDeadCellWithExactlyThreeLiveNeighborsWillBeAlive(){
        int liveNeighbors=3;
        Cell cell = new Cell(false);
        cell.update(liveNeighbors);
        assertTrue(cell.isAlive());
    }
}
