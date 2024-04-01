package org.group68;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import java.util.*;

import static javax.swing.GroupLayout.Alignment.*;
import static javax.swing.LayoutStyle.ComponentPlacement;
import static javax.swing.GroupLayout.*;
import static javax.swing.*;


public class TrainerDashboard extends javax.swing.JFrame{
    private JLabel PIN;
    private JTextField PINdisplay;
    private JPanel availability;
    private JLabel availabilityErrorMessage;
    private JLabel cardNumber;
    private JTextField cardNumberDisplay;
    private JLabel currentAvailability;
    private JPanel dashboard;
    private JPanel editAvailability;
    private JComboBox<String> editFriEnd;
    private JComboBox<String> editFriStart;
    private JComboBox<String> editMonEnd;
    private JComboBox<String> editMonStart;
    private JComboBox<String> editSatEnd;
    private JComboBox<String> editSatStart;
    private JComboBox<String> editSunEnd;
    private JComboBox<String> editSunStart;
    private JComboBox<String> editThursEnd;
    private JComboBox<String> editThursStart;
    private JComboBox<String> editTuesEnd;
    private JComboBox<String> editTuesStart;
    private JComboBox<String> editWedEnd;
    private JComboBox<String> editWedStart;
    private JLabel email;
    private JTextField emailDisplay;
    private JLabel enterMemberName;
    private JLabel fri;
    private JLabel friEnd;
    private JTextField friEndTimeDisplay;
    private JLabel friStart;
    private JTextField friStartTimeDisplay;
    private JLabel friTo;
    private JCheckBox fridayTick;
    private JMenuBar jMenuBar1;
    private JLabel joinDate;
    private JTextField joinDateDisplay;
    private JLabel memberID;
    private JTextField memberIDdisplay;
    private JTextField memberNameEntry;
    private JPanel memberSearch;
    private JMenu menuBar;
    private JLabel mon;
    private JLabel monEnd;
    private JTextField monEndTimeDisplay;
    private JLabel monStart;
    private JTextField monStartTimeDisplay;
    private JLabel monTo;
    private JCheckBox mondayTick;
    private JLabel name;
    private JTextField nameDisplay;
    private JMenuItem openAdmin;
    private JMenuItem openMember;
    private JLabel phone;
    private JTextField phoneDisplay;
    private JMenuItem quit;
    private JLabel sat;
    private JLabel satEnd;
    private JTextField satEndTimeDisplay;
    private JLabel satStart;
    private JTextField satStartTimeDisplay;
    private JLabel satTo;
    private JCheckBox saturdayTick;
    private JButton saveButton;
    private JButton searchButton;
    private JLabel searchErrorMessage;
    private JLabel sun;
    private JLabel sunEnd;
    private JTextField sunEndTimeDisplay;
    private JLabel sunStart;
    private JTextField sunStartTimeDisplay;
    private JLabel sunTo;
    private JCheckBox sundayTick;
    private JLabel thurs;
    private JLabel thursEnd;
    private JTextField thursEndTimeDisplay;
    private JLabel thursStart;
    private JTextField thursStartTimeDisplay;
    private JLabel thursTo;
    private JCheckBox thursdayTick;
    private JLabel tues;
    private JLabel tuesEnd;
    private JTextField tuesEndTimeDisplay;
    private JLabel tuesStart;
    private JTextField tuesStartTimeDisplay;
    private JLabel tuesTo;
    private JCheckBox tuesdayTick;
    private JLabel wed;
    private JLabel wedEnd;
    private JTextField wedEndTimeDisplay;
    private JLabel wedStart;
    private JTextField wedStartTimeDisplay;
    private JLabel wedTo;
    private JCheckBox wednesdayTick;
    public TrainerDashboard(){
        initComponents();

    }


    private void initComponents() {

        dashboard = new JPanel();
        availability = new JPanel();
        currentAvailability = new JLabel();
        mon = new JLabel();
        tues = new JLabel();
        wed = new JLabel();
        thurs = new JLabel();
        fri = new JLabel();
        sat = new JLabel();
        sun = new JLabel();
        monStartTimeDisplay = new JTextField();
        monTo = new JLabel();
        monEndTimeDisplay = new JTextField();
        tuesEndTimeDisplay = new JTextField();
        tuesTo = new JLabel();
        tuesStartTimeDisplay = new JTextField();
        wedEndTimeDisplay = new JTextField();
        wedTo = new JLabel();
        wedStartTimeDisplay = new JTextField();
        thursEndTimeDisplay = new JTextField();
        thursTo = new JLabel();
        thursStartTimeDisplay = new JTextField();
        friEndTimeDisplay = new JTextField();
        friTo = new JLabel();
        friStartTimeDisplay = new JTextField();
        satEndTimeDisplay = new JTextField();
        satTo = new JLabel();
        satStartTimeDisplay = new JTextField();
        sunEndTimeDisplay = new JTextField();
        sunTo = new JLabel();
        sunStartTimeDisplay = new JTextField();
        memberSearch = new JPanel();
        enterMemberName = new JLabel();
        memberNameEntry = new JTextField();
        searchButton = new JButton();
        memberID = new JLabel();
        email = new JLabel();
        joinDate = new JLabel();
        phone = new JLabel();
        name = new JLabel();
        PIN = new JLabel();
        cardNumber = new JLabel();
        cardNumberDisplay = new JTextField();
        PINdisplay = new JTextField();
        phoneDisplay = new JTextField();
        joinDateDisplay = new JTextField();
        emailDisplay = new JTextField();
        nameDisplay = new JTextField();
        memberIDdisplay = new JTextField();
        searchErrorMessage = new JLabel();
        editAvailability = new JPanel();
        mondayTick = new JCheckBox();
        tuesdayTick = new JCheckBox();
        wednesdayTick = new JCheckBox();
        thursdayTick = new JCheckBox();
        fridayTick = new JCheckBox();
        saturdayTick = new JCheckBox();
        sundayTick = new JCheckBox();
        monStart = new JLabel();
        tuesStart = new JLabel();
        wedStart = new JLabel();
        thursStart = new JLabel();
        friStart = new JLabel();
        satStart = new JLabel();
        sunStart = new JLabel();
        monEnd = new JLabel();
        tuesEnd = new JLabel();
        wedEnd = new JLabel();
        thursEnd = new JLabel();
        friEnd = new JLabel();
        satEnd = new JLabel();
        sunEnd = new JLabel();
        saveButton = new JButton();
        editMonStart = new JComboBox<>();
        editTuesStart = new JComboBox<>();
        editWedStart = new JComboBox<>();
        editSatStart = new JComboBox<>();
        editFriStart = new JComboBox<>();
        editThursStart = new JComboBox<>();
        editSunStart = new JComboBox<>();
        editMonEnd = new JComboBox<>();
        editTuesEnd = new JComboBox<>();
        editWedEnd = new JComboBox<>();
        editThursEnd = new JComboBox<>();
        editFriEnd = new JComboBox<>();
        editSatEnd = new JComboBox<>();
        editSunEnd = new JComboBox<>();
        availabilityErrorMessage = new JLabel();
        jMenuBar1 = new JMenuBar();
        menuBar = new JMenu();
        openAdmin = new JMenuItem();
        openMember = new JMenuItem();
        quit = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dashboard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Trainer Dashboard", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 3, 14))); // NOI18N

        availability.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Availability"));

        currentAvailability.setText("Curent availability:");

        mon.setText("Monday:");

        tues.setText("Tuesday:");

        wed.setText("Wednesday:");

        thurs.setText("Thursday:");

        fri.setText("Friday:");

        sat.setText("Saturday:");

        sun.setText("Sunday:");

        monStartTimeDisplay.setEditable(false);

        monTo.setText("to");

        monEndTimeDisplay.setEditable(false);

        tuesEndTimeDisplay.setEditable(false);

        tuesTo.setText("to");

        tuesStartTimeDisplay.setEditable(false);

        wedEndTimeDisplay.setEditable(false);

        wedTo.setText("to");

        wedStartTimeDisplay.setEditable(false);

        thursEndTimeDisplay.setEditable(false);

        thursTo.setText("to");

        thursStartTimeDisplay.setEditable(false);

        friEndTimeDisplay.setEditable(false);

        friTo.setText("to");

        friStartTimeDisplay.setEditable(false);

        satEndTimeDisplay.setEditable(false);

        satTo.setText("to");

        satStartTimeDisplay.setEditable(false);

        sunEndTimeDisplay.setEditable(false);

        sunTo.setText("to");

        sunStartTimeDisplay.setEditable(false);

        GroupLayout availabilityLayout = new GroupLayout(availability);
        availability.setLayout(availabilityLayout);
        availabilityLayout.setHorizontalGroup(
                availabilityLayout.createParallelGroup(LEADING)
                        .addGroup(availabilityLayout.createSequentialGroup()
                                .addGroup(availabilityLayout.createParallelGroup(LEADING)
                                        .addGroup(availabilityLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(availabilityLayout.createParallelGroup(LEADING)
                                                        .addComponent(mon)
                                                        .addComponent(tues)
                                                        .addComponent(wed)
                                                        .addComponent(thurs)
                                                        .addComponent(fri)
                                                        .addComponent(sat)
                                                        .addComponent(sun))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(availabilityLayout.createParallelGroup(LEADING)
                                                        .addComponent(monStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(tuesStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(wedStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(thursStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(friStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(satStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(sunStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(availabilityLayout.createParallelGroup(LEADING)
                                                        .addComponent(monTo)
                                                        .addComponent(tuesTo)
                                                        .addComponent(wedTo)
                                                        .addComponent(thursTo)
                                                        .addComponent(friTo)
                                                        .addComponent(satTo)
                                                        .addComponent(sunTo))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(availabilityLayout.createParallelGroup(LEADING) //Everything afterwards was cut down to the 1 parameter version of addComponent
                                                        .addComponent(thursEndTimeDisplay, TRAILING, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(sunEndTimeDisplay, TRAILING, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(satEndTimeDisplay, TRAILING, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(friEndTimeDisplay, TRAILING, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(wedEndTimeDisplay, TRAILING, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(tuesEndTimeDisplay, TRAILING, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(monEndTimeDisplay, TRAILING, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))) //Everything before was cut down to the 1 parameter version of addComponent
                                        .addGroup(availabilityLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(currentAvailability)))
                                .addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE))
        );
        availabilityLayout.setVerticalGroup(
                availabilityLayout.createParallelGroup(LEADING)
                        .addGroup(availabilityLayout.createSequentialGroup()
                                .addComponent(currentAvailability)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(availabilityLayout.createParallelGroup(BASELINE)
                                        .addComponent(mon)
                                        .addComponent(monStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(monTo)
                                        .addComponent(monEndTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(availabilityLayout.createParallelGroup(BASELINE)
                                        .addComponent(tues)
                                        .addComponent(tuesStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(tuesTo)
                                        .addComponent(tuesEndTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(availabilityLayout.createParallelGroup(BASELINE)
                                        .addComponent(wed)
                                        .addComponent(wedStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(wedTo)
                                        .addComponent(wedEndTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(availabilityLayout.createParallelGroup(BASELINE)
                                        .addComponent(thurs)
                                        .addComponent(thursStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(thursTo)
                                        .addComponent(thursEndTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(availabilityLayout.createParallelGroup(BASELINE)
                                        .addComponent(fri)
                                        .addComponent(friStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(friTo)
                                        .addComponent(friEndTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(availabilityLayout.createParallelGroup(BASELINE)
                                        .addComponent(sat)
                                        .addComponent(satStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(satTo)
                                        .addComponent(satEndTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(availabilityLayout.createParallelGroup(BASELINE)
                                        .addComponent(sun)
                                        .addComponent(sunStartTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(sunTo)
                                        .addComponent(sunEndTimeDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE))
        );

        memberSearch.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Member search"));

        enterMemberName.setText("Enter member name:");

        memberNameEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                memberNameEntryActionPerformed(evt);
            }
        });

        searchButton.setText("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        memberID.setText("Member ID:");

        email.setText("Email:");

        joinDate.setText("Join date:");

        phone.setText("Phone:");

        name.setText("Name:");

        PIN.setText("PIN:");

        cardNumber.setText("Card number:");

        cardNumberDisplay.setEditable(false);

        PINdisplay.setEditable(false);

        phoneDisplay.setEditable(false);

        joinDateDisplay.setEditable(false);

        emailDisplay.setEditable(false);

        nameDisplay.setEditable(false);
        nameDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                nameDisplayActionPerformed(evt);
            }
        });

        memberIDdisplay.setEditable(false);

        searchErrorMessage.setText("Please enter a valid name!");
        searchErrorMessage.setEnabled(false);

        GroupLayout memberSearchLayout = new GroupLayout(memberSearch);
        memberSearch.setLayout(memberSearchLayout);
        memberSearchLayout.setHorizontalGroup(
                memberSearchLayout.createParallelGroup(LEADING)
                        .addGroup(memberSearchLayout.createSequentialGroup()
                                .addGroup(memberSearchLayout.createParallelGroup(LEADING)
                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(memberSearchLayout.createParallelGroup(LEADING)
                                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                                .addComponent(enterMemberName)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(memberNameEntry))
                                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                                .addGroup(memberSearchLayout.createParallelGroup(LEADING)
                                                                        .addComponent(email)
                                                                        .addComponent(joinDate)
                                                                        .addComponent(phone)
                                                                        .addComponent(name))
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                .addGroup(memberSearchLayout.createParallelGroup(LEADING, false)
                                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(memberID)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(memberIDdisplay, PREFERRED_SIZE, 130, PREFERRED_SIZE))
                                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                                .addGap(160, 160, 160)
                                                                .addComponent(searchButton))
                                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                                .addGap(85, 85, 85)
                                                                .addGroup(memberSearchLayout.createParallelGroup(LEADING, false)
                                                                        .addComponent(nameDisplay)
                                                                        .addComponent(emailDisplay)
                                                                        .addComponent(joinDateDisplay)
                                                                        .addComponent(phoneDisplay, DEFAULT_SIZE, 131, Short.MAX_VALUE)))
                                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(memberSearchLayout.createParallelGroup(LEADING)
                                                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                                                .addComponent(PIN)
                                                                                .addGap(160, 160, 160))
                                                                        .addGroup(memberSearchLayout.createSequentialGroup()
                                                                                .addComponent(cardNumber)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(memberSearchLayout.createParallelGroup(LEADING)
                                                                                        .addComponent(PINdisplay)
                                                                                        .addComponent(cardNumberDisplay))
                                                                                .addGap(16, 16, 16)))))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(searchErrorMessage)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        memberSearchLayout.setVerticalGroup(
                memberSearchLayout.createParallelGroup(LEADING)
                        .addGroup(memberSearchLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(enterMemberName)
                                        .addComponent(memberNameEntry, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(searchButton)
                                        .addComponent(searchErrorMessage))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(memberID)
                                        .addComponent(memberIDdisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(name)
                                        .addComponent(nameDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(email)
                                        .addComponent(emailDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(joinDate)
                                        .addComponent(joinDateDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(phone)
                                        .addComponent(phoneDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(cardNumber)
                                        .addComponent(cardNumberDisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(memberSearchLayout.createParallelGroup(BASELINE)
                                        .addComponent(PIN)
                                        .addComponent(PINdisplay, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editAvailability.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Edit Availability"));

        mondayTick.setText("Monday");

        tuesdayTick.setText("Tuesday");

        wednesdayTick.setText("Wednesday");

        thursdayTick.setText("Thursday");

        fridayTick.setText("Friday");

        saturdayTick.setText("Saturday");

        sundayTick.setText("Sunday");

        monStart.setText("Start time:");

        tuesStart.setText("Start time:");

        wedStart.setText("Start time:");

        thursStart.setText("Start time:");

        friStart.setText("Start time:");

        satStart.setText("Start time:");

        sunStart.setText("Start time:");

        monEnd.setText("End time:");

        tuesEnd.setText("End time:");

        wedEnd.setText("End time:");

        thursEnd.setText("End time:");

        friEnd.setText("End time:");

        satEnd.setText("End time:");

        sunEnd.setText("End time:");

        saveButton.setText("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        editMonStart.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editMonStart.setEnabled(false);

        editTuesStart.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editTuesStart.setEnabled(false);

        editWedStart.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editWedStart.setEnabled(false);

        editSatStart.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editSatStart.setEnabled(false);

        editFriStart.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editFriStart.setEnabled(false);

        editThursStart.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editThursStart.setEnabled(false);

        editSunStart.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editSunStart.setEnabled(false);

        editMonEnd.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editMonEnd.setEnabled(false);

        editTuesEnd.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editTuesEnd.setEnabled(false);

        editWedEnd.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editWedEnd.setEnabled(false);

        editThursEnd.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editThursEnd.setEnabled(false);

        editFriEnd.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editFriEnd.setEnabled(false);

        editSatEnd.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editSatEnd.setEnabled(false);

        editSunEnd.setModel(new DefaultComboBoxModel<>(new String[] { "12:00 AM", "12:30 AM", "1:00 AM", "1:30 AM", "2:00 AM", "2:30 AM", "3:00 AM", "3:30 AM", "4:00 AM", "4:30 AM", "5:00 AM", "5:30 AM", "6:00 AM", "6:30 AM", "7:00 AM", "7:30 AM", "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 AM", "12:30 AM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM", "7:00 PM", "7:30 PM", "8:00 PM", "8:30 PM", "9:00 PM", "9:30 PM", "10:00 PM", "10:30 PM", "11:00 PM", "11:30 PM" }));
        editSunEnd.setEnabled(false);

        availabilityErrorMessage.setText("Impractical availability entered. Please check that start times entered precede end times.");
        availabilityErrorMessage.setEnabled(false);

        GroupLayout editAvailabilityLayout = new GroupLayout(editAvailability);
        editAvailability.setLayout(editAvailabilityLayout);
        editAvailabilityLayout.setHorizontalGroup(
                editAvailabilityLayout.createParallelGroup(LEADING)
                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                .addGroup(editAvailabilityLayout.createParallelGroup(LEADING)
                                        .addComponent(mondayTick)
                                        .addComponent(sundayTick)
                                        .addComponent(saturdayTick)
                                        .addComponent(fridayTick)
                                        .addComponent(thursdayTick)
                                        .addComponent(wednesdayTick)
                                        .addComponent(tuesdayTick))
                                .addGap(8, 8, 8)
                                .addGroup(editAvailabilityLayout.createParallelGroup(LEADING)
                                        .addComponent(monStart)
                                        .addComponent(tuesStart)
                                        .addComponent(wedStart)
                                        .addComponent(thursStart)
                                        .addComponent(friStart)
                                        .addComponent(satStart)
                                        .addComponent(sunStart))
                                .addGap(5, 5, 5)
                                .addGroup(editAvailabilityLayout.createParallelGroup(TRAILING, false)
                                        //Everything afterwards was cut down to the 1 parameter version of addComponent
                                        .addComponent(editSatStart, LEADING, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editFriStart, LEADING, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editThursStart, LEADING, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editWedStart, LEADING, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editTuesStart, LEADING, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editMonStart, LEADING, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        //Everything before was cut down to the 1 parameter version of addComponent
                                        .addComponent(editSunStart, 0, DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(editAvailabilityLayout.createParallelGroup(LEADING)
                                        .addComponent(sunEnd)
                                        .addComponent(satEnd)
                                        .addComponent(friEnd)
                                        .addComponent(thursEnd)
                                        .addComponent(wedEnd)
                                        .addComponent(tuesEnd)
                                        .addComponent(monEnd))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(editAvailabilityLayout.createParallelGroup(LEADING, false)
                                        .addComponent(editWedEnd, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editTuesEnd, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editMonEnd, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editThursEnd, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editFriEnd, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editSatEnd, 0, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editSunEnd, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                .addGap(77, 77, 77))
                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(availabilityErrorMessage)
                                .addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(TRAILING, editAvailabilityLayout.createSequentialGroup()
                                .addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton)
                                .addGap(196, 196, 196))
        );
        editAvailabilityLayout.setVerticalGroup(
                editAvailabilityLayout.createParallelGroup(LEADING)
                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(editAvailabilityLayout.createParallelGroup(LEADING)
                                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                                .addGap(168, 168, 168)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(editSunStart, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(sunStart)
                                                        .addComponent(sundayTick)))
                                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(mondayTick)
                                                        .addComponent(monStart)
                                                        .addComponent(editMonStart, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(tuesdayTick)
                                                        .addComponent(tuesStart)
                                                        .addComponent(editTuesStart, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(wednesdayTick)
                                                        .addComponent(wedStart)
                                                        .addComponent(editWedStart, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(LEADING, false)
                                                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                                                .addComponent(thursdayTick)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(saturdayTick))
                                                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                                        .addComponent(editThursStart, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(thursStart))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                                        .addComponent(editFriStart, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(friStart)
                                                                        .addComponent(fridayTick))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                                        .addComponent(editSatStart, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                                        .addComponent(satStart)))))
                                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                                .addGap(140, 140, 140)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(satEnd)
                                                        .addComponent(editSatEnd, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(sunEnd)
                                                        .addComponent(editSunEnd, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)))
                                        .addGroup(editAvailabilityLayout.createSequentialGroup()
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(editMonEnd, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(monEnd))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(editTuesEnd, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(tuesEnd))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(editWedEnd, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(wedEnd))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(editThursEnd, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(thursEnd))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(editAvailabilityLayout.createParallelGroup(BASELINE)
                                                        .addComponent(editFriEnd, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                                        .addComponent(friEnd))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(availabilityErrorMessage)
                                .addContainerGap(9, Short.MAX_VALUE))
        );

        GroupLayout dashboardLayout = new GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
                dashboardLayout.createParallelGroup(LEADING)
                        .addGroup(dashboardLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(dashboardLayout.createParallelGroup(LEADING, false)
                                        .addComponent(editAvailability, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(memberSearch, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(availability, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
                dashboardLayout.createParallelGroup(LEADING)
                        .addGroup(dashboardLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(availability, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editAvailability, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(memberSearch, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE))
        );

        menuBar.setText("File");

        openAdmin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        openAdmin.setText("Open Admin Dashboard");
        menuBar.add(openAdmin);

        openMember.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        openMember.setText("Open Member Dashboard");
        menuBar.add(openMember);

        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        quit.setText("Quit");
        menuBar.add(quit);

        jMenuBar1.add(menuBar);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(LEADING)
                        .addComponent(dashboard, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(dashboard, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void searchButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void nameDisplayActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }


    private void memberNameEntryActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }
}
