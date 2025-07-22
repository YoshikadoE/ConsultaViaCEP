package view;

import controller.EnderecoController;
import model.Endereco;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class EnderecoView extends JFrame {
    private EnderecoController controller;
    
    // Componentes da interface
    private JTextField txtCep;
    private JTextField txtLogradouro;
    private JTextField txtNumero;
    private JTextField txtComplemento;
    private JTextField txtBairro;
    private JTextField txtLocalidade;
    private JTextField txtUf;
    private JList<Endereco> listaEnderecos;
    private DefaultListModel<Endereco> modeloLista;
    
    // Botões
    private JButton btnBuscar;
    private JButton btnSalvar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    
    // Endereço selecionado atualmente
    private Endereco enderecoSelecionado;
    
    public EnderecoView() {
        controller = new EnderecoController();
        inicializarComponentes();
        configurarEventos();
        carregarEnderecos();
    }
    
    private void inicializarComponentes() {
        // Configuração básica da janela
        setTitle("Gerenciador de Endereços - ViaCEP");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel principal com BorderLayout
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de formulário (campos de texto)
        JPanel painelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Componentes do formulário
        JLabel lblCep = new JLabel("CEP:");
        txtCep = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        
        JLabel lblLogradouro = new JLabel("Logradouro:");
        txtLogradouro = new JTextField(30);
        
        JLabel lblNumero = new JLabel("Número:");
        txtNumero = new JTextField(10);
        
        JLabel lblComplemento = new JLabel("Complemento:");
        txtComplemento = new JTextField(30);
        
        JLabel lblBairro = new JLabel("Bairro:");
        txtBairro = new JTextField(20);
        
        JLabel lblLocalidade = new JLabel("Cidade:");
        txtLocalidade = new JTextField(20);
        
        JLabel lblUf = new JLabel("UF:");
        txtUf = new JTextField(5);
        
        // Adiciona componentes ao painel de formulário
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelFormulario.add(new JLabel("CEP:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        txtCep = new JTextField(10);
        painelFormulario.add(txtCep, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        btnBuscar = new JButton("Buscar");
        painelFormulario.add(btnBuscar, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelFormulario.add(new JLabel("Logradouro:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        txtLogradouro = new JTextField(30);
        painelFormulario.add(txtLogradouro, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        painelFormulario.add(new JLabel("Número:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.3;
        txtNumero = new JTextField(10);
        painelFormulario.add(txtNumero, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("Complemento:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.7;
        txtComplemento = new JTextField(20);
        painelFormulario.add(txtComplemento, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("Bairro:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        txtBairro = new JTextField(30);
        painelFormulario.add(txtBairro, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        painelFormulario.add(new JLabel("Cidade:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtLocalidade = new JTextField(20);
        painelFormulario.add(txtLocalidade, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("UF:"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.3;
        txtUf = new JTextField(5);
        painelFormulario.add(txtUf, gbc);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnSalvar = new JButton("Salvar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        
        // Painel da lista de endereços
        JPanel painelLista = new JPanel(new BorderLayout());
        painelLista.setBorder(BorderFactory.createTitledBorder("Endereços Salvos"));
        modeloLista = new DefaultListModel<>();
        listaEnderecos = new JList<>(modeloLista);
        listaEnderecos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(listaEnderecos);
        painelLista.add(scrollLista, BorderLayout.CENTER);

        // Montagem do painel principal
        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        painelPrincipal.add(painelLista, BorderLayout.SOUTH);

        setContentPane(painelPrincipal);

        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }
    
    private void configurarEventos() {
        // Evento do botão Buscar
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCep();
            }
        });
        
        // Evento do botão Salvar
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarEndereco();
            }
        });
        
        // Evento do botão Atualizar
        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarEndereco();
            }
        });
        
        // Evento do botão Excluir
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirEndereco();
            }
        });
        
        // Evento do botão Limpar
        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        
        // Evento de seleção na lista
        listaEnderecos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selecionarEndereco();
                }
            }
        });
    }
    
    private void carregarEnderecos() {
        // Limpa a lista
        modeloLista.clear();
        
        // Carrega todos os endereços do DAO
        List<Endereco> enderecos = controller.listarTodosEnderecos();
        
        System.out.println("Carregados " + enderecos.size() + " endereços");
        // Adiciona os endereços ao modelo da lista
        for (Endereco endereco : enderecos) {
            System.out.println("Adicionando à lista: " + endereco);
            modeloLista.addElement(endereco);
        }
    }

    private void buscarCep() {
        String cep = txtCep.getText().trim();
        
        try {
            Endereco endereco = controller.buscarEnderecoPorCep(cep);
            
            // Preenche os campos com os dados do endereço
            preencherCampos(endereco);
            
            // Configura o estado dos botões
            btnSalvar.setEnabled(true);
            btnAtualizar.setEnabled(false);
            btnExcluir.setEnabled(false);
            
            // Limpa a seleção da lista
            listaEnderecos.clearSelection();
            enderecoSelecionado = null;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao buscar CEP: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvarEndereco() {
        try {
            // Cria um novo objeto Endereco com os dados dos campos
            Endereco endereco = criarEnderecoDoFormulario();
            
            // Salva o endereço
            controller.salvarEndereco(endereco);
            
            // Adicione este log
            System.out.println("Endereço salvo, recarregando lista...");
            
            // Atualiza a lista de endereços
            carregarEnderecos();
            
            // Limpa os campos
            limparCampos();
            
            JOptionPane.showMessageDialog(this, 
                "Endereço salvo com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao salvar endereço: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void atualizarEndereco() {
        if (enderecoSelecionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um endereço para atualizar.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Cria um objeto Endereco com os dados dos campos
            Endereco endereco = criarEnderecoDoFormulario();
            
            // Mantém o ID do endereço selecionado
            endereco.setId(enderecoSelecionado.getId());
            
            // Atualiza o endereço
            controller.atualizarEndereco(endereco);
            
            // Atualiza a lista de endereços
            carregarEnderecos();
            
            // Limpa os campos e a seleção
            limparCampos();
            
            JOptionPane.showMessageDialog(this, 
                "Endereço atualizado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao atualizar endereço: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirEndereco() {
        if (enderecoSelecionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Selecione um endereço para excluir.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirma a exclusão
        int opcao = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este endereço?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcao == JOptionPane.YES_OPTION) {
            try {
                // Exclui o endereço
                controller.excluirEndereco(enderecoSelecionado.getId());
                
                // Atualiza a lista de endereços
                carregarEnderecos();
                
                // Limpa os campos e a seleção
                limparCampos();
                
                JOptionPane.showMessageDialog(this, 
                    "Endereço excluído com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao excluir endereço: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void selecionarEndereco() {
        // Obtém o endereço selecionado na lista
        enderecoSelecionado = listaEnderecos.getSelectedValue();
        
        if (enderecoSelecionado != null) {
            // Preenche os campos com os dados do endereço selecionado
            preencherCampos(enderecoSelecionado);
            
            // Configura o estado dos botões
            btnSalvar.setEnabled(false);
            btnAtualizar.setEnabled(true);
            btnExcluir.setEnabled(true);
        }
    }
    
    private void preencherCampos(Endereco endereco) {
        txtCep.setText(endereco.getCep());
        txtLogradouro.setText(endereco.getLogradouro());
        txtNumero.setText(endereco.getNumero());
        txtComplemento.setText(endereco.getComplemento());
        txtBairro.setText(endereco.getBairro());
        txtLocalidade.setText(endereco.getLocalidade());
        txtUf.setText(endereco.getUf());
    }
    
    private Endereco criarEnderecoDoFormulario() {
        Endereco endereco = new Endereco();
        endereco.setCep(txtCep.getText().trim());
        endereco.setLogradouro(txtLogradouro.getText().trim());
        endereco.setNumero(txtNumero.getText().trim());
        endereco.setComplemento(txtComplemento.getText().trim());
        endereco.setBairro(txtBairro.getText().trim());
        endereco.setLocalidade(txtLocalidade.getText().trim());
        endereco.setUf(txtUf.getText().trim());
        return endereco;
    }
    
    private void limparCampos() {
        txtCep.setText("");
        txtLogradouro.setText("");
        txtNumero.setText("");
        txtComplemento.setText("");
        txtBairro.setText("");
        txtLocalidade.setText("");
        txtUf.setText("");
        
        // Limpa a seleção da lista
        listaEnderecos.clearSelection();
        enderecoSelecionado = null;
        
        // Configura o estado dos botões
        btnSalvar.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);
        
        // Coloca o foco no campo de CEP
        txtCep.requestFocus();
    }
}