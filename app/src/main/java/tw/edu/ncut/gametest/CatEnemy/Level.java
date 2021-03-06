package tw.edu.ncut.gametest.CatEnemy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import tw.edu.ncut.gametest.GameView;
import tw.edu.ncut.gametest.MainActivity;
import tw.edu.ncut.gametest.R;

/**
 * Created by HatsuneMiku on 2018/1/5.
 */

public class Level extends Thread{

    private GameManagerWithCounter gameManager;
    private GameView gameView;
    private Bitmap cat1;
    private boolean gameClear = false;
    private boolean gameOver = false;
    private boolean allOver = false;
    private Activity activity;

    public Level(Activity activity, GameManagerWithCounter gameManager, GameView gameView){
        this.gameManager = gameManager;
        this.gameView = gameView;
        this.activity = activity;
        cat1 = BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.cat_blue);
        init();
    }

    @Override
    public void run() {
        int i = 0;
        while(!allOver) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!(gameClear || gameOver)) {
                gameManager.regist(
                        new SmallBlueCat(gameManager.getContext(),
                                gameManager.getWidth() - SmallBlueCat.CatWidth / 2 - gameManager.getHeight() / 2,
                                gameManager.getHeight() - SmallBlueCat.CatHeight));
                if(Math.random()  > 0.8){
                    gameManager.regist(
                            new EnemyShrimp(gameManager.getContext(),
                                    gameManager.getWidth() - EnemyShrimp.CatWidth / 2 - gameManager.getHeight() / 2,
                                    gameManager.getHeight() - EnemyShrimp.CatHeight));
                }
            }
        }
    }

    private void init() {
        gameClear = false;
        gameOver = false;
        Bitmap a = BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.red);
        int w = (int)(gameManager.getHeight() * ((float)a.getWidth() / a.getHeight()));
        gameManager.regist(new Castle(
                a,
                10000,
                0,
                0,
                0,
                w,
                gameManager.getHeight(),
                "RED TEAM",
                new Castle.DestroyCallBack() {
                    @Override
                    public void onDestroy() {
                        Level.this.gameManager.regist(new TextCharacter("GAME OVER",
                                Level.this.gameManager.getWidth() / 2 - 60,
                                Level.this.gameManager.getHeight() / 2 ,
                                20));
                        Level.this.gameManager.pauseGame();
                        gameOver = true;
                        onGameEnd("GAME OVER");
                    }
                }));
        a = BitmapFactory.decodeResource(gameManager.getContext().getResources(), R.drawable.blue);
        w = (int)(gameManager.getHeight() * ((float)a.getWidth() / a.getHeight()));
        gameManager.regist(new Castle(
                a,
                10000,
                0,
                gameManager.getWidth() - w,
                0,
                w,
                gameManager.getHeight(),
                "BLUE TEAM",
                new Castle.DestroyCallBack() {
                    @Override
                    public void onDestroy() {
                        Level.this.gameManager.regist(new TextCharacter("GAME CLEAR",
                                Level.this.gameManager.getWidth() /  2 - 60,
                                Level.this.gameManager.getHeight() / 2,
                                20));
                        Level.this.gameManager.pauseGame();
                        gameClear = true;
                        onGameEnd("Victory");
                    }
                }));
    }

    private void onGameEnd(final String gameState) {
        gameView.GamePause();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(gameManager.getContext());
                dlgAlert.setMessage(String.format(
                        gameState + "\n" +
                                "your call %d cat\n" +
                                "and kill %d enemy\n"
                        ,
                        gameManager.getRegistTagCount("RED TEAM") - 1,
                        gameManager.getUnregistTagCount("BLUE TEAM")
                ));
                dlgAlert.setTitle("Game Score");
                dlgAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gameManager.cleanAllObject();
                        gameManager.resumeGame();
                        init();
                        gameManager.updateScreen();
                        gameView.GameStart();

                    }
                });
                dlgAlert.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Level.this.allOver = true;
                    }
                });
                dlgAlert.create().show();
            }
        });
    }
}
