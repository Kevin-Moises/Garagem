package br.com.projetolp.dao;

import br.com.projetolp.dto.AluguelDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AluguelDAO {
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public boolean cadastraAluguel(AluguelDTO a) {
        try {
            ConexaoDAO.getConnection();
            
            final String QUERY = "insert into aluguel values (default, ?,?,?,?,?,?)";
            
            pst = ConexaoDAO.con.prepareStatement(QUERY);
            
            pst.setString(1, a.getDataAluguel());
            pst.setString(2, a.getMotivoAluguel());
            pst.setString(3, a.getDataDevolucao());
            pst.setInt(4, a.getIdCarro());
            pst.setInt(5, a.getIdCliente());
            pst.setInt(6, a.getIdFuncionario());
            
            int result = pst.executeUpdate();
            ConexaoDAO.con.commit();
            
            if(result == 1) {
                ResultSet rsAluguel = buscaCarroInsertAluguel(a);
                
                if(rsAluguel.next()) {
                    final String ALTERA_CARRO = "update carro set disponivel "
                            + "= 1 where idcarro = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(ALTERA_CARRO);
                    
                    pst.setInt(1, Integer.parseInt(rsAluguel.getString(
                            "idcarro")));
                    
                    pst.executeUpdate();
                    ConexaoDAO.con.commit();
                }
            }
            
            return true;
        } 
        catch (SQLException e) {
            System.out.println("Erro ao cadastrar aluguel:\n" + e.getMessage());
            return false;
        }
        finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public ResultSet buscaCarroInsertAluguel(AluguelDTO a) {
        try {
            String query = "select idcarro from carro where idcarro = ?";
            
            pst = ConexaoDAO.con.prepareStatement(query);
            
            pst.setInt(1, a.getIdCarro());
            
            rs = pst.executeQuery();
            return rs;
        } 
        catch (SQLException e) {
            System.out.println("Erro ao alterar status de carro:\n" 
                    + e.getMessage());
            return rs;
        }
    }
    
    public ResultSet buscaAluguel(AluguelDTO a, String opcao) {
        try {
            ConexaoDAO.getConnection();
            
            String query = "";
            
            switch(opcao) {
                case "preencher": {
                    query = "select alu.dataaluguel, alu.idaluguel, car.modelo, " +
                        "cli.nomecli, cc.contatocli, func.nomefunc from aluguel alu " +
                        "inner join carro car on alu.idcarro = car.idcarro " +
                        "inner join cliente cli on alu.idcli = cli.idcli " +
                        "inner join contatocli cc on alu.idcli = cc.idcli " +
                        "inner join funcionario func on alu.idfunc = func.idfunc " +
                        "order by alu.idaluguel";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
            }
            
            rs = pst.executeQuery();
            return rs;
        } 
        catch (SQLException e) {
            System.out.println("Erro ao buscar aluguel no banco de dados:\n" 
                    + e.getMessage());
            return rs;
        } 
        finally {
            ConexaoDAO.closeDb();
        }
    }
}