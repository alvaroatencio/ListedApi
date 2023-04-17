package com.asj.listed.services.intefaces;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.request.AccountRequest;
import com.asj.listed.model.response.AccountResponse;
import com.asj.listed.repositories.Repositories;

import java.util.List;

public interface AccountService extends Repositories<AccountResponse, AccountRequest> {
    List<AccountResponse> findByUserId(long id) throws ErrorProcessException;
}
