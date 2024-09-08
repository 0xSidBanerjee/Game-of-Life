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
}
