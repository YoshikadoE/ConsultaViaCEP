package teste;

import model.Endereco;
import service.ViaCEPService;

public class TesteViaCEP {
    public static void main(String[] args) {
        try {
            // Cria uma instância do serviço
            ViaCEPService service = new ViaCEPService();
            
            // CEPs para teste
            String[] ceps = {"01001000", "70150900", "20050000"};
            
            for (String cep : ceps) {
                System.out.println("\n--- Testando CEP: " + cep + " ---");
                try {
                    Endereco endereco = service.buscarEnderecoPorCep(cep);
                    System.out.println("CEP: " + endereco.getCep());
                    System.out.println("Logradouro: " + endereco.getLogradouro());
                    System.out.println("Bairro: " + endereco.getBairro());
                    System.out.println("Cidade: " + endereco.getLocalidade());
                    System.out.println("UF: " + endereco.getUf());
                    System.out.println("Teste bem-sucedido!");
                } catch (Exception e) {
                    System.err.println("Falha no teste: " + e.getMessage());
                }
            }
            
            // Teste com CEP inválido
            System.out.println("\n--- Testando CEP inválido ---");
            try {
                service.buscarEnderecoPorCep("00000000");
                System.err.println("Falha: deveria ter lançado exceção para CEP inexistente");
            } catch (Exception e) {
                System.out.println("Teste bem-sucedido! Exceção capturada: " + e.getMessage());
            }
            
            // Teste com formato inválido
            System.out.println("\n--- Testando formato inválido ---");
            try {
                service.buscarEnderecoPorCep("123");
                System.err.println("Falha: deveria ter lançado exceção para formato inválido");
            } catch (IllegalArgumentException e) {
                System.out.println("Teste bem-sucedido! Exceção capturada: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Erro geral: " + e.getMessage());
            e.printStackTrace();
        }
    }
}