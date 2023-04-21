package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.request.AccountRequest;
import com.asj.listed.model.response.ResponseUtils;
import com.asj.listed.services.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl service;
    @PreAuthorize("hasRole('ADMIN')or hasRole('USUARIO')")
    @GetMapping()
    public ResponseEntity<?> findAllAccounts() throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                    "Cuentas buscadas exitosamente",
                    service.findAll());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<?> findAccountById(@PathVariable int id) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Cuentas buscadas exitosamente",
                service.findById(id));
    }
    @PreAuthorize("hasRole('ADMIN')or hasRole('USUARIO')")
    @PostMapping()
    public ResponseEntity<?> addAccount(@RequestBody AccountRequest accountRequest) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Cuenta creada exitosamente",
                service.add(accountRequest));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") Integer id, @RequestBody AccountRequest usuarioDTO) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Cuenta actualizada exitosamente",
                service.update(id, usuarioDTO));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable int id) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Cuenta actualizada exitosamente",
                service.delete(id));
    }
}
