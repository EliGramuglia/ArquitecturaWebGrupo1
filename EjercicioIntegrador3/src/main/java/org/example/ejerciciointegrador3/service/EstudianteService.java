package org.example.ejerciciointegrador3.service;

import org.example.ejerciciointegrador3.dto.request.EstudianteRequestDTO;
import org.example.ejerciciointegrador3.dto.response.EstudianteResponseDTO;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.example.ejerciciointegrador3.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }


    // Lógica de negocio para dar de alta un Estudiante
    public EstudianteResponseDTO save(EstudianteRequestDTO request) {
        // Primero validamos que los datos enviados sean correctos:
        if(request.getDni() == null) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        if(estudianteRepository.existsById(request.getDni())) {
            throw new IllegalArgumentException("El DNI ya existe");
        }
        if(request.getLU() == null) {
            throw new IllegalArgumentException("El LU es obligatorio");
        }

        // El método recibe un dto, no una entidad, por lo que hay que convertirlo a
        // un Estudiante real, que JPA sepa guardar en la base
        Estudiante estudianteNuevo = new Estudiante(
                request.getDni(),
                request.getNombre(),
                request.getApellido(),
                request.getFechaNacimiento(),
                request.getGenero(),
                request.getCiudadResidencia(),
                request.getLU()
        );

        // Guardo el estudiante en la base
        Estudiante estudianteGuardado = estudianteRepository.save(estudianteNuevo);

        // Devuelvo al front sólo lo que quiero mostrar
        EstudianteResponseDTO response = new EstudianteResponseDTO();
        response.setDni(estudianteGuardado.getDni());
        response.setNombre(estudianteGuardado.getNombre());
        response.setApellido(estudianteGuardado.getApellido());
        response.setLU(estudianteGuardado.getLU());

        return response;
    }
}
