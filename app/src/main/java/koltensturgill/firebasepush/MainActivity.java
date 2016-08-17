package koltensturgill.firebasepush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                Log.d("MainActivity", "Subscribed to news topic");
                Toast.makeText(getApplicationContext(), "Subscribed to news topic", Toast.LENGTH_LONG).show();
            }
        });

        //Registration token need to send message for specific device
        Button buttonGetToken = (Button) findViewById(R.id.buttonGetToken);
        buttonGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d("MainActivity", "InstanceID token: " + token);
            }
        });

    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverLoadTodays, new IntentFilter("update-message"));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private BroadcastReceiver broadcastReceiverLoadTodays = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), intent.getExtras().getString("message"), Toast.LENGTH_SHORT).show();
            TextView textViewMessage = (TextView) findViewById(R.id.textViewMessage);
            textViewMessage.setText(intent.getExtras().getString("message"));
        }
    };

}
