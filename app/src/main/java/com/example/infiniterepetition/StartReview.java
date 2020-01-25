package com.example.infiniterepetition;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.res.Resources;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.KeyEvent;
        import android.view.View;
        import android.widget.TextView;
        import android.widget.Toast;


public class StartReview extends AppCompatActivity {

    SQLiteDatabase db;
    MediaPlayer player;
    int buttonClickCount = 0;
    String prepQuestion = "";
    String prepAnswer = "";
    String prepSound = "";
    String tag = "DEBUG TAG: ";    //debug tag


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_review);

        db = openOrCreateDatabase("Collection_", MODE_PRIVATE, null);

        prepareRandomQandA();   //prepare question when start activity


    }

    public boolean onKeyDown(int keyCode, KeyEvent event)   //clear activity [backButton]
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    private void prepareRandomQandA(){               //TODO Test after added more records

        Cursor c = db.rawQuery("SELECT Id, Question, Answer, Sound FROM TABLE1",null);
        c.moveToLast();
        int max = c.getCount();
        int min = 0;                    //rekordy sa liczone od  0

        int numberOfRandomRow = (int)(Math.random()*((max-min)))+min;  //generowanie losowej liczby

        // String strgDebug = String.valueOf(numberOfRandomRow); prepared string for debug tag
       // Log.d(tag, "DEBUG" + strgDebug + " ????");    //show nr of row

        c.moveToPosition(numberOfRandomRow);

        prepAnswer = c.getString(c.getColumnIndexOrThrow("Answer"));
        prepQuestion = c.getString(c.getColumnIndexOrThrow( "Question"));
        prepSound = c.getString(c.getColumnIndexOrThrow("Sound"));

        showQuestion(prepQuestion, prepSound);


    }


    public void onButtonClickCount(View view){
        buttonClickCount=buttonClickCount+1;

        if (buttonClickCount == 1){
         //Toast.makeText(this, "1 Click" , Toast.LENGTH_SHORT).show(); //show toast with first click
            showAnswer(prepAnswer);
        }

        if (buttonClickCount == 2){
            buttonClickCount = 0;   //clear counter
            hideAnswer();
            player.stop();
            prepareRandomQandA();   //get random question and answer
        }

    }


    public void showQuestion(String preparedQuestion, String preparedSound){              //This function dispaly Question on R.id.QuestionTextView

        TextView questionView = (TextView) findViewById(R.id.QuestionTextView);
        questionView.setText(preparedQuestion);

        int resID = getResources().getIdentifier(preparedSound,         //make a resource from R.raw + string we can't add simply string to R.raw :( its necesarry
                "raw", this.getPackageName());

        player = MediaPlayer.create(this, resID);           //TODO BUG ERROR(-38,0)   BUG ERROR(1,-19)
        player.start();


    }


    public void showAnswer(String preparedAnswer){          //This function dispaly Answer on R.id.AnswerTextView

        TextView answerView = (TextView) findViewById(R.id.AnswerTextView);
        answerView.setText(preparedAnswer);
    }


    public void hideAnswer(){        //This function dissaper Answer on R.id.AnswerTextView

        TextView answerView = (TextView) findViewById(R.id.AnswerTextView);
        answerView.setText("");
    }

}