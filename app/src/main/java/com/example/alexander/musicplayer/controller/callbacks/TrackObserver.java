package com.example.alexander.musicplayer.controller.callbacks;

import com.example.alexander.musicplayer.model.entities.Playlist;

/**
 * Created by Alexander on 26.08.2017.
 */

public interface TrackObserver {

    void update(int i, Playlist playlist);

}
