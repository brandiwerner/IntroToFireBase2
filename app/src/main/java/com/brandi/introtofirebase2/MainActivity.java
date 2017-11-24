package com.brandi.introtofirebase2;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.brandi.introtofirebase2.R.id.EventData_List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity Data";

    // This will hold our collection of EventData Objects that will be printed to the screen
    final List<EventData> events = new ArrayList<EventData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get a reference to our Events
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        // Attach a listener to read the data at our posts reference
        ref.child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(MainActivity.this, "The onDataChange ", Toast.LENGTH_LONG).show();
                //showData(dataSnapshot);

                // get all of the children at this level.
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                // Iterate over the collection called children
                // Each time you get to a child put it in the collection variable
                for(DataSnapshot child : children){
                    // Returned as a java object
                    EventData value = child.getValue(EventData.class);
                    events.add(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Display a toast Error message
                Log.d(TAG, " Error ");
                Toast.makeText(MainActivity.this, "The read failed: " + databaseError.getCode() , Toast.LENGTH_LONG).show();
            }
        });
    }

    // Make array adapter to show results
    ListView listview = (ListView) findViewById(EventData_List);
    ListAdapter eventAdapter = new ArrayAdapter<EventData>(listview.getContext(), android.R.layout.simple_list_item_1, events);
    listview.setAdapter(eventAdapter);

    // Set this event list in the fragment
    //setListAdapter(eventAdapter);

   /** private void showData(DataSnapshot dataSnapshot) {

        // Shake hand with each of them
        for(DataSnapshot ds : children){
            EventData eventData = new EventData();
            eventData.setEventDate(ds.getValue(EventData.class).getEventDate());
            eventData.setEventDesc(ds.getValue(EventData.class).getEventDesc());
            eventData.setEventLocation(ds.getValue(EventData.class).getEventLocation());
            eventData.setEventName(ds.getValue(EventData.class).getEventName());
            eventData.setOrgName(ds.getValue(EventData.class).getOrgName());

            // display all the information
            Log.d(TAG, "showData: Org Name :: " + eventData.getOrgName());
            Log.d(TAG, "showData: Event Name :: " + eventData.getEventName());
            Log.d(TAG, "showData: Event Date :: " + eventData.getEventDate());
            Log.d(TAG, "showData: Event Location :: " + eventData.getEventLocation());
            Log.d(TAG, "showData: Event Description :: " + eventData.getEventDesc());

            ArrayList<String> array = new ArrayList<>();
            array.add(eventData.getOrgName());
            array.add(eventData.getEventName());
            array.add(eventData.getEventDate());
            array.add(eventData.getEventLocation());
            array.add(eventData.getEventDesc());
        }
    }**/
}