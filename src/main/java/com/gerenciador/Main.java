package com.gerenciador;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.init(); // Iniciar o banco
        MenuConsole menu = new MenuConsole();
        menu.iniciar();
    }
}
