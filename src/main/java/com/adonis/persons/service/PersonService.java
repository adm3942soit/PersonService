package com.adonis.persons.service;

import com.adonis.persons.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by oksdud on 29.03.2017.
 */
@Component
public class PersonService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Person> findAll() {
        return jdbcTemplate.query(
                "SELECT FIRST_NAME, LAST_NAME, EMAIL, PICTURE, DATE_OF_BIRTH FROM persons",
                (rs, rowNum) -> new Person(
                        rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PICTURE"),
                        rs.getDate("DATE_OF_BIRTH")
                ));
    }

    public void updateFull(Person customer) {
        jdbcTemplate.update(
                "UPDATE persons SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, LOGIN=?, " +
                        "PICTURE=?, NOTES=?, DATE_OF_BIRTH=?, PASSWORD=? WHERE ID=?",
                customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                customer.getLogin(), customer.getPicture(), customer.getNotes(),
                customer.getDateOfBirth(),customer.getPassword(), customer.getId());
    }
    public void update(Person customer) {
        jdbcTemplate.update(
                "UPDATE persons SET FIRST_NAME=?, LAST_NAME=?, EMAIL=? " +
                        "WHERE ID=?",
                customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                customer.getId());
    }

    public void save(Person customer){
//        jdbcTemplate.query(
    }

    public void delete(Person customer){
        jdbcTemplate.execute("DELETE FROM persons WHERE ID="+ customer.getId());
    }

}
