package com.yu.zz.aspectjpermission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.yu.zz.annotation.LogTest;
import com.yu.zz.annotation.Permission;

/**
 * step 1 创建项目 (基本设定)
 *
 * step 2  moudle BuildSrc（moudle的名字最好是这个） 为Gradle提供aspectJ插件
 *  2.1 新建moudle ,新建时 可以选择任一moudle，这并不重要
 *  2.2 moulde设置，删除无用的文件，新增文件夹
 *  在Project下所显示结构
 *  BuildSrc - -src -main -groovy
 *           |
 *           -resources - META-INF.gradle-plugin
 *           |
 *           build.gradle
 *  2.3 build.gradle编写
 *  2.4 在groovy 下新建 包（包名随你喜欢 标注1 ） 包下新建Plugin(new --file -xxx.groovy)
 *  2.5 在gradle-plugin下新建 new --file --xxxx.properties(这里的名字很重要，请细心编写，在些做为 标注2 )
 *    里面的内容为 implementation-class=（标注1 的包名 +你的类名）
 *  2.6 clean Project  完成后，点击Android studio右边Gradle 按钮，选择 你的项目-：buildsrc-tasks-upload - uplaodArchives
 *      双击 uploadArchives(等待As完成，这个过程，是将这个Plugin发布到本地 生成jar的过程，方便在Project build gradle调用)
 *  2.7 引入自定义的Plugin
 *  2.7.1 Project build.gradle 更改
 *  2.7.2 App build.gradle 更改 ，引入自定义Plygin
 *
 *  step 3 aspecj 代码逻辑编写
 *      3.1 新建 moudle lib 新建注解 {@link com.yu.zz.annotation.Permission} (后对 App build.gradle加入引用)
 *      3.2 aspectJ 切面逻辑 {@link com.yu.zz.aspectjpermission.Aop.PermissionAspectj}
 *
 *
 *  step 4加入方法 {@link #onCall()} 加入注解 测试
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("权限");
        setSupportActionBar(toolbar);
        //加入点击事件 ，实际结果，并未测试。
        findViewById(R.id.tv_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCall();
            }
        });

        onLog();
    }

    //LogTest测试代码
    @LogTest
    private void onLog() {
        Log.e("TAG","原方法执行中");
    }

    /*----------------step 4 始----------------------*/
    @Permission(Manifest.permission.CALL_PHONE)
    void onCall(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + "110");
        intent.setData(data);
        startActivity(intent);
    }
  /*----------------step 4 终----------------------*/


}
