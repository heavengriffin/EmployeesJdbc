package gui;

import db.Employee;

public class EmployeeWrapper {

    private Employee employee;

    public EmployeeWrapper(Employee employee) {
        this.employee = employee;
    }

    public Object getColumnValue(int index) {
        return switch (index) {
            case 0 -> employee.getId();
            case 1 -> employee.getName();
            case 2 -> employee.getSurname();
            case 3 -> employee.getAge();
            case 4 -> employee.getAddress();
            case 5 -> employee.getSalary();
            default -> null;
        };
    }
}
