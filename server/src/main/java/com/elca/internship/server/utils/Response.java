package com.elca.internship.server.utils;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private String statusCode;
    private String statusMsg;
}
