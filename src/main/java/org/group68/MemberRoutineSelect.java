/**
 * Dialog class for member routine selection
 */
package org.group68;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberRoutineSelect extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable routinesTable;
    private JTextField searchField;
    private Connection databaseConnection;
    private DefaultTableModel routinesModel;
    private int memberID;

    /**
     * Constructor for RoutineSelect
     * @param memberID id of member selecting
     * @param databaseConnection connection to the database
     */
    public MemberRoutineSelect(int memberID, Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
        this.memberID = memberID;
        routinesModel = new DefaultTableModel();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400,400);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Select Routine");

        routinesModel = new DefaultTableModel();
        routinesModel.addColumn("ID");
        routinesModel.addColumn("Routine");
        routinesTable.setModel(routinesModel);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        getRoutines();
        routinesTable.getColumnModel().getColumn(0).setPreferredWidth(5);
        routinesTable.setDefaultEditor(Object.class, null);
        setUpSearch();
        setVisible(true);
    }

    /**
     * Sets up search feature of routines table
     */
    private void setUpSearch()
    {
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(routinesTable.getModel());
        routinesTable.setRowSorter(rowSorter);

        searchField.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

        routinesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                buttonOK.setEnabled(!routinesTable.getSelectionModel().isSelectionEmpty());
            }
        });
    }

    /**
     * Function triggers when OK button is pressed, sends an update to the database
     */
    private void onOK() {
        try {
            Statement setRoutine = databaseConnection.createStatement();
            String msg = "UPDATE tracker\n" +
                    "SET exercise_routine = " + routinesTable.getValueAt(routinesTable.getSelectedRow(), 0) + "\n" +
                    "WHERE member_id = " + memberID ;
            setRoutine.execute(msg);
            setRoutine.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dispose();
    }

    /**
     * Triggers on cancel button, disposes of window
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Populates routines table
     */
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
}
