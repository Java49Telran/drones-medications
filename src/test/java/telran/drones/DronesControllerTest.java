package telran.drones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.drones.dto.*;
import telran.drones.exceptions.DroneAlreadyExistException;
import telran.drones.service.DronesService;
import telran.exceptions.GlobalExceptionsHandler;

import static telran.drones.api.ConstraintConstants.*;
import static telran.drones.api.ServiceExceptionMessages.*;
import static telran.drones.TestDisplayNames.*;
import static telran.drones.api.UrlConstants.*;

import java.util.Arrays;

@WebMvcTest
class DronesControllerTest {
	private static final String HOST = "http://localhost:8080/";
	DroneDto droneDto = new DroneDto("D-123", ModelType.Middleweight, 300, (byte) 100, State.IDLE);
	MedicationDto medicationDto = new MedicationDto("CODE_1", "Medication-1", 200);

	DroneDto droneDtoWrongFields = new DroneDto(new String(new char[10000]), ModelType.Middleweight, 600, (byte) 101,
			State.IDLE);
	DroneDto droneDtoMissingFields = new DroneDto(null, null, null, null, null);
	String[] errorMessagesWrongFields = { DRONE_NUMBER_WRONG_LENGTH, MAX_PERCENTAGE_VIOLATION, MAX_WEIGHT_VIOLATION,

	};
	String[] errorMessagesMissingFields = { MISSING_BATTERY_CAPACITY, MISSING_DRONE_NUMBER, MISSING_MODEL,
			MISSING_STATE, MISSING_WEIGHT_LIMIT,

	};
	@Autowired
	MockMvc mockMvc;
	@MockBean
	DronesService dronesService;
	@Autowired
	ObjectMapper mapper;

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
		assertErrorMessages(response, errorMessagesMissingFields);
	}

	private void assertErrorMessages(String response, String[] expectedMessages) {
		String [] actualMessages = response.split(GlobalExceptionsHandler.ERROR_MESSAGES_DELIMITER);
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
		assertErrorMessages(response, errorMessagesWrongFields);
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

}
