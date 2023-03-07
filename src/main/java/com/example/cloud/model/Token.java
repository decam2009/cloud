package com.example.cloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
    @JsonProperty("auth-token")
    private String value;

    public Token(String value) {
        this.value = value;
    }
}
