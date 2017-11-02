package daehwan.com.hwanitalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    ArrayList<Recycler_Chat_item> items = new ArrayList<>();
    Recycler_Chat_Adapter adapter;

    RecyclerView getMessageRecyclerView;
    EditText sendMessageTextView;
    Button sendMessageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSocket.connect();

        getMessageRecyclerView = (RecyclerView) findViewById(R.id.getMessageRecyclerView);
        sendMessageTextView = (EditText) findViewById(R.id.sendmessageTextView);
        sendMessageButton = (Button) findViewById(R.id.sendmessageButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        getMessageRecyclerView.setHasFixedSize(true);
        getMessageRecyclerView.setLayoutManager(layoutManager);

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

    private Socket mSocket;
    {
        try{
            String URL = "";
            mSocket = IO.socket(URL);
        }catch (URISyntaxException e){}
    }

    Emitter.Listener getMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            try {
                JSONObject jsonObject = (JSONObject) args[0];
                items.add(new Recycler_Chat_item(jsonObject.getString("id"), jsonObject.getString("message")));
                adapter = new Recycler_Chat_Adapter(getApplicationContext(), items, R.layout.activity_main);
                getMessageRecyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
