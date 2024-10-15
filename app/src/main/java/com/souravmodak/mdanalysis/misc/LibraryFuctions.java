package com.souravmodak.mdanalysis.misc;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.souravmodak.mdanalysis.R;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;


public class LibraryFuctions {

    public static final int REQUEST_CODE_FILE_PICKER = 1;
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

    public static @NonNull View createSingularCustomTextView(Context context, String value, LinearLayout linearLayout) {
        View tv = LayoutInflater.from(context).inflate(R.layout.custom_text_field_with_title, linearLayout,  false);

        // Set value
        TextView txtView = tv.findViewById(R.id.product_card_accuracy_title);
        txtView.setText(value);

        return tv;
    }

    public static LinearLayout createMultipleEditText(Context context, String... titleValues)
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
                View tv = createSingularCustomEditText(context, value, linearLayout);
                linearLayout.addView(tv);
            }
        }
        return linearLayout;
    }


    public static @NonNull View createSingularCustomEditText(Context context, String value, LinearLayout linearLayout) {
        View tv = LayoutInflater.from(context).inflate(R.layout.custom_edit_text_with_title, linearLayout,  false);

        // Set value
        TextView txtView = tv.findViewById(R.id.edit_text_title);
        txtView.setText(value);

        return tv;
    }

    public static @NonNull View createSingularCustomSpinner(Context context, String titleValue, List<String> spinnerItems, LinearLayout linearLayout) {
        View tv = LayoutInflater.from(context).inflate(R.layout.custom_spinner_with_text_view, linearLayout,  false);

        // Set value
        TextView txtView = tv.findViewById(R.id.spinner_card_title);
        txtView.setText(titleValue);

        Spinner spinner = tv.findViewById(R.id.spinner_card_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.custom_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Set the adapter to the Spinner
        spinner.setAdapter(adapter);


        return tv;
    }


    public static @NonNull View createSingularInformationCard(Context context, String value, LinearLayout linearLayout) {
        View tv = LayoutInflater.from(context).inflate(R.layout.custom_information_md_ai_card, linearLayout,  false);

        // Set value
        TextView txtView = tv.findViewById(R.id.information_card_tv);
        txtView.setText(value);

        return tv;
    }

    public static @NonNull View createSingularImageCard(Context context, String url, LinearLayout linearLayout) {
        View tv = LayoutInflater.from(context).inflate(R.layout.custom_md_image_ai_card, linearLayout,  false);

        // Set value
        ImageView imageView = tv.findViewById(R.id.custom_md_image_iv);

        // Use Glide to load the image from the URL into the ImageView
        Glide.with(context)
                .load(url)                // URL of the image
                .placeholder(R.drawable.ic_menu_gallery)  // Placeholder image while loading
                .timeout(100000)
                .into(imageView);         // Target ImageView

        return tv;
    }

    public static @NonNull View createSingularFileChooser(Context context, Activity activity, String value, LinearLayout linearLayout) {
        View fileChooserView = LayoutInflater.from(context).inflate(R.layout.custom_file_chooser_with_text_view, linearLayout,  false);

        // Create layout parameters for setting margins
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        // Set the margins (left, top, right, bottom) in pixels
        tvParams.setMargins(16, 16, 16, 16); // Adjust these values as needed (in pixels)

        // Apply the layout parameters with margins to the view
        fileChooserView.setLayoutParams(tvParams);

        // Set value
        TextView txtView = fileChooserView.findViewById(R.id.file_chooser_title);
        ImageView fileButton = fileChooserView.findViewById(R.id.file_chooser_button);
        fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickFileChooser(activity);
            }
        });

        txtView.setText(value);

        return fileChooserView;
    }

    public static void onClickFileChooser(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Choose any file type; you can specify specific types like "image/*" if needed
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(Intent.createChooser(intent, "Select a file"), REQUEST_CODE_FILE_PICKER);
    }


    public static HorizontalScrollView generateTabularInfo(JsonObject jsonObject, String key, Context context) {
        // Initialize TableLayout inside HorizontalScrollView
        HorizontalScrollView scrollView = new HorizontalScrollView(context);
        TableLayout tableLayout = new TableLayout(context);

        try {
            // Split the matrix data by rows and columns
            String matrixData = jsonObject.get(key).getAsString();
            String[] rows = matrixData.split("\n");

            for (int i = 0; i < rows.length; i++) {
                // Create a new TableRow
                TableRow tableRow = new TableRow(context);

                // Split the row into individual values (columns)
                String[] columns = rows[i].trim().split("\\s+");

                for (int j = 0; j < columns.length; j++) {
                    TextView textView = new TextView(context);
                    textView.setText(columns[j]);
                    textView.setPadding(10, 10, 10, 10);  // Add some padding
                    textView.setGravity(Gravity.CENTER);

                    // Make the first row (header) and first column bold
                    if (i == 0 || j == 0) {
                        textView.setTypeface(null, Typeface.BOLD);  // Bold text
                        textView.setTextColor(Color.BLACK);
                    }

                    // Add a simple border to each cell
                    textView.setBackgroundResource(android.R.drawable.editbox_background);

                    // Add the TextView to the TableRow
                    tableRow.addView(textView);
                }

                // Add the TableRow to the TableLayout
                tableLayout.addView(tableRow);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set layout parameters and margins for TableLayout
        TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(40, 40, 40, 40);  // Set margins (left, top, right, bottom)

        // Apply the layoutParams to the TableLayout
        tableLayout.setLayoutParams(layoutParams);

        // Add the TableLayout to the ScrollView
        scrollView.addView(tableLayout);

        return scrollView;
    }


    public static JsonObject getApiResponse(String url) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)  // replace with your API's base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<JsonObject> call = apiService.getCorrelationMat();

        // Synchronous call
        Response<JsonObject> response = call.execute();

        if (response.isSuccessful()) {
            return response.body(); // Return the JsonObject directly
        } else {
            throw new IOException("Failed to get response: " + response.errorBody().string());
        }
    }

}
