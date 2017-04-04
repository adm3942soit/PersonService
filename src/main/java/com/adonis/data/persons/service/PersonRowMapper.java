package com.adonis.data.persons.service;

import com.adonis.data.persons.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oksdud on 31.03.2017.
 */
public class PersonRowMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person customer = new Person();
            customer.setId(rs.getLong("ID"));
            customer.setFirstName(rs.getString("First name"));
            customer.setLastName(rs.getString("Last name"));
            customer.setEmail(rs.getString("Email"));
            customer.setDateOfBirth(rs.getDate("BirthDay"));
            return customer;
        }
}
