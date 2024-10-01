package org.example.controller;

import org.example.model.Aluno;
import org.example.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static spark.Spark.*;

public class AlunoController {

    private static AlunoService alunoService = new AlunoService();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        port(4567);

        // Endpoint para retornar todos os alunos
        get("/alunos", (req, res) -> {
            res.type("application/json");
            return objectMapper.writeValueAsString(alunoService.getAllAlunos());
        });

        // Endpoint para retornar um aluno específico por id
        get("/alunos/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            Aluno aluno = alunoService.getAlunoById(id);
            if (aluno == null) {
                res.status(404);
                return "{\"message\":\"Aluno não encontrado\"}";
            }
            return objectMapper.writeValueAsString(aluno);
        });

        // Endpoint para adicionar um novo aluno
        post("/alunos", (req, res) -> {
            res.type("application/json");
            Aluno aluno = objectMapper.readValue(req.body(), Aluno.class);
            alunoService.addAluno(aluno);
            res.status(201);
            return "{\"message\":\"Aluno adicionado com sucesso\"}";
        });

        // Endpoint para alterar um aluno específico
        put("/alunos/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            Aluno alunoAtualizado = objectMapper.readValue(req.body(), Aluno.class);
            boolean atualizado = alunoService.updateAluno(id, alunoAtualizado);
            if (!atualizado) {
                res.status(404);
                return "{\"message\":\"Aluno não encontrado\"}";
            }
            return "{\"message\":\"Aluno atualizado com sucesso\"}";
        });

        // Endpoint para excluir um aluno específico
        delete("/alunos/:id", (req, res) -> {
            res.type("application/json");
            int id = Integer.parseInt(req.params(":id"));
            boolean removido = alunoService.deleteAluno(id);
            if (!removido) {
                res.status(404);
                return "{\"message\":\"Aluno não encontrado\"}";
            }
            return "{\"message\":\"Aluno removido com sucesso\"}";
        });
    }
}
