package com.example.myapplication20;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class AddContactActivity extends AppCompatActivity {
    private EditText etName, etPhone, etEmail, etAddress;
    private ContactDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        dbHelper = new ContactDatabaseHelper(this);

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);

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

            Contact contact = new Contact();
            contact.setName(name);
            contact.setPhone(phone);
            contact.setEmail(email);
            contact.setAddress(address);

            dbHelper.addContact(contact);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        });

        Button btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> finish());
    }
}


