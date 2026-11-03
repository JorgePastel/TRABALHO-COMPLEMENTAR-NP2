package br.com.projeto.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.projeto.controller.UsuarioController;
import br.com.projeto.model.Usuario;

public class TelaUsuario extends JFrame {
    private JTextField txtId, txtNome, txtEmail;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private final UsuarioController controller;

    public TelaUsuario() {
        controller = new UsuarioController();
        initComponents();
        carregarTabela();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Gerenciamento de Usuários - CRUD");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnDeletar = new JButton("Deletar");
        JButton btnLimpar = new JButton("Limpar");

        JPanel panelBotoes = new JPanel(new FlowLayout());
        panelBotoes.add(btnCadastrar);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnDeletar);
        panelBotoes.add(btnLimpar);

        JPanel panelNorth = new JPanel(new BorderLayout());
        panelNorth.add(panelForm, BorderLayout.CENTER);
        panelNorth.add(panelBotoes, BorderLayout.SOUTH);

        add(panelNorth, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Email"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnEditar.addActionListener(e -> editar());
        btnDeletar.addActionListener(e -> deletar());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.getSelectionModel().addListSelectionListener(e -> seleccionarLinha());
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        try {
            List<Usuario> lista = controller.listarUsuarios();
            for (Usuario u : lista) {
                tableModel.addRow(new Object[]{u.getId(), u.getNome(), u.getEmail()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private void cadastrar() {
        try {
            controller.cadastrarUsuario(txtNome.getText(), txtEmail.getText());
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    private void editar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
            return;
        }
        try {
            int id = Integer.parseInt(txtId.getText());
            controller.atualizarUsuario(id, txtNome.getText(), txtEmail.getText());
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + ex.getMessage());
        }
    }

    private void deletar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para deletar.");
            return;
        }
        try {
            int id = Integer.parseInt(txtId.getText());
            controller.deletarUsuario(id);
            JOptionPane.showMessageDialog(this, "Usuário deletado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar: " + ex.getMessage());
        }
    }

    private void seleccionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            txtId.setText(tableModel.getValueAt(linha, 0).toString());
            txtNome.setText(tableModel.getValueAt(linha, 1).toString());
            txtEmail.setText(tableModel.getValueAt(linha, 2).toString());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        tabela.clearSelection();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaUsuario();
            }
        });
    }
}
