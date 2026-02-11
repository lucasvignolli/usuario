package com.lucasvignolli.usuario.infrastructure.exceptions;

import javax.naming.AuthenticationException;

public class IllegalArgumentException extends RuntimeException {

    public IllegalArgumentException(String mensagem){
        super(mensagem);
    }

    public IllegalArgumentException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
