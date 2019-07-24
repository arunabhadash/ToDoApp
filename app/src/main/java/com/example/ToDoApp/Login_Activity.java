package com.example.ToDoApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;

public class Login_Activity extends AppCompatActivity {
  private EditText muserid;
  private  EditText mpass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        muserid=findViewById(R.id.userId);
        mpass=findViewById(R.id.password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        muserid.setText("");
        mpass.setText("");
        muserid.requestFocus();
    }

    public void onLoginPressed(View view)
    {
        int id=Integer.parseInt(muserid.getText().toString());
        String pass=mpass.getText().toString();
        Realm realm = Realm.getDefaultInstance();
        MyUser user = realm.where(MyUser.class)
                .equalTo("id", id)
                .findFirst();
        try {
            if(!pass.equals((user.getPassword())))
            {Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            return ;}
            Intent intent=new Intent(this,Dashboard.class);
            Bundle b=new Bundle();
            b.putString("username",user.getName());
            b.putString("email",user.getEmail());
            b.putString("phone",user.getPhone());
            b.putString("id",Integer.toString(user.getId()));
            intent.putExtras(b);
            startActivity(intent);
        }catch (NullPointerException e)
        {
            Toast.makeText(this, "Invalid UserId", Toast.LENGTH_SHORT).show();
        }
    }
}
