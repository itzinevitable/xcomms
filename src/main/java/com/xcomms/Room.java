package com.xcomms;

public class Room {
    public int id = 0;
    public boolean isPrivate;
    public String name;
    public String password;
    public int capacity;
    
    public Room(int room, boolean isPrivate, String name, String password, int capacity){
        this.id = room;
        this.isPrivate = isPrivate;
        this.name = name;
        this.password = password;
        this.capacity = capacity;
    }

    @Override
    public String toString(){

        String output = ""
            + "Room: " + id
            + "\n isPrivate: " + isPrivate
            + "\n Name: " + name
            + "\n Password: " + password;

        return output;

    }


}
