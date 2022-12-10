package br.com.projetolp.dao;

import br.com.projetolp.dto.FuncionarioDTO;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FuncionarioDAO {
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public boolean cadastraFuncionario(FuncionarioDTO f) {
        try {
            ConexaoDAO.getConnection();
            
            ResultSet rsFuncExistente = buscaFuncionarioInsert(f, 2);
            
            if(!rsFuncExistente.next()) {
                final String QUERY = "insert into funcionario values (default,?"
                        + ",?,?,?,?,?,?,?,?,?,?)";

                pst = ConexaoDAO.con.prepareStatement(QUERY);

                pst.setString(1, f.getNomeFunc());
                pst.setString(2, f.getCpf());
                pst.setString(3, f.getDepartamento());
                pst.setInt(4, f.getRamal());
                pst.setString(5, f.getTurno());
                pst.setString(6, f.getCidade());
                pst.setString(7, f.getRua());
                pst.setInt(8, f.getNum());
                pst.setString(9, f.getCep());
                pst.setString(10, f.getUsuario());
                pst.setString(11, f.getSenha());

                int insert = pst.executeUpdate();
                ConexaoDAO.con.commit();
            
                if(insert == 1) {
                    ResultSet rsTel = buscaFuncionarioInsert(f, 1);

                    if(rsTel.next()) {
                        final String QUERY_TEL = "insert into contatofunc values"
                                + " (?,?,?)";

                        pst = ConexaoDAO.con.prepareStatement(QUERY_TEL);

                        pst.setInt(1, Integer.parseInt( rsTel.getString(
                                "idfunc")));
                        pst.setString(2, f.getTelefone());
                        pst.setString(3, f.getSegundoContato());

                        pst.executeUpdate();
                        ConexaoDAO.con.commit();
                    }
                }
            } else
                return false;
            return true;
        } 
        catch (SQLException e) {
            System.out.println("Falha ao inserir funcionario no banco:\n" 
                    + e.getMessage());
            return false;
        }
        finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public boolean alteraFucionario(FuncionarioDTO f) {
        try {
            ConexaoDAO.getConnection();
            
            final String QUERY = "update funcionario set nomefunc = ?, cpf = ?, "
                    + "departamento = ?, ramal = ?, turno = ?, cidade = ?, "
                    + "rua = ?, num = ?, cep = ?, usuario = ?, senha = ? "
                    + "where cpf = ?";
            
            pst = ConexaoDAO.con.prepareStatement(QUERY);
            
            pst.setString(1, f.getNomeFunc());
            pst.setString(2, f.getCpf());
            pst.setString(3, f.getDepartamento());
            pst.setInt(4, f.getRamal());
            pst.setString(5, f.getTurno());
            pst.setString(6, f.getCidade());
            pst.setString(7, f.getRua());
            pst.setInt(8, f.getNum());
            pst.setString(9, f.getCep());
            pst.setString(10, f.getUsuario());
            pst.setString(11, f.getSenha());
            pst.setString(12, f.getCpf());
            
            int insert = pst.executeUpdate();
            ConexaoDAO.con.commit();
            
            if(insert == 1) {
                ResultSet rsTel = buscaFuncionarioInsert(f, 1);
                
                if(rsTel.next()) {
                    final String QUERY_TEL = "update contatofunc set contatofunc "
                            + "= ?, segundocontato = ? where idfunc = ?";

                    pst = ConexaoDAO.con.prepareStatement(QUERY_TEL);

                    pst.setString(1, f.getTelefone());
                    pst.setString(2, f.getSegundoContato());
                    pst.setInt(3, Integer.parseInt(rs.getString(
                            "idfunc")));

                    pst.executeUpdate();
                    ConexaoDAO.con.commit();
                }
            }
            return true;
            
        } catch (SQLException e) {
            System.out.println("Falha ao alterar funcionario no banco:\n" 
                    + e.getMessage());
            return false;
        } 
        finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public boolean excluiFuncionario(FuncionarioDTO f) {
        try {
            ConexaoDAO.getConnection();
            
            ResultSet rsTel = buscaFuncionarioInsert(f, 1);
            
            if(rsTel.next()) {
                final String QUERY = "delete from contatofunc where idfunc = ?";
            
                pst = ConexaoDAO.con.prepareStatement(QUERY);

                pst.setInt(1, Integer.parseInt(rs.getString(
                        "idfunc")));
                
                int delete = pst.executeUpdate();
                ConexaoDAO.con.commit();
                
                if(delete == 1) {
                    final String QUERY_CLI = "delete from funcionario where "
                            + "cpf = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(QUERY_CLI);
                    
                    pst.setString(1, f.getCpf());
                    
                    pst.executeUpdate();
                    ConexaoDAO.con.commit();
                }
            } else 
                return false;
            return true;
        } catch (SQLException e) {
            System.out.println("Falha ao excluir funcionario no banco:\n" 
                    + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public ResultSet buscaFuncionarioInsert(FuncionarioDTO f, int opcao) {
        try {
            ConexaoDAO.getConnection();
            
            String query = "";
            
            switch(opcao) {
                case 1: {
                    query = "select idfunc from funcionario where cpf = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);

                    pst.setString(1, f.getCpf());
                    break;
                }
                case 2 : {
                    query = "select cpf from funcionario where cpf = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);

                    pst.setString(1, f.getCpf());
                    break;
                }
            }
           
            rs = pst.executeQuery();
            return rs;
        } 
        catch (SQLException e) {
            System.out.println("Erro ao consultar o banco:\n" + e.getMessage());
            return rs;
        }
    }
    
    public ResultSet buscaFuncionario(FuncionarioDTO f, String opcao) {
        try {
            ConexaoDAO.getConnection();
            
            String query = "";
            
            switch(opcao) {
                case "usuario": {
                    query = "select func.nomefunc, func.cpf, func.departamento, "
                            + "cf.contatofunc, cf.segundocontato from "
                            + "funcionario func inner join contatofunc cf "
                            + "on func.idfunc = cf.idfunc "
                            + "where func.usuario = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setString(1, f.getUsuario());
                    break;
                }
                case "nome": {
                    query = "select func.nomefunc, func.cpf, func.departamento, "
                            + "cf.contatofunc, cf.segundocontato from "
                            + "funcionario func inner join contatofunc cf "
                            + "on func.idfunc = cf.idfunc "
                            + "order by func.nomefunc";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "cpf": {
                    query = "select func.nomefunc, func.cpf, func.departamento, "
                            + "cf.contatofunc, cf.segundocontato from "
                            + "funcionario func inner join contatofunc cf "
                            + "on func.idfunc = cf.idfunc "
                            + "order by func.cpf";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "departamento": {
                    query = "select func.nomefunc, func.cpf, func.departamento, "
                            + "cf.contatofunc, cf.segundocontato from "
                            + "funcionario func inner join contatofunc cf "
                            + "on func.idfunc = cf.idfunc "
                            + "order by func.departamento";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "preencher": {
                    query = "select func.*, cf.contatofunc, cf.segundocontato "
                            + "from funcionario func inner join contatofunc cf "
                            + "on func.idfunc = cf.idfunc where func.cpf = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setString(1, f.getCpf());
                    break;
                }
                case "padrao" : {
                    query = "select func.*, cf.contatofunc, cf.segundocontato "
                            + "from funcionario func inner join contatofunc cf "
                            + "on func.idfunc = cf.idfunc";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "aluguel" : {
                    query = "select idfunc from funcionario order by idfunc";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "aluguelpreencher": {
                    query = "select func.*, cf.contatofunc "
                            + "from funcionario func inner join contatofunc cf "
                            + "on func.idfunc = cf.idfunc "
                            + "where func.idfunc = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setInt(1, f.getIdFunc());
                    break;
                }
            }
            
            rs = pst.executeQuery();
            return rs;
        } 
        catch (SQLException e) {
            return rs;
        }
    }
}