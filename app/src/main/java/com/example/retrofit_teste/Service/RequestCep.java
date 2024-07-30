package com.example.retrofit_teste.Service;

import com.example.retrofit_teste.DadosCep;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Esta interface irá fazer a chamada dos dados da api baseando-se no cep desejado pelo usuário.
// Os dados serão associados às propriedades da classe DadosCep
public interface RequestCep {
    @GET("ws/{cep}/json")
    Call<DadosCep> getCep(@Path("cep") String cep);
}
