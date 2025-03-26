package com.xcomms;

public class Room {
    public int room = 0;
    public boolean isPrivate;
    public String name;
    public String password;
    
    public Room(int room, boolean isPrivate, String name, String password){
        this.room = room;
        this.isPrivate = isPrivate;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString(){

        String output = ""
            + "Room: " + room
            + "\n isPrivate: " + isPrivate
            + "\n Name: " + name
            + "\n Password: " + password;

        return output;

    }


}
