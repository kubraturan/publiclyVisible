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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

        private DatabaseReference dbreference;
        private DatabaseReference userRef;
        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;
        private EditText etMail, etPassword;
        private Button btSignIn;
        private TextView tvKaydol;
        private ProgressBar pbLogin;


        @Override
        protected void onStart() {
            super.onStart();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // User is signed in
                String id = user.getUid();
                userRef = dbreference.child(id);
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Intent intent = new Intent(SignInActivity.this,MainActivity.class); //bu kısım patlamaması sonra yönlendirme için   //Kullanıcı giriş yapmışsa
                        Toast.makeText(getApplicationContext(),"Zaten giriş yapıldı.",Toast.LENGTH_SHORT); //Giriş yapılmış                         //Geçici olarak Main act'e yönlendiriliyor.
                        startActivityForResult(intent,0); //<<

                        //Bu kısım login olmuş kullanıcı tekrar uygulamayı açtığında uygulamanın crash olmasına sebep
                        //etMail.setText(dataSnapshot.child("email").getValue().toString());
                        //etPassword.setText(dataSnapshot.child("password").getValue().toString());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }


        @Override

        protected void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_in);

            dbreference= FirebaseDatabase.getInstance().getReference("users");
            mAuth = FirebaseAuth.getInstance();
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser  user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        // User is signed in

                        etMail.setText(user.getEmail());
                        etPassword.setText(dbreference.child(user.getUid()).child("password").getKey());
                    }

                } };
            //pbLogin = (ProgressBar) findViewById(R.id.pbLogin);
            tvKaydol=(TextView) findViewById(R.id.tvKaydol);
            tvKaydol.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent registerIntent=new Intent(SignInActivity.this,RegisterEmail.class);
                    SignInActivity.this.startActivity(registerIntent);
                }
            });
            etMail = (EditText) findViewById(R.id.etMail);
            etPassword = (EditText) findViewById(R.id.etPassword);
            btSignIn = (Button) findViewById(R.id.btSignIn);
            btSignIn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(etMail.getText().toString().trim())){
                        etMail.requestFocus();
                        etPassword.setError("Lütfen Kullanıcı Adı veya Email Adresinizi Giriniz");
                        return;
                    }
                    else if(TextUtils.isEmpty(etPassword.getText().toString())){
                        etPassword.requestFocus();
                        etPassword.setError("Lütfen Şifrenizi Giriniz.");
                        return;
                    }


                    //todo:internet baglantısı olmadıgı zaman kontrol etmek için -done
                    ConnectivityManager cm =
                            (ConnectivityManager)SignInActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

                    final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                    mAuth.signInWithEmailAndPassword(etMail.getText().toString().trim(), etPassword.getText().toString().trim()).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if( isConnected){
                                if (task.isSuccessful()) {


                                    Intent intent = new Intent(SignInActivity.this, UserInformation.class);
                                    startActivity(intent);
                                    finish();
                          }
                                else {
                                    etMail.setText("");
                                    etPassword.setText("");
                                    Toast.makeText(getApplicationContext(), "Kullanıcı Adı veya Şifre Hatalı  Tekrar Deneyin", Toast.LENGTH_SHORT).show();

                                }}
                            else if(isConnected==false ){


                                Toast.makeText(getApplicationContext(), "Internet Bağlantınızı Kontrol Edin", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                }});

 }

    }


