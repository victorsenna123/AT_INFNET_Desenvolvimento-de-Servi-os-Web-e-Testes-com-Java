package org.example.service;

import org.example.model.Aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoService {

    private List<Aluno> alunos = new ArrayList<>();

    public AlunoService() {
        // Inicializando a lista com alguns alunos
        alunos.add(new Aluno(1, "João", 8.5));
        alunos.add(new Aluno(2, "Maria", 9.0));
        alunos.add(new Aluno(3, "Pedro", 7.8));
    }

    // Retorna todos os alunos
    public List<Aluno> getAllAlunos() {
        return alunos;
    }

    // Retorna um aluno por id
    public Aluno getAlunoById(int id) {
        return alunos.stream().filter(aluno -> aluno.getId() == id).findFirst().orElse(null);
    }

    // Altera um aluno existente
    public boolean updateAluno(int id, Aluno alunoAtualizado) {
        Optional<Aluno> alunoOpt = alunos.stream().filter(aluno -> aluno.getId() == id).findFirst();
        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            aluno.setNome(alunoAtualizado.getNome());
            aluno.setNota(alunoAtualizado.getNota());
            return true;
        }
        return false;
    }

    // Exclui um aluno por id
    public boolean deleteAluno(int id) {
        return alunos.removeIf(aluno -> aluno.getId() == id);
    }

    // Adiciona um novo aluno
    public void addAluno(Aluno aluno) {
        alunos.add(aluno);
    }
}
