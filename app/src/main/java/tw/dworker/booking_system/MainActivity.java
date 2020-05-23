package tw.dworker.booking_system;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOGIN = 100;
    boolean logon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!logon){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,REQUEST_LOGIN);
        }

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN){
            if (resultCode != RESULT_OK) {
                finish();
            }else{
                Log.i("Intent Data", data.getDataString());
            }
        }

    }

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

    public void getBookedList(DataSnapshot dataSnapshot){
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
    }
}
