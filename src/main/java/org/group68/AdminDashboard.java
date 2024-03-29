package org.group68;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminDashboard extends JFrame {

    private Connection databaseConnection;
    private databaseEditor editor;
    private JPanel mainPane;
    private JTable mainTable;
    private DefaultTableModel roomsTable;
    private DefaultTableModel equipmentTable;
    private DefaultTableModel classesTable;
    private DefaultTableModel billingTable;

    public AdminDashboard(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
        this.editor = new databaseEditor(databaseConnection);

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
        equipmentTable = new DefaultTableModel(new String[] {"equip_id", "name", "room_booked", "last_inspect"}, 1);
        classesTable = new DefaultTableModel(new String[] {"class_id", "name", "session_date", "start_time", "frequency", "routines"}, 1);
        billingTable = new DefaultTableModel(new String[] {"bill_id", "bill_type", "value", "date_billed", "bill_paid"}, 1);

        //TEST:
        roomsTable.addRow(new String[]{"1", "main", "today", "now", "later"});
        equipmentTable.addRow(new String[] {"1", "treadmill", "yes", "now"});

        mainTable = new JTable();
        mainTable.setModel(roomsTable);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(mainTable);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(scrollPane);
        this.add(panel);

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
        mainTable.setModel(roomsTable);
    }

    private void showEquipment(){
        System.out.println("equipment");
        mainTable.setModel(equipmentTable);
    }

    private void showClasses(){
        System.out.println("classes");
        mainTable.setModel(classesTable);
    }

    private void showBilling(){
        System.out.println("billing");
        mainTable.setModel(billingTable);
    }

    private void deleteRow(){

    }

    public class databaseEditor implements TableModelListener {
        private Connection conn;

        public databaseEditor(Connection conn){
            this.conn = conn;
        }
        @Override
        public void tableChanged(TableModelEvent e) {
            if(e.getType() == TableModelEvent.DELETE) {

            }
            else if (e.getType() == TableModelEvent.UPDATE) {

            }
            else {

            }
        }
    }

    public static void main(String[] args) {

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

        AdminDashboard dashboard = new AdminDashboard(databaseConnection);
    }


}
