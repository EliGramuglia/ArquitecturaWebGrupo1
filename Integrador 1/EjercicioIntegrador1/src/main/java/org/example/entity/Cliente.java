package org.example.entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cliente {

    private Integer idCliente;
    private String nombre;
    private String email;
    private Float totalFacturado;

    public Cliente(Integer idCliente, String nombre, String email) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.email = email;
    }

    public Cliente(Integer idCliente, String nombre, Float totalFacturado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.totalFacturado = totalFacturado;
    }

}
