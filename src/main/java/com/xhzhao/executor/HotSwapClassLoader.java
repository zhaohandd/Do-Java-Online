package com.xhzhao.executor;

/**
 * @author xhzhao
 * 为了多次载入执行类而加入的加载类
 * 把defineClass方法开放出来，只有外部显式调用的时候才会使用loadByte方法
 * 由虚拟机调用时，仍然按照原来的双亲委派规则使用loadClass方法进行类加载
 */
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classBytes) {
        //生成字节码文件
        return defineClass(null, classBytes, 0, classBytes.length);
    }
}
