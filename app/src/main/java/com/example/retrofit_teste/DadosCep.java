package com.example.retrofit_teste;

// Esta classe representa os dados em JSON resgatados da API
public class DadosCep {

    // Definindo as propriedades da classe
    String cep, logradouro, bairro, localidade, uf;

    // Definindo os getters e setters
    public String getCep() { return cep; }

    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }

    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getBairro() { return bairro; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getLocalidade() { return localidade; }

    public void setLocalidade(String localidade) { this.localidade = localidade; }

    public String getUf() { return uf; }

    public void setUf(String uf) { this.uf = uf; }
}
