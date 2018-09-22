package app.tolo.basednd;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class TxtProcessor {

    DatabaseHelper myDB;

    private Context context;
    //File f = new File("assets/precios_para_da.txt");

    public TxtProcessor(Context context) {
        this.context = context;
        myDB = new DatabaseHelper(context);
    }

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
