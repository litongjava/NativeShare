package com.litongjava.naviteshare;


import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.litongjava.naviteshare.activity.SingleFragmentActivity;
import com.litongjava.naviteshare.fragment.MainFragment;

public class MainActivity extends SingleFragmentActivity {

  private Fragment fragment = MainFragment.newInstance();
  @Override
  protected Fragment createFragment() {
    return fragment;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    fragment.onActivityResult(requestCode,resultCode,data);
    super.onActivityResult(requestCode, resultCode, data);
  }
}