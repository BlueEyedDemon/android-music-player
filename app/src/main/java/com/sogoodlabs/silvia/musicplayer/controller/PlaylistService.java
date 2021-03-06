package com.sogoodlabs.silvia.musicplayer.controller;

import com.sogoodlabs.silvia.musicplayer.model.entities.Playlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alexander on 20.08.2017.
 */

public class PlaylistService {

    public List<String> playlistsNames(List<Playlist> playlists){
        List<String> result =  new ArrayList<>();
        for(Playlist pl: playlists){
            result.add(pl.getName());
        }
        return result;
    }

    public Playlist getPlayListByName(String name, List<Playlist> pls){
        for(Playlist pl : pls){
            if(pl.getName().equals(name)) return pl;
        }
        return null;
    }

    public void sortById(List<Playlist> plList){
        Collections.sort(plList, new Comparator<Playlist>() {
            @Override
            public int compare(Playlist playlist1, Playlist playlist2) {
                if(playlist1.getId()>playlist2.getId()) return 1;
                if(playlist1.getId()<playlist2.getId()) return -1;
                return 0;
            }
        });
    }

    public Playlist getFromListByID(List<Playlist> playlists, long id){
        for(Playlist playlist: playlists){
            if(playlist.getId()==id)
                return playlist;
        }
        return null;
    }

}
