package com.example.contactapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditContactActivity extends AppCompatActivity {

    EditText etName, etPhone, etEmail;
    Button btnSave;
    DBHelper dbHelper;
    int contactId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DBHelper(this);

        if (getIntent().hasExtra("CONTACT_ID")) {
            contactId = getIntent().getIntExtra("CONTACT_ID", -1);
            Contact contact = dbHelper.getContactById(contactId);
            if (contact != null) {
                etName.setText(contact.getName());
                etPhone.setText(contact.getPhone());
                etEmail.setText(contact.getEmail());
            }
        }

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Name and Phone are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contactId == -1) {
            dbHelper.insertContact(new Contact(0, name, phone, email));
            Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.updateContact(new Contact(contactId, name, phone, email));
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
