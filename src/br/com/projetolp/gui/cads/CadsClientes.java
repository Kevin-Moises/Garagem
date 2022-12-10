package br.com.projetolp.gui.cads;

import br.com.projetolp.ctr.ClienteCTR;
import br.com.projetolp.dto.ClienteDTO;
import br.com.projetolp.gui.Inicio;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

public class CadsClientes extends javax.swing.JFrame {

    final String TITULO = "Cadastro de Clientes";
    
    public CadsClientes() {
        initComponents();
        initUi();
    }
    
    private void initUi() {
        FlatLightLaf.setup();
        FlatLightLaf.updateUI();
        this.setLocationRelativeTo(null);
        this.setTitle(TITULO);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        labelFuncionario.setText(Inicio.usuarioConectado);
        labelSetor.setText(Inicio.usuarioSetor);
        textNome.putClientProperty("JComponent.roundRect", true);
        textNumeroCnh.putClientProperty("JComponent.roundRect", true);
        textCpf.putClientProperty("JComponent.roundRect", true);
        textCidade.putClientProperty("JComponent.roundRect", true);
        textNumero.putClientProperty("JComponent.roundRect", true);
        textRua.putClientProperty("JComponent.roundRect", true);
        textCep.putClientProperty("JComponent.roundRect", true);
        textTelefone.putClientProperty("JComponent.roundRect", true);
        textSegundoContato.putClientProperty("JComponent.roundRect", 
                true);
        btnAddContato.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnRemContato.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnCadastrar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpar.putClientProperty("JButton.buttonType", "roundRect");
        btnCancelar.putClientProperty("JButton.buttonType", 
                "roundRect");
        textSegundoContato.setEnabled(false);
        btnRemContato.setEnabled(false);
    }
    
    private void cancelaCadastro() {
        Object[] option = {"Não", "Sim"};
        
        if(JOptionPane.showOptionDialog(null, "Deseja realmente "
                + "cancelar o cadastro? \nAtenção: as informações digitadas "
                + "serão\nperdidas!", "Atenção!", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, null, option, 
                option[1]) == 1)
            this.dispose();
    }
    
    private void limpaCampos() {
        textNome.setText("");
        textNome.requestFocus();
        textNumeroCnh.setText("");
        textCpf.setText("");
        textCidade.setText("");
        textNumero.setText("");
        textRua.setText("");
        textCep.setText("");
        textTelefone.setText("");
        
        if(textSegundoContato.isEnabled()) {
            textSegundoContato.setText("");
            manipulaContatos(true, false, false);
        }
    }
    
    private void manipulaContatos(boolean a, boolean b, boolean c) {
        btnAddContato.setEnabled(a);
        btnRemContato.setEnabled(b);
        textSegundoContato.setEnabled(c);
        textSegundoContato.requestFocus();
    }
    
    private void addSegundoContato() {
        manipulaContatos(false, true, true);
    }
    
    private void remSegundoContato() {
        manipulaContatos(true, false, false);
    }
    
    private void cadastraCliente() {
        ClienteCTR clienteCtr = new ClienteCTR();
        ClienteDTO c = new ClienteDTO();
        
        try {
            c.setNomeCli(textNome.getText());
            c.setNumCNH(textNumeroCnh.getText());
            c.setCpf(textCpf.getText());
            c.setCidade(textCidade.getText());
            c.setNum(Integer.parseInt(textNumero.getText()));
            c.setRua(textRua.getText());
            c.setCep(textCep.getText());
            c.setTelefone(textTelefone.getText());
            
            if(textSegundoContato.isEnabled())
                c.setSegundoContato(textSegundoContato.getText());
            else
                c.setSegundoContato(null);
            
            if(textNome.getText().isEmpty() || textNumeroCnh.getText().isEmpty()
                    || textCpf.getText().isEmpty() || textCidade.getText().isEmpty()
                    || textNumero.getText().isEmpty() || textRua.getText().isEmpty()
                    || textCep.getText().isEmpty() || textTelefone.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Por "
                        + "favor, preencha todos os campos\ndo formulário!", 
                        "Atenção!", JOptionPane.WARNING_MESSAGE);
            else {
                JOptionPane.showMessageDialog(null, 
                        clienteCtr.cadastraCliente(c), 
                        "Mensagem!", 
                        JOptionPane.INFORMATION_MESSAGE);
                limpaCampos();
            }
        } 
        catch (NumberFormatException ef) {
            JOptionPane.showMessageDialog(null, "O campo número "
                    + "precisa ser preenchido com números!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
            textNumero.setText("");
            textNumero.requestFocus();
        }
        catch (HeadlessException e) {
            System.out.println("Erro ao cadastrar cliente:\n" 
                    + e.getMessage());
            JOptionPane.showMessageDialog(null, "Por favor, "
                    + "preencha todos os campos \ndo formulário!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
        } 
        finally {
            clienteCtr.closeDb();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSuperior = new javax.swing.JPanel();
        labelFuncionarioConectado = new javax.swing.JLabel();
        labelSetorFuncionario = new javax.swing.JLabel();
        labelFuncionario = new javax.swing.JLabel();
        labelSetor = new javax.swing.JLabel();
        panelInfoDocumento = new javax.swing.JPanel();
        labelNome = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        labelNumeroCnh = new javax.swing.JLabel();
        textNumeroCnh = new javax.swing.JTextField();
        labelCpf = new javax.swing.JLabel();
        textCpf = new javax.swing.JTextField();
        panelInfoPessoais = new javax.swing.JPanel();
        labelCidade = new javax.swing.JLabel();
        textCidade = new javax.swing.JTextField();
        labelNumero = new javax.swing.JLabel();
        textNumero = new javax.swing.JTextField();
        textRua = new javax.swing.JTextField();
        labelRua = new javax.swing.JLabel();
        textCep = new javax.swing.JTextField();
        labelCep = new javax.swing.JLabel();
        labelTelefone = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        btnAddContato = new javax.swing.JButton();
        btnRemContato = new javax.swing.JButton();
        labelSegundoContato = new javax.swing.JLabel();
        textSegundoContato = new javax.swing.JTextField();
        btnCadastrar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labelFuncionarioConectado.setText("Funcionário conectado");

        labelSetorFuncionario.setText("Setor do Funcionário");

        labelFuncionario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelFuncionario.setText("jLabel1");

        labelSetor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelSetor.setText("jLabel2");

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addComponent(labelFuncionarioConectado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                        .addComponent(labelFuncionario))
                    .addGroup(panelSuperiorLayout.createSequentialGroup()
                        .addComponent(labelSetorFuncionario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSetor)))
                .addContainerGap())
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFuncionarioConectado)
                    .addComponent(labelFuncionario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSetorFuncionario)
                    .addComponent(labelSetor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoDocumento.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Documentos"));

        labelNome.setText("Nome");

        labelNumeroCnh.setText("Número CNH");

        labelCpf.setText("CPF");

        javax.swing.GroupLayout panelInfoDocumentoLayout = new javax.swing.GroupLayout(panelInfoDocumento);
        panelInfoDocumento.setLayout(panelInfoDocumentoLayout);
        panelInfoDocumentoLayout.setHorizontalGroup(
            panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNumeroCnh)
                    .addComponent(labelNome))
                .addGap(18, 18, 18)
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                        .addComponent(textNumeroCnh, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(labelCpf)
                        .addGap(18, 18, 18)
                        .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textNome))
                .addContainerGap())
        );
        panelInfoDocumentoLayout.setVerticalGroup(
            panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumeroCnh)
                    .addComponent(textNumeroCnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCpf))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Pessoais"));

        labelCidade.setText("Cidade");

        labelNumero.setText("Número");

        labelRua.setText("Rua");

        labelCep.setText("CEP");

        labelTelefone.setText("Telefone");

        btnAddContato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/adicionar.png"))); // NOI18N
        btnAddContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddContatoActionPerformed(evt);
            }
        });

        btnRemContato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/remover.png"))); // NOI18N
        btnRemContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemContatoActionPerformed(evt);
            }
        });

        labelSegundoContato.setText("Adicionar segundo contato");

        javax.swing.GroupLayout panelInfoPessoaisLayout = new javax.swing.GroupLayout(panelInfoPessoais);
        panelInfoPessoais.setLayout(panelInfoPessoaisLayout);
        panelInfoPessoaisLayout.setHorizontalGroup(
            panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                                .addComponent(labelCidade)
                                .addGap(18, 18, 18)
                                .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                                .addComponent(labelNumero)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelRua, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelCep, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textRua)
                            .addComponent(textCep, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                    .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                        .addComponent(labelTelefone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textSegundoContato, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                                .addComponent(labelSegundoContato)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddContato)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemContato))
                            .addComponent(textTelefone))))
                .addContainerGap())
        );
        panelInfoPessoaisLayout.setVerticalGroup(
            panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCidade)
                    .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRua))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumero)
                    .addComponent(textNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCep))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefone)
                    .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRemContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelSegundoContato, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textSegundoContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrar)
                    .addComponent(btnLimpar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelaCadastro();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpaCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastraCliente();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnAddContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContatoActionPerformed
        addSegundoContato();
    }//GEN-LAST:event_btnAddContatoActionPerformed

    private void btnRemContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemContatoActionPerformed
        remSegundoContato();
    }//GEN-LAST:event_btnRemContatoActionPerformed

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
            java.util.logging.Logger.getLogger(CadsClientes.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new CadsClientes().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddContato;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnRemContato;
    private javax.swing.JLabel labelCep;
    private javax.swing.JLabel labelCidade;
    private javax.swing.JLabel labelCpf;
    private javax.swing.JLabel labelFuncionario;
    private javax.swing.JLabel labelFuncionarioConectado;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelNumero;
    private javax.swing.JLabel labelNumeroCnh;
    private javax.swing.JLabel labelRua;
    private javax.swing.JLabel labelSegundoContato;
    private javax.swing.JLabel labelSetor;
    private javax.swing.JLabel labelSetorFuncionario;
    private javax.swing.JLabel labelTelefone;
    private javax.swing.JPanel panelInfoDocumento;
    private javax.swing.JPanel panelInfoPessoais;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JTextField textCep;
    private javax.swing.JTextField textCidade;
    private javax.swing.JTextField textCpf;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textNumero;
    private javax.swing.JTextField textNumeroCnh;
    private javax.swing.JTextField textRua;
    private javax.swing.JTextField textSegundoContato;
    private javax.swing.JTextField textTelefone;
    // End of variables declaration//GEN-END:variables
}