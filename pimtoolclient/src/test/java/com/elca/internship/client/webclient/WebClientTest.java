package com.elca.internship.client.webclient;

import com.elca.internship.client.models.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

@RunWith(value= SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebClientTest {
    //https://piotrminkowski.com/2019/11/04/using-reactive-webclient-with-spring-webflux/
    @Autowired
    private WebClient webClient;

    @Test
    public void testWebClient(){
        var list = webClient.get().uri("http://localhost:8080/api/projects")
                .retrieve()
                .bodyToFlux(Project.class);
        list.subscribe(project -> {
            System.out.println(project);
        });
    }
}
