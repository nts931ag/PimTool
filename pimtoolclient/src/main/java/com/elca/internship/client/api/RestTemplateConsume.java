package com.elca.internship.client.api;

import com.elca.internship.client.config.connection.Rest;
import com.elca.internship.client.models.entity.Employee;
import com.elca.internship.client.models.entity.Group;
import com.elca.internship.client.models.entity.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class RestTemplateConsume {

    public static final String uri_get_all_project = BASE_URI + "/api/projects";
    public static final String uri_get_all_group = BASE_URI + "/api/groups";
    public static final String uri_get_all_employee = BASE_URI + "/api/employees";
    public static final String uri_save_new_project = BASE_URI + "/api/projects/save";

    private final RestTemplate restTemplate;

    public Project[] getAllProject(){
        var responseForGroups = restTemplate.getForEntity(uri_get_all_project, Project[].class);
        return responseForGroups.getBody();
    }

    public ObservableList<Integer> getAllProjectNumber(){
        var responseForGroups = restTemplate.getForEntity(uri_get_all_project, Project[].class);
        var groups = responseForGroups.getBody();
        return FXCollections.observableArrayList(Arrays.stream(groups).map(Project::getProjectNumber).collect(Collectors.toList()));
    }

    public ObservableList<String> getAllEmployeeVisa(){
        var responseForMembers = restTemplate.getForEntity(uri_get_all_employee, Employee[].class);
        var members = responseForMembers.getBody();
        return FXCollections.observableArrayList(Arrays.stream(members).map(Employee::getVisa).collect(Collectors.toList()));
    }

    public ObservableList<Long> getAllGroupId(){
        var responseForGroups = restTemplate.getForEntity(uri_get_all_group, Group[].class);
        var groups = responseForGroups.getBody();
        return FXCollections.observableArrayList(Arrays.stream(groups).map(Group::getId).collect(Collectors.toList()));
    }

    public ResponseEntity saveNewProject(Project project, List<String> listMemberVisa) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMemberVisa);
        var msg = objectMapper.writeValueAsString(map);
        var uri = BASE_URI + "/api/projects/save";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(msg, headers);
        return restTemplate.exchange(
                uri
                , HttpMethod.POST
                , httpEntity
                , String.class
        );
    }
}
