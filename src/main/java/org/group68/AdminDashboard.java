package org.group68;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

public class AdminDashboard extends JFrame {

    private Connection databaseConnection;
    private JPanel mainPane;
    private JTable mainTable;
    private DefaultTableModel roomsTable;
    private DefaultTableModel equipmentTable;
    private DefaultTableModel classesTable;
    private DefaultTableModel billingTable;

    //TODO: PUT THE DATABASE CONNECTION BACK IN CONSTRUCTOR PARAMS LATER
    public AdminDashboard() {
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

        roomsTable = new DefaultTableModel(new String[] {"room_id", "room_name", "booking_date", "start_time", "end_time"}, 1);
        //TEST:
        roomsTable.addRow(new String[]{"1", "main", "today", "now", "later"});
        equipmentTable = new DefaultTableModel(new String[] {"equip_id", "name", "room_booked", "last_inspect"}, 1);
        classesTable = new DefaultTableModel(new String[] {"class_id", "name", "session_date", "start_time", "frequency", "routines"}, 1);
        billingTable = new DefaultTableModel(new String[] {"bill_id", "bill_type", "value", "date_billed", "bill_paid"}, 1);

        mainTable = new JTable();
        mainTable.setModel(roomsTable);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(mainTable);
        this.add(scrollPane);

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
