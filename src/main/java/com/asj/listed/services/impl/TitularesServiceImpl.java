package com.asj.listed.services.impl;

import com.asj.listed.business.entities.Titulares;
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
    public List<Titulares> listarTodos() {
        return repo.findAll();
    }

    @Override
    public Optional<Titulares> buscarPorId(int id) {
        return repo.findById(id);
    }

    @Override
    public Titulares crear(Titulares titular) {
        return repo.save(titular);
    }

    @Override
    public Titulares actualizar(int id, Titulares titular) {
        //// TODO: 21/3/2023  falta code
        return repo.save(titular);
    }

    @Override
    public Optional<Titulares> eliminar(int id) {
        Optional<Titulares> titularBorrado = repo.findById(id);
        repo.deleteById(id);
        return titularBorrado;
    }
}
