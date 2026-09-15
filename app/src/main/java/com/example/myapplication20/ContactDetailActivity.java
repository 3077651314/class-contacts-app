package com.example.myapplication20;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication20.R;
import com.example.myapplication20.Contact;
import com.example.myapplication20.PhoneUtils;

public class ContactDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        if (contact == null) finish();

        TextView tvName = findViewById(R.id.tv_name);
        TextView tvPhone = findViewById(R.id.tv_phone);
        TextView tvEmail = findViewById(R.id.tv_email);
        TextView tvAddress = findViewById(R.id.tv_address);

        tvName.setText(contact.getName());
        tvPhone.setText(contact.getPhone());
        tvEmail.setText(contact.getEmail());
        tvAddress.setText(contact.getAddress());

        Button btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(v -> PhoneUtils.makePhoneCall(this, contact.getPhone()));

        Button btnEdit = findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditContactActivity.class);
            intent.putExtra("contact", contact);
            startActivity(intent);
        });

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }
}




