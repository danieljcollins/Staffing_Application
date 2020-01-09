CREATE database StaffingApplicationDB;
USE StaffingApplicationDB;

CREATE TABLE shifts(
	id INT AUTO_INCREMENT PRIMARY KEY,
	shift_name VARCHAR(30) NOT NULL,
	start_time TIME,
	end_time TIME,
	required_training VARCHAR(3) NOT NULL
);


CREATE TABLE employees(
	id INT AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	email VARCHAR(50),
	hours_worked INT,
	pool BOOLEAN,
	mdr BOOLEAN,
	linen BOOLEAN,
	dock BOOLEAN
);

