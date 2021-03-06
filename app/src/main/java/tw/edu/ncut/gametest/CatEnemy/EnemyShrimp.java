package tw.edu.ncut.gametest.CatEnemy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.LinkedList;
import java.util.List;

import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/7.
 */

public class EnemyShrimp extends BlueCat {
    public static final int CatWidth = 88;
    public static final int CatHeight = 50;

    public EnemyShrimp(Context context, int x, int y){
        this(context, 250, 30, x, y, CatWidth, CatHeight);
    }

    public EnemyShrimp(Context context, int heal, int attack, int x, int y, int w, int h) {
        super(heal, attack, x, y, w, h);

        setAttackSpeed(0.5f);

        List<Bitmap> w_frame = new LinkedList<>();
        w_frame.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.haibi2), w, h, false));
        w_frame.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.haibi3), w, h, false));

        List<Long> w_time = new LinkedList<>();
        w_time.add(300L);
        w_time.add(300L);
        animation.addAnimation(new BitmapFrame(w_frame, w_time));

        List<Bitmap> a_frame = new LinkedList<>();
        a_frame.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.haibi2), w, h, false));
        a_frame.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.haibi3), w, h, false));

        List<Long> a_time = new LinkedList<>();
        a_time.add((long)(getAttackSpeed() * 500));
        a_time.add((long)(getAttackSpeed() * 500));
        animation.addAnimation(new BitmapFrame(a_frame, a_time));

        animation.start();
    }
}