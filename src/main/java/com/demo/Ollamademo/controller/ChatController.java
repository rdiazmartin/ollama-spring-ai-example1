package com.demo.Ollamademo.controller;

import com.demo.Ollamademo.model.ValorationResponse;
import com.demo.Ollamademo.service.OllamaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    OllamaService service;

    public ChatController(OllamaService ollamaService) {
        this.service = ollamaService;
    }


    @GetMapping("/ia/chat")
    public String chat(@RequestParam(value = "mensaje", defaultValue = "Explícame qué es la computación cuántica en una frase.") String mensaje) {
        // Usamos la API fluida de ChatClient para construir y ejecutar la petición.
        return service.chat(mensaje);
    }

    @GetMapping("/ia/valoration")
    public ValorationResponse valoration(@RequestParam(value = "valoration", defaultValue = "es todo muy positivo porque si") String mensaje) {
        // Usamos la API fluida de ChatClient para construir y ejecutar la petición.
        return service.valoration(mensaje);
    }


}
