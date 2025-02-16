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
    List<CategorySearchModel> categorySearchModels;

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
        setUpPresenter();
        searchPresenter.getSearchedCategories();
        setUpFilterChips();
        setupSearchEditText();
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
                filterCategories(query);
                break;
            case "Country":
                filterAreas(query);
                break;
            case "Ingredient":
                filterIngredients(query);
                break;
        }
    }
    private void filterCategories(String query) {
        List<CategorySearchModel> filteredList = new ArrayList<>();
        for (CategorySearchModel category : originalCategoryList) {
            if (category.getStrCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(category);
            }
        }
        searchAdapter.setCategories(filteredList);
    }

    private void filterAreas(String query) {
        List<AreaModel> filteredList = new ArrayList<>();
        for (AreaModel area : originalAreaList) {
            if (area.getStrArea().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(area);
            }
        }
        areaAdapter.setAreas(filteredList);
    }

    private void filterIngredients(String query) {
        List<IngredientModel> filteredList = new ArrayList<>();
        for (IngredientModel ingredient : originalIngredientList) {
            if (ingredient.getStrIngredient().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(ingredient);
            }
        }
        ingredientAdapter.setIngredients(filteredList);
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
    private List<CategorySearchModel> originalCategoryList = new ArrayList<>();
    private List<AreaModel> originalAreaList = new ArrayList<>();
    private List<IngredientModel> originalIngredientList = new ArrayList<>();

    @Override
    public void showSearchByCategory(List<CategorySearchModel> models) {
        if (models != null) {
            originalCategoryList = new ArrayList<>(models);
            searchAdapter.setCategories(models);
        }
    }

    @Override
    public void showSearchByArea(List<AreaModel> models) {
        if (models != null) {
            originalAreaList = new ArrayList<>(models);
            areaAdapter.setAreas(models);
        }
    }

    @Override
    public void showSearchByIngredient(List<IngredientModel> models) {
        if (models != null) {
            originalIngredientList = new ArrayList<>(models);
            ingredientAdapter.setIngredients(models);
        }
    }

    @Override
    public void showErrorMsg(String err) {

    }
}