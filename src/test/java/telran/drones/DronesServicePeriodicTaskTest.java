package telran.drones;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import telran.drones.api.PropertiesNames;
@SpringBootTest(properties = {PropertiesNames.PERIODIC_UNIT_MICROS + "=10"})
class DronesServicePeriodicTaskTest {

	@Test
	void test() throws InterruptedException {
		Thread.sleep(1000);
		//TODO here there should be test for testing chain of the event logs
	}

}
