package com.elca.internship.server.services;

import java.util.List;

public interface EmployeeService {
    List<Long> getIdsByListVisa(List<String> listVisa);
}
