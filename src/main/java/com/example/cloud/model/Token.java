package com.example.cloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Token {
    @JsonProperty("auth-token")
    private String value;

    public Token(String value) {
        this.value = value;
    }
}
