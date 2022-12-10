package br.com.projetolp.gui;

import br.com.projetolp.gui.cads.CadsAluguel;
import br.com.projetolp.gui.cads.CadsCarros;
import br.com.projetolp.gui.cads.CadsClientes;
import br.com.projetolp.gui.cads.CadsFuncionarios;
import br.com.projetolp.gui.pesqs.PesqsCarros;
import br.com.projetolp.gui.pesqs.PesqsClientes;
import br.com.projetolp.gui.pesqs.PesqsFuncionarios;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;

public class Inicio extends javax.swing.JFrame {
    
    final String TITULO = "Sistema de Garagem";
    
    public static boolean login = false;
    public static boolean primeiraExecucao = true;
    public static String usuarioConectado = null;
    public static String usuarioSetor = null;
    public static String usuarioLogin = null;
    
    public Inicio() {
        initComponents();
        initGui();
    }
    
    private void initGui() {
        FlatLightLaf.setup();
        FlatLightLaf.updateUI();
        this.setLocationRelativeTo(null);
        this.setTitle(TITULO);
        this.setResizable(false);
        initMenus(false);
    }
    
    private void initMenus(boolean a) {
        jMenuCadastro.setEnabled(a);
        jMenuPesquisa.setEnabled(a);
        jMenuRelatório.setEnabled(a);
    }
    
    public static void menus(String setor) {
        if(setor.equalsIgnoreCase("financeiro")) {
            jMenuCadastro.setEnabled(false);
            jMenuPesquisa.setEnabled(true);
            jMenuRelatório.setEnabled(true);
        } else if(setor.equalsIgnoreCase("gerente")) {
            jMenuCadastro.setEnabled(true);
            jMenuPesquisa.setEnabled(true);
            jMenuRelatório.setEnabled(true);
        } else if(setor.equalsIgnoreCase("recepção")) {
            jMenuCadastro.setEnabled(true);
            jMenuPesquisa.setEnabled(true);
            jMenuRelatório.setEnabled(false);
        }   
    }
    
    private void logoff() {
        Object[] option = {"Não", "Sim"};
        
        if(JOptionPane.showOptionDialog(null, "Deseja "
                + "realmente fazer logoff do sistema?", "Atenção!", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, null, option, 
                option[1]) == 1) {
            primeiraExecucao = false;
            login = false;
            initMenus(false);
            abreLogin();
        }
    }
    
    private void sairSistema() {
        Object[] option = {"Não", "Sim"};
        
        if(JOptionPane.showOptionDialog(null, "Deseja "
                + "realmente sair do sistema?", "Atenção!", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, null, option, 
                option[1]) == 1)
            System.exit(0);
    }
    
    private void abreLogin() {
        Login l = new Login();
        l.setVisible(true);
    }
    
    private void abreCadsAluguel() {
        CadsAluguel cAluguel = new CadsAluguel();
        cAluguel.setVisible(true);
    }
    
    private void abreCadsCarros() {
        CadsCarros c = new CadsCarros();
        c.setVisible(true);
    }
    
    private void abreCadsFuncionarios() {
        CadsFuncionarios f = new CadsFuncionarios();
        f.setVisible(true);
    }
    
    private void abreCadsClientes() {
        CadsClientes cli = new CadsClientes();
        cli.setVisible(true);
    }
    
    private void abrePesqsCarros() {
        PesqsCarros pCarros = new PesqsCarros();
        pCarros.setVisible(true);
    }
    
    private void abrePesqsClientes() {
        PesqsClientes pClientes = new PesqsClientes();
        pClientes.setVisible(true);
    }
    
    private void abrePesqsFuncionarios() {
        PesqsFuncionarios pFunc = new PesqsFuncionarios();
        pFunc.setVisible(true);
    }
    
    private void abreRelatorios() {
        Relatorios r = new Relatorios();
        r.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBarraMenu = new javax.swing.JMenuBar();
        jMenuCadastro = new javax.swing.JMenu();
        jMenuItemCadAlugel = new javax.swing.JMenuItem();
        jMenuItemCadCarros = new javax.swing.JMenuItem();
        jMenuItemCadCliente = new javax.swing.JMenuItem();
        jMenuItemCadFunc = new javax.swing.JMenuItem();
        jMenuPesquisa = new javax.swing.JMenu();
        jMenuItemPesqCarros = new javax.swing.JMenuItem();
        jMenuItemPesqClientes = new javax.swing.JMenuItem();
        jMenuItemPesqFunc = new javax.swing.JMenuItem();
        jMenuRelatório = new javax.swing.JMenu();
        jMenuGerarRelatorio = new javax.swing.JMenuItem();
        jMenuSessao = new javax.swing.JMenu();
        jMenuItemEncerrar = new javax.swing.JMenuItem();
        jMenuSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenuCadastro.setText("Cadastro");
        jMenuCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuCadastroMouseClicked(evt);
            }
        });

        jMenuItemCadAlugel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemCadAlugel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/aluguel-de-carros.png"))); // NOI18N
        jMenuItemCadAlugel.setText("Cadastro  de Aluguel");
        jMenuItemCadAlugel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCadAlugelActionPerformed(evt);
            }
        });
        jMenuCadastro.add(jMenuItemCadAlugel);

        jMenuItemCadCarros.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemCadCarros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/carro.png"))); // NOI18N
        jMenuItemCadCarros.setText("Cadastro de Carros");
        jMenuItemCadCarros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCadCarrosActionPerformed(evt);
            }
        });
        jMenuCadastro.add(jMenuItemCadCarros);

        jMenuItemCadCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemCadCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/atendimento-ao-cliente.png"))); // NOI18N
        jMenuItemCadCliente.setText("Cadastro de Clientes");
        jMenuItemCadCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCadClienteActionPerformed(evt);
            }
        });
        jMenuCadastro.add(jMenuItemCadCliente);

        jMenuItemCadFunc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemCadFunc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/carteira-de-identidade.png"))); // NOI18N
        jMenuItemCadFunc.setText("Cadastro de Funcionários");
        jMenuItemCadFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCadFuncActionPerformed(evt);
            }
        });
        jMenuCadastro.add(jMenuItemCadFunc);

        jBarraMenu.add(jMenuCadastro);

        jMenuPesquisa.setText("Pesquisa");
        jMenuPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuPesquisaMouseClicked(evt);
            }
        });

        jMenuItemPesqCarros.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemPesqCarros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/lupa.png"))); // NOI18N
        jMenuItemPesqCarros.setText("Pesquisar Carros");
        jMenuItemPesqCarros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPesqCarrosActionPerformed(evt);
            }
        });
        jMenuPesquisa.add(jMenuItemPesqCarros);

        jMenuItemPesqClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemPesqClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/lupa.png"))); // NOI18N
        jMenuItemPesqClientes.setText("Pesquisar Clientes");
        jMenuItemPesqClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPesqClientesActionPerformed(evt);
            }
        });
        jMenuPesquisa.add(jMenuItemPesqClientes);

        jMenuItemPesqFunc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemPesqFunc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/lupa.png"))); // NOI18N
        jMenuItemPesqFunc.setText("Pesquisar Funcionários");
        jMenuItemPesqFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPesqFuncActionPerformed(evt);
            }
        });
        jMenuPesquisa.add(jMenuItemPesqFunc);

        jBarraMenu.add(jMenuPesquisa);

        jMenuRelatório.setText("Relatório");
        jMenuRelatório.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuRelatórioMouseClicked(evt);
            }
        });

        jMenuGerarRelatorio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuGerarRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/relatorio.png"))); // NOI18N
        jMenuGerarRelatorio.setText("Gerar Relatório");
        jMenuGerarRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuGerarRelatorioActionPerformed(evt);
            }
        });
        jMenuRelatório.add(jMenuGerarRelatorio);

        jBarraMenu.add(jMenuRelatório);

        jMenuSessao.setText("Sessão");

        jMenuItemEncerrar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemEncerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/projetolp/gui/resources/img/ligar.png"))); // NOI18N
        jMenuItemEncerrar.setText("Encerrar");
        jMenuItemEncerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEncerrarActionPerformed(evt);
            }
        });
        jMenuSessao.add(jMenuItemEncerrar);

        jBarraMenu.add(jMenuSessao);

        jMenuSair.setText("Sair");
        jMenuSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuSairMouseClicked(evt);
            }
        });
        jBarraMenu.add(jMenuSair);

        setJMenuBar(jBarraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 777, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuCadastroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuCadastroMouseClicked
        if(!login)
            abreLogin();
    }//GEN-LAST:event_jMenuCadastroMouseClicked

    private void jMenuPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuPesquisaMouseClicked
        if(!login)
            abreLogin();
    }//GEN-LAST:event_jMenuPesquisaMouseClicked

    private void jMenuRelatórioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuRelatórioMouseClicked
        if(!login)
            abreLogin();
    }//GEN-LAST:event_jMenuRelatórioMouseClicked

    private void jMenuItemCadCarrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCadCarrosActionPerformed
        abreCadsCarros();
    }//GEN-LAST:event_jMenuItemCadCarrosActionPerformed

    private void jMenuItemCadFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCadFuncActionPerformed
        abreCadsFuncionarios();
    }//GEN-LAST:event_jMenuItemCadFuncActionPerformed

    private void jMenuItemCadClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCadClienteActionPerformed
        abreCadsClientes();
    }//GEN-LAST:event_jMenuItemCadClienteActionPerformed

    private void jMenuItemPesqCarrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPesqCarrosActionPerformed
        abrePesqsCarros();
    }//GEN-LAST:event_jMenuItemPesqCarrosActionPerformed

    private void jMenuItemPesqClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPesqClientesActionPerformed
        abrePesqsClientes();
    }//GEN-LAST:event_jMenuItemPesqClientesActionPerformed

    private void jMenuItemPesqFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPesqFuncActionPerformed
        abrePesqsFuncionarios();
    }//GEN-LAST:event_jMenuItemPesqFuncActionPerformed

    private void jMenuSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuSairMouseClicked
        sairSistema();
    }//GEN-LAST:event_jMenuSairMouseClicked

    private void jMenuItemEncerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEncerrarActionPerformed
        logoff();
    }//GEN-LAST:event_jMenuItemEncerrarActionPerformed

    private void jMenuItemCadAlugelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCadAlugelActionPerformed
        abreCadsAluguel();
    }//GEN-LAST:event_jMenuItemCadAlugelActionPerformed

    private void jMenuGerarRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuGerarRelatorioActionPerformed
        abreRelatorios();
    }//GEN-LAST:event_jMenuGerarRelatorioActionPerformed

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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new Inicio().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jBarraMenu;
    public static javax.swing.JMenu jMenuCadastro;
    private javax.swing.JMenuItem jMenuGerarRelatorio;
    private javax.swing.JMenuItem jMenuItemCadAlugel;
    private javax.swing.JMenuItem jMenuItemCadCarros;
    private javax.swing.JMenuItem jMenuItemCadCliente;
    private javax.swing.JMenuItem jMenuItemCadFunc;
    private javax.swing.JMenuItem jMenuItemEncerrar;
    private javax.swing.JMenuItem jMenuItemPesqCarros;
    private javax.swing.JMenuItem jMenuItemPesqClientes;
    private javax.swing.JMenuItem jMenuItemPesqFunc;
    public static javax.swing.JMenu jMenuPesquisa;
    public static javax.swing.JMenu jMenuRelatório;
    private javax.swing.JMenu jMenuSair;
    private javax.swing.JMenu jMenuSessao;
    // End of variables declaration//GEN-END:variables
}