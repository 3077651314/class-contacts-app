package com.example.myapplication20;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication20.R;
import com.example.myapplication20.ContactDatabaseHelper;
import com.example.myapplication20.Contact;

public class EditContactActivity extends AppCompatActivity {
    private EditText etName, etPhone, etEmail, etAddress;
    private ContactDatabaseHelper dbHelper;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        dbHelper = new ContactDatabaseHelper(this);
        contact = (Contact) getIntent().getSerializableExtra("contact");

        if (contact == null) {
            finish();
            return;
        }

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);

        etName.setText(contact.getName());
        etPhone.setText(contact.getPhone());
        etEmail.setText(contact.getEmail());
        etAddress.setText(contact.getAddress());

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "姓名和电话不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            contact.setName(name);
            contact.setPhone(phone);
            contact.setEmail(email);
            contact.setAddress(address);

            dbHelper.updateContact(contact);
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            finish();
        });

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> finish());
    }
}
