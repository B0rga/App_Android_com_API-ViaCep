package com.example.retrofit_teste;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.retrofit_teste.Service.RequestCep;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editCep, editRua, editBairro, editNumero, editCidade, editEstado;
    private ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editCep = findViewById(R.id.editCep);
        editRua = findViewById(R.id.editRua);
        editBairro = findViewById(R.id.editBairro);
        editNumero = findViewById(R.id.editNumero);
        editCidade = findViewById(R.id.editCidade);
        editEstado = findViewById(R.id.editEstado);
        main = findViewById(R.id.main);

        // Adicionando um Listener para o input de cep
        editCep.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Assim que o usuário inserir os 8 digitos de um cep existente, o request será realizado e os outros campos serão preenchidos
                if(editCep.length()==8){
                    BuscarCep();
                    EsconderTeclado();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para retornar os dados associados ao cep desejado
    public void BuscarCep(){

        // Variável que recebe o cep digitado
        String cepDigitado = editCep.getText().toString();

        // Criando um objeto do Retrofit, enquanto definimos a url base da API e o conversor de JSON para objeto Java
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Criando um objeto da interface RequestCep
        RequestCep requestCep = retrofit.create(RequestCep.class);

        // Aqui estamos utilizando o método getCep da interface para realizar a chamada dos dados, baseando-se no cepDigitado.
        // Também estamos fazendo um Callback para a classe DadosCep, que representa as informações da API
        requestCep.getCep(cepDigitado).enqueue(new Callback<DadosCep>() {
            @Override
            public void onResponse(Call<DadosCep> call, Response<DadosCep> response) {

                // Caso seja um cep existente, os campos de endereço serão preenchidos automaticamente, correspondendo às informações resgatadas da API
                editRua.setText(response.body().logradouro);
                editBairro.setText(response.body().bairro);
                editCidade.setText(response.body().localidade);
                editEstado.setText(response.body().uf);
            }

            @Override
            public void onFailure(Call<DadosCep> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(),"CEP não encontrado", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean ValidaCampo(){
        if(editCep.length()==0){
            Toast.makeText(getApplicationContext(),"O campo está vazio!", Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    public void LimpaCampo(){
        editCep.getText().clear();
    }

    public void EsconderTeclado(){
        InputMethodManager inm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

}