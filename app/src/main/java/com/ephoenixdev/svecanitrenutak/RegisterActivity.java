package com.ephoenixdev.svecanitrenutak;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ephoenixdev.svecanitrenutak.models.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btnOK;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressBar progressBar;
    private Button btnLogIn;
    private EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnOK = (Button) findViewById(R.id.buttonRegOK);
        editTextEmail = (EditText) findViewById(R.id.editTextRegEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextRegPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBarReg);
        btnLogIn = (Button) findViewById(R.id.buttonRegLogIn);
        editTextPhone = (EditText) findViewById(R.id.editTextRegPhone);

        mAuth = FirebaseAuth.getInstance();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim());
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {
        Log.d("Registracija", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        btnOK.setVisibility(View.GONE);
        btnLogIn.setVisibility(View.GONE);
        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Log.d("Registracija", "Registracija uspesna!");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            sendEmailVerification();
                            createUser();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w("Registracija", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registracija neuspesna!",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                            btnOK.setVisibility(View.VISIBLE);
                            btnLogIn.setVisibility(View.VISIBLE);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void createUser() {

        DatabaseReference databaseUser;
        FirebaseUser currentUser = mAuth.getCurrentUser();
        databaseUser = FirebaseDatabase.getInstance().getReference("User");

        String phoneNumber = editTextPhone.getText().toString();
        boolean isAdmin = false;

        UserModel um = new UserModel(currentUser.getUid().toString(),phoneNumber,isAdmin);
        databaseUser.child(currentUser.getUid()).setValue(um);

    }

    private void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    private void showProgressDialog() {

        progressBar.setVisibility(View.VISIBLE);


    }

    private boolean validateForm() {
        boolean valid = true;

        String email = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Required.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    private void sendEmailVerification() {

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("RegisterActivity", "sendEmailVerification", task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
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
        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
