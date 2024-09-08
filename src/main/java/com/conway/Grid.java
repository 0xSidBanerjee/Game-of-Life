package com.conway;

public class Grid {

    private int rows;
    private int cols;
    private Cell[][] cells;

    public Grid(int rows, int cols){
        this.rows=rows;
        this.cols=cols;
        this.cells= new Cell[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                cells[row][col] = new Cell(false);
            }
        }
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public boolean getCellState(int rows, int cols) {
        return cells[rows][cols].isAlive();
    }

    public void setCellState(int rows, int cols, boolean state) {
        cells[rows][cols].setState(state);
    }

    public boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col <cols;
    }

    public int countLiveNeighbors(int row, int col) {
        int liveNeighbors = 0;

        for(int i=-1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if (i==0 && j==0){
                    continue;
                }
                if(isValidCell(row+i, col+j) && cells[row+i][col+j].isAlive()){
                    liveNeighbors++;
                }
            }
        }
        return liveNeighbors;
    }
}
