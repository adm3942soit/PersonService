package com.adonis.persons.service;

import com.adonis.persons.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        String sql = "SELECT * FROM persons";
//        return jdbcTemplate.query(
//                "SELECT FIRST_NAME, LAST_NAME, EMAIL, PICTURE, DATE_OF_BIRTH FROM persons",
//                (rs, rowNum) -> new Person(
//                        rs.getString("FIRST_NAME"),
//                        rs.getString("LAST_NAME"),
//                        rs.getString("EMAIL"),
//                        rs.getString("PICTURE"),
//                        rs.getDate("DATE_OF_BIRTH")
//                ));
        List<Person> customers  = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper(Person.class));
        return customers;
    }

    public Person findByCustomerId(Long custId) {

        String sql = "SELECT * FROM persons WHERE ID = ?";

        Person customer = (Person) jdbcTemplate.queryForObject(
                sql, new Object[]{custId}, new BeanPropertyRowMapper(Person.class));

        return customer;
    }

    public Person findByFirstLastNameEmail(String firstName, String lastName, String email) {
        Person person = (Person) jdbcTemplate.queryForObject(
                "SELECT * FROM persons p WHERE p.FIRST_NAME = ? and p.LAST_NAME = ? and p.EMAIL = ?",
                new Object[]{firstName, lastName, email}, new BeanPropertyRowMapper(Person.class)
        );
        return person;
    }
    public int findTotalCustomer(){

        String sql = "SELECT COUNT(*) FROM persons";

        int total = jdbcTemplate.queryForObject(sql,Integer.class);

        return total;
    }

    public void updateFull(Person customer) {
        jdbcTemplate.update(
                "UPDATE persons SET FIRST_NAME=?, LAST_NAME=?, EMAIL=?, LOGIN=?, " +
                        "PICTURE=?, NOTES=?, DATE_OF_BIRTH=?, PASSWORD=? WHERE ID=?",
                customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                customer.getLogin(), customer.getPicture(), customer.getNotes(),
                customer.getDateOfBirth(), customer.getPassword(), customer.getId());
    }

    public void update(Person customer) {
            jdbcTemplate.update(
                    "UPDATE persons SET FIRST_NAME=?, LAST_NAME=?, EMAIL=? , LOGIN=?, PASSWORD=?, DATE_OF_BIRTH=?, PICTURE=?, NOTES=? " +
                            "WHERE ID=?",
                    customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                    customer.getLogin(), customer.getPassword(), customer.getDateOfBirth(),
                    customer.getPicture(), customer.getNotes(),
                    customer.getId());
    }
    public void insert(Person customer) {
            jdbcTemplate.update(
                    "INSERT INTO persons (FIRST_NAME, LAST_NAME, EMAIL, LOGIN, PASSWORD," +
                            " PICTURE, NOTES, DATE_OF_BIRTH) VALUES " +
                            "(?,?,?,?,?,?,?,?)",
                    new Object[]{
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getEmail(),
                            customer.getLogin(),
                            customer.getPassword(),
                            customer.getPicture(),
                            customer.getNotes(),
                            customer.getDateOfBirth()

                    } );
    }

    public void save(Person customer) {
        jdbcTemplate.update(
                "UPDATE persons SET FIRST_NAME=?, LAST_NAME=?, EMAIL=? , LOGIN=?, PASSWORD=?, DATE_OF_BIRTH=?, PICTURE=?, NOTES=? " +
                        "WHERE ID=?",
                customer.getFirstName(), customer.getLastName(), customer.getEmail(),
                customer.getLogin(), customer.getPassword(), customer.getDateOfBirth(),
                customer.getPicture(), customer.getNotes(),
                customer.getId());
    }

    public void delete(Person customer) {
        jdbcTemplate.execute("DELETE FROM persons WHERE ID=" + customer.getId());
    }

}
