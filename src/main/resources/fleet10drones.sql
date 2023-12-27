delete from logs;
delete from drones;
delete from medications;
insert into drones (number, model, weight_limit, battery_capacity, state) 
	values
		('Drone-1', 'Middleweight', 300, 100, 'IDLE'),
		('Drone-2', 'Middleweight', 300, 100, 'IDLE'),
		('Drone-3', 'Middleweight', 300, 100, 'IDLE'),
		('Drone-4', 'Lightweight', 100, 100, 'IDLE'),
		('Drone-5', 'Lightweight', 100, 100, 'IDLE'),
		('Drone-6', 'Lightweight', 100, 100, 'IDLE'),
		('Drone-7', 'Cruiserweight', 200, 100, 'IDLE'),
		('Drone-8', 'Cruiserweight', 200, 100, 'IDLE'),
		('Drone-9', 'Heavyweight', 500, 100, 'IDLE'),
		('Drone-10', 'Heavyweight', 500, 100, 'IDLE');
insert into medications (code, name, weight)
	values 
		('MED_1', 'Medication-1', 200),
		('MED_2', 'Medication-2', 350),
		('MED_3', 'Medication-3', 90),
		('MED_4', 'Medication-4', 150),
		('MED_5', 'Medication-5', 250),
		('MED_6', 'Medication-6', 100),
		('MED_7', 'Medication-7', 120),
		('MED_8', 'Medication-8', 85);		
		