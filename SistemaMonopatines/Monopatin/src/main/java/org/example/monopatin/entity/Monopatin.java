package org.example.monopatin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.monopatin.utils.Estado;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "monopatines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Monopatin {

    @Id
    private String id; // Mongo genera ObjectId por defecto

    private Integer latitud;
    private Integer longitud;
    private Integer kmRecorridos;
    private Long viajeId;       // Cómo funciona la relación entre tablas de diferentes microservicios?
    private Estado estado;      // Está bien que usemos enum como tipo de dato de estado o usar tabla distinta?
    private Integer horasUso;   // Está bien este approach para saber cuándo darle mantenimiento al monopatín?

    public Monopatin(Integer latitud, Integer longitud, Integer kmRecorridos, Long viajeId, Estado estado) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.kmRecorridos = kmRecorridos;
        this.viajeId = viajeId;
        this.estado = estado;
    }

}