package com.demo.Ollamademo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final ChatClient chatClient;

    /**
     * El starter de Spring AI configura automáticamente un ChatClient.Builder.
     * Lo inyectamos aquí a través del constructor para crear nuestro cliente.
     */
    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Este endpoint recibe una petición GET, la envía al modelo de IA
     * y devuelve la respuesta generada.
     *
     * @param mensaje El texto que se enviará al modelo.
     * @return La respuesta del modelo de IA.
     */
    @GetMapping("/ia/chat")
    public String chat(@RequestParam(value = "mensaje", defaultValue = "Explícame qué es la computación cuántica en una frase.") String mensaje) {
        // Usamos la API fluida de ChatClient para construir y ejecutar la petición.
        return chatClient.prompt()
                .user(mensaje) // Contenido del prompt del usuario.
                .call()
                .content(); // Extraemos el contenido de la respuesta.
    }

}
