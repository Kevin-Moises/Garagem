package br.com.projetolp.gui.cads;

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
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class CadsFuncionarios extends javax.swing.JFrame {

    final String TITULO = "Cadastro de Funcionários";
    
    public CadsFuncionarios() {
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
        textCpf.putClientProperty("JComponent.roundRect", true);
        textCidade.putClientProperty("JComponent.roundRect", true);
        textNumero.putClientProperty("JComponent.roundRect", true);
        textRua.putClientProperty("JComponent.roundRect", true);
        textCep.putClientProperty("JComponent.roundRect", true);
        textTelefone.putClientProperty("JComponent.roundRect", true);
        textSegundoContato.putClientProperty("JComponent.roundRect", 
                true);
        textRamal.putClientProperty("JComponent.roundRect", true);
        comboDepartamento.putClientProperty("JComponent.roundRect", 
                true);
        comboTurno.putClientProperty("JComponent.roundRect", true);
        textUsuario.putClientProperty("JComponent.roundRect", true);
        pswdSenha.putClientProperty("JComponent.roundRect", true);
        btnAddContato.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnRemContato.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnCadastrar.putClientProperty("JButton.buttonType", 
                "roundRect");
        btnLimpar.putClientProperty("JButton.buttonType", "roundRect");
        btnCancelar.putClientProperty("JButton.buttonType", 
                "roundRect");
        pswdSenha.setEchoChar('*');
        textUsuario.setEnabled(false);
        pswdSenha.setEnabled(false);
        manipulaContatos(true, false, false);
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
        textCpf.setText("");
        textCidade.setText("");
        textNumero.setText("");
        textRua.setText("");
        textCep.setText("");
        textTelefone.setText("");
        textRamal.setText("");
        comboDepartamento.setSelectedItem(" ");
        comboTurno.setSelectedItem(" ");
        textUsuario.setText("");
        pswdSenha.setText("");
        boxMostraSenha.setSelected(false);
        
        if(textSegundoContato.isEnabled()) {
            textSegundoContato.setText("");
            manipulaContatos(true, false, false);
        }
    }
    
    private void mostraSenha() {
        if(boxMostraSenha.isSelected())
            pswdSenha.setEchoChar('\u0000');
        else
            pswdSenha.setEchoChar('*');
    }
    
    private void geraUsuario() {
        if(textNome.getText().isEmpty() || textCidade.getText().isEmpty() ||
            textCpf.getText().isEmpty() || textNumero.getText().isEmpty() || 
            textRua.getText().isEmpty() || textCep.getText().isEmpty() || 
            textTelefone.getText().isEmpty() || textRamal.getText().isEmpty() ||
            comboDepartamento.getSelectedItem().equals(" ") 
                || comboTurno.getSelectedItem().equals(" ")) 
            JOptionPane.showMessageDialog(null, "Para criar "
                    + "o usuário do funcionário corretamente por favor,\n "
                    + "preencha todos os campos do formulário!", "Atenção!", 
                    JOptionPane.WARNING_MESSAGE);
        else {
            if(comboDepartamento.getSelectedItem().equals("Recepção")) {
                textUsuario.setText(extraiNome(textNome.getText().
                        toLowerCase()) + ".recepcao");
                pswdSenha.setText(extraiNome(textNome.getText().
                        toLowerCase()));
            } else {
                textUsuario.setText(extraiNome(textNome.getText().
                        toLowerCase()) + "." 
                        + comboDepartamento.getSelectedItem().toString().
                                toLowerCase());
                pswdSenha.setText(extraiNome(textNome.getText().
                        toLowerCase()));
            }
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
    
    private String extraiNome(String str) {
        String[] nomeConvertido = str.split(" ");
        
        return nomeConvertido[0];
    }
    
    private void cadastraFuncionario() {
        FuncionarioDTO f = new FuncionarioDTO();
        FuncionarioCTR funcionarioCtr = new FuncionarioCTR();
        
        try {
            f.setNomeFunc(textNome.getText());
            f.setCpf(textCpf.getText());
            f.setCidade(textCidade.getText());
            f.setNum(Integer.parseInt(textNumero.getText()));
            f.setRua(textRua.getText());
            f.setCep(textCep.getText());
            f.setTelefone(textTelefone.getText());
            
            if(textSegundoContato.isEnabled())
                f.setSegundoContato(textSegundoContato.getText());
            else
                f.setSegundoContato(null);
            
            f.setRamal(Integer.parseInt(textRamal.getText()));
            f.setTurno(comboTurno.getSelectedItem().toString());
            f.setDepartamento(comboDepartamento.getSelectedItem()
                    .toString());
            f.setUsuario(textUsuario.getText());
            f.setSenha(criptografarAes(new String(pswdSenha.
                    getPassword())));
            
            if(textNome.getText().isEmpty() || textCidade.getText().isEmpty() ||
                    textCpf.getText().isEmpty() || 
                    textNumero.getText().isEmpty() || 
                    textRua.getText().isEmpty() || 
                    textCep.getText().isEmpty() || 
                    textTelefone.getText().isEmpty() || 
                    textRamal.getText().isEmpty() || 
                    comboDepartamento.getSelectedItem().equals(" ") 
                    || comboTurno.getSelectedItem().equals(" "))
                JOptionPane.showMessageDialog(null, "Por "
                        + "favor, preencha todos os campos\ndo formulário!", 
                        "Atenção!", JOptionPane.WARNING_MESSAGE);
            else {
                JOptionPane.showMessageDialog(null, 
                        funcionarioCtr.cadastraFuncionario(f), 
                        "Mensagem!", 
                        JOptionPane.INFORMATION_MESSAGE);
                limpaCampos();
            } 
        } 
        catch (NumberFormatException ef) {
            JOptionPane.showMessageDialog(null, "Os campos número "
                    + "e ramal precisam ser preenchido com números!", 
                    "Atenção!", JOptionPane.WARNING_MESSAGE);
            textRamal.setText("");
            textNumero.setText("");
            textNumero.requestFocus();
        }
        catch (HeadlessException e) {
            System.out.println("Erro ao cadastrar funcionario:\n" 
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

        panelSuperior = new javax.swing.JPanel();
        labelFuncionarioConectado = new javax.swing.JLabel();
        labelFuncionario = new javax.swing.JLabel();
        labelSetorFuncionario = new javax.swing.JLabel();
        labelSetor = new javax.swing.JLabel();
        panelInfoPessoais = new javax.swing.JPanel();
        labelNome = new javax.swing.JLabel();
        textNome = new javax.swing.JTextField();
        labelCpf = new javax.swing.JLabel();
        textCpf = new javax.swing.JTextField();
        labelCidade = new javax.swing.JLabel();
        textCidade = new javax.swing.JTextField();
        labelRua = new javax.swing.JLabel();
        textRua = new javax.swing.JTextField();
        labelNumero = new javax.swing.JLabel();
        textNumero = new javax.swing.JTextField();
        labelCep = new javax.swing.JLabel();
        textCep = new javax.swing.JTextField();
        labelTelefone = new javax.swing.JLabel();
        textTelefone = new javax.swing.JTextField();
        btnAddContato = new javax.swing.JButton();
        btnRemContato = new javax.swing.JButton();
        labelSegundoContato = new javax.swing.JLabel();
        textSegundoContato = new javax.swing.JTextField();
        labelInfoAtribuicao = new javax.swing.JPanel();
        labelRamal = new javax.swing.JLabel();
        textRamal = new javax.swing.JTextField();
        labelDepartamento = new javax.swing.JLabel();
        comboDepartamento = new javax.swing.JComboBox<>();
        labelTurno = new javax.swing.JLabel();
        comboTurno = new javax.swing.JComboBox<>();
        panelInfoLogin = new javax.swing.JPanel();
        labelUsuario = new javax.swing.JLabel();
        textUsuario = new javax.swing.JTextField();
        labelSenha = new javax.swing.JLabel();
        pswdSenha = new javax.swing.JPasswordField();
        boxMostraSenha = new javax.swing.JCheckBox();
        btnGerarUsuario = new javax.swing.JButton();
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

        panelInfoPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Pessoais"));

        labelNome.setText("Nome");

        labelCpf.setText("CPF");

        labelCidade.setText("Cidade");

        labelRua.setText("Rua");

        labelNumero.setText("Número");

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
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfoPessoaisLayout.createSequentialGroup()
                                .addComponent(labelCidade)
                                .addGap(14, 14, 14))
                            .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                                .addComponent(labelNumero)
                                .addGap(7, 7, 7)))
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(textNumero)
                            .addComponent(textCidade, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelRua, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelCep, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textRua)
                            .addComponent(textCep, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                    .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNome)
                            .addComponent(labelCpf))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textNome)
                            .addComponent(textCpf)))
                    .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                        .addComponent(labelTelefone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                                .addComponent(labelSegundoContato)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAddContato)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemContato))
                            .addComponent(textTelefone)
                            .addComponent(textSegundoContato))))
                .addContainerGap())
        );
        panelInfoPessoaisLayout.setVerticalGroup(
            panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(textNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCpf)
                    .addComponent(textCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCidade)
                    .addComponent(textCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelRua))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumero)
                    .addComponent(textNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCep)
                    .addComponent(textCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTelefone)
                    .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelInfoPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAddContato)
                        .addComponent(btnRemContato))
                    .addComponent(labelSegundoContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textSegundoContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        labelInfoAtribuicao.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Atribuição"));

        labelRamal.setText("Ramal");

        labelDepartamento.setText("Departamento");

        comboDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Financeiro", "Recepção", "Gerente" }));

        labelTurno.setText("Turno");

        comboTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Manhã", "Tarde", "Integral" }));

        javax.swing.GroupLayout labelInfoAtribuicaoLayout = new javax.swing.GroupLayout(labelInfoAtribuicao);
        labelInfoAtribuicao.setLayout(labelInfoAtribuicaoLayout);
        labelInfoAtribuicaoLayout.setHorizontalGroup(
            labelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(labelInfoAtribuicaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(labelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, labelInfoAtribuicaoLayout.createSequentialGroup()
                        .addComponent(labelRamal)
                        .addGap(18, 18, 18))
                    .addGroup(labelInfoAtribuicaoLayout.createSequentialGroup()
                        .addComponent(labelTurno)
                        .addGap(20, 20, 20)))
                .addGroup(labelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(comboTurno, 0, 100, Short.MAX_VALUE)
                    .addComponent(textRamal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(labelDepartamento)
                .addGap(18, 18, 18)
                .addComponent(comboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        labelInfoAtribuicaoLayout.setVerticalGroup(
            labelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, labelInfoAtribuicaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(labelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelRamal)
                    .addComponent(textRamal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDepartamento)
                    .addComponent(comboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(labelInfoAtribuicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTurno)
                    .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInfoLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Login"));

        labelUsuario.setText("Usuário");

        labelSenha.setText("Senha");

        boxMostraSenha.setText("Mostar senha");
        boxMostraSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxMostraSenhaActionPerformed(evt);
            }
        });

        btnGerarUsuario.setText("Gerar usuário");
        btnGerarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInfoLoginLayout = new javax.swing.GroupLayout(panelInfoLogin);
        panelInfoLogin.setLayout(panelInfoLoginLayout);
        panelInfoLoginLayout.setHorizontalGroup(
            panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfoLoginLayout.createSequentialGroup()
                        .addComponent(labelUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textUsuario))
                    .addGroup(panelInfoLoginLayout.createSequentialGroup()
                        .addComponent(labelSenha)
                        .addGap(20, 20, 20)
                        .addComponent(pswdSenha)))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boxMostraSenha)
                    .addComponent(btnGerarUsuario))
                .addContainerGap())
        );
        panelInfoLoginLayout.setVerticalGroup(
            panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfoLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsuario)
                    .addComponent(textUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerarUsuario))
                .addGap(18, 18, 18)
                .addGroup(panelInfoLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSenha)
                    .addComponent(pswdSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxMostraSenha))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(panelInfoPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelInfoAtribuicao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelInfoLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
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
                .addComponent(panelInfoPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelInfoAtribuicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInfoLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrar)
                    .addComponent(btnLimpar)
                    .addComponent(btnCancelar))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        setBounds(0, 0, 416, 648);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cancelaCadastro();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpaCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void boxMostraSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxMostraSenhaActionPerformed
        mostraSenha();
    }//GEN-LAST:event_boxMostraSenhaActionPerformed

    private void btnGerarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarUsuarioActionPerformed
        geraUsuario();
    }//GEN-LAST:event_btnGerarUsuarioActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        cadastraFuncionario();
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
            java.util.logging.Logger.getLogger(CadsFuncionarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new CadsFuncionarios().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox boxMostraSenha;
    private javax.swing.JButton btnAddContato;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGerarUsuario;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnRemContato;
    private javax.swing.JComboBox<String> comboDepartamento;
    private javax.swing.JComboBox<String> comboTurno;
    private javax.swing.JLabel labelCep;
    private javax.swing.JLabel labelCidade;
    private javax.swing.JLabel labelCpf;
    private javax.swing.JLabel labelDepartamento;
    private javax.swing.JLabel labelFuncionario;
    private javax.swing.JLabel labelFuncionarioConectado;
    private javax.swing.JPanel labelInfoAtribuicao;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelNumero;
    private javax.swing.JLabel labelRamal;
    private javax.swing.JLabel labelRua;
    private javax.swing.JLabel labelSegundoContato;
    private javax.swing.JLabel labelSenha;
    private javax.swing.JLabel labelSetor;
    private javax.swing.JLabel labelSetorFuncionario;
    private javax.swing.JLabel labelTelefone;
    private javax.swing.JLabel labelTurno;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel panelInfoLogin;
    private javax.swing.JPanel panelInfoPessoais;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JPasswordField pswdSenha;
    private javax.swing.JTextField textCep;
    private javax.swing.JTextField textCidade;
    private javax.swing.JTextField textCpf;
    private javax.swing.JTextField textNome;
    private javax.swing.JTextField textNumero;
    private javax.swing.JTextField textRamal;
    private javax.swing.JTextField textRua;
    private javax.swing.JTextField textSegundoContato;
    private javax.swing.JTextField textTelefone;
    private javax.swing.JTextField textUsuario;
    // End of variables declaration//GEN-END:variables
}