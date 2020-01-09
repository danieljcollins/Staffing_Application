/* This file is for inserting dummy-data into the database
to allow the portfolio project to work in a simulated way.
Eventually, some type of migration script would be 
neccessary to use real data
*/

-- shift table dummy data:
-- Pool shifts

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('P1', '070000', '150000', 'P');

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('P2', '150000', '230000', 'P');

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('P3', '230000', '070000', 'P');

-- Linen shifts

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('L1', '070000', '150000', 'L');

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('L2', '070000', '150000', 'L');

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('L3', '070000', '150000', 'L');

-- Dock shifts

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('D1', '070000', '150000', 'D');

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('D2', '150000', '230000', 'D');

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('D3', '230000', '070000', 'D');

-- MDR shifts

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('M1', '070000', '150000', 'M');

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('M2', '150000', '230000', 'M');

INSERT INTO shifts (shift_name, start_time, end_time, 
required_training)
VALUES ('M3', '230000', '070000', 'M');

-- employees table dummy data:

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Dan', 'Collins', 'dan.collins@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Joe', 'Silver', 'joe.silver@company.com', 
	1000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Stacy', 'Klein', 'stacy.klein@company.com', 
	1000, TRUE, FALSE, FALSE, FALSE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Jennifer', 'Gold', 'jennifer.gold@company.com', 
	25000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Moe', 'Cedar', 'moe.cedar@company.com', 
	4500, TRUE, FALSE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Amber', 'Nickel', 'amber.nickel@company.com',
	15000, FALSE, FALSE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Carl', 'Stevens', 'carl.stevens@company.com', 
	25000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Ronald', 'Jones', 'ronald.jones@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Mick', 'Dagger', 'mick.dagger@company.com', 
	35000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Auryn', 'James', 'auryn.james@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Don', 'Curtains', 'don.curtains@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Madeline', 'Mombay', 'madeline.mombay@company.com', 
	65000, TRUE, TRUE, FALSE, TRUE);
	
INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Michael', 'Sleeman', 'michael.sleeman@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('John', 'Conway', 'john.conway@company.com', 
	65000, TRUE, TRUE, FALSE, TRUE);
	
INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Liam', 'Kincaid', 'liam.kincaid@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Lemmy', 'Rockolo', 'lemmy.rockolo@company.com', 
	65000, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Kimberly', 'Cline', 'kimberly.cline@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Ashley', 'Melee', 'ashley.melee@company.com', 
	65000, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Sarah', 'Cool', 'sarah.cool@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

-- additional employees below

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Shawn', 'Trip', 'shawn.tripp@company.com', 
	65000, TRUE, TRUE, FALSE, TRUE);
	
INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Lauren', 'Sloan', 'lauren.sloan@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Andrea', 'Walton', 'andrea.walton@company.com', 
	65000, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Kate', 'Peters', 'kate.peters@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Jennifer', 'Mallo', 'jennifer.mallo@company.com', 
	65000, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, email, 
	hours_worked, pool, mdr, linen, dock)
VALUES('Amelia', 'Reynolds', 'amelia.renolds@company.com', 
	15000, TRUE, TRUE, TRUE, TRUE);
