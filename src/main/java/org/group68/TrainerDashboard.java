package org.group68;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.Date;

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
    private JTextField weightDisplay;
    private JTextField restingHRDisplay;
    private JLabel memberID;
    private JLabel errorName;
    private JLabel restingHR;
    private JLabel weight;
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
    private JLabel dataError;
    private JTextField bloodPressureDisplay;
    private JLabel bp;
    private int trainerID;
    private Connection databaseConnection;

    public TrainerDashboard(int trainerID, Connection connection){
        this.trainerID = trainerID;
        this.databaseConnection = connection;

        LafManager.setTheme(new DarculaTheme());
        LafManager.install();

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
            displayTrainerSchedule();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        errorName.setVisible(false);
        timeError.setVisible(false);
        dataError.setVisible(false);

    }

    private void yeetTableContents(String tobeYeeted) throws SQLException{
        Statement stmt = databaseConnection.createStatement();
        String SQL = "DELETE FROM " + tobeYeeted + " WHERE trainer_id ="+ trainerID +";";
        ResultSet rs = stmt.executeQuery(SQL);
        rs.close();
    }

    private void saveButtonPressed(ActionEvent evt) {
        try {
            yeetTableContents("TrainerAvailability");
        } catch (SQLException e) {
            timeError.setVisible(true);
            e.printStackTrace();
        }

        try {
            Statement stmt = databaseConnection.createStatement();

            String SQL;
            ResultSet resultSet;

            if(sundayCheckBox.isSelected()){
                if (!checkTimeConsistency(sunStartTime, sunEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "SUNDAY";
                    insertTrainerTuple(stmt, inputDayOfWeekString, sunStartTime, sunEndTime);
                }
            }

            if(mondayCheckBox.isSelected()){
                if (!checkTimeConsistency(monStartTime, monEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "MONDAY";
                    insertTrainerTuple(stmt, inputDayOfWeekString, monStartTime, monEndTime);
                }
            }

            if(tuesdayCheckBox.isSelected()){
                if (!checkTimeConsistency(tuesStartTime, tuesEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "TUESDAY";
                    insertTrainerTuple(stmt, inputDayOfWeekString, tuesStartTime, tuesEndTime);
                }
            }

            if(wednesdayCheckBox.isSelected()){
                if (!checkTimeConsistency(wedStartTime, wedEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "WEDNESDAY";
                    insertTrainerTuple(stmt, inputDayOfWeekString, wedStartTime, wedEndTime);
                }
            }

            if(thursdayCheckBox.isSelected()){
                if (!checkTimeConsistency(thursStartTime, thursEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "THURSDAY";
                    insertTrainerTuple(stmt, inputDayOfWeekString, thursStartTime, thursEndTime);
                }
            }

            if(fridayCheckBox.isSelected()){
                if (!checkTimeConsistency(friStartTime, friEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "FRIDAY";
                    insertTrainerTuple(stmt, inputDayOfWeekString, friStartTime, friEndTime);
                }
            }

            if(saturdayCheckBox.isSelected()){
                if (!checkTimeConsistency(satStartTime, satEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "SATURDAY";
                    insertTrainerTuple(stmt, inputDayOfWeekString, satStartTime, satEndTime);
                }
            }

        }catch (SQLException e){
            dataError.setVisible(true);
            e.printStackTrace();
        }
        errorName.setVisible(false);
        timeError.setVisible(false);
        dataError.setVisible(false);
    }

    private void insertTrainerTuple(Statement stmt, String inputDayOfWeekString, JComboBox starter, JComboBox ender) throws SQLException {
        String SQL;
        String availableDayAttribute = getAvailableDayAttribute(inputDayOfWeekString);

        String startTime = getTimeAttribute(starter);

        String endTime = getTimeAttribute(ender);

        SQL = "INSERT INTO TrainerAvailability (trainer_id, available_day, start_time, end_time) VALUES (" +
                trainerID + ", " + availableDayAttribute + ", " + startTime + ", " + endTime + ");";
        ResultSet rs = stmt.executeQuery(SQL);
        rs.close();
    }

    private String getTimeAttribute(JComboBox start) {
        String startTime = start.getSelectedItem().toString();

        String[] forStartHour = startTime.split("(?<=:)");
        String startHour = forStartHour[0]; // hour
        int sthr = Integer.parseInt(startHour.replace(':', ' ').trim());

        String[] forStartMinutes = startTime.split("(?>:)");
        String startMinute = forStartMinutes[0]; // minutes
        String starter = startMinute.substring(0, 2); //get the minutes from the part after the colon ('## AM' or '## PM')
        String startM = startMinute.substring(3);
        int stmin = Integer.parseInt(starter);

        if(startM.equals("PM")){
            sthr += 12;
        }

        String start_time = sthr + ":" + stmin + ":00";

        return start_time;
    }

    private String getAvailableDayAttribute(String inputDayOfWeekString) {
        DayOfWeek inputDayOfWeek = DayOfWeek.valueOf(inputDayOfWeekString);
        String availableDay;
        LocalDate today = LocalDate.now();
        if (today.getDayOfWeek().equals(inputDayOfWeek)) {
            availableDay = today.toString();
        } else {
            LocalDate sameDayNextWeek = today.with(TemporalAdjusters.next(inputDayOfWeek));
            LocalDate sameDayPreviousWeek = today.with(TemporalAdjusters.previous(inputDayOfWeek));
            LocalDate dateCloserToToday = (sameDayNextWeek.toEpochDay() - today.toEpochDay()) < (today.toEpochDay() - sameDayPreviousWeek.toEpochDay()) ? sameDayNextWeek : sameDayPreviousWeek;
            availableDay = dateCloserToToday.toString();
        }
        return availableDay;
    }

    private boolean checkTimeConsistency(JComboBox start, JComboBox end){
        String startTime = start.getSelectedItem().toString();
        String endTime = end.getSelectedItem().toString();

        String[] forStartHour = startTime.split("(?<=:)");
        String startHour = forStartHour[0]; // hour
        int sthr = Integer.parseInt(startHour.replace(':', ' ').trim());

        String[] forStartMinutes = startTime.split("(?>:)");
        String startMinute = forStartMinutes[0]; // minutes
        String starter = startMinute.substring(0, 2); //get the minutes from the part after the colon ('## AM' or '## PM')
        String startM = startMinute.substring(3);
        int stmin = Integer.parseInt(starter);

        String[] forEndHour = endTime.split("(?<=:)");
        String endHour = forEndHour[0];
        int endhr = Integer.parseInt(endHour.replace(':', ' ').trim());

        String[] forEndMinutes = startTime.split("(?>:)");
        String endMinute = forEndMinutes[0];
        String ender = endMinute.substring(0, 2);
        String endM = endMinute.substring(3);
        int endmin = Integer.parseInt(ender);

        if(startM.equals(endM)){
            if(sthr > endhr){
                return false;
            } else if (sthr == endhr) {
                if(stmin >= endmin){
                    return false;
                }
            }
        } else if (startM.equals("AM") && endM.equals("PM")) {

            return true;
        }else{
            return false;
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
        String SQL = "SELECT available_day, start_time, end_time FROM TrainerAvailability WHERE trainer_id =" + trainerID;
        ResultSet rs = stmt.executeQuery(SQL); // Process the result
        while(rs.next()){
            String day = rs.getDate("available_day").toString();
            String start = rs.getString("start_time");
            String end = rs.getString("end_time");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate ldate = LocalDate.parse(day, formatter);

            Calendar cal = Calendar.getInstance();
            Date date = Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            cal.setTime(date);
            int today = cal.get(Calendar.DAY_OF_WEEK);

            switch (today) {
                case 1:
                    sunStart.setText(start);
                    sunEnd.setText(end);
                    break;
                case 2:
                    monStart.setText(start);
                    monEnd.setText(end);
                    break;
                case 3:
                    tuesStart.setText(start);
                    tuesEnd.setText(end);
                case 4:
                    wedStart.setText(start);
                    wedEnd.setText(end);
                    break;
                case 5:
                    thursStart.setText(start);
                    thursEnd.setText(end);
                    break;
                case 6:
                    friStart.setText(start);
                    friEnd.setText(end);
                    break;
                case 7:
                    satStart.setText(start);
                    satEnd.setText(end);
                    break;
                default:
                    break;
            }
        }

        rs.close();
    }

    private void searchButtonPressed(ActionEvent evt){
        int memID = displayMemberInfo(memberField.getText());
        displayMemberMetrics(memID);
    }

    private int displayMemberInfo(String memberName){
        String fname = memberName.substring(0, memberName.indexOf(' '));
        String lname = memberName.substring(memberName.indexOf(' ')).trim();

        Integer memID;
        try{
            Statement stmt = databaseConnection.createStatement();

            String SQL = "SELECT member_id, email, join_date, phone, first_name, last_name FROM GymMembers WHERE first_name = " + fname + " AND last_name = " + lname;
            ResultSet rs = stmt.executeQuery(SQL); // Process the result
            Integer member_id = rs.getInt("member_id");
            memID = member_id;
            while(rs.next()){
                String email = rs.getString("email");
                String joinDate = rs.getDate("join_date").toString();
                String phone = rs.getString("phone");

                memberIDDisplay.setText(member_id.toString());
                emailDisplay.setText(email);
                joinDateDisplay.setText(joinDate);
                phoneDisplay.setText(phone);

                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                nameDisplay.setText(firstName + " " + lastName);
            }
            rs.close();
            return memID;
        }catch (SQLException e){
            e.printStackTrace();
            errorName.setVisible(true);
        }
        return 0;
    }

    private void displayMemberMetrics(int id){
        if(id == 0){
            return;
        }
        try{
            Statement stmt = databaseConnection.createStatement();
            String SQL = "SELECT member_id, weight, resting_hr, blood_pressure FROM Metrics WHERE member_id = " + id + ";";
            ResultSet rs = stmt.executeQuery(SQL); // Process the result
            while(rs.next()){
                Integer weight = rs.getInt("weight");
                Integer restingHr = rs.getInt("resting_hr");
                Integer bloodPressure = rs.getInt("blood_pressure");

                weightDisplay.setText(weight.toString());
                restingHRDisplay.setText(restingHr.toString());
                bloodPressureDisplay.setText(bloodPressure.toString());

            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
            errorName.setVisible(true);
        }
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
