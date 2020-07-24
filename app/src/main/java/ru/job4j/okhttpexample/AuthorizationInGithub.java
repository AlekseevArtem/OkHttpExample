package ru.job4j.okhttpexample;

import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInGithub extends OkHttpClient {
    private getAnswerFromGitHubAPI callback;
    private String login;
    private String password;

    public AuthorizationInGithub (String login, String password, getAnswerFromGitHubAPI callback) {
        this.login = login;
        this.password = password;
        this.callback = callback;
    }

    public interface getAnswerFromGitHubAPI {
        void successAuthorization(String result);
        void failedAuthorization();
    }

    public void signIn(){
        String credentials = Credentials.basic(login, password);
        String url = "https://api.github.com/user";
        Request request = new Request.Builder().addHeader("Authorization", credentials).url(url).build();
        this.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String strResponse = Objects.requireNonNull(response.body()).string();
                    callback.successAuthorization(strResponse);
                } else {
                    callback.failedAuthorization();
                }
            }
        });
    }
}
