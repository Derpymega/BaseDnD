package app.tolo.basednd;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    // Database related
    DatabaseHelper myDB;
    //TxtProcessor tp;

    // Layout related
    EditText inputtxt;
    Button findbtn, viewbtn;
    ProgressBar progressBar;

    // Extra
    Context context = MainActivity.this;


    // Extra class
    public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    myDB = new DatabaseHelper(context);
                    myDB.deleteAll();
                    readTxt();

                }
            }).start();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(context,"Base de datos cargada", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        tp = new TxtProcessor(this);

        tp.readTxt();
        */

        // Layout related
        inputtxt = findViewById(R.id.inputText);
        findbtn = findViewById(R.id.buttonFind);
        viewbtn = findViewById(R.id.viewButton);
        progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.GONE);



        // Database related
        new MyAsyncTask().execute();

        /*****************************************/

        findbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                myDB.deleteAll();
                progressBar.setVisibility(View.GONE);
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    SQLiteDatabase db = myDB.getReadableDatabase();

                } catch (SQLException e) { e.printStackTrace(); }


                Intent intent = new Intent(context, ListDataAvtivity.class);
                startActivity(intent);

            }
        });
    }

    /********** METHODS **********/

    public void readTxt() {
        try {

            InputStream is = context.getAssets().open("precios_para_da.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String n, q, p;
            String line;

            while((line = reader.readLine()) != null) {
                String[] aux = line.split(" ");
                String a = aux[0].replace('_', ' ');
                n = a.substring(0, 1).toUpperCase() + a.substring(1);
                q = aux[1];
                p = aux[2];

                System.out.println("Nombre: " + n + " Cantidad: " + q + " Precio: " + p);

                myDB.addData(n,q,p);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
