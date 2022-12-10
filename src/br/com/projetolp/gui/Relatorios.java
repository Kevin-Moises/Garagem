package br.com.projetolp.gui;

import br.com.projetolp.dao.ConexaoDAO;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorios extends javax.swing.JFrame {

    final String TITULO = "Gerar relatórios";
    Connection conexao = null;

    public Relatorios() {
        initComponents();
        initUi();
        conexao = ConexaoDAO.getConnection();
    }

    private void initUi() {
        FlatLightLaf.setup();
        FlatLightLaf.updateUI();
        this.setLocationRelativeTo(null);
        this.setTitle(TITULO);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        btnGerarRelatorio.putClientProperty("JButton.buttonType",
                "roundRect");
        btnCancelar.putClientProperty("JButton.buttonType",
                "roundRect");
        btnLimpar.putClientProperty("JButton.buttonType",
                "roundRect");
        radioDesativado.setVisible(false);
    }

    private void limparCampos() {
        radioCarros.setSelected(false);
        radioClientes.setSelected(false);
        radioFuncionarios.setSelected(false);
        radioDesativado.setSelected(true);
    }

    private void geraRelatorioClientes() {
        try {
            JasperPrint print = JasperFillManager.fillReport(
                    "C:/Users/Kevin/Documents/"
                    + "NetBeansProjects/GaragemDeVeiculos-master"
                    + "/reports/clientes.jasper", null,
                    conexao);
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            System.out.println("Erro ao gerar relatorio:\n" + e.getMessage());
        }
    }
    
    private void geraRelatorioCarros() {
        try {
            JasperPrint print = JasperFillManager.fillReport(
                    "C:/Users/Kevin/Documents/"
                    + "NetBeansProjects/GaragemDeVeiculos-master"
                    + "/reports/carros.jasper", null,
                    conexao);
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            System.out.println("Erro ao gerar relatorio:\n" + e.getMessage());
        }
    }
    
    private void geraRelatorioFuncionarios() {
        try {
            JasperPrint print = JasperFillManager.fillReport(
                    "C:/Users/Kevin/Documents/"
                    + "NetBeansProjects/GaragemDeVeiculos-master"
                    + "/reports/funcionarios.jasper", null,
                    conexao);
            JasperViewer.viewReport(print, false);
        } catch (JRException e) {
            System.out.println("Erro ao gerar relatorio:\n" + e.getMessage());
        }
    }

    private void geraRelatorio() {
        if (radioClientes.isSelected()) 
            geraRelatorioClientes();
        else if(radioCarros.isSelected())
            geraRelatorioCarros();
        else if(radioFuncionarios.isSelected())
            geraRelatorioFuncionarios();
        else
            JOptionPane.showMessageDialog(null, "Por favor, "
                    + "selecione um tipo de relatório para ser gerado!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoRelatorio = new javax.swing.ButtonGroup();
        panelRelatorio = new javax.swing.JPanel();
        labelTipoRelatorio = new javax.swing.JLabel();
        radioClientes = new javax.swing.JRadioButton();
        radioCarros = new javax.swing.JRadioButton();
        radioFuncionarios = new javax.swing.JRadioButton();
        btnGerarRelatorio = new javax.swing.JButton();
        radioDesativado = new javax.swing.JRadioButton();
        btnCancelar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelRelatorio.setBorder(javax.swing.BorderFactory.createTitledBorder("Gerar relatórios"));

        labelTipoRelatorio.setText("Tipo de Relatório");

        grupoRelatorio.add(radioClientes);
        radioClientes.setText("Clientes");

        grupoRelatorio.add(radioCarros);
        radioCarros.setText("Carros");

        grupoRelatorio.add(radioFuncionarios);
        radioFuncionarios.setText("Funcionários");

        btnGerarRelatorio.setText("Gerar relatório");
        btnGerarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarRelatorioActionPerformed(evt);
            }
        });

        grupoRelatorio.add(radioDesativado);
        radioDesativado.setText("Desativado");

        btnCancelar.setText("Cancelar");

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRelatorioLayout = new javax.swing.GroupLayout(panelRelatorio);
        panelRelatorio.setLayout(panelRelatorioLayout);
        panelRelatorioLayout.setHorizontalGroup(
            panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRelatorioLayout.createSequentialGroup()
                        .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelTipoRelatorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRelatorioLayout.createSequentialGroup()
                                .addComponent(radioClientes)
                                .addGap(18, 18, 18)
                                .addComponent(radioCarros)
                                .addGap(18, 18, 18)
                                .addComponent(radioFuncionarios))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRelatorioLayout.createSequentialGroup()
                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnGerarRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(radioDesativado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRelatorioLayout.setVerticalGroup(
            panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRelatorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTipoRelatorio)
                    .addComponent(radioClientes)
                    .addComponent(radioCarros)
                    .addComponent(radioFuncionarios))
                .addGap(18, 18, 18)
                .addComponent(radioDesativado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGerarRelatorio)
                    .addComponent(btnCancelar)
                    .addComponent(btnLimpar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setBounds(0, 0, 406, 144);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnGerarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarRelatorioActionPerformed
        geraRelatorio();
    }//GEN-LAST:event_btnGerarRelatorioActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Relatorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new Relatorios().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGerarRelatorio;
    private javax.swing.JButton btnLimpar;
    private javax.swing.ButtonGroup grupoRelatorio;
    private javax.swing.JLabel labelTipoRelatorio;
    private javax.swing.JPanel panelRelatorio;
    private javax.swing.JRadioButton radioCarros;
    private javax.swing.JRadioButton radioClientes;
    private javax.swing.JRadioButton radioDesativado;
    private javax.swing.JRadioButton radioFuncionarios;
    // End of variables declaration//GEN-END:variables
}
