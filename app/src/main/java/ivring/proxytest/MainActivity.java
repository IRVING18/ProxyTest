package ivring.proxytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Proxy;

import ivring.proxytest.proxy.beidaili.beidailiren;
import ivring.proxytest.proxy.callback.Isusong;
import ivring.proxytest.proxy.daili.dailiren;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //构造一个小明
        Isusong beidailiren = new beidailiren();
        //构造一个动态代理
        dailiren dailiren = new dailiren(beidailiren);
        //获取被代理类小明的ClassLoader
        ClassLoader loader = beidailiren.getClass().getClassLoader();
        //动态构造一个代理律师
        Isusong daili = (Isusong) Proxy.newProxyInstance(loader,new Class[]{Isusong.class},dailiren);
        //律师提交诉讼请求
        daili.submit();
        //律师进行举证
        daili.burden();
        //律师代替小明进行辩护
        daili.defend();
        //完成诉讼
        daili.finish();
    }
}
