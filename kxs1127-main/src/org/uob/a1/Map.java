package org.uob.a1;

public class Map {
    private char[][] mapArr;
    private boolean[][] explored = new boolean[5][5];
    private int height;
    private int width;
    final private char EMPTY='.';

    public Map(int height,int width){
        this.height=height;
        this.width=width;
        mapArr= new char[height][width];
        //explored[0][0] = false;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mapArr[i][j] = EMPTY;
                explored[i][j]=false;
            }
        }
    }
    
    public boolean isValidPosition(Position pos) {
        return pos.x >= 0 && pos.x < width && pos.y >= 0 && pos.y < height;
    }
    
    public void placeRoom(Position pos, char symbol){
        if(isValidPosition(pos)){
            mapArr[pos.y][pos.x]=symbol;
            explored[pos.y][pos.x]=true;
        }
        else{
            System.out.println("Invalid room position.");
        }
        
    }
    
    public boolean isExplored(Position pos) {
        if (isValidPosition(pos)) {
            return true;
        }else
          return false;
    }

    public String display() {
        StringBuilder mapDisplay = new StringBuilder();
        for (int i =0; i<height; i++) {
            for (int j = 0;j < width; j++) {
                if(explored[i][j]){
                    mapDisplay.append(mapArr[i][j]);
                }
                else{
                    mapDisplay.append(EMPTY);
                }
            }
            mapDisplay.append("\n");
        }
        return mapDisplay.toString();
    }

    public Position getAdjacentPosition(Position current, String direction) {
        switch (direction) {
            case "north":
                return new Position(current.x, current.y - 1);
            case "south":
                return new Position(current.x, current.y + 1);
            case "east":
                return new Position(current.x + 1, current.y);
            case "west":
                return new Position(current.x - 1, current.y);
            default:
                return null;
        }
    }
}