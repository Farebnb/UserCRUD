package com.cognizant.usercrud.repo;

import com.cognizant.usercrud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findById(int id);
    User findByListingId(int listingId);

}
