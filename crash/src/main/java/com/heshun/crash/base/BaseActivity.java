package com.heshun.crash.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.heshun.crash.crash.ActivityStack;

public class BaseActivity extends AppCompatActivity
{
  protected void onCreate(@Nullable Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ActivityStack.addActivity(this);
  }

  protected void onDestroy()
  {
    ActivityStack.removeActivity(this);
    super.onDestroy();
  }
}

/* Location:           C:\Users\Administrator\Desktop\文档大全\反编译\jd-gui\jd-gui\classes-dex2jar.jar
 * Qualified Name:     com.heshun.newcrash.base.BaseActivity
 * JD-Core Version:    0.6.2
 */