package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //0 : yellow , 1 : red , 2 : empty
    int activePlayer = 0;
    int coinTag;
    String winner;
    Boolean gameActive = false;
    Boolean gameOver = false;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    Button playAgainButton;
    TextView winnerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playAgainButton = (Button)findViewById(R.id.playAgainButton);
        winnerTextView = (TextView)findViewById(R.id.winnerTextView);
    }

    public void dropIn(View view){
        ImageView coinImageView = (ImageView)view;
        coinTag = Integer.parseInt(coinImageView.getTag().toString());

        if(gameState[coinTag]==2 && gameActive){
            gameState[coinTag] = activePlayer;

            if(activePlayer==0){
                coinImageView.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            }else{
                coinImageView.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            coinImageView.setTranslationY(-1500);
            coinImageView.animate().translationYBy(1500).setDuration(300);

            for(int[] winningPosition:winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]]!=2){


                    if(activePlayer==1){
                        winner = "Yellow";
                        winnerTextView.setTextColor(ContextCompat.getColor(this, R.color.yellow));
                    }else{
                        winner = "Red";
                        winnerTextView.setTextColor(ContextCompat.getColor(this, R.color.red));
                    }

                    gameActive = false;
                    gameOver = true;

                    Toast.makeText(this, winner+" has WON", Toast.LENGTH_SHORT).show();
                    playAgainButton.setText(R.string.playAgain);
                    winnerTextView.setText(winner+" has WON");
                }
            }
        }else{
            Toast.makeText(this, "Invalid move !", Toast.LENGTH_SHORT).show();
        }
    }

    public void playAgain(View view){

        if(!gameActive && !gameOver){
            playAgainButton.setText(R.string.pause);
            gameActive = true;
            Log.d(TAG, "playAgain: game is now active - pause");
        }

        else if(gameActive && !gameOver){
            playAgainButton.setText(R.string.resume);
            gameActive = false;
            Log.d(TAG, "playAgain: game is now paused - resume");
        }

        else if(gameOver){

            Log.d(TAG, "playAgain: game is restarted - start");

            GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);

            for(int i=0; i<gridLayout.getChildCount(); i++) {
                ImageView coin = (ImageView)gridLayout.getChildAt(i);
                coin.setImageDrawable(null);
            }

            for(int i = 0;i < gameState.length;i++){
                gameState[i] = 2;
            }

            gameActive = false;
            gameOver = false;

            playAgainButton.setText(R.string.start);

            winnerTextView.setText(R.string.app_name);
            winnerTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
    }
}
