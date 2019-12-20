package com.example.rideapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button signIn;
    EditText phoneNumber;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    EditText verfiyNumberET;
    Button verifyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        signIn = findViewById(R.id.signInBtn);
        signIn.setOnClickListener(this);
        phoneNumber = findViewById(R.id.phoneNumberTV);

        verfiyNumberET = findViewById(R.id.verifyNumberET);
        verifyBtn = findViewById(R.id.verifyBtn);

        verifyBtn.setOnClickListener(this);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
                if(code!=null){
                    verifyVerificationCode(code);
                }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInBtn:
                otp();
                break;
            case R.id.verifyBtn:
                String code = verfiyNumberET.getText().toString();
                verifyVerificationCode(code);
                default:
                    break;
        }
    }

    public void otp(){
        String mobileNumber = phoneNumber.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+1"+mobileNumber,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

public void verifyVerificationCode(String code){
    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

    //signing the user
    signInWithPhoneAuthCredential(credential);
}


public void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Successfull",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
}
}
