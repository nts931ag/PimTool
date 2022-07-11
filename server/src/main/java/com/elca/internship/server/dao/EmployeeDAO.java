package com.elca.internship.server.dao;

import java.util.List;

public interface EmployeeDAO {
    List<Long> findIdBylistVisa(List<String> listVisa);
}
