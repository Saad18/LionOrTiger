package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    enum player {
        ONE, Two, NO
    }

    player currentPlayer = player.ONE;

    player[] playerChoices = new player[9];

    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver = false;
    private Button btnReset;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        Arrays.fill( playerChoices, player.NO );

        btnReset = findViewById( R.id.btnReset );
        gridLayout = findViewById( R.id.gridLayout );

        btnReset.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        } );
    }

    public void imageViewIsTapped(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt( tappedImageView.getTag().toString() );

        if (playerChoices[tiTag] == player.NO && !gameOver) {

            tappedImageView.setTranslationX( -2000 );


            playerChoices[tiTag] = currentPlayer;

            if (currentPlayer == player.ONE) {
                tappedImageView.setImageResource( R.drawable.lion );
                currentPlayer = player.Two;
            } else if (currentPlayer == player.Two) {
                tappedImageView.setImageResource( R.drawable.tiger );
                currentPlayer = player.ONE;
            }

            tappedImageView.animate().translationXBy( 2000 ).
                    alpha( 1 ).rotation( 3600 ).setDuration( 1000 );

            Toast.makeText( this, tappedImageView.getTag().
                    toString(), Toast.LENGTH_LONG ).show();

            for (int[] winnerColumns : winnerRowsColumns) {
                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]] &&
                        playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]] &&
                        playerChoices[winnerColumns[0]] != player.NO) {

                    btnReset.setVisibility( View.VISIBLE );


                    gameOver = true;

                    String winnerOfGame = "";

                    if (currentPlayer == player.ONE) {
                        winnerOfGame = "Player TWO";

                    } else if (currentPlayer == player.Two) {

                        winnerOfGame = "Player ONE";
                    }
                    Toast.makeText( this, winnerOfGame + " is the Winnr", Toast.LENGTH_LONG ).show();
                }

            }

        }

    }

    private void resetTheGame() {
        for (int index = 0; index < gridLayout.getChildCount(); index++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt( index );
            imageView.setImageDrawable( null );
            imageView.setAlpha( 0.2f );
        }

        currentPlayer = player.ONE;


        Arrays.fill( playerChoices, player.NO );

        gameOver = false;
        btnReset.setVisibility( View.INVISIBLE );
    }

}
