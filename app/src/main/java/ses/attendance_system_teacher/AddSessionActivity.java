package ses.attendance_system_teacher;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ses.attendance_system_teacher.model.Session;

public class AddSessionActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText et_location;
    EditText et_date;
    EditText et_start_time;
    EditText et_end_time;
    Button btn_submit;
    AutoCompleteTextView et_subject;
    TextInputLayout til_subject;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_session);


        et_location = findViewById(R.id.et_location);
        et_date = findViewById(R.id.et_date);
        et_start_time = findViewById(R.id.et_start_time);
        et_end_time = findViewById(R.id.et_end_time);
        btn_submit = findViewById(R.id.btn_submit);
        et_subject = findViewById(R.id.tv_subject);
        til_subject = findViewById(R.id.til_subject);
        databaseReference = FirebaseDatabase.getInstance().getReference("Session");

        String subjects[] = {"Option 1", "Option 2", "Option 3", "Option 4"};
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.add_session_subject_list_item, subjects);
        ((AutoCompleteTextView)til_subject.getEditText()).setAdapter(adapter);
        et_subject.setShowSoftInputOnFocus(false);
        et_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
            }
        });


        DatePickerDialog datePicker = new DatePickerDialog(AddSessionActivity.this, 0);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();

            }
        });
        datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                et_date.setText(dayOfMonth + "/" + month + "/" + year);
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new Session();
                session.setSession_date(et_date.getText().toString());
                session.setSession_location(et_location.getText().toString());
                session.setSession_start_time(et_start_time.getText().toString());
                session.setSession_end_time(et_end_time.getText().toString());
                session.setSession_subject(et_subject.getText().toString());
                databaseReference.push().setValue(session);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(AddSessionActivity.this, "Session added", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddSessionActivity.this, "Failed to add session " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

}