package com.lucasvignolli.usuario.infrastructure.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String mensagem){
        super(mensagem);
    }

    public ResourceNotFoundException (String mensangem, Throwable throwable){
        super(mensangem, throwable);
    }
}
