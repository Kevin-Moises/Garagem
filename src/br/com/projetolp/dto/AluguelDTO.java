package br.com.projetolp.dto;

public class AluguelDTO {
    private int idCarro, idCliente, idFuncionario, solicitacao;
    private String dataAluguel, motivoAluguel, dataDevolucao;

    public int getIdCarro() {
        return idCarro;
    }
    public void setIdCarro(int idCarro) {
        this.idCarro = idCarro;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getDataAluguel() {
        return dataAluguel;
    }
    public void setDataAluguel(String dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public int getSolicitacao() {
        return solicitacao;
    }
    public void setSolicitacao(int solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getMotivoAluguel() {
        return motivoAluguel;
    }
    public void setMotivoAluguel(String motivoAluguel) {
        this.motivoAluguel = motivoAluguel;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}