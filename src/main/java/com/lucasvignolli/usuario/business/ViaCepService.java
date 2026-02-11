package com.lucasvignolli.usuario.business;

import com.lucasvignolli.usuario.business.dto.ViaCepDTO;
import com.lucasvignolli.usuario.infrastructure.clients.ViaCepClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor

public class ViaCepService {

    private final ViaCepClient client;

    public ViaCepDTO buscarDadosEndereco(String cep){
        return client.buscaDadosEndereco(processarCep(cep));
    }

    private String processarCep(String cep){
        String cepFormato = cep.replace(" ", "").replace("-","");
        if (!cepFormato.matches("\\d+") || !Objects.equals(cepFormato.length(), 8)){
            throw new IllegalArgumentException("O CEP contém caracteres inválidos, favor verificiar");
        }
        return cepFormato;
    }
}
