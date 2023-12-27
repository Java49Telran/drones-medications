delete from logs;
delete from drones;
delete from medications;
insert into drones (number, model, weight_limit, battery_capacity, state) 
	values
		('Drone-1', 'Middleweight', 300, 100, 'IDLE'),
		('Drone-2', 'Middleweight', 300, 20, 'IDLE'),
		('Drone-3', 'Middleweight', 300, 100, 'LOADING');
insert into medications (code, name, weight)
	values 
		('MED_1', 'Medication-1', 200),
		('MED_2', 'Medication-2', 350)	
		