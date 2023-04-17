package com.asj.listed.services.intefaces;


import com.asj.listed.model.request.HolderRequest;
import com.asj.listed.model.response.HolderResponse;
import com.asj.listed.repositories.Repositories;

public interface HolderService extends Repositories<HolderResponse, HolderRequest> {
}
