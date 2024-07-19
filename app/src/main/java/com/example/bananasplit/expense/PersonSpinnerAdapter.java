package com.example.bananasplit.expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.util.ImageUtils;

import java.util.List;

public class PersonSpinnerAdapter extends ArrayAdapter<Person> {

    private final LayoutInflater inflater;
    private final List<Person> persons;
    private final int resourceId;

    public PersonSpinnerAdapter(Context context, int resourceId, List<Person> persons) {
        super(context, resourceId, persons);
        this.inflater = LayoutInflater.from(context);
        this.resourceId = resourceId;
        this.persons = persons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(inflater, position, convertView, parent, resourceId);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(inflater, position, convertView, parent, resourceId);
    }

    private View createViewFromResource(LayoutInflater inflater, int position, View convertView, ViewGroup parent, int resource) {
        final View view;
        final ViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.profilePicture = view.findViewById(R.id.friend_profile_picture);
            holder.name = view.findViewById(R.id.person_name);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        Person person = persons.get(position);
        holder.name.setText(person.getName());
        ImageUtils.setProfileImage(holder.profilePicture, person.getName());

        return view;
    }

    private static class ViewHolder {
        ImageView profilePicture;
        TextView name;
    }
}
