Project V2 - Health and Fitness Club Management System
COMP3005B
Winter 2024

Authors:
Marwan Zeid - 101185876
Masrur Husain - 101195917
Eric Wang - 101183647

What is this?:
This is a submission for the COMP3005 Project V2 - Health and Fitness Club Management System.
This project serves to test our knowledge on database design and application management, going through the design process
for creating a Database and interacting with it. For this project, we created a Health and Fitness Club Management System,
With 3 main users: Members, Trainers, and Admins. According to the requirements of this project:
"Members should be able to register and manage their profiles, establish personal fitness goals (you can
 determine suitable fitness goals such as weight and time, and members will set the values), and input health
 metrics. They should have access to a personalized dashboard that tracks exercise routines, fitness achievements,
 and health statistics. Members can schedule, reschedule, or cancel personal training sessions with certified
 trainers. Additionally, they should be able to register for group fitness classes.
 Trainers should have the ability to manage their schedules and view member profiles.
 Administrative Staff should be equipped with features to manage room bookings, monitor fitness equipment
 maintenance, update class schedules, oversee billing, and process payments for membership fees, personal
 training sessions, and other services."

The project was written in Java, and a GUI was implemented using Swing. All requirements were met, and any assumptions
made are included in the report.

Installation:
Download the repository from GitHub as a ZIP
Extract COMP3005_Project_V2.zip
Open IntelliJ
Click Open and select COMP3005_Project_V2-master project folder inside the extracted folder

To Use This:
First you must create a database with PostgreSQL, to do this, run pgAdmin4 and login. Keep note of your username and password.
Under Servers, right-click Databases and Create>Database... Name this database FitnessClub and click save. Now the database has been created
Next we need to put some data. To do this, right-click the database you just created and select Query Tool. Now select the folder Icon (Open File)
and select the FitnessClubDDL file, which you will find in the project under src/SQL. Now run it by pressing F5 or the arrow in pgAdmin4.
This should have created a named students, which you can see under Schemas>Tables in your database. Next we need to populate this table, so you
must open the DML files this time, and run it, which will populate the table with some values. You need to run the DML files in order, run
FitnessClubDML-1 first, then run FitnessClubDML-2. the first file populates the tables with no foreign key requirements, while
the second one does so.

Before running this application, navigate to the Main file which is under src/main/java/org.group68 (You can navigate through
it on the left side of the IDE). Using the username and password you took note of, change the variables String user and String password to your
username and password respectively. Next, to run this application you either click the Green arrow button next to the main
method of the Main class or pressing Shift+F10. This will run the login page of the app.

Video Demo: https://youtu.be/jgo6HCVojYc

The following resources are from third parties:
Blood Pressure Icon:
https://www.freepik.com/icon/blood-pressure_3389240 Icon by Smashicons

Heart Icon:
https://www.freepik.com/icon/heart-attack_1818145#fromView=search&page=1&position=9&uuid=bb3b88fd-e8b8-4653-9a8f-b9c4784c0728 Icon by Freepik

Weight Icon:
https://www.freepik.com/icon/scale_384235#fromView=search&page=1&position=30&uuid=7672e4fc-1655-496f-b525-24cf966a88ab Icon by Freepik
