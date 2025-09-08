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
    private Float totalRecaudado;

    public Producto(int idProducto) {
    }
    public Producto(int idProducto, String nombre, Float valor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
    }

}
