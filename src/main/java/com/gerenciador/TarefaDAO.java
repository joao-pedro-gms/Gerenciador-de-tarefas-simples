package com.gerenciador;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public void inserir(Tarefa tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, prioridade, status, dataCriacao, dataConclusao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tarefa.getTitulo());
            pstmt.setString(2, tarefa.getDescricao());
            pstmt.setString(3, tarefa.getPrioridade().name());
            pstmt.setString(4, tarefa.getStatus().name());
            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setTimestamp(6, tarefa.getDataConclusao());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tarefa buscarPorId(int id) {
        String sql = "SELECT * FROM tarefas WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setId(rs.getInt("id"));
                    tarefa.setTitulo(rs.getString("titulo"));
                    tarefa.setDescricao(rs.getString("descricao"));
                    tarefa.setPrioridade(Prioridade.valueOf(rs.getString("prioridade")));
                    tarefa.setStatus(StatusTarefa.valueOf(rs.getString("status")));
                    tarefa.setDataCriacao(rs.getTimestamp("dataCriacao"));
                    tarefa.setDataConclusao(rs.getTimestamp("dataConclusao"));
                    return tarefa;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Tarefa> buscarPorStatus(StatusTarefa status) {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas WHERE status = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setId(rs.getInt("id"));
                    tarefa.setTitulo(rs.getString("titulo"));
                    tarefa.setDescricao(rs.getString("descricao"));
                    tarefa.setPrioridade(Prioridade.valueOf(rs.getString("prioridade")));
                    tarefa.setStatus(StatusTarefa.valueOf(rs.getString("status")));
                    tarefa.setDataCriacao(rs.getTimestamp("dataCriacao"));
                    tarefa.setDataConclusao(rs.getTimestamp("dataConclusao"));
                    lista.add(tarefa);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean atualizar(Tarefa tarefa) {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, prioridade = ?, status = ?, dataConclusao = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tarefa.getTitulo());
            pstmt.setString(2, tarefa.getDescricao());
            pstmt.setString(3, tarefa.getPrioridade().name());
            pstmt.setString(4, tarefa.getStatus().name());
            pstmt.setTimestamp(5, tarefa.getDataConclusao());
            pstmt.setInt(6, tarefa.getId());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Tarefa> listarTodas() {
        List<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setPrioridade(Prioridade.valueOf(rs.getString("prioridade")));
                tarefa.setStatus(StatusTarefa.valueOf(rs.getString("status")));
                tarefa.setDataCriacao(rs.getTimestamp("dataCriacao"));
                tarefa.setDataConclusao(rs.getTimestamp("dataConclusao"));
                lista.add(tarefa);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
