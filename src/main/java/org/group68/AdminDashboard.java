/**
 * The Admin dashboard, to be used by Fitness Club Admins. It manages room bookings, equipment, billing, group classes, and can view exercise routines.
 *
 * @author Eric Wang
 * @version 04/13/2024
 */

package org.group68;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {

    private Connection databaseConnection;
    private JPanel mainPane;
    private JTable mainTable;
    private DefaultTableModel currentTable;
    private DefaultTableModel roomsTable;
    private DefaultTableModel equipmentTable;
    private DefaultTableModel classesTable;
    private DefaultTableModel billingTable;
    private DefaultTableModel routinesTable;

    public AdminDashboard(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;

        setTitle("Admin Dashboard");
        mainPane = new JPanel(new GridBagLayout());
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

        JMenuItem addEquipment = new JMenuItem("Register Equipment");
        addEquipment.addActionListener(e-> addEquipment());
        equipment.add(addEquipment);
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

        JMenu routines = new JMenu("Exercise Routines");
        JMenuItem viewRoutines = new JMenuItem("View Routines");
        viewRoutines.addActionListener(e -> showRoutines());
        routines.add(viewRoutines);
        menuBar.add(routines);

        this.setJMenuBar(menuBar);

        //table model setup
        roomsTable = new DefaultTableModel(new String[] {"room_id", "booking_date", "start_time", "end_time"}, 0);
        equipmentTable = new DefaultTableModel(new String[] {"equip_id", "name", "room", "last_inspect"}, 0);
        classesTable = new DefaultTableModel(new String[] {"class_id", "trainer_id", "class_name", "exercise_routine", "room_id"}, 0);
        billingTable = new DefaultTableModel(new String[] {"bill_id", "member_id", "bill_type", "bill_value", "date_billed", "bill_paid"}, 0);
        routinesTable = new DefaultTableModel(new String[] {"routine_id", "routine_desc"}, 0);

        //one DatabaseEditor per table
        roomsTable.addTableModelListener(new DatabaseEditor(databaseConnection, roomsTable, "RoomBookings"));
        equipmentTable.addTableModelListener(new DatabaseEditor(databaseConnection, equipmentTable, "equipment"));
        classesTable.addTableModelListener(new DatabaseEditor(databaseConnection, classesTable, "groupclasses"));
        billingTable.addTableModelListener(new DatabaseEditor(databaseConnection, billingTable, "billings"));
        routinesTable.addTableModelListener(new DatabaseEditor(databaseConnection, routinesTable, "exerciseroutines"));

        mainTable = new JTable();
        mainTable.setModel(roomsTable);
        mainTable.setAutoCreateRowSorter(true);
        currentTable = roomsTable;
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(mainTable);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(scrollPane, gbc);
        this.add(panel, gbc);

        showRoom();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Opens a popup window for input, and registers a new piece of equipment based on the given input.
     */
    private void addEquipment() {
        System.out.println("add equipment");
        JTextField name = new JTextField();
        JTextField room_id = new JTextField();
        JTextField last_inspected = new JTextField();
        Object[] params = {
                "Equipment Name: ", name,
                "Room ID: ", room_id,
                "Last Inspected (YYYY-MM-DD): ", last_inspected
        };
        int option = JOptionPane.showConfirmDialog(null, params, "Register Equipment", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Statement stmt = databaseConnection.createStatement();
                stmt.executeUpdate("INSERT INTO equipment (equip_name, room, last_inspect) VALUES ('" + name.getText() + "', " + room_id.getText() + ", '" + last_inspected.getText() + "');");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input, please try again.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            showEquipment();
        }
    }

    /**
     * Opens a popup window for input, and creates a new row in the billing table based on the given input.
     */
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

    /**
     * Opens a popup window for input, and books a new group class based on the given input.
     */
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

    /**
     * Opens a popup window for input, and registers a new room booking based on the given input.
     */
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

    /**
     * Displays the exercise routines table.
     */
    private void showRoutines() {
        System.out.println("view routines");
        try {
            routinesTable.setRowCount(0);
            Statement stmt = databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from exerciseroutines");
            while (rs.next()) {
                routinesTable.addRow(new Object[]{rs.getInt(1), rs.getString(2)});
            }
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();

        }
        mainTable.setModel(routinesTable);
        currentTable = routinesTable;
    }

    /**
     * Displays the room bookings table.
     */
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

    /**
     * Displays the equipment table.
     */
    private void showEquipment(){
        System.out.println("equipment");
        try {
            equipmentTable.setRowCount(0);
            Statement stmt = databaseConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Equipment");
            while(rs.next()){
                equipmentTable.addRow(new Object[] {rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDate(4)});
            }
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        mainTable.setModel(equipmentTable);
        currentTable = equipmentTable;
    }

    /**
     * Displays the group classes table.
     */
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

    /**
     * Displays the billing table.
     */
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

    /**
     * Deletes the currently selected row from the currently shown table. If somehow multiple rows are selected, only deletes the first.
     * If none are selected, does nothing.
     */
    private void deleteRow(){
        int selected = mainTable.getSelectedRow();
        if(selected == -1) return;
        System.out.println(selected);
        Statement stmt;
        if(currentTable == equipmentTable || currentTable == billingTable || currentTable == classesTable || currentTable == routinesTable) {
            int id = (Integer) mainTable.getValueAt(selected, 0);
            try {
                stmt = databaseConnection.createStatement();
                if (currentTable == equipmentTable) {
                    stmt.executeUpdate("DELETE FROM equipment WHERE equip_id = " + id + ";");
                    showEquipment();
                }
                else if (currentTable == billingTable) {
                    stmt.executeUpdate("DELETE FROM billings WHERE bill_id = " + id + ";");
                    showBilling();
                }

                else if (currentTable == routinesTable) {
                    JOptionPane.showMessageDialog(null, "You cannot delete exercise routines. Please contact the relevant trainer.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    showRoutines();
                }
                else {
                    stmt.executeUpdate("DELETE FROM groupclasses WHERE class_id = " + id + ";");
                    showClasses();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                stmt = databaseConnection.createStatement();
                stmt.executeUpdate("DELETE FROM roombookings WHERE room_id = " + mainTable.getValueAt(selected, 0) + " AND booking_date = '" + mainTable.getValueAt(selected, 1) + "' AND start_time = '" + mainTable.getValueAt(selected, 2) + "';");
                showRoom();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * DatabaseEditor class updates the backing SQL table to match whenever a row in the JTable is updated by the admin.
     * Row creation and deletion are handled elsewhere, this is only for updating existing rows.
     */
    public class DatabaseEditor implements TableModelListener {
        private Connection conn;
        private TableModel model;
        private String tableName;
        public DatabaseEditor(Connection conn, TableModel model, String tableName){
            this.conn = conn;
            this.model = model;
            this.tableName = tableName;
        }
        @Override
        public void tableChanged(TableModelEvent e) {
            if(e.getType() == TableModelEvent.UPDATE) {
                System.out.println("updated a row");
                String columnName = model.getColumnName(e.getColumn());
                Object value = model.getValueAt(e.getFirstRow(), e.getColumn());
                if(tableName.equalsIgnoreCase("roombookings")) {
                    JOptionPane.showMessageDialog(null, "All room bookings are final and unchangeable. Please delete this booking and create a new one.", "ERROR: Room Bookings cannot be modified", JOptionPane.ERROR_MESSAGE);
                    showRoom();
                }
                else {
                    try {
                        Statement stmt = conn.createStatement();
                        int id = (int) model.getValueAt(e.getFirstRow(), 0);
                        if (value instanceof String) {
                            String str = (String) value;
                            System.out.println("UPDATE " + tableName + " SET " + columnName + " = " + str + " WHERE " + model.getColumnName(0) + " = " + id + ";");
                            stmt.executeUpdate("UPDATE " + tableName + " SET " + columnName + " = '" + str + "' WHERE " + model.getColumnName(0) + " = " + id + ";");
                        }
                        else if (value instanceof Integer) {
                            int intValue = (Integer) value;
                            stmt.executeUpdate("UPDATE " + tableName + " SET " + columnName + " = " + intValue + " WHERE " + model.getColumnName(0) + " = " + id + ";");
                        }
                        else if (value instanceof Date) {
                            String str = value.toString();
                            stmt.executeUpdate("UPDATE " + tableName + " SET " + columnName + " = '" + str + "' WHERE " + model.getColumnName(0) + " = " + id + ";");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
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
