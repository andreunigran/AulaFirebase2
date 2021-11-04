package br.unigran.aulafirebase2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

import br.unigran.aulafirebase2.model.Pessoa;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.novoItem:
                novo();
            break;
            case R.id.sobreItem:
                Toast.makeText(MainActivity.this,"Sobre",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recycle view
        recyclerView=findViewById(R.id.recView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //fim recycle view

        //firebase
        databaseReference= DataFirebase.getDatabaseReference();

        pessoas= new LinkedList<>();
        //chamada firebase
        listar();

    }
    PessoaAdapter pessoaAdapter;
    private void preenche() {
        pessoaAdapter= new PessoaAdapter(pessoas);
        recyclerView.setAdapter(pessoaAdapter);

    }


    List<Pessoa> pessoas;
    public void listar(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot dataSnapshot = snapshot.child("Pessoa");
                pessoas.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Pessoa pessoa = postSnapshot.getValue(Pessoa.class);
                    pessoas.add(pessoa);

                }
                preenche();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    Pessoa pessoa;
    public void listarSomenteUm(String id){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 pessoa = (Pessoa) snapshot.child("Pessoa").child(id).getValue(Pessoa.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void novo(View view){
        novo();

    }
    private void novo(){
        Intent it = new Intent(this,Cadastro.class);
        it.putExtra("Pessoa",new Pessoa(pessoas.size()+1));
        someActivityResultLauncher.launch(it);

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        doSomeOperations();
                    }
                }
            });

    private void doSomeOperations() {
        listar();
    }

}