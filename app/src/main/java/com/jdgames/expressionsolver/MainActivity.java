package com.jdgames.expressionsolver;

import static com.jdgames.expressionsolver.Utils.Config.BASE_URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jdgames.expressionsolver.Adapter.RecyclerViewAdapter;
import com.jdgames.expressionsolver.Interface.ApiService;
import com.jdgames.expressionsolver.Interface.ListViewClickListener;
import com.jdgames.expressionsolver.Pojo.Expression;
import com.jdgames.expressionsolver.Pojo.ResultParse;
import com.jdgames.expressionsolver.RoomDB.DatabaseClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ListViewClickListener {

    ApiService apiService = null;
    EditText editText;
    Button button;
    int requestCount = 0;
    int finishCount = 0;

    ArrayList<ResultParse> resultParses = new ArrayList<>();
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        recyclerViewAdapter = new RecyclerViewAdapter(this, resultParses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(recyclerViewAdapter);

        findViewById(R.id.textView).setOnClickListener(v -> OpenHistory());

        button.setOnClickListener(v -> {
            String str = editText.getText().toString().trim();
            if (str.isEmpty()) {
                return;
            }
            resultParses.clear();
            findViewById(R.id.resultsTitle).setVisibility(View.GONE);
            recyclerViewAdapter.notifyDataSetChanged();
            String[] lines = str.split("\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    System.out.println(line);
                    requestCount++;
                    getResult(line.trim());
                }
            }
            button.setEnabled(false);
        });
    }

    public void getResult(String expression) {
        apiService.getResult(expression)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        finishCount++;
                        if (response.isSuccessful()) {
                            String result = response.body();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String date = sdf.format(new Date());
                            Expression newExpression = new Expression();
                            newExpression.setExpression(expression);
                            newExpression.setResult(result);
                            newExpression.setDate(date);
                            DatabaseClient.getInstance(getApplicationContext())
                                    .getAppDatabase().expressionDao()
                                    .insert(newExpression);
                            resultParses.add(new ResultParse(expression, result));
                        }
                        if (requestCount == finishCount) {
                            runOnUiThread(() -> {
                                button.setEnabled(true);
                                findViewById(R.id.resultsTitle).setVisibility(View.VISIBLE);
                                recyclerViewAdapter.notifyDataSetChanged();
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        finishCount++;
                        if (requestCount == finishCount) {
                            runOnUiThread(() -> {
                                button.setEnabled(true);
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                findViewById(R.id.resultsTitle).setVisibility(View.VISIBLE);
                                recyclerViewAdapter.notifyDataSetChanged();
                            });
                        }
                    }
                });
    }

    public void OpenHistory() {
        HistoryBottomDialog historyBottomDialog = new HistoryBottomDialog();
        historyBottomDialog.show(getSupportFragmentManager(), historyBottomDialog.getTag());
        historyBottomDialog.addListener(MainActivity.this);
    }

    @Override
    public void onClick(String expression) {
        editText.setText(expression);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_history) {
            OpenHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}