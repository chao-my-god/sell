package com.chao.sell.utils;

import java.util.Random;


public class KeyUtil {
    /**
     * 获取唯一的键值
     * 格式为当前时间+随机数
     * synchronized保证多线程时不重复
     * @return
     */
    //static方法被调用时可以直接用类名调用，无需实例化对象
    //静态方法无法访问非静态变量和方法
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
