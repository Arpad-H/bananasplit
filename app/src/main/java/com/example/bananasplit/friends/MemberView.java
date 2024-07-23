package com.example.bananasplit.friends;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.bananasplit.R;
import com.example.bananasplit.dataModel.Person;
import com.example.bananasplit.util.ImageUtils;

import java.util.List;

/**
 * Custom view to display a list of member profile images with optional overflow count.
 * Mimics the cool current Google users thingy.
 * @author Arpad Horvath
 */
public class MemberView extends LinearLayout {

    private static final int IMAGE_SIZE = 100;
    private static final int IMAGE_MARGIN = -30;
    private static final int PADDING_LEFT = 10;
    private static final int PADDING_TOP = 30;

    private static final int MAX_DISPLAY_COUNT = 3;

    public MemberView(Context context) {
        super(context);
        initialize(context);
    }

    public MemberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public MemberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * Initializes the view by setting its orientation.
     */
    private void initialize(Context context) {
        setOrientation(HORIZONTAL);
    }

    /**
     * Sets the list of members to be displayed.
     * @param members The list of Person objects representing members.
     */
    public void setMembers(List<Person> members) {
        removeAllViews();
        displayMemberImages(members);
        displayOverflowCountIfNeeded(members.size());
    }

    /**
     * Creates and adds ImageViews for the members' profile images.
     * @param members The list of Person objects representing members.
     */
    private void displayMemberImages(List<Person> members) {
        int count = Math.min(members.size(), MAX_DISPLAY_COUNT);

        for (int i = 0; i < count; i++) {
            ImageView imageView = createProfileImageView(i);
            ImageUtils.setProfileImage(imageView, members.get(i).getName()); //TODO currently just using placeholder Images
            addView(imageView);
        }
    }

    /**
     * Creates an ImageView for displaying a profile image.
     * @param index The index of the ImageView to determine its margin.
     * @return The created ImageView.
     */
    private ImageView createProfileImageView(int index) {
        ImageView imageView = new ImageView(getContext());
        LayoutParams params = new LayoutParams(IMAGE_SIZE, IMAGE_SIZE);

        if (index > 0) {
            params.setMargins(IMAGE_MARGIN, 0, 0, 0); // Overlapping effect
        }

        imageView.setLayoutParams(params);
        return imageView;
    }

    /**
     * Creates and adds a TextView to display the overflow count if there are more members than the max display count.
     * @param totalMemberCount The total number of members.
     */
    private void displayOverflowCountIfNeeded(int totalMemberCount) {
        if (totalMemberCount > MAX_DISPLAY_COUNT) {
            TextView moreTextView = createOverflowTextView(totalMemberCount);
            addView(moreTextView);
        }
    }

    /**
     * Creates a TextView to display the number of additional members.
     * @param totalMemberCount The total number of members.
     * @return The created TextView.
     */
    @SuppressLint("SetTextI18n")
    private TextView createOverflowTextView(int totalMemberCount) {
        TextView moreTextView = new TextView(getContext());
        moreTextView.setText("+" + (totalMemberCount - MAX_DISPLAY_COUNT));
        moreTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        moreTextView.setPadding(PADDING_LEFT, PADDING_TOP, 0, 0);
        return moreTextView;
    }
}
