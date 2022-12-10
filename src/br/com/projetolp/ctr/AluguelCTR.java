package br.com.projetolp.ctr;

import br.com.projetolp.dto.AluguelDTO;
import br.com.projetolp.dao.AluguelDAO;
import br.com.projetolp.dao.ConexaoDAO;
import java.sql.ResultSet;

public class AluguelCTR {
    AluguelDAO aluguelDao = new AluguelDAO();
    
    public String cadastraAluguel(AluguelDTO a) {
        try {
            if(aluguelDao.cadastraAluguel(a))
                return "Aluguel cadastrado com sucesso!";
        } 
        catch (Exception e) {
            System.out.println("Erro ao cadastrar aluguel:\n" + e.getMessage());
        }
        return "Falha ao cadastrar aluguel!";
    }
    
    public ResultSet buscaAluguel(AluguelDTO a, String opcao) {
        return aluguelDao.buscaAluguel(a, opcao);
    }
    
    public void closeDb() {
        ConexaoDAO.closeDb();
    }
}
