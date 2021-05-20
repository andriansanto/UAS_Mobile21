package umn.ac.id.uas_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Re_adapter extends RecyclerView.Adapter<Re_adapter.Myviewholder> {

    ArrayList<String> act_name, act_credit, act_date;
    Context context;

    public Re_adapter(Context ct, ArrayList<String> activity_name, ArrayList<String> activity_credit, ArrayList<String> activity_date){
        this.context = ct;
        this.act_name = activity_name;
        this.act_credit = activity_credit;
        this.act_date = activity_date;

    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.re_row,  parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.act_name.setText(act_name.get(position));
        holder.act_credit.setText(act_credit.get(position));
        holder.date.setText(act_date.get(position));
    }

    @Override
    public int getItemCount() {
        return act_name.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView act_name, act_credit,date;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            act_credit = itemView.findViewById(R.id.act_credit);
            act_name = itemView.findViewById(R.id.act_name);
            date = itemView.findViewById(R.id.date);
        }
    }
}
