package com.app.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.roomdatabase.adapters.UsersAdapter;
import com.app.roomdatabase.models.Users;
import com.app.roomdatabase.rooms.UsersDatabase;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName;
    private Button btnSave, btnUpdate, btnDelete;
    private ListView lvData;

    private UsersDatabase usersDatabase;

    private UsersAdapter adapter;
    private ArrayList<Users> arrayList;

    private Users selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDatabase = UsersDatabase.getInstance(this);

        selectedUser = new Users();

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        btnSave = findViewById(R.id.btn_save);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        lvData = findViewById(R.id.lv_data);

        lvData.setOnItemClickListener((parent, view, position, id) -> {
            selectedUser = arrayList.get(position);
            etFirstName.setText(selectedUser.getFirstName());
            etLastName.setText(selectedUser.getLastName());
        });

        btnSave.setOnClickListener(v -> {
            if (!etFirstName.getText().toString().isEmpty() && !etLastName.getText().toString().isEmpty()) {
                Users user = new Users();
                user.setFirstName(etFirstName.getText().toString());
                user.setLastName(etLastName.getText().toString());
                long insert = usersDatabase.usersDAO().insertUser(user);
                Log.e("insert", insert+"");
                if (insert > 0) {
                    etFirstName.setText("");
                    etLastName.setText("");
                    getUsers();
                }
            }
        });

        btnUpdate.setOnClickListener(v -> {
            if (selectedUser.getId() == 0) {
                Toast.makeText(this, "no user selected", Toast.LENGTH_SHORT).show();
            }else {
                selectedUser.setFirstName(etFirstName.getText().toString());
                selectedUser.setLastName(etLastName.getText().toString());
                int update = usersDatabase.usersDAO().updateUser(selectedUser);
                Log.e("update", update + "");
                if (update > 0) {
                    getUsers();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
            deleteDialog.setTitle("RoomDatabase");
            deleteDialog.setMessage("would you like to delete this user?");
            deleteDialog.setNegativeButton("NO", (dialog, which) -> deleteDialog.setCancelable(true));
            deleteDialog.setPositiveButton("YES", (dialog, which) -> {
                int delete = usersDatabase.usersDAO().deleteUser(selectedUser);
                Log.e("delete", delete+"");
                if (delete > 0) {
                    etFirstName.setText("");
                    etLastName.setText("");
                    selectedUser = new Users();
                    getUsers();
                }
            });
            if (selectedUser.getId() == 0) {
                Toast.makeText(this, "no user selected", Toast.LENGTH_SHORT).show();
            }else {
                deleteDialog.show();
            }
        });

        getUsers();

    }

    private void getUsers(){
        arrayList = new ArrayList<>(usersDatabase.usersDAO().getUsers());
        Log.e("list", arrayList.toString());
        if (!arrayList.isEmpty()) {
            adapter = new UsersAdapter(MainActivity.this, R.layout.item_user, arrayList);
            lvData.setAdapter(adapter);
        }else {
            lvData.setAdapter(null);
        }
    }


}