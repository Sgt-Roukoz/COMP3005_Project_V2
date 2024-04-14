/**
 * Dialog class for member rescheduling sessions
 */
package org.group68;

import net.time4j.ClockUnit;
import net.time4j.PlainTime;
import net.time4j.range.ChronoInterval;
import net.time4j.range.ClockInterval;
import net.time4j.range.IntervalCollection;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class MemberSessionReschedule extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> rescheduleDaySelector;
    private JTextField trainerField;
    private JComboBox rescheduleTimeSelector;
    private Connection databaseConnection;
    private  int trainerInQuestion;
    private int sessionid;

    public MemberSessionReschedule(int sessionid, int trainer, String trainerName, Connection databaseConnection) {
        setContentPane(contentPane);
        trainerField.setText(trainerName);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.databaseConnection = databaseConnection;
        this.trainerInQuestion = trainer;
        this.sessionid = sessionid;
        setSize(400,400);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Reschedule Session");

        LocalDate localDate = LocalDate.now();
        LocalDate firstDayOfNextWeek = localDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        List<LocalDate> remainingDays = localDate.datesUntil(firstDayOfNextWeek)
                .collect(Collectors.toList());
        Vector<String> days = new Vector<>();
        for (LocalDate date : remainingDays)
        {
            String day = date.getDayOfWeek().toString();
            day = day.charAt(0) + day.substring(1).toLowerCase();
            days.add(day);
        }

        rescheduleDaySelector.setModel(new DefaultComboBoxModel<>(days));

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
        rescheduleDaySelector.addActionListener(new selectorListener());
        rescheduleTimeSelector.addActionListener(new selectorListener());
        setVisible(true);
    }

    public class selectorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String item = (String)rescheduleDaySelector.getSelectedItem();
            String time = (String)rescheduleTimeSelector.getSelectedItem();
            if (!item.equals("") ) GetTrainerAvailability(trainerInQuestion, time, item);
        }
    }

    private void onOK() {
        try{
            LocalDate day = convertToDate(rescheduleDaySelector.getSelectedItem().toString());
            LocalTime time = LocalTime.parse(rescheduleTimeSelector.getSelectedItem().toString());

            Statement updateSession = databaseConnection.createStatement();
            String sessionMsg = "UPDATE privatesessions\n" +
                    "SET session_date = '" + day + "', start_time = '" + time + "'\n" +
                    "WHERE session_id = " + sessionid;
            updateSession.execute(sessionMsg);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void GetTrainerAvailability(int trainer, String timeSelected, String daySelected) {
        LocalDate day = convertToDate(daySelected);
        System.out.println(daySelected);
        LocalTime time = LocalTime.parse(timeSelected);
        IntervalCollection<PlainTime> trainerIntervals = null;
        System.out.println(time);
        System.out.println(day);
        boolean timefound = false;

        try {
            //grab trainers that are available during the selected day
            Statement initTrainer = databaseConnection.createStatement();
            String tmessage = "SELECT gymtrainers.trainer_id, available_day, start_time, end_time\n" +
                    "FROM traineravailability JOIN gymtrainers ON traineravailability.trainer_id = gymtrainers.trainer_id\n" +
                    "WHERE available_day = '" + day + "' AND start_time <= '" + time + "' AND end_time >= '" + time.plusHours(1) + "' AND gymtrainers.trainer_id = " + trainer;
            initTrainer.executeQuery(tmessage);
            ResultSet trainerSet = initTrainer.getResultSet();

            while (trainerSet.next())
            {
                //trainerSelector.addItem(trainerSet.getString("first_name") + " " + trainerSet.getString("last_name"));
                PlainTime startTime = PlainTime.of(Integer.parseInt(trainerSet.getString("start_time").split(":")[0]),
                        Integer.parseInt(trainerSet.getString("start_time").split(":")[1]));
                PlainTime endTime = PlainTime.of(Integer.parseInt(trainerSet.getString("end_time").split(":")[0]),
                        Integer.parseInt(trainerSet.getString("end_time").split(":")[1]));
                trainerIntervals = IntervalCollection.onClockAxis().plus(ClockInterval.between(startTime, endTime));
            }

            if (trainerIntervals == null)
            {
                System.out.println("Trainer not available for this time");
                return;
            }

            //System.out.println(trainerIntervals);
            trainerSet.close();
            initTrainer.close();

            //check if trainers have group classes scheduled during this time, and remove that from availability
            Statement taken = databaseConnection.createStatement();
            String takenMessage = "SELECT trainer_id, booking_date, start_time, end_time\n" +
                    "FROM groupclasses JOIN roombookings ON groupclasses.room_id = roombookings.room_id\n" +
                    "WHERE booking_date = '" + day + "' AND trainer_id = " + trainer;
            taken.executeQuery(takenMessage);
            ResultSet takenSet = taken.getResultSet();

            while (takenSet.next())
            {
                PlainTime slotStartTime = PlainTime.of(Integer.parseInt(takenSet.getString("start_time").split(":")[0]),
                            Integer.parseInt(takenSet.getString("start_time").split(":")[1]));
                PlainTime slotEndTime = PlainTime.of(Integer.parseInt(takenSet.getString("end_time").split(":")[0]),
                            Integer.parseInt(takenSet.getString("end_time").split(":")[1]));
                ClockInterval slot = ClockInterval.between(slotStartTime, slotEndTime);
                trainerIntervals = trainerIntervals.minus(slot);
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
                PlainTime slotStartTime = PlainTime.of(Integer.parseInt(sessionSet.getString("start_time").split(":")[0]),
                        Integer.parseInt(sessionSet.getString("start_time").split(":")[1]));
                PlainTime slotEndTime = slotStartTime.plus(1, ClockUnit.HOURS);
                ClockInterval slot = ClockInterval.between(slotStartTime, slotEndTime);
                trainerIntervals = trainerIntervals.minus(slot);
                System.out.println("A session was found");
            }

            //generate the availability intervals for each viable trainer
            List<ChronoInterval<PlainTime>> trainerAvailability = trainerIntervals.getIntervals();
            System.out.println(trainerAvailability);
            taken.close();
            takenSet.close();

            //finally, check if chosen session time fits in any intervals of viable trainers
            ClockInterval chosenTime = ClockInterval.between(time.plusSeconds(1), time.plusMinutes(59).plusSeconds(59));

                for (ChronoInterval<PlainTime> interval : trainerAvailability) {
                    if (((ClockInterval) interval).encloses(chosenTime)) {
                        timefound = true;
                    }
                }

                buttonOK.setEnabled(timefound);
                if (!timefound)
                {
                    System.out.println("Trainer not available for this time");
                }
                else System.out.println("Trainer is available during that time!");

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
}
