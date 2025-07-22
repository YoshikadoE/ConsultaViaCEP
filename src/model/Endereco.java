package model;

import java.io.Serializable;

public class Endereco implements Serializable {
    // Atributos privados (encapsulamento)
    private int id; 
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;
    
    
    // Construtor padrão
    public Endereco() {
    }
    
    // Construtor com parâmetros
    public Endereco(String cep, String logradouro, String numero, String complemento, String bairro,
                   String localidade, String uf, String ibge, String gia, String ddd, String siafi) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
        this.ibge = ibge;
        this.gia = gia;
        this.ddd = ddd;
        this.siafi = siafi;
    }
    
    // Getters e Setters (métodos de acesso)
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public String getLogradouro() {
        return logradouro;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getComplemento() {
        return complemento;
    }
    
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getLocalidade() {
        return localidade;
    }
    
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
    
    public String getUf() {
        return uf;
    }
    
    public void setUf(String uf) {
        this.uf = uf;
    }
    
    public String getIbge() {
        return ibge;
    }
    
    public void setIbge(String ibge) {
        this.ibge = ibge;
    }
    
    public String getGia() {
        return gia;
    }
    
    public void setGia(String gia) {
        this.gia = gia;
    }
    
    public String getDdd() {
        return ddd;
    }
    
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
    
    public String getSiafi() {
        return siafi;
    }
    
    public void setSiafi(String siafi) {
        this.siafi = siafi;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    // Sobrescrita do método toString para facilitar a exibição
    @Override
    public String toString() {
        return logradouro + ", " + numero + " - " + bairro + " - " + localidade + "/" + uf + " - CEP: " + cep;
    }
}
