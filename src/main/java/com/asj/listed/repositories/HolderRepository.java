package com.asj.listed.repositories;

import com.asj.listed.model.entities.Holder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolderRepository extends JpaRepository<Holder,Long> {
}
