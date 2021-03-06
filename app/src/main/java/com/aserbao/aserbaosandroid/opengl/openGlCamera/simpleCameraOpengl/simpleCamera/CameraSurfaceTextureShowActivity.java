package com.aserbao.aserbaosandroid.opengl.openGlCamera.simpleCameraOpengl.simpleCamera;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;

import com.aserbao.aserbaosandroid.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraSurfaceTextureShowActivity extends AppCompatActivity implements SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = "CameraSurfaceTextureShowActivity";
    @BindView(R.id.camera_texture_view)
    TextureView mCameraTextureView;
    public Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_surface_texture);
        ButterKnife.bind(this);
        SurfaceTexture surfaceTexture = new SurfaceTexture(false);
        surfaceTexture.setOnFrameAvailableListener(this);
        mCameraTextureView.post(new Runnable() {
            @Override
            public void run() {
                openCamera(surfaceTexture);
            }
        });
        mCameraTextureView.setSurfaceTexture(surfaceTexture);
    }

    @Override
    protected void onPause() {
        super.onPause();
        release();
    }


    private void openCamera(SurfaceTexture surfaceTexture) {
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewTexture(surfaceTexture);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void release() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @OnClick(R.id.btn_texture_anim)
    public void onViewClicked() {
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.3f,1.0f);
        PropertyValuesHolder valuesHolder4 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.3f,1.0f);
        PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("rotationX", 0.0f, 2 * 360.0f, 0.0F);
        PropertyValuesHolder valuesHolder5 = PropertyValuesHolder.ofFloat("rotationY", 0.0f, 2 * 360.0f, 0.0F);
        PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.7f, 1.0F);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mCameraTextureView, valuesHolder1, valuesHolder2, valuesHolder3,valuesHolder4,valuesHolder5);
        objectAnimator.setDuration(5000).start();
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
    }
}
