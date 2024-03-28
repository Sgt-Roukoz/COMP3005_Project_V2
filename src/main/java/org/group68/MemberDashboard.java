package org.group68;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

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


    public MemberDashboard(Connection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
        // For default theme (IntelliJ)
        // Specify explicit theme.
        LafManager.setTheme(new DarculaTheme());
        LafManager.install();

        setUpAchievements();
        setTitle("Member Dashboard");
        setContentPane(mainPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUpAchievements()
    {
        DefaultTableModel achievementmodel = new AchievementModel();
        achievementmodel.addRow(new Object[]{"Testing", false});
        achievementmodel.addRow(new Object[]{"Dump", true});
        achievementmodel.addRow(new Object[]{"Jump", true});
        achievementmodel.addRow(new Object[]{"Buy", false});
        achievementTable.setModel(achievementmodel);
        achievementTable.getColumnModel().getColumn(0).setPreferredWidth(390);
        achievementTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        System.out.println(achievementTable.getColumnModel().getColumn(1).getHeaderValue());

    }

    public static class AchievementModel extends DefaultTableModel {

        public AchievementModel() {
            super(new String[]{"Achievement", "Achieved"}, 0);
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
