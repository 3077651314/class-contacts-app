package com.example.myapplication20;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication20.R;
import com.example.myapplication20.ContactListActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnContacts = findViewById(R.id.btn_contacts);
        btnContacts.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ContactListActivity.class));
        });
    }
}


