package com.asj.listed.services.impl;

import com.asj.listed.business.entities.Titular;
import com.asj.listed.repositories.TitularesRepository;
import com.asj.listed.services.intefaces.TitularesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TitularesServiceImpl implements TitularesService {
    public final TitularesRepository repo;

    public TitularesServiceImpl(TitularesRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<Titular> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Optional<Titular> buscarPorId(long id) {
        return repo.findById(id);
    }

    @Override
    public Titular crear(Titular titular) {
        return repo.save(titular);
    }

    @Override
    public Titular actualizar(long id, Titular titular) {
        //// TODO: 21/3/2023  falta code
        return repo.save(titular);
    }

    @Override
    public Optional<Titular> eliminar(long id) {
        Optional<Titular> titularBorrado = repo.findById(id);
        repo.deleteById(id);
        return titularBorrado;
    }
}
