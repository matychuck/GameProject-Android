package com.example.user.game_projekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.user.game_projekt.PopUp.active;
import static com.example.user.game_projekt.PopUp.active_pop;

public class SetNameActivity extends AppCompatActivity {

    EditText nick;
    Button play;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);

        nick = findViewById(R.id.editText);
        play = findViewById(R.id.button5);
        back = findViewById(R.id.button6);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(nick.getText()))
                {
                    Intent intent = new Intent(SetNameActivity.this,GameActivity.class);
                    active = true;
                    active_pop = true;
                    intent.putExtra("nick",nick.getText().toString());
                    v.getContext().startActivity(intent);
                }
                else
                {
                    Toast.makeText(v.getContext(),R.string.error_name,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
