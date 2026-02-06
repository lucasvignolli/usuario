package com.lucasvignolli.usuario.infrastructure.exceptions;

import javax.naming.AuthenticationException;

public class UnauthorizedExceptions extends AuthenticationException {

    public UnauthorizedExceptions(String mensagem){
        super(mensagem);
    }

    public UnauthorizedExceptions(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
