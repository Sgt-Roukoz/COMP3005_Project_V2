package org.group68;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

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
        LafManager.install(new DarculaTheme());
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

        roomsTable = new DefaultTableModel(new String[] {"room_id", "booking_date", "start_time", "end_time"}, 0);
        equipmentTable = new DefaultTableModel(new String[] {"equip_id", "name", "room_booked", "last_inspect"}, 0);
        classesTable = new DefaultTableModel(new String[] {"class_id", "trainer_id", "class_name", "exercise_routine", "room_id"}, 0);
        billingTable = new DefaultTableModel(new String[] {"bill_id", "member_id", "bill_type", "bill_value", "date_billed", "bill_paid"}, 0);

        roomsTable.addTableModelListener(editor);
        equipmentTable.addTableModelListener(editor);
        classesTable.addTableModelListener(editor);
        billingTable.addTableModelListener(editor);

        mainTable = new JTable();
        mainTable.setModel(roomsTable);
        currentTable = roomsTable;
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(mainTable);

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(scrollPane);
        this.add(panel);
        showRoom();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void processPayment() {
        System.out.println("process payment");
        JTextField member_id = new JTextField();
        JTextField bill_type = new JTextField();
        JTextField value = new JTextField();
        JTextField date_billed = new JTextField();
        JCheckBox bill_paid = new JCheckBox();
        Object[] params = {
                "Member ID: ", member_id,
                "Bill Type: ",  bill_type,
                "Value: ", value,
                "Date Billed (YYYY-MM-DD): ", date_billed,
                "Bill Paid: ", bill_paid
        };
        int option = JOptionPane.showConfirmDialog(null, params, "Process Payment", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Statement stmt = databaseConnection.createStatement();
                stmt.executeUpdate("INSERT INTO Billings (member_id, bill_type, bill_value, date_billed, bill_paid) VALUES ('" + member_id.getText() + "', '" + bill_type.getText() + "', '" + value.getText() + "', '" + date_billed.getText() + "', '" + bill_paid.isSelected() + "');");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input, please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            showBilling();
        }
    }

    private void bookClass() {
        System.out.println("book class");
        JTextField trainer_id = new JTextField();
        JTextField name = new JTextField();
        JTextField date = new JTextField();
        JTextField startTime = new JTextField();
        JTextField endTime = new JTextField();
        JTextField exercise_routine = new JTextField();
        JTextField room_id = new JTextField();
        Object[] params = {
                "Trainer ID: ", trainer_id,
                "Class Name: ",  name,
                "Date (YYYY-MM-DD): ", date,
                "Start Time: ", startTime,
                "End Time: ", endTime,
                "Exercise Routine ID: ", exercise_routine,
                "Room ID: ", room_id
        };
        int option = JOptionPane.showConfirmDialog(null, params, "Book a Class", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Statement stmt = databaseConnection.createStatement();
                System.out.println(date.getText());
                stmt.executeUpdate("INSERT INTO GroupClasses (trainer_id,class_name,exercise_routine,room_id) VALUES (" + trainer_id.getText() + ", '" + name.getText() + "', " + exercise_routine.getText() + ", " + room_id.getText() +  ");");
                stmt.executeUpdate("INSERT INTO RoomBookings (room_id,booking_date,start_time,end_time) VALUES (" + room_id.getText() + ", '" + date.getText() + "', '" + startTime.getText() + "', '" + endTime.getText() + "');");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input, please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            showClasses();
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
                "Booking Date (YYYY-MM-DD): ", date,
                "Start Time: " , startTime,
                "End Time: ", endTime
        };
        int option = JOptionPane.showConfirmDialog(null, params, "Book a Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Statement stmt = databaseConnection.createStatement();
                System.out.println(room_id.getText());
                System.out.println(date.getText());
                stmt.executeUpdate("INSERT INTO RoomBookings (room_id,booking_date,start_time,end_time) VALUES (" + room_id.getText() + ", '" + date.getText() + "', '" + startTime.getText() + "', '" + endTime.getText() + "');");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input, please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            showRoom();
        }
    }

    private void showRoom(){
        System.out.println("view room");
        try {
            roomsTable.setRowCount(0);
            Statement stmt = databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from RoomBookings");
            while (rs.next()) {
                roomsTable.addRow(new Object[]{rs.getInt(1), rs.getDate(2), rs.getTime(3), rs.getTime(4)});
            }
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        mainTable.setModel(roomsTable);
        currentTable = roomsTable;
    }

    private void showEquipment(){
        System.out.println("equipment");
        try {
            equipmentTable.setRowCount(0);
            Statement stmt = databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Equipment");
            while(rs.next()){
                equipmentTable.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getDate(4)});
            }
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        mainTable.setModel(equipmentTable);
        currentTable = equipmentTable;
    }

    private void showClasses(){
        System.out.println("classes");
        try {
            classesTable.setRowCount(0);
            Statement stmt = databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from GroupClasses");
            while(rs.next()){
                classesTable.addRow(new Object[] {rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5)});
            }
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        mainTable.setModel(classesTable);
        currentTable = classesTable;
    }

    private void showBilling(){
        System.out.println("billing");

        try {
            billingTable.setRowCount(0);
            Statement stmt = databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Billings");
            while(rs.next()){
                billingTable.addRow(new Object[] {rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getBoolean(6)});
            }
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        mainTable.setModel(billingTable);
        currentTable = billingTable;
    }

    //TODO
    private void deleteRow(){
        int selected = mainTable.getSelectedRow();
        System.out.println(selected);
        if(currentTable == equipmentTable || currentTable == billingTable) {
            int id = (Integer) mainTable.getValueAt(selected, 0);
        }

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
