package br.com.projetolp.dao;

import br.com.projetolp.dto.ClienteDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public boolean cadastraCliente(ClienteDTO c) {
        try {
            ConexaoDAO.getConnection();
            
            ResultSet rsFuncExistente = buscaClienteInsertContato(c, 2);
            
            if(!rsFuncExistente.next()) {
                final String QUERY = "insert into cliente values (default,?,?,?"
                        + ",?,?,?,?)";

                pst = ConexaoDAO.con.prepareStatement(QUERY);

                pst.setString(1, c.getNomeCli());
                pst.setString(2, c.getNumCNH());
                pst.setString(3, c.getCpf());
                pst.setString(4, c.getRua());
                pst.setInt(5, c.getNum());
                pst.setString(6, c.getCep());
                pst.setString(7, c.getCidade());

                int insert = pst.executeUpdate();
                ConexaoDAO.con.commit();

                if(insert == 1) {
                    ResultSet rsTel = buscaClienteInsertContato(c, 1);

                    if(rsTel.next()) {
                        final String QUERY_TEL = "insert into contatocli values"
                                + " (?,?,?)";

                        pst = ConexaoDAO.con.prepareStatement(QUERY_TEL);

                        pst.setInt(1, Integer.parseInt(rsTel.getString(
                                "idcli")));
                        pst.setString(2, c.getTelefone());
                        pst.setString(3, c.getSegundoContato());

                        pst.executeUpdate();
                        ConexaoDAO.con.commit();
                    }
                }
            } else
                return false;
            return true;
        } 
        catch (SQLException e) {
            System.out.println("Falha ao inserir cliente no banco:\n" 
                    + e.getMessage());
            return false;
        } 
        finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public boolean alteraCliente(ClienteDTO c) {
        try {
            ConexaoDAO.getConnection();
            
            final String QUERY = "update cliente set nomecli = ?, numcnh = ?, "
                    + "cpf = ?, rua = ?, num = ?, cep = ?, cidade = ? "
                    + "where cpf = ?";
            
            pst = ConexaoDAO.con.prepareStatement(QUERY);
            
            pst.setString(1, c.getNomeCli());
            pst.setString(2, c.getNumCNH());
            pst.setString(3, c.getCpf());
            pst.setString(4, c.getRua());
            pst.setInt(5, c.getNum());
            pst.setString(6, c.getCep());
            pst.setString(7, c.getCidade());
            pst.setString(8, c.getCpf());
            
            int insert = pst.executeUpdate();
            ConexaoDAO.con.commit();
            
            if(insert == 1) {
                ResultSet rsTel = buscaClienteInsertContato(c, 1);
                
                if(rsTel.next()) {
                    final String QUERY_TEL = "update contatocli set contatocli "
                            + "= ?, segundocontato = ? where idcli = ?";

                    pst = ConexaoDAO.con.prepareStatement(QUERY_TEL);

                    pst.setString(1, c.getTelefone());
                    pst.setString(2, c.getSegundoContato());
                    pst.setInt(3, Integer.parseInt(rs.getString(
                            "idcli")));

                    pst.executeUpdate();
                    ConexaoDAO.con.commit();
                }
            }
            return true;
        } 
        catch (SQLException e) {
            System.out.println("Falha ao alterar cliente no banco:\n" 
                    + e.getMessage());
            return false;
        } 
        finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public boolean excluiCliente(ClienteDTO c) {
        try {
            ConexaoDAO.getConnection();
            
            ResultSet rsTel = buscaClienteInsertContato(c, 1);
            
            if(rsTel.next()) {
                final String QUERY = "delete from contatocli where idcli = ?";
            
                pst = ConexaoDAO.con.prepareStatement(QUERY);

                pst.setInt(1, Integer.parseInt(rs.getString(
                        "idcli")));
                
                int delete = pst.executeUpdate();
                ConexaoDAO.con.commit();
                
                if(delete == 1) {
                    final String QUERY_CLI = "delete from cliente where "
                            + "cpf = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(QUERY_CLI);
                    
                    pst.setString(1, c.getCpf());
                    
                    pst.executeUpdate();
                    ConexaoDAO.con.commit();
                }
            } else 
                return false;
            return true;
        } 
        catch (SQLException e) {
            System.out.println("Falha ao excluir cliente no banco:\n" 
                    + e.getMessage());
            return false;
        } 
        finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public ResultSet buscaClienteInsertContato(ClienteDTO c, int opcao) {
        try {
            ConexaoDAO.getConnection();
            
            String query = "";
            
            switch(opcao) {
                case 1 : {
                    query = "select idcli from cliente where cpf = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
            
                    pst.setString(1, c.getCpf());
                    break;
                }
                case 2 : {
                    query = "select cpf from cliente where cpf = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
            
                    pst.setString(1, c.getCpf());
                    break;
                }
            }
            
            rs = pst.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("Erro ao consultar o banco:\n" + e.getMessage());
            return rs;
        }
    }
    
    public ResultSet buscaCliente(ClienteDTO c, String opcao) {
        try {
            ConexaoDAO.getConnection();
            
            String query = "";
            
            switch(opcao) {
                case "cnh": {
                    query = "select cli.nomecli, cli.cpf, cli.cidade, "
                            + "cc.contatocli from cliente cli inner join "
                            + "contatocli cc on cli.idcli = cc.idcli "
                            + "where cli.numcnh = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setString(1, c.getNumCNH());
                    break;
                }
                case "nome": {
                    query = "select cli.nomecli, cli.cpf, cli.cidade, "
                            + "cc.contatocli from cliente cli inner join "
                            + "contatocli cc on cli.idcli = cc.idcli order by "
                            + "cli.nomecli";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "cpf": {
                    query = "select cli.nomecli, cli.cpf, cli.cidade, "
                            + "cc.contatocli from cliente cli inner join "
                            + "contatocli cc on cli.idcli = cc.idcli order by "
                            + "cli.cpf";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "cidade": {
                    query = "select cli.nomecli, cli.cpf, cli.cidade, "
                            + "cc.contatocli from cliente cli inner join "
                            + "contatocli cc on cli.idcli = cc.idcli order by "
                            + "cli.cidade";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "padrao": {
                    query = "select cli.nomecli, cli.cpf, cli.cidade, "
                            + "cc.contatocli from cliente cli inner join "
                            + "contatocli cc on cli.idcli = cc.idcli";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "preencher": {
                    query = "select cli.*, cc.contatocli, cc.segundocontato "
                            + "from cliente cli inner join contatocli cc on "
                            + "cli.idcli = cc.idcli "
                            + "where cli.cpf = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setString(1, c.getCpf());
                    break;
                }
                case "aluguel": {
                    query = "select idcli from cliente order by idcli";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "aluguelpreencher": {
                    query = "select cli.*, cc.contatocli "
                            + "from cliente cli inner join contatocli cc on "
                            + "cli.idcli = cc.idcli "
                            + "where cli.idcli = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setInt(1, c.getIdCli());
                    break;
                }
            }
            
            rs = pst.executeQuery();
            return rs;
        } 
        catch (SQLException e) {
            System.out.println("Erro ao buscar registros no banco:\n" 
                    + e.getMessage());
            return rs;
        }
    }
}