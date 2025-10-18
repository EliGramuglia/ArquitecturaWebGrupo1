package org.example.ejerciciointegrador3.service;

import lombok.AllArgsConstructor;
import org.example.ejerciciointegrador3.dto.CarreraCantInscriptosDTO;
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
        return carreraRepository.findAll()
                .stream()
                .map(mapper::convertToDTO)
                .toList();
    }

    // Obtener 1 carrera, usando su id
    public CarreraDTO findById(Integer id) {
        Carrera c = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una carrera con el id: " + id));

        return mapper.convertToDTO(c);
    }

    // Editar una Carrera
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

    // Borra una carrera
    public void delete(Integer id) {
        Carrera e = carreraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró una carrera con el id: "+ id));
        carreraRepository.delete(e);
    }


    /*-------------------------- MÉTODOS ESPECÍFICOS --------------------------*/
    // Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    public List<CarreraCantInscriptosDTO> findCarrerasInscriptos() {
        return carreraRepository.findCarrerasInscriptos();    }
}
