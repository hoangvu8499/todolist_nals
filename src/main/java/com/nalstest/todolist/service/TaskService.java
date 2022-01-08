package com.nalstest.todolist.service;

import com.nalstest.todolist.common.Constants;
import com.nalstest.todolist.exception.ApiRequestException;
import com.nalstest.todolist.model.Task;
import com.nalstest.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public Page<Task> getTask(Integer page, Integer offset) {
        try {
            Pageable pageable = PageRequest.of(page == null ? Constants.DEFAULT_PAGE : page, offset == null ? Constants.DEFAULT_MAX_OFFSET : offset);
            return taskRepository.findAllByDeletedIsNullOrderByStartingDateAsc(pageable);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public Task saveTask(Task task) {
        Task result = new Task();
        try {
            result = taskRepository.save(task);
        } catch (Exception e) {
            throw e;
        }
        return result.getId() == 0 ? null : result;
    }

    public void save(Task task) {
        try {
            taskRepository.save(task);
        } catch (Exception e) {
            throw new ApiRequestException("Have some fields is Null");
        }
    }

    public String delete(Long id) {
        try {
            if (id != null && checkExistsById(id)) {
                Task task = findById(id);
                task.setDeleted(true);
                save(task);
            } else {
                return "Your task is not Exist";
            }
        } catch (Exception e) {
            throw new ApiRequestException("Can not delete this task");
        }
        return "Delete success";
    }

    public Boolean checkExistsById(Long id) {
        return taskRepository.existsById(id);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }
}
