package org.example.ejerciciointegrador3.service;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.CarreraDTO;
import org.example.ejerciciointegrador3.entity.Carrera;
import org.example.ejerciciointegrador3.mapper.CarreraMapper;
import org.example.ejerciciointegrador3.repository.CarreraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CarreraService {
    private final CarreraRepository carreraRepository;
    private final CarreraMapper mapper;


    /*----------------------------- CRUD -----------------------------*/
    // Lógica de negocio para dar de alta una carrera
    public CarreraDTO save(CarreraDTO request) {
        Carrera carreraNueva = mapper.convertToEntity(request);

        Carrera carreraGuardada = carreraRepository.save(carreraNueva);

        return mapper.convertToDTO(carreraGuardada);
    }

    public List<CarreraDTO> findAll() {
        return carreraRepository.findAll() // devuelve una lista de <Estudiante>
                .stream() // convierte la lista para poder hacer el mapping
                .map(mapper::convertToDTO) // map() transforma cada elemento de la lista a un dto
                .toList(); // convierte el stream de nuevo en lista para devolverlo al front
    }

    // Obtener 1 estudiante, usando su id
    public CarreraDTO findById(Integer id) {
        Carrera c = carreraRepository.findById(id) // devuelve un Optional<Estudiante>
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una carrera con el id: " + id));
        // tengo que chequear si encontró el estudiante. Sino, devuelve una excepción

        return mapper.convertToDTO(c); // convierte la Entidad a un DTO para devolverla al front
    }

    // Editar un Estudiante
    public CarreraDTO update(Integer id, CarreraDTO request) {

        Carrera carreraEditar = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe una carrera con ese id: " + id));


        if(request.getNombre() == null || request.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if(request.getDuracion() == null || request.getDuracion() <= 0) {
            throw new IllegalArgumentException("La duración es obligatoria");
        }

        carreraEditar.setNombre(request.getNombre());
        carreraEditar.setDuracion(request.getDuracion());

        Carrera actualizado = carreraRepository.save(carreraEditar);

        return mapper.convertToDTO(actualizado);
    }


    // Borra un estudiante
    public void delete(Integer id) {
        Carrera e = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una carrera con el id: "+ id));
        carreraRepository.delete(e);
    }


    /*-------------------------- MÉTODOS ESPECÍFICOS --------------------------*/

    public CarreraDTO findByLU(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null");
        }

        Carrera c = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe una carrera: "+ id));

        return mapper.convertToDTO(c);
    }
}
