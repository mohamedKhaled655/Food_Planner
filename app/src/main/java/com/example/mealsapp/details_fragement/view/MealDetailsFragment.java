package com.example.mealsapp.details_fragement.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mealsapp.R;
import com.example.mealsapp.data.Models.MealDetailsModel;
import com.example.mealsapp.data.Models.MealModel;
import com.example.mealsapp.data.local.MealLocalDataSourceImpl;
import com.example.mealsapp.data.network.MealRemoteDataSourceImpl;
import com.example.mealsapp.data.repo.MealRepository;
import com.example.mealsapp.data.repo.MealRepositoryImpl;
import com.example.mealsapp.details_fragement.presenter.MealDetailsPresenter;
import com.example.mealsapp.details_fragement.presenter.MealDetailsPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsFragment extends Fragment implements DetailsMealView {
    private static final String TAG = "MealDetailsFragment";
    private MealDetailsPresenter mealDetailsPresenter;
    ImageView imageView, imgCountry;
    TextView desc, category, txtCountry, detailsName;
    IngredientsDetailsAdapter ingredientsDetailsAdapter;
    private RecyclerView recyclerView;
    ImageButton img_arrowBack;
    WebView webView;

    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_info_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imageView = view.findViewById(R.id.img_info_image);
        imgCountry = view.findViewById(R.id.img_country);
        webView = view.findViewById(R.id.video_title);

        desc = view.findViewById(R.id.txt_description_details);
        category = view.findViewById(R.id.txt_cat_details);
        txtCountry = view.findViewById(R.id.txt_country_name);
        detailsName = view.findViewById(R.id.txt_details_name);
        recyclerView = view.findViewById(R.id.rv_ingredient_item);


        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        ingredientsDetailsAdapter = new IngredientsDetailsAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(ingredientsDetailsAdapter);


        img_arrowBack = view.findViewById(R.id.btn_arr_back);
        img_arrowBack.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view1);
            navController.navigateUp();
        });


        MealModel mealInfo = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealInfo();
        Toast.makeText(getContext(), mealInfo.getIdMeal(), Toast.LENGTH_SHORT).show();


        setUpPresenter();
        mealDetailsPresenter.getMealDetails(mealInfo.getIdMeal());


        setupWebView();
    }

    private void setUpPresenter() {
        MealRepository mealRepository = MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(requireContext()));
        mealDetailsPresenter = new MealDetailsPresenterImpl(this, mealRepository);
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void showAllMealsDetails(List<MealDetailsModel> models) {
        Log.d(TAG, "MealDetailsModel received: " + models.toString());
        Toast.makeText(getContext(), "MealDetailsModel received:" + models.get(0).getIngredients(), Toast.LENGTH_SHORT).show();


        detailsName.setText(models.get(0).getName());
        category.setText(models.get(0).getCategory());
        desc.setText(models.get(0).getInstructions());
        txtCountry.setText(models.get(0).getArea());


        Glide.with(this)
                .load(models.get(0).getImageUrl())
                .into(imageView);


        String urlCountry = models.get(0).getArea().substring(0, 2).toUpperCase();
        Glide.with(this)
                .load("https://flagsapi.com/" + urlCountry + "/shiny/64.png")
                .placeholder(R.drawable.loadimg)
                .error(R.drawable.flag)
                .into(imgCountry);


        ingredientsDetailsAdapter.updateIngredients(models.get(0).getIngredients());


        String youtubeUrl = models.get(0).getYoutubeUrl();
        if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
            String videoId = youtubeUrl.substring(youtubeUrl.lastIndexOf("=") + 1);
            String embedUrl = "https://www.youtube.com/embed/" + videoId;
            String videoHtml = "<html><body style=\"margin:0;padding:0;\">" +
                    "<iframe width=\"100%\" height=\"100%\" src=\"" + embedUrl +
                    "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            webView.loadData(videoHtml, "text/html", "utf-8");
            webView.setVisibility(View.VISIBLE);
        }else{
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorMsg(String err) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(err).setTitle("An Error Occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
