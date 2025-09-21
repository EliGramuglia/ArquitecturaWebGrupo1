package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.repository.CarreraRepository;

public class CarreraRepositoryImpl implements CarreraRepository {
    private EntityManager em;

    public CarreraRepositoryImpl(EntityManager em) {
        this.em = em;
    }
}
