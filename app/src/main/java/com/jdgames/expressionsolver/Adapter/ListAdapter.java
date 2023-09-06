package com.jdgames.expressionsolver.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jdgames.expressionsolver.Pojo.Expression;
import com.jdgames.expressionsolver.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Expression> {
    private final Context context;
    private final List<Expression> items;

    public ListAdapter(Context context, List<Expression> items) {
        super(context, R.layout.list_item);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Expression getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, null);

        TextView titleTextView = view.findViewById(R.id.item_title);
        TextView descriptionTextView = view.findViewById(R.id.item_description);

        titleTextView.setText(items.get(position).getExpression());
        descriptionTextView.setText(items.get(position).getResult());

        return view;
    }
}
