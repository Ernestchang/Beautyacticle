package com.nicodelee.beautyarticle.ui.view.activity;

import android.os.Bundle;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nicodelee.beautyarticle.R;
import com.nicodelee.beautyarticle.app.APP;
import com.nicodelee.beautyarticle.app.BaseAct;
import com.nicodelee.beautyarticle.bus.CropEvent;
import com.nicodelee.beautyarticle.utils.DevicesUtil;
import com.nicodelee.view.CropImageView;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by alee on 2015/9/4.
 */
public class CropAct extends BaseAct {

  @Bind(R.id.cropImageView) CropImageView mCropImageView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected int getLayoutResId() {
    return R.layout.act_corp_photo;
  }

  @Override protected void initView() {
  }

  @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  public void onEvent(CropEvent cropEvent) {
    mCropImageView.setCropMode(cropEvent.getCropMode());
    mCropImageView.setImageBitmap(
        APP.getInstance().imageLoader.loadImageSync("file:///" + cropEvent.getImagePath(),
            new ImageSize(DevicesUtil.screenWidth, DevicesUtil.screenHeight)));
  }

  @OnClick({ R.id.corp_done, R.id.corp_cancel }) public void Click(View view) {
    switch (view.getId()) {
      case R.id.corp_done:
        if (mCropImageView.getCroppedBitmap() != null) {
          EventBus.getDefault().postSticky(mCropImageView.getCroppedBitmap());
        }
        finish();
        break;
      case R.id.corp_cancel:
        finish();
        break;
    }
  }
}
