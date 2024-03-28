package org.group68;

import javax.swing.*;
import java.sql.Connection;

public class AdminDashboard extends JFrame {

    private Connection databaseConnection;
    private JPanel mainPane;

    //TODO: PUT THE DATABASE CONNECTION BACK IN CONSTRUCTOR PARAMS LATER
    public AdminDashboard()
    {
        this.databaseConnection = databaseConnection;
        setTitle("Admin Dashboard");
        mainPane = new JPanel();
        setContentPane(mainPane);
        JMenuBar menuBar = new JMenuBar();
        JMenu room = new JMenu("Room Booking");
        room.addActionListener(e -> showRoom());
        menuBar.add(room);
        JMenu equipment = new JMenu("Equipment Maintenance");
        equipment.addActionListener(e -> showEquipment());
        menuBar.add(equipment);
        JMenu classes = new JMenu("Class Schedule");
        classes.addActionListener(e -> showClasses());
        menuBar.add(classes);
        JMenu billing = new JMenu("Billing");
        billing.addActionListener(e -> showBilling());
        menuBar.add(billing);
        this.setJMenuBar(menuBar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        AdminDashboard dashboard = new AdminDashboard();
    }

    private void showRoom(){
        System.out.println("room");
    }

    private void showEquipment(){
        System.out.println("equipment");
    }

    private void showClasses(){
        System.out.println("classes");
    }

    private void showBilling(){
        System.out.println("billing");
    }
}
