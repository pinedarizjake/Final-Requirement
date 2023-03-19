package com.example.calendarplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    TextView textView;
    FirebaseUser user;

    private ListView reminderListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);
        user = auth.getCurrentUser();
        initWidgets();
        loadFromDBtoMemory();
        setReminderAdapter();
        setOnClickListener();


        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image_1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_5, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_6, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_7, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_8, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_9, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_10, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_11, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image_12, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);



        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else {
            textView.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();

            }
        });
    }


    private void initWidgets()
    {
        reminderListView = findViewById(R.id.reminderListView);
    }

    private void loadFromDBtoMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateReminderListArray();
    }
    private void setReminderAdapter()
    {
        ReminderAdapater reminderAdapater = new ReminderAdapater(getApplicationContext(), Reminder.nonDeletedNotes());
        reminderListView.setAdapter(reminderAdapater);
    }

    private void setOnClickListener()
    {
        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Reminder selectedNote = (Reminder) reminderListView.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), ReminderDetailActivity.class);
                editNoteIntent.putExtra(Reminder.REMINDER_EDIT_EXTRA, selectedNote.getId());
                startActivity(editNoteIntent);
            }
        });
    }

    public void newReminder(View view)
    {
        Intent newReminderIntent = new Intent(this, ReminderAdapater.class);
        startActivity(newReminderIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setReminderAdapter();
    }
}
