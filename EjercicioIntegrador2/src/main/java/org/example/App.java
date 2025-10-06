package org.example;

import jakarta.persistence.EntityManagerFactory;
import org.example.dto.ReporteDTO;
import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Inscripcion;
import org.example.entity.InscripcionId;
import org.example.factory.JPAUtil;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.InscripcionRepository;
import org.example.repository.impl.CarreraRepositoryImpl;
import org.example.repository.impl.EstudianteRepositoryImpl;
import org.example.repository.impl.InscripcionRepositoryImpl;
import org.example.utils.DBUtils;

import java.io.IOException;
import java.time.LocalDate;


public class App {
    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        DBUtils dbUtils = new DBUtils();

        // SINGLETON (el repo, no el entityManager -> NO thread-safe)
        EstudianteRepository estudianteRepository = EstudianteRepositoryImpl.getInstance(emf);
        CarreraRepository carreraRepository = CarreraRepositoryImpl.getInstance(emf);
        InscripcionRepository inscripcionRepository = InscripcionRepositoryImpl.getInstance(emf);

        /* --------------------------- CARGA DE ARCHIVOS CSV -------------------------- */
        //dbUtils.addCarrera(carreraRepository);
        //dbUtils.addEstudiante(estudianteRepository);
        //dbUtils.addInscripcion(inscripcionRepository);

        /* --------------------------- CONSIGNAS --------------------------- */
        // A) Dar de alta un estudiante
        /*Estudiante e1 = new Estudiante();
        e1.setNombre("Roberto");
        e1.setApellido("Lopez");
        e1.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e1.setGenero("Masculino");
        e1.setDni(12345678);
        e1.setCiudadResidencia("Tandil");
        e1.setLU(1234);
        estudianteRepository.create(e1);*/

        // B) Matricular un estudiante en una carrera
        // inscripcionRepository.create(12345678,1, null ,null);
        // inscripcionRepository.create(27532732,2, null, null);

        // C) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple:
        // por DNI de forma ascendente (menor a mayor)
        // estudianteRepository.findAllOrderByDniAsc().forEach(System.out::println);

        // D) Recuperar un estudiante, en base a su número de libreta universitaria.
        // estudianteRepository.findByLU(1);
        // System.out.println(estudianteRepository.findByLU(59652));

        // E) Recuperar todos los estudiantes, en base a su género.
        // estudianteRepository.findAllByGenero("Female").forEach(System.out::println);

        // F) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
        // carreraRepository.findCarreraOrderByCantInscriptos().forEach(System.out::println);

        // G) Recuperar los estudiantes de una determinada carrera, filtrando por ciudad de residencia.
        // estudianteRepository.findAllEstudianteByCarreraAndCiudad("TUDAI", "Rauch").forEach(System.out::println);

        // 3) Generar reporte de carreras
         carreraRepository.generarReporte().forEach(System.out::println);



        /* --------------------------- PRUEBAS DEL CRUD --------------------------- */
        // estudianteRepository.delete(2);
        // System.out.println(carreraRepository.findById(1));
        // carreraRepository.delete(2);
        // System.out.println(inscripcionRepository.findById(1, 4));
        /*Inscripcion inscripcionABorrar = inscripcionRepository.findById(1,1);
        inscripcionRepository.delete(inscripcionABorrar);*/


/*        Estudiante e2 = new Estudiante();
        e2.setNombre("Pepe");
        e2.setApellido("Lopez");
        e2.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e2.setGenero("Masculino");
        e2.setDni(255556);
        e2.setCiudadResidencia("Azul");
        e2.setLU(55555)
        estudianteRepository.create(e2);

        Estudiante e3 = new Estudiante();
        e3.setNombre("Martita");
        e3.setApellido("Sanchez");
        e3.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e3.setGenero("Femenino");
        e3.setDni(336987);
        e3.setCiudadResidencia("Tandil");
        e3.setLU(666666)
        estudianteRepository.create(e3);

        Estudiante e4 = new Estudiante();
        e4.setNombre("Olga");
        e4.setApellido("Garcia");
        e4.setFechaNacimiento(LocalDate.of(2000, 5, 21));
        e4.setGenero("Femenino");
        e4.setDni(102255);
        e4.setCiudadResidencia("Tandil");
        e4.setLU(777777)
        estudianteRepository.create(e4);

        Carrera c1 = new Carrera();
        c1.setNombre("TUDAI");
        c1.setDuracion(2);
        carreraRepository.create(c1);

        Carrera c2 = new Carrera();
        c2.setNombre("INGENIERIA EN SISTEMAS");
        c2.setDuracion(5);
        carreraRepository.create(c2);

*/

    }
}


/* EntityManagerFactory → SI es thread-safe →
se puede tener una sola instancia para toda la aplicación (Singleton).

EntityManager → NO thread-safe →
cada thread / transacción debe crear su propia instancia y cerrarla después de usarla.*/