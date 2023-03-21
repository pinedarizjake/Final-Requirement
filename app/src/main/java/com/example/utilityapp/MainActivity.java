package com.example.utilityapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDBToMemory();
        setNoteAdapter();
        setOnClickListener();


        ImageSlider imageSlider;


        imageSlider =findViewById(R.id.imageSlider);

        ArrayList<SlideModel> imageList = new ArrayList<>();


        imageList.add(new SlideModel(R.drawable.image_1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_2,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_3,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_4,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_5,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_6,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_7,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_8,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_9,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_10,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_11,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.image_12,ScaleTypes.FIT));


        imageSlider.setImageList(imageList, ScaleTypes.FIT);
    }


    private void initWidgets()
    {
        noteListView = findViewById(R.id.noteListView);
    }

    private void loadFromDBToMemory()
    {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();
    }

    private void setNoteAdapter()
    {
        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), Note.nonDeletedNotes());
        noteListView.setAdapter(noteAdapter);
    }


    private void setOnClickListener()
    {
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Note selectedNote = (Note) noteListView.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), NoteDetailActivity.class);
                editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
                startActivity(editNoteIntent);
            }
        });
    }


    public void newNote(View view)
    {
        Intent newNoteIntent = new Intent(this, NoteDetailActivity.class);
        startActivity(newNoteIntent);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setNoteAdapter();
    }
}