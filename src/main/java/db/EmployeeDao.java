package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import gui.*;

public class EmployeeDao implements Dao<Employee>{

    public static final String URL = "jdbc:mysql://localhost:3306/firm_employees";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "Developer'sAngles9";

    @Override
    public void insert(Employee entity) {

    }

    @Override
    public void update(Employee entity) {

    }

    @Override
    public boolean delete(Employee entity) {
        return false;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setSurname(resultSet.getString("surname"));
                employee.setAge(resultSet.getInt("age"));
                employee.setAddress(resultSet.getString("address"));
                employee.setSalary(resultSet.getInt("salary"));

                employees.add(employee);
            }

        } catch (SQLException exc) {
            System.err.println(exc.getMessage());
        }

        return employees;
    }

    @Override
    public Employee getEmployee(Object aValue) {
        return null;
    }

    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSetMetaData.getColumnName(i);
                columnNames.add(columnName);
            }

        } catch (SQLException exc) {
            System.err.println(exc.getMessage());
        }
        return columnNames;
    }
}
