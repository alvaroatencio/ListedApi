package com.asj.listed.repositories;

import com.asj.listed.business.entities.Titular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitularesRepository extends JpaRepository<Titular,Long> {
}
