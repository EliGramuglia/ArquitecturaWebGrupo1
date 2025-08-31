package org.example.entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Producto {

    private Integer idFactura;
    private Integer idProducto;
    private Integer cantidad;

}
