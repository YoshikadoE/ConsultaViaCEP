package controller;

import dao.EnderecoDAO;
import dao.EnderecoDAOImpl;
import model.Endereco;
import service.ViaCEPService;

import java.util.List;

public class EnderecoController {
    private EnderecoDAO enderecoDAO;
    private ViaCEPService viaCEPService;
    
    public EnderecoController() {
        this.enderecoDAO = new EnderecoDAOImpl();
        this.viaCEPService = new ViaCEPService();
    }
    
    // Método para buscar endereço na API ViaCEP
    public Endereco buscarEnderecoPorCep(String cep) throws Exception {
        return viaCEPService.buscarEnderecoPorCep(cep);
    }
    
    // Métodos CRUD delegados para o DAO
    public void salvarEndereco(Endereco endereco) {
        enderecoDAO.salvar(endereco);
    }
    
    public Endereco buscarEnderecoPorId(int id) {
        return enderecoDAO.buscarPorId(id);
    }
    
    public List<Endereco> listarTodosEnderecos() {
        return enderecoDAO.listarTodos();
    }
    
    public void atualizarEndereco(Endereco endereco) {
        enderecoDAO.atualizar(endereco);
    }
    
    public void excluirEndereco(int id) {
        enderecoDAO.excluir(id);
    }
}