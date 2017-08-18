package ivring.proxytest.proxy.callback;

/**
 * 诉讼流程接口
 * Created by IVRING on 2017/7/11.
 */

public interface  Isusong {
    // 提交申请
    void submit();
    // 进行举证
    void burden();
    //开始辩护
    void defend();
    //诉讼完成
    void finish();
}
