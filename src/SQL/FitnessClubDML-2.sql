INSERT INTO memberlogins
VALUES(1, 'epicuser', 'coolpassword');

INSERT INTO groupclasses(trainer_id, class_name, exercise_routine, room_id)
VALUES(2, 'Secondary Class', 2, 1);

INSERT INTO RoomBookings
VALUES(1, '2024-04-21', '19:00', '23:00');

INSERT INTO  TrainerLogin
VALUES(1, 'jimBob', 'jimsPassword');

INSERT INTO TrainerLogin
VALUES(2, 'dig', 'digsPassword');

INSERT INTO TrainerLogin
VALUES(3, 'dug', 'dugsPassword');

INSERT INTO adminlogin
VALUES(1, 'goatedAdmin', 'adminsPassword');

INSERT INTO groupclasses(trainer_id, class_name, exercise_routine, room_id)
VALUES(1, 'YDK Strength by Jim Bob', 2, 2);

INSERT INTO RoomBookings
VALUES(2, '2024-04-15', '12:00', '14:00');

INSERT INTO ClassMembers
VALUES(1, 1);

INSERT INTO PrivateSessions(member_id, trainer_id, set_routine, start_time, session_date)
VALUES(1, 1, 'Strength', '12:00', '2024-04-16');

INSERT INTO TrainerAvailability
VALUES(1, '2024-04-15', '9:00', '14:00'),
      (1, '2024-04-16', '9:00', '14:00'),
      (1, '2024-04-17', '9:00', '12:00'),
      (2, '2024-04-14', '9:00', '14:00'),
      (2, '2024-04-16', '9:00', '16:00'),
      (2, '2024-04-19', '9:00', '10:30');