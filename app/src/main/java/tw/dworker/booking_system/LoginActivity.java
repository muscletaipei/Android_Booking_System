package tw.dworker.booking_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName(); //TAG
    EditText mAccount;
    EditText mPassword;
    CheckBox mCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set SharePreferences
/*        getSharedPreferences("Booked", MODE_PRIVATE)
                .edit()
                .putInt("LEVEL",3)
                .putString("NAME","Joe")
                .commit();
        int level = getSharedPreferences("Joe", MODE_PRIVATE)
                .getInt("LEVEL", 0);
        Log.d(TAG, "onCreate:" + level);

        String userid = getSharedPreferences("Booked",MODE_PRIVATE)
                .getString("USERID","");
        mAccount.setText(userid);*/
        //set SharePreferences

        mAccount = findViewById(R.id.mAccount);
        mPassword = findViewById(R.id.mPassword);
        mCheck = findViewById(R.id.mCheck);

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
        Log.i(" login"," OK");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("users").child(userid).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String pw = (String) dataSnapshot.getValue();
                        Log.i("Firebase Connected"," OK");
                        if (pw.equals(passwd)){
                            // save user id 寫入先前已登入成功的userid
/*                            getSharedPreferences("Booked",MODE_PRIVATE)
                                    .edit()
                                    .putString("USERID",userid)
                                    .apply();*/
                            // save user id 寫入先前已登入成功的userid
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
                                    .setTitle("登入結果")
                                    .setMessage("登入失敗")
                                    .setPositiveButton("OK",null)
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
