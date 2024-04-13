package org.group68;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberRoutineDialog extends JDialog {

    int memberID;
    Connection databaseConnection;
    JScrollPane pane;
    JTable routinesTable;
    DefaultTableModel routinesModel;
    JButton selectRoutine;
    JButton cancel;

    public MemberRoutineDialog(int memberID, Connection databaseConnection)
    {
        this.memberID = memberID;
        this.databaseConnection = databaseConnection;
        routinesModel = new DefaultTableModel();
        routinesModel.addColumn("ID");
        routinesModel.addColumn("Routine");

        routinesTable = new JTable();
        routinesTable.setModel(routinesModel);
        add(routinesTable);
        selectRoutine = new JButton("Select Routine");

        getRoutines();
    }


    private void getRoutines()
    {
        try{
            Statement routinesStatement = databaseConnection.createStatement();
            String routinesMessage = "SELECT * \n" +
                    "FROM exerciseroutines";
            routinesStatement.executeQuery(routinesMessage);
            ResultSet routinesSet = routinesStatement.getResultSet();

            while (routinesSet.next())
            {
                routinesModel.addRow(new Object[]{routinesSet.getString("routine_id"), routinesSet.getString("routine_desc")});
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void showDialog(){
        setModal(true);
        setLocationRelativeTo(null);
        setSize(600,600);
        setResizable(false);
        setVisible(true);
        //return 1;
    }
}
