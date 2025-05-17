import java.time.LocalDate;

public class Tarefa {
    private long id;
    private String descricao;
    private LocalDate dataCriacao;
    private LocalDate dataVencimento;
    private Prioridade prioridade;
    private StatusTarefa status;

    // Construtor
    public Tarefa(String descricao, LocalDate dataVencimento, Prioridade prioridade) {
        this.id = System.currentTimeMillis();
        this.descricao = descricao;
        this.dataCriacao = LocalDate.now();
        this.dataVencimento = dataVencimento;
        this.prioridade = prioridade;
        this.status = StatusTarefa.PENDENTE;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    // Método para marcar tarefa como concluída
    public void marcarComoConcluida() {
        this.status = StatusTarefa.CONCLUIDA;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Descrição: %s | Data de Criação: %s | Data de Vencimento: %s | Prioridade: %s | Status: %s",
            id, descricao, dataCriacao, dataVencimento != null ? dataVencimento : "Sem data", prioridade, status);
    }

}
