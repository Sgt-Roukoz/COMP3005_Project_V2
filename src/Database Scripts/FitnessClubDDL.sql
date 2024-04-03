CREATE TABLE IF NOT EXISTS ExerciseRoutines (
	routine_id SERIAL PRIMARY KEY,
	routine_desc text
);

CREATE TABLE IF NOT EXISTS GymTrainers (
	trainer_id SERIAL PRIMARY KEY,
	email varchar(255) NOT NULL UNIQUE,
	phone varchar(255),
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS TrainerAvailability (
	trainer_id SERIAL PRIMARY KEY,
	available_day DATE NOT NULL,
	start_time TIME NOT NULL,
	end_time TIME NOT NULL
);

CREATE TABLE IF NOT EXISTS GymMembers (
	member_id SERIAL PRIMARY KEY,
	email varchar(255) NOT NULL UNIQUE,
	join_date DATE NOT NULL,
	phone varchar(255),
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL,
	card_num varchar(255) NOT NULL,
	pin int NOT NULL
);

CREATE TABLE IF NOT EXISTS Rooms (
	room_id SERIAL PRIMARY KEY,
	room_name varchar(255)
);

CREATE TABLE IF NOT EXISTS PrivateSessions (
	session_id SERIAL PRIMARY KEY,
	member_id INT NOT NULL,
	trainer_id INT NOT NULL,
	routine_id INT NOT NULL,
	start_time TIME NOT NULL,
	session_date DATE NOT NULL,
	room_id INT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id),
	FOREIGN KEY (trainer_id) REFERENCES GymTrainers(trainer_id),
	FOREIGN KEY (routine_id) REFERENCES ExerciseRoutines(routine_id),
	FOREIGN KEY (room_id) REFERENCES Rooms(room_id)
);

CREATE TABLE IF NOT EXISTS RoomBookings (
	room_id INT NOT NULL,
	booking_date DATE NOT NULL,
	start_time TIME NOT NULL,
	end_time TIME NOT NULL,
	FOREIGN KEY (room_id) REFERENCES Rooms(room_id)
);

CREATE TABLE IF NOT EXISTS GroupClasses (
	class_id SERIAL PRIMARY KEY,
	trainer_id INT NOT NULL,
	class_name varchar(255) NOT NULL,
	exercise_routine INT,
	room_id INT NOT NULL,
	FOREIGN KEY (trainer_id) REFERENCES GymTrainers(trainer_id),
	FOREIGN KEY (exercise_routine) REFERENCES ExerciseRoutines(routine_id),
	FOREIGN KEY (room_id) REFERENCES Rooms(room_id)
);

CREATE TABLE IF NOT EXISTS ClassMembers (
	class_id INT NOT NULL,
	member_id INT NOT NULL,
	FOREIGN KEY (class_id) REFERENCES GroupClasses(class_id),
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id)
);

CREATE TABLE IF NOT EXISTS Billings (
	bill_id SERIAL PRIMARY KEY,
	member_id INT NOT NULL,
	bill_type varchar(255) NOT NULL,
	bill_value varchar(255) NOT NULL,
	date_billed DATE NOT NULL,
	bill_paid BOOLEAN NOT NULL,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id)
);

CREATE TABLE IF NOT EXISTS Equipment (
	equip_id SERIAL PRIMARY KEY,
	equip_name varchar(255) NOT NULL,
	room_booked BOOLEAN NOT NULL,
	last_inspect DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS GymAdmin (
	admin_id SERIAL PRIMARY KEY,
	email varchar(255) NOT NULL UNIQUE,
	phone varchar(255) NOT NULL,
	first_name varchar(255) NOT NULL,
	last_name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS AdminLogin (
	admin_id INT NOT NULL,
	admin_username varchar(255) NOT NULL UNIQUE,
	admin_password varchar(255) NOT NULL,
	FOREIGN KEY (admin_id) REFERENCES GymAdmin(admin_id)
);

CREATE TABLE IF NOT EXISTS MemberLogins (
	member_id INT NOT NULL,
	member_username varchar(255) NOT NULL UNIQUE,
	member_password varchar(255) NOT NULL,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id)
);

CREATE TABLE IF NOT EXISTS Tracker (
	member_id INT NOT NULL,
	exercise_routine INT,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id),
	FOREIGN KEY (exercise_routine) REFERENCES ExerciseRoutines(routine_id)
);

CREATE TABLE IF NOT EXISTS Achievements (
	member_id INT NOT NULL,
	description varchar(255) NOT NULL,
	achieved BOOLEAN NOT NULL,
	date_achieved DATE,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id)
);

CREATE TABLE IF NOT EXISTS Metrics (
	member_id INT NOT NULL,
	weight INT NOT NULL,
	resting_hr INT NOT NULL,
	blood_pressure INT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id)
);

CREATE TABLE IF NOT EXISTS MemberStatistics (
	member_id INT NOT NULL,
	avg_weight INT NOT NULL,
	max_weight INT NOT NULL,
	min_weight INT NOT NULL,
	avg_resting_hr INT NOT NULL,
	max_resting_hr INT NOT NULL,
	min_resting_hr INT NOT NULL,
	avg_blood_pressure INT NOT NULL,
	max_blood_pressute INT NOT NULL,
	min_blood_pressure INT NOT NULL,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id)
);

CREATE TABLE IF NOT EXISTS WeightAccumulate (
	member_id INT NOT NULL,
	weight INT NOT NULL,
	date_logged DATE,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id)
);

CREATE TABLE IF NOT EXISTS RestingHRAccumulate (
	member_id INT NOT NULL,
	resting_hr INT NOT NULL,
	date_logged DATE,
	FOREIGN KEY (member_id) REFERENCES GymMembers(member_id)
);

CREATE TABLE IF NOT EXISTS BloodPRAccumulate (
	member_id INT NOT NULL,
	blood_pr INT NOT NULL,
	date_logged DATE,
	FOREIGN KEY (member_id) REFERENCES gymmembers(member_id)
);

-- TRIGGERS
CREATE OR REPLACE FUNCTION add_metrics()
	returns TRIGGER
	language plpgsql
AS
$$
begin
	INSERT INTO Metrics
	VALUES(NEW.member_id, 0, 0, 0);
	INSERT INTO MemberStatistics
	VALUES(NEW.member_id, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	RETURN NEW;
end;
$$;

CREATE OR REPLACE TRIGGER addMetrics
	AFTER INSERT ON GymMembers
	FOR EACH ROW
	EXECUTE PROCEDURE add_metrics();

-- update weight
CREATE OR REPLACE FUNCTION check_weight()
	returns TRIGGER
	language plpgsql
AS
$$
begin
	UPDATE MemberStatistics 
	SET (avg_weight, max_weight, min_weight) = (
		SELECT avg(weight),
			   max(weight),
			   min(weight)
		FROM WeightAccumulate
		WHERE WeightAccumulate.member_id = NEW.member_id) 
	WHERE MemberStatistics.member_id = NEW.member_id;
	
	UPDATE Metrics
	SET weight = NEW.weight
	WHERE Metrics.member_id = NEW.member_id;
	RETURN NEW;
end;
$$;

CREATE OR REPLACE TRIGGER updateWeight
	AFTER INSERT ON WeightAccumulate
	FOR EACH ROW
	EXECUTE PROCEDURE check_weight();

-- update heart rate
CREATE OR REPLACE FUNCTION check_hr()
	returns TRIGGER
	language plpgsql
AS
$$
begin
	UPDATE MemberStatistics 
	SET (avg_resting_hr, max_resting_hr, min_resting_hr) = (
		SELECT avg(resting_hr),
			   max(resting_hr),
			   min(resting_hr)
		FROM RestingHRAccumulate
		WHERE RestingHRAccumulate.member_id = NEW.member_id
	)
	WHERE MemberStatistics.member_id = NEW.member_id;
	
	UPDATE Metrics
	SET resting_hr = NEW.weight
	WHERE Metrics.member_id = NEW.member_id;
	RETURN NEW;
end;
$$;

CREATE OR REPLACE TRIGGER updateHR
	AFTER INSERT ON RestingHRAccumulate
	FOR EACH ROW
	EXECUTE PROCEDURE check_hr();

-- update blood pressure
CREATE OR REPLACE FUNCTION check_bpr()
	returns TRIGGER
	language plpgsql
AS
$$
begin
	UPDATE MemberStatistics 
	SET (avg_blood_pressure, max_blood_pressure, min_blood_pressure) =(
		SELECT avg(blood_pressure) as bpravg,
			   max(blood_pressure) as bprmax,
			   min(blood_pressure) as bprmin
		FROM BloodPRAccumulate
		WHERE BloodPRAccumulate.member_id = NEW.member_id
	)
	WHERE MemberStatistics.member_id = NEW.member_id;
	
	UPDATE Metrics
	SET resting_hr = NEW.weight
	WHERE Metrics.member_id = NEW.member_id;
	RETURN NEW;
end;
$$;

CREATE OR REPLACE TRIGGER updateHR
	AFTER INSERT ON RestingHRAccumulate
	FOR EACH ROW
	EXECUTE PROCEDURE check_hr();
	
-- Bill Triggers
