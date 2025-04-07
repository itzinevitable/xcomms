package com.xcomms;


import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Random;

import org.json.*;


import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
public class Controller {

    //global variables
    final DataManager dm = new DataManager();
    private final Moderation moderator = new Moderation();
    private final SimpMessagingTemplate messagingTemplate;
    
    public Controller(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }




    //testing

    @GetMapping(value = "/testing")
    @ResponseStatus(HttpStatus.OK)
    public String testing(){
        return "Render deployed correcctly.";
    }

    //login stuff


    @PostMapping(value = "/login/signin", consumes = "application/json; UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public String signIn(@RequestBody Client client){
        System.out.println("recieved");
        if(dm.matchesClient(client.getUser(), client.getPass())){
            return "Welcome!";
        }
        return "Wrong Password!";
    }
    
    @PostMapping(value = "/login/register", consumes = "application/json;UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public String logininfo(@RequestBody Client client){
        if(dm.hasUsername(client.getUser())){
            return "User already existent";
        }
        dm.registerNewUser(client);
        System.out.println("New Client!");
        return "New Client!";
    }





    //room management




    @PostMapping(value = "/room/create", consumes = "application/json; UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public int createRoom(@RequestBody String json){
        JSONObject payload = new JSONObject(json);
        payload.put("id", generateId());
        
        dm.addRoom(payload.getInt("id"), payload.getBoolean("private"), payload.getString("password"), payload.getString("name"), payload.getInt("capacity"));

        dm.updateRooms();

        return payload.getInt("id");
    }   

    @PostMapping(value = "room/delete", consumes = "application/json; UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRoom(@RequestBody String json){
        JSONObject payload = new JSONObject(json);
        int id = payload.getInt("id");
        dm.deleteRoom(id);
        dm.updateRooms();
        return "Room Deleted!";
    }

    private int generateId(){
        Random rand = new Random();
        int id = 1000 + rand.nextInt(9000);
        id = 4975;

        while(dm.containsId(id) == true){
            id = 1000 + rand.nextInt(9000);
        }

        return id; 
    }

    @Scheduled(fixedRate = 1000)
    @SendTo("/topic/rooms")
    public void updateRooms(){
        JSONObject json = new JSONObject();
        JSONArray rooms = new JSONArray();

        for(int i = 0; i < dm.getRooms().length; i++){
            JSONObject room = new JSONObject();
            room.put("id", dm.getRooms()[i].id);
            room.put("private", dm.getRooms()[i].isPrivate);
            room.put("name", dm.getRooms()[i].name);
            room.put("password", dm.getRooms()[i].password);
            room.put("capacity", dm.getRooms()[i].capacity);
            rooms.put(room);
        }

        json.put("rooms", rooms);
        messagingTemplate.convertAndSend("/topic/rooms", json.toString());

    }






    //Message management


    /*
    
    {
        username: example,
        message: hihihihi,
        roomId: 1234,
        timestamp: 1234567890,
        capacity: 10,
    }   



     */








    @MessageMapping(value="/chat/send")
    @SendTo("/topic/public")
    public String sendMessage(@Payload String payload){
        
        JSONObject json = new JSONObject(payload);
        
        if(moderator.isAppropriate(json.getString("message"))){
            return payload;
        }

        json.put("message", "This message has been deleted due to inappropriate content.");

        return json.toString();
    }








   
}

/*
 
JSON structure for rooms

{
    id=automaticaly generated,
    private=false,
    password="bestpasswordtrust",
    name="yapping abt life..."
}





 */
 


















