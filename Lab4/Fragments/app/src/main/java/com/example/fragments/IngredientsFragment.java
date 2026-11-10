package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IngredientsFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private int position;
    public IngredientsFragment() {
    }

    public static IngredientsFragment newInstance(int position) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        MainActivity activity = (MainActivity) getActivity();

        TextView textName = view.findViewById(R.id.textName);
        TextView textIngredients = view.findViewById(R.id.textIngredients);
        TextView textMethod = view.findViewById(R.id.textMethod);

        try {
            JSONObject recipe = activity.getRecipes().getJSONObject(position);
            String ingredients = "";
            JSONArray ingredientList = recipe.getJSONArray("ingredients");
            for (int i = 0; i < ingredientList.length(); i++) {
                ingredients = ingredients + "- " + ingredientList.getString(i) + "\n";
            }

            String method = "";
            JSONArray methodList = recipe.getJSONArray("method");
            for (int i = 0; i < methodList.length(); i++) {
                method = method + (i + 1) + ". " + methodList.getString(i) + "\n";
            }

            textName.setText(recipe.getString("name"));
            textIngredients.setText(ingredients);
            textMethod.setText(method);
        } catch (JSONException e) {
            textName.setText("Could not load recipe");
        }

        return view;
    }
}