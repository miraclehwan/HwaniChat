package daehwan.com.hwanitalk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by daehwan.kim on 2017-11-02.
 */

public class Recycler_Chat_Adapter extends RecyclerView.Adapter<Recycler_Chat_Adapter.ViewHolder>{

    Context context;
    List<Recycler_Chat_item> items;
    int item_layout;
    Recycler_Chat_item item;

    public Recycler_Chat_Adapter(Context context, List<Recycler_Chat_item> items, int item_layout){
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        item = items.get(position);
        holder.id.setText(item.getId());
        holder.message.setText(item.getMessage());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView message;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.textview_message_id);
            message = (TextView) itemView.findViewById(R.id.textview_message_message);
        }
    }

}
