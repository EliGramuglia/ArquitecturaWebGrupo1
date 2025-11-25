package org.example.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoPagoResponseDTO {

    private Long pagoId;
    private String status; // "approved", "pending", "rejected"

}