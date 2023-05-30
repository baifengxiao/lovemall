package cn.sc.love.common.cache;

import java.lang.annotation.*;

/**
 * @author YPT
 * @create 2023-05-30-8:04
 * @Target:用于描述注解的使用范围
 * @Retention: 表示注解的生命周期:source，源码，class，字节码文件中，runtime，运行时期
 * @Inherited：表示被该注解修饰的子类会不会继承GmallCache
 * @Documented:javadoc可以生成文档
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface GmallCache {

    //缓存的前缀
    String prefix() default "cache:";

    //缓存的后缀
    String suffix() default ":info";
}
