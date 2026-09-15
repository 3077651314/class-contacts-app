package com.example.myapplication20;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication20.R;
import com.example.myapplication20.Contact;
import com.example.myapplication20.ContactDatabaseHelper;
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

        Button btnDelete = findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("删除联系人")
                        .setMessage("确定删除「" + contact.getName() + "」吗？该操作不可恢复。")
                        .setPositiveButton("删除", (dialog, which) -> {
                            ContactDatabaseHelper dbHelper = new ContactDatabaseHelper(this);
                            int rows = dbHelper.deleteContact(contact.getId());
                            if (rows > 0) {
                                Toast.makeText(this, "已删除", Toast.LENGTH_SHORT).show();
                            }
                            finish();   // ContactListActivity.onResume() 会重新加载列表
                        })
                        .setNegativeButton("取消", null)
                        .show()
        );

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }
}




