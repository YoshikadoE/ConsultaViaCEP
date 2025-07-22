package dao;

import model.Endereco;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAOImpl implements EnderecoDAO {
	
	private static final String ARQUIVO_ENDERECOS = "enderecos.txt";
    
	@Override
    public void salvar(Endereco endereco) {
        List<Endereco> enderecos = listarTodos();
        
        // Gera um ID único para o novo endereço
        int proximoId = 1;
        if (!enderecos.isEmpty()) {
            int maxId = 0;
            for (Endereco e : enderecos) {
                if (e.getId() > maxId) {
                    maxId = e.getId();
                }
            }
            proximoId = maxId + 1;
        }
        
        endereco.setId(proximoId);
        enderecos.add(endereco);
        salvarArquivo(enderecos);
    }
    
    @Override
    public Endereco buscarPorId(int id) {
        List<Endereco> enderecos = listarTodos();
        for (Endereco endereco : enderecos) {
            if (endereco.getId() == id) {
                return endereco;
            }
        }
        return null;
    }
    
    @Override
    public List<Endereco> listarTodos() {
        List<Endereco> enderecos = new ArrayList<>();
        
        try {
            File arquivo = new File(ARQUIVO_ENDERECOS);
            System.out.println("Lendo de: " + arquivo.getAbsolutePath());
            
            if (!arquivo.exists()) {
                System.out.println("Arquivo não existe!");
                return enderecos;
            }
            
            System.out.println("Arquivo existe, tamanho: " + arquivo.length() + " bytes");
            
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            int numeroLinha = 0;
            
            while ((linha = reader.readLine()) != null) {
                numeroLinha++;
                System.out.println("Lendo linha " + numeroLinha + ": " + linha);
                
                // Verifica se a linha não está vazia
                if (linha.trim().isEmpty()) {
                    System.out.println("Linha " + numeroLinha + " está vazia, pulando.");
                    continue;
                }
                
                String[] dados = linha.split("\\|",-1);
                System.out.println("Linha " + numeroLinha + " dividida em " + dados.length + " partes.");
                
                if (dados.length >= 12) {
                    try {
                        Endereco endereco = new Endereco();
                        endereco.setId(Integer.parseInt(dados[0]));
                        endereco.setCep(dados[1]);
                        endereco.setLogradouro(dados[2]);
                        endereco.setNumero(dados[3]);
                        endereco.setComplemento(dados[4]);
                        endereco.setBairro(dados[5]);
                        endereco.setLocalidade(dados[6]);
                        endereco.setUf(dados[7]);
                        endereco.setIbge(dados[8]);
                        endereco.setGia(dados[9]);
                        endereco.setDdd(dados[10]);
                        endereco.setSiafi(dados[11]);
                        
                        enderecos.add(endereco);
                        System.out.println("Endereço adicionado da linha " + numeroLinha + ": " + endereco);
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao parsear ID na linha " + numeroLinha + ": " + linha + " - " + e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Erro de índice ao parsear linha " + numeroLinha + ": " + linha + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Linha " + numeroLinha + " ignorada (campos insuficientes): " + linha);
                }
            }
            
            reader.close();
            System.out.println("Leitura concluída. Total de endereços lidos: " + enderecos.size());
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) { // Captura outras exceções inesperadas
            System.err.println("Erro inesperado ao ler endereços: " + e.getMessage());
            e.printStackTrace();
        }
        
        return enderecos;
    }

    
    @Override
    public void atualizar(Endereco endereco) {
        List<Endereco> enderecos = listarTodos();
        
        for (int i = 0; i < enderecos.size(); i++) {
            if (enderecos.get(i).getId() == endereco.getId()) {
                enderecos.set(i, endereco);
                break;
            }
        }
        
        salvarArquivo(enderecos);
    }
    
    @Override
    public void excluir(int id) {
        List<Endereco> enderecos = listarTodos();
        enderecos.removeIf(e -> e.getId() == id);
        salvarArquivo(enderecos);
    }
    
    private void salvarArquivo(List<Endereco> enderecos) {
        try {
        	File arquivo = new File(ARQUIVO_ENDERECOS);
        	System.out.println("Salvando em: " + arquivo.getAbsolutePath());
        	
            // Cria o diretório pai se não existir
            File diretorioPai = arquivo.getParentFile();
            if (diretorioPai != null && !diretorioPai.exists()) {
                diretorioPai.mkdirs();
            }
            
            System.out.println("Salvando em: " + arquivo.getAbsolutePath());
            BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ENDERECOS));
            
            for (Endereco endereco : enderecos) {
                writer.write(
                    endereco.getId() + "|" +
                    valorOuVazio(endereco.getCep()) + "|" +
                    valorOuVazio(endereco.getLogradouro()) + "|" +
                    valorOuVazio(endereco.getNumero()) + "|" +
                    valorOuVazio(endereco.getComplemento()) + "|" +
                    valorOuVazio(endereco.getBairro()) + "|" +
                    valorOuVazio(endereco.getLocalidade()) + "|" +
                    valorOuVazio(endereco.getUf()) + "|" +
                    valorOuVazio(endereco.getIbge()) + "|" +
                    valorOuVazio(endereco.getGia()) + "|" +
                    valorOuVazio(endereco.getDdd()) + "|" +
                    valorOuVazio(endereco.getSiafi())
                );
                writer.newLine();
            }
            
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String valorOuVazio(String valor) {
        return valor != null ? valor : "";
    }
}
