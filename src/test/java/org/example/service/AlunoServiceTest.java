package org.example.service;

import org.example.model.Aluno;
import org.example.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;

public class AlunoServiceTest {

    private AlunoService alunoService;
    private static final String BASE_URL = "http://localhost:4567/alunos";

    @BeforeEach
    public void setUp() {
        alunoService = new AlunoService();
    }

    @Test
    public void alunoServiceAlunoIdEncontradoTest() {
        Aluno aluno = alunoService.getAlunoById(1);
        assertNotNull(aluno);
        assertEquals(1, aluno.getId());
        assertEquals("João", aluno.getNome());
    }

    @Test
    public void alunoServiceAlunoIdNaoEncontradoTest() {
        Aluno aluno = alunoService.getAlunoById(999);
        assertNull(aluno);
    }

    @Test
    public void testConsultarAlunoStatusCode() throws IOException {
        URL url = new URL(BASE_URL + "/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        connection.disconnect();
    }

    @Test
    public void testIncluirAlunoStatusCode() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = "{\"id\": 3, \"nome\": \"Maria\", \"nota\": 8.5}";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        assertEquals(201, responseCode);

        connection.disconnect();
    }
}
