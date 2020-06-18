package tw.dworker.booking_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    PieChart pieChart;
    ArrayList<PieEntry> pieEntries;
    ArrayList<RegionalTaskData> regionalTaskDataArrayList = new ArrayList<>();
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        pieChart = findViewById(R.id.pieChart);
        pieEntries = new ArrayList<>();

        fillRegionalTaskArrayList();
        for (int i =0; i < regionalTaskDataArrayList.size();i++){
            String region = regionalTaskDataArrayList.get(i).getRegion();
            int task = regionalTaskDataArrayList.get(i).getTask();
            pieEntries.add(new PieEntry(task,region));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Regional Task");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setValueTextSize(16);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        Legend legend = pieChart.getLegend();
        legend.setTextSize(13);
        legend.setDrawInside(false);

        legend.setTextColor(getResources().getColor(R.color.colorPrimary));
        legend.setWordWrapEnabled(true);
        pieChart.animateXY(2000,2000);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int x =pieChart.getData().getDataSetForEntry(e).getEntryIndex((PieEntry)e);
                String region = regionalTaskDataArrayList.get(x).getRegion();
                String task = NumberFormat.getCurrencyInstance().format(regionalTaskDataArrayList.get(x).getTask());
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);
                builder.setCancelable(true);
                View view = LayoutInflater.from(TaskListActivity.this).inflate(R.layout.regional_task_layout, null);
                TextView regionTxtView = view.findViewById(R.id.region);
                TextView taskTxtView = view.findViewById(R.id.chart_task);
                regionTxtView.setText(region);
                taskTxtView.setText(task);
                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.show();

            }

            @Override
            public void onNothingSelected() {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.recyclerView_task);

        new FirebaseDatabaseHelper().readTasks(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Task> tasks, List<String> keys) {
                new RecyclerViewTask().setConfig(mRecyclerView,TaskListActivity.this,tasks,keys);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
    private void fillRegionalTaskArrayList(){
        regionalTaskDataArrayList.add(new RegionalTaskData("Joe",553));
        regionalTaskDataArrayList.add(new RegionalTaskData("Clark",640));
        regionalTaskDataArrayList.add(new RegionalTaskData("Luna",420));
        regionalTaskDataArrayList.add(new RegionalTaskData("Frank",256));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tasklist_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_task:
                startActivity(new Intent(this, AddTaskActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
