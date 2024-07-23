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

/**
 * Adapter for displaying Person objects in a Spinner.
 * @author Arpad Horvath
 */
public class PersonSpinnerAdapter extends ArrayAdapter<Person> {

    private final LayoutInflater inflater;
    private final int resourceId;

    /**
     * Constructor for PersonSpinnerAdapter.
     * @param context the context
     * @param resourceId the layout resource ID
     * @param persons the list of Person objects
     */
    public PersonSpinnerAdapter(Context context, int resourceId, List<Person> persons) {
        super(context, resourceId, persons);
        this.inflater = LayoutInflater.from(context);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    /**
     * Creates or updates a view to display a Person item.
     * @param position the position of the item in the list
     * @param convertView the recycled view to reuse, if possible
     * @param parent the parent view group
     * @return the view to display the item
     */
    private View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.profilePicture = convertView.findViewById(R.id.friend_profile_picture);
            holder.name = convertView.findViewById(R.id.person_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Person person = getItem(position);
        if (person != null) {
            holder.name.setText(person.getName());
            ImageUtils.setProfileImage(holder.profilePicture, person.getName()); //TODO currently just placeholder image
        }

        return convertView;
    }

    /**
     * ViewHolder pattern for optimizing view lookup.
     */
    private static class ViewHolder {
        ImageView profilePicture;
        TextView name;
    }
}
