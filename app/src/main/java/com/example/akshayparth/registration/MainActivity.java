package com.example.akshayparth.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String[] Events;
    ArrayList<String> events;
    //int[] Images=getResources().getIntArray(R.array.Images);
    ArrayList<String> events_registered;
    String encoded = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Events = getResources().getStringArray(R.array.Events);
        events = new ArrayList<String>();
        events_registered = new ArrayList<String>();

        decoder();
        createEventsLeft();


        // for (int i = 0; i < Events.length; i++)
        //   events.add(Events[i]);


        ArrayAdapter ad1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, events);
        ArrayAdapter ad2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, events_registered);

        final ListView ls1 = (ListView) findViewById(R.id.list_Events);
        final ListView ls2 = (ListView) findViewById(R.id.list_eventsRegistered);
        ls1.setAdapter(ad1);
        ls2.setAdapter(ad2);

        //Adding events to the list for which user wants to register....

        ls1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                events_registered.add(events.get(i).toString());
                events.remove(i);
                ArrayAdapter ad1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, events);
                ArrayAdapter ad2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, events_registered);

                ls1.setAdapter(ad1);
                ls2.setAdapter(ad2);
            }
        });

        ls2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                events.add(events_registered.get(i).toString());
                events_registered.remove(i);

                ArrayAdapter<String> ad1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, events);
                ArrayAdapter<String> ad2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, events_registered);

                ls1.setAdapter(ad1);
                ls2.setAdapter(ad2);
            }
        });

        Button b = (Button) findViewById(R.id.button_reg);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                encoded = "";
                encoded = encodeEvent();
                Toast.makeText(MainActivity.this, encoded,
                        Toast.LENGTH_LONG).show();
                if (encoded == null)
                    Toast.makeText(MainActivity.this, "No event added",
                            Toast.LENGTH_LONG).show();

                SharedPreferences prefs = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("Events Registered", encoded);
                edit.commit();


            }
        });






       /*
        SharedPreferences prefs=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=prefs.edit();

        Set<String> set = new HashSet<String>();
        set.addAll(events_registered);
        edit.putStringSet("Event", set);
        edit.commit();*/




/*      FOR DATA RETRIVAL.......
        Set<String> set = prefs.getStringSet("yourKey", null);
        List<String> sample=new ArrayList<String>(set);

        */


    }


    private String encodeEvent() {
        int size = events_registered.size();
        String s = "";
        if (size != 0) {
            for (int i = 0; i < size; i++)
                s = s + events_registered.get(i) + "$";
            return s;

        }
        return null;
    }


    private void decoder() {


        String temp = "";
        char ch;

        SharedPreferences sp = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        if (sp.contains("Events Registered")) {
            encoded = sp.getString("Events Registered", null);

            int length = encoded.length();
            for (int i = 0; i < length; i++) {

                ch = encoded.charAt(i);
                if (ch != '$')
                    temp = temp + ch;
                else {
                    events_registered.add(temp);
                    temp = "";
                }


            }
        }


    }


    private void createEventsLeft() {

        int length = Events.length;
        for (int i = 0; i < length; i++) {
            if (!search(i))
                events.add(Events[i]);
        }


    }


    private boolean search(int i) {
        int length_registered = events_registered.size();
        for (int j = 0; j < length_registered; j++)
            if (Events[i].equals(events_registered.get(j)))
                return true;

        return false;
    }
}





