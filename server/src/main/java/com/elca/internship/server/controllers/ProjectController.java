package com.elca.internship.server.controllers;

import com.elca.internship.server.models.entity.Project;
import com.elca.internship.server.services.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;


    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity createNewProject(@RequestBody String jsonObject){


        log.info("Object from client: " + jsonObject);

        return new ResponseEntity(HttpStatus.OK);
    }

}
