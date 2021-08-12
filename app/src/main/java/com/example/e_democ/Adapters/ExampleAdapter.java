package com.example.e_democ.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_democ.Model.Candidate;
import com.example.e_democ.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<Candidate> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView c_image;
        public TextView c_name;
        public TextView c_no_of_supporters;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            c_image = itemView.findViewById(R.id.c_image);
            c_name = itemView.findViewById(R.id.c_name);
            c_no_of_supporters = itemView.findViewById(R.id.c_no_of_supporters);

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

    public ExampleAdapter(ArrayList<Candidate> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_list, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Candidate currentItem = mExampleList.get(position);

        String img_url="https://firebasestorage.googleapis.com/v0/b/e-democ.appspot.com/o/images%2F"+currentItem.getC_img_name()+"?alt=media&token=fe347521-367b-4d59-8c8b-53c9d08c45c0";
        Picasso.get().load(img_url).into(holder.c_image);
        holder.c_name.setText(currentItem.getC_full_name());
        holder.c_no_of_supporters.setText("No of Supporters : "+currentItem.getC_supporters());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
