package org.example.ejerciciointegrador3.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class InscripcionId implements Serializable {
    private Integer dni;
    private Integer idCarrera;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InscripcionId)) return false;
        InscripcionId that = (InscripcionId) o;
        return Objects.equals(idCarrera, that.idCarrera) &&
                Objects.equals(dni, that.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCarrera, dni);
    }

}
