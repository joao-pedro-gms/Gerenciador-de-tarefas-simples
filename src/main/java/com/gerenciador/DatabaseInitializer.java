package com.gerenciador;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void init() {
        String sql = """
            CREATE TABLE IF NOT EXISTS tarefas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                descricao TEXT,
                prioridade TEXT,
                status TEXT,
                dataCriacao TIMESTAMP,
                dataConclusao TIMESTAMP
            );
        """;

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
