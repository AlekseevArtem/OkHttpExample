package ru.job4j.okhttpexample;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationGithub extends OkHttpClient {
    private Callback callback;
    private String login;
    private String password;

    public AuthorizationGithub(String login, String password, Callback callback) {
        this.login = login;
        this.password = password;
        this.callback = callback;
    }

    public interface Callback {
        void successAuthorization(String result);
        void failedAuthorization();
    }

    public void signIn(){
        String credentials = Credentials.basic(login, password);
        String url = "https://api.github.com/user";
        Request request = new Request.Builder().addHeader("Authorization", credentials).url(url).build();
        this.newCall(request).enqueue(new okhttp3.Callback() {
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
