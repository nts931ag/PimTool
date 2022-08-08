package com.elca.internship.server.models.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "version")
    @Version
    private Integer version;
}
