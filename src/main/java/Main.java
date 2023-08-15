import db.*;
import gui.EmployeePanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame{

    public static void main(String[] args) {

        Main frame = new Main();
        EmployeePanel employeePanel = new EmployeePanel();
        frame.setContentPane(employeePanel);
        frame.setSize(500, 270);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
