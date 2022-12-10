package br.com.projetolp.ctr;

import br.com.projetolp.dto.CarroDTO;
import br.com.projetolp.dao.CarroDAO;
import br.com.projetolp.dao.ConexaoDAO;
import java.sql.ResultSet;

public class CarroCTR {
    CarroDAO carroDao = new CarroDAO();
    
    public String cadastraCarro(CarroDTO c) {
        try {
            if(carroDao.cadastraCarro(c))
                return "Carro cadastrado com sucesso!";
        } 
        catch (Exception e) {
            System.out.println("Falha ao cadastrar carro:\n" + e.getMessage());
        }
        return "Falha ao cadastrar carro!";
    }
    
    public String alteraCarro(CarroDTO c) {
        try {
            if(carroDao.alteraCarro(c))
                return "Carro alterado com sucesso!";
        } 
        catch (Exception e) {
            System.out.println("Falha ao alterar carro:\n" + e.getMessage());
        }
        return "Falha ao alterar carro!";
    }
    
    public String excluiCarro(CarroDTO c) {
        try {
            if(carroDao.excluiCarro(c))
                return "Carro excluído com sucesso!";
        } catch (Exception e) {
            System.out.println("Falha ao alterar carro:\n" + e.getMessage());
        }
        return "Falha ao excluir registro!";
    }
    
    public ResultSet buscaCarro(CarroDTO c, String opcao) {
        return carroDao.buscaCarro(c, opcao);
    }
    
    public void closeDb() {
        ConexaoDAO.closeDb();
    }
}