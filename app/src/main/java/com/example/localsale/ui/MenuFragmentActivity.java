package com.example.localsale.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.localsale.R;
import com.example.localsale.ui.Navigation.NavigationFragment;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class MenuFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();
    protected abstract Fragment createNavigationFragment();
    private static int REQUEST_PERMISSION_CODE = 1;


    @LayoutRes
    protected int  getLayoutResId(){//重写该方法，设置activity含有多个fragment
        return R.layout.activity_fragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment =fm.findFragmentById(R.id.fragment_container);
        Fragment menuFragment =fm.findFragmentById(R.id.menu_container);
        if(fragment==null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
        if(menuFragment==null){
            menuFragment = createNavigationFragment();
            fm.beginTransaction().add(R.id.menu_container,menuFragment).commit();
        }
        initPermissonRequest(this);
    }
    public void changFragment(int id,Fragment fragment ){
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(id,fragment).commit();
    }

    /*
     * 动态获取本地的读写文件的权限，PERMISSIONS_STORAGE为需要获取的权限
     * */
    private void initPermissonRequest(Context context){


        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        //请求状态码


        //循环申请字符串数组里面的权限，在小米中是直接弹出一个权限框等待用户确认，确认一次既将上面数组里面的权限全部申请
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

    }
}