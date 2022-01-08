package com.nalstest.todolist.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nalstest.todolist.controller.TaskController;
import com.nalstest.todolist.enums.Status;
import com.nalstest.todolist.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskController taskController;

    @Test
    void getDontHaveTask() throws Exception {
        ResponseEntity<Page<Task>> responseEntity = this.taskController.getAll(0, 2);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    void getHaveTask() throws Exception {
        ResponseEntity<Page<Task>> responseEntity = this.taskController.getAll(0, 2);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals(4, responseEntity.getBody().getTotalPages());
        Assertions.assertEquals(7, responseEntity.getBody().getTotalElements());
        Assertions.assertEquals("Task 3",
                responseEntity.getBody().getContent().get(1).getNameTask());
    }

    @Test
    void create() throws Exception {
        Task task = new Task("NEW TASK", Status.PLANNING, new Date(), new Date());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/nals//task")
                        .content(ow.writeValueAsString(task))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void update() throws Exception {
        Task task = new Task(9, "RENAME NEW TASK", Status.PLANNING, new Date(), new Date());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/api/nals//task")
                        .content(ow.writeValueAsString(task))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void updateTaskNotAvailable() throws Exception {
        Task task = new Task(1000, "This is task 1000", Status.PLANNING, new Date(), new Date());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ResponseEntity<Task> responseEntity = this.taskController.update(task);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void delete() {
        ResponseEntity<String> responseEntity = this.taskController.delete(9L);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void deleteFailId() {
        ResponseEntity<String> responseEntity = this.taskController.delete(1000L);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("Your task is not Exist", responseEntity.getBody());
    }
}
