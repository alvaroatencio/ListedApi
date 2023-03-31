package com.asj.listed.repositories;

import java.util.List;
import java.util.Optional;
public interface Repositories<T> {
    List<T> listarTodos();
    Optional<T> buscarPorId(long id);
    T crear(T t);
    T actualizar (long id,T t);
    Optional<T> eliminar (long id);
}
