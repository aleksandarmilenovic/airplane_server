package org.plu.dao;

import org.plu.entities.Aerodrom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AerodromRepository extends JpaRepository<Aerodrom,Integer>{ }
