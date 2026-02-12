package com.lucasvignolli.usuario.infrastructure.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnauthorizedExceptions extends AuthenticationException {

    public UnauthorizedExceptions(String mensagem){
        super(mensagem);
    }

    public UnauthorizedExceptions(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
