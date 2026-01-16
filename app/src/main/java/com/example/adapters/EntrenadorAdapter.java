package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pojos.EntrenadoresEntity;
import com.example.proyectotfg.R;

import java.util.List;


public class EntrenadorAdapter extends RecyclerView.Adapter<EntrenadorAdapter.ViewHolder> {

    private List<EntrenadoresEntity> entrenadores;
    private Context context;
    private OnItemClickListener listener;

    public EntrenadorAdapter(Context context, List<EntrenadoresEntity> entrenadores, OnItemClickListener listener) {
        this.context = context;
        this.entrenadores = entrenadores;
        this.listener = listener;

    }


    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_entrenador, parent, false);
        return new ViewHolder(view);
    }

    /**
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EntrenadoresEntity e = entrenadores.get(position);
        holder.textNombre.setText(e.getNombre());
        holder.textCorreo.setText("Contacto: " + e.getCorreo());
        holder.textEspecialidad.setText("Especialidad: " + e.getEspecialidad());
        holder.textExperiencia.setText("Experiencia: " + e.getExperiencia());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(e));

    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return entrenadores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textCorreo, textEspecialidad, textExperiencia;

        public ViewHolder(View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombreEntrenador);
            textCorreo = itemView.findViewById(R.id.textCorreoEntrenador);
            textEspecialidad = itemView.findViewById(R.id.textEspecialidad);
            textExperiencia = itemView.findViewById(R.id.textExperiencia);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EntrenadoresEntity entrenador);
    }
}


