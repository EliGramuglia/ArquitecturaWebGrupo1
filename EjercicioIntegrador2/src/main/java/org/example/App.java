package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Inscripcion;
import org.example.factory.JPAUtil;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.InscripcionRepository;
import org.example.repository.impl.CarreraRepositoryImpl;
import org.example.repository.impl.EstudianteRepositoryImpl;
import org.example.repository.impl.InscripcionRepositoryImpl;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

        // SINGLETON (el repo, no el entityManager -> NO thread-safe)
        EstudianteRepository estudianteRepository = EstudianteRepositoryImpl.getInstance(emf);
        CarreraRepository carreraRepository = CarreraRepositoryImpl.getInstance(emf);
        InscripcionRepository inscripcionRepository = InscripcionRepositoryImpl.getInstance(emf);

        // Dar de alta un estudiante
/*        Estudiante e1 = new Estudiante();
        e1.setNombre("Roberto");
        e1.setApellido("Lopez");
        e1.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e1.setGenero("Masculino");
        e1.setDni(121345);
        e1.setCiudadResidencia("Tandil");
        estudianteRepository.create(e1);

        Estudiante e2 = new Estudiante();
        e2.setNombre("Pepe");
        e2.setApellido("Lopez");
        e2.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e2.setGenero("Masculino");
        e2.setDni(255556);
        e2.setCiudadResidencia("Azul");
        estudianteRepository.create(e2);

        Estudiante e3 = new Estudiante();
        e3.setNombre("Martita");
        e3.setApellido("Lopez");
        e3.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e3.setGenero("Masculino");
        e3.setDni(336987);
        e3.setCiudadResidencia("Tandil");
        estudianteRepository.create(e3);

        Estudiante e4 = new Estudiante();
        e4.setNombre("Martita");
        e4.setApellido("Lopez");
        e4.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e4.setGenero("Masculino");
        e4.setDni(102255);
        e4.setCiudadResidencia("Tandil");
        estudianteRepository.create(e4);


        Carrera c1 = new Carrera();
        c1.setNombre("TUDAI");
        c1.setDuracion(2);
        carreraRepository.create(c1);
*/

        // Matricular un estudiante en una carrera
        inscripcionRepository.create("Pepe","TUDAI");


        /*
        //recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
        estudianteRepository.findAllOrderByDniAsc().forEach(System.out::println);
        //recuperar un estudiante, en base a su número de libreta universitaria.

        //recuperar todos los estudiantes, en base a su género.
        estudianteRepository.findAllByGenero("Femenino").forEach(System.out::println);
*/


        /**
         f) recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
         g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.*/


    }
}


/* EntityManagerFactory → SI es thread-safe →
se puede tener una sola instancia para toda la aplicación (Singleton).

EntityManager → NO thread-safe →
cada thread / transacción debe crear su propia instancia y cerrarla después de usarla.*/