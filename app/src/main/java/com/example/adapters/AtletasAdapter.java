package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pojos.AtletasEntity;
import com.example.proyectotfg.R;

import java.util.List;

public class AtletasAdapter extends RecyclerView.Adapter<AtletasAdapter.ViewHolder>{
    private List<AtletasEntity> atletas;
    private Context context;
    private OnItemClickListener listener;



    public AtletasAdapter(Context context, List<AtletasEntity> atletas, OnItemClickListener listener) {
        this.context = context;
        this.atletas = atletas;
        this.listener = listener;

    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return view
     */
    @NonNull
    @Override
    public AtletasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_atleta, parent, false);
        return new AtletasAdapter.ViewHolder(view);
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AtletasAdapter.ViewHolder holder, int position) {
        AtletasEntity a = atletas.get(position);
        holder.textNombre.setText(a.getNombre());
        holder.textCorreo.setText("Contacto: " + a.getCorreo());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(a));

    }

    @Override
    public int getItemCount() {
        return atletas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textCorreo;

        public ViewHolder(View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombreAtleta);
            textCorreo = itemView.findViewById(R.id.textCorreoAtleta);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AtletasEntity atleta);
    }

}
