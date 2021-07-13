package com.example.crewinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.crewinfo.MainActivity;
import com.example.crewinfo.R;
import com.example.crewinfo.RoomArchitecture.CrewEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    ArrayList<CrewEntity> crewEntities = new ArrayList<>();
    Context context;
    OnCrewClicked onCrewClicked;

    public CrewAdapter(OnCrewClicked onCrewClicked) {
        this.onCrewClicked = onCrewClicked;
    }

    @NonNull
    @NotNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_layout, parent, false);
        return new CrewViewHolder(view, onCrewClicked, crewEntities);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CrewAdapter.CrewViewHolder holder, int position) {
        CrewEntity entity = crewEntities.get(position);
        holder.crew_name.setText(entity.getName());
        holder.crew_agency.setText(entity.getAgency());
        holder.crew_status.setText(entity.getStatus());
        holder.crew_wikipedia.setText(entity.getWikipedia());
        Glide.with(context).load(entity.getImage()).into(holder.crew_image);
        holder.itemView.setTag(entity);
    }

    public void setCrewInfo(List<CrewEntity> newEntities){
        crewEntities.clear();
        crewEntities.addAll(newEntities);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return crewEntities.size();
    }

    public static class CrewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView crew_image;
        TextView crew_name, crew_agency, crew_wikipedia, crew_status;
        OnCrewClicked onCrewClicked;
        ArrayList<CrewEntity> crewEntities;
        public CrewViewHolder(@NonNull @NotNull View itemView, OnCrewClicked onCrewClicked, ArrayList<CrewEntity> crewEntities) {
            super(itemView);
            crew_name = itemView.findViewById(R.id.crew_name);
            crew_image = itemView.findViewById(R.id.crew_image);
            crew_agency = itemView.findViewById(R.id.crew_agency);
            crew_wikipedia = itemView.findViewById(R.id.crew_wikipedia);
            crew_status = itemView.findViewById(R.id.crew_status);
            itemView.setOnClickListener(this);
            this.onCrewClicked = onCrewClicked;
            this.crewEntities = crewEntities;

        }

        @Override
        public void onClick(View v) {
            onCrewClicked.OnItemClicked(crewEntities.get(getAdapterPosition()));
        }

    }
    public interface OnCrewClicked{
        void OnItemClicked(CrewEntity entity);
        void OndeleteCrew(CrewEntity entity);
    }
}
