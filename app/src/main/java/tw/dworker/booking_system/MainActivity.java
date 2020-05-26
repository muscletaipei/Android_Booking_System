package tw.dworker.booking_system;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOGIN = 100; //回傳的值
    boolean logon = false; //進入主頁前判斷是否已登入
    String[] functions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //判斷login回傳的值
        if (!logon){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_LOGIN);
        }
        //判斷login回傳的值


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //recycler
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter
        FunctionAdapter adapter = new FunctionAdapter(this);
        recyclerView.setAdapter(adapter);
    }
    //ViewHolder 類別
    public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder> {
        @NonNull
        @Override
        public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull IconHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class IconHolder extends RecyclerView.ViewHolder{
            ImageView iconImage;
            TextView nameText;

            public IconHolder(@NonNull View itemView) {
                super(itemView);
                iconImage = itemView.findViewById(R.id.item_icon);
                nameText = itemView.findViewById(R.id.item_name);

            }
        }
    }

    //回傳的值：result code是否正確
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN){
            if (resultCode != RESULT_OK) {
                finish();
            }
/*            else{
                Log.i("Intent Data", data.getDataString());
            }*/
        }

    }
    //回傳的值：result code是否正確

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

/*    public void getBookedList(DataSnapshot dataSnapshot){
        DataSnapshot lab1 = dataSnapshot.child("lab1");
        DataSnapshot lab2 = dataSnapshot.child("lab2");
        Calendar cal = Calendar.getInstance();
        String thisYear = String.valueOf(cal.get(Calendar.YEAR));
        String thisMonth = String.valueOf(cal.get(Calendar.MONTH)+1);
        String thisDay = String.valueOf(cal.get(Calendar.DATE));
        Log.i("thisYear/thisMonth",thisYear + "/" + thisMonth);
        if(lab1.hasChild(thisYear)){
            if(lab1.child(thisYear).hasChild(thisMonth)){
                Iterable<DataSnapshot> bookedDate = lab1.child(thisYear).child(thisMonth).getChildren();
                for(DataSnapshot date: bookedDate){
                    Log.i("Booked:", date.getKey().toString() + date.getValue().toString());
                };

            }
        }

        for(DataSnapshot data: dataSnapshot.getChildren()) {
            Log.i("equipment=", data.getValue().toString());
        }
    }*/
}
