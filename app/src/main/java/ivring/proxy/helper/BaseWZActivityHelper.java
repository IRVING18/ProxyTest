package ivring.proxy.helper;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import ivring.proxy.ControlFactory;
import ivring.proxy.ModelMap;
import ivring.proxy.control.BaseControl;
import ivring.proxy.handler.BaseHandler;
import ivring.proxy.message.MessageProxy;

import static android.content.ContentValues.TAG;

/**
 * 这个是用来在BaseActivity中初始化MessageProxy，mcontrol，ModeMap。
 * Created by IVRING on 2017/7/12.
 */

public class BaseWZActivityHelper<T extends BaseControl,R extends AppCompatActivity> {
    protected T mControl;
//    这个是BaseHandler的代理类，
    protected MessageProxy messageProxy;
//    定义集合对象，这个集合用于被WangZheng注解的函数执行后得到的值进行存储。
    protected ModelMap mModel;
//    其实就是Activity或者Fragment对象
    protected R mReferenceObj;
    protected BaseHandler mHandler;

    public BaseWZActivityHelper(R mReferenceObj, BaseHandler mHandler) {
        this.mReferenceObj = mReferenceObj;
        this.mHandler = mHandler;
    }

    /**
     * 在BaseActivity中初始化Control。
     * @return
     */
    public T getControl() {
        return mControl;
    }


    /**
     * 在BaseActivity中初始化Handler代理类用。
     * @return
     */
    public MessageProxy getMessageProxy() {
        return messageProxy;
    }


    /**
     * 在BaseActivity中初始化存储数据的集合。
     * @return
     */
    public ModelMap getModel() {
        return mModel;
    }


    /**
     * 初始化方法。
     */
    public void initHelper(){
        Class clazz;
//        其实就是具体的继承BaseActivity的Activity和Fragment的对象的字节码文件。
        clazz = mReferenceObj.getClass();
        generateControl(clazz);
        if (mControl == null) {
            generateControl(clazz.getSuperclass());
        }
    }

    /**
     *
     * @param clazz
     */
    private void generateControl(Class clazz) {
        Log.i(TAG, "clazz: "+clazz);//class zw.lxhf.com.ftth.ui.activity.LoginActivity
//        getGenericSuperclass()获得带有泛型的父类
//        Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        Type type = clazz.getGenericSuperclass();
        Log.i(TAG, "getGenericSuperclass: "+type);// zw.lxhf.com.ftth.ui.activity.BaseActivity<zw.lxhf.com.ftth.proxy.control.SingleControl>
//        ParameterizedType参数化类型，即泛型
        if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) type;
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
            Type[] arrayClasses = p.getActualTypeArguments();
            Log.i(TAG, "getActualTypeArguments: "+ Arrays.toString(arrayClasses));// [class zw.lxhf.com.ftth.proxy.control.SingleControl]
            for (Type item : arrayClasses) {
                if (item instanceof Class) {
                    Class<T> tClass = (Class<T>) item;
                    Log.i(TAG, "(Class<T>) item: "+tClass);//class zw.lxhf.com.ftth.proxy.control.SingleControl
//                    tClass.getSuperclass()获取父类字节码文件
//                    判断这个泛型的类是否是BaseControl或者其子类。
                    if (tClass.equals(BaseControl.class) || (tClass.getSuperclass() != null && tClass.getSuperclass().equals(BaseControl.class))) {
//                        创建Handler的代理类MessageProxy
                        messageProxy = new MessageProxy(mHandler);
//                        动态代理的方式创建Control对象。
                        mControl = ControlFactory.getControlInstance(tClass,
                                messageProxy);
                        mModel = new ModelMap();
//                        设置
                        mControl.setModelMap(mModel);
                        return;
                    }
                }
            }
        }
    }
}
