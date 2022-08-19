package com.elca.internship.client.models;

import java.util.List;

public record ProjectFormRecord(
        Project project,
        List<String> listMember
) {
}
