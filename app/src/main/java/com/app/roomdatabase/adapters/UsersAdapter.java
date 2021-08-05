package com.app.roomdatabase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.roomdatabase.R;
import com.app.roomdatabase.models.Users;

import java.util.ArrayList;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class UsersAdapter extends ArrayAdapter<Users> {

    private Context context;
    private int resource;

    private ArrayList<Users> items;
    private Users user;

    public UsersAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Users> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.items=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Holder holder = new Holder();
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
            holder.tvFirstName = view.findViewById(R.id.tv_first_name);
            holder.tvLastName = view.findViewById(R.id.tv_last_name);
            view.setTag(holder);
        }else {
            holder=(Holder)view.getTag();
        }

        user = items.get(position);

        holder.tvFirstName.setText(user.getFirstName());
        holder.tvLastName.setText(user.getLastName());

        return view;
    }

    private static class Holder{
        private TextView tvFirstName, tvLastName;
    }

}
