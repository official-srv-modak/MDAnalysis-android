package com.souravmodak.mdanalysis.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.souravmodak.mdanalysis.R;

public class LibraryFuctions {

    public static LinearLayout createMultipleTextView(int number, Context context)
    {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for(int i =0; i<number; i++)
        {
            View tv = LayoutInflater.from(context).inflate(R.layout.custom_text_field_with_title, linearLayout,  false);
            linearLayout.addView(tv);
        }
        return linearLayout;
    }
}
