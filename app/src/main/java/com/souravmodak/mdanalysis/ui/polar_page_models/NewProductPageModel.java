package com.souravmodak.mdanalysis.ui.polar_page_models;

import static com.souravmodak.mdanalysis.misc.LibraryFuctions.createSingularFileChooser;
import static com.souravmodak.mdanalysis.misc.LibraryFuctions.getApiResponse;

import android.content.Context;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.souravmodak.mdanalysis.R;
import com.souravmodak.mdanalysis.misc.LibraryFuctions;

import java.io.IOException;
import java.util.Arrays;

public abstract class NewProductPageModel {


    public static void renderUI(ScrollView constraintLayout, Context context){
        LinearLayout linearLayout = LibraryFuctions.createMultipleTextView(context, "Name");
        constraintLayout.addView(linearLayout);
        linearLayout.addView(createSingularFileChooser(context, "Train File", linearLayout));
        linearLayout.addView(createSingularFileChooser(context, "Test File", linearLayout));
        linearLayout.addView(LibraryFuctions.createMultipleTextView(context, "Split"));
        linearLayout.addView((LibraryFuctions.createSingularCustomSpinner(context, "Decision Column", Arrays.asList("Option 1", "Option 2", "Option 3"), linearLayout)));
        // Now retrieve the "corr_mat" field as a string
        linearLayout.addView(LibraryFuctions.createSingularInformationCard(context, context.getString(R.string.summary_card_fake_project_desc), linearLayout));
        JsonObject jsonObject = new JsonObject();
        linearLayout.addView(LibraryFuctions.createSingularImageCard(context, "http://10.0.0.47:7654/api/get-correlation-matrix-image?id=1", linearLayout));

//        jsonObject.addProperty("corr_mat", context.getString(R.string.sample_text));
        HorizontalScrollView tableLayout = LibraryFuctions.generateTabularInfo(jsonObject, "corr_mat", context);
        linearLayout.addView(tableLayout);

        linearLayout.addView((LibraryFuctions.createSingularCustomSpinner(context, "Columns", Arrays.asList("Option 1", "Option 2", "Option 3"), linearLayout)));
    }
}