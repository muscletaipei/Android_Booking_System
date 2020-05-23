package tw.dworker.booking_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText mAccount;
    private EditText mPassword;
    Button mButton1,mButton2;
    CheckBox mCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAccount = findViewById(R.id.mAccount);
        mPassword = findViewById(R.id.mPassword);
        mCheck = findViewById(R.id.mCheck);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);

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


    }

    public void login(View view){
        String userid = mAccount.getText().toString();
        String passwd = mPassword.getText().toString();
        if ("jack".equals(userid) && "1234".equals(passwd)) {
            setResult(RESULT_OK);
            Toast.makeText(this,"歡迎光臨，登入成功。",Toast.LENGTH_LONG).show();
            finish();
        }else {
            new AlertDialog.Builder(this)
                    .setTitle("登入結果")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK",null)
                    .show();

        }


    }
    public void quit(View view){
        finish();


    }


}
