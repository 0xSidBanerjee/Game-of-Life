package com.conway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(10,10);
        for (int row = 0; row < grid.getRows(); row++){
            for (int col = 0; col < grid.getCols(); col++){
                grid.setCellState(row, col, false);
            }
        }
    }

    @DisplayName("Initialize a grid with all dead cells")
    @Test
    public void testInitializeGrid(){
        assertEquals(10, grid.getRows());
        assertEquals(10, grid.getCols());
        for(int row=0; row<grid.getRows(); row++){
            for(int col=0; col<grid.getCols(); col++){
                assertFalse(grid.getCellState(row, col));
            }
        }

    }

    @DisplayName("Should set and retrieve the state of a specific cell in the grid")
    @ParameterizedTest(name = "Set and retrieve the state of a cell in the grid at ({0},{1})")
    @CsvSource({
            "0,5",
            "7,7",
            "9,2"
    })
    public void testSetGridCellState(int row, int col){
        grid.setCellState(row,col, true);
        assertEquals(true, grid.getCellState(row,col));
    }

    @DisplayName("Should determine if a cell if within the grid")
    @Test
    public void testCellWithinGrid() {
        assertTrue(grid.isValidCell(4,5));
        assertFalse(grid.isValidCell(10,1));
    }

    @DisplayName("Calculate live neighbor cells for a cell(middle of the grid)")
    @ParameterizedTest(name = "Number for live neighbors for cell ({0}, {1})")
    @CsvSource({
            "6,4",
            "4,5"
    })
    public void testCountLiveNeighbors(int row, int col) {
        grid.setCellState(0,1,true);
        grid.setCellState(5,5, true);
        assertEquals(1, grid.countLiveNeighbors(row, col));
    }

    @DisplayName("Calculate live neighbor cells for an edge cell")
    @ParameterizedTest(name = "Number of live neighbors for cell({0}, {1})")
    @CsvSource({
            "0,4",
            "1,9",
            "6,0"
    })
    public void testCountLiveNeighborsEdgeCell(int row, int col) {
        grid.setCellState(0,3, true);
        grid.setCellState(0,5, true);
        grid.setCellState(1,8, true);
        grid.setCellState(2,9, true);
        grid.setCellState(5,0, true);
        grid.setCellState(6,1, true);
        assertEquals(2, grid.countLiveNeighbors(row, col));
    }

    @DisplayName("Calculate live neighbor cells for an corner cell")
    @ParameterizedTest(name = "Number of live neighbors for cell({0}, {1})")
    @CsvSource({
            "0,0",
            "9,9",
            "0,9",
            "9,0"
    })
    public void testCountLiveNeighborsCornerCell(int row, int col) {
        grid.setCellState(0,1, true);
        grid.setCellState(1,0, true);
        grid.setCellState(8,8, true);
        grid.setCellState(8,9, true);
        grid.setCellState(1,8, true);
        grid.setCellState(0,8, true);
        grid.setCellState(9,1, true);
        grid.setCellState(8,1, true);
        assertEquals(2, grid.countLiveNeighbors(row, col));
    }

    @DisplayName("Update entire grid and produce next generation")
    @Test
    public void testUpdateGridToNextGeneration() {
        boolean[][] initialState = {
                {true, false, false, false},
                {true, true, false, true},
                {true, false, true, false},
                {true, true, false, true}
        };

        grid = new Grid(4,4, initialState);
        grid.nextGeneration();

        assertTrue(grid.getCellState(0,0));
        assertTrue(grid.getCellState(0,1));
        assertTrue(grid.getCellState(1,0));
        assertTrue(grid.getCellState(1,2));
        assertTrue(grid.getCellState(2,3));
        assertTrue(grid.getCellState(3,0));
        assertTrue(grid.getCellState(3,1));
        assertTrue(grid.getCellState(3,2));
    }

    @DisplayName("Verification of a stable structure")
    @Test
    public void testStableStructure() {
        boolean[][] initialState = {
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}
        };

        grid = new Grid(4,4, initialState);
        grid.nextGeneration();

        // Remains alive
        assertTrue(grid.getCellState(1,1));
        assertTrue(grid.getCellState(1,2));
        assertTrue(grid.getCellState(2,1));
        assertTrue(grid.getCellState(2,2));

        //Remains dead
        assertFalse(grid.getCellState(0,3));
        assertFalse(grid.getCellState(2,0));
        assertFalse(grid.getCellState(3,1));
        assertFalse(grid.getCellState(2,3));
    }

    @DisplayName("Verification of a blinker")
    @Test
    public void testBlinker() {
        boolean[][] initialState = {
                {false, true, false},
                {false, true, false},
                {false, true, false}
        };

        grid = new Grid(3,3, initialState);
        grid.nextGeneration();
        grid.nextGeneration(); //returns to the same state after 2 generations

        // Remains alive
        assertTrue(grid.getCellState(0,1));
        assertTrue(grid.getCellState(1,1));
        assertTrue(grid.getCellState(2,1));

        //Remains dead
        assertFalse(grid.getCellState(1,0));
        assertFalse(grid.getCellState(2,0));
        assertFalse(grid.getCellState(1,2));
    }

    @DisplayName("An empty grid remains empty over generations")
    @Test
    public void testEmptyGridOverGenerations() {
        grid.nextGeneration();
        grid.nextGeneration();
        grid.nextGeneration();
        grid.nextGeneration();
        grid.nextGeneration();

        for(int row = 0; row < grid.getRows(); row++){
            for(int col = 0; col < grid.getCols(); col++){
                assertFalse(grid.getCellState(row, col));
            }
        }
    }

    @DisplayName("All cells die if grid is full of live cells")
    @Test
    public void testOvercrowding() {
        boolean[][] initialState ={
                {true, true, true, true},
                {true, true, true, true},
                {true, true, true, true},
                {true, true, true, true}
        };
        grid = new Grid(4,4, initialState);
        grid.nextGeneration();
        grid.nextGeneration();
        grid.nextGeneration();

        for(int row = 0; row < grid.getRows(); row++){
            for(int col = 0; col < grid.getCols(); col++){
                assertFalse(grid.getCellState(row, col));
            }
        }
    }

    @DisplayName("Test a glider pattern and verify its movement over multiple generations")
    @Test
    public void testGliderPatternMovement() {
        boolean[][] gliderPattern = {
                {false, true, false, false, false},
                {false, false, true, false, false},
                {true, true, true, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
        };

        grid = new Grid(5, 5, gliderPattern);

        // First generation movement
        grid.nextGeneration();
        assertTrue(grid.getCellState(1, 0));
        assertTrue(grid.getCellState(1, 2));
        assertTrue(grid.getCellState(2, 1));
        assertTrue(grid.getCellState(2, 2));
        assertTrue(grid.getCellState(3, 1));

        // Second generation movement
        grid.nextGeneration();
        assertTrue(grid.getCellState(1, 2));
        assertTrue(grid.getCellState(2, 0));
        assertTrue(grid.getCellState(2, 2));
        assertTrue(grid.getCellState(3,1));
        assertTrue(grid.getCellState(3,2));

        // Third generation movement (Glider moves diagonally)
        grid.nextGeneration();
        assertTrue(grid.getCellState(1, 1));
        assertTrue(grid.getCellState(2, 2));
        assertTrue(grid.getCellState(2, 3));
        assertTrue(grid.getCellState(3, 1));
        assertTrue(grid.getCellState(3, 2));
    }

}
