package io.github.douglasjunior.androidSimpleTooltip.sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        finish();

        new SimpleTooltip.Builder(this)
                .anchorView(toolbar)
                .text(getString(R.string.btn_other_activity))
                .gravity(Gravity.BOTTOM)
                .transparentOverlay(false)
                .backgroundColor(Color.RED)
                .arrowColor(Color.RED)
                .build()
                .show();

        Toast.makeText(this, "Other Actvity opened and closed before the tooltip open!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
