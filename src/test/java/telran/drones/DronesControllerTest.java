package telran.drones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import telran.drones.dto.*;
import telran.drones.exceptions.DroneAlreadyExistException;
import telran.drones.exceptions.DroneNotFoundException;
import telran.drones.exceptions.IllegalDroneStateException;
import telran.drones.exceptions.IllegalMedicationWeightException;
import telran.drones.exceptions.LowBatteryCapacityException;
import telran.drones.exceptions.MedicationNotFoundException;
import telran.drones.service.DronesService;
import telran.exceptions.GlobalExceptionsHandler;

import static telran.drones.api.ConstraintConstants.*;
import static telran.drones.api.ServiceExceptionMessages.*;
import static telran.drones.TestDisplayNames.*;
import static telran.drones.api.UrlConstants.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;

record DroneDtoWrongEnum(String number, String model, int weightLimit, byte percent, String sate) {

}
@AllArgsConstructor
class DroneNumberMedicationAmount implements DroneMedicationsAmount {
String droneNumber;
long amount;
	@Override
	public String getNumber() {
		return droneNumber;
	}

	@Override
	public long getAmount() {
		return amount;
	}
	
}
@WebMvcTest
class DronesControllerTest {
	private static final String HOST = "http://localhost:8080/";
	private static final String DRONE_NUMBER = "DRONE-1";
	private static final String MEDICATION_CODE = "MED_1";
	DroneDto droneDto = new DroneDto("D-123", ModelType.Middleweight, 300, (byte) 100, State.IDLE);
	DroneDto droneDto2 = new DroneDto("D-124", ModelType.Middleweight, 300, (byte) 100, State.IDLE);
	MedicationDto medicationDto = new MedicationDto("CODE_1", "Medication-1", 200);
	MedicationDto medicationDto2 = new MedicationDto("CODE_2", "Medication-2", 200);
	DroneDto droneDtoWrongFields = new DroneDto(new String(new char[10000]), ModelType.Middleweight, 600, (byte) 101,
			State.IDLE);
	DroneDtoWrongEnum droneDtoWrongType = new DroneDtoWrongEnum("D-123", "KUKU", 300, (byte) 100, HOST);
	DroneDto droneDtoMissingFields = new DroneDto(null, null, null, null, null);
	String[] errorMessagesDroneWrongFields = { DRONE_NUMBER_WRONG_LENGTH, MAX_PERCENTAGE_VIOLATION,
			MAX_WEIGHT_VIOLATION };
	String[] errorMessagesDroneMissingFields = { MISSING_BATTERY_CAPACITY, MISSING_DRONE_NUMBER, MISSING_MODEL,
			MISSING_STATE, MISSING_WEIGHT_LIMIT };
	DroneMedication droneMedication = new DroneMedication(DRONE_NUMBER, MEDICATION_CODE);
	DroneMedication droneMedicationWrongFields = new DroneMedication(new String(new char[101]), "code1");
	String[] errorMesagesDroneMedicationWrongFields = { DRONE_NUMBER_WRONG_LENGTH, WRONG_MEDICATION_CODE_MESSAGE };
	DroneMedication droneMedicationMissingFields = new DroneMedication(null, null);
	String[] errorMessagesDroneMedicationMissingFields = { MISSING_DRONE_NUMBER, MISSING_MEDICATION_CODE };
	@Autowired
	MockMvc mockMvc;
	@MockBean
	DronesService dronesService;
	@Autowired
	ObjectMapper mapper;
	LogDto logDto = new LogDto(LocalDateTime.now(), DRONE_NUMBER, State.LOADING, 100, MEDICATION_CODE);

@Test
@DisplayName("Controller:" + REGISTER_DRONE_NORMAL)
	void testDroneRegisterNormal() throws Exception{
		when(dronesService.registerDrone(droneDto)).thenReturn(droneDto);
		String droneJSON = mapper.writeValueAsString(droneDto);
		String response = mockMvc.perform(post(HOST + DRONES ).contentType(MediaType.APPLICATION_JSON)
				.content(droneJSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(droneJSON, response);
		
	}

	@Test
	@DisplayName("Controller:" + REGISTER_DRONE_MISSING_FIELDS)
	void testDronRegisterMissingFields() throws Exception {
		String droneJSON = mapper.writeValueAsString(droneDtoMissingFields);
		String response = mockMvc
				.perform(post(HOST + DRONES).contentType(MediaType.APPLICATION_JSON).content(droneJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(response, errorMessagesDroneMissingFields);
	}

	@Test
	@DisplayName("Controller:" + REGISTER_DRONE_WRONG_TYPE)
	void testDronRegisterWrongType() throws Exception {
		String droneJSON = mapper.writeValueAsString(droneDtoWrongType);
		String response = mockMvc
				.perform(post(HOST + DRONES).contentType(MediaType.APPLICATION_JSON).content(droneJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertTrue(response.contains("parse error"));
	}

	private void assertErrorMessages(String response, String[] expectedMessages) {
		String[] actualMessages = response.split(GlobalExceptionsHandler.ERROR_MESSAGES_DELIMITER);
		Arrays.sort(actualMessages);
		Arrays.sort(expectedMessages);
		assertArrayEquals(expectedMessages, actualMessages);
	}

	@Test
	@DisplayName("Controller:" + REGISTER_DRONE_VALIDATION_VIOLATION)
	void testDronRegisterWrongFields() throws Exception {
		String droneJSON = mapper.writeValueAsString(droneDtoWrongFields);
		String response = mockMvc
				.perform(post(HOST + DRONES).contentType(MediaType.APPLICATION_JSON).content(droneJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(response, errorMessagesDroneWrongFields);
	}

@Test
@DisplayName("Controller:" + REGISTER_DRONE_ALREADY_EXISTS)
void testDroneRegisterAlreadyExists() throws Exception{
	when(dronesService.registerDrone(droneDto)).thenThrow(new DroneAlreadyExistException());
	String droneJSON = mapper.writeValueAsString(droneDto);
	String response = mockMvc.perform(post(HOST + DRONES ).contentType(MediaType.APPLICATION_JSON)
			.content(droneJSON)).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
	assertEquals(DRONE_ALREADY_EXISTS, response);
	
}

@Test
@DisplayName("Controller:" + LOAD_DRONE_NORMAL)
void loadMedicationNormal() throws Exception{
	when(dronesService.loadDrone(DRONE_NUMBER, MEDICATION_CODE)).thenReturn(logDto );
	String logDtoJSON = mapper.writeValueAsString(logDto);
	String droneMedicationJSON = mapper.writeValueAsString(droneMedication);
	String response = mockMvc.perform(post(HOST + LOAD_DRONE ).contentType(MediaType.APPLICATION_JSON)
			.content(droneMedicationJSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
	assertEquals(logDtoJSON, response);
	
}

	@Test
	@DisplayName("Controller:" + LOAD_DRONE_MEDICATION_NOT_FOUND)
	void loadMedicationMedicationNotFound() throws Exception {
		serviceExceptionRequest(new MedicationNotFoundException(), 404, MEDICATION_NOT_FOUND);

	}

	@Test
	@DisplayName("Controller:" + LOAD_DRONE_NOT_FOUND)
	void loadMedicationDroneNotFound() throws Exception {
		serviceExceptionRequest(new DroneNotFoundException(), 404, DRONE_NOT_FOUND);

	}

	@Test
	@DisplayName("Controller:" + LOAD_DRONE_LOW_BATTERY_CAPCITY)
	void loadMedicationLowBatteryCapacity() throws Exception {
		serviceExceptionRequest(new LowBatteryCapacityException(), 400, LOW_BATTERY_CAPACITY);

	}

	@Test
	@DisplayName("Controller:" + LOAD_DRONE_NOT_MATCHING_STATE)
	void loadMedicationNotMatchingState() throws Exception {
		serviceExceptionRequest(new IllegalDroneStateException(), 400, NOT_IDLE_STATE);

	}

	@Test
	@DisplayName("Controller:" + LOAD_DRONE_NOT_MATCHING_WEIGHT)
	void loadMedicationNotMatchingWeight() throws Exception {
		serviceExceptionRequest(new IllegalMedicationWeightException(), 400, WEIGHT_LIMIT_VIOLATION);

	}

	@Test
	@DisplayName("Controller:" + LOAD_DRONE_WRONG_FIELDS)
	void loadMedicationWrongFields() throws Exception {
		validationExceptionRequest(errorMesagesDroneMedicationWrongFields, droneMedicationWrongFields);
	}

	@Test
	@DisplayName("Controller:" + LOAD_DRONE_MISSING_FIELDS)
	void loadMedicationMissingFields() throws Exception {
		validationExceptionRequest(errorMessagesDroneMedicationMissingFields, droneMedicationMissingFields);
	}

private void serviceExceptionRequest(RuntimeException serviceException, int statusCode, String errorMessage)
		throws  Exception {
	when(dronesService.loadDrone(DRONE_NUMBER, MEDICATION_CODE)).thenThrow(serviceException);
	String droneMedicationJSON = mapper.writeValueAsString(droneMedication);
	String response = mockMvc.perform(post(HOST + LOAD_DRONE ).contentType(MediaType.APPLICATION_JSON)
			.content(droneMedicationJSON)).andExpect(status().is(statusCode)).andReturn().getResponse().getContentAsString();
	assertEquals(errorMessage, response);
}

	private void validationExceptionRequest(String[] errorMessages, DroneMedication droneMedicationDto)
			throws Exception {
		String droneMedicationJSON = mapper.writeValueAsString(droneMedicationDto);
		String response = mockMvc
				.perform(post(HOST + LOAD_DRONE).contentType(MediaType.APPLICATION_JSON).content(droneMedicationJSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();
		assertErrorMessages(response, errorMessages);
	}

	@Test
	@DisplayName("Controller: " + AVAILABLE_DRONES)
	void getAvailableDrones() throws Exception {
		DroneDto[] returnedDrones = { droneDto, droneDto2 };
		when(dronesService.checkAvailableDrones()).thenReturn(Arrays.asList(returnedDrones));
		String returnedDronesJSON = mapper.writeValueAsString(returnedDrones);
		String response = getRequest(HOST + GET_DRONES_AVAILABLE, 200);
		assertEquals(returnedDronesJSON, response);

	}
	@Test
	@DisplayName("Controller: " + CHECK_MED_ITEMS_NORMAL)
	void getLoadedMedicationItems() throws Exception {
		MedicationDto[] returnedMedications = {
				medicationDto, medicationDto2
		};
		when(dronesService.checkMedicationItems(DRONE_NUMBER))
		.thenReturn(Arrays.asList(returnedMedications));
		String returndeMedicationsJSON = mapper.writeValueAsString(returnedMedications);
		String response = getRequest(HOST + GET_DRONE_MEDICATIONS  + DRONE_NUMBER, 200);
		assertEquals(returndeMedicationsJSON, response);
	}
	@Test
	@DisplayName("Controller: " + CHECK_MED_ITEMS_DRONE_NOT_FOUND)
	void getLoadedMedicationsDroneNotFound() throws Exception {
		when(dronesService.checkMedicationItems(DRONE_NUMBER))
		.thenThrow(new DroneNotFoundException());
		String response = getRequest(HOST + GET_DRONE_MEDICATIONS  + DRONE_NUMBER,
				404);
		assertEquals(DRONE_NOT_FOUND, response);
	}
	@Test
	@DisplayName("Controller: " + CHECK_BATTERY_LEVEL_NORMAL)
	void getBatteryCapacity() throws  Exception {
		int capacity = 100;
		when(dronesService.checkBatteryLevel(DRONE_NUMBER))
		.thenReturn(capacity);
		String response = getRequest(HOST + GET_DRONE_BATTERY_CAPACITY + DRONE_NUMBER, 200);
		assertEquals(Integer.toString(capacity), response);
	}

	private String getRequest(String url, int status) throws Exception {
		String response = mockMvc.perform(get(url)).andExpect(status().is(status))
				.andReturn().getResponse().getContentAsString();
		return response;
	}
	@Test
	@DisplayName("Controller: " + CHECK_LOGS_NORMAL)
	void getLogs() throws Exception{
		LogDto [] returnedLogs = {
				logDto
		};
		when(dronesService.checkLogs(DRONE_NUMBER))
		.thenReturn(Arrays.asList(returnedLogs));
		String returnedLogsJSON = mapper.writeValueAsString(returnedLogs);
		String response = getRequest(HOST + GET_DRONE_LOGS + DRONE_NUMBER, 200);
		assertEquals(returnedLogsJSON, response);
	}
	void getDronesMedicationsAmounts() throws Exception {
		DroneNumberMedicationAmount[] returnedDronesAmounts = {
				new DroneNumberMedicationAmount(DRONE_NUMBER, 5),
				new DroneNumberMedicationAmount(DRONE_NUMBER + 10, 3),
				new DroneNumberMedicationAmount(DRONE_NUMBER + 20, 0)
		};
		when(dronesService.checkDronesMedicationItemsAmounts())
		.thenReturn(Arrays.asList(returnedDronesAmounts));
		String returnedDronesAmountJSON = mapper.writeValueAsString(returnedDronesAmounts);
		String response = getRequest(HOST + GET_DRONES_MEDICATIONS_AMOUNTS, 200);
		assertEquals(returnedDronesAmountJSON, response);
	}
	

}
