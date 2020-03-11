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

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Dan', 'Collins', 'Portering Services', 'dan.collins@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Joe', 'Silver', 'Portering Services', 'joe.silver@company.com', 
	1000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Stacy', 'Klein', 'Portering Services', 'stacy.klein@company.com', 
	1000, 0, TRUE, FALSE, FALSE, FALSE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Jennifer', 'Gold', 'Portering Services', 'jennifer.gold@company.com', 
	25000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Moe', 'Cedar', 'Portering Services', 'moe.cedar@company.com', 
	4500, 0, TRUE, FALSE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Amber', 'Nickel', 'Portering Services', 'amber.nickel@company.com',
	15000, 0, FALSE, FALSE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Carl', 'Stevens', 'Portering Services', 'carl.stevens@company.com', 
	25000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Ronald', 'Jones', 'Portering Services', 'ronald.jones@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Mick', 'Dagger', 'Portering Services', 'mick.dagger@company.com', 
	35000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Auryn', 'James', 'Portering Services', 'auryn.james@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Don', 'Curtains', 'Portering Services', 'don.curtains@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Madeline', 'Mombay', 'Portering Services', 'madeline.mombay@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);
	
INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Michael', 'Sleeman', 'Portering Services', 'michael.sleeman@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('John', 'Conway', 'Portering Services', 'john.conway@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);
	
INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Liam', 'Kincaid', 'Portering Services', 'liam.kincaid@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Lemmy', 'Rockolo', 'Portering Services', 'lemmy.rockolo@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Kimberly', 'Cline', 'Portering Services', 'kimberly.cline@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Ashley', 'Melee', 'Portering Services', 'ashley.melee@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Sarah', 'Cool', 'Portering Services', 'sarah.cool@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Shawn', 'Trip',  'Portering Services','shawn.tripp@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);
	
INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Lauren', 'Sloan', 'Portering Services', 'lauren.sloan@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Andrea', 'Walton', 'Portering Services', 'andrea.walton@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Kate', 'Peters', 'Portering Services', 'kate.peters@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Jennifer', 'Mallo', 'Portering Services', 'jennifer.mallo@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Amelia', 'Reynolds', 'Portering Services', 'amelia.renolds@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);
	
-- new batch

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Tony', 'Subtle', 'Portering Services', 'tony.stark@company.com', 
	35000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Charles', 'Excalibur', 'Portering Services', 'charles.xavier@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Johnathan', 'Thomas', 'Portering Services', 'johnathan.thomas@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Gregory', 'Bite', 'Portering Services', 'gregory.peck@company.com', 
	65000, 0, TRUE, TRUE, FALSE, TRUE);

INSERT INTO employees(first_name, last_name, department, email, 
	hours_worked, sick_days, pool, mdr, linen, dock)
VALUES('Stewart', 'Large', 'Portering Services', 'stewart.large@company.com', 
	15000, 0, TRUE, TRUE, TRUE, TRUE);
