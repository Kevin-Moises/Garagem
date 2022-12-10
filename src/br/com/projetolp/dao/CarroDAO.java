package br.com.projetolp.dao;

import br.com.projetolp.dto.CarroDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarroDAO {
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    
    public boolean cadastraCarro(CarroDTO c) {
        try {
            ConexaoDAO.getConnection();
            
            final String QUERY = "insert into carro values (default,?,?,?,?,?,?"
                    + ",?)";
            
            pst = ConexaoDAO.con.prepareStatement(QUERY);
            
            pst.setString(1, c.getPlaca());
            pst.setString(2, c.getChassi());
            pst.setInt(3, c.getAno());
            pst.setString(4, c.getModelo());
            pst.setString(5, c.getMontadora());
            pst.setString(6, c.getUltimoAluguel());
            pst.setInt(7, c.getDisponivel());
            
            pst.executeUpdate();
            ConexaoDAO.con.commit();
            return true;
        } 
        catch (SQLException e) {
            System.out.println("Erro ao inserir registro de carro:\n" 
                    + e.getMessage());
            return false;
        } 
        finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public boolean alteraCarro(CarroDTO c) {
        try {
            ConexaoDAO.getConnection();
            
            final String QUERY = "update carro set placa = ?, chassi = ?, "
                    + "ano = ?, modelo = ?, montadora = ?, ultimoaluguel = ?, "
                    + "disponivel = ? where placa = ?";
            
            pst = ConexaoDAO.con.prepareStatement(QUERY);
            
            pst.setString(1, c.getPlaca());
            pst.setString(2, c.getChassi());
            pst.setInt(3, c.getAno());
            pst.setString(4, c.getModelo());
            pst.setString(5, c.getMontadora());
            pst.setString(6, c.getUltimoAluguel());
            pst.setInt(7, c.getDisponivel());
            pst.setString(8, c.getPlaca());
            
            pst.executeUpdate();
            ConexaoDAO.con.commit();
            return true;
        } 
        catch (SQLException e) {
            System.out.println("Erro ao alterar registro no banco:\n" 
                    + e.getMessage());
            return false;
        }
        finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public boolean excluiCarro(CarroDTO c) {
        try {
            ConexaoDAO.getConnection();
            
            final String QUERY = "delete from carro where placa = ?";
            
            pst = ConexaoDAO.con.prepareStatement(QUERY);
            
            pst.setString(1, c.getPlaca());
            
            pst.executeUpdate();
            ConexaoDAO.con.commit();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir registro no banco:\n" 
                    + e.getMessage());
            return false;
        } finally {
            ConexaoDAO.closeDb();
        }
    }
    
    public ResultSet buscaCarro(CarroDTO c, String opcao) {
        try {
            ConexaoDAO.getConnection();
            
            String query = "";
            
            switch (opcao) {
                case "chassi": {
                    query = "select modelo, placa, ultimoaluguel, disponivel "
                            + "from carro where chassi = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setString(1, c.getChassi());
                    break;
                }
                case "modelo": {
                    query = "select modelo, placa, ultimoaluguel, disponivel "
                            + "from carro order by modelo";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "placa": {
                    query = "select modelo, placa, ultimoaluguel, disponivel "
                            + "from carro order by placa";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                } 
                case "ultimoaluguel": {
                    query = "select modelo, placa, ultimoaluguel, disponivel "
                            + "from carro order by ultimoaluguel";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                } 
                case "disponibilidade": {
                    query = "select modelo, placa, ultimoaluguel, disponivel "
                            + "from carro order by disponivel";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "preencher": {
                    query = "select * from carro where placa = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setString(1, c.getPlaca());
                    break;
                }
                case "aluguel": {
                    query = "select idcarro from carro order by idcarro";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    break;
                }
                case "aluguelpreencher": {
                    query = "select * from carro where idcarro = ?";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
                    
                    pst.setInt(1, c.getIdCarro());
                    break;
                }
                default: {
                    query = "select modelo, placa, ultimoaluguel, disponivel "
                            + "from carro";
                    
                    pst = ConexaoDAO.con.prepareStatement(query);
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
        finally {
            ConexaoDAO.closeDb();
        }
    }
}