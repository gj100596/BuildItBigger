package gj.udacity.project1.androidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LibraryJokeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_joke_display);

        TextView jokeView = (TextView) findViewById(R.id.joke);

        String joke = getIntent().getStringExtra("Joke");
        if(joke != null)
            jokeView.setText(joke);

    }
}
