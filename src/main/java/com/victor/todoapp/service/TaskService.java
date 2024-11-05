package com.victor.todoapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.victor.todoapp.exceptions.ToDoExceptions;
import com.victor.todoapp.mapper.TaskInDTOToTask;
import com.victor.todoapp.persistence.entity.Task;
import com.victor.todoapp.persistence.entity.TaskStatus;
import com.victor.todoapp.persistence.repository.TaskRepository;
import com.victor.todoapp.service.dto.TaskInDTO;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskInDTOToTask mapper;

    public TaskService(TaskRepository repository, TaskInDTOToTask mapper ) {
        this.repository = repository;
        this.mapper=mapper;
    }

    public Task createTask(TaskInDTO taskInDTO){
        Task task= mapper.map(taskInDTO);
        return this.repository.save(task);
    }

    public List<Task> findAll(){
        return this.repository.findAll();
    }

    public Optional<Task> findById(Long id){
        return this.repository.findById(id);
    }

    public List<Task> findAllByTaskStatus(TaskStatus status){
        return this.repository.findAllByTaskStatus(status);
    }

    @Transactional
    public void updateTaskAsFinished(Long id){
        Optional<Task> optionalTask=this.repository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
            
        }
        this.repository.markTaskFinished(id);
    }

    public void deleteById(Long id){
        Optional<Task> optionalTask=this.repository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ToDoExceptions("Task not found", HttpStatus.NOT_FOUND);
            
        }
        this.repository.deleteById(id);
    }
}
