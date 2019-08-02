package com.example.vrgsofttestnechet.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vrgsofttestnechet.Constants;
import com.example.vrgsofttestnechet.R;
import com.example.vrgsofttestnechet.db.SQLiteConnector;
import com.example.vrgsofttestnechet.model.Model;
import com.example.vrgsofttestnechet.ui.PageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapterNews extends RecyclerView.Adapter<RecyclerAdapterNews.MostEmailed> {

    private Context context;
    private List<Model> models;
    private SQLiteConnector connector;
    private SQLiteDatabase db;
    private MyInterface myInterface;
    private int count;

    public RecyclerAdapterNews(Context context, List<Model> models, int count) {
        this.context = context;
        this.models = models;
        this.count = count;
    }

    public void setMyInterface(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    @NonNull
    @Override
    public RecyclerAdapterNews.MostEmailed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new RecyclerAdapterNews.MostEmailed(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterNews.MostEmailed holder, final int position) {
        connector = new SQLiteConnector(context);
        Picasso.get()
                .load(models.get(position).getImage())
                .into(holder.ivImage);

        holder.tvTitle.setText(models.get(position).getTitle());

        holder.btnToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = connector.getWritableDatabase();
                connector.toFavorites(models.get(position).getTitle(), models.get(position).getImage(), models.get(position).getHtmlUrl());
                if(count == Constants.KEY_FAVOTITE){
                    myInterface.btnChange(position);
                }else {
                    Toast.makeText(context, "Добавленно в избранное", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PageActivity.class);
                intent.putExtra("pageURL", models.get(position).getHtmlUrl());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MostEmailed extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvTitle;
        private Button btnToFavorites;

        public MostEmailed(View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            btnToFavorites = itemView.findViewById(R.id.btnToFavorites);
        }
    }

    public interface MyInterface {
        void btnChange(int position);
    }
}
