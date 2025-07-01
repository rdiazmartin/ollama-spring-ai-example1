package com.demo.Ollamademo.service;

import com.demo.Ollamademo.model.ValorationResponse;

public interface OllamaService {
    public String chat(String message);
    ValorationResponse valoration(String review);
}
