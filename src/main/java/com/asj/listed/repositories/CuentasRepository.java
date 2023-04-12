package com.asj.listed.repositories;

import com.asj.listed.model.entities.Cuenta;
import com.asj.listed.model.response.CuentasResponse;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CuentasRepository extends JpaRepository<Cuenta,Long>, JpaSpecificationExecutor<Cuenta> {
    List<CuentasResponse> findByUsuario_Id(long id_usuario);

    //// TODO: 11/4/2023  revisar, puede que tenga que modificar los root
    static Specification<Cuenta> bancoOrTitular_NombresOrTitular_Email1OrTitular_Email2OrAlias(String search){
        return((root, query, criteriaBuilder) -> {
            String pattern = "%" + search + "%";
            Predicate bancoPredicate = criteriaBuilder.like(root.get("banco"),pattern);
            Predicate titular_NombresPredicate = criteriaBuilder.like(root.get("titular").get("nombres"),pattern);
            Predicate titular_Email1Predicate = criteriaBuilder.like(root.get("titular").get("mail1"),pattern);
            Predicate titular_Email2Predicate = criteriaBuilder.like(root.get("Titular_Mail2"),pattern);
            Predicate aliasPredicate = criteriaBuilder.like(root.get("alias"),pattern);
            return criteriaBuilder
                    .or(bancoPredicate,titular_NombresPredicate,titular_Email1Predicate,titular_Email2Predicate,aliasPredicate);
        });
    }
}
