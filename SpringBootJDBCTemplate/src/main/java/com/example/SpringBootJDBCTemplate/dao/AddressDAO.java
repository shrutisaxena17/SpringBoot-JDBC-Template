package com.example.SpringBootJDBCTemplate.dao;

import com.example.SpringBootJDBCTemplate.entity.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AddressDAO {
    JdbcTemplate jdbcTemplate;

    AddressDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private final RowMapper<Address> rowMapper=new RowMapper<Address>() {
        @Override
        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
            Address address=new Address();
            address.setAddress_id(rs.getInt("address_id"));
            address.setEmployee_id(rs.getInt("employee_id"));
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setPin_code(rs.getString("pin_code"));
            return address;
        }
    };

    //CRUD Operations
    //Create Employee's Address
    public int create(Address address) {
        String sql = "INSERT INTO address(employee_id, street, city, state, pin_code) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, address.getEmployee_id(), address.getStreet(),
                address.getCity(), address.getState(), address.getPin_code());
    }

    //Read all employee's address
    public List<Address> findAll(){
        String sql="SELECT * FROM Address";
        return jdbcTemplate.query(sql,rowMapper);
    }

    //retrieve address of employee by employee's id
    public Address findById(int employeeId) {
        String sql = "SELECT a.* FROM address a JOIN employee e ON a.employee_id = e.employee_id WHERE e.employee_id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, employeeId);
    }

    //update Address
    public int update(Address address) {
        String sql = "UPDATE address SET street = ?, city = ?, state = ?, pin_code = ? WHERE address_id = ?";
        return jdbcTemplate.update(sql, address.getStreet(), address.getCity(),
                address.getState(), address.getPin_code(), address.getAddress_id());
    }

    // Delete Address
    public int delete(int id) {
        String sql = "DELETE FROM address WHERE address_id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
