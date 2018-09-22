package app.tolo.basednd;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListDataAvtivity extends AppCompatActivity {

    private static final String TAG = "ListDataAvtivity";

    //DatabaseHelper myDB;
    DatabaseHelper dh;
    TxtProcessor tp;

    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_avtivity);

        lv = findViewById(R.id.ListView);
        //myDB = new DatabaseHelper(this);

        dh = DatabaseHelper.getInstance(this);


        tp = new TxtProcessor(this);

        tp.readTxt();


        // It is necessary to use a thread (to be able to handle everything)
        new Thread(new Runnable() {
            @Override
            public void run() {
                populateListView();
            }
        }).start();

    }

    private void populateListView() {
        //Log.d(TAG, "populateListView: Displaying data in the ListView");
        // Get the data and append to a list
        Cursor data = dh.getData();
        ArrayList<String> listData = new ArrayList<>();

        while(data.moveToNext()) {
            // Get the value from the database in colum 1, then add it to the ArrayList

            listData.add(data.getString(1));
        }

        // Create a list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        lv.setAdapter(adapter);
    }


}
