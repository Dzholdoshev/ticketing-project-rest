package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTasks(){
        return ResponseEntity.ok(new ResponseWrapper("List of tasks", taskService.listAllTasks(), HttpStatus.OK));
    }
    @GetMapping("/{taskId}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId")Long id){
        return ResponseEntity.ok(new ResponseWrapper("Task by ID is retrieved", taskService.findById(id), HttpStatus.OK));
    }
    @PostMapping()
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO task){
        taskService.save(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is created", HttpStatus.OK));
    }
    @DeleteMapping("/{taskId}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("taskId")Long id){
        taskService.delete(id);
        return ResponseEntity.ok(new ResponseWrapper("Task is deleted", HttpStatus.OK));
    }
    @PutMapping()
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is updated", HttpStatus.OK));
    }
    @GetMapping("/employee/pending-task")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeePendingTasks(){
       List<TaskDTO> list =  taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
        return ResponseEntity.ok(new ResponseWrapper("Tasks are retrieved", list ,HttpStatus.OK));
    }
    @PutMapping("/employee/update")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeUpdateTasks(@RequestBody TaskDTO task){
        taskService.update(task);
        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated",HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
       List<TaskDTO> list = taskService.listAllTasksByStatus(Status.COMPLETE);
       return ResponseEntity.ok(new ResponseWrapper("Tasks are retrieved", list ,HttpStatus.OK));
    }


}
