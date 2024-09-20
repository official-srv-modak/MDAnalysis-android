package com.souravmodak.mdanalysis.misc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.souravmodak.mdanalysis.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.souravmodak.mdanalysis.misc.ApiService;

import java.io.IOException;


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

        // Set value
        TextView txtView = tv.findViewById(R.id.product_card_accuracy_title);
        txtView.setText(value);

        return tv;
    }


    private static @NonNull View createSingularInformationCard(Context context, String value, LinearLayout linearLayout) {
        View tv = LayoutInflater.from(context).inflate(R.layout.custom_information_md_ai_card, linearLayout,  false);

        // Set value
        TextView txtView = tv.findViewById(R.id.information_card_tv);
        txtView.setText(value);

        return tv;
    }

    public static @NonNull View createSingularFileChooser(Context context, String value, LinearLayout linearLayout) {
        View fileChooserView = LayoutInflater.from(context).inflate(R.layout.custom_file_chooser_with_text_view, linearLayout,  false);

        // Create layout parameters for setting margins
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Set the margins (left, top, right, bottom) in pixels
        tvParams.setMargins(16, 16, 16, 16); // Adjust these values as needed (in pixels)

        // Apply the layout parameters with margins to the view
        fileChooserView.setLayoutParams(tvParams);

        // Set value
        TextView txtView = fileChooserView.findViewById(R.id.file_chooser_title);
        txtView.setText(value);

        return fileChooserView;
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
