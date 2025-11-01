package org.example.monopatin.repository;

import org.example.monopatin.entity.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MonopatinRepository extends MongoRepository<Monopatin, String> {



}
