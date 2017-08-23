package com.example.alexander.musicplayer;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.alexander.musicplayer.model.entities.Playlist;

/**
 * Created by Alexander on 18.08.2017.
 */

public class CreatePlayListDialog extends DialogFragment {

    Playlist playlist;
    View.OnClickListener goToNewPlaylist;
    View.OnClickListener onCancelCallback;
    EditText playlistName;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.create_playlist_dialog, container, false);
        playlistName = ll.findViewById(R.id.playlist_title);
        ll.findViewById(R.id.ok).setOnClickListener(getOkButtonListener());
        ll.findViewById(R.id.cancel).setOnClickListener(getCancelButtonListener());
        return ll;
    }

    private View.OnClickListener getOkButtonListener(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                playlist.setName(playlistName.getText().toString());
                if(goToNewPlaylist!=null) goToNewPlaylist.onClick(v);
                CreatePlayListDialog.this.dismiss();
            }
        };
    }

    private View.OnClickListener getCancelButtonListener(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                if(onCancelCallback!=null) onCancelCallback.onClick(v);
                CreatePlayListDialog.this.dismiss();
            }
        };
    }

    public View.OnClickListener getGoToNewPlaylist() {
        return goToNewPlaylist;
    }
    public void setGoToNewPlaylist(View.OnClickListener goToNewPlaylist) {
        this.goToNewPlaylist = goToNewPlaylist;
    }

    public View.OnClickListener getOnCancelCallback() {
        return onCancelCallback;
    }
    public void setOnCancelCallback(View.OnClickListener onCancelCallback) {
        this.onCancelCallback = onCancelCallback;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
