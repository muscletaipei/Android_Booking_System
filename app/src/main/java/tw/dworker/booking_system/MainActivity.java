package tw.dworker.booking_system;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOGIN = 100; //回傳的值
    private static final String TAG = MainActivity.class.getSimpleName();
    boolean logon = false; //進入主頁前判斷是否已登入
    private List<Function> functions;
    MediaPlayer mediaPlayer; //Background music
    //    String[] functions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.loading_main_pb).setVisibility(View.GONE);

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
        setupFunctions();

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        //set different Layout
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));  //style 1
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));  //style 2

        //adapter
//        FunctionAdapter adapter = new FunctionAdapter(this);
        IconAdapter adapter = new IconAdapter();
        recyclerView.setAdapter(adapter);

        //Background music
        mediaPlayer = MediaPlayer.create(this,R.raw.dreamwalking);
        mediaPlayer.setLooping(true);
        //Background music
    }

    private void setupFunctions() {
        functions = new ArrayList<>();
        String[] funcs = getResources().getStringArray(R.array.functions);
        functions.add(new Function(funcs[0],R.drawable.home));
        functions.add(new Function(funcs[1],R.drawable.infomation));
        functions.add(new Function(funcs[2],R.drawable.lab_booking_2));
        functions.add(new Function(funcs[3],R.drawable.room_booking_1));
        functions.add(new Function(funcs[4],R.drawable.task_1));
        functions.add(new Function(funcs[5],R.drawable.exit));
    }

    //ViewHolder 類別
    public class IconAdapter extends RecyclerView.Adapter<IconAdapter.IconHolder> {
        @NonNull
        @Override
        public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_icon,parent,false);
            return new IconHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IconHolder holder, int position) {
            final Function function = functions.get(position);
            holder.nameText.setText(function.getName());
            holder.iconImage.setImageResource(function.getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked(function);
                }
            });
        }

        @Override
        public int getItemCount() {

            return functions.size();
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

    private void itemClicked(Function function) {
        Log.d(TAG, "itemClicked:" + function.getName());
        switch (function.getIcon()){
            case R.drawable.home:
                break;
            case R.drawable.infomation:
                break;
            case R.drawable.lab_booking_2:
                Intent lab = new Intent(this,LabActivity.class);
                startActivity(lab);
                break;
            case R.drawable.room_booking_1:
                Intent room = new Intent(this,RoomActivity.class);
                startActivity(room);
                break;
            case R.drawable.task_1:
                Intent task = new Intent(this,TaskListActivity.class);
                startActivity(task);
                break;
            case R.drawable.exit:
                finish();
                break;
        }
    }

    //回傳的值：result code是否正確
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN){
            Toast
                    .makeText(this, "Welcome, login successfully.",Toast.LENGTH_SHORT)
                    .show();
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

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
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
