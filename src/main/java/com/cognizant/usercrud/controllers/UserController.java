package com.cognizant.usercrud.controllers;

import com.cognizant.usercrud.models.User;
import com.cognizant.usercrud.repo.UserRepo;
import com.cognizant.usercrud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepo ur;

    @Autowired
    private UserService us;

    @Autowired
    private UserController(UserRepo ur, UserService us){
        this.ur = ur;
        this.us = us;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody LinkedHashMap<String, String> body){
        return new ResponseEntity<>(us.register(body.get("firstname"), body.get("lastname"), body.get("username"), body.get("email"), body.get("password")), HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    public ResponseEntity<User> getById(@RequestParam int id) {
        return new ResponseEntity<>(us.getById(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/username")
    public ResponseEntity<User> getByUsername(@RequestParam String username){
        return new ResponseEntity<>(us.getByUsername(username), HttpStatus.ACCEPTED);
    }

    @GetMapping("/listingId")
    public ResponseEntity<User> getByListingId(@RequestParam int listingId){
        return new ResponseEntity<>(us.getByListingId(listingId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(us.getAll(), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User u){
        return new ResponseEntity<>(us.update(u), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(@RequestParam int id){
        User u = us.getById(id);
        if(u == null) {
            return ResponseEntity.notFound().build();
        }
        us.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
