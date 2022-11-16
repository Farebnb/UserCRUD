package com.cognizant.usercrud.services;

import com.cognizant.usercrud.models.User;
import com.cognizant.usercrud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo ur;

    public UserService(UserRepo ur) {
        this.ur = ur;
    }

    //CREATE
    public User register(String firstname, String lastname, String username, String email, String password){
        User registerUser = new User(0, firstname, lastname, username, email, password, 0);
        return ur.save(registerUser);
    }

    //READ
    public User getByUsername(String username){
        return ur.findByUsername(username);
    }

    public User getById(int id) {
        return ur.findById(id);
    }

    public User getByListingId(int listingId){
        return ur.findByListingId(listingId);
    }

    public List<User> getAll() {
        return ur.findAll();
    }

    //UPDATE
    public User update(User u) {
        return ur.saveAndFlush(u);
    }

    //DELETE
    public void delete(int id) {
        ur.deleteById(id);
    }

}
