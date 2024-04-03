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

INSERT INTO groupclasses(trainer_id, class_name, exercise_routine, room_id)
VALUES(2, 'Two Class', 1, 2);

INSERT INTO RoomBookings
VALUES(2, CURRENT_DATE, '19:00', '23:00')

INSERT INTO groupclasses(trainer_id, class_name, exercise_routine, room_id)
VALUES(3, 'Three Class', 1, 3);

INSERT INTO RoomBookings
VALUES(3, CURRENT_DATE, '12:00', '14:00');

INSERT INTO ClassMembers
VALUES(1, 1);