package org.uob.a1;

public class Room {
    private String name;
    private String description;
    private char symbol;
    private Position position;
    private boolean explored;


    public Room(String name, String description, char symbol, Position position){
        this.name=name;
        this.description=description;
        this.symbol=symbol;
        this.position=position;
        this.explored = false;
    }

    
    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public char getSymbol(){
        return symbol;
    }

    public Position getPosition(){
        return position;
    }


    public boolean isExplored(){
        return explored;
    }

    public void markAsExplored(){
        this.explored = true;
    }
}