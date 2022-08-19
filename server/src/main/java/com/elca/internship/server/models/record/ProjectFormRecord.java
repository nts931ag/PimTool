package com.elca.internship.server.models.record;


import java.util.List;

public record ProjectFormRecord(
        ProjectRecord project,
        List<String> listMember
) {
}
