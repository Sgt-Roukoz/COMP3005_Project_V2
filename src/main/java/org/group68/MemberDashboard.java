package org.group68;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
    DefaultTableModel achievementmodel;
    private int memberID;


    public MemberDashboard(Connection databaseConnection, int memberID) throws UnsupportedLookAndFeelException {
        this.databaseConnection = databaseConnection;
        this.memberID = memberID;
        // For default theme (IntelliJ)
        // Specify explicit theme.
        LafManager.install(new DarculaTheme());

        setUpInitialValues();
        setTitle("Member Dashboard");
        setContentPane(mainPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
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
        //LafManager.forceLafUpdate();
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setUpAchievements();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUpAchievements()
    {
        achievementmodel = new AchievementModel();
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
