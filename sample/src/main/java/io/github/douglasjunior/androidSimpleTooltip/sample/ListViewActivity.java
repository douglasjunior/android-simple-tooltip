package io.github.douglasjunior.androidSimpleTooltip.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Created by Douglas on 31/12/2016.
 */

public class ListViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, 0,
                Arrays.asList("Iron Man", "Spider Man", "Captain America", "Ant Man", "Hulk", "Thor"));

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        View header = getLayoutInflater().inflate(R.layout.listview_header, null);
        listView.addHeaderView(header);

        findViewById(R.id.btn_tooltip1).setOnClickListener(this);
        findViewById(R.id.btn_tooltip2).setOnClickListener(this);
        findViewById(R.id.btn_tooltip3).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tooltip1){
            View header = listView.findViewById(R.id.header1);
            new SimpleTooltip.Builder(this)
                    .anchorView(header)
                    .text("This is Header 1")
                    .gravity(Gravity.TOP)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();
        }
        if (v.getId() == R.id.btn_tooltip2){
            View header = listView.findViewById(R.id.header2);
            new SimpleTooltip.Builder(this)
                    .anchorView(header)
                    .text("This is Header 2")
                    .gravity(Gravity.TOP)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();
        }
        if (v.getId() == R.id.btn_tooltip3){
            View header = listView.findViewById(R.id.header3);
            new SimpleTooltip.Builder(this)
                    .anchorView(header)
                    .text("This is Header 3")
                    .gravity(Gravity.TOP)
                    .animated(true)
                    .transparentOverlay(false)
                    .build()
                    .show();
        }
    }
}
