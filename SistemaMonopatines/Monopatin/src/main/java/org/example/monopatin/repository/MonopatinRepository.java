package org.example.monopatin.repository;

import org.example.monopatin.entity.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MonopatinRepository extends MongoRepository<Monopatin, String> {
    @Query("{ 'latitud': { $gte: ?0, $lte: ?1 }, " +
            "'longitud': { $gte: ?2, $lte: ?3 }, " +
            "'estadoMonopatin': 'DISPONIBLE' }")
    List<Monopatin> findMonopatinesCercanos(double latMin, double latMax, double lonMin, double lonMax);

}

/**@Query("{ ... }")
 * le dice a Spring Data MongoDB que queremos ejecutar una consulta personalizada en formato JSON de MongoDB.
 * {} estructura que usaríamos si ejecutáramos una query en la consola de Mongo
 * 'latitud': { $gte: ?0, $lte: ?1 } filtra por el campo latitud, $gte-->mayor o igual que, y $lte---> menor o igual que
 * ?0 → primer parámetro del método → latMin y ?1 → segundo parámetro del método → latMax*/
