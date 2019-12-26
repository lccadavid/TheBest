package com.thebest.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class ValuePoint extends AuditableEntity {

    public enum ValuePointType {
        PLATINUM, GOLD, BRONZE
    }

    @Id
    private Long id;
    private ValuePointType type;
    private String name;


}
