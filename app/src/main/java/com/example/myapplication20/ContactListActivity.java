package com.example.myapplication20;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication20.R;
import com.example.myapplication20.ContactAdapter;
import com.example.myapplication20.ContactDatabaseHelper;
import com.example.myapplication20.Contact;
import com.example.myapplication20.SortManager;
import com.example.myapplication20.PhoneUtils;
import com.example.myapplication20.SearchUtil;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements ContactAdapter.OnContactClickListener {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contactList;
    private ContactDatabaseHelper dbHelper;
    private SearchUtil searchUtil;
    private SortManager sortManager;
    private String currentKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        dbHelper = new ContactDatabaseHelper(this);
        searchUtil = new SearchUtil(dbHelper);
        sortManager = new SortManager();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>(dbHelper.getAllContacts());
        adapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fab_add).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            showSearchDialog();
            return true;
        } else if (id == R.id.action_sort) {
            showSortDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_search, null);
        EditText etKeyword = view.findViewById(R.id.et_search);
        builder.setView(view)
                .setTitle("搜索")
                .setPositiveButton("搜索", (dialog, which) -> {
                    String keyword = etKeyword.getText().toString().trim();
                    currentKeyword = keyword;
                    if (keyword.isEmpty()) {
                        contactList = new ArrayList<>(dbHelper.getAllContacts());
                    } else {
                        contactList = searchUtil.searchByName(keyword);
                    }
                    adapter.updateData(contactList);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void showSortDialog() {
        String[] options = {"按姓名升序", "按姓名降序", "按电话升序", "按电话降序"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("排序方式")
                .setItems(options, (dialog, which) -> {
                    int sortType;
                    switch (which) {
                        case 0: sortType = SortManager.SORT_NAME_ASC; break;
                        case 1: sortType = SortManager.SORT_NAME_DESC; break;
                        case 2: sortType = SortManager.SORT_PHONE_ASC; break;
                        default: sortType = SortManager.SORT_PHONE_DESC;
                    }
                    sortManager.sort(contactList, sortType);
                    adapter.updateData(contactList);
                })
                .show();
    }

    @Override
    public void onContactClick(Contact contact) {
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    @Override
    public void onCallClick(Contact contact) {
        PhoneUtils.makePhoneCall(this, contact.getPhone());
    }

    @Override
    public void onCopyClick(Contact contact) {
        PhoneUtils.copyToClipboard(this, contact.getPhone());
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactList = new ArrayList<>(dbHelper.getAllContacts());
        adapter.updateData(contactList);
    }
}


