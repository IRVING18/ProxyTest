package ivring.proxy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * $desc
 * Created by IVRING on 2017/7/12.
 */
//让注解在运行期间也存活
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WangZheng {
    enum ArgType{normal,atom}
    ArgType methodType() default ArgType.atom;

//    是否伴随加载框的出现。
    boolean withDialog() default false;

    boolean withCancelableDialog() default false;
}
