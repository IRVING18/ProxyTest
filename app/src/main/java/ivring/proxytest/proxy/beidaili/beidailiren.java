package ivring.proxytest.proxy.beidaili;

import ivring.proxytest.proxy.callback.Isusong;

/**
 * $desc
 * Created by IVRING on 2017/7/11.
 */

public class beidailiren implements Isusong {
    @Override
    public void submit() {
        //老板拖欠小明工资 小明只好仲裁
        System.out.println("老板拖欠工资！特此申请仲裁！");


    }
    @Override
    public void burden() {
        //小明证据充足，不怕告不赢
        System.out.println("这是合同书和过去一年的银行工资流水！");


    }
    @Override
    public void defend() {
        //铁证如山，辩护也没啥好的
        System.out.println("证据确凿！不需要在说啥了！");

    }
    @Override
    public void finish() {
        //结果也是肯定的，必赢
        System.out.println("诉讼成功！判决即日起七天内结算工资！");
    }
}
