package org.example.entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Producto {

    private Integer idProducto;
    private String nombre;
    private Float valor;

    public Producto(int idProducto) {
    }


}
