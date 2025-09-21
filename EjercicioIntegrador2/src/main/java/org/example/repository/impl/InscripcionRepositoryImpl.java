package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.repository.InscripcionRepository;

public class InscripcionRepositoryImpl implements InscripcionRepository {
    private EntityManager em;

    public InscripcionRepositoryImpl(EntityManager em) {
        this.em = em;
    }
}
