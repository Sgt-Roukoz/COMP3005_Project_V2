INSERT INTO gymtrainers(email, phone, first_name, last_name)
VALUES ('test1@email.com', '613-445-5434', 'Jim', 'Bob'),
('test2@email.com', '613-441-5434', 'Dig', 'Grug'),
('test3@email.com', '613-423-6784', 'Dug', 'Grig');

INSERT INTO rooms(room_name)
VALUES ('South Room'),
('North Room'),
('West Room');

INSERT INTO gymmembers(email, join_date, phone, first_name, last_name, card_num, pin)
VALUES ('anemail', '2024-02-04', '912-222-3333', 'AName', 'BName', '1111111111', '324');

INSERT INTO exerciseroutines(routine_desc)
VALUES(E'100 Pushups, 100 Situps, 10KM Run\n')

INSERT INTO groupclasses(trainer_id, class_name, exercise_routine, room_id)
VALUES(11, 'Two Class', 2, 11);

INSERT INTO RoomBookings
VALUES(11, '2024-04-21', '19:00', '23:00');

INSERT INTO groupclasses(trainer_id, class_name, exercise_routine, room_id)
VALUES(10, 'Three Class', 2, 10);

INSERT INTO RoomBookings
VALUES(10, '2024-04-15', '12:00', '14:00');

INSERT INTO ClassMembers
VALUES(1, 1);

INSERT INTO TrainerAvailability
VALUES(1, '2024-04-15', '9:00', '14:00'),
(1, '2024-04-16', '9:00', '14:00'),
(1, '2024-04-16', '9:00', '12:00'),
(2, '2024-04-14', '9:00', '14:00'),
(2, '2024-04-16', '9:00', '16:00'),
(2, '2024-04-19', '9:00', '10:30');