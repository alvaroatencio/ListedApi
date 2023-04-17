package com.asj.listed.services.impl;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.entities.Account;
import com.asj.listed.model.request.AccountRequest;
import com.asj.listed.model.response.AccountResponse;
import com.asj.listed.repositories.AccountRepository;
import com.asj.listed.services.intefaces.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.asj.listed.exceptions.response.ErrorResponse.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository cuentaRepository;

    @Override
    public List<AccountResponse> findAll() throws ErrorProcessException {
        try {
            System.out.println("AccountServiceImpl.findAll 1");
            List<AccountResponse> ac = cuentaRepository.findAll()
                    .stream()
                    .map(AccountResponse::toResponse)
                    .collect(Collectors.toList());
            System.out.println("AccountServiceImpl.findAll 2");
            return ac;
        } catch(Exception e){
            System.out.println("AccountServiceImpl.findAll 3");
            return null;
        }
    }

    @Override
    public AccountResponse findById(long id) throws ErrorProcessException {
        Account cuentas = cuentaRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Account with id %s does not exist", id)));
        try {
            return AccountResponse.toResponse(cuentas);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    //// TODO: 12/4/2023  ARREGLAR EN ESTOS METODOS LAS RELACIONES
    @Override
    public AccountResponse add(AccountRequest accountRequest) throws ErrorProcessException {
        log.info("Account creation request received");
        try {
            log.info("Creating account: {}", accountRequest);
            return AccountResponse.toResponse(cuentaRepository.save(AccountRequest.toEntity(accountRequest)));
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public AccountResponse update(long id, AccountRequest accountRequest) throws ErrorProcessException {
        Account account = cuentaRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Account with id %s does not exist", id)));
        if (!StringUtils.isEmpty(accountRequest.getNroCuenta()))
            account.setNroCuenta(accountRequest.getNroCuenta());
        if (!StringUtils.isEmpty(accountRequest.getCbu()))
            account.setCbu(accountRequest.getCbu());
        if (!StringUtils.isEmpty(accountRequest.getSucursal()))
            account.setSucursal(account.getSucursal());
        if (!StringUtils.isEmpty(accountRequest.getBanco()))
            account.setBanco(accountRequest.getBanco());
        if (!StringUtils.isEmpty(accountRequest.getAlias()))
            account.setAlias(accountRequest.getAlias());
        try {
            return AccountResponse.toResponse(cuentaRepository.save(account));
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public AccountResponse delete(long id) throws ErrorProcessException {
        Account accountAEliminar = cuentaRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Account with id %s does not exist", id)));
        try {
            cuentaRepository.delete(accountAEliminar);
            return AccountResponse.toResponse(accountAEliminar);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public List<AccountResponse> findByUserId(long id_usuario) throws ErrorProcessException {
        try {
            return cuentaRepository.findByUsuario_Id(id_usuario);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
}
