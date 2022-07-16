package com.elca.internship.client.api;

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

import static com.elca.internship.client.config.connection.Rest.BASE_URI;

@Component
@RequiredArgsConstructor
public class RestTemplateConsume {

    public static final String URI_GET_ALL_PROJECT = BASE_URI + "/api/projects";
    public static final String URI_GET_ALL_GROUP = BASE_URI + "/api/groups";
    public static final String uri_get_all_employee = BASE_URI + "/api/employees";
    public static final String uri_save_new_project = BASE_URI + "/api/projects/save";
    public static final String uri_get_all_project_criterial_specified = BASE_URI + "/api/projects/search?proName={proName}&proStatus={proStatus}";
    public static final String uri_get_all_employee_of_project_id = BASE_URI + "/api/projects-employees/{id}";
    private final RestTemplate restTemplate;

    public List<String> getAllEmployeeVisaByProjectId(long id){
        var param = new HashMap<>();
        param.put("id", id);
        return restTemplate.getForObject(uri_get_all_employee_of_project_id, List.class, param);
    }
    public Project[] getAllProject(){
        var responseForGroups = restTemplate.getForEntity(URI_GET_ALL_PROJECT, Project[].class);
        return responseForGroups.getBody();
    }

    public ObservableList<Integer> getAllProjectNumber(){
        var responseForGroups = restTemplate.getForEntity(URI_GET_ALL_PROJECT, Project[].class);
        var groups = responseForGroups.getBody();
        return FXCollections.observableArrayList(Arrays.stream(groups).map(Project::getProjectNumber).toList());
    }

    public ObservableList<String> getAllEmployeeVisa(){
        var responseForMembers = restTemplate.getForEntity(uri_get_all_employee, Employee[].class);
        var members = responseForMembers.getBody();
        return FXCollections.observableArrayList(Arrays.stream(members).map(Employee::getVisa).toList());
    }

    public ObservableList<Long> getAllGroupId(){
        var responseForGroups = restTemplate.getForEntity(URI_GET_ALL_GROUP, Group[].class);
        var groups = responseForGroups.getBody();
        return FXCollections.observableArrayList(Arrays.stream(groups).map(Group::getId).toList());
    }

    public ResponseEntity saveNewProject(Project project, List<String> listMemberVisa) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        var map = new HashMap<String, Object>();
        map.put("project", project);
        map.put("listMember", listMemberVisa);
        var msg = objectMapper.writeValueAsString(map);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(msg, headers);
        return restTemplate.exchange(
                uri_save_new_project
                , HttpMethod.POST
                , httpEntity
                , String.class
        );
    }

    public Project[] searchProjectByCriteria(String tfSearchValue, String cbStatusValue){
        var headers = new HttpHeaders();
        var requestEntity = new HttpEntity<>(headers);
        var uriVariables = new HashMap<>();
        uriVariables.put("proName", tfSearchValue);
        uriVariables.put("proStatus", cbStatusValue);

        var response = restTemplate.exchange(
                uri_get_all_project_criterial_specified,
                HttpMethod.GET,
                requestEntity,
                Project[].class, uriVariables
        );

        return response.getBody();
    }

    public void getAllEmployeeVisaOfProject(long id) {
    }
}