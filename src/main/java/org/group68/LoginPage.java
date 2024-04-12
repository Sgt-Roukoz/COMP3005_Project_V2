package org.group68;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.*;
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
    private JLabel userName;
    private JLabel pWord;
    private JTextField userEntered;
    private JTextField passEntered;
    private JButton createLogin;
    private JLabel success;
    private Connection connection;
    public LoginPage(Connection connection){
        this.connection = connection;
        error.setVisible(false);
        invalid.setVisible(false);
        registryFields.setVisible(false);
        success.setVisible(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLoginDetails();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterRegisterDetails();
            }
        });

        createLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    /**
     * Creates a new tuple in MemberLogin and fills it with the entered username and password
     * */
    public void setCreateLogin(){
        //Remember to enable register button
    }

    /**
     * Checks if the login credentials entered match with any tuples in the specified table and opens the respective
     * dashboard. Displays an error message if no match was found.
     **/
    public void checkLoginDetails() {
        try{
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
        }catch (SQLException x){
            x.printStackTrace();
            error.setVisible(true);
        }
    }

    /**
     * Enters all the information needed for a new member to register into the relevant tables aside from MemberLogin.
     * Shows error messages if some fields were not filled, and if some inputs were invalid.
     * */
    public void enterRegisterDetails() {
        try{
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
                Integer newestMemberID = getMemberID(emailInfo, stmt);

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

                success.setVisible(true);
            }else{
                invalid.setVisible(true);
            }
        }catch (SQLException ex){
            registryFields.setVisible(true);
            ex.printStackTrace();
        }
    }

    /**
     * Enters the weight value for the member into the accumulate table.
     *
     * @param weighty the weight of the member
     * @param stmt the SQL statement used to execute the query
     * @param newestMemberID the ID of the member
     * @param sqlDate the date the weight value was logged
     * */
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

    /**
     * Enters the heart rate value for the member into the accumulate table.
     *
     * @param heartRate the heart rate of the member
     * @param stmt the SQL statement used to execute the query
     * @param newestMemberID the ID of the member
     * @param sqlDate the date the heart rate value was logged
     * */
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

    /**
     * Enters the blood pressure value for the member into the accumulate table.
     *
     * @param pressure the blood pressure of the member
     * @param stmt the SQL statement used to execute the query
     * @param newestMemberID the ID of the member
     * @param sqlDate the date the blood pressure value was logged
     * */
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

    /**
     * Gets the ID of a member by using their email, a unique identifier.
     *
     * @param emailInfo the email of the member
     * @param stmt the SQL statement used to execute the query
     *             
     * @return the Integer wrapper value of the member
     * */
    private Integer getMemberID(String emailInfo, Statement stmt) {
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

    /**
     * Enters the new member's information into the GymMembers table.
     *
     * @param fname the first name of the member
     * @param lname the last name of the member
     * @param emailInfo the email of the member
     * @param phoneNum the phone number of the member
     * @param cardNum the card number of the member
     * @param pinny the PIN of the member
     * @param today the date the member joined
     * @param stmt the SQL statement used to execute the query
     * */
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
