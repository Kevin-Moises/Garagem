package br.com.projetolp.ctr;

import br.com.projetolp.dao.ConexaoDAO;
import br.com.projetolp.dto.LoginDTO;
import br.com.projetolp.dao.LoginDAO;
import java.sql.ResultSet;

public class LoginCTR {
    LoginDAO loginDao = new LoginDAO();
    
    public ResultSet autenticaUsuario(LoginDTO l) {
        return loginDao.autenticaUsuario(l);
    }
    
    public void closeDb() {
        ConexaoDAO.closeDb();
    }
}
