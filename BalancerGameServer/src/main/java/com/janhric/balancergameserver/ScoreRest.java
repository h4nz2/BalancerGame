/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.janhric.balancergameserver;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.QueryParam;

@Path("/scores")
public class ScoreRest {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Scores> getScores() {
        ArrayList<Scores> scores = new ArrayList<Scores>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:6000/my_database", USERNAME, PASSWORD);            
            Statement statement = connection.createStatement();            
            ResultSet resultSet = statement.executeQuery("select * from scores order by score desc, id asc"); 
            while(resultSet.next()){
                Scores score = new Scores();
                score.setId(resultSet.getInt("id"));
                score.setName(resultSet.getString("name"));
                score.setScore(resultSet.getInt("score"));
                score.setDate(resultSet.getString("date"));
                score.setLatitude(resultSet.getFloat("latitude"));
                score.setLongitude(resultSet.getFloat("longitude"));
                scores.add(score);
            }      
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scores;
    }
    
    @GET
    @Path("/player")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Scores> getScore(@QueryParam("name") String name) {
        ArrayList<Scores> scores = new ArrayList<Scores>();
        
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:6000/my_database", USERNAME, PASSWORD);            
            Statement statement = connection.createStatement();            
            ResultSet resultSet = statement.executeQuery("select * from scores where name = " + name + " order by score desc");
            while(resultSet.next()){
                Scores score = new Scores();
                score.setId(resultSet.getInt("id"));
                score.setName(resultSet.getString("name"));
                score.setScore(resultSet.getInt("score"));
                score.setDate(resultSet.getString("date"));
                score.setLatitude(resultSet.getFloat("latitude"));
                score.setLongitude(resultSet.getFloat("longitude"));
                scores.add(score);
            }             
        } catch (Exception e){
            e.printStackTrace();
        }
        return scores;
    }
    
    @POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postScore(Scores score) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:6000/my_database", USERNAME, PASSWORD); 
            String query = "insert into scores(name, score, date, latitude, longitude) values(?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, score.getName());
            preparedStmt.setInt(2, score.getScore());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            java.util.Date date = new java.util.Date();
            preparedStmt.setString(3, dateFormat.format(date));
            preparedStmt.setFloat(4, score.getLatitude());
            preparedStmt.setFloat(5, score.getLongitude());
            preparedStmt.execute();
            
            score.setDate(dateFormat.format(date));
            return Response.status(201).entity(score).build();
        } catch (Exception e){
            return Response.status(500).build();
        }
    } 
}
