package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pojos.RutinasEntity;
import com.example.proyectotfg.R;

import java.util.List;

public class RutinasAdapter extends RecyclerView.Adapter<RutinasAdapter.ViewHolder> {
    private List<RutinasEntity> rutinas;
    private Context context;
    private OnItemClickListener listener;

    public RutinasAdapter(Context context, List<RutinasEntity> rutinas, OnItemClickListener listener) {
        this.context = context;
        this.rutinas = rutinas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RutinasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rutina, parent, false);
        return new RutinasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinasAdapter.ViewHolder holder, int position) {
        RutinasEntity r = rutinas.get(position);
        holder.textFechaCreacion.setText(r.getFechaCreacion());
        holder.textNombreEjercicio.setText(r.getNombre());
        holder.textGrupoMuscular.setText("Grupo muscular: " + r.getGrupoMuscular());
        holder.textSeries.setText(String.valueOf(r.getSeries()) + " series");
        holder.textRepeticiones.setText(String.valueOf(r.getRepeticiones()) + " repeticiones");
        holder.textPeso.setText("Peso: " + String.valueOf(r.getPeso()) + " kg");

        holder.itemView.setOnClickListener(v ->{
            if (listener != null) {
                listener.onItemClick(r);
            }
        });
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return rutinas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textFechaCreacion, textNombreEjercicio, textGrupoMuscular, textSeries, textRepeticiones, textPeso;

        public ViewHolder(View itemView) {
            super(itemView);
            textFechaCreacion = itemView.findViewById(R.id.textFechaCreacion);
            textNombreEjercicio = itemView.findViewById(R.id.textNombreEjercicio);
            textGrupoMuscular = itemView.findViewById(R.id.textGrupoMuscular);
            textSeries = itemView.findViewById(R.id.textSeries);
            textRepeticiones = itemView.findViewById(R.id.textRepeticiones);
            textPeso = itemView.findViewById(R.id.textPeso);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RutinasEntity rutina);
    }
}
