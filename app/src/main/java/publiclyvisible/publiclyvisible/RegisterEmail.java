package publiclyvisible.publiclyvisible;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//private FirebaseAuth mAuth;

public class RegisterEmail extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText etMail;
    private EditText etPassword;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        mAuth=FirebaseAuth.getInstance();
        etMail= (EditText) findViewById(R.id.etMail);
        etPassword= (EditText) findViewById(R.id.etPassword);
        Button btMailRegister = (Button) findViewById(R.id.btRegisterMailComplete);
        btMailRegister.setOnClickListener(this);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void createAccount(String email, String password){
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterEmail.this, "Kayıt Başarılı",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterEmail.this, "Kayıt İşlemi Başarısız",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    /*private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }*/
    private boolean validateForm() {
        boolean valid = true;

        String email = etMail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etMail.setError("Gerekli.");
            valid = false;
        } else {
            etMail.setError(null);
        }
        if (Patterns.EMAIL_ADDRESS.matcher(etMail.toString()).matches()){

        }
        else{
            etMail.setError("Mail biçimi yanlış.");
            valid=false;
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Gerekli.");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }


    @Override
    public void onClick(View view) {

            try {
                createAccount(etMail.getText().toString(), etPassword.getText().toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

}
