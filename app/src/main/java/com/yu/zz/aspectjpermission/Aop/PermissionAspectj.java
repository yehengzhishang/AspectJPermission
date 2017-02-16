package com.yu.zz.aspectjpermission.Aop;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.yu.zz.annotation.Permission;
import com.yu.zz.aspectjpermission.App;
import com.yu.zz.aspectjpermission.Utils.MPermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * step 3.2 此步骤，杂项较多
 *  记得Manifest加入权限 兼容低版本
 *  manifest记得加入{@link App}
 *
 *
 */
@Aspect
public class PermissionAspectj {

    @Around("execution(@com.yu.zz.annotation.Permission * *(..)) && @annotation(permission)")//在所有 有Permission的方法替换代码
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, final Permission permission) throws Throwable {
        final AppCompatActivity ac = (AppCompatActivity) App.getAppContext().getCurActivity();
        new AlertDialog.Builder(ac)
                .setTitle("提示")
                .setMessage("为了应用可以正常使用，请您点击确认申请权限。")
                .setNegativeButton("取消", null)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MPermissionUtils.requestPermissionsResult(ac, 1, permission.value()
                                , new MPermissionUtils.OnPermissionListener() {
                                    @Override
                                    public void onPermissionGranted() {
                                        try {
                                            joinPoint.proceed();//获得权限，执行原方法
                                        } catch (Throwable e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onPermissionDenied() {
                                        MPermissionUtils.showTipsDialog(ac);
                                    }
                                });
                    }
                })
                .create()
                .show();
    }
}
