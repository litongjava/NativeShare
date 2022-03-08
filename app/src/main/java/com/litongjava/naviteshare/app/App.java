package com.litongjava.naviteshare.app;

import android.app.Application;

import com.hss01248.glidepicker.GlideIniter;

import me.iwf.photopicker.PhotoPickUtils;

/**
 * @author Ping E Lee
 * @email itonglinux@qq.com
 * @date 2022/3/8
 */
public class App extends Application {

  @Override
  public void onCreate() {
    //初始化PhotoPicker
    PhotoPickUtils.init(getApplicationContext(), new GlideIniter());
    super.onCreate();
  }
}
