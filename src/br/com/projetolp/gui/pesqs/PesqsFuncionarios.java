package br.com.projetolp.gui.pesqs;

import br.com.projetolp.ctr.FuncionarioCTR;
import br.com.projetolp.dto.FuncionarioDTO;
import br.com.projetolp.gui.Inicio;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.HeadlessException;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class PesqsFuncionarios extends javax.swing.JFrame {

    final String TITULO = "Pesquisa de Funcionários";
    DefaultTableModel modeloTabelaFuncionarios;
    
    public PesqsFuncionarios() {
        initComponents();
        initUi();
        centralizaTabela();
        modeloTabelaFuncionarios = (DefaultTableModel) tabelaFuncionarios
                .getModel();
    }
    
    private void initUi() {
        FlatLightLaf.setup();
        FlatLightLaf.updateUI();
        this.setLocationRelativeTo(null);
        this.setTitle(TITULO);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        textPesqUsuario.putClientProperty("JComponent.roundRect", true);
        textNome.putClientProperty("JComponent.roundRect", true);
        textCpf.putClientProperty("JComponent.roundRect", true);
        textCidade.putClientProperty("JComponent.roundRect", true);
        textNumero.putClientProperty("JComponent.roundRect", true);
        textRua.putClientProperty("JComponent.roundRect", true);
        textCep.putClientProperty("JComponent.roundRect", true);
        textRamal.putClientProperty("JComponent.roundRect", true);
        comboTurno.putClientProperty("JComponent.roundRect", true);
        comboDepartamento.putClientProperty("JComponent.roundRect", 
                true);
        textTelefone.putClientProperty("JComponent.roundRect", true);
        textSegundoContato.putClientProperty("JComponent.roundRect", 
                true);
        textUsuario.putClientProperty("JComponent.roundRect", true);
        btnPesquisar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpaFiltros.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpaTabela.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnAddContato.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnRemContato.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnAlterar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnExcluir.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpar.putClientProperty("JButton.buttonType", 
                "roundRect");
        radioDesativado.setVisible(false);
        textSegundoContato.setEnabled(false);
        btnRemContato.setEnabled(false);
        radioDesativado.setVisible(false);
    }
    
    private void centralizaTabela() {
        DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer();
	cellRender.setHorizontalAlignment(SwingConstants.CENTER);
        tabelaFuncionarios.setDefaultRenderer(Object.class, 
                cellRender);
    }
    
    private void limpaFiltros() {
        radioNome.setSelected(false);
        radioCpf.setSelected(false);
        radioDepartamento.setSelected(false);
        radioDesativado.setSelected(true);
        modeloTabelaFuncionarios.setNumRows(0);
    }
    
    private void limpaTabela() {
        if(!textPesqUsuario.getText().isEmpty())
            textPesqUsuario.setText("");
        
        limpaFiltros();
    }
    
    private void limpaCampos() {
        textNome.setText("");
        textNome.requestFocus();
        textCpf.setText("");
        textCidade.setText("");
        textNumero.setText("");
        textRua.setText("");
        textCep.setText("");
        textRamal.setText("");
        comboTurno.setSelectedItem(" ");
        comboDepartamento.setSelectedItem(" ");
        textTelefone.setText("");
        
        if(textSegundoContato.isEnabled()) {
            textSegundoContato.setText("");
            manipulaContatos(true, false, false);
        }
        
        textUsuario.setText("");
        labelSenha.setText("");
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
        else {
            manipulaContatos(true, false, false);
            textTelefone.requestFocus();
        }
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

    
    private String descriptografarAes(String str) {
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
                    "AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            
            return new String(cipher.doFinal(Base64.getDecoder().
                    decode(str)));
        } 
        catch (InvalidAlgorithmParameterException | InvalidKeyException | 
                NoSuchAlgorithmException | InvalidKeySpecException | 
                BadPaddingException | IllegalBlockSizeException | 
                NoSuchPaddingException e) {
            System.out.println("Erro durante o processo de desencriptação:"
                    + "\n" + e);
        }
        return null;
    }
    
    private void buscaFuncionario() {
        FuncionarioCTR funcionarioCtr = new FuncionarioCTR();
        FuncionarioDTO f = new FuncionarioDTO();
        
        try {
            ResultSet rs = null;
            
            if(!textPesqUsuario.getText().isEmpty()) {
                modeloTabelaFuncionarios.setNumRows(0);
                f.setUsuario(textPesqUsuario.getText());
                rs = funcionarioCtr.buscaFuncionario(f, "usuario");
            } else if(radioNome.isSelected()) {
                modeloTabelaFuncionarios.setNumRows(0);
                rs = funcionarioCtr.buscaFuncionario(f, "nome");
            } else if(radioCpf.isSelected()) {
                modeloTabelaFuncionarios.setNumRows(0);
                rs = funcionarioCtr.buscaFuncionario(f, "cpf");
            } else if(radioDepartamento.isSelected()) {
                modeloTabelaFuncionarios.setNumRows(0);
                rs = funcionarioCtr.buscaFuncionario(f, "departamento");
            } else {
                modeloTabelaFuncionarios.setNumRows(0);
                rs = funcionarioCtr.buscaFuncionario(f, "padrao");
            }
            
            while(rs.next()) {
                modeloTabelaFuncionarios.addRow(new Object[] {
                    rs.getString("nomefunc"),
                    rs.getString("cpf"),
                    rs.getString("departamento"),
                    rs.getString("contatofunc"),
                });
            }
        } 
        catch (SQLException e) {
            System.out.println("Erro ao buscar funcionarios no banco:\n" 
                    + e.getMessage());
        } 
        finally {
            funcionarioCtr.closeDb();
        }
    }
    
    private void preencheCampos(String cpf) {
        FuncionarioCTR funcionarioCtr = new FuncionarioCTR();
        FuncionarioDTO f = new FuncionarioDTO();
        
        try {
            f.setCpf(cpf);
            
            ResultSet rs = funcionarioCtr.buscaFuncionario(f, "preencher");
            
            if(rs.next()) {
                textNome.setText(rs.getString("nomefunc"));
                textCpf.setText(rs.getString("cpf"));
                textCidade.setText(rs.getString("cidade"));
                textNumero.setText(rs.getString("num"));
                textRua.setText(rs.getString("rua"));
                textCep.setText(rs.getString("cep"));
                textRamal.setText(rs.getString("ramal"));
                comboTurno.setSelectedItem(rs.getString("turno"));
                comboDepartamento.setSelectedItem(rs.getString(
                        "departamento"));
                textTelefone.setText(rs.getString("contatofunc"));
                
                if(rs.getString("segundocontato") == null) 
                    manipulaContatosAoPreencher(true, false, false, 
                            "");
                else 
                    manipulaContatosAoPreencher(false, true, true, 
                            rs.getString("segundocontato"));
                
                textUsuario.setText(rs.getString("usuario"));
                labelSenha.setText(descriptografarAes(rs.getString(
                        "senha")));
            }
        } 
        catch (SQLException e) {
            System.out.println("Falha ao preencher os campos do formulario:\n"
                    + e.getMessage());
        }
        finally {
            funcionarioCtr.closeDb();
        }
    }
    
    private void alteraFuncionario() {
        FuncionarioCTR funcionarioCtr = new FuncionarioCTR();
        FuncionarioDTO f = new FuncionarioDTO();
        
        try {
            if(Inicio.usuarioLogin.equals(textUsuario.getText())) {
                JOptionPane.showMessageDialog(null, "Não é "
                        + "possível alterar usuário com sessão aberta no sistema!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
                limpaCampos();
            } else {
                f.setNomeFunc(textNome.getText());
                f.setCpf(textCpf.getText());
                f.setCidade(textCidade.getText());
                f.setNum(Integer.parseInt(textNumero.getText()));
                f.setRua(textRua.getText());
                f.setCep(textCep.getText());
                f.setRamal(Integer.parseInt(textRamal.getText()));
                f.setTurno(comboTurno.getSelectedItem().toString());
                f.setDepartamento(comboDepartamento.getSelectedItem()
                        .toString());
                f.setTelefone(textTelefone.getText());

                if(textSegundoContato.isEnabled())
                    f.setSegundoContato(textSegundoContato.getText());
                else
                    f.setSegundoContato(null);

                f.setUsuario(textUsuario.getText());
                f.setSenha(criptografarAes(labelSenha.getText()));
                
                JOptionPane.showMessageDialog(null,
                        funcionarioCtr.alteraFuncionario(f),
                        "Mensagem!",
                        JOptionPane.INFORMATION_MESSAGE);
                limpaCampos();
                buscaFuncionario();
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
            funcionarioCtr.closeDb();
        }
    }
    
    private String extraiNome(String str) {
        String[] nomeConvertido = str.split(" ");
        
        return nomeConvertido[0];
    }
    
    private void alteraUsuario() {
        if(comboDepartamento.getSelectedItem().equals("Recepção")) {
            textUsuario.setText(extraiNome(textNome.getText().
                    toLowerCase()) + ".recepcao");
        } else {
            textUsuario.setText(extraiNome(textNome.getText().
                    toLowerCase()) + "." 
                    + comboDepartamento.getSelectedItem().toString().
                            toLowerCase());
        }
    }
    
    private void excluiFuncionario() {
        FuncionarioCTR funcionarioCtr = new FuncionarioCTR();
        FuncionarioDTO f = new FuncionarioDTO();
        
        try {
            if(textCpf.getText().isEmpty())
                JOptionPane.showMessageDialog(null, 
                    "Insira um CPF válido para excluir o registro!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
            else {
                f.setCpf(textCpf.getText());
                JOptionPane.showMessageDialog(null, 
                    funcionarioCtr.excluiCliente(f), "Mensagem!", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            buscaFuncionario();
            limpaCampos();
        } catch (NumberFormatException ef) {
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
            funcionarioCtr.closeDb();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoBusca = new javax.swing.ButtonGroup();
        panelPesquisaAvancada = new javax.swing.JPanel();
        labelPesqUsuario = new javax.swing.JLabel();
        textPesqUsuario = new javax.swing.JTextField();
        labelBuscarPor = new javax.swing.JLabel();
        radioNome = new javax.swing.JRadioButton();
        radioCpf = new javax.swing.JRadioButton();
        radioDepartamento = new javax.swing.JRadioButton();
        radioDesativado = new javax.swing.JRadioButton();
        btnPesquisar = new javax.swing.JButton();
        btnLimpaFiltros = new javax.swing.JButton();
        btnLimpaTabela = new javax.swing.JButton();
        panelTabela = new javax.swing.JScrollPane();
        tabelaFuncionarios = new javax.swing.JTable();
        panelGerenciarCadastro = new javax.swing.JPanel();
        panelInfoPessoal = new javax.swing.JPanel();
        labelNome = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        labelCpf = new javax.swing.JLabel();
        textCpf = new javax.swing.JTextField();
        labelCidade = new javax.swing.JLabel();
        textCidade = new javax.swing.JTextField();
        labelNumero = new javax.swing.JLabel();
        textNumero = new javax.swing.JTextField();
        labelRua = new javax.swing.JLabel();
        textRua = new javax.swing.JTextField();
        labelCep = new javax.swing.JLabel();
        textCep = new javax.swing.JTextField();
        panelInfoAtribuicao = new javax.swing.JPanel();
        labelRamal = new javax.swing.JLabel();
        textRamal = new javax.swing.JTextField();
        labelDepartamento = new javax.swing.JLabel();
        comboDepartamento = new javax.swing.JComboBox<>();
        labelTurno = new javax.swing.JLabel();
        comboTurno = new javax.swing.JComboBox<>();
        panelInfoContato = new javax.swing.JPanel();
        labelTelefone = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        btnAddContato = new javax.swing.JButton();
        btnRemContato = new javax.swing.JButton();
        labelSegundoContato = new javax.swing.JLabel();
        textSegundoContato = new javax.swing.JTextField();
        panelInfoLogin = new javax.swing.JPanel();
        labelInfoUsuario = new javax.swing.JLabel();
        labelInfoSenha = new javax.swing.JLabel();
        labelSenha = new javax.swing.JLabel();
        textUsuario = new javax.swing.JTextField();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPesquisaAvancada.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa avançada"));
        panelPesquisaAvancada.setPreferredSize(new java.awt.Dimension(459, 135));

        labelPesqUsuario.setText("Usuario");

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

        grupoBusca.add(radioDepartamento);
        radioDepartamento.setText("Departamento");
        radioDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioDepartamentoActionPerformed(evt);
            }
        });

        grupoBusca.add(radioDesativado);
        radioDesativado.setText("Desativado");

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
                        .addComponent(labelPesqUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(textPesqUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(btnPesquisar))
                    .addGroup(panelPesquisaAvancadaLayout.createSequentialGroup()
                        .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(radioDepartamento)
                                .addGap(18, 18, 18)
                                .addComponent(radioDesativado)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelPesquisaAvancadaLayout.setVerticalGroup(
            panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPesquisaAvancadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPesqUsuario)
                    .addComponent(textPesqUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBuscarPor)
                    .addComponent(radioNome)
                    .addComponent(radioCpf)
                    .addComponent(radioDepartamento)
                    .addComponent(radioDesativado))
                .addGap(18, 18, 18)
                .addGroup(panelPesquisaAvancadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpaFiltros)
                    .addComponent(btnLimpaTabela))
                .addContainerGap())
        );

        tabelaFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "CPF", "Departamento", "Contato"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaFuncionarios.getTableHeader().setReorderingAllowed(false);
        tabelaFuncionarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaFuncionariosMouseClicked(evt);
            }
        });
        panelTabela.setViewportView(tabelaFuncionarios);

        panelGerenciarCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Gerenciar cadastro de funcionários"));

        panelInfoPessoal.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Pessoais"));

        labelNome.setText("Nome");

        labelCpf.setText("CPF");

        labelCidade.setText("Cidade");

        labelNumero.setText("Número");

        labelRua.setText("Rua");

        labelCep.setText("CEP");

        javax.swing.GroupLayout panelInfoPessoalLayout = new javax.swing.GroupLayout(panelInfoPessoal);
        panelInfoPessoal.setLayout(panelInfoPessoalLayout);
        panelInfoPessoalLayout.setHorizontalGroup(
            panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                        .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNome)
                            .addComponent(labelCpf)
                            .addComponent(labelCidade))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                                .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelRua)
                                .addGap(18, 18, 18)
                                .addComponent(textRua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textNome)
                            .addComponent(textCpf)))
                    .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                        .addComponent(labelNumero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelCep)
                        .addGap(18, 18, 18)
                        .addComponent(textCep, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelInfoPessoalLayout.setVerticalGroup(
            panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPessoalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCpf)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCidade)
                    .addComponent(textRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRua))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumero)
                    .addComponent(textNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCep))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoAtribuicao.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Atribuição"));

        labelRamal.setText("Ramal");

        labelDepartamento.setText("Departamento");

        comboDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Financeiro", "Recepção", "Gerente" }));
        comboDepartamento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboDepartamentoItemStateChanged(evt);
            }
        });

        labelTurno.setText("Turno");

        comboTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Manhã", "Tarde", "Integral" }));

        javax.swing.GroupLayout panelInfoAtribuicaoLayout = new javax.swing.GroupLayout(panelInfoAtribuicao);
        panelInfoAtribuicao.setLayout(panelInfoAtribuicaoLayout);
        panelInfoAtribuicaoLayout.setHorizontalGroup(
            panelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoAtribuicaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelRamal)
                    .addComponent(labelTurno))
                .addGap(24, 24, 24)
                .addGroup(panelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textRamal)
                    .addComponent(comboTurno, 0, 100, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelDepartamento)
                .addGap(18, 18, 18)
                .addComponent(comboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelInfoAtribuicaoLayout.setVerticalGroup(
            panelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoAtribuicaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRamal)
                    .addComponent(textRamal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDepartamento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTurno)
                    .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoContato.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Contato"));

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

        labelSegundoContato.setText("Segundo contato");

        javax.swing.GroupLayout panelInfoContatoLayout = new javax.swing.GroupLayout(panelInfoContato);
        panelInfoContato.setLayout(panelInfoContatoLayout);
        panelInfoContatoLayout.setHorizontalGroup(
            panelInfoContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoContatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInfoContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelInfoContatoLayout.createSequentialGroup()
                            .addComponent(labelTelefone)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoContatoLayout.createSequentialGroup()
                            .addComponent(btnAddContato)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnRemContato)))
                    .addGroup(panelInfoContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelSegundoContato)
                        .addComponent(textSegundoContato, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelInfoContatoLayout.setVerticalGroup(
            panelInfoContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoContatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefone)
                    .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddContato)
                    .addComponent(btnRemContato)
                    .addComponent(labelSegundoContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textSegundoContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Login"));

        labelInfoUsuario.setText("Usuário");

        labelInfoSenha.setText("Senha");

        labelSenha.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        textUsuario.setEnabled(false);

        javax.swing.GroupLayout panelInfoLoginLayout = new javax.swing.GroupLayout(panelInfoLogin);
        panelInfoLogin.setLayout(panelInfoLoginLayout);
        panelInfoLoginLayout.setHorizontalGroup(
            panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelInfoUsuario)
                    .addComponent(labelInfoSenha))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLoginLayout.createSequentialGroup()
                        .addComponent(labelSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(textUsuario))
                .addContainerGap())
        );
        panelInfoLoginLayout.setVerticalGroup(
            panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelInfoUsuario)
                    .addComponent(textUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLoginLayout.createSequentialGroup()
                        .addComponent(labelInfoSenha)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                    .addComponent(panelInfoPessoal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoAtribuicao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelGerenciarCadastroLayout.createSequentialGroup()
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelInfoLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelGerenciarCadastroLayout.setVerticalGroup(
            panelGerenciarCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGerenciarCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInfoPessoal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoAtribuicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(panelPesquisaAvancada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
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
                        .addComponent(panelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE))
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

    private void btnAddContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContatoActionPerformed
        addSegundoContato();
    }//GEN-LAST:event_btnAddContatoActionPerformed

    private void btnRemContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemContatoActionPerformed
        remSegundoContato();
    }//GEN-LAST:event_btnRemContatoActionPerformed

    private void radioNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioNomeActionPerformed
        buscaFuncionario();
    }//GEN-LAST:event_radioNomeActionPerformed

    private void radioCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioCpfActionPerformed
        buscaFuncionario();
    }//GEN-LAST:event_radioCpfActionPerformed

    private void radioDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioDepartamentoActionPerformed
        buscaFuncionario();
    }//GEN-LAST:event_radioDepartamentoActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        buscaFuncionario();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void tabelaFuncionariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaFuncionariosMouseClicked
        preencheCampos(String.valueOf(
                tabelaFuncionarios.getValueAt(
                tabelaFuncionarios.getSelectedRow(), 1)));
    }//GEN-LAST:event_tabelaFuncionariosMouseClicked

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpaCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alteraFuncionario();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        excluiFuncionario();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void comboDepartamentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboDepartamentoItemStateChanged
        alteraUsuario();
    }//GEN-LAST:event_comboDepartamentoItemStateChanged

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
            java.util.logging.Logger.getLogger(PesqsFuncionarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new PesqsFuncionarios().setVisible(true);
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
    private javax.swing.JComboBox<String> comboDepartamento;
    private javax.swing.JComboBox<String> comboTurno;
    private javax.swing.ButtonGroup grupoBusca;
    private javax.swing.JLabel labelBuscarPor;
    private javax.swing.JLabel labelCep;
    private javax.swing.JLabel labelCidade;
    private javax.swing.JLabel labelCpf;
    private javax.swing.JLabel labelDepartamento;
    private javax.swing.JLabel labelInfoSenha;
    private javax.swing.JLabel labelInfoUsuario;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelNumero;
    private javax.swing.JLabel labelPesqUsuario;
    private javax.swing.JLabel labelRamal;
    private javax.swing.JLabel labelRua;
    private javax.swing.JLabel labelSegundoContato;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelTelefone;
    private javax.swing.JLabel labelTurno;
    private javax.swing.JPanel panelGerenciarCadastro;
    private javax.swing.JPanel panelInfoAtribuicao;
    private javax.swing.JPanel panelInfoContato;
    private javax.swing.JPanel panelInfoLogin;
    private javax.swing.JPanel panelInfoPessoal;
    private javax.swing.JPanel panelPesquisaAvancada;
    private javax.swing.JScrollPane panelTabela;
    private javax.swing.JRadioButton radioCpf;
    private javax.swing.JRadioButton radioDepartamento;
    private javax.swing.JRadioButton radioDesativado;
    private javax.swing.JRadioButton radioNome;
    private javax.swing.JTable tabelaFuncionarios;
    private javax.swing.JTextField textCep;
    private javax.swing.JTextField textCidade;
    private javax.swing.JTextField textCpf;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textNumero;
    private javax.swing.JTextField textPesqUsuario;
    private javax.swing.JTextField textRamal;
    private javax.swing.JTextField textRua;
    private javax.swing.JTextField textSegundoContato;
    private javax.swing.JTextField textTelefone;
    private javax.swing.JTextField textUsuario;
    // End of variables declaration//GEN-END:variables
}