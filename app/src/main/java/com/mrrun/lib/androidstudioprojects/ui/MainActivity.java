package com.mrrun.lib.androidstudioprojects.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.TextView;

import com.mrrun.lib.androidstudioprojects.R;
import com.mrrun.lib.androidstudioprojects.view.episode.EpisodeEntity;
import com.mrrun.lib.androidstudioprojects.view.episode.EpisodeAdapter;
import com.mrrun.lib.androidstudioprojects.view.episode.EpisodeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lipin
 */
public class MainActivity extends AppCompatActivity {

    private TextView contentView;

    private EpisodeLayout episodelayout;

    private EpisodeAdapter episodeAdapter;

    List<EpisodeEntity> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniView();

        initData();
    }

    private void initData() {
        createData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false);
        episodelayout.setLayoutManager(linearLayoutManager);
        episodeAdapter = new EpisodeAdapter(episodelayout, data);
        episodelayout.setAdapter(episodeAdapter);
        episodeAdapter.setOnItemClickListener(new EpisodeAdapter.ItemClickListener() {
            @Override
            public void onItemUserClick(View itemView, int position) {
                contentView.setText(String.format("第%d集", position));
            }

            @Override
            public void onItemUserSet(int position) {
                contentView.setText(String.format("第%d集", position));
            }
        });
    }

    private void createData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        for (int i = 0; i < 5; i++) {
            data.add(new EpisodeEntity(String.valueOf(i + 1), false, false, true, false));
        }
        for (int i = 5; i < 10; i++) {
            data.add(new EpisodeEntity(String.valueOf(i + 1), false, false, false, false));
        }
        for (int i = 10; i < 20; i++) {
            data.add(new EpisodeEntity(String.valueOf(i + 1), false, true, false, false));
        }
        for (int i = 20; i < 30; i++) {
            data.add(new EpisodeEntity(String.valueOf(i + 1), false, true, false, true));
        }
    }

    private void iniView() {
        contentView = findViewById(R.id.content_view);
        episodelayout = findViewById(R.id.episodelayout);
    }
}
