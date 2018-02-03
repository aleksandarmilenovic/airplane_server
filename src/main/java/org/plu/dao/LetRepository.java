package org.plu.dao;

import org.plu.entities.Let;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetRepository extends JpaRepository<Let,Integer>{ }
