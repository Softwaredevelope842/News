package com.example.newsapp;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.datamodelclass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<datamodelclass.Article> list=new ArrayList<>();
    public NewsAdapter(ArrayList<datamodelclass.Article> list){ this.list=list;}
    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).title);
        holder.textView2.setText(list.get(position).publishedAt.toString());


            Picasso.get().load(list.get(position).urlToImage).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(v.getContext(), NewsDetail.class);
            intent.putExtra("key",position);
            v.getContext().startActivity(intent);
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        TextView textView2;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
            textView2=itemView.findViewById(R.id.textView2);
            imageView=itemView.findViewById(R.id.imageView);
 }
}
}
