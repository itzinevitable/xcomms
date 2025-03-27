package com.xcomms;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        // SpringApplication app = new SpringApplication(Main.class);
        // app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        // SpringApplication.run(Main.class, args);

        
        // moderator.isOkay("");
        DataManager dm = new DataManager();

        // Room[] output = dm.getRooms();

        // for(int i = 0; i < output.length; i++){
        //     System.out.println(output[i].toString());
        // }
        // dm.executeSQL("CREATE TABLE Rooms(id INTEGER, private BOOL, password varchar(255), name varchar(255))");
        dm.executeSQL("CREATE TABLE Users(Username VARCHAR(50), Password VARCHAR(255));");
        // dm.runSQL("INSERT INTO Messages(Content) VALUES ('hi')");
        // dm.runSQL("DELETE FROM Users WHERE Username='itz_inevitable'");
        // System.out.println(dm.getTableLength(""));
        // dm.runSQL("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE'");
        // dm.runSQL("SELECT * FROM Users");
        // dm.runSQL("INSERT INTO Users(Username, Password) VALUES('itz_inevitable', 'skb')");
        // dm.runSQL("SELECT COUNT(*) FROM Users");
        // dm.runSQL("CREATE TABLE Messages(Messenger VARCHAR(50), Content VARCHAR(255), Reciever VARCHAR(50))");


    }
}