package ru.job4j.okhttpexample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowAccActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_acc_activity);
        TextView result = findViewById(R.id.text_view_result);
        result.setText(getIntent().getStringExtra("result"));
    }
}
