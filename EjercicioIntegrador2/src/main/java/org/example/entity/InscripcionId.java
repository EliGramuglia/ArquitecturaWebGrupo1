package org.example.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class InscripcionId implements Serializable {
    private Integer LU;
    private Integer idCarrera;

}
