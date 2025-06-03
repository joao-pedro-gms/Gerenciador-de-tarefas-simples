package com.gerenciador;

import java.util.List;
import java.util.Scanner;

public class MenuConsole {
    private GerenciadorDeTarefas gerenciadorDeTarefas;
    private Scanner scanner;

    public MenuConsole() {
        this.gerenciadorDeTarefas = new GerenciadorDeTarefas();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuPrincipal() {
        System.out.println("\n===== GERENCIADOR DE TAREFAS =====");
        System.out.println("1. Cadastrar nova tarefa");
        System.out.println("2. Listar tarefas pendentes");
        System.out.println("3. Listar tarefas concluídas");
        System.out.println("4. Marcar tarefa como concluída");
        System.out.println("5. Marcar tarefa como pendente");
        System.out.println("6. Atualizar tarefa");
        System.out.println("7. Remover tarefa");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void processarOpcaoDoUsuario(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarTarefa();
            case 2 -> listarTarefas(StatusTarefa.PENDENTE);
            case 3 -> listarTarefas(StatusTarefa.CONCLUIDA);
            case 4 -> marcarTarefaComoConcluida();
            case 5 -> marcarTarefaComoPendente();
            case 6 -> atualizarTarefa();
            case 7 -> removerTarefa();
            case 0 -> System.out.println("Saindo do sistema. Até logo!");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public void iniciar() {
        int opcao;
        do {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }
            processarOpcaoDoUsuario(opcao);
        } while (opcao != 0);
    }

    // Métodos auxiliares

    private void cadastrarTarefa() {
        System.out.print("Título da tarefa: ");
        String titulo = scanner.nextLine();

        System.out.print("Descrição da tarefa: ");
        String descricao = scanner.nextLine();

        System.out.print("Prioridade (1- BAIXA, 2- MEDIA, 3- ALTA): ");
        String prioridadeStr = scanner.nextLine();
        Prioridade prioridade;
        switch (prioridadeStr) {
            case "1":
                prioridade = Prioridade.BAIXA;
                break;
            case "2":
                prioridade = Prioridade.MEDIA;
                break;
            case "3":
                prioridade = Prioridade.ALTA;
                break;
            default:
                System.out.println("Prioridade inválida. Usando MEDIA.");
                prioridade = Prioridade.MEDIA;
        }

        gerenciadorDeTarefas.adicionarTarefa(titulo, descricao, prioridade);
        System.out.println("Tarefa cadastrada com sucesso!");
    }

    private void listarTarefas(StatusTarefa status) {
        List<Tarefa> tarefas = gerenciadorDeTarefas.listarTarefas(status);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            for (Tarefa t : tarefas) {
                System.out.printf("ID: %d | Título: %s | Descrição: %s | Prioridade: %s | Status: %s | Data de Criação: %s | Data de Conclusão: %s\n",
                        t.getId(), t.getTitulo(), t.getDescricao() ,t.getPrioridade(), t.getStatus(), t.getDataCriacao(), t.getDataConclusao());
            }
        }
    }

    private void marcarTarefaComoConcluida() {
        System.out.print("ID da tarefa a ser marcada como concluída: ");
        int id = lerId();
        if (gerenciadorDeTarefas.marcarTarefaComoConcluida(id)) {
            System.out.println("Tarefa marcada como concluída.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private void marcarTarefaComoPendente() {
        System.out.print("ID da tarefa a ser marcada como pendente: ");
        int id = lerId();
        if (gerenciadorDeTarefas.marcarTarefaComoPendente(id)) {
            System.out.println("Tarefa marcada como pendente.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private void atualizarTarefa() {
        System.out.print("ID da tarefa a ser atualizada: ");
        int id = lerId();

        Tarefa tarefa = gerenciadorDeTarefas.buscarTarefa(id);
        if (tarefa == null) {
            System.out.println("Tarefa não encontrada.");
            return;
        }

        System.out.print("Novo título (ou pressione Enter para manter): ");
        String novoTitulo = scanner.nextLine();
        if (!novoTitulo.isBlank()) tarefa.setTitulo(novoTitulo);

        System.out.print("Nova descrição (ou pressione Enter para manter): ");
        String novaDescricao = scanner.nextLine();
        if (!novaDescricao.isBlank()) tarefa.setDescricao(novaDescricao);

        System.out.print("Nova prioridade (BAIXA, MEDIA, ALTA) ou Enter para manter: ");
        String novaPrioridadeStr = scanner.nextLine().toUpperCase();
        if (!novaPrioridadeStr.isBlank()) {
            try {
                tarefa.setPrioridade(Prioridade.valueOf(novaPrioridadeStr));
            } catch (Exception e) {
                System.out.println("Prioridade inválida. Mantendo a atual.");
            }
        }

        if (gerenciadorDeTarefas.atualizarTarefa(tarefa)) {
            System.out.println("Tarefa atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar a tarefa.");
        }
    }

    private void removerTarefa() {
        System.out.print("ID da tarefa a ser removida: ");
        int id = lerId();
        if (gerenciadorDeTarefas.removerTarefa(id)) {
            System.out.println("Tarefa removida com sucesso!");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private int lerId() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Digite um número inteiro.");
            return -1;
        }
    }
}
