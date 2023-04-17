package com.asj.listed.model.response;

import com.asj.listed.model.entities.Account;
import com.asj.listed.model.entities.Holder;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private String alias;
    private String banco;
    private String cbu;
    private String nroCuenta;
    private String sucursal;
    private Holder holder;
    private Long usuarioId;

    public static AccountResponse toResponse(Account account){
        return AccountResponse.builder()
                .id(account.getId())
                .alias(account.getAlias())
                .banco(account.getBanco())
                .cbu(account.getCbu())
                .nroCuenta(account.getNroCuenta())
                .sucursal(account.getSucursal())
                .holder(account.getTitular())
                .usuarioId(account.getUsuario().getId())
                .build();
    }
}
