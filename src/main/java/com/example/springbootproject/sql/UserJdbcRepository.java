package com.example.springbootproject.sql;

import com.example.springbootproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertUser(User user) throws Exception {
        try {
            return jdbcTemplate.update("INSERT INTO TBL_USERS (first_name, last_name) " + "VALUES (?, ?)",
                    new Object[]{user.getFirstName(), user.getLastName()});
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public User findUserById(long id) throws Exception {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM TBL_USERS WHERE id = ?", new Object[] {id},
                new BeanPropertyRowMapper<User>(User.class));
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public List<User> findAllUsers() throws Exception {
        try{
            return jdbcTemplate.query("SELECT * FROM TBL_USERS",
                new UserRowMapper());
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public int deleteById(long id) throws Exception {
        try{
            return jdbcTemplate.update("DELETE FROM TBL_USERS WHERE id = ?", new Object[] {id});
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public User findByFullName(User user) throws Exception {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM TBL_USERS WHERE first_name = ? AND last_name = ?",
                    new Object[]{user.getFirstName(), user.getLastName()},
                    new BeanPropertyRowMapper<User>(User.class));
        }catch (EmptyResultDataAccessException e) {
            return null;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
