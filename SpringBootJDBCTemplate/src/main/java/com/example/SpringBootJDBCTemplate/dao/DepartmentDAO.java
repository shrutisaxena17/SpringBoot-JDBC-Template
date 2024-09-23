package com.example.SpringBootJDBCTemplate.dao;

import com.example.SpringBootJDBCTemplate.entity.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentDAO {
    JdbcTemplate jdbcTemplate;

    DepartmentDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private final RowMapper<Department> rowMapper=new RowMapper<Department>() {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            Department department=new Department();
            department.setDepartment_id(rs.getInt("department_id"));
            department.setDepartment_name(rs.getString("department_name"));
            return department;
        }
    };

    //CRUD OPERATIONS

    //create a department
    public int create(Department department){
        String sql="INSERT INTO Department(department_name) VALUES (?)";
        return jdbcTemplate.update(sql,department.getDepartment_name());
    }

    //retrieve all departments
    public List<Department> findAll(){
        String sql="SELECT * FROM Department";
        return jdbcTemplate.query(sql,rowMapper);
    }

    //retrieve departments by id
    public Department findById(int department_id){
        String sql="SELECT * FROM Department WHERE department_id=?";
        return jdbcTemplate.queryForObject(sql,rowMapper,department_id);
    }

    //update department
    public int update(Department department){
        String sql="UPDATE Department SET department_name=? WHERE department_id=?";
        return jdbcTemplate.update(sql,department.getDepartment_name(),department.getDepartment_id());
    }

    // Delete Department
    public int delete(int id) {
        String sql = "DELETE FROM department WHERE department_id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
