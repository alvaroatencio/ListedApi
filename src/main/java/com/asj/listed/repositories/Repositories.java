package com.asj.listed.repositories;

import java.util.List;
import java.util.Optional;
public interface Repositories<T> {
    List<T> listarTodos();
    Optional<T> buscarPorId(int id);
    T crear(T t);
    T actualizar (int id,T t);
    Optional<T> eliminar (int id);
}
