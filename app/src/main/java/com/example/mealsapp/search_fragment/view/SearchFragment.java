package com.example.mealsapp.search_fragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.AreaModel;
import com.example.mealsapp.data.Models.CategoryModel;
import com.example.mealsapp.data.Models.CategorySearchModel;
import com.example.mealsapp.data.Models.IngredientModel;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.example.mealsapp.home_fragment.presenter.HomePresenterImpl;
import com.example.mealsapp.home_fragment.view.CategoryAdapter;
import com.example.mealsapp.search_fragment.presenter.SearchPresenter;
import com.example.mealsapp.search_fragment.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SearchFragment extends Fragment implements SearchMealView{

    private static final String TAG = "SearchFragment";
    private EditText searchEditText;
    private String currentFilter = "Category";
    private SearchAdapter searchAdapter;
    private AreaAdapter areaAdapter;
    private IngredientAdapter ingredientAdapter;
    private SearchPresenter searchPresenter;
    private ChipGroup chipGroup;
    private RecyclerView recyclerView;
    private ProgressBar loadingIndicator;

    private Map<String, List<CategoryModel>> searchByCategory;

    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        chipGroup = view.findViewById(R.id.chip_group);
        chipGroup.setSingleSelection(true);
        recyclerView = view.findViewById(R.id.rv_search_ingredients);

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        searchAdapter = new SearchAdapter(getContext(),new ArrayList<>());
        recyclerView.setAdapter(searchAdapter);
        areaAdapter = new AreaAdapter(getContext(),new ArrayList<>());
        recyclerView.setAdapter(areaAdapter);
        ingredientAdapter = new IngredientAdapter(getContext(),new ArrayList<>());
        recyclerView.setAdapter(ingredientAdapter);
        searchEditText = view.findViewById(R.id.search_edit_text);
        loadingIndicator=view.findViewById(R.id.loading_indicator);
        setUpPresenter();
        //searchPresenter.getSearchedCategories();
        searchPresenter.getSearchedIngredient();
        //searchPresenter.getSearchedArea();
        setUpFilterChips();
        showLoading();
        setupSearchEditText();
    }
     public void showLoading() {
        loadingIndicator.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

     public void hideLoading() {
        loadingIndicator.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void setupSearchEditText() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                filterResults(s.toString());
            }
        });
    }
    private void filterResults(String query) {
        switch (currentFilter) {
            case "Category":
                searchPresenter.filterCategoriesByQuery(query);
                break;
            case "Country":
                searchPresenter.filterAreasByQuery(query);
                break;
            case "Ingredient":
                searchPresenter.filterIngredientsByQuery(query);
                break;
        }
    }


    private void setUpPresenter() {

        MealRepository mealRepository= MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(requireContext()));
        searchPresenter=new SearchPresenterImpl(this,mealRepository);
    }

    private void setUpFilterChips() {
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    currentFilter = chip.getText().toString();
                    searchEditText.setText("");
                    showLoading();

                    switch (currentFilter) {
                        case "Category":
                            recyclerView.setAdapter(searchAdapter);
                            searchPresenter.getSearchedCategories();
                            break;
                        case "Country":
                            recyclerView.setAdapter(areaAdapter);
                            searchPresenter.getSearchedArea();
                            break;
                        case "Ingredient":
                            recyclerView.setAdapter(ingredientAdapter);
                            searchPresenter.getSearchedIngredient();
                            break;
                    }
                }
            });
        }
    }


    @Override
    public void showSearchByCategory(List<CategorySearchModel> models) {
        hideLoading();
        if (models != null) {
            searchAdapter.setCategories(models);
        }
    }

    @Override
    public void showSearchByArea(List<AreaModel> models) {
        hideLoading();
        if (models != null) {
            areaAdapter.setAreas(models);
        }
    }

    @Override
    public void showSearchByIngredient(List<IngredientModel> models) {
        hideLoading();
        if (models != null) {
            ingredientAdapter.setIngredients(models);
        }
    }

    @Override
    public void showErrorMsg(String err) {
        Toast.makeText(requireContext(), err, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchPresenter != null) {
            searchPresenter.onDestroy();
        }
    }
}