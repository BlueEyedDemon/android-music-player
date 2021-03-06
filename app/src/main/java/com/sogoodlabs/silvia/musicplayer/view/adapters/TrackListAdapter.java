package com.sogoodlabs.silvia.musicplayer.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sogoodlabs.silvia.musicplayer.R;
import com.sogoodlabs.silvia.musicplayer.controller.TrackController;
import com.sogoodlabs.silvia.musicplayer.model.entities.Playlist;
import com.sogoodlabs.silvia.musicplayer.model.entities.Song;

/**
 * Created by Alexander on 23.08.2017.
 */

public class TrackListAdapter extends BaseAdapter {

    private TrackController trackController;

    /** Current listed playlist */
    private Playlist currentPlaylist;

    private LayoutInflater lInflater;

    public TrackListAdapter(Context ctx, Playlist playlist, TrackController trackController){
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.currentPlaylist = playlist;
        this.trackController = trackController;
    }

    @Override
    public int getCount() {
        return currentPlaylist.getSongs().size();
    }

    @Override
    public Object getItem(int i) {
        return currentPlaylist.getSongs().get(i);
    }

    @Override
    public long getItemId(int i) {
        return currentPlaylist.getSongs().get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Song song = currentPlaylist.getSongs().get(i);
        LinearLayout ll = getStyleForTrackView(i);
        setSongViewNameAndMarkLastPlayed(ll, song, i);
        setSongMetadata(ll, song);
        return ll;
    }

    /** Return different layout for song in playlist depending on whether the song is in track controller now */
    private LinearLayout getStyleForTrackView(int i){
        if(currentPlaylist.getSongs().get(i)==trackController.getCurrentTrack()) {
            return (LinearLayout) lInflater.inflate(R.layout.track_layout_current, null, false);
        } else {
            return (LinearLayout) lInflater.inflate(R.layout.track_layout, null, false);
        }
    }
    /** If song is the last played track (or current) then mark it */
    private void setSongViewNameAndMarkLastPlayed(LinearLayout ll, Song song, int i){
        TextView songTitle = ll.findViewById(R.id.song_title);
        if(currentPlaylist.getSongs().get(i)==currentPlaylist.getCurrentTrack()){
            songTitle.setText(song.getName());
            songTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        } else
            songTitle.setText(song.getName());
    }

    private void setSongMetadata(LinearLayout ll, Song song){
        ((TextView)ll.findViewById(R.id.song_duration)).setText(song.getMetadata().get("duration"));
        TextView songMeta = ll.findViewById(R.id.song_meta);
        songMeta.setSelected(true);
        if(song.getMetadata().get("artist")!=null && song.getMetadata().get("title")!=null)
            songMeta.setText(song.getMetadata().get("artist") + " - " + song.getMetadata().get("title"));
        else songMeta.setText("");
    }

//    public void setCurrentTrack(int i){
//        //currentTrack = i;
//        notifyDataSetChanged();
//    }
}
