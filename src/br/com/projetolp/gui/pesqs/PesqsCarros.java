package br.com.projetolp.gui.pesqs;

import br.com.projetolp.ctr.CarroCTR;
import br.com.projetolp.dto.CarroDTO;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PesqsCarros extends javax.swing.JFrame {
    
    final String TITULO = "Pesquisa de Carros";
    DefaultTableModel modeloTabelaCarros;

    public PesqsCarros() {
        initComponents();
        initUi();
        centralizaTabela();
        modeloTabelaCarros = (DefaultTableModel) tabelaCarros.getModel();
    }
    
    private void initUi() {
        FlatLightLaf.setup();
        FlatLightLaf.updateUI();
        this.setLocationRelativeTo(null);
        this.setTitle(TITULO);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        textChassi.putClientProperty("JComponent.roundRect", true);
        textChassi.requestFocus();
        textPlaca.putClientProperty("JComponent.roundRect", true);
        textAlterChassi.putClientProperty("JComponent.roundRect", true);
        textAno.putClientProperty("JComponent.roundRect", true);
        textMontadora.putClientProperty("JComponent.roundRect", true);
        formattedUltimoAluguel.putClientProperty("JComponent.roundRect", 
                true);
        textModelo.putClientProperty("JComponent.roundRect", true);
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
        radioDesativado.setVisible(false);
    }
    
    private void centralizaTabela() {
        DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
	cellRender.setHorizontalAlignment(SwingConstants.CENTER);
        tabelaCarros.setDefaultRenderer(Object.class, 
                cellRender);
    }
    
    private void limpaFiltros() {
        radioModelo.setSelected(false);
        radioPlaca.setSelected(false);
        radioUltimoAluguel.setSelected(false);
        radioDisponibilidade.setSelected(false);
        radioDesativado.setSelected(true);
        modeloTabelaCarros.setNumRows(0);
    }
    
    private void limpaTabela() {
        if(!textChassi.getText().isEmpty())
            textChassi.setText("");
        
        limpaFiltros();
    }
    
    private void limpaCampos() {
        textPlaca.setText("");
        textAlterChassi.setText("");
        textAno.setText("");
        textMontadora.setText("");
        formattedUltimoAluguel.setText("");
        textModelo.setText("");
        boxDisponivel.setSelected(false);
    }
    
    private void buscarCarros() {
        CarroDTO c = new CarroDTO();
        CarroCTR carroCtr = new CarroCTR();
        
        try {
            ResultSet rs = null;
            
            if(!textChassi.getText().isEmpty()) {
                modeloTabelaCarros.setNumRows(0);
                c.setChassi(textChassi.getText());
                rs = carroCtr.buscaCarro(c, "chassi");
            } else if(radioModelo.isSelected()) {
                modeloTabelaCarros.setNumRows(0);
                rs = carroCtr.buscaCarro(c, "modelo");
            } else if(radioPlaca.isSelected()) {
                modeloTabelaCarros.setNumRows(0);
                rs = carroCtr.buscaCarro(c, "placa");
            } else if(radioUltimoAluguel.isSelected()) {
                modeloTabelaCarros.setNumRows(0);
                rs = carroCtr.buscaCarro(c, "ultimoaluguel");
            } else if(radioDisponibilidade.isSelected()) {
                modeloTabelaCarros.setNumRows(0);
                rs = carroCtr.buscaCarro(c, "disponibilidade");
            } else {
                modeloTabelaCarros.setNumRows(0);
                rs = carroCtr.buscaCarro(c, "padrao");
            }
           
            while(rs.next()) {
                if(rs.getString("disponivel").equals("0"))
                    modeloTabelaCarros.addRow(new Object[] {
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("ultimoaluguel"),
                        "Disponível",
                    });
                else if(rs.getString("disponivel").equals("1"))
                    modeloTabelaCarros.addRow(new Object[] {
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("ultimoaluguel"),
                        "Indisponível",
                    });
            }
        } 
        catch (SQLException e) {
            System.out.println("Erro ao buscar carros no banco:\n" 
                    + e.getMessage());
        }
        finally {
           carroCtr.closeDb();
        }
    }
    
    private void preencheCampos(String placa) {
        CarroCTR carroCtr = new CarroCTR();
        CarroDTO c = new CarroDTO();
        
        try {
            c.setPlaca(placa);
            
            ResultSet rs = carroCtr.buscaCarro(c, "preencher");
            
            if(rs.next()) {
                textPlaca.setText(rs.getString("placa"));
                textAlterChassi.setText(rs.getString("chassi"));
                textAno.setText(rs.getString("ano"));
                textMontadora.setText(rs.getString("montadora"));
                formattedUltimoAluguel.setText(rs.getString(
                        "ultimoaluguel"));
                textModelo.setText(rs.getString("modelo"));
                
                if(rs.getString("disponivel").equals("0"))
                    boxDisponivel.setSelected(true);
                else
                    boxDisponivel.setSelected(false);
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher os campos do formulario:\n"
                    + e.getMessage());
        }
        finally {
            carroCtr.closeDb();
        }
    }
    
    private void alteraCarro() {
        CarroCTR carroCtr = new CarroCTR();
        CarroDTO c = new CarroDTO();
        
        try {
            c.setPlaca(textPlaca.getText());
            c.setChassi(textAlterChassi.getText());
            c.setAno(Integer.parseInt(textAno.getText()));
            c.setMontadora(textMontadora.getText());
            c.setUltimoAluguel(formattedUltimoAluguel.getText());
            c.setModelo(textModelo.getText());
            
            if(boxDisponivel.isSelected())
                c.setDisponivel(0);
            else
                c.setDisponivel(1);
            
            JOptionPane.showMessageDialog(null, 
                    carroCtr.alteraCarro(c), 
                    "Mensagem!", 
                    JOptionPane.INFORMATION_MESSAGE);
            buscarCarros();
            limpaCampos();
        } 
        catch (NumberFormatException ef) {
            JOptionPane.showMessageDialog(null, "O campo ano "
                    + "precisa ser preenchido com números!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
            textAno.setText("");
            textAno.requestFocus();
        }
        catch (HeadlessException e) {
            System.out.println("Erro ao alterar carro:\n" 
                    + e.getMessage());
        }
        finally {
            carroCtr.closeDb();
        }
    }
    
    private void excluiCarro() {
        CarroCTR carroCtr = new CarroCTR();
        CarroDTO c = new CarroDTO();
        
        try {
            if(textPlaca.getText().isEmpty())
                JOptionPane.showMessageDialog(null, 
                    "Insira uma placa válida para excluir o registro!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
            else {
                c.setPlaca(textPlaca.getText());
                JOptionPane.showMessageDialog(null, 
                    carroCtr.excluiCarro(c), "Mensagem!", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            buscarCarros();
            limpaCampos();
        } 
        catch (NumberFormatException ef) {
            JOptionPane.showMessageDialog(null, "O campo ano "
                    + "precisa ser preenchido com números!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
            textAno.setText("");
            textAno.requestFocus();
        }
        catch (HeadlessException e) {
            System.out.println("Erro ao excluir carro:\n" 
                    + e.getMessage());
        }
        finally {
            carroCtr.closeDb();
        }
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoBusca = new javax.swing.ButtonGroup();
        panelTabela = new javax.swing.JScrollPane();
        tabelaCarros = new javax.swing.JTable();
        panelPesquisaAvancada = new javax.swing.JPanel();
        labelChassi = new javax.swing.JLabel();
        textChassi = new javax.swing.JTextField();
        labelOrdenarPor = new javax.swing.JLabel();
        radioModelo = new javax.swing.JRadioButton();
        radioPlaca = new javax.swing.JRadioButton();
        radioUltimoAluguel = new javax.swing.JRadioButton();
        radioDisponibilidade = new javax.swing.JRadioButton();
        btnPesquisar = new javax.swing.JButton();
        btnLimpaFiltros = new javax.swing.JButton();
        radioDesativado = new javax.swing.JRadioButton();
        btnLimpaTabela = new javax.swing.JButton();
        panelGerenciarCadastro = new javax.swing.JPanel();
        panelInfoDocumento = new javax.swing.JPanel();
        labelPlaca = new javax.swing.JLabel();
        textPlaca = new javax.swing.JTextField();
        labelAlterChassi = new javax.swing.JLabel();
        textAlterChassi = new javax.swing.JTextField();
        labelAno = new javax.swing.JLabel();
        textAno = new javax.swing.JTextField();
        panelInfoAluguel = new javax.swing.JPanel();
        labelMontadora = new javax.swing.JLabel();
        textMontadora = new javax.swing.JTextField();
        labelUltimoAluguel = new javax.swing.JLabel();
        formattedUltimoAluguel = new javax.swing.JFormattedTextField();
        labelModelo = new javax.swing.JLabel();
        textModelo = new javax.swing.JTextField();
        boxDisponivel = new javax.swing.JCheckBox();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabelaCarros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Modelo", "Placa", "Ultimo aluguel", "Disponível"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaCarros.getTableHeader().setReorderingAllowed(false);
        tabelaCarros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaCarrosMouseClicked(evt);
            }
        });
        panelTabela.setViewportView(tabelaCarros);

        panelPesquisaAvancada.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa avançada"));

        labelChassi.setText("Chassi");

        labelOrdenarPor.setText("Buscar por");

        grupoBusca.add(radioModelo);
        radioModelo.setText("Modelo");
        radioModelo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioModeloMouseClicked(evt);
            }
        });

        grupoBusca.add(radioPlaca);
        radioPlaca.setText("Placa");
        radioPlaca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioPlacaMouseClicked(evt);
            }
        });

        grupoBusca.add(radioUltimoAluguel);
        radioUltimoAluguel.setText("Ultimo aluguel");
        radioUltimoAluguel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioUltimoAluguelMouseClicked(evt);
            }
        });

        grupoBusca.add(radioDisponibilidade);
        radioDisponibilidade.setText("Disponibilidade");
        radioDisponibilidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radioDisponibilidadeMouseClicked(evt);
            }
        });

        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnLimpaFiltros.setText("Limpar filtros");
        btnLimpaFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpaFiltrosActionPerformed(evt);
            }
        });

        grupoBusca.add(radioDesativado);
        radioDesativado.setText("Desativado");

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
                        .addComponent(labelChassi)
                        .addGap(18, 18, 18)
                        .addComponent(textChassi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                        .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                                .addComponent(btnLimpaFiltros)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpaTabela))
                            .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                                .addComponent(labelOrdenarPor)
                                .addGap(18, 18, 18)
                                .addComponent(radioModelo)
                                .addGap(18, 18, 18)
                                .addComponent(radioPlaca)
                                .addGap(18, 18, 18)
                                .addComponent(radioUltimoAluguel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioDisponibilidade, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(radioDesativado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        panelPesquisaAvancadaLayout.setVerticalGroup(
            panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelChassi)
                    .addComponent(textChassi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOrdenarPor)
                    .addComponent(radioModelo)
                    .addComponent(radioPlaca)
                    .addComponent(radioUltimoAluguel)
                    .addComponent(radioDisponibilidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpaFiltros)
                    .addComponent(radioDesativado)
                    .addComponent(btnLimpaTabela))
                .addGap(18, 18, 18))
        );

        panelGerenciarCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Gerenciar cadastro de carros"));

        panelInfoDocumento.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Documento"));

        labelPlaca.setText("Placa");

        labelAlterChassi.setText("Chassi");

        labelAno.setText("Ano");

        javax.swing.GroupLayout panelInfoDocumentoLayout = new javax.swing.GroupLayout(panelInfoDocumento);
        panelInfoDocumento.setLayout(panelInfoDocumentoLayout);
        panelInfoDocumentoLayout.setHorizontalGroup(
            panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                        .addComponent(labelPlaca)
                        .addGap(18, 18, 18)
                        .addComponent(textPlaca))
                    .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                        .addComponent(labelAlterChassi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textAlterChassi, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(labelAno)
                .addGap(18, 18, 18)
                .addComponent(textAno, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelInfoDocumentoLayout.setVerticalGroup(
            panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoDocumentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPlaca)
                    .addComponent(textPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAno))
                .addGap(18, 18, 18)
                .addGroup(panelInfoDocumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAlterChassi)
                    .addComponent(textAlterChassi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoAluguel.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Aluguel"));

        labelMontadora.setText("Montadora");

        labelUltimoAluguel.setText("Ultimo aluguel");

        try {
            formattedUltimoAluguel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        labelModelo.setText("Modelo");

        boxDisponivel.setText("Disponível para aluguel?");

        javax.swing.GroupLayout panelInfoAluguelLayout = new javax.swing.GroupLayout(panelInfoAluguel);
        panelInfoAluguel.setLayout(panelInfoAluguelLayout);
        panelInfoAluguelLayout.setHorizontalGroup(
            panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoAluguelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoAluguelLayout.createSequentialGroup()
                        .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelUltimoAluguel)
                            .addComponent(labelMontadora))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textMontadora, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(formattedUltimoAluguel))
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
                .addContainerGap()
                .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMontadora)
                    .addComponent(textMontadora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelModelo))
                .addGap(18, 18, 18)
                .addGroup(panelInfoAluguelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUltimoAluguel)
                    .addComponent(formattedUltimoAluguel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(boxDisponivel)
                .addContainerGap(12, Short.MAX_VALUE))
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
                    .addComponent(panelInfoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoAluguel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGerenciarCadastroLayout.createSequentialGroup()
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
                .addComponent(panelInfoAluguel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelGerenciarCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAlterar)
                    .addComponent(btnExcluir)
                    .addComponent(btnLimpar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addComponent(panelPesquisaAvancada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGerenciarCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelPesquisaAvancada, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelGerenciarCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioModeloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioModeloMouseClicked
        buscarCarros();
    }//GEN-LAST:event_radioModeloMouseClicked

    private void radioPlacaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioPlacaMouseClicked
        buscarCarros();
    }//GEN-LAST:event_radioPlacaMouseClicked

    private void radioUltimoAluguelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioUltimoAluguelMouseClicked
        buscarCarros();
    }//GEN-LAST:event_radioUltimoAluguelMouseClicked

    private void radioDisponibilidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioDisponibilidadeMouseClicked
        buscarCarros();
    }//GEN-LAST:event_radioDisponibilidadeMouseClicked

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        buscarCarros();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnLimpaFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpaFiltrosActionPerformed
        limpaFiltros();
    }//GEN-LAST:event_btnLimpaFiltrosActionPerformed

    private void btnLimpaTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpaTabelaActionPerformed
        limpaTabela();
    }//GEN-LAST:event_btnLimpaTabelaActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpaCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void tabelaCarrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaCarrosMouseClicked
        preencheCampos(String.valueOf(
                tabelaCarros.getValueAt(
                tabelaCarros.getSelectedRow(), 1)));
    }//GEN-LAST:event_tabelaCarrosMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alteraCarro();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluiCarro();
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
            java.util.logging.Logger.getLogger(PesqsCarros.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new PesqsCarros().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxDisponivel;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimpaFiltros;
    private javax.swing.JButton btnLimpaTabela;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JFormattedTextField formattedUltimoAluguel;
    private javax.swing.ButtonGroup grupoBusca;
    private javax.swing.JLabel labelAlterChassi;
    private javax.swing.JLabel labelAno;
    private javax.swing.JLabel labelChassi;
    private javax.swing.JLabel labelModelo;
    private javax.swing.JLabel labelMontadora;
    private javax.swing.JLabel labelOrdenarPor;
    private javax.swing.JLabel labelPlaca;
    private javax.swing.JLabel labelUltimoAluguel;
    private javax.swing.JPanel panelGerenciarCadastro;
    private javax.swing.JPanel panelInfoAluguel;
    private javax.swing.JPanel panelInfoDocumento;
    private javax.swing.JPanel panelPesquisaAvancada;
    private javax.swing.JScrollPane panelTabela;
    private javax.swing.JRadioButton radioDesativado;
    private javax.swing.JRadioButton radioDisponibilidade;
    private javax.swing.JRadioButton radioModelo;
    private javax.swing.JRadioButton radioPlaca;
    private javax.swing.JRadioButton radioUltimoAluguel;
    private javax.swing.JTable tabelaCarros;
    private javax.swing.JTextField textAlterChassi;
    private javax.swing.JTextField textAno;
    private javax.swing.JTextField textChassi;
    private javax.swing.JTextField textModelo;
    private javax.swing.JTextField textMontadora;
    private javax.swing.JTextField textPlaca;
    // End of variables declaration//GEN-END:variables
}