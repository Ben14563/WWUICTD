package com.example;

/**
 * Created by kasingj on 10/19/16.
 */

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import netscape.javascript.JSObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.codehaus.jackson.*;
import javax.json.*;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



/**
 * Created by kasingj on 10/5/16.
 */
@RequestMapping("/API")
@RestController

public class API {

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String index(){

        return "Quit Mates API";
    }
    /*
    *
    * Get user with id
    * */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable("id") String id ){
        return id;
    }

    /*
    *
    * Add user to database
    * */
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public @ResponseBody
    String addUser(@RequestParam(value = "name", required = true)String name,
                   @RequestParam(value = "email", required = true)String email,
                   @RequestParam(value = "cigs_per_day",required=true)String cigs_per_day,
                   @RequestParam(value = "price_per_pack",required=true)String price_per_pack
    ){


        return name + email + cigs_per_day + price_per_pack;
    }

/*
* Update given field for for given day by one
* */

    @RequestMapping(value = "/user/{id}/{day}/{field}", method = RequestMethod.GET)
    public String getUser(@PathVariable("id") String id,
                          @PathVariable("day") String day,
                          @PathVariable("field") String field){
        return id + day+ field;
    }

    /*
*
* Get feed for user
* */
    @RequestMapping(value = "/user/{id}/feed", method = RequestMethod.GET)
    public String getFeedForUser(@PathVariable("id") String id){
        return "feed for " +id;
    }

    /*
*
* Get info for day for user
* */

    @RequestMapping(value = "/user/{id}/day", method = RequestMethod.GET)
    public String getInfoForDay(@PathVariable("id") String id){
        return "feed for " +id;
    }


/*
* add a day for the current date with cravings, cigs smoked, and cravings_resisted equals 0
*
* */


    @RequestMapping(value = "/day/add/{id}", method = RequestMethod.POST)
    public @ResponseBody String addDay(@PathVariable("id") String id){

        return "Adding user for user: " + id;
    }
/*
*
* add buddy to user
* */

    @RequestMapping(value = "/user/{id}/buddy/add/{email}", method = RequestMethod.GET)
    public String addBuddyToUser(@PathVariable("id") String id,
                                 @PathVariable("email") String email){
        return "adding buddy to user email must exist in database " + id + " " + email;
    }


}

