package org.group68;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.nio.channels.SelectableChannel;
import java.sql.*;

public class AdminDashboard extends JFrame {

    private Connection databaseConnection;
    private DatabaseEditor editor;
    private JPanel mainPane;
    private JTable mainTable;
    private DefaultTableModel currentTable;
    private DefaultTableModel roomsTable;
    private DefaultTableModel equipmentTable;
    private DefaultTableModel classesTable;
    private DefaultTableModel billingTable;

    public AdminDashboard(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
        this.editor = new DatabaseEditor(databaseConnection);

        setTitle("Admin Dashboard");
        mainPane = new JPanel();
        setContentPane(mainPane);
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem deleteRow = new JMenuItem("Delete Selected Row");
        deleteRow.addActionListener(e -> deleteRow());
        file.add(deleteRow);
        menuBar.add(file);

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

        roomsTable.addTableModelListener(editor);
        equipmentTable.addTableModelListener(editor);
        classesTable.addTableModelListener(editor);
        billingTable.addTableModelListener(editor);

        Statement stmt= null;
        try {
            stmt = databaseConnection.createStatement();
            ResultSet rs=stmt.executeQuery("select * from RoomBookings");
            while(rs.next()){
                roomsTable.addRow(new Object[] {rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4)});
            }
            rs = stmt.executeQuery("select * from GroupClasses");
            while(rs.next()){
                classesTable.addRow(new Object[] {rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5)});
            }
            rs = stmt.executeQuery("select * from Equipment");
            while(rs.next()){
                equipmentTable.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getDate(4)});
            }
            rs = stmt.executeQuery("select * from Billings");
            while(rs.next()){
                billingTable.addRow(new Object[] {rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getBoolean(6)});
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        mainTable = new JTable();
        mainTable.setModel(roomsTable);
        currentTable = roomsTable;
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
        JTextField name = new JTextField();
        JTextField date = new JTextField();
        JTextField startTime = new JTextField();
        JTextField endTime = new JTextField();
        JTextField freq = new JTextField();
        JTextField routines = new JTextField();
        Object[] params = {
                "Class Name: ",  name,
                "Date: ", date,
                "Start Time: ", startTime,
                "End Time: ", endTime,
                "Frequency: ", freq,
                "Routines:", routines
        };
        int option = JOptionPane.showConfirmDialog(null, params, "Book a Class", JOptionPane.OK_CANCEL_OPTION);


        if (option == JOptionPane.OK_OPTION) {
            classesTable.addRow(new String[] {name.getText(), date.getText(), startTime.getText(), endTime.getText(), freq.getText(), routines.getText()});
        }
    }

    private void bookRoom() {
        System.out.println("book room");
        JTextField room_id = new JTextField();
        JTextField date = new JTextField();
        JTextField startTime = new JTextField();
        JTextField endTime = new JTextField();
        Object[] params = {
                "Room ID: ", room_id,
                "Booking Date: ", date,
                "Start Time: " , startTime,
                "End Time: ", endTime
        };
        int option = JOptionPane.showConfirmDialog(null, params, "Book a Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            roomsTable.addRow(new String[] {date.getText(), startTime.getText(), endTime.getText()});
        }
    }

    private void showRoom(){
        System.out.println("view room");
        mainTable.setModel(roomsTable);
        currentTable = roomsTable;
    }

    private void showEquipment(){
        System.out.println("equipment");
        mainTable.setModel(equipmentTable);
        currentTable = equipmentTable;
    }

    private void showClasses(){
        System.out.println("classes");
        mainTable.setModel(classesTable);
        currentTable = classesTable;
    }

    private void showBilling(){
        System.out.println("billing");
        mainTable.setModel(billingTable);
        currentTable = billingTable;
    }

    private void deleteRow(){
        int selected = mainTable.getSelectedRow();
        System.out.println(selected);
        if(selected == -1) return;
        currentTable.removeRow(selected);
    }

    public class DatabaseEditor implements TableModelListener {
        private Connection conn;

        public DatabaseEditor(Connection conn){
            this.conn = conn;
        }
        @Override
        public void tableChanged(TableModelEvent e) {
            if(e.getType() == TableModelEvent.DELETE) {
                System.out.println("deleted a row");
            }
            else if (e.getType() == TableModelEvent.UPDATE) {
                System.out.println("updated a row");
            }
            else if(e.getType() == TableModelEvent.INSERT){
                System.out.println("inserted a row");
            }
        }
    }

    public static void main(String[] args) {

        Connection databaseConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/FitnessClubSystem";
            String user = "postgres";
            String password = "admin";
            databaseConnection = DriverManager.getConnection(url, user, password);
            if (databaseConnection != null) System.out.println("Connected Successfully");
            else System.out.println("Connection Failed");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        AdminDashboard dashboard = new AdminDashboard(databaseConnection);
    }


}
