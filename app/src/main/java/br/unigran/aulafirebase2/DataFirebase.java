package br.unigran.aulafirebase2;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.unigran.aulafirebase2.model.Pessoa;

public class DataFirebase {
   private static FirebaseDatabase firebaseDatabase;
   private static DatabaseReference databaseReference;
    private static void inicio(){
        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference= firebaseDatabase.getReference();
    }

    public static DatabaseReference getDatabaseReference() {
        if(databaseReference==null)
            inicio();
        return databaseReference;
    }

    public static void salvar(Pessoa pessoa){
        if(databaseReference==null)
            inicio();
        databaseReference.child("Pessoa").child(
                pessoa.getId().toString()
        ).child("nome").setValue(pessoa.getNome());
    }
    public void remover(Pessoa pessoa){

        databaseReference.child("Pessoa").child(pessoa.getId()+"").removeValue();
    }





}
