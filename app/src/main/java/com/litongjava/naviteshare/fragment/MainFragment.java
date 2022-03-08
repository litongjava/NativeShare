package com.litongjava.naviteshare.fragment;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.litongjava.android.utils.dialog.AlertDialogUtils;
import com.litongjava.android.utils.toast.ToastUtils;
import com.litongjava.android.view.inject.annotation.FindViewById;
import com.litongjava.android.view.inject.annotation.OnClick;
import com.litongjava.android.view.inject.utils.ViewInjectUtils;
import com.litongjava.naviteshare.MainActivity;
import com.litongjava.naviteshare.R;
import com.litongjava.naviteshare.share.ShareUtils;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;


public class MainFragment extends Fragment {
  //布局id
  int resourceId = R.layout.fragment_main;
  //日志类
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @FindViewById(R.id.recycler_view)
  public MultiPickResultView recyclerView;



  public static Fragment newInstance() {
    return new MainFragment();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(resourceId, container, false);
    //ViewInjectUtils.injectView(view, this);
    //ViewInjectUtils.injectOnClick(view, this);
    ViewInjectUtils.injectViewAndOnClick(view, this);
    recyclerView.init(getActivity(),MultiPickResultView.ACTION_SELECT,null);
    initPermission();
    return view;
  }

  /**
   * 初始权限
   */
  private void initPermission() {
    //创建Builder
    AcpOptions.Builder builder = new AcpOptions.Builder();

    //创建acpOptions
    AcpOptions acpOptions = builder.setPermissions(
      //写入外部设备权限
      Manifest.permission.WRITE_EXTERNAL_STORAGE,
      //读取外部设备权限
      Manifest.permission.READ_EXTERNAL_STORAGE)
      .setDeniedMessage("DeniedMessage")
      .setDeniedCloseBtn("DeniedCloseBtn")
      .setDeniedSettingBtn("DeniedSettingBtn")
      .setRationalMessage("RationalMessage")
      .setRationalBtn("RationalBtn")
      .build();

    //创建acpListener
    AcpListener acpListener = new AcpListener() {
      @Override
      public void onGranted() {
        ToastUtils.defaultToast(getActivity(), "获取权限成功");
      }

      @Override
      public void onDenied(List<String> permissions) {
        ToastUtils.defaultToast(getActivity(), permissions.toString() + "权限拒绝");
      }
    };
    Acp acp = Acp.getInstance(getActivity());
    acp.request(acpOptions, acpListener);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    recyclerView.onActivityResult(requestCode,resultCode,data);
    PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {

      @Override
      public void onPickSuccess(ArrayList<String> photos, int requestCode) {
        //已经预先做了null或size为0的判断
      }

      @Override
      public void onPreviewBack(ArrayList<String> photos, int requestCode) {

      }

      @Override
      public void onPickFail(String error, int requestCode) {
        Toast.makeText(getActivity(),error,Toast.LENGTH_LONG).show();
      }

      @Override
      public void onPickCancle(int requestCode) {
        Toast.makeText(getActivity(),"取消选择",Toast.LENGTH_LONG).show();
      }
    });
  }

  @OnClick(R.id.btnShare)
  public void btnShare_OnClick(View view){
    ArrayList<String> photos = recyclerView.getPhotos();
    if(photos.size()<1){
      AlertDialogUtils.showWaringDialog(getContext(),"请先选中图片");
    }
    String filePath = photos.get(0);
    File file = new File(filePath);
    ShareUtils.sharePictureToQQFriend(getContext(),file);
  }
}
