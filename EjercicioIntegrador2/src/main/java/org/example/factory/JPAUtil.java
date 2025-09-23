package org.example.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    // Se inicializa una sola vez (Singleton)
    static {
        try {
            emf = Persistence.createEntityManagerFactory("RegistroEstudiantes");
        } catch (Throwable ex) {
            System.err.println("Error al crear EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Para obtener la única instancia del EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    // Para obtener un EntityManager en cualquier parte del código
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Para cerrar el factory cuando termine la transaccion
    public static void close() {
        emf.close();
    }
}
