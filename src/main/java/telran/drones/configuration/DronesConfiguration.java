package telran.drones.configuration;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import telran.drones.dto.*;

@Configuration
public class DronesConfiguration {
	@Bean
	ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
	    .setFieldAccessLevel(AccessLevel.PRIVATE)
	    .setFieldMatchingEnabled(true);
		return modelMapper;
	}
	@Bean
	Map<State, State> getMovesMap() {
		Map<State, State> res = new HashMap<>();
		res.put(State.LOADING, State.LOADED);
		res.put(State.LOADED, State.DELIVERING);
		res.put(State.DELIVERING, State.DELIVERING1);
		res.put(State.DELIVERING1, State.DELIVERING2);
		res.put(State.DELIVERING2, State.DELIVERING3);
		res.put(State.DELIVERING3, State.DELIVERED);
		res.put(State.DELIVERED, State.RETURNING);
		res.put(State.RETURNING, State.RETURNING1);
		res.put(State.RETURNING1, State.RETURNING2);
		res.put(State.RETURNING2, State.RETURNING3);
		res.put(State.RETURNING3, State.IDLE);
		return res;
	}
}
