package kr.playar.playar;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by Erroneous on 2018-03-25.
 */

public class VibrationService {
    private Vibrator vibrator;
    private MainActivity activity;
    public VibrationService(MainActivity activity){
        this.activity = activity;
        this.vibrator = (Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);
    }
    public void vibrate(){
        vibrator.vibrate(1000);
    }
}
