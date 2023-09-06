package com.jdgames.expressionsolver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jdgames.expressionsolver.Adapter.HistoryAdapter;
import com.jdgames.expressionsolver.Interface.ListViewClickListener;
import com.jdgames.expressionsolver.Pojo.Expression;
import com.jdgames.expressionsolver.Pojo.HistoryItemParse;
import com.jdgames.expressionsolver.RoomDB.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

public class HistoryBottomDialog extends BottomSheetDialogFragment {

    List<Expression> expressions = new ArrayList<>();
    ListView listView;
    TextView bottom;
    ListViewClickListener listener;

    public void addListener(ListViewClickListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_bottom_dialog, container, false);

        bottom = view.findViewById(R.id.bottom);
        listView = view.findViewById(R.id.listView);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            listener.onClick(expressions.get(position).getExpression());
            dismiss();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        expressions = DatabaseClient.getInstance(getContext())
                .getAppDatabase().expressionDao()
                .getAllExpressions();

        ArrayList<String> dates = new ArrayList<>();
        for (Expression expression : expressions) {
            String date = expression.getDate();
            if (!dates.contains(date)) {
                dates.add(date);
            }
        }
        ArrayList<HistoryItemParse> historyItemParses = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            ArrayList<Expression> list = new ArrayList<>();
            for (Expression expr : expressions) {
                if (date.equalsIgnoreCase(expr.getDate())) {
                    list.add(expr);
                }
            }
            historyItemParses.add(new HistoryItemParse(date, list));
        }
        if (expressions.size() > 0) {
            HistoryAdapter historyAdapter = new HistoryAdapter(getContext(), historyItemParses, listener);
            listView.setAdapter(historyAdapter);
            bottom.setText("This application was developed by Jugendra Bhati");
        } else {
            bottom.setText("Nothing in History!");
        }
    }
}

