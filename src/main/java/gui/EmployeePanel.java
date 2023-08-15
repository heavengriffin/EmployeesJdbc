package gui;

import db.Employee;
import db.EmployeeDao;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePanel extends JPanel {
    List<Employee> employees;

    EmployeeDao employeeDao = new EmployeeDao();

    List<String> columnNames;

    List<Employee> employeesSearchedByName;

    boolean searchedByValue = false;

    public void refresh() {
        columnNames = employeeDao.getColumnNames();
        employees = employeeDao.getAll();
    }

    public void searchByValue() {
        columnNames = employeeDao.getColumnNames();
        employees = employeesSearchedByName;
    }

    public EmployeePanel() {
        setLayout(new FlowLayout());
        EmployeeTableModel employeeTableModel = new EmployeeTableModel();
        JTable employeeTable = new JTable(employeeTableModel);
        JScrollPane jScrollPane = new JScrollPane(employeeTable);
        jScrollPane.setPreferredSize(new Dimension(jScrollPane.getPreferredSize().width, 200));
        add(jScrollPane);
        JButton[] buttons = new JButton[5];
        for (int i = 0; i < buttons.length; i++)
            add(buttons[i] = new JButton());
        buttons[0].setText("Insert");

        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame insertFrame = new JFrame("Insert");
                JLabel name = new JLabel("Name: ");
                insertFrame.add(name);
                JTextField nameField = new JTextField();
                nameField.setPreferredSize(new Dimension(100, 20));
                insertFrame.add(nameField);
                JLabel surname = new JLabel("Surname: ");
                insertFrame.add(surname);
                JTextField surnameField = new JTextField();
                surnameField.setPreferredSize(new Dimension(100, 20));
                insertFrame.add(surnameField);
                JLabel age = new JLabel("Age: ");
                insertFrame.add(age);
                JTextField ageField = new JTextField();
                ageField.setPreferredSize(new Dimension(30, 20));
                insertFrame.add(ageField);
                JLabel address = new JLabel("Address: ");
                insertFrame.add(address);
                JTextField addressField = new JTextField();
                addressField.setPreferredSize(new Dimension(150, 20));
                insertFrame.add(addressField);
                JLabel salary = new JLabel("Salary: ");
                insertFrame.add(salary);
                JTextField salaryField = new JTextField();
                salaryField.setPreferredSize(new Dimension(70, 20));
                insertFrame.add(salaryField);
                JButton insertButton = new JButton("Insert");
                insertFrame.add(insertButton);
                insertButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String query = "INSERT INTO employees (`name`, `surname`, `age`, `address`, `salary`) VALUES (?, ?, ?, ?, ?)";
                        try (Connection connection = DriverManager.getConnection(EmployeeDao.URL, EmployeeDao.USERNAME, EmployeeDao.PASSWORD);
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, nameField.getText());
                            preparedStatement.setString(2, surnameField.getText());
                            preparedStatement.setInt(3, Integer.parseInt(ageField.getText()));
                            preparedStatement.setString(4, addressField.getText());
                            preparedStatement.setInt(5, Integer.parseInt(salaryField.getText()));
                            preparedStatement.execute();
                            refresh();
                            insertFrame.dispose();

                        } catch (SQLException exc) {
                            System.err.println(exc.getMessage());
                        }
                    }
                });
                insertFrame.setLayout(new FlowLayout());
                insertFrame.setSize(800, 100);
                insertFrame.setVisible(true);
                insertFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


            }
        });

        buttons[1].setText("Update");
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame selectUpdateFrame = new JFrame("select id");
                JLabel selectUpdate = new JLabel("Select id: ");
                selectUpdateFrame.add(selectUpdate);
                JTextField selectUpdateField = new JTextField();
                selectUpdateField.setPreferredSize(new Dimension(50, 20));
                selectUpdateFrame.add(selectUpdateField);
                JButton updateOnIdButton = new JButton("Select");
                selectUpdateFrame.add(updateOnIdButton);
                selectUpdateFrame.setLayout(new FlowLayout());
                selectUpdateFrame.pack();
                selectUpdateFrame.setVisible(true);
                updateOnIdButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectUpdateFrame.dispose();
                        employees = employeeDao.getAll();
                        int foundId = 0;
                        int actualId = 0;
                        for (int i = 0; i < employees.size(); i++) {
                            if (employees.get(i).getId() == Integer.parseInt(selectUpdateField.getText())) {
                                foundId = i;
                                actualId = employees.get(i).getId();
                            }
                        }
                        JFrame updateFrame = new JFrame("Update");
                        JLabel updateName = new JLabel("Name: ");
                        updateFrame.add(updateName);
                        JTextField updateNameField = new JTextField(employees.get(foundId).getName());
                        updateNameField.setPreferredSize(new Dimension(100, 20));
                        updateFrame.add(updateNameField);
                        JLabel updateSurname = new JLabel("Surname: ");
                        updateFrame.add(updateSurname);
                        JTextField updateSurnameField = new JTextField(employees.get(foundId).getSurname());
                        updateSurnameField.setPreferredSize(new Dimension(100, 20));
                        updateFrame.add(updateSurnameField);
                        JLabel updateAge = new JLabel("Age: ");
                        updateFrame.add(updateAge);
                        JTextField updateAgeField = new JTextField(String.valueOf(employees.get(foundId).getAge()));
                        updateAgeField.setPreferredSize(new Dimension(30, 20));
                        updateFrame.add(updateAgeField);
                        JLabel updateAddress = new JLabel("Address: ");
                        updateFrame.add(updateAddress);
                        JTextField updateAddressField = new JTextField(employees.get(foundId).getAddress());
                        updateAddressField.setPreferredSize(new Dimension(150, 20));
                        updateFrame.add(updateAddressField);
                        JLabel updateSalary = new JLabel("Salary: ");
                        updateFrame.add(updateSalary);
                        JTextField updateSalaryField = new JTextField(String.valueOf(employees.get(foundId).getSalary()));
                        updateSalaryField.setPreferredSize(new Dimension(70, 20));
                        updateFrame.add(updateSalaryField);
                        JButton updateButton = new JButton("Update");
                        updateFrame.add(updateButton);
                        int finalActualId = actualId;
                        updateButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String query = "UPDATE employees SET `name` = ?, `surname` = ?, `age` = ?, `address` = ?, `salary` = ? WHERE id = " + finalActualId;
                                try (Connection connection = DriverManager.getConnection(EmployeeDao.URL, EmployeeDao.USERNAME, EmployeeDao.PASSWORD);
                                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                                    preparedStatement.setString(1, updateNameField.getText());
                                    preparedStatement.setString(2, updateSurnameField.getText());
                                    preparedStatement.setInt(3, Integer.parseInt(updateAgeField.getText()));
                                    preparedStatement.setString(4, updateAddressField.getText());
                                    preparedStatement.setInt(5, Integer.parseInt(updateSalaryField.getText()));
                                    preparedStatement.execute();
                                    refresh();
                                } catch (SQLException exc) {
                                    System.err.println(exc.getMessage());
                                }
                                updateFrame.dispose();
                            }
                        });
                        updateFrame.setLayout(new FlowLayout());
                        updateFrame.setSize(800, 100);
                        updateFrame.setVisible(true);
                        updateFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }
                });


            }
        });

        buttons[2].setText("Delete");
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame deleteFrame = new JFrame("Delete");
                JLabel delete = new JLabel("Select id to delete: ");
                deleteFrame.add(delete);
                JTextField deleteField = new JTextField();
                deleteField.setPreferredSize(new Dimension(50, 20));
                deleteFrame.add(deleteField);
                JButton deleteOnSelect = new JButton("Delete");
                deleteFrame.add(deleteOnSelect);
                deleteOnSelect.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int foundId = Integer.parseInt(deleteField.getText());
                        String query = "DELETE FROM employees WHERE id = " + foundId;
                        try (Connection connection = DriverManager.getConnection(EmployeeDao.URL, EmployeeDao.USERNAME, EmployeeDao.PASSWORD);
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.execute();
                            refresh();
                        } catch (SQLException exc) {
                            System.err.println(exc.getMessage());
                        }
                        deleteFrame.dispose();
                    }
                });
                deleteFrame.setLayout(new FlowLayout());
                deleteFrame.pack();
                deleteFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                deleteFrame.setVisible(true);
            }
        });

        buttons[3].setText("Search");
        buttons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame searchFrame = new JFrame("Search");
                JLabel searchName = new JLabel("Search by name: ");
                searchFrame.add(searchName);
                JTextField searchNameField = new JTextField();
                searchNameField.setPreferredSize(new Dimension(100, 20));
                searchFrame.add(searchNameField);
                JButton searchByName = new JButton("Search");
                searchFrame.add(searchByName);
                searchFrame.setLayout(new FlowLayout());
                searchFrame.setSize(400, 200);
                searchFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                searchFrame.setVisible(true);
                searchByName.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String query = "SELECT * FROM employees WHERE `name` = ?";
                        try (Connection connection = DriverManager.getConnection(EmployeeDao.URL, EmployeeDao.USERNAME, EmployeeDao.PASSWORD);
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, searchNameField.getText());
                            ResultSet resultSet = preparedStatement.executeQuery();
                            employeesSearchedByName = new ArrayList<>();
                            while (resultSet.next()) {
                                Employee employee = new Employee();
                                employee.setId(resultSet.getInt("id"));
                                employee.setName(resultSet.getString("name"));
                                employee.setSurname(resultSet.getString("surname"));
                                employee.setAge(resultSet.getInt("age"));
                                employee.setAddress(resultSet.getString("address"));
                                employee.setSalary(resultSet.getInt("salary"));

                                employeesSearchedByName.add(employee);

                            }
                            searchedByValue = true;
                            searchByValue();

                        } catch (SQLException exc) {
                            System.err.println(exc.getMessage());
                        }
                        searchFrame.dispose();
                    }
                });
                JLabel searchSurname = new JLabel("Search by surname: ");
                searchFrame.add(searchSurname);
                JTextField searchSurnameField = new JTextField();
                searchSurnameField.setPreferredSize(new Dimension(100, 20));
                searchFrame.add(searchSurnameField);
                JButton searchBySurname = new JButton("Search");
                searchFrame.add(searchBySurname);
                searchBySurname.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String query = "SELECT * FROM employees WHERE `surname` = ?";
                        try (Connection connection = DriverManager.getConnection(EmployeeDao.URL, EmployeeDao.USERNAME, EmployeeDao.PASSWORD);
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, searchSurnameField.getText());
                            ResultSet resultSet = preparedStatement.executeQuery();
                            employeesSearchedByName = new ArrayList<>();
                            while (resultSet.next()) {
                                Employee employee = new Employee();
                                employee.setId(resultSet.getInt("id"));
                                employee.setName(resultSet.getString("name"));
                                employee.setSurname(resultSet.getString("surname"));
                                employee.setAge(resultSet.getInt("age"));
                                employee.setAddress(resultSet.getString("address"));
                                employee.setSalary(resultSet.getInt("salary"));

                                employeesSearchedByName.add(employee);

                            }
                            searchedByValue = true;
                            searchByValue();

                        } catch (SQLException exc) {
                            System.err.println(exc.getMessage());
                        }
                        searchFrame.dispose();
                    }
                });
                JLabel searchAge = new JLabel("Search by age: ");
                searchFrame.add(searchAge);
                JTextField searchAgeField = new JTextField();
                searchAgeField.setPreferredSize(new Dimension(100, 20));
                searchFrame.add(searchAgeField);
                JButton searchByAge = new JButton("Search");
                searchFrame.add(searchByAge);
                searchByAge.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String query = "SELECT * FROM employees WHERE `age` = ?";
                        try (Connection connection = DriverManager.getConnection(EmployeeDao.URL, EmployeeDao.USERNAME, EmployeeDao.PASSWORD);
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setInt(1, Integer.parseInt(searchAgeField.getText()));
                            ResultSet resultSet = preparedStatement.executeQuery();
                            employeesSearchedByName = new ArrayList<>();
                            while (resultSet.next()) {
                                Employee employee = new Employee();
                                employee.setId(resultSet.getInt("id"));
                                employee.setName(resultSet.getString("name"));
                                employee.setSurname(resultSet.getString("surname"));
                                employee.setAge(resultSet.getInt("age"));
                                employee.setAddress(resultSet.getString("address"));
                                employee.setSalary(resultSet.getInt("salary"));

                                employeesSearchedByName.add(employee);

                            }
                            searchedByValue = true;
                            searchByValue();

                        } catch (SQLException exc) {
                            System.err.println(exc.getMessage());
                        }
                        searchFrame.dispose();
                    }
                });
                JLabel searchAddress = new JLabel("Search by address: ");
                searchFrame.add(searchAddress);
                JTextField searchAddressField = new JTextField();
                searchAddressField.setPreferredSize(new Dimension(100, 20));
                searchFrame.add(searchAddressField);
                JButton searchByAddress = new JButton("Search");
                searchFrame.add(searchByAddress);
                searchByAddress.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String query = "SELECT * FROM employees WHERE `address` = ?";
                        try (Connection connection = DriverManager.getConnection(EmployeeDao.URL, EmployeeDao.USERNAME, EmployeeDao.PASSWORD);
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, searchAddressField.getText());
                            ResultSet resultSet = preparedStatement.executeQuery();
                            employeesSearchedByName = new ArrayList<>();
                            while (resultSet.next()) {
                                Employee employee = new Employee();
                                employee.setId(resultSet.getInt("id"));
                                employee.setName(resultSet.getString("name"));
                                employee.setSurname(resultSet.getString("surname"));
                                employee.setAge(resultSet.getInt("age"));
                                employee.setAddress(resultSet.getString("address"));
                                employee.setSalary(resultSet.getInt("salary"));

                                employeesSearchedByName.add(employee);

                            }
                            searchedByValue = true;
                            searchByValue();

                        } catch (SQLException exc) {
                            System.err.println(exc.getMessage());
                        }
                        searchFrame.dispose();
                    }
                });
                JLabel searchSalary = new JLabel("Search by salary: ");
                searchFrame.add(searchSalary);
                JTextField searchSalaryField = new JTextField();
                searchSalaryField.setPreferredSize(new Dimension(100, 20));
                searchFrame.add(searchSalaryField);
                JButton searchBySalary = new JButton("Search");
                searchFrame.add(searchBySalary);
                searchBySalary.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String query = "SELECT * FROM employees WHERE `salary` = ?";
                        try (Connection connection = DriverManager.getConnection(EmployeeDao.URL, EmployeeDao.USERNAME, EmployeeDao.PASSWORD);
                             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setInt(1, Integer.parseInt(searchSalaryField.getText()));
                            ResultSet resultSet = preparedStatement.executeQuery();
                            employeesSearchedByName = new ArrayList<>();
                            while (resultSet.next()) {
                                Employee employee = new Employee();
                                employee.setId(resultSet.getInt("id"));
                                employee.setName(resultSet.getString("name"));
                                employee.setSurname(resultSet.getString("surname"));
                                employee.setAge(resultSet.getInt("age"));
                                employee.setAddress(resultSet.getString("address"));
                                employee.setSalary(resultSet.getInt("salary"));

                                employeesSearchedByName.add(employee);

                            }
                            searchedByValue = true;
                            searchByValue();

                        } catch (SQLException exc) {
                            System.err.println(exc.getMessage());
                        }
                        searchFrame.dispose();
                    }
                });
            }
        });

        buttons[4].setText("Show All");
        buttons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchedByValue = false;
                refresh();
                employeeTable.repaint();

            }
        });

    }

    class EmployeeTableModel extends AbstractTableModel {

        EmployeeTableModel() {

            if (!searchedByValue)
                refresh();
            else
                searchByValue();
        }

        @Override
        public int getRowCount() {
            return employees.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Employee employee = employees.get(rowIndex);
            EmployeeWrapper employeeWrapper = new EmployeeWrapper(employee);
            return employeeWrapper.getColumnValue(columnIndex);
        }

        @Override
        public String getColumnName(int column) {
            return columnNames.get(column);
        }

    }

}
