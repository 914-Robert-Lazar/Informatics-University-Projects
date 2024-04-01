package com.example.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.rest.Model.Exercise;
import com.example.rest.Repository.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PayRollApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Repository repository;
					
	@SuppressWarnings("null")
	@Test
	void testAdd() throws JsonProcessingException, Exception {
		Exercise mockExercise = new Exercise("Dip", "push", 3);

		ResultActions result = mockMvc.perform(post("/api/exercises").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockExercise)));
		
		result.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.name", is(mockExercise.getName())))
			.andExpect(jsonPath("$.type", is(mockExercise.getType())))
			.andExpect(jsonPath("$.level", is(mockExercise.getLevel())));
	}

	@SuppressWarnings("null")
	@Test
	void testUpdate() throws JsonProcessingException, Exception {
		Exercise mockExercise = new Exercise("Dip", "push", 3);

		ResultActions result = mockMvc.perform(put("/api/exercises/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mockExercise)));

		result.andExpect(status().isOk())
			  .andDo(print())
			  .andExpect(jsonPath("$.name", is(mockExercise.getName())))
			  .andExpect(jsonPath("$.type", is(mockExercise.getType())))
			  .andExpect(jsonPath("$.level", is(mockExercise.getLevel())));
	}

	@SuppressWarnings("null")
	@Test
	public void testGetAll() throws Exception{
        List<Exercise> listOfExercises = new ArrayList<>();
        listOfExercises.add(new Exercise("Dip", "push", 3));
        listOfExercises.add(new Exercise("Pull-up", "pull", 3));
        repository.saveAll(listOfExercises);
    
        ResultActions response = mockMvc.perform(get("/api/exercises"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfExercises.size() + 9)));
    }
}
