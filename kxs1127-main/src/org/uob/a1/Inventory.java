package org.uob.a1;

public class Inventory {
    private static final int MAX_ITEMS = 10;
    private String[] items;
    private int itemCount; 

    public Inventory(){
        itemCount = 0;
        items = new String[MAX_ITEMS];
    }

    public void addItem(String item){
        if (itemCount < MAX_ITEMS){
            items[itemCount]=item;
            itemCount++;
        }
        else{
            System.out.println("Inventory is full remove an item to add" + item +"to the inventory." );
        }
    }

    public int hasItem(String item){
        for(int i = 0; i<itemCount;i++){
            if (items[i].equals(item)){
                return i;
            }
        }
        return -1;
    }
    
   public void removeItem(String item){
        int itemPos = hasItem(item);
        if (itemPos != -1){
            for(int i = itemPos; i<itemCount-1;i++){
               items[i]=items[i+1];
            }
            items[itemCount - 1]=null;
            itemCount--;
       }
       else{
           System.out.println(item+" is not in your inventory");
       }
   }



    public String displayInventory(){
        StringBuilder listInventory = new StringBuilder();
        for(int i = 0; i< itemCount;i++){
            listInventory.append(items[i]).append(" ");
        }
        return listInventory.toString();
    }
}