package br.unigran.aulafirebase2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import br.unigran.aulafirebase2.model.Pessoa;

public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.PessoaHolder> {
    List<Pessoa> dados;

    public PessoaAdapter(List<Pessoa> pessoas) {
        this.dados=pessoas;
    }

    @NonNull
    @Override
    public PessoaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(viewGroup.getContext());
        View view =layoutInflater.inflate(R.layout.linhas,viewGroup,false);
        return new PessoaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaHolder pessoaViewHolder, int i) {
        Pessoa pessoa = dados.get(i);
        pessoaViewHolder.nomeItem.setText(pessoa.getNome());
    }
    @Override
    public int getItemCount() {
        return dados.size();
    }
    public class PessoaHolder extends RecyclerView.ViewHolder {
        private TextView nomeItem;
        private Button btn;
        public PessoaHolder(@NonNull View itemView) {
            super(itemView);
            nomeItem=itemView.findViewById(R.id.nomeItemLinha);
            btn=itemView.findViewById(R.id.removerIDItem);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"Ola"+nomeItem.getText(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
