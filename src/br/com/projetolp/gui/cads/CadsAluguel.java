package br.com.projetolp.gui.cads;

import br.com.projetolp.ctr.AluguelCTR;
import br.com.projetolp.ctr.CarroCTR;
import br.com.projetolp.ctr.ClienteCTR;
import br.com.projetolp.ctr.FuncionarioCTR;
import br.com.projetolp.dto.AluguelDTO;
import br.com.projetolp.dto.CarroDTO;
import br.com.projetolp.dto.ClienteDTO;
import br.com.projetolp.dto.FuncionarioDTO;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CadsAluguel extends javax.swing.JFrame {
    
    final String TITULO = "Cadastro de Aluguel";
    DefaultTableModel modeloTabelaAlugueis;
    private boolean controleCamposAuto = false;
    
    Date data = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    public CadsAluguel() {
        initComponents();
        initUi();
        centralizaTabela();
        preencheCarro();
        preencheCliente();
        preencheFuncionario();
        modeloTabelaAlugueis = (DefaultTableModel) tabelaAlugueis.getModel();
        preencheTabela();
    }
    
    private void initUi() {
        FlatLightLaf.setup();
        FlatLightLaf.updateUI();
        this.setLocationRelativeTo(null);
        this.setTitle(TITULO);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        comboIdCarro.putClientProperty("JComponent.roundRect", true);
        textModelo.putClientProperty("JComponent.roundRect", 
                true);
        textDisponibilidade.putClientProperty("JComponent.roundRect", 
                true);
        textUltimoAluguel.putClientProperty("JComponent.roundRect", 
                true);
        comboIdCliente.putClientProperty("JComponent.roundRect", 
                true);
        textNomeCliente.putClientProperty("JComponent.roundRect", 
                true);
        textCpfCliente.putClientProperty("JComponent.roundRect", 
                true);
        textContatoCliente.putClientProperty("JComponent.roundRect", 
                true);
        comboIdFuncionario.putClientProperty("JComponent.roundRect", 
                true);
        textNomeFuncionario.putClientProperty("JComponent.roundRect", 
                true);
        textCpfFuncionario.putClientProperty("JComponent.roundRect", 
                true);
        textContatoFuncionario.putClientProperty("JComponent.roundRect", 
                true);
        textDataAluguel.putClientProperty("JComponent.roundRect", 
                true);
        textMotivoAluguel.putClientProperty("JComponent.roundRect", 
                true);
        btnCadastrar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpar.putClientProperty("JButton.buttonType", "roundRect");
    }
    
    private void centralizaTabela() {
        DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
	cellRender.setHorizontalAlignment(SwingConstants.CENTER);
        tabelaAlugueis.setDefaultRenderer(Object.class, 
                cellRender);
    }
    
    private void limpaCampos() {
        comboIdCarro.setSelectedItem(" ");
        comboIdCliente.setSelectedItem(" ");
        comboIdFuncionario.setSelectedItem(" ");
        limpaCamposComplementares();
    }
    
    private void setCamposComplementares() {
        textDataAluguel.setText(formatter.format(data));
    }
    
    private void limpaCamposComplementares() {
        textDataAluguel.setText("");
        textMotivoAluguel.setText("");
    }
    
    private void logSetCampos() {
        if(!controleCamposAuto) {
            setCamposComplementares();
            controleCamposAuto = true;
        }
    }
    
    private void preencheCarro() {
        CarroCTR carroCtr = new CarroCTR();
        CarroDTO carroDto = new CarroDTO();
        
        try {
            ResultSet rs = carroCtr.buscaCarro(carroDto, "aluguel");
            
            comboIdCarro.addItem(" ");
            while(rs.next()) {
                comboIdCarro.addItem(rs.getString("idcarro"));
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher os id de carros:\n"
                    + e.getMessage());
        }
        finally {
            carroCtr.closeDb();
        }
    }
    
    private void preencheCliente() {
        ClienteCTR clienteCtr = new ClienteCTR();
        ClienteDTO clienteDto = new ClienteDTO();
        
        try {
            ResultSet rs = clienteCtr.buscaCliente(clienteDto, "aluguel");
            
            comboIdCliente.addItem(" ");
            while(rs.next()) {
                comboIdCliente.addItem(rs.getString("idcli"));
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher os id de clientes:\n"
                    + e.getMessage());
        }
        finally {
            clienteCtr.closeDb();
        }
    }
    
    private void preencheFuncionario() {
        FuncionarioCTR funcionarioCtr = new FuncionarioCTR();
        FuncionarioDTO funcionarioDto = new FuncionarioDTO();
        
        try {
            ResultSet rs = funcionarioCtr.buscaFuncionario(funcionarioDto, 
                    "aluguel");
            
            comboIdFuncionario.addItem(" ");
            while(rs.next()) {
                comboIdFuncionario.addItem(rs.getString("idfunc"));
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher os id de clientes:\n"
                    + e.getMessage());
        } 
        finally {
        }
    }
    
    private void preencheCamposCarro() {
        CarroCTR carroCtr = new CarroCTR();
        CarroDTO c = new CarroDTO();
        
        try {
            if(!comboIdCarro.getSelectedItem().equals(" ")) {
                c.setIdCarro(Integer.parseInt(comboIdCarro.getSelectedItem()
                        .toString()));
                ResultSet rs = carroCtr.buscaCarro(c, "aluguelpreencher");
            
                if(rs.next()) {
                    if(rs.getString("disponivel").equals("0")) {
                        textModelo.setText(rs.getString("modelo"));
                        textDisponibilidade.setText("Disponível");
                        textUltimoAluguel.setText(rs.getString(
                                "ultimoaluguel"));
                    } else {
                        textModelo.setText(rs.getString("modelo"));
                        textDisponibilidade.setText("Indisponível");
                        textUltimoAluguel.setText(rs.getString(
                                "ultimoaluguel"));
                    }
                }
            } else {
                textModelo.setText("");
                textDisponibilidade.setText("");
                textUltimoAluguel.setText("");
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher os campos de carros:\n"
                    + e.getMessage());
        } 
        finally {
            carroCtr.closeDb();
        }
    }
    
    private void preencheCamposCliente() {
        ClienteCTR clienteCtr = new ClienteCTR();
        ClienteDTO c = new ClienteDTO();
        
        try {
            if(!comboIdCliente.getSelectedItem().equals(" ")) {
                c.setIdCli(Integer.parseInt(comboIdCliente.getSelectedItem()
                        .toString()));
                ResultSet rs = clienteCtr.buscaCliente(c, "aluguelpreencher");
            
                if(rs.next()) {
                    textNomeCliente.setText(rs.getString("nomecli"));
                    textCpfCliente.setText(rs.getString("cpf"));
                    textContatoCliente.setText(rs.getString(
                            "contatocli"));
                } 
            } else {
                textNomeCliente.setText("");
                textCpfCliente.setText("");
                textContatoCliente.setText("");
            }
        } catch (SQLException e) {
            System.out.println("Falha ao preencher os campos de cliente:\n"
                    + e.getMessage());
        } 
        finally {
            clienteCtr.closeDb();
        }
    }
    
    private void preencheCamposFuncionario() {
        FuncionarioCTR funcionarioCtr = new FuncionarioCTR();
        FuncionarioDTO funcionarioDto = new FuncionarioDTO();
        
        try {
            if(!comboIdFuncionario.getSelectedItem().equals(" ")) {
                funcionarioDto.setIdFunc(Integer.parseInt(
                        comboIdFuncionario.getSelectedItem().toString()));
                ResultSet rs = funcionarioCtr.buscaFuncionario(funcionarioDto, 
                        "aluguelpreencher");
            
                if(rs.next()) {
                    textNomeFuncionario.setText(rs.getString(
                            "nomefunc"));
                    textCpfFuncionario.setText(rs.getString("cpf"));
                    textContatoFuncionario.setText(rs.getString(
                            "contatofunc"));
                }
            }
            else {
                textNomeFuncionario.setText("");
                textCpfFuncionario.setText("");
                textContatoFuncionario.setText("");
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher os campos de funcionario:\n"
                    + e.getMessage());
        }
        finally {
            funcionarioCtr.closeDb();
        }
    }
    
    private void cadastraAluguel() {
        AluguelCTR aluguelCtr = new AluguelCTR();
        AluguelDTO a = new AluguelDTO();
        
        try {
            a.setDataAluguel(textDataAluguel.getText());
            a.setIdCarro(Integer.parseInt(comboIdCarro
                    .getSelectedItem().toString()));
            a.setIdCliente(Integer.parseInt(comboIdCliente
                    .getSelectedItem().toString()));
            a.setIdFuncionario(Integer.parseInt(comboIdFuncionario
                    .getSelectedItem().toString()));
            
            if(!textDisponibilidade.getText().equals("Indisponível")) {
                JOptionPane.showMessageDialog(null, 
                    aluguelCtr.cadastraAluguel(a), 
                    "Informação!", 
                    JOptionPane.INFORMATION_MESSAGE);
            
                limpaCampos();
                preencheTabela();
            } else {
               JOptionPane.showMessageDialog(null, 
                    "O veículo selecionado está indisponível, por favor "
                            + "selecione outro!", "Informação!", 
                    JOptionPane.WARNING_MESSAGE);
                limpaCampos();
            }
        } 
        catch (HeadlessException | NumberFormatException e) {
            System.out.println("Erro ao cadastrar aluguel:\n" + e.getMessage());
        } 
        finally {
            aluguelCtr.closeDb();
        }
    }
    
    private void preencheTabela() {
        AluguelCTR aluguelCtr = new AluguelCTR();
        AluguelDTO a = new AluguelDTO();
        
        try {
            ResultSet rs = aluguelCtr.buscaAluguel(a, "preencher");
            
            modeloTabelaAlugueis.setNumRows(0);
            while(rs.next()) {
                modeloTabelaAlugueis.addRow(new Object[] {
                    rs.getString("idaluguel"),
                    rs.getString("modelo"),
                    rs.getString("nomecli"),
                    rs.getString("contatocli"),
                    rs.getString("nomefunc"),
                    rs.getString("dataaluguel"),
                });
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher tabela:\n" + e.getMessage());
        } 
        finally {
            aluguelCtr.closeDb();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCarro = new javax.swing.JPanel();
        labelModelo = new javax.swing.JLabel();
        comboIdCarro = new javax.swing.JComboBox<>();
        labelDisponibilidade = new javax.swing.JLabel();
        textDisponibilidade = new javax.swing.JTextField();
        labelUltimoAluguel = new javax.swing.JLabel();
        textUltimoAluguel = new javax.swing.JTextField();
        labelIdVeiculo = new javax.swing.JLabel();
        textModelo = new javax.swing.JTextField();
        panelCliente = new javax.swing.JPanel();
        labelIdCliente = new javax.swing.JLabel();
        comboIdCliente = new javax.swing.JComboBox<>();
        labelNomeCliente = new javax.swing.JLabel();
        textNomeCliente = new javax.swing.JTextField();
        labelCpfCliente = new javax.swing.JLabel();
        textCpfCliente = new javax.swing.JTextField();
        labelContatoCliente = new javax.swing.JLabel();
        textContatoCliente = new javax.swing.JTextField();
        panelFuncionario = new javax.swing.JPanel();
        labelIdFuncionario = new javax.swing.JLabel();
        comboIdFuncionario = new javax.swing.JComboBox<>();
        labelNomeFuncionario = new javax.swing.JLabel();
        textNomeFuncionario = new javax.swing.JTextField();
        labelCpfFuncionario = new javax.swing.JLabel();
        textCpfFuncionario = new javax.swing.JTextField();
        labelContatoFuncionario = new javax.swing.JLabel();
        textContatoFuncionario = new javax.swing.JTextField();
        panelInfoComplementar = new javax.swing.JPanel();
        labelDataAlu = new javax.swing.JLabel();
        textDataAluguel = new javax.swing.JTextField();
        labelMotivoAlu = new javax.swing.JLabel();
        textMotivoAluguel = new javax.swing.JTextField();
        btnLimpar = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        panelTabela = new javax.swing.JScrollPane();
        tabelaAlugueis = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelCarro.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Veículo"));

        labelModelo.setText("Modelo");

        comboIdCarro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboIdCarroItemStateChanged(evt);
            }
        });
        comboIdCarro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboIdCarroMouseClicked(evt);
            }
        });

        labelDisponibilidade.setText("Disponibilidade");

        textDisponibilidade.setEnabled(false);

        labelUltimoAluguel.setText("Último aluguel");

        textUltimoAluguel.setEnabled(false);

        labelIdVeiculo.setText("ID veículo");

        textModelo.setEnabled(false);

        javax.swing.GroupLayout panelCarroLayout = new javax.swing.GroupLayout(panelCarro);
        panelCarro.setLayout(panelCarroLayout);
        panelCarroLayout.setHorizontalGroup(
            panelCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCarroLayout.createSequentialGroup()
                        .addComponent(labelIdVeiculo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboIdCarro, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCarroLayout.createSequentialGroup()
                        .addComponent(labelModelo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCarroLayout.createSequentialGroup()
                        .addComponent(labelDisponibilidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(textDisponibilidade, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCarroLayout.createSequentialGroup()
                        .addComponent(labelUltimoAluguel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textUltimoAluguel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelCarroLayout.setVerticalGroup(
            panelCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCarroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboIdCarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelIdVeiculo))
                .addGap(18, 18, 18)
                .addGroup(panelCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelModelo))
                .addGap(18, 18, 18)
                .addGroup(panelCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDisponibilidade)
                    .addComponent(textDisponibilidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCarroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUltimoAluguel)
                    .addComponent(textUltimoAluguel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Cliente"));

        labelIdCliente.setText("ID cliente");

        comboIdCliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboIdClienteItemStateChanged(evt);
            }
        });
        comboIdCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboIdClienteMouseClicked(evt);
            }
        });

        labelNomeCliente.setText("Nome");

        textNomeCliente.setEnabled(false);

        labelCpfCliente.setText("CPF");

        textCpfCliente.setEnabled(false);

        labelContatoCliente.setText("Contato");

        textContatoCliente.setEnabled(false);

        javax.swing.GroupLayout panelClienteLayout = new javax.swing.GroupLayout(panelCliente);
        panelCliente.setLayout(panelClienteLayout);
        panelClienteLayout.setHorizontalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(labelIdCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(comboIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(labelNomeCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(labelCpfCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textCpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelClienteLayout.createSequentialGroup()
                        .addComponent(labelContatoCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textContatoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelClienteLayout.setVerticalGroup(
            panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdCliente)
                    .addComponent(comboIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomeCliente)
                    .addComponent(textNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCpfCliente)
                    .addComponent(textCpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelContatoCliente)
                    .addComponent(textContatoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Funcionário"));

        labelIdFuncionario.setText("ID funcionário");

        comboIdFuncionario.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboIdFuncionarioItemStateChanged(evt);
            }
        });
        comboIdFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboIdFuncionarioMouseClicked(evt);
            }
        });

        labelNomeFuncionario.setText("Nome");

        textNomeFuncionario.setEnabled(false);

        labelCpfFuncionario.setText("CPF");

        textCpfFuncionario.setEnabled(false);

        labelContatoFuncionario.setText("Contato");

        textContatoFuncionario.setEnabled(false);

        javax.swing.GroupLayout panelFuncionarioLayout = new javax.swing.GroupLayout(panelFuncionario);
        panelFuncionario.setLayout(panelFuncionarioLayout);
        panelFuncionarioLayout.setHorizontalGroup(
            panelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFuncionarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIdFuncionario)
                    .addComponent(labelNomeFuncionario)
                    .addComponent(labelCpfFuncionario)
                    .addComponent(labelContatoFuncionario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(panelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textNomeFuncionario)
                    .addComponent(comboIdFuncionario, 0, 120, Short.MAX_VALUE)
                    .addComponent(textCpfFuncionario)
                    .addComponent(textContatoFuncionario))
                .addContainerGap())
        );
        panelFuncionarioLayout.setVerticalGroup(
            panelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFuncionarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdFuncionario)
                    .addComponent(comboIdFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNomeFuncionario)
                    .addComponent(textNomeFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCpfFuncionario)
                    .addComponent(textCpfFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelContatoFuncionario)
                    .addComponent(textContatoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoComplementar.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações complementares"));

        labelDataAlu.setText("Data aluguel");

        labelMotivoAlu.setText("Motivo aluguel");

        javax.swing.GroupLayout panelInfoComplementarLayout = new javax.swing.GroupLayout(panelInfoComplementar);
        panelInfoComplementar.setLayout(panelInfoComplementarLayout);
        panelInfoComplementarLayout.setHorizontalGroup(
            panelInfoComplementarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoComplementarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoComplementarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMotivoAlu)
                    .addComponent(labelDataAlu))
                .addGap(18, 18, 18)
                .addGroup(panelInfoComplementarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textMotivoAluguel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(textDataAluguel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelInfoComplementarLayout.setVerticalGroup(
            panelInfoComplementarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoComplementarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoComplementarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDataAlu)
                    .addComponent(textDataAluguel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoComplementarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMotivoAlu)
                    .addComponent(textMotivoAluguel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLimpar.setText("Limpar");

        btnCadastrar.setText("Cadastrar");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        tabelaAlugueis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID aluguel", "Modelo carro", "Nome cliente", "Contato cliente", "Funcionário", "Data aluguel"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaAlugueis.getTableHeader().setReorderingAllowed(false);
        panelTabela.setViewportView(tabelaAlugueis);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Gerenciar aluguel"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTabela)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelCarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panelInfoComplementar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCarro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoComplementar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpar)
                    .addComponent(btnCadastrar))
                .addGap(18, 18, 18)
                .addComponent(panelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboIdCarroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboIdCarroItemStateChanged
        preencheCamposCarro();
    }//GEN-LAST:event_comboIdCarroItemStateChanged

    private void comboIdClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboIdClienteItemStateChanged
        preencheCamposCliente();
    }//GEN-LAST:event_comboIdClienteItemStateChanged

    private void comboIdFuncionarioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboIdFuncionarioItemStateChanged
        preencheCamposFuncionario();
    }//GEN-LAST:event_comboIdFuncionarioItemStateChanged

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastraAluguel();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void comboIdCarroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboIdCarroMouseClicked
        logSetCampos();
    }//GEN-LAST:event_comboIdCarroMouseClicked

    private void comboIdClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboIdClienteMouseClicked
        logSetCampos();
    }//GEN-LAST:event_comboIdClienteMouseClicked

    private void comboIdFuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboIdFuncionarioMouseClicked
        logSetCampos();
    }//GEN-LAST:event_comboIdFuncionarioMouseClicked

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
            java.util.logging.Logger.getLogger(CadsAluguel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new CadsAluguel().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JComboBox<String> comboIdCarro;
    private javax.swing.JComboBox<String> comboIdCliente;
    private javax.swing.JComboBox<String> comboIdFuncionario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelContatoCliente;
    private javax.swing.JLabel labelContatoFuncionario;
    private javax.swing.JLabel labelCpfCliente;
    private javax.swing.JLabel labelCpfFuncionario;
    private javax.swing.JLabel labelDataAlu;
    private javax.swing.JLabel labelDisponibilidade;
    private javax.swing.JLabel labelIdCliente;
    private javax.swing.JLabel labelIdFuncionario;
    private javax.swing.JLabel labelIdVeiculo;
    private javax.swing.JLabel labelModelo;
    private javax.swing.JLabel labelMotivoAlu;
    private javax.swing.JLabel labelNomeCliente;
    private javax.swing.JLabel labelNomeFuncionario;
    private javax.swing.JLabel labelUltimoAluguel;
    private javax.swing.JPanel panelCarro;
    private javax.swing.JPanel panelCliente;
    private javax.swing.JPanel panelFuncionario;
    private javax.swing.JPanel panelInfoComplementar;
    private javax.swing.JScrollPane panelTabela;
    private javax.swing.JTable tabelaAlugueis;
    private javax.swing.JTextField textContatoCliente;
    private javax.swing.JTextField textContatoFuncionario;
    private javax.swing.JTextField textCpfCliente;
    private javax.swing.JTextField textCpfFuncionario;
    private javax.swing.JTextField textDataAluguel;
    private javax.swing.JTextField textDisponibilidade;
    private javax.swing.JTextField textModelo;
    private javax.swing.JTextField textMotivoAluguel;
    private javax.swing.JTextField textNomeCliente;
    private javax.swing.JTextField textNomeFuncionario;
    private javax.swing.JTextField textUltimoAluguel;
    // End of variables declaration//GEN-END:variables
}