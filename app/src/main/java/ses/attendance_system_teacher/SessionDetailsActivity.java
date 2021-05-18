package ses.attendance_system_teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SessionDetailsActivity extends AppCompatActivity {

    Intent intent;
    DatabaseReference sessionDatabaseReference;
    TextView tv_code;
    TextView tv_students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_session_details);

        intent = getIntent();

        tv_code = findViewById(R.id.tv_code);
        tv_students = findViewById(R.id.tv_students);
        tv_code.setText(intent.getStringExtra("session_code"));
        sessionDatabaseReference = FirebaseDatabase.getInstance().getReference("Session").child(intent.getStringExtra("session_subject")).child(intent.getStringExtra("session_id"));
        sessionDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("session_students")) {
                    for(DataSnapshot child : snapshot.child("session_students").getChildren()) {
                        tv_students.setText(tv_students.getText() + "\n" + child.getKey() + " - " + child.getValue());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}