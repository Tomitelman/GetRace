package com.example.getracefix;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ListAdapter extends FirestoreRecyclerAdapter<Race, ListAdapter.ListHolder> {
    private OnItemClickListener mListener;

    public ListAdapter(@NonNull FirestoreRecyclerOptions<Race> options) {
        super(options);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    //getting information from firebase on races , cards listener
    @Override
    protected void onBindViewHolder(@NonNull ListHolder holder, int position, @NonNull Race model) {
        String Origin = getItem(position).getOrigin();
        String destination = getItem(position).getDestination();
        String email = getItem(position).getEmail();
        String phoneNumber = getItem(position).getPhoneNumber();
        String carModel = getItem(position).getCarModel();
        String plate = getItem(position).getPlateNumber();

        Race Race1 = new Race(Origin , destination , email , phoneNumber , carModel , plate);
        holder.textViewOrigin.setText(model.getOrigin());
        holder.textViewDestination.setText(Race1.getDestination());
        holder.textViewEmail.setText(Race1.getEmail());
        holder.textViewPhoneNumber.setText(Race1.getPhoneNumber());
        holder.textViewCarModel.setText(Race1.getCarModel());
        //holder.textViewPlate.setText(Race1.getPlateNumber());
    }
//defining what the recyclerview "hold"
    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profileitem,
                parent, false);
        return new ListHolder(v, mListener);
    }

    class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewOrigin;
        TextView textViewDestination;
        TextView textViewEmail;
        TextView textViewPhoneNumber;
        TextView textViewCarModel;
        TextView textViewPlate;


        public ListHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewOrigin = itemView.findViewById(R.id.origin_tv);
            textViewDestination = itemView.findViewById(R.id.destination_tv);
            textViewEmail = itemView.findViewById(R.id.email_tv);
            textViewPhoneNumber = itemView.findViewById(R.id.phonenumber_tv);
            textViewCarModel = itemView.findViewById(R.id.carmodel_tv);
            textViewPlate = itemView.findViewById(R.id.platenumber_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
