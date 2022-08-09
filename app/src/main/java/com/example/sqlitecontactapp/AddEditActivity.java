package com.example.sqlitecontactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.example.sqlitecontactapp.model.DatabaseHelper;

public class AddEditActivity extends AppCompatActivity {
    EditText txt_id, txt_name, txt_address;
    Button btn_submit, btn_cancel;
    DatabaseHelper SQLite = new DatabaseHelper(this);
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txt_id = (EditText) findViewById(R.id.txt_id);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_address = (EditText) findViewById(R.id.txt_address);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);
        if (id == null || id == "") {
            setTitle("Add Data");
        }else {
            setTitle("Update Data");
            txt_id.setText(id);
            txt_name.setText(name);
            txt_address.setText(address);
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (txt_id.getText().toString().equals("")){
                        save();
                    }else {
                        edit();
                    }
                }catch (Exception ex) {
                    Log.e("submit", ex.getMessage());
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blank();
                finish();
            }
        });
    }

    private void save() {
        if (String.valueOf(txt_name.getText()).equals(null) || String.valueOf(txt_name.getText()).equals("")
            || String.valueOf(txt_address.getText()).equals("")) {
            Toast.makeText(getApplicationContext(), "Please input name or address", Toast.LENGTH_SHORT).show();
        }else {
            SQLite.insert(txt_name.getText().toString().trim(),txt_address.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void edit() {
        if (String.valueOf(txt_name.getText()).equals(null) || (String.valueOf(txt_name.getText()).equals(""))
            || String.valueOf(txt_address.getText()).equals(null) || (String.valueOf(txt_address.getText()).equals(""))){

            Toast.makeText(getApplicationContext(), "Please input name or address", Toast.LENGTH_SHORT).show();
        }else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()),
                txt_name.getText().toString().trim(), txt_address.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void blank() {
        txt_name.requestFocus();
        txt_id.setText(null);
        txt_address.setText(null);
        txt_name.setText(null);
    }
}