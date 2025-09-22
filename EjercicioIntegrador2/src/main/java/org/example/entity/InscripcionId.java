package org.example.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Embeddable
public class InscripcionId implements Serializable {
    private Integer LU;
    private Integer idCarrera;

}
