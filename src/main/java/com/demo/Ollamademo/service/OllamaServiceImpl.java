package com.demo.Ollamademo.service;

import com.demo.Ollamademo.model.ValorationResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OllamaServiceImpl implements OllamaService {

    private final ChatClient chatClient;
    private final BeanOutputConverter<ValorationResponse> outputConverter = new BeanOutputConverter<>(ValorationResponse.class);
    // Cargamos la nueva plantilla, que es más limpia.
    @Value("classpath:/templates/prompt_review.st")
    private Resource sentimentPromptResource;

    // Configuramos el ChatClient para que siempre solicite formato JSON al modelo.
    public OllamaServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @Override
    public String chat(String message) {
        // Usamos la API fluida de ChatClient para construir y ejecutar la petición.
        return chatClient.prompt()
                .user(message) // Contenido del prompt del usuario.
                .call()
                .content(); // Extraemos el contenido de la respuesta.
    }

    @Override
    public ValorationResponse valoration(String review) {
        // Obtenemos las instrucciones de formato del conversor.
        String format = outputConverter.getFormat();

        // Creamos la plantilla y el mapa de valores.
        PromptTemplate promptTemplate = new PromptTemplate(sentimentPromptResource);
        Map<String, Object> model = Map.of(
                "review", review,
                "format", format // Pasamos las instrucciones de formato a la plantilla.
        );

        Prompt finalPrompt = promptTemplate.create(model);

        // Hacemos la llamada al modelo.
        ChatResponse chatResponse = chatClient.prompt(finalPrompt).call().chatResponse();

        // Usamos el conversor para parsear la respuesta.
        // Este método es mucho más robusto que el parseo manual.
        String rawResponse = chatResponse.getResult().getOutput().getContent();

        try {
            // Usamos el conversor para parsear la respuesta.
            return outputConverter.convert(rawResponse);
        } catch (Exception e) {
            // Si el parseo falla, lanzamos una excepción que incluye la respuesta
            // cruda del modelo para poder depurar qué está enviando exactamente.
            throw new RuntimeException("No se pudo parsear la respuesta del modelo. Respuesta recibida: \n" + rawResponse, e);
        }
    }

}
