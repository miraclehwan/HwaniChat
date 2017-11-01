package daehwan.com.hwanitalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    {
        try{
            String URL = "";
            mSocket = IO.socket(URL);
        }catch (URISyntaxException e){}
    }

    TextView getMessageTextView;
    EditText sendMessageTextView;
    Button sendMessageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.connect();

        getMessageTextView = (TextView) findViewById(R.id.getmessageTextView);
        sendMessageTextView = (EditText) findViewById(R.id.sendmessageTextView);
        sendMessageButton = (Button) findViewById(R.id.sendmessageButton);


        //Send Button Event
        sendMessageButton.setOnClickListener(sendMessage);

        //Socket Event
        mSocket.on("getMessage", getMessage);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
    }

    Emitter.Listener getMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getMessageTextView.setText(String.valueOf(args[0]));
                }
            });
        }
    };

    View.OnClickListener sendMessage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSocket.emit("sendMessage",sendMessageTextView.getText().toString());
            sendMessageTextView.setText("");
        }
    };

}
