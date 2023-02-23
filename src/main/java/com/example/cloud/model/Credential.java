package com.example.cloud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table (name = "credential")
@AllArgsConstructor
@NoArgsConstructor
public class Credential implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String login;
    private char @NonNull [] password;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Credential{" +
                ", login='" + login + '\'' +
                ", password=" + Arrays.toString(password) +
                '}';
    }
}
