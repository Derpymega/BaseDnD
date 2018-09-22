package app.tolo.basednd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        DatabaseHelper myDBA = new DatabaseHelper(this);

    }
}
