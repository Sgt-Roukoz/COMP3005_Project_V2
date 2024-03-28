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
        JMenu room = new JMenu("Rooms");
        JMenuItem viewRoom = new JMenuItem("View Room Bookings");
        viewRoom.addActionListener(e -> showRoom());
        room.add(viewRoom);
        JMenuItem bookRoom = new JMenuItem("Book a Room");
        bookRoom.addActionListener(e -> bookRoom());
        room.add(bookRoom);
        menuBar.add(room);
        JMenu equipment = new JMenu("Equipment Maintenance");
        JMenuItem viewEquipment = new JMenuItem("View Equipment");
        viewEquipment.addActionListener(e -> showEquipment());
        equipment.add(viewEquipment);
        menuBar.add(equipment);
        JMenu classes = new JMenu("Classes");
        JMenuItem viewClasses = new JMenuItem("View Classes");
        viewClasses.addActionListener(e -> showClasses());
        classes.add(viewClasses);
        JMenuItem bookClass = new JMenuItem("Book a Class");
        bookClass.addActionListener(e -> bookClass());
        classes.add(bookClass);
        menuBar.add(classes);
        JMenu billing = new JMenu("Billing");
        JMenuItem viewBilling = new JMenuItem("View Billing info");
        viewBilling.addActionListener(e -> showBilling());
        billing.add(viewBilling);
        JMenuItem payment = new JMenuItem("Process Payment");
        payment.addActionListener(e -> processPayment());
        billing.add(payment);
        menuBar.add(billing);
        this.setJMenuBar(menuBar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void processPayment() {
        System.out.println("process payment");
    }

    private void bookClass() {
        System.out.println("book class");
    }

    private void bookRoom() {
        System.out.println("book room");
    }

    private void showRoom(){
        System.out.println("view room");
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

    public static void main(String[] args) {
        AdminDashboard dashboard = new AdminDashboard();
    }


}
