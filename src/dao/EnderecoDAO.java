package dao;

import model.Endereco;
import java.util.List;

public interface EnderecoDAO {
    // Métodos CRUD
    void salvar(Endereco endereco);
    Endereco buscarPorId(int id);
    List<Endereco> listarTodos();
    void atualizar(Endereco endereco);
    void excluir(int id);
}