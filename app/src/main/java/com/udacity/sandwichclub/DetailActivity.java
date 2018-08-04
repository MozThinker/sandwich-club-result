package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    TextView mAlsoKnown, mDescription, mIngredients,mDetailPlaceOfOrigin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        // Initializing UI
        mAlsoKnown = findViewById(R.id.also_known_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mDetailPlaceOfOrigin = findViewById(R.id.origin_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_no_image)
                .into(ingredientsIv);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        mDescription.setText(sandwich.getDescription());
        mIngredients.setText(sandwich.getIngredients().toString()
                .replace("[", "\u2022  ")
                .replace("]", "")
                .replace(", ", "\n"+"\u2022  "));
        mDetailPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        mAlsoKnown.setText(sandwich.getAlsoKnownAs().toString()
                .replace("[]", getString(R.string.name_not_available))
                .replace("[", "\u2022  ")
                .replace("]", "")
                .replace(", ", "\n"+"\u2022  "));

    }
}
