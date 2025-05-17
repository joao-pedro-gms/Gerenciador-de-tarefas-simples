import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class MenuConsole {
    private GerenciadorDeTarefas gerenciadorDeTarefas;
    private Scanner scanner;

    public MenuConsole() {
        this.gerenciadorDeTarefas = new GerenciadorDeTarefas();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuPrincipal() {
        System.out.println("===== GERENCIADOR DE TAREFAS =====");
        System.out.println("1. Cadastrar nova tarefa");
        System.out.println("2. Listar tarefas pendentes");
        System.out.println("3. Listar tarefas concluídas");
        System.out.println("4. Marcar tarefa como concluída");
        System.out.println("5. Marcar tarefa como pendente");
        System.out.println("6. Atualizar tarefa");
        System.out.println("7. Remover tarefa");
        System.out.println("8. Salvar tarefas");
        System.out.println("9. Carregar tarefas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void processarOpcaoDoUsuario(int opcao) {
        switch (opcao) {
            case 1:
                cadastrarTarefa();
                break;
            case 2:
                listarTarefas(StatusTarefa.PENDENTE);
                break;
            case 3:
                listarTarefas(StatusTarefa.CONCLUIDA);
                break;
            case 4:
                marcarTarefaComoConcluida();
                break;
            case 5:
                marcarTarefaComoPendente();
                break;
            case 6:
                atualizarTarefa();
                break;
            case 7:
                removerTarefa();
                break;
            case 8:
                salvarTarefas();
                break;
            case 9:
                carregarTarefas();
                break;
            case 0:
                System.out.println("Saindo do sistema. Até logo!");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public void iniciar() {
        int opcao = -1;
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
        System.out.print("Descrição da tarefa: ");
        String descricao = scanner.nextLine();

        System.out.print("Data de vencimento (AAAA-MM-DD) ou deixe em branco: ");
        String dataStr = scanner.nextLine();
        java.time.LocalDate dataVencimento = null;
        if (!dataStr.trim().isEmpty()) {
            try {
                dataVencimento = java.time.LocalDate.parse(dataStr);
            } catch (Exception e) {
                System.out.println("Data inválida. Tarefa será cadastrada sem data de vencimento.");
            }
        }

        System.out.print("Prioridade (BAIXA, MEDIA, ALTA): ");
        String prioridadeStr = scanner.nextLine().toUpperCase();
        Prioridade prioridade;
        try {
            prioridade = Prioridade.valueOf(prioridadeStr);
        } catch (Exception e) {
            System.out.println("Prioridade inválida. Usando prioridade MEDIA.");
            prioridade = Prioridade.MEDIA;
        }

        gerenciadorDeTarefas.adicionarTarefa(descricao, dataVencimento, prioridade);
        System.out.println("Tarefa cadastrada com sucesso!");
    }

    private void listarTarefas(StatusTarefa status) {
        java.util.List<Tarefa> tarefas = gerenciadorDeTarefas.listarTarefas(status);
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            for (Tarefa t : tarefas) {
                System.out.println(t);
            }
        }
    }

    private void marcarTarefaComoConcluida() {
        System.out.print("ID da tarefa a ser marcada como concluída: ");
        long id = lerId();
        if (gerenciadorDeTarefas.marcarTarefaComoConcluida(id)) {
            System.out.println("Tarefa marcada como concluída.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private void marcarTarefaComoPendente() {
        System.out.print("ID da tarefa a ser marcada como pendente: ");
        long id = lerId();
        if (gerenciadorDeTarefas.marcarTarefaComoPendente(id)) {
            System.out.println("Tarefa marcada como pendente.");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private void atualizarTarefa() {
        System.out.print("ID da tarefa a ser atualizada: ");
        long id = lerId();
        
        System.out.print("Nova descrição: ");
        String novaDescricao = scanner.nextLine();

        System.out.print("Nova data de vencimento (AAAA-MM-DD) ou deixe em branco: ");
        String dataStr = scanner.nextLine();
        LocalDate novaDataVencimento = null;
        if (!dataStr.trim().isEmpty()) {
            try {
                novaDataVencimento = LocalDate.parse(dataStr);
            } catch (Exception e) {
                System.out.println("Data inválida. Mantendo a data anterior.");
            }
        }

        System.out.print("Nova prioridade (BAIXA, MEDIA, ALTA): ");
        String prioridadeStr = scanner.nextLine().toUpperCase();
        Prioridade novaPrioridade;
        try {
            novaPrioridade = Prioridade.valueOf(prioridadeStr);
        } catch (Exception e) {
            System.out.println("Prioridade inválida. Mantendo a prioridade anterior.");
            return;
        }

        if (gerenciadorDeTarefas.atualizarTarefa(id, novaDescricao, novaDataVencimento, novaPrioridade)) {
            System.out.println("Tarefa atualizada com sucesso!");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private void removerTarefa() {
        System.out.print("ID da tarefa a ser removida: ");
        long id = lerId();
        if (gerenciadorDeTarefas.removerTarefa(id)) {
            System.out.println("Tarefa removida com sucesso!");
        } else {
            System.out.println("Tarefa não encontrada.");
        }
    }

    private void salvarTarefas() {
        System.out.print("Nome do arquivo para salvar: ");
        String nomeArquivo = scanner.nextLine();
        gerenciadorDeTarefas.salvarTarefas(nomeArquivo);
        System.out.println("Tarefas salvas com sucesso!");
    }

    private void carregarTarefas() {
        System.out.print("Nome do arquivo para carregar: ");
        String nomeArquivo = scanner.nextLine();
        gerenciadorDeTarefas.carregarTarefas(nomeArquivo);
        System.out.println("Tarefas carregadas com sucesso!");
    }

    private long lerId() {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Digite um número.");
            return -1;
        }
    }
}
