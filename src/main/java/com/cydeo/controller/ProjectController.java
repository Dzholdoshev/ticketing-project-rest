package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping()
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjects(){
        return ResponseEntity.ok(new ResponseWrapper("list of projects", projectService.listAllProjects(), HttpStatus.OK ));
    }
    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("projectCode") String code){
        return ResponseEntity.ok(new ResponseWrapper("project retrieved", projectService.getByProjectCode(code), HttpStatus.OK ));
    }
    @PostMapping
    @RolesAllowed({"Manager","Admin"})
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO){
        projectService.save(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("project is created", HttpStatus.OK ));
    }
    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("project is updated", HttpStatus.OK ));
    }
    @DeleteMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable ("projectCode") String code){
        projectService.delete(code);
        return ResponseEntity.ok(new ResponseWrapper("project is deleted", HttpStatus.OK));
    }
    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
        projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("list of projects", HttpStatus.OK ));
    }
    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode")String code){
        projectService.complete(code);
        return ResponseEntity.ok(new ResponseWrapper("project is completed", HttpStatus.OK ));
    }
}
