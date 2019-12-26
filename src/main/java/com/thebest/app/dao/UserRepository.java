package com.thebest.app.dao;

import com.thebest.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u where u.id = ?1 and u.deleted = 0")
    User loadById(Long id);

    @Query("SELECT u FROM User u where u.email = ?1")
    User loadByEmail(String email);
}
