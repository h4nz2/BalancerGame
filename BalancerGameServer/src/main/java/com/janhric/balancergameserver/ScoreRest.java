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
import java.util.ArrayList;
import java.util.List;


@Path("/score")
public class ScoreRest {
    @GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Scores> getTrackInJSON() {
            ArrayList<Scores> scores = new ArrayList<Scores>();
            
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:6000/my_database", "root", "1234");            
                Statement statement = connection.createStatement();            
                ResultSet resultSet = statement.executeQuery("select * from scores"); 
                while(resultSet.next()){
                    Scores score = new Scores();
                    score.setId(resultSet.getInt("id"));
                    score.setName(resultSet.getString("name"));
                    score.setScore(resultSet.getInt("score"));
                    score.setDate(resultSet.getString("date"));
                    scores.add(score);
                }
            /*while(resultSet.next()){
                System.out.println("ID: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("name") + ", Score: " + resultSet.getInt("score") + ", Date: " + resultSet.getString("date"));
            }*/
            
        } catch (Exception e) {
            e.printStackTrace();
        }
            
            return scores;
	}
}
