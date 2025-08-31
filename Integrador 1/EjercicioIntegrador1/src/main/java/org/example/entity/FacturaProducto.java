package org.example.entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FacturaProducto {

    private Integer idProducto;
    private String nombre;
    private Float valor;

}
