package org.example;

import jakarta.persistence.EntityManager;
import org.example.entity.Estudiante;
import org.example.factory.JPAUtil;
import org.example.repository.EstudianteRepository;
import org.example.repository.impl.EstudianteRepositoryImpl;
import java.time.LocalDate;


public class App {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        // estudianteRepository tiene que ser singleton ? (tener un getInstance()...)
        EstudianteRepository estudianteRepository = new EstudianteRepositoryImpl(em);

        // Dar de alta un estudiante
        Estudiante e1 = new Estudiante();
        e1.setNombre("Roberto");
        e1.setApellido("Lopez");
        e1.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e1.setGenero("Masculino");
        e1.setDni(121345);
        e1.setCiudadResidencia("Tandil");

        estudianteRepository.create(e1);

    }
}
