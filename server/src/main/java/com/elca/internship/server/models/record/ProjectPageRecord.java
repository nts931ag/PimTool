package com.elca.internship.server.models.record;

import java.util.List;

public record ProjectPageRecord(long totalElements, List<ProjectRecord> projectRecordList) {
}
