package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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
}
