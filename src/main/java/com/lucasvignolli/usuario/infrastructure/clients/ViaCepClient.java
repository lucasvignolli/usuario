package com.lucasvignolli.usuario.infrastructure.clients;

import com.lucasvignolli.usuario.business.dto.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "via-cep", url = "${viacep.url}")
public interface ViaCepClient {

    @GetMapping("/ws/{cep}/json")
    ViaCepDTO buscaDadosEndereco(@RequestParam("email") String email);
}
