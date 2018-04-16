package publiclyvisible.publiclyvisible;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//private FirebaseAuth mAuth;

public class RegisterEmail extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText etMail;
    private EditText etPassword;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private DatabaseReference dbRef;
    private static final String TAG = "EmailPassword";
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        mAuth = FirebaseAuth.getInstance();
        etMail = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        userRef = FirebaseDatabase.getInstance().getReference("users");
        Button btMailRegister = (Button) findViewById(R.id.btRegisterMailComplete);
        btMailRegister.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterEmail.this, "Kayıt Başarılı",
                                    Toast.LENGTH_SHORT).show();

                            Intent userInfromation = new Intent(getApplicationContext(), UserInformation.class);
                            startActivity(userInfromation);

                            //updateUI(user);

                            userRef.child(task.getResult().getUser().getUid()).setValue(etMail.getText().toString(), etPassword.getText().toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterEmail.this, "Kayıt İşlemi Başarısız",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }


                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etMail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            etMail.setError("Gerekli.");
            valid = false;
        } else {
            etMail.setError(null);
        }


        if (android.util.Patterns.EMAIL_ADDRESS.matcher(etMail.getText().toString().trim()).matches()) {

        } else {
            etMail.setError("Mail biçimi yanlış.");
            valid = false;
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Gerekli.");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        //todo:internet baglantısı olmadıgı zaman kontrol etmek için -done
        ConnectivityManager cm =
                (ConnectivityManager) RegisterEmail.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        mAuth.signInWithEmailAndPassword(etMail.getText().toString().trim(), etPassword.getText().toString().trim()).addOnCompleteListener(RegisterEmail.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (isConnected) {
                    if (task.isSuccessful()) {


                        Intent intent = new Intent(RegisterEmail.this, UserInformation.class);
                        startActivity(intent);
                        finish();
                    } else {
                        etMail.setText("");
                        etPassword.setText("");
                        Toast.makeText(getApplicationContext(), "Kullanıcı Adı veya Şifre Hatalı  Tekrar Deneyin", Toast.LENGTH_SHORT).show();

                    }
                } else if (isConnected == false) {


                    Toast.makeText(getApplicationContext(), "Internet Bağlantınızı Kontrol Edin", Toast.LENGTH_SHORT).show();

                }

            }
        });
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
