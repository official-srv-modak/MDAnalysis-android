package com.souravmodak.mdanalysis.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.souravmodak.mdanalysis.R;

public class LibraryFuctions {

    public static LinearLayout createMultipleTextView(Context context, String... titleValues)
    {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        linearLayout.setLayoutParams(layoutParams);

        int number = titleValues.length;

        for(int i =0; i<number; i++)
        {
            String value = context.getString(R.string.sample_title);
            if(i < titleValues.length)
            {
                value = titleValues[i];
                View tv = createSingularCustomTextView(context, value, linearLayout);
                linearLayout.addView(tv);
            }
        }
        return linearLayout;
    }

    private static @NonNull View createSingularCustomTextView(Context context, String value, LinearLayout linearLayout) {
        View tv = LayoutInflater.from(context).inflate(R.layout.custom_text_field_with_title, linearLayout,  false);

        // Create layout parameters for setting margins
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Set the margins (left, top, right, bottom) in pixels
        tvParams.setMargins(16, 16, 16, 16); // Adjust these values as needed (in pixels)

        // Apply the layout parameters with margins to the view
        tv.setLayoutParams(tvParams);

        // Set value
        TextView txtView = tv.findViewById(R.id.product_card_accuracy_title);
        txtView.setText(value);

        return tv;
    }
}
