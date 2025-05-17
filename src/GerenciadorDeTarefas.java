import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class GerenciadorDeTarefas {
    private List<Tarefa> listaDeTarefas;
    private long proximoId;

    public GerenciadorDeTarefas() {
        this.listaDeTarefas = new ArrayList<>();
        this.proximoId = 1;
    }

    public void adicionarTarefa(String descricao, LocalDate dataVencimento, Prioridade prioridade) {
        Tarefa novaTarefa = new Tarefa(descricao, dataVencimento, prioridade);
        listaDeTarefas.add(novaTarefa);
    }

    public List<Tarefa> listarTarefas(StatusTarefa filtroStatus) {
        List<Tarefa> tarefasFiltradas = new ArrayList<>();
        for (Tarefa tarefa : listaDeTarefas) {
            if (filtroStatus == null || tarefa.getStatus() == filtroStatus) {
                tarefasFiltradas.add(tarefa);
            }
        }
        return tarefasFiltradas;
    }

    public Tarefa buscarTarefaPorId(long id) {
        for (Tarefa tarefa : listaDeTarefas) {
            if (tarefa.getId() == id) {
                return tarefa;
            }
        }
        return null;
    }

    public boolean atualizarTarefa(long id, String novaDescricao, LocalDate novaDataVencimento, Prioridade novaPrioridade) {
        Tarefa tarefa = buscarTarefaPorId(id);
        if (tarefa != null) {
            tarefa.setDescricao(novaDescricao);
            tarefa.setDataVencimento(novaDataVencimento);
            tarefa.setPrioridade(novaPrioridade);
            return true;
        }
        return false;
    }

    public boolean marcarTarefaComoConcluida(long id) {
        Tarefa tarefa = buscarTarefaPorId(id);
        if (tarefa != null) {
            tarefa.marcarComoConcluida();
            return true;
        }
        return false;
    }

    public boolean marcarTarefaComoPendente(long id) {
        Tarefa tarefa = buscarTarefaPorId(id);
        if (tarefa != null) {
            tarefa.setStatus(StatusTarefa.PENDENTE);
            return true;
        }
        return false;
    }

    public boolean removerTarefa(long id) {
        Tarefa tarefa = buscarTarefaPorId(id);
        if (tarefa != null) {
            listaDeTarefas.remove(tarefa);
            return true;
        }
        return false;
    }

    public void salvarTarefas(String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(listaDeTarefas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarTarefas(String nomeArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            listaDeTarefas = (List<Tarefa>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar tarefas: " + e.getMessage());
            listaDeTarefas = new ArrayList<>();
        }
    }
}
