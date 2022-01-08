package com.nalstest.todolist.controller;

import com.nalstest.todolist.model.Task;
import com.nalstest.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nals/")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/get-task")
    public ResponseEntity<Page<Task>> getAll(@RequestParam Integer page, @RequestParam Integer offset) {
        Page<Task> tasks = taskService.getTask(page, offset);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping("/task")
    public ResponseEntity<Task> create(@RequestBody Task task) {
        Task newTask = taskService.saveTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }

    @PutMapping("/task")
    public ResponseEntity<Task> update(@RequestBody Task task) {
        Task newTask = taskService.saveTask(task);
        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        String responseMessage = taskService.delete(id);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
}
