package br.com.projetolp.ctr;

import br.com.projetolp.dao.ConexaoDAO;
import br.com.projetolp.dto.FuncionarioDTO;
import br.com.projetolp.dao.FuncionarioDAO;
import java.sql.ResultSet;

public class FuncionarioCTR {
    FuncionarioDAO funcionarioDao = new FuncionarioDAO();
    
    public String cadastraFuncionario(FuncionarioDTO f) {
        try {
            boolean result = funcionarioDao.cadastraFuncionario(f);
            
            if(result)
                return "Funcionário cadastrado com sucesso!";
            else
                return "CPF já cadastrado no sistema!";
        } 
        catch (Exception e) {
            System.out.println("Falha ao cadastrar funcionario:\n" 
                    + e.getMessage());
        }
        return "Falha ao cadastrar funcionário!";
    }
    
    public String alteraFuncionario(FuncionarioDTO f) {
        try {
            if(funcionarioDao.alteraFucionario(f))
                return "Funcionário alterado com sucesso!";
        } 
        catch (Exception e) {
            System.out.println("Falha ao alterar funcionario:\n" 
                    + e.getMessage());
        }
        return "Falha ao alterar funcionário!";
    }
    
    public String excluiCliente(FuncionarioDTO f) {
        try {
            boolean result = funcionarioDao.excluiFuncionario(f);
            
            if(result)
                return "Funcionário excluído com sucesso!";
            else
                return "Falha ao excluir funcionário!";
        } 
        catch (Exception e) {
            System.out.println("Falha ao excluir funcionario:\n" 
                    + e.getMessage());
        }
        return "Falha ao excluir funcionario!";
    }
    
    public ResultSet buscaFuncionario(FuncionarioDTO f, String opcao) {
        return funcionarioDao.buscaFuncionario(f, opcao);
    }
    
    public void closeDb() {
        ConexaoDAO.closeDb();
    }
}