package com.thebest.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AuditableEntity {

    @Column(nullable = false, columnDefinition = "TINYINT(3) DEFAULT 0", length = 1)
    private boolean deleted;

}
