package fr.eisti.android.babillonsimulator;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private MediaPlayer mPlayer = null;
    private int currentPicId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, SoundService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer.release();
        }
    }

    public void reveilDamien(View view){
        Intent intent = new Intent(MainActivity.this, AlarmeActivity.class);
        startActivity(intent);
    }

    public void callDamien(View view){
        String number = "0658877455";
        Intent intent = new Intent( Intent.ACTION_DIAL, Uri.parse("tel:"+number) );
        startActivity(intent);
    }

    public static Intent getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/1098506452"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/damien.babillon"));
        }
    }

    public void openFacebookDamien(View view){
        Intent intent = getOpenFacebookIntent(this);
        startActivity(intent);
    }

    public void changeImage(View view)
    {
        int i = (new Random().nextInt(2)+1);
        currentPicId = (currentPicId + i)%3;
        ImageView image = (ImageView)findViewById(R.id.imageView);
        if (currentPicId==0)
        {
            image.setImageResource(R.drawable.babillon_happy);
        }
        else if (currentPicId==1)
        {
            image.setImageResource(R.drawable.babillon_mad);
        }
        else
        {
            image.setImageResource(R.drawable.babillon_neutral);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        stopService(new Intent(this, SoundService.class));
        Log.i("END activity", "on stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, SoundService.class));
    }
}
