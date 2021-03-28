package ses.attendance_system_teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class AddSessionActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        email = findViewById(R.id.et_location);
        reset = findViewById(R.id.btn_submit);
        auth = FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String send_email = email.getText().toString();
                if (send_email.equals("")) {
                    Toast.makeText(AddSessionActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    auth.sendPasswordResetEmail(send_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddSessionActivity.this, "Please Check Your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddSessionActivity.this, LoginActivity.class));
                            }else{
                                String error =  task.getException().getMessage();
                                Toast.makeText(AddSessionActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}