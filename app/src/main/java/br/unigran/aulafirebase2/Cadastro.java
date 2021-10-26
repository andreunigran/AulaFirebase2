package br.unigran.aulafirebase2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.unigran.aulafirebase2.model.Pessoa;

public class Cadastro extends AppCompatActivity {
    private TextView nome;
    private Pessoa pessoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nome=findViewById(R.id.nomeID);
        Intent i = getIntent();
        pessoa= (Pessoa) i.getSerializableExtra("Pessoa");
    }
    public  void salvar(View view){
        pessoa.setNome(nome.getText().toString());
        DataFirebase.salvar(pessoa);

    }
}