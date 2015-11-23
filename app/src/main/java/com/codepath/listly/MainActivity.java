package com.codepath.listly;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private ListView myListView;
    private ListlyDatabase mDB;
    private SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDB = new ListlyDatabase(this);

        String[] columns = new String[] {ListlyDatabase.COL_ITEM};
        int[] uiTo = new int[]{android.R.id.text1};
        cursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,null,columns,uiTo,2);


        myListView = (ListView)findViewById(R.id.listView);
        myListView.setAdapter(cursorAdapter);

        Button addButton = (Button)findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.editText);
                String str = editText.getText().toString();
                mDB.insertItem(str);
                editText.setText("");
                //items.add(str);
                cursorAdapter.swapCursor(mDB.getAllListItems());
                cursorAdapter.notifyDataSetChanged();
                return;
            }
        });

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                items.remove(position);
                mDB.deleteItem(id);
                cursorAdapter.swapCursor(mDB.getAllListItems());
                cursorAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                //intent.putExtra("id", id);
                Cursor list_cursor = (Cursor)parent.getItemAtPosition(position);
                String str = list_cursor.getString(list_cursor.getColumnIndex(ListlyDatabase.COL_ITEM));
                //intent.putExtra("listitem",str);
                Bundle value = new Bundle();
                value.putLong("id", id);
                value.putString("listitem", str);
                intent.putExtras(value);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        cursorAdapter.swapCursor(mDB.getAllListItems());
        cursorAdapter.notifyDataSetChanged();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
