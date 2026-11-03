package br.com.projeto.controller;

import br.com.projeto.model.Usuario;
import br.com.projeto.model.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void cadastrarUsuario(String nome, String email) throws SQLException {
        Usuario usuario = new Usuario(nome, email);
        usuarioDAO.cadastrar(usuario);
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    public void atualizarUsuario(int id, String nome, String email) throws SQLException {
        Usuario usuario = new Usuario(id, nome, email);
        usuarioDAO.atualizar(usuario);
    }

    public void deletarUsuario(int id) throws SQLException {
        usuarioDAO.deletar(id);
    }
}