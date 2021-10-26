package br.unigran.aulafirebase2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference= DataFirebase.getDatabaseReference();
        listView=findViewById(R.id.listView);
        pessoas= new LinkedList<>();
        listar();
        }
    ArrayAdapter arrayAdapter;
    private void preenche() {
        if(arrayAdapter==null) {
            arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,
                    pessoas);
            listView.setAdapter(arrayAdapter);
        }else
            arrayAdapter.notifyDataSetChanged();

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