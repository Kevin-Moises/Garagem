package br.com.projetolp.ctr;

import br.com.projetolp.dao.ClienteDAO;
import br.com.projetolp.dao.ConexaoDAO;
import br.com.projetolp.dto.ClienteDTO;
import java.sql.ResultSet;

public class ClienteCTR {
    ClienteDAO clienteDao = new ClienteDAO();
    
    public String cadastraCliente(ClienteDTO c) {
        try {
            boolean result = clienteDao.cadastraCliente(c);
            
            if(result)
                return "Cliente cadastrado com sucesso!";
            else
                return "CPF já cadastrado no sistema!";
        } 
        catch (Exception e) {
            System.out.println("Falha ao cadastrar cliente:\n" 
                    + e.getMessage());
        }
        return "Falha ao cadastrar cliente!";
    }
    
    public String alteraCliente(ClienteDTO c) {
        try {
            if(clienteDao.alteraCliente(c))
                return "Cliente alterado com sucesso!";
        } 
        catch (Exception e) {
            System.out.println("Falha ao alterar cliente:\n" 
                    + e.getMessage());
        }
        return "Falha ao alterar cliente!";
    }
    
    public String excluiCliente(ClienteDTO c) {
        try {
            boolean result = clienteDao.excluiCliente(c);
            
            if(result)
                return "Cliente excluído com sucesso!";
            else
                return "Falha ao excluir cliente!";
        } 
        catch (Exception e) {
            System.out.println("Falha ao excluir cliente:\n" 
                    + e.getMessage());
        }
        return "Falha ao excluir cliente!";
    }
    
    public ResultSet buscaCliente(ClienteDTO c, String opcao) {
        return clienteDao.buscaCliente(c, opcao);
    }
    
    public void closeDb() {
        ConexaoDAO.closeDb();
    }
}