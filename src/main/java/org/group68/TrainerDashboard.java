package org.group68;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JPanel Routines;
    private JPanel upcoming;
    private JTextArea upcomingEvents;
    private JTable routineTable;
    private JScrollPane routineScroll;
    private JPanel editRoutines;
    private JLabel idText;
    private JTextField idEntered;
    private JTextField descEntered;
    private JLabel descText;
    private JButton saveRoutine;
    private JLabel IDErrorMessage;
    private JLabel descErrorMessage;
    private JLabel routineSucc;
    private JLabel instructions;
    private int trainerID;
    private Connection databaseConnection;
    private DefaultTableModel model;
    public TrainerDashboard(int trainerID, Connection connection){
        this.trainerID = trainerID;
        this.databaseConnection = connection;

        LafManager.setTheme(new DarculaTheme());
        LafManager.install();

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveButtonPressed();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                searchButtonPressed();
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

        mondayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(mondayCheckBox.isSelected()){
                    monStartTime.setEnabled(true);
                    monEndTime.setEnabled(true);
                }else{
                    monStartTime.setEnabled(false);
                    monEndTime.setEnabled(false);
                }
            }
        });

        tuesdayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(tuesdayCheckBox.isSelected()){
                    tuesStartTime.setEnabled(true);
                    tuesEndTime.setEnabled(true);
                }else{
                    tuesStartTime.setEnabled(false);
                    tuesEndTime.setEnabled(false);
                }
            }
        });

        wednesdayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(wednesdayCheckBox.isSelected()){
                    wedStartTime.setEnabled(true);
                    wedEndTime.setEnabled(true);
                }else{
                    wedStartTime.setEnabled(false);
                    wedEndTime.setEnabled(false);
                }
            }
        });

        thursdayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(thursdayCheckBox.isSelected()){
                    thursStartTime.setEnabled(true);
                    thursEndTime.setEnabled(true);
                }else{
                    thursStartTime.setEnabled(false);
                    thursEndTime.setEnabled(false);
                }
            }
        });

        fridayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(fridayCheckBox.isSelected()){
                    friStartTime.setEnabled(true);
                    friEndTime.setEnabled(true);
                }else{
                    friStartTime.setEnabled(false);
                    friEndTime.setEnabled(false);
                }
            }
        });

        saturdayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(saturdayCheckBox.isSelected()){
                    satStartTime.setEnabled(true);
                    satEndTime.setEnabled(true);
                }else{
                    satStartTime.setEnabled(false);
                    satEndTime.setEnabled(false);
                }
            }
        });

        sundayCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(sundayCheckBox.isSelected()){
                    sunStartTime.setEnabled(true);
                    sunEndTime.setEnabled(true);
                }else{
                    sunStartTime.setEnabled(false);
                    sunEndTime.setEnabled(false);
                }
            }
        });

        displayUpcoming();

        model = new DefaultTableModel();

        model.addColumn("Routine ID");
        model.addColumn("Routine Description");

        getRoutines(model, routineTable);

        saveRoutine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRoutine(idEntered, descEntered);
            }
        });

        IDErrorMessage.setVisible(false);
        descErrorMessage.setVisible(false);
        routineSucc.setVisible(false);

        this.setVisible(true);
        setContentPane(TrainerDashboard);
        this.pack();

    }

    /**
     * Edits a routine added by the trainer in ExerciseRoutines. Adds a new one if no routine id was specified.
     *
     * @param ID the textfield holding the routine ID entered by the trainer
     * @param description the textfield holding the routine description entered by the trainer
     * */
    private void saveRoutine(JTextField ID, JTextField description){
        String id = ID.getText();
        String desc = description.getText();
        if(desc.equals("")){
            descErrorMessage.setVisible(true);
            return;
        }

        Statement statement;
        String SQL;

        int d;
        if(!id.equals("")){
            try {
                d = Integer.parseInt(id);
                if(d < 0){
                    IDErrorMessage.setVisible(true);
                    return;
                }
                try{ //If the routine id was entered
                    statement = databaseConnection.createStatement();
                    SQL = "UPDATE ExerciseRoutines SET routine_desc = " + desc + " FROM ExerciseRoutines WHERE routine_id = " + d;
                    statement.executeQuery(SQL);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            } catch (NumberFormatException nfe) {
                IDErrorMessage.setVisible(true);
                return;
            }
        }else{
            try{ //If ID wasn't entered
                statement = databaseConnection.createStatement();
                SQL = "INSERT INTO ExerciseRoutines (routine_desc) VALUES (" + desc +")";
                statement.executeQuery(SQL);
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        getRoutines(model, routineTable);
        routineSucc.setVisible(true);
        descErrorMessage.setVisible(false);
        IDErrorMessage.setVisible(false);

    }

    /**
     * Gets the routines from the ExerciseRoutines table and displays them on the routine JTable.
     *
     * @param model the DefaultTableModel that serves as the source for the JTable
     * @param table the JTable displaying the ExerciseRoutines table to the trainer
     * */
    private void getRoutines(DefaultTableModel model, JTable table){
        try{
            Statement statement = databaseConnection.createStatement();
            String SQL = "SELECT routine_id, routine_desc FROM ExerciseRoutines";
            ResultSet rs = statement.executeQuery(SQL);
            while(rs.next()){
                String id = rs.getString("routine_id");
                String desc = rs.getString("routine_desc");
                model.addRow(new Object[]{id, desc});
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        table.setModel(model);
    }

    /**
     * Displays the upcoming classes and sessions for a trainer in a text area.
     * */
    private void displayUpcoming(){
        try{
            upcomingEvents.setText("Upcoming group classes: \n");
            Statement statement = databaseConnection.createStatement();
            String SQL = "SELECT trainer_id, class_id, class_name, room_id, booking_date, start_time, end_time FROM GroupClasses JOIN RoomBookings ON GroupClasses.room_id = RoomBookings.room_id WHERE trainer_id = " + this.trainerID;
            ResultSet rs = statement.executeQuery(SQL);
            while(rs.next()){
                String cid = rs.getString("class_id");
                String cname = rs.getString("class_name");
                String roomNum = rs.getString("room_id");
                String bookingDate = rs.getString("booking_date");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                upcomingEvents.append("Class ID: " + cid + "\n Class name: " + cname + " \n Room number: " + roomNum + " \n Date: " + bookingDate + "\n Start time: " + startTime + "\n End time: " + endTime + "\n \n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            upcomingEvents.setText("Upcoming private sessions: \n");
            Statement stmt = databaseConnection.createStatement();
            String SQL = "SELECT trainer_id, member_id, set_routine, session_date, start_time, first_name, last_name FROM PrivateSessions JOIN GymMembers ON PrivateSessions.member_id = GymMembers.member_id  WHERE trainer_id = " + this.trainerID;
            ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()){
                String fname = rs.getString("first_name");
                String lname = rs.getString("last_name");
                String setRoutine = rs.getString("set_routine");
                String sessionDate = rs.getString("session_date");
                String startTime = rs.getString("start_time");
                upcomingEvents.append("Routine: " + setRoutine + "With member: " + fname + " " + lname + " \n Date: " + sessionDate + "\n Start time: " + startTime + "\n \n");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * Deletes all contents of a given table associated with the trainer's ID. Used when trainers want to remake their
     * schedules.
     *
     * @param tobeYeeted the table from which all trainer-associated tuples are to be deleted
     * */
    private void yeetTableContents(String tobeYeeted) throws SQLException{
        Statement stmt = databaseConnection.createStatement();
        String SQL = "DELETE FROM " + tobeYeeted + " WHERE trainer_id ="+ trainerID +";";
        ResultSet rs = stmt.executeQuery(SQL);
        rs.close();
    }

    /**
     * Attempts to destroy the old schedule and save the current one entered into the JComboBoxes by the trainer into
     * TrainerSchedule.
     * */
    private void saveButtonPressed() {
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
                    insertAvailabilityTuple(stmt, inputDayOfWeekString, sunStartTime, sunEndTime);
                }
            }

            if(mondayCheckBox.isSelected()){
                if (!checkTimeConsistency(monStartTime, monEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "MONDAY";
                    insertAvailabilityTuple(stmt, inputDayOfWeekString, monStartTime, monEndTime);
                }
            }

            if(tuesdayCheckBox.isSelected()){
                if (!checkTimeConsistency(tuesStartTime, tuesEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "TUESDAY";
                    insertAvailabilityTuple(stmt, inputDayOfWeekString, tuesStartTime, tuesEndTime);
                }
            }

            if(wednesdayCheckBox.isSelected()){
                if (!checkTimeConsistency(wedStartTime, wedEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "WEDNESDAY";
                    insertAvailabilityTuple(stmt, inputDayOfWeekString, wedStartTime, wedEndTime);
                }
            }

            if(thursdayCheckBox.isSelected()){
                if (!checkTimeConsistency(thursStartTime, thursEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "THURSDAY";
                    insertAvailabilityTuple(stmt, inputDayOfWeekString, thursStartTime, thursEndTime);
                }
            }

            if(fridayCheckBox.isSelected()){
                if (!checkTimeConsistency(friStartTime, friEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "FRIDAY";
                    insertAvailabilityTuple(stmt, inputDayOfWeekString, friStartTime, friEndTime);
                }
            }

            if(saturdayCheckBox.isSelected()){
                if (!checkTimeConsistency(satStartTime, satEndTime)){
                    timeError.setVisible(true);
                }else{
                    String inputDayOfWeekString = "SATURDAY";
                    insertAvailabilityTuple(stmt, inputDayOfWeekString, satStartTime, satEndTime);
                }
            }

        }catch (SQLException e){
            dataError.setVisible(true);
            e.printStackTrace();
        }
        errorName.setVisible(false);
        dataError.setVisible(false);
    }

    /**
     * Enters a specific days' hours into TrainerSchedule as chosen by the trainer.
     *
     * @param stmt the SQL statement used to execute the query
     * @param inputDayOfWeekString the String representing the day of the week chosen by the trainer
     * @param starter the JComboBox containing the start time for the workday
     * @param ender the JComboBox containing the end time for the workday
     * */
    private void insertAvailabilityTuple(Statement stmt, String inputDayOfWeekString, JComboBox starter, JComboBox ender) throws SQLException {
        String SQL;
        String availableDayAttribute = getAvailableDayAttribute(inputDayOfWeekString);

        String startTime = getTimeAttribute(starter);

        String endTime = getTimeAttribute(ender);

        SQL = "INSERT INTO TrainerAvailability (trainer_id, available_day, start_time, end_time) VALUES (" +
                trainerID + ", " + availableDayAttribute + ", " + startTime + ", " + endTime;
        ResultSet rs = stmt.executeQuery(SQL);
        rs.close();
    }

    /**
     * Reformats the time string in a JComboBox to a 24-hour clock format more suitable for SQL, and returns the
     * resulting String.
     *
     * @param source the JComboBox whose selected time needs to be reformatted
     *
     * @return the String containing the time in a 24-hour clock format, ready to be inserted into a SQL table
     * */
    private String getTimeAttribute(JComboBox source) {
        String time = source.getSelectedItem().toString();

        String[] forHour = time.split("(?<=:)");
        String hour = forHour[0]; // hour
        int hr = Integer.parseInt(hour.replace(':', ' ').trim());

        String[] forMinutes = time.split("(?>:)");
        String minute = forMinutes[0]; // minutes
        String er = minute.substring(0, 2); //get the minutes from the part after the colon ('## AM' or '## PM')
        String M = minute.substring(3);
        int min = Integer.parseInt(er);

        if(M.equals("PM")){
            hr += 12;
        }

        String result = hr + ":" + min + ":00";

        return result;
    }

    /**
     * Takes a String containing a day of the week in capital letters and returns a date String formatted as yyyy-MM-dd
     * to be inserted into TrainerAvailability. The date returned is the closest date to the input parameter.
     *
     * @param inputDayOfWeekString a day of the week in capital letters
     *
     * @return the String representation of the date
     * */
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

    /**
     * Takes two JComboBoxes and determines whether their contents are possible as start and end times.
     *
     * @param start the JComboBox containing the start time
     * @param end the JComboBox containing the end time
     * @return true if the latter argument is at a later time than the former, false otherwise
     * */
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

    /**
     * Displays the current trainer's basic info in the top of the JPanel by pulling it from the GymTrainers table.
     * */
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

    /**
     * Displays the current trainer's schedule in the availability JPanel by pulling it from the TrainerAvailability table.
     * */
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

    /**
     * Simply calls the displayMemberInfo and displayMemberMetrics methods whenever the searchButton is clicked
     * */
    private void searchButtonPressed(){
        int memID = displayMemberInfo(memberField.getText());
        displayMemberMetrics(memID);
        memberField.setText("");
    }

    /**
     * Displays the member's administrative information in the viewMembers JPanel when the trainer searches for the
     * member's name by pulling it from the GymMembers table.
     *
     * @param memberName the String entered into the memberField JTextField
     * @return the integer value of the member ID
     * */
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

    /**
     * Displays the member's metrics in the viewMembers JPanel when the trainer searches for the member's name by
     * pulling it from the Metrics table.
     *
     * @param id the member id
     * */
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

//        String url = "jdbc:postgresql://localhost:5432/COMP3005";
//        String user = "postgres";
//        String password = "TFERPLGK";





        try {
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://localhost:5432/COMP3005";
                String user = "postgres";
                String password = "TFERPLGK";
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement stmt = connection.createStatement();
                 if (connection != null){ System.out.println("Connected Successfully");

                     new TrainerDashboard(1, connection);
                 }
                else System.out.println("Connection Failed");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }


}
