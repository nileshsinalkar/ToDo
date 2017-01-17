package com.example.nilesh.todo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBhelper dBhelper;
    ListView listview;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBhelper=new DBhelper(this);
        listview=(ListView)findViewById(R.id.listview);
        loadtasklist();

    }

    private void loadtasklist() {
        ArrayList<String> tasklist=dBhelper.gettasklist();
        if(adapter==null){
            adapter=new ArrayAdapter<String>(this, R.layout.row,R.id.textView);
            listview.setAdapter(adapter);
        }else{
            adapter.clear();
            adapter.addAll(tasklist);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.plus:
                final EditText editext = new EditText(this);
                AlertDialog dialog=new AlertDialog.Builder(this)
                        .setTitle("Add new task")
                        .setMessage("What do u want to do next")
                        .setView(editext)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String task=String.valueOf(editext.getText());
                                dBhelper.insertnewtask(task);

                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deletetask(View view){
        View parent=(View)view.getParent();
        TextView textView=(TextView)findViewById(R.id.textView);
        String task=String.valueOf(textView.getText());
        dBhelper.deletetask(task);
        loadtasklist();
    }
}
