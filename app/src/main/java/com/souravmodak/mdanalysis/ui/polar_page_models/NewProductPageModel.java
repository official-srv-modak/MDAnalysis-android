package com.souravmodak.mdanalysis.ui.polar_page_models;

import static com.souravmodak.mdanalysis.misc.LibraryFuctions.createSingularFileChooser;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.souravmodak.mdanalysis.misc.LibraryFuctions;

public abstract class NewProductPageModel {


    public static void renderUI(ConstraintLayout constraintLayout, Context context){
        LinearLayout linearLayout = LibraryFuctions.createMultipleTextView(context, "Name");
        constraintLayout.addView(linearLayout);
        linearLayout.addView(createSingularFileChooser(context, "Train File", linearLayout));
        linearLayout.addView(createSingularFileChooser(context, "Test File", linearLayout));
        linearLayout.addView(LibraryFuctions.createMultipleTextView(context, "Split", "Decision Column", "Columns"));
    }
}