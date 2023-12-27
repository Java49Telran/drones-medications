Assumptions:
1. Battery capacity is changing on 2% per time unit. Total for full cycle on 22% (11 time units)
2. From LOADING to LOADED, LOADED to DELIVERING, DELIVERED to RETURNING required one time unit (time unit is predefined and may be configured)
3. From DELIVERING to DELIVERED, RETURNING to IDLE - 4 time units
4. To keep things much simplier there are introduced intermediate states between DELIVERING and DELIVERED, and between RETURNING and IDLE that has allowed considering all moves per one time unit with appropriated logging

Running Instructions
1. Download ZIP from GitHub
2. Unzip
3. Enter folder with unzipped project
4. Run command maven wrapper mvnw package. All Unit Tests should be performed and as a result there will be created JAR file drones-medications-0.0.1.jar
5. Run command java -jar trget/drones-medications-0.0.1.jar. As a result the application will start on the port 8080
6. By using Postman or any other Restful client there may be performed a sanity integration test according to the API
   6.1 drones/load POST request with DTO as DroneMedication class
   6.2 drones/available GET request
   6.3 drones/medications/{droneNumber} GET request
   6.4 drones/battery/capacity/{droneNumber} GET request
   6.5 drones/logs/{droneNumber} GET request
   6.6 drones/medications/amounts GET request for getting how many mediacations there were delivered by each drone
7. Predefined DB is created by the script
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
		
