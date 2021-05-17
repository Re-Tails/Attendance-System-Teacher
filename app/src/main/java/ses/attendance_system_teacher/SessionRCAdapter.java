package ses.attendance_system_teacher;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ses.attendance_system_teacher.model.Session;

public class SessionRCAdapter extends RecyclerView.Adapter<SessionRCAdapter.ViewHolder> {

    List<Session> sessionList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_session;

        public ViewHolder(View view) {
            super(view);
            tv_session = (TextView) view.findViewById(R.id.tv_session);
        }

        public TextView getTextView() {
            return tv_session;
        }

        public View getView() {return itemView;}
    }

    public SessionRCAdapter(List sessionList) {
        this.sessionList = sessionList;
        Log.v("Count", String.valueOf(sessionList.size()));
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.v("view", "binded");
        Session session = sessionList.get(position);
        holder.getTextView().setText(session.getSession_subject()
                                    + " on "
                                    + session.getSession_date());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context viewContext = v.getContext();
                Intent intent = new Intent(viewContext, SessionDetailsActivity.class);
                intent.putExtra("session_id", session.getSession_id());
                intent.putExtra("session_location", session.getSession_location());
                intent.putExtra("session_date", session.getSession_date());
                intent.putExtra("session_start_time", session.getSession_start_time());
                intent.putExtra("session_end_time", session.getSession_end_time());
                intent.putExtra("session_subject", session.getSession_subject());
                intent.putExtra("session_code", session.getSession_code());
                viewContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }


}
