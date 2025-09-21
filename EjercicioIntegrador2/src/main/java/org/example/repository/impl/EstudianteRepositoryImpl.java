package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.repository.EstudianteRepository;

public class EstudianteRepositoryImpl implements EstudianteRepository {
    private EntityManager em;

    public EstudianteRepositoryImpl(EntityManager em) {
        this.em = em;
    }
}
