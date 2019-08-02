package com.example.vrgsofttestnechet.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vrgsofttestnechet.Constants;
import com.example.vrgsofttestnechet.R;
import com.example.vrgsofttestnechet.adapter.RecyclerAdapterNews;
import com.example.vrgsofttestnechet.db.SQLiteConnector;
import com.example.vrgsofttestnechet.model.Model;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView rwFavorites;
    private List<Model> models;
    private SQLiteConnector connector;
    private SQLiteDatabase db;
    private RecyclerAdapterNews mAdapter;
    private RecyclerView.LayoutManager mLayoutManagers;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);
        rwFavorites = v.findViewById(R.id.rwFavorites);

        models = new ArrayList<>();
        models.clear();

        mLayoutManagers = new LinearLayoutManager(getContext());
        rwFavorites.setLayoutManager(mLayoutManagers);

        mAdapter = new RecyclerAdapterNews(getContext(), models, Constants.KEY_FAVOTITE);
        rwFavorites.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        connector = new SQLiteConnector(getContext());
        db = connector.getWritableDatabase();

        mAdapter.setMyInterface(new RecyclerAdapterNews.MyInterface() {
            @Override
            public void btnChange(int position) {
                db = connector.getWritableDatabase();
                connector.deletinItemTorgPred(models.get(position).getTitle());
                models.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        metod();

        return v;
    }

    private void metod() {
        Cursor cursor = connector.getReadableDatabase().query("NewsFavorites"
                , new String[]{"tittle", "image", "url"}
                , null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setTitle(cursor.getString(0));
                model.setImage(cursor.getString(1));
                model.setHtmlUrl(cursor.getString(2));

                models.add(model);
                mAdapter.notifyDataSetChanged();

                rwFavorites.getAdapter().notifyDataSetChanged();

            } while (cursor.moveToNext());
        }
    }

}
