package fr.eisti.android.babillonsimulator;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SoundService extends Service {
    private boolean _paused;
    private boolean _stopped;
    private MediaPlayer mPlayer = null;
    private Handler handler;
    private List<Integer> mSounds;

    private static final int RANDOM_MAX = 20;
    private static final int RANDOM_MIN = 5;

    public SoundService() {
        _paused = false;
        _stopped = false;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        _paused = false;
        _stopped = false;
        mSounds = new ArrayList<Integer>();
        mSounds.add(R.raw.bonjour);
        mSounds.add(R.raw.damienbabillon);
        mSounds.add(R.raw.debout);
        mSounds.add(R.raw.projetarendre);
        mSounds.add(R.raw.finiprojet);
        mSounds.add(R.raw.heuretravailler);
        mSounds.add(R.raw.facebook);
        mSounds.add(R.raw.disney1);
        mSounds.add(R.raw.quanddisney);
        mSounds.add(R.raw.pourquoidisney);
        handler = new Handler();
        handler.post(runnable);
        return 1;
    }

    public void onStop() {
        _stopped = true;
    }

    public void onPause() {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
            mPlayer.release();
        }
        _paused = true;
    }

    @Override
    public void onDestroy() {
        _stopped = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void playSound(int resId) {
        if (mPlayer != null) {
            mPlayer.reset();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!_paused) {
                Log.i("Service.run", "sent");
            }
            if (!_stopped) {
                int delai = (new Random().nextInt(RANDOM_MAX - RANDOM_MIN) + RANDOM_MIN) * 1000;
                try {
                  //  Thread.sleep(delai);
                    playSound(mSounds.get(new Random().nextInt(mSounds.size())));
                    handler.postDelayed(runnable, delai);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}