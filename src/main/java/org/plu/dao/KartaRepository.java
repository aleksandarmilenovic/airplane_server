package org.plu.dao;

import org.plu.entities.Karta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KartaRepository extends JpaRepository<Karta,Integer>{
}
