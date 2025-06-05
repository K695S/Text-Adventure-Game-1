package org.uob.a1;

public class Position {
    public int x;
    public int y;

    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }

    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

}

