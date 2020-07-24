package ru.job4j.okhttpexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AuthorizationInGithub.getAnswerFromGitHubAPI {
    private Button signIn;
    private EditText login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.editText_login);
        password = findViewById(R.id.editText_password);
        signIn = findViewById(R.id.button_sign_in);
        signIn.setOnClickListener(v -> clickOnSignIn());
    }

    @Override
    protected void onResume() {
        super.onResume();
        signIn.setEnabled(true);
    }

    private void clickOnSignIn(){
        new AuthorizationInGithub(String.valueOf(login.getText()),String.valueOf(password.getText()),this).signIn();
        signIn.setEnabled(false);
    }

    @Override
    public void successAuthorization(String result) {
        Intent intent = new Intent(this, ShowAccActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
    }

    @Override
    public void failedAuthorization() {
        runOnUiThread(() -> {
                Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                signIn.setEnabled(true);
        });
    }
}