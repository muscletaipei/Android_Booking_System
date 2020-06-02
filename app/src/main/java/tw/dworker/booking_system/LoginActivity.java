package tw.dworker.booking_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName(); //TAG
    private EditText mAccount;
    private EditText mPassword;
    CheckBox mCheck;
    private CheckBox mCheck_re_userid;
    ViewFlipper v_flipper; //sider show

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAccount = findViewById(R.id.mAccount);
        mPassword = findViewById(R.id.mPassword);
        mCheck = findViewById(R.id.mCheck);
        mCheck_re_userid = findViewById(R.id.mCheck_re_userid);

        //Fragment
/*        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.container_news, NewsFragment.getInstance());
        fragmentTransaction.commit();*/

        //set SharePreferences
        //存入 data
        getSharedPreferences("Booked", MODE_PRIVATE)
                .edit()
                .putInt("LEVEL", 3)
                .putString("NAME", "Joe")
                .commit();
        //寫入 data
        int level = getSharedPreferences("Joe", MODE_PRIVATE)
                .getInt("LEVEL", 0);
        Log.d(TAG, "onCreate:" + level);
        //讀取data
        mCheck_re_userid.setChecked(
                getSharedPreferences("Booked",MODE_PRIVATE)
                .getBoolean("REMEMBER_USERID",false));

        mCheck_re_userid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences("Booked", MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_USERID",isChecked)
                        .apply();
            }
        });
        String userid = getSharedPreferences("Booked",MODE_PRIVATE)
                .getString("USERID","");
        mAccount.setText(userid);
        //set SharePreferences

        //sider show
        int image[] = {R.drawable.info_1, R.drawable.info_2};
        v_flipper = findViewById(R.id.v_flipper);
        //for loop
/*        for (int m=0; m<image.length; m++){
            flipperImages(image[m]);*/
        //I refer foreach
        for (int images : image ){
            flipperImages(images);
        }
        //sider show
        //判斷顯示密碼方法
        mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCheck.isChecked()){
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //判斷顯示密碼方法
    }
    //sider show
    public void flipperImages(int image){
        ImageView imagview = new ImageView(this);
        imagview.setBackgroundResource(image);
        v_flipper.addView(imagview);
        v_flipper.setFlipInterval(4000);//4sec
        v_flipper.setAutoStart(true);
        //animation
        v_flipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }

    public void login(View view){
        final String userid = mAccount.getText().toString();
        final String passwd = mPassword.getText().toString();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userRefer = database.getReference("users");
//        userRefer.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot data: dataSnapshot.getChildren()) {
//                    Log.i("Firebase Connected", " OK" + data.getValue().toString());
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        Log.i(" login"," OK");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("users").child(userid).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String pw = (String) dataSnapshot.getValue();
                        Log.i("Firebase Connected"," OK");
                        if (pw.equals(passwd)){
                            boolean remember = getSharedPreferences("Booked",MODE_PRIVATE)
                                    .getBoolean("REMEMBER_USERID",false);
                            if (remember) {
                                // getSharePreferences: save user id 寫入先前已登入成功的userid
                                getSharedPreferences("Booked", MODE_PRIVATE)
                                        .edit()
                                        .putString("USERID", userid)
                                        .apply();
                            }
                            //  getSharePreferences: save user id 寫入先前已登入成功的userid
                            setResult(RESULT_OK);

//                            Log.i("Firebase Connected"," OK-2");
//                            DatabaseReference equipmentsRefer = database.getReference("equipments");
//                            equipmentsRefer.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                            intent.putExtra("equipments", (Parcelable) equipmentsRefer);
//                            setResult(RESULT_OK, intent);
                            finish();
                        }else {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Login result")
                                    .setMessage("Login failed")
                                    .setPositiveButton("Confirm",null)
                                    .show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    public void quit(View view){
        finish();

    }
// local端判斷登入系統
 /*       if ("jack".equals(userid) && "1234".equals(passwd)) {
            Toast.makeText(this,"歡迎光臨，登入成功。",Toast.LENGTH_LONG).show();
            setResult(RESULT_OK);
            finish();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("登入結果")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK",null)
                    .show();
        }*/

}
