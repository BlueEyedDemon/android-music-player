package com.sogoodlabs.silvia.musicplayer;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sogoodlabs.silvia.musicplayer.controller.BeanContext;
import com.sogoodlabs.silvia.musicplayer.controller.TrackController;
import com.sogoodlabs.silvia.musicplayer.view.adapters.TrackControllerAdapter;
import com.sogoodlabs.silvia.musicplayer.model.entities.Playlist;

public class MainActivity extends AppCompatActivity {

    TrackControllerAdapter trackControllerAdapter;

    BeanContext beanContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAndBindBeans();
        beanContext.getThemeService().applyTheme();
        setContentView(R.layout.activity_main);

        if(beanContext.getTrackController() != null){
            beanContext.setPlaylists(beanContext.getTrackController().getPlaylist()!=null?
                    beanContext.getPlaylistDAO().getAllPlayLists(beanContext.getTrackController().getPlaylist()):
                    beanContext.getPlaylistDAO().getAllPlayLists());
            beanContext.injectInTrackController();
        } else {
            beanContext.setPlaylists(beanContext.getPlaylistDAO().getAllPlayLists());
            Playlist playlist = beanContext.getCurrentPlaylistStorageService().getLastPlayList(beanContext.getPlaylists());
            beanContext.setTrackController(new TrackController(playlist));
            beanContext.injectInTrackController();
        }

        beanContext.getViewChanger().showPlayLists();

        this.findViewById(R.id.track_controller_button).setOnClickListener(getTrackControllerButtonListener());
        this.findViewById(R.id.settings_button).setOnClickListener(getSettingsButtonListener());

        Typeface face= Typeface.createFromAsset(getAssets(), "font/GreatVibes-Regular.otf");
        ((TextView)this.findViewById(R.id.header)).setTypeface(face);

        setDrawerLayout();
    }

    /** Initialize track controller */
    private void setDrawerLayout(){
        DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        trackControllerAdapter = new TrackControllerAdapter(this, beanContext.getTrackController());
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(trackControllerAdapter);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, dl,
                R.string.app_name, R.string.app_name) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                trackControllerAdapter.offVisualizer();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                trackControllerAdapter.onVisualizer();
            }
        };

        // Set the drawer toggle as the DrawerListener
        dl.setDrawerListener(mDrawerToggle);
        setWidthForSlide(mDrawerList, 0.8f);
        beanContext.getViewChanger().setDrawerLayout(dl);
    }

    /** Set width for track controller slide
     * @param widthMultiplier  - multiplier for all screen width (slide width = multiplyer * screen width) */
    private void setWidthForSlide(ListView mDrawerList, float widthMultiplier){
        int width = (int) (getResources().getDisplayMetrics().widthPixels*widthMultiplier);
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerList.getLayoutParams();
        params.width = width;
        mDrawerList.setLayoutParams(params);
    }

    /** Button handler for button which opens track controller slide*/
    private View.OnClickListener getTrackControllerButtonListener(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                beanContext.getViewChanger().openSlide();
            }
        };
    }

    /** Button handler for opening settings */
    private View.OnClickListener getSettingsButtonListener(){
        return new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.getBeanContext().getViewChanger().showSettings();
            }
        };
    }

    /** Some spring-like stuff */
    void createAndBindBeans(){
        DBhelper dBhelper = new DBhelper(this);
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        beanContext = new BeanContext();
        beanContext.injectDB(db);
        beanContext.injectActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        trackControllerAdapter.onDestroy();
        System.gc();
    }

    public TrackControllerAdapter getTrackControllerAdapter() {
        return trackControllerAdapter;
    }

    public BeanContext getBeanContext() {
        return beanContext;
    }
}
