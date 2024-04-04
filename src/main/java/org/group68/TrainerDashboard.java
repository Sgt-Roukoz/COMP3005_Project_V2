package org.group68;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainerDashboard extends JFrame{

    private JPanel TrainerDashboard;
    private JPanel availability;
    private JPanel editAvailability;
    private JPanel viewMember;
    private JLabel mon;
    private JTextField monStart;
    private JTextField monEnd;
    private JLabel monTo;
    private JTextField tuesStart;
    private JTextField wedStart;
    private JTextField friStart;
    private JTextField satStart;
    private JTextField sunStart;
    private JLabel tuesTo;
    private JTextField thursStart;
    private JTextField tuesEnd;
    private JTextField wedEnd;
    private JTextField thursEnd;
    private JTextField friEnd;
    private JTextField satEnd;
    private JTextField sunEnd;
    private JLabel sunTo;
    private JLabel thu;
    private JLabel tue;
    private JLabel currentAvailability;
    private JLabel wed;
    private JLabel satTo;
    private JLabel friTo;
    private JLabel sun;
    private JLabel fri;
    private JLabel sat;
    private JLabel wedTo;
    private JLabel thursTo;
    private JCheckBox mondayCheckBox;
    private JComboBox monStartTime;
    private JComboBox monEndTime;
    private JCheckBox tuesdayCheckBox;
    private JComboBox tuesStartTime;
    private JComboBox tuesEndTime;
    private JCheckBox wednesdayCheckBox;
    private JCheckBox thursdayCheckBox;
    private JComboBox wedStartTime;
    private JComboBox wedEndTime;
    private JCheckBox fridayCheckBox;
    private JCheckBox saturdayCheckBox;
    private JCheckBox sundayCheckBox;
    private JComboBox thursStartTime;
    private JComboBox thursEndTime;
    private JComboBox friStartTime;
    private JComboBox friEndTime;
    private JComboBox satStartTime;
    private JComboBox satEndTime;
    private JComboBox sunStartTime;
    private JComboBox sunEndTime;
    private JButton saveButton;
    private JLabel editMonStart;
    private JLabel editTuesStart;
    private JLabel editWedStart;
    private JLabel editMonEnd;
    private JLabel editTuesEnd;
    private JLabel editWedEnd;
    private JLabel editThursEnd;
    private JLabel editFriEnd;
    private JLabel editSatEnd;
    private JLabel editSunEnd;
    private JLabel editThursStart;
    private JLabel editFriStart;
    private JLabel editSatStart;
    private JLabel editSunStart;
    private JTextField memberField;
    private JButton searchButton;
    private JLabel memberName;
    private JTextField memberIDDisplay;
    private JTextField nameDisplay;
    private JTextField emailDisplay;
    private JTextField joinDateDisplay;
    private JTextField phoneDisplay;
    private JTextField cardNumberDisplay;
    private JTextField PINDisplay;
    private JLabel memberID;
    private JLabel errorName;
    private JLabel PIN;
    private JLabel cardNumber;
    private JLabel phone;
    private JLabel joinDate;
    private JLabel email;
    private JLabel name;
    private JLabel timeError;
    private JLabel nameValue;
    private JLabel nameOfTrainer;
    private JLabel trainerPhone;
    private JLabel phoneNumber;
    private JLabel trainerEmail;
    private JLabel emailOfTrainer;
    private int trainerID;
    private Connection databaseConnection;
    private HashMap<Integer, Boolean> daysSelected;
    private ArrayList<JComboBox> startCombos;
    private ArrayList<JComboBox> endCombos;
    private ArrayList<JCheckBox> daysTicked;

    public TrainerDashboard(int trainerID, Connection connection){
        this.trainerID = trainerID;
        this.databaseConnection = connection;

        LafManager.setTheme(new DarculaTheme());
        LafManager.install();

        daysSelected = new HashMap<Integer, Boolean>(7);

        startCombos = new ArrayList<>(7);
        startCombos.add(monStartTime);
        startCombos.add(tuesStartTime);
        startCombos.add(wedStartTime);
        startCombos.add(thursStartTime);
        startCombos.add(friStartTime);
        startCombos.add(satStartTime);
        startCombos.add(sunStartTime);

        endCombos = new ArrayList<>(7);
        endCombos.add(monEndTime);
        endCombos.add(tuesEndTime);
        endCombos.add(wedEndTime);
        endCombos.add(thursEndTime);
        endCombos.add(friEndTime);
        endCombos.add(satEndTime);
        endCombos.add(sunEndTime);

        daysTicked = new ArrayList<>(7);
        daysTicked.add(mondayCheckBox);
        daysTicked.add(tuesdayCheckBox);
        daysTicked.add(wednesdayCheckBox);
        daysTicked.add(thursdayCheckBox);
        daysTicked.add(fridayCheckBox);
        daysTicked.add(saturdayCheckBox);
        daysTicked.add(sundayCheckBox);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveButtonPressed(evt);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                searchButtonPressed(evt);
            }
        });

        try {
            displayTrainerInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        errorName.setVisible(false);
        timeError.setVisible(false);

    }

    private void setDaysSelected(){
        daysSelected.clear();
        for(int i = 0; i < daysTicked.size(); i++){
            daysSelected.put(i, daysTicked.get(i).isSelected());
        }
    }

    private void saveButtonPressed(ActionEvent evt) {
        setDaysSelected();
        for(Map.Entry<Integer, Boolean> entry : daysSelected.entrySet()){
            if(entry.getValue() == true){ //If the corresponding day was ticked

            }
        }

    }

    private boolean checkTimeConsistency(JComboBox start, JComboBox end){
        String startTime = start.getSelectedItem().toString();
        String endTime = end.getSelectedItem().toString();

        String[] forStartHour = startTime.split("(?<=:)");
        String startHour = forStartHour[0]; // hour
        int sthr = Integer.parseInt(startHour.replace(':', ' ').trim());

        String[] forStartMinutes = startTime.split("(?>:)");
        String startMinute = forStartMinutes[0]; // minutes
        //Still have to check for whitespace and AM/PM after minutes
        int stmin = Integer.parseInt(startMinute);

        String[] forEndHour = endTime.split("(?<=:)");
        String endHour = forEndHour[0]; // hour
        int endhr = Integer.parseInt(endHour.replace(':', ' ').trim());

        String[] forEndMinutes = startTime.split("(?>:)");
        String endMinute = forEndMinutes[0]; // minutes
        int endmin = Integer.parseInt(endMinute);

        if(sthr > endhr){

        }
        return true;
    }

    private void displayTrainerInfo() throws SQLException{
        Statement stmt = databaseConnection.createStatement();
        String SQL = "SELECT email, phone, first_name, last_name FROM GymTrainers WHERE trainer_id = " + trainerID;
        ResultSet rs = stmt.executeQuery(SQL); // Process the result
        while(rs.next()){
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");

            nameOfTrainer.setText(firstName + " " + lastName);
            phoneNumber.setText(phone);
            emailOfTrainer.setText(email);
        }
        rs.close();
    }

    private void displayTrainerSchedule() throws SQLException {
        Statement stmt = databaseConnection.createStatement();
        String SQL = "SELECT available_day, start_time, end_time FROM TrainerAvailability WHERE trainer_id = " + trainerID;
        ResultSet rs = stmt.executeQuery(SQL); // Process the result
        while(rs.next()){

            String day = rs.getString("available_day");
            String start = rs.getString("start_time");
            String end = rs.getString("end_time");

            //Iterate through table and textfields to display

        }

        rs.close();
    }

    private void searchButtonPressed(ActionEvent evt){

    }

    public static void main(String[] args) {
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

            new TrainerDashboard(1, databaseConnection);
        }
    }

}
