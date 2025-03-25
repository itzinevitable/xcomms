package com.xcomms;


// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import org.json.*;


import org.springframework.http.HttpStatus;

// @CrossOrigin(origins="http://localhost:5500")
@RestController
@RequestMapping("/api")
public class Controller {

    //global variables
    final DataManager dm = new DataManager();

    

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
    public void createRoom(@RequestBody String json){
        JSONObject payload = new JSONObject(json);
        dm.addRoom(payload.getInt("id"), payload.getBoolean("private"), payload.getString("password"), payload.getString("name"));

        
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
 


















