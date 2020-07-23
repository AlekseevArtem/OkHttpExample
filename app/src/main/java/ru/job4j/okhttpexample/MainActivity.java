package ru.job4j.okhttpexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
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

    private void clickOnSignIn(){
        OkHttpClient client = new OkHttpClient();
        String credentials = Credentials.basic(String.valueOf(login.getText()),String.valueOf(password.getText()));
        String url = "https://api.github.com/user";
        Request request = new Request.Builder().addHeader("Authorization", credentials).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String strResponse = Objects.requireNonNull(response.body()).string();
                    Intent intent = new Intent(MainActivity.this, ShowAccActivity.class);
                    intent.putExtra("result",strResponse);
                    startActivity(intent);
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }



}