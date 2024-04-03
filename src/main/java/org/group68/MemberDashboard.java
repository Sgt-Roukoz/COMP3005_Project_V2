package org.group68;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class MemberDashboard extends JFrame {

    private Connection databaseConnection;

    //panels
    private JPanel mainPane;
    private JPanel metricsPanel;

    //metrics icons
    private JLabel hrIcon;
    private JLabel weightIcon;
    private JLabel bpIcon;
    private JButton achievementReset;
    private JButton achievementApply;
    private JTable achievementTable;
    private JTable table1;
    private JButton button1;
    private JTabbedPane dashboardPane;
    private JTable goaltable;
    private JButton button2;
    private JButton button3;

    //Schedule Page Variables
    private JTable upcomingClasses;
    private JTable upcomingSessions;
    private JComboBox<String> classSelector;
    private JComboBox<String> sessionDaySelector;
    private JComboBox<String> trainerSelector;
    private JTextField dateField;
    private JTextField endtimeField;
    private JTextField starttimeField;
    private Map<Integer, GroupClass> availableClasses;

    //models
    private DefaultTableModel classesModel;
    DefaultTableModel achievementmodel, goalmodel;

    //Member Data
    private int memberID;


    public MemberDashboard(Connection databaseConnection, int memberID) throws UnsupportedLookAndFeelException {
        this.databaseConnection = databaseConnection;
        this.memberID = memberID;
        availableClasses = new HashMap<>();
        // For default theme (IntelliJ)
        // Specify explicit theme.

        setUpInitialValues();
        setTitle("Member Dashboard");
        setContentPane(mainPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 900);
        setLocationRelativeTo(null);
        LafManager.install(new DarculaTheme());
        setVisible(true);
        setResizable(false);
        achievementApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyAchievementChanges();
            }
        });
        achievementReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retrieveAchievements();
            }
        });
        classSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String[] itemSelected = ((String) cb.getSelectedItem()).split(" ");
                GroupClass selectedClass = availableClasses.get(Integer.valueOf(itemSelected[0]));
                dateField.setText(selectedClass.date());
                starttimeField.setText(selectedClass.start_time());
                endtimeField.setText(selectedClass.end_time());
            }
        });
    }

    private void setUpInitialValues(){
        try
        {
            Statement statement = databaseConnection.createStatement();
            statement.executeQuery("SELECT * FROM metrics WHERE member_id = " + memberID);
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            hrIcon.setText(String.valueOf(resultSet.getInt("weight")));
            hrIcon.setFont(new Font("Jetbrains Mono", Font.BOLD, 15));
            weightIcon.setText(String.valueOf(resultSet.getInt("resting_hr")));
            weightIcon.setFont(new Font("Jetbrains Mono", Font.BOLD, 15));
            bpIcon.setText(String.valueOf(resultSet.getInt("blood_pressure")));
            bpIcon.setFont(new Font("Jetbrains Mono", Font.BOLD, 15));
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setUpAchievements();
        setUpSessionsClasses();
    }

    @SuppressWarnings("StringTemplateMigration")
    private void setUpSessionsClassesValues() {
        try
        {
            Statement statement = databaseConnection.createStatement();
            String message = "WITH FullClassInfo(class_id, class_name, booking_date, start_time, end_time) AS (\n" +
                    "\tSELECT class_id, class_name, booking_date, start_time, end_time\n" +
                    "\tFROM groupclasses JOIN roombookings ON groupclasses.room_id = roombookings.room_id\n" +
                    ")\n" +
                    "SELECT class_name, booking_date, start_time, end_time\n" +
                    "FROM FullClassInfo JOIN ClassMembers ON classmembers.class_id = FullClassInfo.class_id\n" +
                    "WHERE member_id = " + memberID; // query that pulls class info that member has registered in
            statement.executeQuery(message);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next())
            {
                classesModel.addRow(new Object[]{resultSet.getString("class_name"), resultSet.getString("booking_date"),
                        resultSet.getString("start_time"), resultSet.getString("end_time")});
            }
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        GetAvailableClasses();
    }

    private void GetAvailableClasses()
    {
        try
        {
            Statement statement = databaseConnection.createStatement();
            String message = "WITH FullClassInfo(class_id, class_name, booking_date, start_time, end_time) AS (\n" +
                    "    SELECT class_id, class_name, booking_date, start_time, end_time\n" +
                    "    FROM (groupclasses JOIN roombookings ON groupclasses.room_id = roombookings.room_id)\n" +
                    ")\n" +
                    "SELECT FullClassInfo.class_id, class_name, booking_date, start_time, end_time\n" +
                    "FROM FullClassInfo FULL OUTER JOIN ClassMembers ON classmembers.class_id = FullClassInfo.class_id\n" +
                    "WHERE member_id IS null OR member_id !=" + memberID; // query that pulls class info that member has NOT registered in

            statement.executeQuery(message);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next())
            {
                GroupClass newClass = new GroupClass(resultSet.getInt("class_id"), resultSet.getString("class_name"),
                        resultSet.getString("booking_date"), resultSet.getString("start_time"), resultSet.getString("end_time"));
                availableClasses.put(newClass.id(), newClass);
                classSelector.addItem(STR."\{newClass.id()} \{newClass.name()}");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        classSelector.setSelectedIndex(-1);

    }

    private void setUpSessionsClasses() {
        dateField.setEditable(false);
        starttimeField.setEditable(false);
        endtimeField.setEditable(false);
        classesModel = new DefaultTableModel();
        classesModel.addColumn("Class Name");
        classesModel.addColumn("Date");
        classesModel.addColumn("Start Time");
        classesModel.addColumn("End Time");
        upcomingClasses.setModel(classesModel);
        setUpSessionsClassesValues();
    }

    private void retrieveAchievements() {
        try{
            Statement statement = databaseConnection.createStatement();
            statement.executeQuery("SELECT * FROM achievements WHERE member_id = " + memberID);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next())
            {
                achievementmodel.addRow(new Object[]{resultSet.getString("description"),
                        resultSet.getBoolean("achieved"), resultSet.getString("date_achieved")});
                goalmodel.addRow(new Object[]{resultSet.getString("description"),
                        resultSet.getBoolean("achieved"), resultSet.getString("date_achieved")});
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUpAchievements()
    {
        achievementmodel = new AchievementModel();
        goalmodel = new AchievementModel();
        retrieveAchievements();
        achievementTable.setModel(achievementmodel);
        achievementTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        achievementTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        System.out.println(achievementTable.getColumnModel().getColumn(1).getHeaderValue());

    }

    public static class AchievementModel extends DefaultTableModel {

        public AchievementModel() {
            super(new String[]{"Achievement", "", "Date Achieved"}, 0);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class clazz = String.class;

            switch (columnIndex) {
                case 1:
                    clazz = Boolean.class;
                    break;
            }
            return clazz;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1;
        }

        @Override
        public void setValueAt(Object aValue, int row, int column) {
            if (aValue instanceof Boolean && column == 1) {
                System.out.println(aValue);
                var rowData = getDataVector().get(row);
                rowData.set(1, (boolean)aValue);
                fireTableCellUpdated(row, column);
            }
        }
    }

    protected void applyAchievementChanges() {
        try{
            Statement statement = databaseConnection.createStatement();
            int modelSize = achievementmodel.getRowCount();
            for (int i = 0; i < modelSize; i++) {
                if (achievementmodel.getValueAt(i, 2) == null)
                {
                    boolean achieved = (boolean)achievementmodel.getValueAt(i, 1);
                    if (achieved)
                    {
                        String message = "UPDATE achievements" +
                        " SET achieved = true, date_achieved = CURRENT_DATE" +
                        " WHERE member_id = " + memberID + " AND description = '" + achievementmodel.getValueAt(i, 0) + "';";
                        statement.execute(message);
                    }
                }
                System.out.println(achievementmodel.getValueAt(i, 1));
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setUpAchievements();
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {

        Connection databaseConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/FitnessClub";
            String user = "postgres";
            String password = "z3i0";
            databaseConnection = DriverManager.getConnection(url, user, password);
            if (databaseConnection != null) System.out.println("Connected Successfully");
            else System.out.println("Connection Failed");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        new MemberDashboard(databaseConnection, 1);
    }

}
