package com.asj.listed.services.intefaces;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.request.UserRequest;
import com.asj.listed.model.response.UserResponse;
import com.asj.listed.repositories.Repositories;

public interface UserService extends Repositories<UserResponse, UserRequest> {
    UserResponse buscarUsuario(String usuarioOmail) throws ErrorProcessException;
}
