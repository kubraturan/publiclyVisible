package publiclyvisible.publiclyvisible;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInformation extends AppCompatActivity {
    private EditText etName;
    private EditText etSurname;
    private EditText etPhoneNumber;
    private EditText etBorn;
    private Button btSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etBorn = (EditText) findViewById(R.id.etBorn);
        btSave = (Button) findViewById(R.id.btSave);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("");

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.setValue(etName.toString());
            }
        });
    }
}
