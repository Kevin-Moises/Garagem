package br.com.projetolp.gui.cads;

import br.com.projetolp.ctr.CarroCTR;
import br.com.projetolp.dto.CarroDTO;
import br.com.projetolp.gui.Inicio;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;

public class CadsCarros extends javax.swing.JFrame {
    
    final String TITULO = "Cadastro de Carros";
    
    public CadsCarros() {
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
        textPlaca.putClientProperty("JComponent.roundRect", true);
        textPlaca.putClientProperty("JComponent.roundRect", true);
        textChassi.putClientProperty("JComponent.roundRect", true);
        textAno.putClientProperty("JComponent.roundRect", true);
        textMontadora.putClientProperty("JComponent.roundRect", true);
        textModelo.putClientProperty("JComponent.roundRect", true);
        formattedUltimoAluguel.putClientProperty("JComponent.roundRect", 
                true);
        btnCadastrar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnCancelar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpar.putClientProperty("JButton.buttonType", "roundRect");
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
        textPlaca.setText("");
        textPlaca.requestFocus();
        textChassi.setText("");
        textAno.setText("");
        textMontadora.setText("");
        textModelo.setText("");
        formattedUltimoAluguel.setText("");
        boxDisponivel.setSelected(false);
    }
    
    private void cadastraCarro() {
        CarroCTR carroCtr = new CarroCTR();
        CarroDTO c = new CarroDTO();
        
        try {
            c.setPlaca(textPlaca.getText());
            c.setChassi(textChassi.getText());
            c.setAno(Integer.parseInt(textAno.getText()));
            c.setModelo(textModelo.getText());
            c.setMontadora(textMontadora.getText());
            c.setUltimoAluguel(formattedUltimoAluguel.getText());
            if(boxDisponivel.isSelected())
                c.setDisponivel(0);
            else
                c.setDisponivel(1);
            
            if(textPlaca.getText().isEmpty() || textChassi.getText().isEmpty() 
                    || textAno.getText().isEmpty() 
                    || textModelo.getText().isEmpty() 
                    || textMontadora.getText().isEmpty())
                JOptionPane.showMessageDialog(null, "Por "
                        + "favor, preencha todos os campos do formulário!", 
                        "Atenção!", JOptionPane.WARNING_MESSAGE);
            else {
                JOptionPane.showMessageDialog(null, 
                        carroCtr.cadastraCarro(c), "Mensagem!", 
                        JOptionPane.INFORMATION_MESSAGE);
                limpaCampos();
            }
        } 
        catch (NumberFormatException ef) {
            JOptionPane.showMessageDialog(null, "O campo ano "
                    + "precisa ser preenchido com números!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
            textAno.setText("");
            textAno.requestFocus();
        }
        catch (HeadlessException e) {
            System.out.println("Falha ao cadastrar carro:\n" + e.getMessage());
            
        }
        finally {
            carroCtr.closeDb();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSuperior = new javax.swing.JPanel();
        labelFuncionarioConectado = new javax.swing.JLabel();
        labelFuncionario = new javax.swing.JLabel();
        labelSetorFuncionario = new javax.swing.JLabel();
        labelSetor = new javax.swing.JLabel();
        panelInfoDocumento = new javax.swing.JPanel();
        labelPlaca = new javax.swing.JLabel();
        textPlaca = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        textChassi = new javax.swing.JTextField();
        labelAno = new javax.swing.JLabel();
        textAno = new javax.swing.JTextField();
        panelInfoAluguel = new javax.swing.JPanel();
        labelMontadora = new javax.swing.JLabel();
        textMontadora = new javax.swing.JTextField();
        labelModelo = new javax.swing.JLabel();
        textModelo = new javax.swing.JTextField();
        labelUltimoAluguel = new javax.swing.JLabel();
        formattedUltimoAluguel = new javax.swing.JFormattedTextField();
        boxDisponivel = new javax.swing.JCheckBox();
        btnCadastrar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelSuperior.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labelFuncionarioConectado.setText("Funcionário conectado");

        labelFuncionario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelFuncionario.setText("jLabel1");

        labelSetorFuncionario.setText("Setor do Funcionário");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        panelInfoDocumento.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Documento"));

        labelPlaca.setText("Placa");

        jLabel1.setText("Chassi");

        labelAno.setText("Ano");

        javax.swing.GroupLayout panelInfoDocumentoLayout = new javax.swing.GroupLayout(panelInfoDocumento);
        panelInfoDocumento.setLayout(panelInfoDocumentoLayout);
        panelInfoDocumentoLayout.setHorizontalGroup(
            panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                .addComponent(labelPlaca)
                .addGap(18, 18, 18)
                .addComponent(textPlaca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelAno)
                .addGap(18, 18, 18)
                .addComponent(textAno, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoDocumentoLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textChassi, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(197, 197, 197))
        );
        panelInfoDocumentoLayout.setVerticalGroup(
            panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPlaca)
                    .addComponent(textPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAno)
                    .addComponent(textAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textChassi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoAluguel.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Aluguel"));

        labelMontadora.setText("Montadora");

        labelModelo.setText("Modelo");

        labelUltimoAluguel.setText("Ultimo aluguel");

        try {
            formattedUltimoAluguel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        boxDisponivel.setText("Disponível para aluguel?");

        javax.swing.GroupLayout panelInfoAluguelLayout = new javax.swing.GroupLayout(panelInfoAluguel);
        panelInfoAluguel.setLayout(panelInfoAluguelLayout);
        panelInfoAluguelLayout.setHorizontalGroup(
            panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoAluguelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoAluguelLayout.createSequentialGroup()
                        .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelInfoAluguelLayout.createSequentialGroup()
                                .addComponent(labelMontadora)
                                .addGap(37, 37, 37)
                                .addComponent(textMontadora, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                            .addGroup(panelInfoAluguelLayout.createSequentialGroup()
                                .addComponent(labelUltimoAluguel)
                                .addGap(18, 18, 18)
                                .addComponent(formattedUltimoAluguel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelModelo)
                        .addGap(18, 18, 18)
                        .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInfoAluguelLayout.createSequentialGroup()
                        .addComponent(boxDisponivel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelInfoAluguelLayout.setVerticalGroup(
            panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoAluguelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMontadora)
                    .addComponent(textMontadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelModelo)
                    .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUltimoAluguel)
                    .addComponent(formattedUltimoAluguel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(boxDisponivel))
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
                    .addComponent(panelInfoAluguel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoAluguel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnLimpar)
                    .addComponent(btnCadastrar))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        setBounds(0, 0, 416, 392);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelaCadastro();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpaCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastraCarro();
    }//GEN-LAST:event_btnCadastrarActionPerformed

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
            java.util.logging.Logger.getLogger(CadsCarros.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new CadsCarros().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxDisponivel;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JFormattedTextField formattedUltimoAluguel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelAno;
    private javax.swing.JLabel labelFuncionario;
    private javax.swing.JLabel labelFuncionarioConectado;
    private javax.swing.JLabel labelModelo;
    private javax.swing.JLabel labelMontadora;
    private javax.swing.JLabel labelPlaca;
    private javax.swing.JLabel labelSetor;
    private javax.swing.JLabel labelSetorFuncionario;
    private javax.swing.JLabel labelUltimoAluguel;
    private javax.swing.JPanel panelInfoAluguel;
    private javax.swing.JPanel panelInfoDocumento;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JTextField textAno;
    private javax.swing.JTextField textChassi;
    private javax.swing.JTextField textModelo;
    private javax.swing.JTextField textMontadora;
    private javax.swing.JTextField textPlaca;
    // End of variables declaration//GEN-END:variables
}