package io.github.douglasjunior.androidSimpleTooltip.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Created by Douglas on 31/12/2016.
 */

public class ListViewFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, 0,
                Arrays.asList("Iron Man", "Spider Man", "Captain America", "Ant Man", "Hulk", "Thor",
                        "Iron Man", "Spider Man", "Captain America", "Ant Man", "Hulk", "Thor",
                        "Iron Man", "Spider Man", "Captain America", "Ant Man", "Hulk", "Thor",
                        "Iron Man", "Spider Man", "Captain America", "Ant Man", "Hulk", "Thor",
                        "Iron Man", "Spider Man", "Captain America", "Ant Man", "Hulk", "Thor"));

        listView = (ListView) getView().findViewById(R.id.listview);
        listView.setAdapter(adapter);

        View header = getActivity().getLayoutInflater().inflate(R.layout.listview_header, null);
        listView.addHeaderView(header);

        getView().findViewById(R.id.btn_tooltip1).setOnClickListener(this);
        getView().findViewById(R.id.btn_tooltip2).setOnClickListener(this);
        getView().findViewById(R.id.btn_tooltip3).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tooltip1){
            View header = listView.findViewById(R.id.header1);
            new SimpleTooltip.Builder(getActivity())
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
            new SimpleTooltip.Builder(getActivity())
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
            new SimpleTooltip.Builder(getActivity())
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
