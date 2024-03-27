package org.group68;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MemberDashboard extends JFrame {

    private Connection databaseConnection;

    //panels
    private JPanel mainPane;
    private JPanel metricsPanel;

    //metrics icons
    private JLabel hrIcon;
    private JLabel weightIcon;
    private JLabel bpIcon;


    public MemberDashboard(Connection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
        setTitle("Member Dashboard");
        setContentPane(mainPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        Connection databaseConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Students";
            String user = "postgres";
            String password = "z3i0";
            databaseConnection = DriverManager.getConnection(url, user, password);
            if (databaseConnection != null) System.out.println("Connected Successfully");
            else System.out.println("Connection Failed");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        new MemberDashboard(databaseConnection);
    }
}
