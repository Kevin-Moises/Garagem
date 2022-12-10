package br.com.projetolp.gui.pesqs;

import br.com.projetolp.ctr.ClienteCTR;
import br.com.projetolp.dto.ClienteDTO;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PesqsClientes extends javax.swing.JFrame {
    
    final String TITULO = "Pesquisa de Clientes";
    DefaultTableModel modeloTabelaClientes;
    
    public PesqsClientes() {
        initComponents();
        initUi();
        centralizaTabela();
        modeloTabelaClientes = (DefaultTableModel) tabelaClientes
                .getModel();
    }
    
    private void initUi() {
        FlatLightLaf.setup();
        FlatLightLaf.updateUI();
        this.setLocationRelativeTo(null);
        this.setTitle(TITULO);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        textPesqNumCnh.putClientProperty("JComponent.roundRect", true);
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
        btnPesquisar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpaFiltros.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpaTabela.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnAlterar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnExcluir.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpar.putClientProperty("JButton.buttonType", 
                "roundRect");
        textSegundoContato.setEnabled(false);
        btnRemContato.setEnabled(false);
        radioDesativado.setVisible(false);
    }
    
    private void centralizaTabela() {
        DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
	cellRender.setHorizontalAlignment(SwingConstants.CENTER);
        tabelaClientes.setDefaultRenderer(Object.class, 
                cellRender);
    }
    
    private void limpaFiltros() {
        radioNome.setSelected(false);
        radioCpf.setSelected(false);
        radioCidade.setSelected(false);
        radioDesativado.setSelected(true);
        modeloTabelaClientes.setNumRows(0);
    }
    
    private void limpaTabela() {
        if(!textPesqNumCnh.getText().isEmpty())
            textPesqNumCnh.setText("");
        
        limpaFiltros();
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
    
    private void manipulaContatosAoPreencher(boolean a, boolean b, boolean c, 
            String content) {
        btnAddContato.setEnabled(a);
        btnRemContato.setEnabled(b);
        textSegundoContato.setEnabled(c);
        textSegundoContato.setText(content);
    }
    
    private void addSegundoContato() {
        manipulaContatos(false, true, true);
    }
    
    private void remSegundoContato() {
        if(!textSegundoContato.getText().isEmpty())
            manipulaContatosAoPreencher(true, false, false, "");
        else
            manipulaContatos(true, false, false);
    }
    
    
    private void buscaCliente() {
        ClienteCTR clienteCtr = new ClienteCTR();
        ClienteDTO c = new ClienteDTO();
        
        try {
            ResultSet rs = null;
            
            if(!textPesqNumCnh.getText().isEmpty()) {
                modeloTabelaClientes.setNumRows(0);
                c.setNumCNH(textPesqNumCnh.getText());
                rs = clienteCtr.buscaCliente(c, "cnh");
            } else if(radioNome.isSelected()) {
                modeloTabelaClientes.setNumRows(0);
                rs = clienteCtr.buscaCliente(c, "nome");
            } else if(radioCpf.isSelected()) {
                modeloTabelaClientes.setNumRows(0);
                rs = clienteCtr.buscaCliente(c, "cpf");
            } else if(radioCidade.isSelected()) {
                modeloTabelaClientes.setNumRows(0);
                rs = clienteCtr.buscaCliente(c, "cidade");
            } else {
                modeloTabelaClientes.setNumRows(0);
                rs = clienteCtr.buscaCliente(c, "padrao");
            }
            
            while(rs.next()) {
                modeloTabelaClientes.addRow(new Object[] {
                    rs.getString("nomecli"),
                    rs.getString("cpf"),
                    rs.getString("cidade"),
                    rs.getString("contatocli"),
                });
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar clientes no banco:\n" 
                    + e.getMessage());
        }
        finally {
            clienteCtr.closeDb();
        }
    }
    
    private void preencheCampos(String cpf) {
        ClienteCTR clienteCtr = new ClienteCTR();
        ClienteDTO c = new ClienteDTO();
        
        try {
            c.setCpf(cpf);
            
            ResultSet rs = clienteCtr.buscaCliente(c, "preencher");
            
            if(rs.next()) {
                textNome.setText(rs.getString("nomecli"));
                textNumeroCnh.setText(rs.getString("numcnh"));
                textCpf.setText(rs.getString("cpf"));
                textCidade.setText(rs.getString("cidade"));
                textNumero.setText(rs.getString("num"));
                textRua.setText(rs.getString("rua"));
                textCep.setText(rs.getString("cep"));
                textTelefone.setText(rs.getString("contatocli"));
                
                if(rs.getString("segundocontato") == null) 
                    manipulaContatosAoPreencher(true, false, false, 
                            "");
                else 
                    manipulaContatosAoPreencher(false, true, true, 
                            rs.getString("segundocontato"));
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher os campos do formulario:\n"
                    + e.getMessage());
        } 
        finally {
            clienteCtr.closeDb();
        }
    }
    
    private void alteraCliente() {
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
            
            JOptionPane.showMessageDialog(null, 
                        clienteCtr.alteraCliente(c), 
                        "Mensagem!", 
                        JOptionPane.INFORMATION_MESSAGE);
            limpaCampos();
            buscaCliente();
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
    
    private void excluiCliente() {
        ClienteCTR clienteCtr = new ClienteCTR();
        ClienteDTO c = new ClienteDTO();
        
        try {
            if(textCpf.getText().isEmpty())
                JOptionPane.showMessageDialog(null, 
                    "Insira um CPF válido para excluir o registro!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
            else {
                c.setCpf(textCpf.getText());
                JOptionPane.showMessageDialog(null, 
                    clienteCtr.excluiCliente(c), "Mensagem!", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            buscaCliente();
            limpaCampos();
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

        grupoBusca = new javax.swing.ButtonGroup();
        panelPesquisaAvancada = new javax.swing.JPanel();
        labelPesqCpf = new javax.swing.JLabel();
        textPesqNumCnh = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        labelBuscarPor = new javax.swing.JLabel();
        radioNome = new javax.swing.JRadioButton();
        radioCpf = new javax.swing.JRadioButton();
        radioCidade = new javax.swing.JRadioButton();
        radioDesativado = new javax.swing.JRadioButton();
        btnLimpaFiltros = new javax.swing.JButton();
        btnLimpaTabela = new javax.swing.JButton();
        panelGerenciarCadastro = new javax.swing.JPanel();
        panelInfoDocumento = new javax.swing.JPanel();
        labelNome = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        labelNumeroCnh = new javax.swing.JLabel();
        textNumeroCnh = new javax.swing.JTextField();
        labelCpf = new javax.swing.JLabel();
        textCpf = new javax.swing.JTextField();
        panelInfoPessoal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textCidade = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        textNumero = new javax.swing.JTextField();
        textRua = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textCep = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        btnAddContato = new javax.swing.JButton();
        btnRemContato = new javax.swing.JButton();
        textSegundoContato = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        panelTabela = new javax.swing.JScrollPane();
        tabelaClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPesquisaAvancada.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa avançada"));

        labelPesqCpf.setText("Número CNH");

        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        labelBuscarPor.setText("Buscar por");

        grupoBusca.add(radioNome);
        radioNome.setText("Nome");
        radioNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioNomeActionPerformed(evt);
            }
        });

        grupoBusca.add(radioCpf);
        radioCpf.setText("CPF");
        radioCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCpfActionPerformed(evt);
            }
        });

        grupoBusca.add(radioCidade);
        radioCidade.setText("Cidade");
        radioCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioCidadeActionPerformed(evt);
            }
        });

        grupoBusca.add(radioDesativado);
        radioDesativado.setText("Desativado");

        btnLimpaFiltros.setText("Limpa filtros");
        btnLimpaFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpaFiltrosActionPerformed(evt);
            }
        });

        btnLimpaTabela.setText("Limpar tabela");
        btnLimpaTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpaTabelaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPesquisaAvancadaLayout = new javax.swing.GroupLayout(panelPesquisaAvancada);
        panelPesquisaAvancada.setLayout(panelPesquisaAvancadaLayout);
        panelPesquisaAvancadaLayout.setHorizontalGroup(
            panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                        .addComponent(labelPesqCpf)
                        .addGap(18, 18, 18)
                        .addComponent(textPesqNumCnh, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                        .addComponent(btnLimpaFiltros)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpaTabela))
                    .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                        .addComponent(labelBuscarPor)
                        .addGap(18, 18, 18)
                        .addComponent(radioNome)
                        .addGap(18, 18, 18)
                        .addComponent(radioCpf)
                        .addGap(18, 18, 18)
                        .addComponent(radioCidade)
                        .addGap(18, 18, 18)
                        .addComponent(radioDesativado)))
                .addGap(4, 4, 4))
        );
        panelPesquisaAvancadaLayout.setVerticalGroup(
            panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPesqCpf)
                    .addComponent(textPesqNumCnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar))
                .addGap(18, 18, 18)
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBuscarPor)
                    .addComponent(radioNome)
                    .addComponent(radioCpf)
                    .addComponent(radioCidade)
                    .addComponent(radioDesativado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpaFiltros)
                    .addComponent(btnLimpaTabela))
                .addContainerGap())
        );

        panelGerenciarCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Gerenciar cadastro de clientes"));

        panelInfoDocumento.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Documento"));

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
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                        .addComponent(textNumeroCnh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelCpf)
                        .addGap(18, 18, 18)
                        .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(labelCpf)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoPessoal.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Pessoais"));

        jLabel1.setText("Cidade");

        jLabel2.setText("Número");

        jLabel3.setText("Rua");

        jLabel4.setText("CEP");

        javax.swing.GroupLayout panelInfoPessoalLayout = new javax.swing.GroupLayout(panelInfoPessoal);
        panelInfoPessoal.setLayout(panelInfoPessoalLayout);
        panelInfoPessoalLayout.setHorizontalGroup(
            panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textRua)
                    .addComponent(textCep, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelInfoPessoalLayout.setVerticalGroup(
            panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Contato"));

        jLabel5.setText("Telefone");

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

        jLabel6.setText("Segundo contato");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddContato)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemContato))
                    .addComponent(textSegundoContato, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textTelefone))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddContato)
                    .addComponent(btnRemContato)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textSegundoContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGerenciarCadastroLayout = new javax.swing.GroupLayout(panelGerenciarCadastro);
        panelGerenciarCadastro.setLayout(panelGerenciarCadastroLayout);
        panelGerenciarCadastroLayout.setHorizontalGroup(
            panelGerenciarCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGerenciarCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGerenciarCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGerenciarCadastroLayout.createSequentialGroup()
                        .addGroup(panelGerenciarCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelInfoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelInfoPessoal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelGerenciarCadastroLayout.createSequentialGroup()
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelGerenciarCadastroLayout.setVerticalGroup(
            panelGerenciarCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGerenciarCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInfoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoPessoal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(panelGerenciarCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAlterar)
                    .addComponent(btnExcluir)
                    .addComponent(btnLimpar))
                .addContainerGap())
        );

        tabelaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "CPF", "Cidade", "Contato"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaClientes.getTableHeader().setReorderingAllowed(false);
        tabelaClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaClientesMouseClicked(evt);
            }
        });
        panelTabela.setViewportView(tabelaClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTabela)
                    .addComponent(panelPesquisaAvancada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGerenciarCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelPesquisaAvancada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(panelGerenciarCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpaFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpaFiltrosActionPerformed
        limpaFiltros();
    }//GEN-LAST:event_btnLimpaFiltrosActionPerformed

    private void btnLimpaTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpaTabelaActionPerformed
        limpaTabela();
    }//GEN-LAST:event_btnLimpaTabelaActionPerformed

    private void radioNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNomeActionPerformed
        buscaCliente();
    }//GEN-LAST:event_radioNomeActionPerformed

    private void radioCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCpfActionPerformed
        buscaCliente();
    }//GEN-LAST:event_radioCpfActionPerformed

    private void radioCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCidadeActionPerformed
        buscaCliente();
    }//GEN-LAST:event_radioCidadeActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        buscaCliente();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnAddContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContatoActionPerformed
        addSegundoContato();
    }//GEN-LAST:event_btnAddContatoActionPerformed

    private void btnRemContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemContatoActionPerformed
        remSegundoContato();
    }//GEN-LAST:event_btnRemContatoActionPerformed

    private void tabelaClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaClientesMouseClicked
        preencheCampos(String.valueOf(
                tabelaClientes.getValueAt(
                tabelaClientes.getSelectedRow(), 1)));
    }//GEN-LAST:event_tabelaClientesMouseClicked

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpaCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alteraCliente();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluiCliente();
    }//GEN-LAST:event_btnExcluirActionPerformed

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
            java.util.logging.Logger.getLogger(PesqsClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new PesqsClientes().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddContato;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpaFiltros;
    private javax.swing.JButton btnLimpaTabela;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnRemContato;
    private javax.swing.ButtonGroup grupoBusca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelBuscarPor;
    private javax.swing.JLabel labelCpf;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelNumeroCnh;
    private javax.swing.JLabel labelPesqCpf;
    private javax.swing.JPanel panelGerenciarCadastro;
    private javax.swing.JPanel panelInfoDocumento;
    private javax.swing.JPanel panelInfoPessoal;
    private javax.swing.JPanel panelPesquisaAvancada;
    private javax.swing.JScrollPane panelTabela;
    private javax.swing.JRadioButton radioCidade;
    private javax.swing.JRadioButton radioCpf;
    private javax.swing.JRadioButton radioDesativado;
    private javax.swing.JRadioButton radioNome;
    private javax.swing.JTable tabelaClientes;
    private javax.swing.JTextField textCep;
    private javax.swing.JTextField textCidade;
    private javax.swing.JTextField textCpf;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textNumero;
    private javax.swing.JTextField textNumeroCnh;
    private javax.swing.JTextField textPesqNumCnh;
    private javax.swing.JTextField textRua;
    private javax.swing.JTextField textSegundoContato;
    private javax.swing.JTextField textTelefone;
    // End of variables declaration//GEN-END:variables
}