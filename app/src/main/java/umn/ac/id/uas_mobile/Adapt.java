package umn.ac.id.uas_mobile;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapt extends RecyclerView.Adapter<Adapt.viewHolder> {
    Context context;
    ArrayList<Putarlagu> audio_list;
    public OnItemClickListener onItemClickListener;

    public Adapt(Context context, ArrayList<Putarlagu> audio_list) {
        this.context = context;
        this.audio_list = audio_list;
    }

    @Override
    public Adapt.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.listlagu, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapt.viewHolder holder, final int i) {
        holder.title.setText(audio_list.get(i).getjudulaud());
        holder.artist.setText(audio_list.get(i).getartist());
    }

    @Override
    public int getItemCount() {
        return audio_list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title, artist;
        ImageView delete, edit;
        public viewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            artist = (TextView) itemView.findViewById(R.id.artist);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}