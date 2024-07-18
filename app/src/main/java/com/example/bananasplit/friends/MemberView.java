// MemberView.java
package com.example.bananasplit.friends;

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

public class MemberView extends LinearLayout {
    public MemberView(Context context) {
        super(context);
        init(context);
    }

    public MemberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MemberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
    }

    public void setMembers(List<Person> members) {
        removeAllViews();
        int maxDisplayCount = 3;

        for (int i = 0; i < Math.min(members.size(), maxDisplayCount); i++) {
            ImageView imageView = new ImageView(getContext());
            LayoutParams params = new LayoutParams(100, 100);
            if (i > 0) {
                params.setMargins(-30, 0, 0, 0);  // Overlapping effect
            }
            imageView.setLayoutParams(params);
//            if (members.get(i).getProfilePicture() != null) {
//                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), members.get(i).getProfilePicture()));
//            } else {
//                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.logo)); // TODO Use a placeholder or actual image
//            }
            ImageUtils.setProfileImage(imageView,members.get(i).getName()); // TODO Use a placeholder or actual image
            addView(imageView);
        }

        if (members.size() > maxDisplayCount) {
            TextView moreTextView = new TextView(getContext());
            moreTextView.setText("+" + (members.size() - maxDisplayCount));
            moreTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            moreTextView.setPadding(10, 30, 0, 0);
            addView(moreTextView);
        }
    }
}
