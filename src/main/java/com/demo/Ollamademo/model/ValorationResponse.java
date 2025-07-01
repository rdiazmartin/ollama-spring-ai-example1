package com.demo.Ollamademo.model;

import java.util.List;

public record ValorationResponse(
        double score,
        List<String> positiveReasons,
        List<String> negativeReasons
) {}
