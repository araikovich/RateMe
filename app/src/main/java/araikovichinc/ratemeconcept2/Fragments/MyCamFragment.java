package araikovichinc.ratemeconcept2.Fragments;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;

import araikovichinc.ratemeconcept2.R;
import araikovichinc.ratemeconcept2.Utils.CustomViewPager;

/**
 * Created by Tigran on 03.12.2017.
 */

public class MyCamFragment extends Fragment implements View.OnClickListener {

    final String LOG = "MyLogs";
    int camId;

    ImageView takePhotoBtn, galleryBtn, changeCamBtn, backBtn;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    HolderCallBack holderCallBack;
    CustomViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_cam, container, false);
        ////////////////////init/////////////////////////////
        takePhotoBtn = (ImageView)v.findViewById(R.id.take_photo_btn);
        galleryBtn = (ImageView)v.findViewById(R.id.camera_gallery);
        changeCamBtn = (ImageView)v.findViewById(R.id.change_cam_btn);
        backBtn = (ImageView)v.findViewById(R.id.camera_back_right_btn);
        viewPager = (CustomViewPager) getActivity().findViewById(R.id.home_view_pager);
        takePhotoBtn.setOnClickListener(this);
        galleryBtn.setOnClickListener(this);
        changeCamBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        surfaceView = (SurfaceView)v.findViewById(R.id.surface_view);
        camId = 0;
        //////////////////////////////////////////////////////////////

        return v;
    }

    @Override
    public void onPause() {
        surfaceView.setVisibility(View.GONE);
        if(camera!=null){
            camera.stopPreview();
            camera.release();
        }
        camera = null;
        surfaceHolder.removeCallback(holderCallBack);
        surfaceHolder = null;
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG, "Start Camera in Resume");
        startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        surfaceView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_photo_btn:
                camera.takePicture(null, null, pictureCallback);
                break;
            case R.id.camera_back_right_btn:
                viewPager.setPagingEnabled(true);
                viewPager.setCurrentItem(1);
                break;
            case R.id.camera_gallery:

                break;
            case R.id.change_cam_btn:
                if(camera!=null)
                    camera.release();
                if(camId == Camera.CameraInfo.CAMERA_FACING_BACK){
                    camId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                }
                else {
                    camId = Camera.CameraInfo.CAMERA_FACING_BACK;
                }
                camera = Camera.open(camId);

                setCameraDisplayOrientation(camId);
                try {

                    camera.setPreviewDisplay(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setPreviewSize(true);
                camera.startPreview();
                break;
        }
    }

    private void startCamera(int cameraId) {
        Log.d(LOG, "start camera");
        if (camera != null) {
            camera.stopPreview();
            camera.release();
        }
        surfaceHolder = surfaceView.getHolder();
        holderCallBack = new HolderCallBack();
        surfaceHolder.addCallback(holderCallBack);
        Log.d(LOG, "callBack added");
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        setPreviewSize(true);
    }

    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            surfaceHolder.removeCallback(holderCallBack);
            Bundle bundle = new Bundle();
            bundle.putByteArray("photo", data);
            bundle.putInt("camId", camId);
            PictureFragment pictureFragment = new PictureFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            pictureFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.camera_container, pictureFragment).commit();
        }
    };

    void setPreviewSize(boolean fullScreen) {

        // получаем размеры экрана
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        boolean widthIsMax = display.getWidth() > display.getHeight();

        // определяем размеры превью камеры
        Camera.Size size = camera.getParameters().getPreviewSize();

        RectF rectDisplay = new RectF();
        RectF rectPreview = new RectF();

        // RectF экрана, соотвествует размерам экрана
        rectDisplay.set(0, 0, display.getWidth(), display.getHeight());

        // RectF первью
        if (widthIsMax) {
            // превью в горизонтальной ориентации
            rectPreview.set(0, 0, size.width, size.height);
        } else {
            // превью в вертикальной ориентации
            rectPreview.set(0, 0, size.height, size.width);
        }

        Matrix matrix = new Matrix();
        // подготовка матрицы преобразования
        if (!fullScreen) {
            // если превью будет "втиснут" в экран (второй вариант из урока)
            matrix.setRectToRect(rectPreview, rectDisplay,
                    Matrix.ScaleToFit.START);
        } else {
            // если экран будет "втиснут" в превью (третий вариант из урока)
            matrix.setRectToRect(rectDisplay, rectPreview,
                    Matrix.ScaleToFit.START);
            matrix.invert(matrix);
        }
        // преобразование
        matrix.mapRect(rectPreview);

        // установка размеров surface из получившегося преобразования
        surfaceView.getLayoutParams().height = (int) (rectPreview.bottom);
        surfaceView.getLayoutParams().width = (int) (rectPreview.right);
    }

    void setCameraDisplayOrientation(int cameraId) {
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result = 0;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            result = ((360 - degrees) + info.orientation);
        } else
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = ((360 - degrees) - info.orientation);
            result += 360;
        }
        result = result % 360;
        camera.setDisplayOrientation(result);
    }

    class HolderCallBack implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            camera.stopPreview();
            setCameraDisplayOrientation(camId);
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
