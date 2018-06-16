package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.RecipeManipulationViewListAdapter;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.adapter.itemWrapper.RecipeManipulationViewItemWrapper;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by MarjanaKarzek on 16.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 16.06.2018
 */
public class RecipeManipulationActivity extends BaseActivity implements RecipeManipulationContract.View {

    @Inject
    RecipeManipulationContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.bottom_sheet_image_picker) LinearLayout bottomSheet;

    private BottomSheetBehavior bottomSheetBehavior;

    private int recipeId;
    private boolean editMode = false;

    private Bitmap image;
    private int CAMERA = 0;
    private int GALLERY = 1;

    public static Intent newIntent(Context context, @Nullable Integer recipeId) {
        Intent intent = new Intent(context, RecipeManipulationActivity.class);

        if (recipeId != null)
            intent.putExtra("recipeId", recipeId.intValue());

        return intent;
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    public void setPresenter(RecipeManipulationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_recipe, menu);

        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_manipulation);
        ButterKnife.bind(this);

        presenter.setView(this);
        setupSupportActionBar();
        setupRecyclerView();

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().containsKey("recipeId")) {
                    recipeId = getIntent().getExtras().getInt("recipeId");

                    editMode = true;
                    presenter.startEditMode(recipeId);

                } else {
                    presenter.start();
                }
            } else {
                presenter.start();
            }
        } else {
            presenter.start();
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setHideable(true);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeManipulationViewListAdapter(presenter, presenter));
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white, null));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home)
            finish();
        else if (item.getItemId() == R.id.recipe_manipulation_camera) {
            presenter.onCameraIconClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void closeBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void openBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    public void setupViewsInRecyclerView(RecipeDisplayModel displayModel) {
        ArrayList<RecipeManipulationViewItemWrapper> views = new ArrayList<>();
        if(displayModel.getPhoto() != null){
            views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.PHOTO_VIEW, BitmapFactory.decodeByteArray(displayModel.getPhoto(), 0, displayModel.getPhoto().length)));
        }
        views.add(new RecipeManipulationViewItemWrapper(RecipeManipulationViewItemWrapper.ItemType.INGREDIENTS_TITLE_AND_PORTIONS_VIEW,displayModel.getPortions()));

        ((RecipeManipulationViewListAdapter)recyclerView.getAdapter()).setList(views);
    }

    @OnClick(R.id.image_source_camera) public void onOpenCameraClicked(){
        presenter.onOpenCameraClicked();
    }

    @OnClick(R.id.image_source_gallery) public void onOpenGalleryClicked(){
        presenter.onOpenGalleryClicked();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    presenter.addPhotoToRecipe(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            presenter.addPhotoToRecipe(bitmap);
        }
    }

}
