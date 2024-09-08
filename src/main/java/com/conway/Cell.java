package com.conway;

public class Cell {

    private boolean alive;

    Cell (boolean alive) {
        this.alive=alive;
    }

    public void update(int liveNeighbors) {
        if(alive) {
            if (liveNeighbors <= 3 && liveNeighbors >= 2) {
                this.alive = true;
            } else {
                this.alive = false;
            }
        }
        else {
            this.alive = liveNeighbors==3;
        }
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setState(boolean state){
        this.alive=state;
    }
}
