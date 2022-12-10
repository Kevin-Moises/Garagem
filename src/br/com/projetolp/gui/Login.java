package br.com.projetolp.gui;

import br.com.projetolp.ctr.LoginCTR;
import br.com.projetolp.dao.ConexaoDAO;
import br.com.projetolp.dto.LoginDTO;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {
    
    final String TITULO = "Login em Sistema de Garagem";
    
    public Login() {
        initComponents();
        initGui();
        
        if(Inicio.primeiraExecucao)
            abreInicio();
    }
    
    private void initGui() {
        FlatLightLaf.setup();
        FlatLightLaf.updateUI();
        this.setLocationRelativeTo(null);
        this.setTitle(TITULO);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        textUsuario.putClientProperty("JComponent.roundRect", true);
        pswdSenha.putClientProperty("JComponent.roundRect", true);
        btnEntrar.putClientProperty("JButton.buttonType", "roundRect");
        btnCancelar.putClientProperty("JButton.buttonType", 
                "roundRect");
        pswdSenha.setEchoChar('*');
        testaConexaoBanco();
    }
    
    private void abreInicio() {
        Inicio i = new Inicio();
        Inicio.primeiraExecucao = false;
        i.setVisible(true);
    }
    
    private void testaConexaoBanco() {
        ConexaoDAO.getConnection();
        
        if(ConexaoDAO.con != null) {
            labelStatus.setText("Conectado!");
            ConexaoDAO.closeDb();
        } else {
            labelStatus.setText("Falha ao conectar!");
            labelStatus.setForeground(Color.red);
        }
    }
    
    private void mostraSenha() {
        if(boxMostraSenha.isSelected())
            pswdSenha.setEchoChar('\u0000');
        else
            pswdSenha.setEchoChar('*');
    }
    
    private String criptografarAes(String str) {
        final String CHAVE = "sparko";
        final String SALT = "encrypter";
        
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(
                    "PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(CHAVE.toCharArray(),
                    SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(),
                    "AES");

            Cipher cipher = Cipher.getInstance(
                    "AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            
            return Base64
                    .getEncoder().encodeToString(cipher.doFinal(
                            str.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException
                | NoSuchAlgorithmException | InvalidKeySpecException
                | BadPaddingException | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            System.out.println("Erro durante o processo de encriptação:\n"
                    + e);
        }
        return null;
    }
    
    private void autenticaUsuario() {
        LoginDTO l = new LoginDTO();
        LoginCTR loginCtr = new LoginCTR();
        
        try {
            final String STR = new String(pswdSenha.getPassword());
            final String SENHA_SEGURA = criptografarAes(STR);
            
            l.setUsuario(textUsuario.getText());
            l.setSenha(SENHA_SEGURA);
            
            if(textUsuario.getText().isEmpty() || SENHA_SEGURA.isEmpty())
                JOptionPane.showMessageDialog(null, "Informe"
                        + " os dados de usuário!", "Atenção!", 
                        JOptionPane.WARNING_MESSAGE);
            else {
                ResultSet rs = loginCtr.autenticaUsuario(l);
                
                if(rs.next()) {
                    if(textUsuario.getText().equals(rs.getString(
                            "usuario")) && SENHA_SEGURA.equals(
                                    rs.getString("senha"))) {
                        Inicio.login = true;
                        Inicio.usuarioConectado = rs.getString("nomefunc");
                        Inicio.usuarioSetor = rs.getString("departamento");
                        Inicio.usuarioLogin = rs.getString("usuario");
                        Inicio.menus(rs.getString("departamento"));
                        this.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, 
                                "Usuário e/ou senha não encontrados!",  
                                "Atenção!", 
                                JOptionPane.WARNING_MESSAGE);
                    limpaCampos();
                }
            }
        } 
        catch (HeadlessException | SQLException e) {
            System.out.println("Erro ao logar no sistema:\n" + e.getMessage());
        }
        finally {
            loginCtr.closeDb();
        }
    }
    
    private void cancelaLogin() {
        Object[] option = {"Não", "Sim"};
        
        if(JOptionPane.showOptionDialog(null, "Deseja "
                + "realmente cancelar o login?", "Atenção!", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, null, option, 
                option[1]) == 1)
            this.dispose();
    }
    
    private void limpaCampos() {
        textUsuario.setText("");
        textUsuario.requestFocus();
        pswdSenha.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelUsuario = new javax.swing.JLabel();
        textUsuario = new javax.swing.JTextField();
        labelSenha = new javax.swing.JLabel();
        pswdSenha = new javax.swing.JPasswordField();
        boxMostraSenha = new javax.swing.JCheckBox();
        btnEntrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        labelConexao = new javax.swing.JLabel();
        labelStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelUsuario.setText("Usuário");

        labelSenha.setText("Senha");

        pswdSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pswdSenhaKeyPressed(evt);
            }
        });

        boxMostraSenha.setText("Mostrar senha");
        boxMostraSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boxMostraSenhaMouseClicked(evt);
            }
        });

        btnEntrar.setText("Entrar");
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        labelConexao.setText("Status de conexão");

        labelStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelStatus.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boxMostraSenha)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(labelConexao)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelStatus))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                            .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labelUsuario)
                                .addComponent(labelSenha))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(textUsuario)
                                .addComponent(pswdSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsuario)
                    .addComponent(textUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSenha)
                    .addComponent(pswdSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(boxMostraSenha)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEntrar)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelConexao)
                    .addComponent(labelStatus))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boxMostraSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boxMostraSenhaMouseClicked
        mostraSenha();
    }//GEN-LAST:event_boxMostraSenhaMouseClicked

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        autenticaUsuario();
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelaLogin();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void pswdSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pswdSenhaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            autenticaUsuario();
    }//GEN-LAST:event_pswdSenhaKeyPressed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | 
                javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxMostraSenha;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JLabel labelConexao;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelStatus;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPasswordField pswdSenha;
    private javax.swing.JTextField textUsuario;
    // End of variables declaration//GEN-END:variables
}