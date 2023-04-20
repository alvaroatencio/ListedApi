package com.asj.listed.model.request;

import com.asj.listed.model.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    private Long id;
    private String alias;
    private String banco;
    private String cbu;
    private String nroCuenta;
    private String sucursal;
    private HolderRequest titular;
    private Long usuarioId;
    public static Account toEntity(AccountRequest accountRequest) {
        return Account.builder()
                .id(accountRequest.getId())
                .alias(accountRequest.getAlias())
                .banco(accountRequest.getBanco())
                .cbu(accountRequest.getCbu())
                .nroCuenta(accountRequest.getNroCuenta())
                .sucursal(accountRequest.getSucursal())
                .build();
    }
}
