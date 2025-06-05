package org.uob.a1;

import java.util.Scanner; 

public class Game {
    private Map map;
    private Score score;
    private Inventory inventory;
    private Room currentRoom;

    //Room decraration
    private Room observationDeck;
    private Room controlRoom;
    private Room storageBay;
    private Room crewQuarters;
    private Room engineeringBay;
    private Room medicalBay;
    private Room greenhouse;
    private Room hangar;
    private Room armory;
    private Room escapePodRoom;

    //Constuctor
    public Game(){
        this.map = new Map(5,5);
        this.inventory = new Inventory();
        this.score= new Score(10);
    }
    
    // room initialisation
    private void roomSetup(){
        observationDeck = new Room("Observation Deck", "There is a large glass window and you see a something sparkle on the floor you can look at it. There is a room to your east", 'O', new Position(0, 0));
        controlRoom = new Room("Control Room", "Displays data on all systems but all the displays are broken. There is a locker you can search which still looks intact. There is a room to your east/west.", 'C', new Position(1, 0));
        storageBay = new Room("Storage Bay", "Filled with various tools and supplies. There is also a machine in this room. There is a room to your south/west", 'S', new Position(2, 0));
        engineeringBay = new Room("Engineering Bay", "There are broken systems and wires that are exposed here. A tool-kit in great condtion is on a table. There is a room to your north/south", 'E', new Position(2, 1));
        medicalBay = new Room("Medical Bay", "The room is dimly lit and the lights are flickering and the room is in ruin. There is a room to your north/east .", 'M', new Position(2,2));
        greenhouse = new Room("Greenhouse", "Plants have overrun this area. The thick vines of the plants spead all over the room. There is a room to your east/west/south. ", 'G', new Position(3, 2));
        crewQuarters = new Room("Crew Quarters", "There are logs displayed here. You can view entries written by crew members. There is also a vault with a thick glass window but you need to take a closer look at the vault to see inside. There is a room to your south.", 'Q', new Position(4, 1));
        hangar = new Room("Hangar", "The room was always heavily projected but after the engine failure the door is wide open and a crate looks like its open. There is a room to your north/east", 'H', new Position(3, 3));
        armory = new Room("Armory", "Damaged equipment is scattered and the room look completely destroyed. There is a room to your north/east", 'A', new Position(4, 3));
        escapePodRoom = new Room("Escape Pod Room", "There is a damaged escape pod which needs fuel, space-suit, oxygen-mask and tool-kit to escape. There is a room to your west/south/north", 'X', new Position(4, 2));

        //Sets up the starting room
        this.currentRoom = observationDeck;
        
        //Makes the room visible on the map
        exploreRoom(currentRoom);
        
    }
    
    // Main Game Loop. Allows inputs from the user
    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to my Game");
        
        //Sets up rooms 
        roomSetup();
        System.out.println("You are in the " + currentRoom.getName());
        
        //Game Loop
        boolean gameOver = false;
        while(!gameOver){
            String input = scanner.nextLine().toLowerCase();
            gameOver = handleInput(input);
            }
        
        System.out.println("Thanks for playing");
    }
    // Handles the users inputs
    private boolean handleInput(String input){
        // If 2 words are entered by the user it is split into 2 seperate variable here
        String[] split = input.split(" ",2);
        String action = split[0];
        // Simplified if statement Ternary Operator. How it works:  condition ? If True : If False;
        String arg= split.length > 1 ? split[1] : "";

        switch (action){
            case "use":
                use(arg);
                break;
                
            case "enter":
                escape(arg);
                break;
                    
            case "grab":
                grab(arg);
                break;

            case "search":
                search(arg);
                break;
                
            case "drop":
                drop(arg);
                break;
                
            case "move":
                move(arg);
                break;
                
            case "look":
                look(arg);
                break;

            case "inventory":
                System.out.println("Inventory: "+ inventory.displayInventory());
                break;

            case "score":
                System.out.println("Score: "+score.getScore());
                break;

            case "map":
                System.out.println(map.display());
                break;

            case "help":
                displayHelp();
                break;

            case "quit":
                return true;

            default:
                System.out.println("Invalid Command");
                
        }
        return false;
    }

    // Additional Puzzle Solving Mechanic use <item> removes item from inventory and gives you something new to finish the game
    private void use(String object){
        if(object.equalsIgnoreCase("coin") && currentRoom.getName().equals("Storage Bay") && inventory.hasItem("coin")!= -1){
            inventory.removeItem("coin");
            System.out.println("Your coin was inserted into the machine");
            inventory.addItem("oxygen-mask");
            score.solvePuzzle();
            System.out.println("The machine gave you an oxygen-mask");
        }
        else if(object.equalsIgnoreCase("keycard") && currentRoom.getName().equals("Crew Quarters") && inventory.hasItem("keycard")!= -1){
            inventory.removeItem("keycard");
            System.out.println("You used your keycard at the vault and you were unable to take the keycard back");
            inventory.addItem("space-suit");
            score.solvePuzzle();
            System.out.println("You open the vault and take the space-suit.");
        }
        else{
            System.out.println("You do not have this item or you cannot use it here");
        }
    }

    //Additonal Puzzle solving mechanic search <object>
    private void search(String object){
        //search locker in control room
        if(currentRoom.getName().equals("Control Room") && object.equalsIgnoreCase("locker")){
            System.out.println("You search the locker and find fuel");
            inventory.addItem("fuel");
            score.solvePuzzle();
            System.out.println("The fuel was added to your inventory");
        }
        //search crate in hanger
        else if(currentRoom.getName().equals("Hangar") && object.equalsIgnoreCase("crate")){
            System.out.println("You search the crate and find a keycard");
            inventory.addItem("keycard");
            score.solvePuzzle();
            System.out.println("The keycard was added to your inventory");
        }
        else{
            System.out.println("You can't search that");
        }
    }
    
    // Final Puzzle Solving Mechanic requires fuel, space-suit, oxygen-mask in your inventory to finish the game
    private void escape(String object){
        if(object.equalsIgnoreCase("pod") && currentRoom.getName().equals("Escape Pod Room")){
            if(inventory.hasItem("fuel") != -1 && inventory.hasItem("space-suit") != -1 && inventory.hasItem("oxygen-mask") != -1 && inventory.hasItem("tool-kit") != -1){
                score.solvePuzzle();
                System.out.println("Your escape pod launches and see your destroyed space station below you as you fly away in your pod");
                System.out.println("Your final score is: "+score.getScore());
            }
            else{
                System.out.println("You do not have the required items to escape");
            }
        }
    }

    // Pick up items which are availible in certain rooms
    private void grab(String object){
        if(currentRoom.getName().equals("Observation Deck") && object.equalsIgnoreCase("coin")){
            inventory.addItem("coin");
            System.out.println("The coin was added to your inventory");
        }
        else if(currentRoom.getName().equals("Engineering Bay") && object.equalsIgnoreCase("tool-kit")){
            inventory.addItem("tool-kit");
            System.out.println("The tool-kit was added to your inventory");
        }
        else{
            System.out.println("That item doesn't exist");
        }
    }
    
    // Drop Items from Inventory 
    private void drop(String object){
        if(inventory.hasItem(object)!= -1){
            inventory.removeItem(object);
            System.out.println("The item was removed from your inventory");
        }
    }

    // Look <item> & Look <feature>
    private void look(String featureOrItem){
        // Returns Description
        if(featureOrItem.isEmpty()){
            System.out.println(currentRoom.getDescription());
        }
        // Features
        else if (currentRoom.getName().equals("Crew Quarters") && featureOrItem.equalsIgnoreCase("vault")){
            System.out.println("You look closely at the vault and see a space-suit in it and the vault requires a keycard to be opened");
        } 
        else if (currentRoom.getName().equals("Storage Bay") && featureOrItem.equalsIgnoreCase("machine")){
            System.out.println("The machine has a coin insert if you have a coin you can insert it into the machine");
        } 
        else if (currentRoom.getName().equals("Observation Deck") && featureOrItem.equalsIgnoreCase("window")){
            System.out.println("You can see the stars gleaming in the distance");
        }
        else if (currentRoom.getName().equals("Observation Deck") && featureOrItem.equalsIgnoreCase("sparkle")){
            System.out.println("You see a coin on the floor");
        }
        else if (currentRoom.getName().equals("Crew Quarters") && featureOrItem.equalsIgnoreCase("logs")){
            System.out.println("4:33 There was a loud crash");            
            System.out.println("4:36 The engines failed prepare to launch escape pods");
            System.out.println("4:49 Escape pod A2 & B9 launched");
        }
        //Items
        else if(inventory.hasItem(featureOrItem)!= -1 && featureOrItem.equalsIgnoreCase("coin")){
            System.out.println("The coin has a imprint of a 4 leafed clover");
        }
        else if(inventory.hasItem(featureOrItem)!= -1 && featureOrItem.equalsIgnoreCase("keycard")){
            System.out.println("The keycard has an inscription of the room Crew Quarters");
        }
        else if(inventory.hasItem(featureOrItem)!= -1 && featureOrItem.equalsIgnoreCase("space-suit")){
            System.out.println("The space-suit used for protection from the harsh environment of outer space");
        }
        else if(inventory.hasItem(featureOrItem)!= -1 && featureOrItem.equalsIgnoreCase("oxygen-mask")){
            System.out.println("The oxygen-mask is required to survive in space");
        }
        else if(inventory.hasItem(featureOrItem)!= -1 && featureOrItem.equalsIgnoreCase("fuel")){
            System.out.println("This fuel is high grade looks like it can be used for a escape pod");
        }
        else if(inventory.hasItem(featureOrItem)!= -1 && featureOrItem.equalsIgnoreCase("tool-kit")){
            System.out.println("The tool-kit is in great conditon.");
        }
        else{
            System.out.println("You can't look at that item or feature");
        }
    }

    
    private Room getRoomByPos(Position position) {
        //System.out.println("Inside getRoomByPos"+position);
        if (position.equals(observationDeck.getPosition())) return observationDeck;
        if (position.equals(controlRoom.getPosition())) return controlRoom;
        if (position.equals(storageBay.getPosition())) return storageBay;
        if (position.equals(crewQuarters.getPosition())) return crewQuarters;
        if (position.equals(engineeringBay.getPosition())) return engineeringBay;
        if (position.equals(medicalBay.getPosition())) return medicalBay;
        if (position.equals(greenhouse.getPosition())) return greenhouse;
        if (position.equals(hangar.getPosition())) return hangar;
        if (position.equals(armory.getPosition())) return armory;
        if (position.equals(escapePodRoom.getPosition())) return escapePodRoom;
        
        return null;
    }
    
    private void move(String direction){
        //Debuging
        //System.out.println("you are in: " + currentRoom.getName());
        //System.out.println("Trying to move in direction: " + direction);
        //System.out.println("you are in: " + map.display());
        Position currentPosition = null;
        Position nextPosition = getNextPosition(currentRoom.getPosition(), direction);
            //System.out.println("Next next position: " + nextPosition);
            if (nextPosition != null && map.isValidPosition(nextPosition)) {
                //System.out.println("Next inside if next position: " + nextPosition);
                currentPosition = nextPosition;
                //System.out.println("Next current position: " + currentPosition);
                if(map.isExplored(currentPosition)){
                    //System.out.println("Next inside map next position: " + currentPosition);
                    if(getRoomByPos(currentPosition)!= null){
                       exploreRoom(getRoomByPos(currentPosition));

                    }
                }
            }
        

        
        // move into the next room given that it is a valid room
        Room nextRoom = getRoomByPos(nextPosition);
        if (nextRoom != null){
            //System.out.println("Inside nextRoom");
            //System.out.println(nextRoom.getName());
            currentRoom= nextRoom;
            exploreRoom(currentRoom);
            System.out.println("You have move into the "+ currentRoom.getName());
            }
        else {
            System.out.println("You cannot move in this direction.");  
            }
        }

    // gets the new positon when you move
    public Position getNextPosition(Position current, String direction) {
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
  
    private void exploreRoom(Room room){
        //System.out.println("Inside Explore Room"+room.isExplored());
        if(!room.isExplored()){
            room.markAsExplored();
            map.placeRoom(room.getPosition(),room.getSymbol());
            score.visitRoom();
            //System.out.println("Room " + room.getName() + " explored at position: " + room.getPosition());
        }else{
            //System.out.println("Inside Else ");
            
        }

    }
    

    private void displayHelp(){
        System.out.println("Available commands:");
        System.out.println("move <direction> - Move in a given direction (north, south, east, west)");
        System.out.println("look - Look around the room");
        System.out.println("look <feature> - Look at a specific feature in the room");
        System.out.println("look <item> - Look at a specific item in your inventory");
        System.out.println("grab <item> - Add item to your inventory");
        System.out.println("use <item>- Use an item you have in your inventory");
        System.out.println("search <object> - Search for items in objects like creates and lockers");
        System.out.println("enter pod - To finish the game when you have gathered all the items");
        System.out.println("inventory - Show your current inventory");
        System.out.println("score - Show your current score");
        System.out.println("map - Display the map");
        System.out.println("help - to display this image");
        System.out.println("quit - Quit the game");
    }
    
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
    
}
