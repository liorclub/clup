package com.mkyong.rest;
 
import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.RequestWrapper;

import java.awt.*;
import java.util.*;
import java.util.List;


@Path("/")
public class HelloWorldService {
 
	@GET
	@Path("/insert/{name}")
	public Response InsertUser(@PathParam("name") String name) {


        String response;
		String output = InsertUserToDb(name);
		return Response.status(200).entity(output).build();
 
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/auth")
    public Response AuthUser(String user) {
        String output;

        Gson gson = new Gson();

        AuthUser authUser = gson.fromJson(user,AuthUser.class);

        boolean auth = CheckDB(authUser);

        String result = "" + auth;
        return Response.status(200).entity(result).build();
    }

    private boolean CheckDB(AuthUser user) {
        boolean auth = false;
        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            DB db = mongoClient.getDB("test");

            DBCollection collection = db.getCollection("users");



            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("name", user.getName());
            DBCursor cursor = collection.find(whereQuery);

            if (cursor != null && cursor.length() >0 ) {
                DBObject obj = cursor.toArray().get(0);
                String password = obj.get("password").toString();
                if (password.equals(user.getPassword())) { auth = true; }
            }




            return auth;



        }
        catch (Exception ex) {

            return auth;
        }

    }
    @GET
    @Path("/display/{name}")
    public Response DisplyByName(@PathParam("name") String name) {

        String response;
        String output = displayUserByName(name);
        return Response.status(200).entity(output).build();

    }

    public String displayUserByName(String name) {
        String result = "";
        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            DB db = mongoClient.getDB("test");

            DBCollection collection = db.getCollection("users");

            DBObject whereQuery = new BasicDBObject();
            whereQuery.put("email", name);
            DBCursor cursor = collection.find(whereQuery);

            while(cursor.hasNext()) {
               result += cursor.next();
            }


        }
        catch (Exception ex) {

            ex.printStackTrace();
        }

        return result;

    }

    public String InsertUserToDb(String name) {
        String result = "";
        try {
            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

            DB db = mongoClient.getDB("test");

            User user = new User(hashCode(),"Lior","1234");
            User user2 = new User(hashCode(),"Test","7893");
            User user3 = new User(hashCode(),"new","elkwnfkle");
            List<User> users = new ArrayList<User>();
            users.add(user);
            users.add(user2);
            users.add(user3);

            Gson gson = new Gson();

            DBCollection table = db.getCollection("users");

            for (User obj : users) {
                DBObject document = (DBObject) JSON.parse(gson.toJson(user));
                table.insert(document);

            }

        }
        catch (Exception ex) {

            ex.printStackTrace();
        }

        return result;

    }
}