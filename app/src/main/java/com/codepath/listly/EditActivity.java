package com.codepath.listly;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    private ListlyDatabase mDB;
    private long rowID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent editIntent = getIntent();
        Bundle bundle = (Bundle)editIntent.getExtras();

        mDB = new ListlyDatabase(this);
        rowID = bundle.getLong("id");

        EditText editText = (EditText)findViewById(R.id.editText2);
        String str = bundle.getString("listitem");
        editText.setText(str);
        editText.setSelection(str.length());


        Button editButton = (Button)findViewById(R.id.button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.editText2);
                String str = editText.getText().toString();
                mDB.updateItem(rowID, str);
                Toast.makeText(getApplicationContext(), "List Item updated",Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        });


    }

}
