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

    public Cliente(int idCliente, String nombre, String email) {
    }
}
