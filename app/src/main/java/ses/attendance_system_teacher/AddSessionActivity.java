package ses.attendance_system_teacher;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ses.attendance_system_teacher.model.Session;

public class AddSessionActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference sessionDatabaseReference;
    DatabaseReference addedSessionDatabaseReference;
    DatabaseReference subjectDatabaseReference;
    EditText et_location;
    EditText et_date;
    EditText et_start_time;
    EditText et_end_time;
    Button btn_submit;
    AutoCompleteTextView et_subject;
    TextInputLayout til_subject;
    Session session;
    ArrayList<String> subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Hello", "hello");






        firebaseDatabase = FirebaseDatabase.getInstance();
        sessionDatabaseReference = firebaseDatabase.getReference("Session");
        subjectDatabaseReference = firebaseDatabase.getReference("Subject");

        subjects = new ArrayList<String>();
        subjectDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    subjects.add(postSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.add_session_subject_list_item, subjects);

        setContentView(R.layout.activity_add_session);
        til_subject = findViewById(R.id.til_subject);
        et_location = findViewById(R.id.et_location);
        et_date = findViewById(R.id.et_date);
        et_start_time = findViewById(R.id.et_start_time);
        et_end_time = findViewById(R.id.et_end_time);
        btn_submit = findViewById(R.id.btn_submit);
        et_subject = findViewById(R.id.tv_subject);
        
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
                int fixedMonth = month + 1;
                DateTimeFormatter currentFormatter = DateTimeFormatter.ofPattern("d/M/uuuu");
                DateTimeFormatter convertedOutputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String currentDate = dayOfMonth + "/" + fixedMonth + "/" + year;
                LocalDate localDate = LocalDate.parse(currentDate, currentFormatter);
                et_date.setText(localDate.format(convertedOutputFormatter));
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
                session.setSession_code(getRandomSixDigit());
                sessionDatabaseReference.child(et_subject.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()) {
                            sessionDatabaseReference.push().setValue(et_subject.getText().toString());
                        }

                        addedSessionDatabaseReference = sessionDatabaseReference.child(et_subject.getText().toString()).push();
                        session.setSession_id(addedSessionDatabaseReference.getKey());
                        addedSessionDatabaseReference.setValue(session);
                        sessionDatabaseReference.addValueEventListener(new ValueEventListener() {
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

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
    private String getRandomSixDigit() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }


}