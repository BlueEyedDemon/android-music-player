package com.example.alexander.musicplayer;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.example.alexander.musicplayer.entities.Playlist;
import com.example.alexander.musicplayer.file_chooser.FileChoosingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PlaylistsFragment playlistsFragment;
    List<Playlist> playlists = new ArrayList<>();
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState != null) {
            return;
        }

        playlistsFragment = new PlaylistsFragment();
        playlistsFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, playlistsFragment).commit();

        setDrawerLayout();
    }

    private void setDrawerLayout(){
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new TrackControllerAdapter(this));
        setWidthForSlide(0.8f);
    }

    private void setWidthForSlide(float widthMultiplier){
        int width = (int) (getResources().getDisplayMetrics().widthPixels*widthMultiplier);
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        params.width = width;
        mDrawerList.setLayoutParams(params);
    }


    public void showChooseFilesFragment(ArrayList<String> tracks) {
        FileChoosingFragment fileChoosingFragment = new FileChoosingFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("paths", tracks);
        fileChoosingFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fileChoosingFragment).addToBackStack(null).commit();
    }

    public void showPlayLists() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playlistsFragment).addToBackStack(null).commit();
    }

    public void showPlayListContent(Playlist playlist){
        PlaylistContentFragment playlistContentFragment = new PlaylistContentFragment();
        playlistContentFragment.setCurrentPlaylist(playlist);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playlistContentFragment).addToBackStack(null).commit();
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }
}
