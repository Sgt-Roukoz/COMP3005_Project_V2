package org.group68;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class LoginPage extends JFrame{
    private JPanel Login;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel username;
    private JButton loginButton;
    private JLabel password;
    private JComboBox memberType;
    private JLabel iAm;
    private JLabel error;
    private JTextField firstname;
    private JTextField lastname;
    private JPanel register;
    private JTextField phone;
    private JTextField cardNumber;
    private JPasswordField pin;
    private JButton registerButton;
    private JLabel pinText;
    private JLabel cardnumText;
    private JLabel emailText;
    private JLabel phoneText;
    private JLabel lastnameText;
    private JLabel firstnameText;
    private JFormattedTextField restingHR;
    private JFormattedTextField bloodPressure;
    private JFormattedTextField weight;
    private JLabel hrText;
    private JLabel bpText;
    private JLabel weightText;
    private JFormattedTextField email;
    private JLabel invalid;
    private JLabel registryFields;
    private Connection connection;
    public LoginPage(Connection connection){
        this.connection = connection;
        error.setVisible(false);
        invalid.setVisible(false);
        registryFields.setVisible(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    checkLoginDetails();
                }catch (SQLException x){
                    x.printStackTrace();
                    error.setVisible(true);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    enterRegisterDetails();
                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public void checkLoginDetails() throws SQLException {
        String authority = memberType.getSelectedItem().toString();
        String user;
        String pword;
        if(!authority.equals("(please select)")){
            user = usernameField.getText();
            pword = passwordField.getText();
            Statement stmt = connection.createStatement();
            ResultSet rs;
            String SQL;
            switch (authority){
                case "Member":
                    try {
                        SQL = "SELECT member_id, member_username, member_password FROM MemberLogins WHERE member_username = " + user;
                        rs = stmt.executeQuery(SQL); // Process the result
                    }catch (Exception e){
                        error.setVisible(true);
                        break;
                    }
                    while(rs.next()){
                        int memberID = rs.getInt("member_id");
                        String memberPassword = rs.getString("member_password");
                        if(memberPassword.equals(pword)){
                            MemberDashboard newInstance = new MemberDashboard(connection,memberID);
                            newInstance.setVisible(true);
                            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                        }else{
                            error.setVisible(true);
                        }
                    }
                    // Close resources
                    rs.close();
                    break;
                case "Trainer":
                    try {
                        SQL = "SELECT trainer_id, trainer_username, trainer_password FROM TrainerLogin WHERE trainer_username = " + user;
                        rs = stmt.executeQuery(SQL); // Process the result
                    }catch (Exception e){
                        error.setVisible(true);
                        break;
                    }
                    while(rs.next()){
                        int trainerID = rs.getInt("trainer_id");
                        String trainerPassword = rs.getString("trainer_password");
                        if(trainerPassword.equals(pword)){
                            TrainerDashboard newInstance = new TrainerDashboard(trainerID, connection);
                            newInstance.setVisible(true);
                            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                        }else{
                            error.setVisible(true);
                        }
                    }
                    // Close resources
                    rs.close();
                    break;
                case "Administrator":
                    try {
                        SQL = "SELECT admin_id, admin_username, admin_password FROM AdminLogin WHERE admin_username = " + user;
                        rs = stmt.executeQuery(SQL); // Process the result
                    }catch (Exception e){
                        error.setVisible(true);
                        break;
                    }

                    while(rs.next()){
                        int adminID = rs.getInt("admin_id");
                        String adminPassword = rs.getString("admin_password");
                        if(adminPassword.equals(pword)){
                            //AdminDashboard newInstance = new AdminDashboard(adminID, connection);
                            //newInstance.setVisible(true);
                            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                        }else{
                            error.setVisible(true);
                        }
                    }
                    // Close resources
                    rs.close();
                    break;
                default:
                    error.setVisible(true);
            }
        }else{
            error.setVisible(true);
        }
    }

    public void enterRegisterDetails() throws SQLException {
        if(firstname.getText() != "" && lastname.getText() != "" && email.getText() != "@" && phone.getText() != "" && cardNumber.getText() != "" && pin.getText() != "" && restingHR.getText() != "" && weight.getText() != "" && bloodPressure.getText() != ""){
            String fname = firstname.getText();
            String lname = lastname.getText();
            String emailInfo = email.getText();
            String phoneNum = phone.getText();
            String cardNum = cardNumber.getText();
            String pinny = pin.getText();
            String heartRate = restingHR.getText();
            String weighty = weight.getText();
            String pressure = bloodPressure.getText();
            Date day = new Date();
            day.setHours(0);
            java.sql.Date SQLtoday = new java.sql.Date(day.getTime());

            Statement stmt = connection.createStatement();
            createGymMembersTuple(fname, lname, emailInfo, phoneNum, cardNum, pinny, SQLtoday, stmt);
            String SQL;
            Integer newestMemberID = getNewMemberID(emailInfo, stmt);

            if(newestMemberID == 0){
                registryFields.setVisible(false);
                return;
            }

            Date today = new Date();
            today.setHours(0);

            java.sql.Date sqlDate = new java.sql.Date(today.getTime());

            enterWeight(weighty, stmt, newestMemberID, sqlDate);

            enterHeartRate(heartRate, stmt, newestMemberID, sqlDate);

            enterBloodPressure(pressure, stmt, newestMemberID, sqlDate);

        }else{
            invalid.setVisible(true);
        }
    }

    private void enterWeight(String weighty, Statement stmt, Integer newestMemberID, java.sql.Date sqlDate) {
        String SQL;
        try{
            SQL = "UPDATE WeightAccumulate (weight, date_logged) SET weight = " + weighty + ", date_logged = " + sqlDate + " WHERE member_id = " + newestMemberID + ";";
            stmt.executeQuery(SQL);
        }catch (SQLException e){
            e.printStackTrace();
            registryFields.setVisible(true);
        }
    }

    private void enterHeartRate(String heartRate, Statement stmt, Integer newestMemberID, java.sql.Date sqlDate) {
        String SQL;
        try{
            SQL = "UPDATE RestingHRAccumulate (resting_hr, date_logged) SET weight = " + heartRate + ", date_logged = " + sqlDate + " WHERE member_id = " + newestMemberID + ";";
            stmt.executeQuery(SQL);
        }catch (SQLException e){
            e.printStackTrace();
            registryFields.setVisible(true);
        }
    }

    private void enterBloodPressure(String pressure, Statement stmt, Integer newestMemberID, java.sql.Date sqlDate) {
        String SQL;
        try{
            SQL = "UPDATE BloodPRAccumulate (blood_pr, date_logged) SET weight = " + pressure + ", date_logged = " + sqlDate + " WHERE member_id = " + newestMemberID + ";";
            stmt.executeQuery(SQL);
        }catch (SQLException e){
            e.printStackTrace();
            registryFields.setVisible(true);
        }
    }

    private Integer getNewMemberID(String emailInfo, Statement stmt) {
        Integer newestMemberID;
        try{
            String secondSQL = "SELECT member_id FROM GymMembers WHERE email = " + emailInfo + ";";
            ResultSet r = stmt.executeQuery(secondSQL);
            while(r.next()){
                newestMemberID = r.getInt("member_id");
                return newestMemberID;
            }
            r.close();
        }catch (SQLException e){
            e.printStackTrace();
            error.setVisible(true);
        }
        return 0;
    }

    private void createGymMembersTuple(String fname, String lname, String emailInfo, String phoneNum, String cardNum, String pinny, java.sql.Date today, Statement stmt) {
        ResultSet rs;
        String SQL;

        try {
            SQL = "INSERT INTO GymMembers (email, join_date, phone, first_name, last_name, card_num, pin) VALUES (" + emailInfo + ", " + today + ", " + phoneNum + ", " + fname + ", " + lname + ", " + cardNum + ", " + pinny + ");";
            rs = stmt.executeQuery(SQL); // Process the result
        }catch (SQLException e){
            error.setVisible(true);
            e.printStackTrace();
        }
    }


}
