package com.example.testgallery.fragments.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.SlideShowActivity;
import com.example.testgallery.activities.mainActivities.data_favor.DataLocalManager;
import com.example.testgallery.adapters.ItemAlbumAdapter;

import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;


import java.util.ArrayList;

import java.util.List;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;

    private List<String> imageListPath;
    private List<Image> imageList;
    private androidx.appcompat.widget.Toolbar toolbar_favor;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container,false);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.favor_category);
        toolbar_favor = view.findViewById(R.id.toolbar_favor);
        // Toolbar events
        toolbar_favor.inflateMenu(R.menu.menu_top_item_album);
        toolbar_favor.setTitle("Favorite");

        toolbar_favor.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.album_item_search:
                        Toast.makeText(view.getContext(), "Tìm kiếm", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.album_item_add:
                        Toast.makeText(view.getContext(), "Thêm ảnh vào album", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.album_item_delete:
                        Toast.makeText(view.getContext(), "Xóa", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.album_item_slideshow:
                        slideShowEvents();
                        break;
                }

                return true;
            }
        });

        imageListPath = DataLocalManager.getListImg();
        imageList = getListImgFavor(imageListPath);

        setRyc();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FavoriteFragment.MyAsyncTask myAsyncTask = new FavoriteFragment.MyAsyncTask();
        myAsyncTask.execute();
    }

    private void setRyc() {

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerView.setAdapter(new ItemAlbumAdapter(new ArrayList<>(imageListPath)));

    }



    private List<Image> getListImgFavor(List<String> imageListUri) {
        List<Image> listImageFavor = new ArrayList<>();
        List<Image> imageList = GetAllPhotoFromGallery.getAllImageFromGallery(context);
        for (int i = 0; i < imageList.size(); i++) {
            for (String st: imageListUri) {
                if(imageList.get(i).getPath().equals(st)){
                    listImageFavor.add(imageList.get(i));
                }
            }
        }

        return listImageFavor;
    }
    public class MyAsyncTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            imageListPath = DataLocalManager.getListImg();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            recyclerView.setAdapter(new ItemAlbumAdapter(new ArrayList<>(imageListPath)));
        }
    }

    private void slideShowEvents() {

    }
}