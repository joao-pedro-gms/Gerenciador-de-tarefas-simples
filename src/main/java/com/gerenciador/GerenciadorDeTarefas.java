package com.gerenciador;

import java.util.List;

public class GerenciadorDeTarefas {

    private TarefaDAO tarefaDAO;

    public GerenciadorDeTarefas() {
        this.tarefaDAO = new TarefaDAO();
    }

    public void adicionarTarefa(String titulo, String descricao, Prioridade prioridade) {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(titulo);
        tarefa.setDescricao(descricao);
        tarefa.setPrioridade(prioridade);
        tarefa.setStatus(StatusTarefa.PENDENTE);
        tarefaDAO.inserir(tarefa);
    }

    public List<Tarefa> listarTarefas(StatusTarefa status) {
        return tarefaDAO.buscarPorStatus(status);
    }

    public boolean marcarTarefaComoConcluida(int id) {
        Tarefa tarefa = tarefaDAO.buscarPorId(id);
        if (tarefa != null) {
            tarefa.setStatus(StatusTarefa.CONCLUIDA);
            return tarefaDAO.atualizar(tarefa);
        }
        return false;
    }

    public boolean marcarTarefaComoPendente(int id) {
        Tarefa tarefa = tarefaDAO.buscarPorId(id);
        if (tarefa != null) {
            tarefa.setStatus(StatusTarefa.PENDENTE);
            return tarefaDAO.atualizar(tarefa);
        }
        return false;
    }

    public Tarefa buscarTarefa(int id) {
        return tarefaDAO.buscarPorId(id);
    }

    public boolean atualizarTarefa(Tarefa tarefa) {
        return tarefaDAO.atualizar(tarefa);
    }

    public boolean removerTarefa(int id) {
        return tarefaDAO.remover(id);
    }
}
