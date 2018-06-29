package de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.dialog.bottomSheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;

/**
 * Created by MarjanaKarzek on 29.06.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.06.2018
 */
public class ImageSelectorBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private OnOpenCameraClickListener cameraClickListener;
    private OnOpenGalleryClickListener galleryClickListener;

    public static ImageSelectorBottomSheetDialogFragment newInstance() {
        return new ImageSelectorBottomSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.snippet_bottom_sheet_image_selector, container,
                false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            cameraClickListener = (OnOpenCameraClickListener) getActivity();
            galleryClickListener = (OnOpenGalleryClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement OnOpenCameraClickListener and OnOpenGaleryClickListener");
        }
    }

    @OnClick(R.id.image_source_camera) void onOpenCameraClicked(){
        cameraClickListener.onOpenCameraClickedInBottomSheet();
        this.dismiss();
    }

    @OnClick(R.id.image_source_gallery) void onOpenGalleryClicked(){
        galleryClickListener.onOpenGalleryClickedInBottomSheet();
        this.dismiss();
    }

    public interface OnOpenCameraClickListener {
        void onOpenCameraClickedInBottomSheet();
    }

    public interface OnOpenGalleryClickListener {
        void onOpenGalleryClickedInBottomSheet();
    }
}
