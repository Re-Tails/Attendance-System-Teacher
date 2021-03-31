package ses.attendance_system_teacher;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class AddSessionActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText et_location;
    Button btn_submit;
    AutoCompleteTextView et_subject;
    TextInputLayout til_subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_session);


        et_location = findViewById(R.id.et_location);
        btn_submit = findViewById(R.id.btn_submit);
        et_subject = findViewById(R.id.tv_subject);
        til_subject = findViewById(R.id.til_subject);
        auth = FirebaseAuth.getInstance();

        String items[] = {"Option 1", "Option 2", "Option 3", "Option 4"};
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.add_session_subject_list_item, items);
        ((AutoCompleteTextView)til_subject.getEditText()).setAdapter(adapter);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String send_email = et_location.getText().toString();
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