package com.example.SpringBootJDBCTemplate.dao;

import com.example.SpringBootJDBCTemplate.entity.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDAO {
    JdbcTemplate jdbcTemplate;

    EmployeeDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private final RowMapper<Employee> rowMapper=new RowMapper<Employee>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee=new Employee();
            employee.setEmployee_id(rs.getInt("employee_id"));
            employee.setFirst_name(rs.getString("first_name"));
            employee.setLast_name(rs.getString("last_name"));
            employee.setEmail(rs.getString("email"));
            employee.setDate_of_birth(rs.getDate("date_of_birth"));
            employee.setPhone(rs.getLong("phone"));
            employee.setGender(rs.getString("gender"));
            employee.setJob_role(rs.getString("job_role"));
            employee.setSalary(rs.getDouble("salary"));
            employee.setDepartment_id(rs.getInt("department_id"));
            return employee;
        }
    };

    //CRUD Methods
    //Create Employee
    public int create(Employee employee){
        String sql="INSERT INTO employee(first_name, last_name, email, date_of_birth, phone, gender, job_role, salary, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, employee.getFirst_name(), employee.getLast_name(), employee.getEmail(),
                employee.getDate_of_birth(), employee.getPhone(), employee.getGender(),
                employee.getJob_role(), employee.getSalary(), employee.getDepartment_id());
    }

    //read all Employees from database
    public List<Employee> findAll(){
        String sql="SELECT * FROM Employee";
        return jdbcTemplate.query(sql,rowMapper);
    }

    //retrieve an employee by id
    public Employee findById(int id){
        String sql="SELECT * FROM Employee WHERE employee_id=?";
        return jdbcTemplate.queryForObject(sql,rowMapper,id);
    }

    //update Employee
    public int update(Employee employee){
        String sql = "UPDATE employee SET first_name = ?, last_name = ?, email = ?, date_of_birth = ?, phone = ?, gender = ?, job_role = ?, salary = ?, department_id = ? " +
                "WHERE employee_id = ?";
        return jdbcTemplate.update(sql, employee.getFirst_name(), employee.getLast_name(), employee.getEmail(),
                employee.getDate_of_birth(), employee.getPhone(), employee.getGender(),
                employee.getJob_role(), employee.getSalary(), employee.getDepartment_id(),
                employee.getEmployee_id());
    }

    //delete Employee
    public int delete(int id){
        String sql="DELETE FROM Employee where employee_id = ?";
        return jdbcTemplate.update(sql,id);
    }
}
