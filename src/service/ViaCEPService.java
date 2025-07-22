package service;

import model.Endereco;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViaCEPService {
    private static final String API_URL = "https://viacep.com.br/ws/";
    private static final String API_FORMAT = "/json/";
    
    public Endereco buscarEnderecoPorCep(String cep ) throws Exception {
        // Remove caracteres não numéricos do CEP
        cep = cep.replaceAll("\\D", "");
        
        // Valida o formato do CEP
        if (cep.length() != 8) {
            throw new IllegalArgumentException("CEP deve conter 8 dígitos");
        }
        
        // Constrói a URL da API
        String urlStr = API_URL + cep + API_FORMAT;
        URL url = new URL(urlStr);
        
        // Abre a conexão HTTP
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        
        // Verifica o código de resposta
        int responseCode = conexao.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Erro ao consultar CEP: " + responseCode);
        }
        
        // Lê a resposta da API
        BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        String linha;
        
        while ((linha = reader.readLine()) != null) {
            resposta.append(linha);
        }
        reader.close();
        
        String jsonStr = resposta.toString();
        
        // Verifica se o CEP foi encontrado
        if (jsonStr.contains(".*\"erro\"\\s*:\\s*true.*")) {
            throw new RuntimeException("CEP não encontrado");
        }
       
        // Extrai os dados do JSON usando expressões regulares
        Endereco endereco = new Endereco();
        endereco.setCep(extrairCampo(jsonStr, "cep"));
        endereco.setLogradouro(extrairCampo(jsonStr, "logradouro"));
        endereco.setComplemento(extrairCampo(jsonStr, "complemento"));
        endereco.setBairro(extrairCampo(jsonStr, "bairro"));
        endereco.setLocalidade(extrairCampo(jsonStr, "localidade"));
        endereco.setUf(extrairCampo(jsonStr, "uf"));
        endereco.setIbge(extrairCampo(jsonStr, "ibge"));
        endereco.setGia(extrairCampo(jsonStr, "gia"));
        endereco.setDdd(extrairCampo(jsonStr, "ddd"));
        endereco.setSiafi(extrairCampo(jsonStr, "siafi"));
        
        return endereco;
    }
    
    private String extrairCampo(String json, String campo) {
        Pattern pattern = Pattern.compile("\"" + campo + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
