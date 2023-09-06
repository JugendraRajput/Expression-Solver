package com.jdgames.expressionsolver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jdgames.expressionsolver.Pojo.HistoryItemParse;
import com.jdgames.expressionsolver.Interface.ListViewClickListener;
import com.jdgames.expressionsolver.R;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryItemParse> {
    private final Context context;
    private final List<HistoryItemParse> items;
    ListViewClickListener listener;

    public HistoryAdapter(Context context, List<HistoryItemParse> items, ListViewClickListener listener) {
        super(context, R.layout.history_view);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public HistoryItemParse getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.history_view, null);

        TextView titleTextView = view.findViewById(R.id.item_title);
        ListView listView = view.findViewById(R.id.innerListView);
        listView.setOnItemClickListener((p, v, i, id) -> {
            listener.onClick(items.get(position).getItems().get(i).getExpression());
        });

        titleTextView.setText(items.get(position).getDate());
        ListAdapter listAdapter = new ListAdapter(getContext(), items.get(position).getItems());
        listView.setAdapter(listAdapter);

        return view;
    }
}
