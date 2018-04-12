package publiclyvisible.publiclyvisible;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btMailRegister = (Button) findViewById(R.id.btRegisterOpen);
        btMailRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),RegisterActivity.class);
                startActivityForResult(intent,0);
            }
        });

        Button btSignInOpen = (Button) findViewById(R.id.btSignInOpen);
        btSignInOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SignInActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }
}
