package com.xcomms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;



public class DataManager {



    //global variables
    private Client[] registeredClients = getClients();
    // private Client[] registeredClients = null;
    private final String databaseURL = "jdbc:postgresql://pg-24a4a67c-springbootapi.h.aivencloud.com:10193/defaultdb?sslmode=require";
    private final String username = "avnadmin";
    private final String password = "AVNS_1E5FZpr-OIOurXHQohD";
    private Room[] rooms = null;


    public DataManager(){
        rooms = getRooms();
    }








    //Client Management



    public boolean hasUsername(String username){
        for(int i = 0; i < registeredClients.length; i++){
            if(registeredClients[i].getUser().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean matchesClient(String username, String password){
        for(int i = 0; i < registeredClients.length; i++){
            if(registeredClients[i].getUser().equals(username) && registeredClients[i].getPass().equals(password)){
                return true;
            }
        }
        return false;
    }




    public void registerNewUser(Client newClient){
        String sql = "INSERT INTO Users (Username, Password) VALUES('" + newClient.getUser() + "', '" + newClient.getPass() + "')";
        executeSQL(sql);
        registeredClients = getClients();
    }


    
    private Client[] getClients(){
        String sql = "SELECT * FROM Users";

        Client[] clients = new Client[getTableLength("Users")];

        try(Connection conn = DriverManager.getConnection(databaseURL, username, password)){
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);
            int i = 0;
            while(rs.next()){
                Client client = new Client();
                client.setUsername(rs.getString(1));
                client.setPassword(rs.getString(2));
                clients[i] = client;
                System.out.println(client.toString());
                i++;
            }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        // Client[] output = null;
        // clients.toArray(output);

        return clients;
    }




    //Room Management
    public void addRoom(int id, boolean isPrivate, String password, String name, int capacity){
        String sql = ""
            + "INSERT INTO Rooms(id, private, password, name)"
            + "VALUES("
            + id + ", "
            + isPrivate + ", '"
            + password + "', "
            + username + "', "
            + capacity + ";";

        executeSQL(sql);
    }

    public void deleteRoom(int id){
        String sql = ""
            + "DELETE FROM Rooms WHERE "
            + "id=" + id;
        executeSQL(sql);
    }

    public Room[] getRooms(){

        String sql = "SELECT * FROM Rooms";
        Room[] output = new Room[getTableLength("Rooms")];
        int index = 0;

        try(Connection conn = DriverManager.getConnection(databaseURL, username, password)){
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(sql);

            while(rs.next()){
                output[index] = new Room(rs.getInt(1), rs.getBoolean(2), rs.getString(3), rs.getString(4), rs.getInt(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return output;

    }

    public Room[] getGlobalRooms(){
        return rooms;
    }

    public boolean containsId(int id){
        int left = 0;
        int right = rooms.length -1;

        while(left <= right){
            int mid = left + (right - left) / 2;

            if(rooms[mid].id == id){
                return true;
            }
            if (rooms[mid].id < id) {
                left = mid + 1;
            } 
            // If target is smaller, ignore the right half
            else {
                right = mid - 1;
            }
        }

        return false;


    }


    












    //SQL Connection/execution functions



    public void executeSQL(String sql){
        try(Connection conn = DriverManager.getConnection(databaseURL, username, password)){
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
            // ResultSet rs = stmnt.executeQuery(sql);
            // while(rs.next()){
            //     System.out.println(rs.getString(1));
            //     System.out.println(rs.getString(2));
            // }
            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }





    public String[] querySQL(String sql){
        String[] output = new String[3];

        try(Connection conn = DriverManager.getConnection(databaseURL, username, password)){
            Statement stmnt = conn.createStatement();
            // stmnt.execute(sql);
            ResultSet rs = stmnt.executeQuery(sql);
            while(rs.next()){
                output[0] = rs.getString(1);
                output[1] = rs.getString(2);
                output[2] = rs.getString(3);
            }
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return output;
    }



    public int getTableLength(String table){
        try(Connection conn = DriverManager.getConnection(databaseURL, username, password)){
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT COUNT(*) FROM " + table);
            rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return 0;
    }
}
