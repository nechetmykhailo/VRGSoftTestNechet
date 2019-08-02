package com.example.vrgsofttestnechet.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vrgsofttestnechet.Constants;
import com.example.vrgsofttestnechet.R;
import com.example.vrgsofttestnechet.adapter.RecyclerAdapterNews;
import com.example.vrgsofttestnechet.model.Model;
import com.example.vrgsofttestnechet.retrofit.MyInterface;
import com.example.vrgsofttestnechet.retrofit.RetroClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewedFragment extends Fragment {

    private RecyclerView rwViewed;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManagers;
    private List<Model> models;
    private final MyInterface api = RetroClient.getApiService();

    public ViewedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_viewed, container, false);
        rwViewed = v.findViewById(R.id.rwViewed);
        models = new ArrayList<>();

        mLayoutManagers = new LinearLayoutManager(getContext());
        rwViewed.setLayoutManager(mLayoutManagers);

        mAdapter = new RecyclerAdapterNews(getContext(), models, Constants.KEY_ALL);
        rwViewed.setAdapter(mAdapter);

        metodGetRetrofitEmailed();
        return v;
    }

    private void metodGetRetrofitEmailed() {
        Call<JsonObject> call = api.getViewed(Constants.API_KEYS);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                JsonArray jsonArray = jsonObject.get("results").getAsJsonArray();
                for (JsonElement element : jsonArray) {
                    Model model = new Model();
                    jsonObject = element.getAsJsonObject();
                    model.setTitle(jsonObject.get("title").getAsString());
                    model.setHtmlUrl(jsonObject.get("url").getAsString());
                    JsonArray jsonArray1 = jsonObject.get("media").getAsJsonArray();
                    for (JsonElement jsonElement : jsonArray1) {
                        JsonArray jsonArray2 = jsonElement.getAsJsonObject().get("media-metadata").getAsJsonArray();
                        for (JsonElement jsonElement2 : jsonArray2) {
                            jsonObject = jsonElement2.getAsJsonObject();

                            model.setImage(jsonObject.get("url").getAsString());
                        }
                    }
                    models.add(model);
                }
                rwViewed.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }
}
