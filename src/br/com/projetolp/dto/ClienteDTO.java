package br.com.projetolp.dto;

public class ClienteDTO {

    int idCli, num;
    String nomeCli, numCNH, cpf, rua, cep, cidade, telefone, segundoContato;

    public ClienteDTO() {
    }

    public int getIdCli() {
        return idCli;
    }

    public void setIdCli(int idCli) {
        this.idCli = idCli;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getNomeCli() {
        return nomeCli;
    }

    public void setNomeCli(String nomeCli) {
        this.nomeCli = nomeCli;
    }

    public String getNumCNH() {
        return numCNH;
    }

    public void setNumCNH(String numCNH) {
        this.numCNH = numCNH;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSegundoContato() {
        return segundoContato;
    }

    public void setSegundoContato(String segundoContato) {
        this.segundoContato = segundoContato;
    }
}