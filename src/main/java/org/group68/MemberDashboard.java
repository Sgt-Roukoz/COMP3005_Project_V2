package org.group68;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import net.time4j.ClockUnit;
import net.time4j.PlainTime;
import net.time4j.range.ChronoInterval;
import net.time4j.range.ClockInterval;
import net.time4j.range.IntervalCollection;

public class MemberDashboard extends JFrame {

    private Connection databaseConnection;

    //panels
    private JPanel mainPane;
    private JPanel metricsPanel;

    //metrics icons
    private JLabel hrIcon;
    private JLabel weightIcon;
    private JLabel bpIcon;

    //
    private JButton achievementReset;
    private JButton achievementApply;
    private JTable achievementTable;
    private JButton addRoutine;
    private JTabbedPane dashboardPane;
    private JTable goaltable;
    private JButton removeGoal;
    private JButton addGoal;

    //Schedule Page Variables
    private JTable upcomingClasses;
    private JTable upcomingSessions;
    private JComboBox<String> classSelector;
    private JComboBox<String> sessionDaySelector;
    private JComboBox<String> trainerSelector;
    private JTextField dateField;
    private JTextField endtimeField;
    private JTextField starttimeField;
    private JComboBox sessionTimeSelector;
    private JButton privateSessionBook;
    private JButton cancelSession;
    private JButton rescheduleSession;
    private JButton cancelClass;
    private JButton joinClassButton;
    private JComboBox routineSelector;
    private JButton paySelectedBillButton;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField currentPass;
    private JPasswordField newPass;
    private JPasswordField confirmPass;
    private JButton changePasswordButton;
    private JButton changeEmailButton;
    private JButton changeUsernameButton;
    private JTextField hrfield;
    private JTextField bpfield;
    private JTextField weightfield;
    private JButton logButton;
    private JTextField avgHR;
    private JTextField avgWeight;
    private JTextField minHR;
    private JTextField maxHR;
    private JTextField minWeight;
    private JTextField maxWeight;
    private JLabel firstName;
    private JLabel lastName;
    private JButton applyGoals;
    private JLabel logStatus;
    private JButton resetButton;
    private JTextArea routineTextArea;
    private JPasswordField cardPinField;
    private JTextField cardNumField;
    private JButton cardButton;
    private Map<Integer, GroupClass> availableClasses;

    //models
    private DefaultTableModel classesModel, sessionsModel, achievementmodel, goalmodel;

    //Member Data
    private int memberID;


    public MemberDashboard(Connection databaseConnection, int memberID) {
        this.databaseConnection = databaseConnection;
        this.memberID = memberID;
        sessionTimeSelector.setEnabled(false);
        availableClasses = new HashMap<>();

        LocalDate localDate = LocalDate.now();
        LocalDate firstDayOfNextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        List<LocalDate> remainingDays = localDate.datesUntil(firstDayOfNextWeek)
                .collect(Collectors.toList());
        Vector<String> days = new Vector<>();
        for (LocalDate date : remainingDays)
        {
            String day = date.getDayOfWeek().toString();
            day = day.charAt(0) + day.substring(1).toLowerCase();
            days.add(day);
        }

        sessionDaySelector.setModel(new DefaultComboBoxModel<>(days));
        sessionDaySelector.setSelectedIndex(-1);
        setUpInitialValues();
        setTitle("Member Dashboard");
        setContentPane(mainPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 900);
        setLocationRelativeTo(null);
        LafManager.install(new DarculaTheme()); //using custom theme from LaFManager
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
                if (cb == null || cb.getSelectedItem() == null) return;
                String[] itemSelected = ((String) cb.getSelectedItem()).split(" ");
                GroupClass selectedClass = availableClasses.get(Integer.valueOf(itemSelected[0]));
                dateField.setText(selectedClass.date());
                starttimeField.setText(selectedClass.start_time());
                endtimeField.setText(selectedClass.end_time());
            }
        });

        sessionDaySelector.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String daySelected = (String) cb.getSelectedItem();
            if (sessionTimeSelector.getSelectedItem() != null)
            {
                sessionTimeSelector.setEnabled(true);
                trainerSelector.removeAllItems();
                GetAvailableTrainers((String) sessionTimeSelector.getSelectedItem(), daySelected);
            } else sessionTimeSelector.setEnabled(false);

        });

        sessionTimeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String timeSelected = (String) cb.getSelectedItem();
                trainerSelector.removeAllItems();
                GetAvailableTrainers(timeSelected, (String) sessionDaySelector.getSelectedItem());
            }
        });

        cancelClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = upcomingClasses.getSelectedRow();

                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to leave this class?",
                        "Class Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (selectedOption == 0) removeMemberFromClass(Integer.parseInt((String) upcomingClasses.getModel().getValueAt(selectedRow, 0)));
            }
        });
        joinClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] items = classSelector.getSelectedItem().toString().split(" ");

                joinSelectedClass(Integer.valueOf(items[0]));
            }
        });

        privateSessionBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int trainerid = Integer.parseInt(trainerSelector.getSelectedItem().toString().split(" ")[0]);
                LocalDate day = convertToDate(sessionDaySelector.getSelectedItem().toString());
                String routine = routineSelector.getSelectedItem().toString();
                String time = sessionTimeSelector.getSelectedItem().toString();
                LocalTime start_time = LocalTime.parse(time);

                bookNewSession(trainerid, routine, day, start_time);
                setUpSessionsValues();
            }
        });

        addRoutine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MemberRoutineSelect(memberID, databaseConnection);
                setRoutine();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        rescheduleSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = upcomingSessions.getSelectedRow();

                new MemberSessionReschedule( Integer.parseInt((String)upcomingSessions.getModel().getValueAt(row, 0)),
                        Integer.parseInt((String)upcomingSessions.getModel().getValueAt(row, 1)),(String)upcomingSessions.getModel().getValueAt(row, 2) ,databaseConnection);
                setUpSessionsValues();

            }
        });

        cancelSession.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = upcomingSessions.getSelectedRow();

                int session_id = Integer.parseInt((String)upcomingSessions.getModel().getValueAt(row, 0));

                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to cancel this session?",
                        "Choose",
                        JOptionPane.YES_NO_OPTION);

                try {
                    Statement cancelSession = databaseConnection.createStatement();
                    String msg = "DELETE FROM privatesessions\n" +
                            "WHERE session_id = " + session_id;
                    cancelSession.execute(msg);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                setUpSessionsValues();
            }
        });

        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (isNumeric(hrfield.getText()) && bpfield.getText().matches("[0-9]{2,3}/[0-9]{2}") && isNumeric(weightfield.getText())) {
                    logStatus.setText("");
                    try{

                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to log these values?",
                                "Choose",
                                JOptionPane.YES_NO_OPTION);

                        if (selectedOption == 1) return;
                        Statement metrics = databaseConnection.createStatement();
                        String msg = "INSERT INTO weightaccumulate\n" +
                                "VALUES(" + memberID + ", '" + weightfield.getText() + "' , CURRENT_DATE);\n" +
                                "INSERT INTO restinghraccumulate\n" +
                                "VALUES(" + memberID + ", '" + hrfield.getText() + "' , CURRENT_DATE);\n" +
                                "INSERT INTO bloodpraccumulate\n" +
                                "VALUES(" + memberID + ", '" + bpfield.getText() + "' , CURRENT_DATE);";
                        metrics.execute(msg);
                    } catch (SQLException ex) {
                        logStatus.setText("Something went wrong");
                    }

                    resetMetrics();
                }
                else logStatus.setText("Values invalid");
            }
        });
        addGoal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goalmodel.addRow(new Object[]{"",false,""});
            }
        });

        removeGoal.addActionListener(new ActionListener() { //remove selected goal
            @Override
            public void actionPerformed(ActionEvent e) {

                String goal = (String) goaltable.getValueAt(goaltable.getSelectedRow(), 0);

                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want remove this goal?",
                        "Choose",
                        JOptionPane.YES_NO_OPTION);

                if (selectedOption == 1) return;

                try {
                    Statement removeGoal = databaseConnection.createStatement();
                    String msg = "DELETE FROM achievements\n" +
                            "WHERE member_id = " + memberID + " AND description = '" + goal + "';";
                    removeGoal.execute(msg);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                setUpAchievements();
            }
        });

        applyGoals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to apply these changes?\n" +
                                "This could reset the achieved status",
                        "Choose",
                        JOptionPane.YES_NO_OPTION);

                if (selectedOption == 1) return;

                try {
                    Statement getGoals = databaseConnection.createStatement();
                    String msg = "SELECT * \n" +
                            "FROM achievements\n" +
                            "WHERE member_id = " + memberID;
                    getGoals.executeQuery(msg);

                    ResultSet goalSet = getGoals.getResultSet();

                    int row = 0;
                    while (goalSet.next())
                    {
                        String goal = (String) goalmodel.getValueAt(row, 0);
                        if (!goal.equals(goalSet.getString("description")))
                        {
                            Statement updateGoal = databaseConnection.createStatement();
                            String setmsg = "UPDATE achievements\n" +
                                    "SET achieved = FALSE, date_achieved = null, description = '" + goal + "'\n" +
                                    "WHERE member_id = " + memberID + " AND description = '" + goalSet.getString("description") + "'";
                            updateGoal.execute(setmsg);
                            updateGoal.close();
                        }
                        row++;
                    }
                    for (; row < goalmodel.getRowCount(); row++)
                    {
                        String goal = (String) goalmodel.getValueAt(row, 0);
                        Statement addGoal = databaseConnection.createStatement();
                        String addmsg = "INSERT INTO achievements\n" +
                                "VALUES(" + memberID + ", '" + goal + "', false, null)";
                        addGoal.execute(addmsg);
                        addGoal.close();
                    }

                    setUpAchievements();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpAchievements();
            }
        });
        changeEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().toString();
                String emailregex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|" +
                        "\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\" +
                        "x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]" +
                        "*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|" +
                        "[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-" +
                        "\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

                if (email.matches(emailregex))
                {
                    try {
                        Statement checkExists = databaseConnection.createStatement();
                        String existsmsg = "SELECT EXISTS(\n" +
                                "    SELECT 1 \n" +
                                "   \tFROM gymmembers \n" +
                                "    WHERE email = '" + email + "'\n" +
                                "  );";
                        checkExists.executeQuery(existsmsg);
                        ResultSet set = checkExists.getResultSet();
                        set.next();
                        if (set.getBoolean(1)) {
                            JOptionPane.showMessageDialog(null, "Invalid Email, Is it already registered?");
                            return;
                        }

                        Statement emailChange = databaseConnection.createStatement();
                        String emailmsg =  "UPDATE gymmembers\n" +
                                "SET email = '" + email + "'\n" +
                                "WHERE member_id = " + memberID;
                        emailChange.execute(emailmsg);
                        emailChange.close();

                        emailField.setText("");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //change card information button listener
        cardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNum = cardNumField.getText();
                String cardPin = new String(cardPinField.getPassword());

                String cardRegex = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}" +
                        "|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";

                System.out.println(cardNum);
                System.out.println(cardPin.matches("^[0-9]{3,4}$"));

                if (!cardNum.matches(cardRegex) || !cardPin.matches("^[0-9]{3,4}$"))
                {
                    JOptionPane.showMessageDialog(null, "Invalid Card Information");
                    return;
                }

                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to change this information?",
                        "Choose",
                        JOptionPane.YES_NO_OPTION);

                if (selectedOption == 1) return;

                try {
                    Statement cardChange = databaseConnection.createStatement();
                    String cardMsg =  "UPDATE gymmembers\n" +
                            "SET card_num = '" + cardNum + "', pin = " + cardPin + "\n" +
                            "WHERE member_id = " + memberID;
                    cardChange.execute(cardMsg);
                    cardChange.close();

                    cardNumField.setText("");
                    cardPinField.setText("");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //change password button
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPassword = new String(currentPass.getPassword());
                String newPassword = new String(newPass.getPassword());
                String newPasswordConfirm = new String(confirmPass.getPassword());

                if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()) return;

                try{
                    Statement statement = databaseConnection.createStatement();
                    statement.executeQuery("SELECT * FROM memberlogins WHERE member_id = " + memberID);
                    ResultSet resultSet = statement.getResultSet();
                    resultSet.next();

                    if (!currentPassword.equals(resultSet.getString("member_password")))
                    {
                        JOptionPane.showMessageDialog(null, "Current Password doesn't match");
                        statement.close();
                        resultSet.close();
                        return;
                    }
                    else if (newPassword.equals(resultSet.getString("member_password")))
                    {
                        JOptionPane.showMessageDialog(null, "New password must be different");
                        statement.close();
                        resultSet.close();
                        return;
                    }
                    else if (!newPassword.equals(newPasswordConfirm))
                    {
                        JOptionPane.showMessageDialog(null, "Ensure that new passwords match");
                        statement.close();
                        resultSet.close();
                        return;
                    }

                    statement.close();
                    resultSet.close();

                    int selectedOption = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to change your password?",
                            "Choose",
                            JOptionPane.YES_NO_OPTION);

                    if (selectedOption == 1) return;

                    Statement updatePass = databaseConnection.createStatement();
                    String updatePasss = "UPDATE memberlogins\n" +
                            "SET member_password = '" + newPassword + "'\n" +
                            "WHERE member_id = " + memberID;
                    updatePass.execute(updatePasss);

                    currentPass.setText("");
                    newPass.setText("");
                    confirmPass.setText("");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        //change current member's username
        changeUsernameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newusername = usernameField.getText();

                if (newusername.isEmpty()) return;

                try {
                    Statement exists = databaseConnection.createStatement();
                    String existsmsg = "SELECT EXISTS(\n" +
                            "    SELECT 1 \n" +
                            "   \tFROM memberlogins \n" +
                            "    WHERE member_username = '" + newusername + "'\n" +
                            "  );";
                    exists.executeQuery(existsmsg);
                    ResultSet set = exists.getResultSet();
                    set.next();

                    if (set.getBoolean(1)) {
                        JOptionPane.showMessageDialog(null, "Username already taken");
                        exists.close();
                        set.close();
                        return;
                    }
                    exists.close();
                    set.close();

                    int selectedOption = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to change your username?",
                            "Choose",
                            JOptionPane.YES_NO_OPTION);

                    if (selectedOption == 1) return;

                    Statement updateUser = databaseConnection.createStatement();
                    String updateUserr = "UPDATE memberlogins\n" +
                            "SET member_username = '" + newusername + "'\n" +
                            "WHERE member_id = " + memberID;
                    updateUser.execute(updateUserr);

                    updateUser.close();
                    usernameField.setText("");

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    /**
     * Checks if string is a number
     * @param strNum string being checked
     * @return Returns true if string is a number, false otherwise
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Book new private session with selected trainer within start_time
     *
     * @param trainerid trainer to do session with
     * @param routine the requested routine type
     * @param day       the date of the session
     * @param startTime start time of the session
     */
    private void bookNewSession(int trainerid, String routine, LocalDate day, LocalTime startTime) {
        try {
            Statement bookSession = databaseConnection.createStatement();
            String bookQuery = "INSERT INTO privatesessions(member_id, trainer_id, set_routine, start_time, session_date)\n" +
                    "VALUES(" + memberID + ", " + trainerid + ", '" + routine + "' , '" + startTime + "' , '" + day + "')";
            bookSession.execute(bookQuery);
            bookSession.close();
            GetAvailableTrainers(sessionTimeSelector.getSelectedItem().toString(), sessionDaySelector.getSelectedItem().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lets member join selected class
     * @param class_id class selected to join
     */
    private void joinSelectedClass(Integer class_id) {
        try {
            Statement joinClassStatement = databaseConnection.createStatement();
            String joinMessage = "INSERT INTO classmembers\n" +
                    "VALUES(" + class_id + ", " + memberID + ")";
            joinClassStatement.execute(joinMessage);
            joinClassStatement.close();
            resetClassSection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void resetClassSection() {
        setUpSessionsClasses();
        GetAvailableClasses();
    }

    /**
     * Remove member from registered class
     * @param class_id class to be removed from
     */
    private void removeMemberFromClass(int class_id){
        try {
            Statement removeClassStatement = databaseConnection.createStatement();
            String removeMessage = "DELETE FROM classmembers\n" +
                    "WHERE class_id = " + class_id + " AND member_id = " + memberID;
            removeClassStatement.execute(removeMessage);
            removeClassStatement.close();
            resetClassSection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds trainers that are available at the selected day and time
     * @param timeSelected the selected time
     * @param daySelected the selected day
     */
    private void GetAvailableTrainers(String timeSelected, String daySelected) {
        trainerSelector.removeAllItems();
        LocalDate day = convertToDate(daySelected);
        LocalTime time = LocalTime.parse(timeSelected);
        Map<Integer, IntervalCollection<PlainTime>> trainerIntervals = new HashMap<>();
        try {
            //grab trainers that are available during the selected day
            Statement initTrainer = databaseConnection.createStatement();
            String tmessage = "SELECT gymtrainers.trainer_id, available_day, start_time, end_time\n" +
                    "FROM traineravailability JOIN gymtrainers ON traineravailability.trainer_id = gymtrainers.trainer_id\n" +
                    "WHERE available_day = '" + day + "' AND start_time <= '" + time + "' AND end_time >= '" + time.plusHours(1) + "'";
            initTrainer.executeQuery(tmessage);
            ResultSet trainerSet = initTrainer.getResultSet();

            while (trainerSet.next())
            {
                //trainerSelector.addItem(trainerSet.getString("first_name") + " " + trainerSet.getString("last_name"));
                PlainTime startTime = PlainTime.of(Integer.parseInt(trainerSet.getString("start_time").split(":")[0]),
                        Integer.parseInt(trainerSet.getString("start_time").split(":")[1]));
                PlainTime endTime = PlainTime.of(Integer.parseInt(trainerSet.getString("end_time").split(":")[0]),
                        Integer.parseInt(trainerSet.getString("end_time").split(":")[1]));
                trainerIntervals.put(trainerSet.getInt("trainer_id"), IntervalCollection.onClockAxis().plus(ClockInterval.between(startTime, endTime)));
            }
            trainerSet.close();
            initTrainer.close();

            //check if trainers have group classes scheduled during this time, and remove that from availability
            Statement taken = databaseConnection.createStatement();
            String takenMessage = "SELECT trainer_id, booking_date, start_time, end_time\n" +
                    "FROM groupclasses JOIN roombookings ON groupclasses.room_id = roombookings.room_id\n" +
                    "WHERE booking_date = '" + day + "'";
            taken.executeQuery(takenMessage);
            ResultSet takenSet = taken.getResultSet();
            Map<Integer, List<ChronoInterval<PlainTime>>> trainerAvailability = new HashMap<>();

            while (takenSet.next())
            {
                int currentTrainer = takenSet.getInt("trainer_id");
                if (trainerIntervals.containsKey(currentTrainer))
                {
                    PlainTime slotStartTime = PlainTime.of(Integer.parseInt(takenSet.getString("start_time").split(":")[0]),
                            Integer.parseInt(takenSet.getString("start_time").split(":")[1]));
                    PlainTime slotEndTime = PlainTime.of(Integer.parseInt(takenSet.getString("end_time").split(":")[0]),
                            Integer.parseInt(takenSet.getString("end_time").split(":")[1]));
                    ClockInterval slot = ClockInterval.between(slotStartTime, slotEndTime);
                    trainerIntervals.put(currentTrainer, trainerIntervals.get(currentTrainer).minus(slot));
                }
            }

            Statement sessions = databaseConnection.createStatement();
            String sessionMessage = "SELECT trainer_id, start_time\n" +
                    "FROM privatesessions\n" +
                    "WHERE session_date = '" + day + "'";
            sessions.executeQuery(sessionMessage);
            ResultSet sessionSet = sessions.getResultSet();
            //check if trainers have private sessions during this time
            while (sessionSet.next())
            {
                int currentTrainer = sessionSet.getInt("trainer_id");
                if (trainerIntervals.containsKey(currentTrainer))
                {
                    PlainTime slotStartTime = PlainTime.of(Integer.parseInt(sessionSet.getString("start_time").split(":")[0]),
                            Integer.parseInt(sessionSet.getString("start_time").split(":")[1]));
                    PlainTime slotEndTime = slotStartTime.plus(1, ClockUnit.HOURS);
                    ClockInterval slot = ClockInterval.between(slotStartTime, slotEndTime);
                    trainerIntervals.put(currentTrainer, trainerIntervals.get(currentTrainer).minus(slot));
                }
            }

            //generate the availability intervals for each viable trainer
            trainerIntervals.keySet().forEach(k -> trainerAvailability.putIfAbsent(k, trainerIntervals.get(k).getIntervals()));

            taken.close();
            takenSet.close();

            //finally, check if chosen session time fits in any intervals of viable trainers
            ClockInterval chosenTime = ClockInterval.between(time.plusSeconds(1), time.plusMinutes(59).plusSeconds(59));

            for (Map.Entry<Integer, List<ChronoInterval<PlainTime>>> entry : trainerAvailability.entrySet())
            {
                for (ChronoInterval<PlainTime> interval : entry.getValue())
                {
                    if (((ClockInterval) interval).encloses(chosenTime))
                    {
                        Statement chosenTrainer = databaseConnection.createStatement();

                        String getTrainer = "SELECT first_name, last_name\n" +
                                "FROM gymtrainers\n" +
                                "WHERE trainer_id = " + entry.getKey();
                        chosenTrainer.executeQuery(getTrainer);
                        ResultSet chosenSet = chosenTrainer.getResultSet();
                        chosenSet.next();

                        String trainerName = entry.getKey() + " " + chosenSet.getString("first_name") + " " + chosenSet.getString("last_name");
                        trainerSelector.addItem(trainerName);
                        chosenSet.close();
                        chosenTrainer.close();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //trainers available in those days
        //classes during this day
        //remove ranges from trainers
        //check if time selected in the final calculated ranges
        //set trainers in trainerselector
    }

    /**
     * Convert string Day to the closest week date
     * @param daySelected selected day as string
     * @return
     */
    private LocalDate convertToDate(String daySelected)
    {
        DayOfWeek inputDayOfWeek = DayOfWeek.valueOf(daySelected.toUpperCase());

        LocalDate today = LocalDate.now();
        return (today.with(TemporalAdjusters.next(inputDayOfWeek)));
    }

    /**
     * Setting up initial metrics for member dashboard
     */
    private void setUpInitialValues() {
        setMetricNames();
        resetMetrics();
        setUpAchievements();
        setUpSessionsClasses();
        setRoutine();
    }

    private void setMetricNames() {
        try {
            Statement statement = databaseConnection.createStatement();
            statement.executeQuery("SELECT * FROM gymmembers WHERE member_id = " + memberID);
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();

            firstName.setText(resultSet.getString("first_name"));
            lastName.setText(resultSet.getString("last_name"));
            firstName.setFont(new Font("Jetbrains Mono", Font.BOLD, 18));
            lastName.setFont(new Font("Jetbrains Mono", Font.BOLD, 18));
            routineTextArea.setFont(new Font("Jetbrains Mono", Font.BOLD, 16));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetMetrics() {
        try {
            Statement statement = databaseConnection.createStatement();
            statement.executeQuery("SELECT * FROM metrics WHERE member_id = " + memberID);
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            hrIcon.setText(resultSet.getString("resting_hr") + " bpm");
            hrIcon.setFont(new Font("Jetbrains Mono", Font.BOLD, 15));
            weightIcon.setText(resultSet.getString("weight") + " lb");
            weightIcon.setFont(new Font("Jetbrains Mono", Font.BOLD, 15));
            bpIcon.setText(resultSet.getString("blood_pressure") + " mmHg");
            bpIcon.setFont(new Font("Jetbrains Mono", Font.BOLD, 15));
            statement.close();
            resultSet.close();

            statement = databaseConnection.createStatement();
            String statMsg = "SELECT *\n" +
                    "FROM memberstatistics\n" +
                    "WHERE member_id = " + memberID;
            statement.executeQuery(statMsg);
            ResultSet statSet = statement.getResultSet();
            statSet.next();
            avgHR.setText(statSet.getString("avg_resting_hr"));
            maxHR.setText(statSet.getString("max_resting_hr"));
            minHR.setText(statSet.getString("min_resting_hr"));
            avgWeight.setText(statSet.getString("avg_weight"));
            maxWeight.setText(statSet.getString("max_weight"));
            minWeight.setText(statSet.getString("min_weight"));

            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Populates Upcoming Sessions and Upcoming Classes tables
     */
    private void setUpClassesValues() {
        try {
            Statement classesStatement = databaseConnection.createStatement();
            String message = "WITH FullClassInfo(class_id, class_name, booking_date, start_time, end_time) AS (\n" +
                    "\tSELECT class_id, class_name, booking_date, start_time, end_time\n" +
                    "\tFROM groupclasses JOIN roombookings ON groupclasses.room_id = roombookings.room_id\n" +
                    ")\n" +
                    "SELECT FullClassInfo.class_id, class_name, booking_date, start_time, end_time\n" +
                    "FROM FullClassInfo JOIN ClassMembers ON classmembers.class_id = FullClassInfo.class_id\n" +
                    "WHERE member_id = " + memberID; // query that pulls class info that member has registered in
            classesStatement.executeQuery(message);
            ResultSet classesResultSet = classesStatement.getResultSet();
            while (classesResultSet.next()) {
                classesModel.addRow(new Object[]{classesResultSet.getString("class_id"), classesResultSet.getString("class_name"), classesResultSet.getString("booking_date"),
                        classesResultSet.getString("start_time"), classesResultSet.getString("end_time")});
            }
            classesResultSet.close();
            classesStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        GetAvailableClasses();

    }

    /**
     * Get upcoming sessions booked by member
     */
    private void setUpSessionsValues() {
        sessionsModel.setRowCount(0);
        try {
            Statement sessionsStatement = databaseConnection.createStatement();
            String message = "SELECT * \n" +
                    "FROM privatesessions JOIN gymtrainers ON privatesessions.trainer_id = gymtrainers.trainer_id\n" +
                    "WHERE session_date >= CURRENT_DATE AND member_id = " + memberID; // query that pulls class info that member has registered in

            sessionsStatement.executeQuery(message);
            ResultSet sessionsStatementResultSet = sessionsStatement.getResultSet();
            while (sessionsStatementResultSet.next()) {
                sessionsModel.addRow(new String[]{sessionsStatementResultSet.getString("session_id"),
                        sessionsStatementResultSet.getString("trainer_id"),
                        sessionsStatementResultSet.getString("first_name") + " " + sessionsStatementResultSet.getString("last_name"),
                        sessionsStatementResultSet.getString("set_routine"),
                        sessionsStatementResultSet.getString("session_date"),
                        LocalTime.parse(sessionsStatementResultSet.getString("start_time")).toString(),
                        LocalTime.parse(sessionsStatementResultSet.getString("start_time")).plusHours(1).toString()});
            }
            sessionsStatementResultSet.close();
            sessionsStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get classes that will occur later
     */
    private void GetAvailableClasses() {
        try {
            availableClasses.clear();
            classSelector.removeAllItems();
            Statement statement = databaseConnection.createStatement();
            String message = "WITH FullClassInfo(class_id, class_name, booking_date, start_time, end_time) AS (\n" +
                    "    SELECT class_id, class_name, booking_date, start_time, end_time\n" +
                    "    FROM (groupclasses JOIN roombookings ON groupclasses.room_id = roombookings.room_id)\n" +
                    ")\n" +
                    "SELECT FullClassInfo.class_id, class_name, booking_date, start_time, end_time\n" +
                    "FROM FullClassInfo FULL OUTER JOIN ClassMembers ON classmembers.class_id = FullClassInfo.class_id\n" +
                    "WHERE (member_id IS null OR member_id !=" + memberID + ") AND booking_date > '" + LocalDate.now() + "'"; // query that pulls class info that member has NOT registered in

            statement.executeQuery(message);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                GroupClass newClass = new GroupClass(resultSet.getInt("class_id"), resultSet.getString("class_name"),
                        resultSet.getString("booking_date"), resultSet.getString("start_time"), resultSet.getString("end_time"));
                availableClasses.put(newClass.id(), newClass);
                classSelector.addItem(newClass.id() + " " + newClass.name());
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        classSelector.setSelectedIndex(-1);
        //sessionDaySelector.setSelectedIndex(-1);

    }

    /**
     * Sets up Upcoming Sessions and Upcoming Classes tables
     */
    private void setUpSessionsClasses() {
        dateField.setEditable(false);
        starttimeField.setEditable(false);
        endtimeField.setEditable(false);
        classesModel = new DefaultTableModel();
        sessionsModel = new DefaultTableModel();

        //add columns
        classesModel.addColumn("ID");
        classesModel.addColumn("Class Name");
        classesModel.addColumn("Date");
        classesModel.addColumn("Start Time");
        classesModel.addColumn("End Time");
        sessionsModel.addColumn("Session ID");
        sessionsModel.addColumn("Trainer ID");
        sessionsModel.addColumn("Trainer");
        sessionsModel.addColumn("Exercise");
        sessionsModel.addColumn("Date");
        sessionsModel.addColumn("Start Time");
        sessionsModel.addColumn("End Time");
        upcomingClasses.setModel(classesModel);
        upcomingSessions.setModel(sessionsModel);
        upcomingSessions.getColumnModel().removeColumn(upcomingSessions.getColumn("Session ID"));
        upcomingSessions.getColumnModel().removeColumn(upcomingSessions.getColumn("Trainer ID"));
        upcomingClasses.getColumnModel().removeColumn(upcomingClasses.getColumn("ID"));

        JTextField tf = new JTextField();
        tf.setEditable(false);
        DefaultCellEditor editor = new DefaultCellEditor( tf );
        upcomingClasses.setDefaultEditor(Object.class, editor);
        upcomingSessions.setDefaultEditor(Object.class, editor);

        //upcomingClasses.c

        upcomingClasses.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cancelClass.setEnabled(!upcomingClasses.getSelectionModel().isSelectionEmpty());
            }
        });

        upcomingSessions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cancelSession.setEnabled(!upcomingSessions.getSelectionModel().isSelectionEmpty());
                rescheduleSession.setEnabled(!upcomingSessions.getSelectionModel().isSelectionEmpty());
            }
        });

        setUpClassesValues();
        setUpSessionsValues();
    }

    /**
     * Updates achievements and goals table
     */
    private void retrieveAchievements() {
        try {
            goalmodel.setRowCount(0);
            achievementmodel.setRowCount(0);
            Statement statement = databaseConnection.createStatement();
            statement.executeQuery("SELECT * FROM achievements WHERE member_id = " + memberID);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
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

    /**
     * Populates achievement table
     */
    private void setUpAchievements() {
        achievementmodel = new AchievementModel();
        goalmodel = new GoalModel();
        retrieveAchievements();
        achievementTable.setModel(achievementmodel);
        goaltable.setModel(goalmodel);

        goaltable.getColumnModel().getColumn(0).setPreferredWidth(250);
        achievementTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        achievementTable.getColumnModel().getColumn(1).setPreferredWidth(10);

        goaltable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (goaltable.getSelectedRow() < 0) return;
                String goal = (String) goaltable.getValueAt(goaltable.getSelectedRow(), 0);

                removeGoal.setEnabled(!goaltable.getSelectionModel().isSelectionEmpty() && !goal.equals(""));
            }
        });
    }

    /**
     * Table model for the goals table
     */
    public static class GoalModel extends DefaultTableModel {
        public GoalModel() {
            super(new String[]{"Goal"}, 0);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
    }

    /**
     * Table model for achievements table
     */
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
                var rowData = getDataVector().get(row);
                rowData.set(1, (boolean) aValue);
                fireTableCellUpdated(row, column);
            }
        }
    }

    /**
     * Apply changes made to achievements table to database
     */
    protected void applyAchievementChanges() {
        try {
            Statement statement = databaseConnection.createStatement();
            int modelSize = achievementmodel.getRowCount();
            for (int i = 0; i < modelSize; i++) {
                if (achievementmodel.getValueAt(i, 2) == null) {
                    boolean achieved = (boolean) achievementmodel.getValueAt(i, 1);
                    if (achieved) {
                        String message = "UPDATE achievements" +
                                " SET achieved = true, date_achieved = CURRENT_DATE" +
                                " WHERE member_id = " + memberID + " AND description = '" + achievementmodel.getValueAt(i, 0) + "';";
                        statement.execute(message);
                    }
                }
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setUpAchievements();
    }


    /**
     * Set routine text field
     */
    protected void setRoutine()
    {
        try {
            Statement getRoutineId = databaseConnection.createStatement();
            String msg = "SELECT * \n" +
                    "FROM tracker\n" +
                    "WHERE member_id = " + memberID;
            getRoutineId.executeQuery(msg);
            ResultSet routineSet = getRoutineId.getResultSet();

            while (routineSet.next())
            {
                String text = routineSet.getString("exercise_routine");
                Statement getRoutine = databaseConnection.createStatement();
                String roumsg = "SELECT routine_desc\n" +
                        "FROM exerciseroutines\n" +
                        "WHERE routine_id = " + text;

                getRoutine.executeQuery(roumsg);
                ResultSet getSet = getRoutine.getResultSet();
                getSet.next();
                text = getSet.getString(1);

                if (text == null) return;
                text = text.replaceAll(", ", "\n");
                text = text.replaceAll(",", "\n");
                routineTextArea.setText(text);

                getSet.close();
                getRoutine.close();
            }
            routineSet.close();
            getRoutineId.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

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
