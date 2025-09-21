package org.example;

import jakarta.persistence.EntityManager;
import org.example.factory.JPAUtil;

public class App {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        //persistencia....
    }
}
