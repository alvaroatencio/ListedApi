package com.asj.listed.repositories;

import com.asj.listed.exceptions.ErrorProcessException;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface Repositories<T,R> {
    List<T> findAll() throws ErrorProcessException;
    T findById(long id)throws ErrorProcessException;
    T add(R t) throws ErrorProcessException;
    T update(long id, R t) throws ErrorProcessException;
    T delete(long id) throws ErrorProcessException;
}
