package br.com.projetolp.dao;

import br.com.projetolp.dto.LoginDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public ResultSet autenticaUsuario(LoginDTO l) {
        try {
            ConexaoDAO.getConnection();
            
            final String QUERY = "select nomefunc, departamento, usuario, senha"
                    + " from funcionario where usuario = ? and senha = ?";
            
            pst = ConexaoDAO.con.prepareStatement(QUERY);
            
            pst.setString(1, l.getUsuario());
            pst.setString(2, l.getSenha());
            
            rs = pst.executeQuery();
            
            return rs;
        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário no banco:\n" 
                    + e.getMessage());
            return rs;
        }
    }
}