package com.souravmodak.mdanalysis.misc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.souravmodak.mdanalysis.R;

import org.json.JSONObject;

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

    public static TableLayout generateTabularInfo(JSONObject jsonObject, String key, Context context){
        // Parse the response
        // Initialize TableLayout
        TableLayout tableLayout = new TableLayout(context);
        try {

            // Split the matrix data by rows and columns
            String matrixData = jsonObject.getString(key);

            String[] rows = matrixData.split("\n");

            for (String row : rows) {
                // Create a new TableRow
                TableRow tableRow = new TableRow(context);

                // Split the row into individual values (columns)
                String[] columns = row.trim().split("\\s+");

                // Iterate over each column and create a TextView for each cell
                for (String column : columns) {
                    TextView textView = new TextView(context);
                    textView.setText(column);
                    textView.setPadding(5, 5, 5, 5);  // Add some padding
                    tableRow.addView(textView);  // Add the TextView to the TableRow
                }

                // Add the TableRow to the TableLayout
                tableLayout.addView(tableRow);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableLayout;
    }
}
