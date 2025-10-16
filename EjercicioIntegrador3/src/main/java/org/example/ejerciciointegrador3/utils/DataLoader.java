package org.example.ejerciciointegrador3.utils;

import org.example.ejerciciointegrador3.dto.EstudianteDTO;
import org.example.ejerciciointegrador3.dto.request.EstudianteRequestDTO;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.example.ejerciciointegrador3.mapper.EstudianteMapper;
import org.example.ejerciciointegrador3.repository.EstudianteRepository;
import org.example.ejerciciointegrador3.service.CsvReaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CsvReaderService csvReaderService;
    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper estudianteMapper;

    public DataLoader(CsvReaderService csvReaderService, EstudianteRepository estudianteRepository, EstudianteMapper estudianteMapper) {
        this.csvReaderService = csvReaderService;
        this.estudianteRepository = estudianteRepository;
        this.estudianteMapper = estudianteMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        // guardar desde CSV de estudiantes
        csvReaderService.addEstudiantes();

//        // Guardar en la base de datos
//        for (EstudianteRequestDTO e : estudiantes) {
//            if (e.getDni() != null && !estudianteRepository.existsById(e.getDni())) {
//                Estudiante estudiante =  EstudianteMapper.convertToEntity(e);
//                List<Estudiante> estudiantesEntity = EstudianteMapper.convertToEntity();
//
//                estudianteRepository.save(e);
//            } else if (e.getDni() == null) {
//                System.out.println("Registro ignorado por DNI nulo: " + e);
//            }
//        }


        System.out.println("Carga de estudiantes completada. Total: " + estudianteRepository.count());
    }
}
