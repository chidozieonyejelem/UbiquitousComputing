package com.example.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private JSONArray recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FragmentManager manager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container, new RecipesFragment());
            transaction.commit();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && manager.findFragmentById(R.id.detail_container) == null) {
            showIngredients(0);
        }
    }

    public JSONArray getRecipes() {
        if (recipes == null) {
            try {
                Scanner scan = new Scanner(getAssets().open("recipes.json"));
                String json = "";
                while (scan.hasNextLine()) {
                    json = json + scan.nextLine();
                }
                scan.close();
                JSONObject jsonObject = new JSONObject(json);
                recipes = jsonObject.getJSONArray("recipes");
            } catch (Exception e) {
                recipes = new JSONArray();
            }
        }
        return recipes;
    }

    public void showIngredients(int position) {
        IngredientsFragment detailFragment = IngredientsFragment.newInstance(position);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.detail_container, detailFragment);
        } else {
            transaction.replace(R.id.fragment_container, detailFragment);
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}