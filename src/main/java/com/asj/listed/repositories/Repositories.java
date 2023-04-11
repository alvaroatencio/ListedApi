package com.asj.listed.repositories;

import com.asj.listed.exceptions.ErrorProcessException;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
@NoRepositoryBean
public interface Repositories<T> {
    List<T> findAll() throws ErrorProcessException;
    Optional<T> buscarPorId(long id);
    T crear(T t);
    T actualizar (long id,T t);
    Optional<T> eliminar (long id);
}
