package ses.attendance_system_teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SessionDetailsActivity extends AppCompatActivity {

    Intent intent;
    TextView tv_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        intent = getIntent();

        tv_code = findViewById(R.id.tv_code);
        tv_code.setText(intent.getStringExtra("session_code"));
    }
}