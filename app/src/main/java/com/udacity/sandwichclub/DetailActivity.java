package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private String tempList = "";

    TextView alsoKnownAs_TV, origin_TV, ingredients_TV, description_TV;
    ImageView image_IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        alsoKnownAs_TV = findViewById(R.id.also_known_tv);
        origin_TV = findViewById(R.id.origin_tv);
        ingredients_TV = findViewById(R.id.ingredients_tv);
        description_TV = findViewById(R.id.description_tv);
        image_IV = findViewById(R.id.image_iv);

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
                .into(image_IV);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        for (String a: alsoKnownAsList) tempList += a + " .:. ";
        if (tempList == "") tempList = getString(R.string.detail_missing);
        alsoKnownAs_TV.setText(tempList);
        tempList = "";

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            origin_TV.setText(R.string.detail_missing);
        } else {
            origin_TV.setText(sandwich.getPlaceOfOrigin());
        }

        List<String> ingredientsList = sandwich.getIngredients();
        for (String i: ingredientsList) tempList += i + " .:. ";
        if (tempList == "") tempList = getString(R.string.detail_missing);
        ingredients_TV.setText(tempList);
        tempList = "";

        if (sandwich.getDescription().isEmpty()) {
            description_TV.setText(R.string.detail_missing);
        } else {
            description_TV.setText(sandwich.getDescription());
        }
    }
}
