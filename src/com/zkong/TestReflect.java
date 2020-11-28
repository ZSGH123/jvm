package com.zkong;

import com.zkong.sout.CoolWater;
import com.zkong.sout.Water;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射测试
 */
public class TestReflect {

    public static void main(String[] args) throws Exception {
        Water water = new Water();
        System.out.println(water);
        Class<Water> waterClass = (Class<Water>) water.getClass();
        Class<?>[] classes = waterClass.getClasses();

        System.out.println("water.getClass()===" + waterClass);
        System.out.println("water.getTypeName()===" + waterClass.getTypeName());
        System.out.println("Integer.TYPE==" + Integer.TYPE);
        System.out.println(Integer.TYPE.equals(int.class));
        System.out.println(Integer.TYPE == int.class);
        //isPrimitive 判断是否是基本类型的字节码
        System.out.println(Integer.TYPE.isPrimitive());
        System.out.println("void.class==Void.TYPE:" + (void.class == Void.TYPE));

        //测试  instanceof     isInstance isAssignableFrom
        System.out.println(new CoolWater() instanceof Water);
        System.out.println(Water.class.isInstance(new CoolWater()));
        System.out.println(Water.class.isAssignableFrom(Water.class));
        System.out.println(Water.class.isAssignableFrom(CoolWater.class));


        //测试构造函数
        Class<CoolWater> coolWaterClass = CoolWater.class;
        Constructor<CoolWater> coolWaterConstructor = coolWaterClass.getConstructor(String.class);
        Constructor<CoolWater> coolWaterConstructorWithoutParams = coolWaterClass.getConstructor();
        CoolWater coolWater = coolWaterConstructor.newInstance("fsdfs");
        CoolWater coolWater1 = coolWaterConstructorWithoutParams.newInstance();
        System.out.println("====coolWater" + coolWater);
        System.out.println("====coolWater1" + coolWater1);

        //测试filed 成员变量
        Field field = coolWaterClass.getDeclaredField("hight");
        System.out.println("属性:" + field.getName());
        //设置可访问
        field.setAccessible(true);
        field.set(coolWater1, "rwerewtwet");
        System.out.println("coolWater属性值:" + field.get(coolWater));
        System.out.println("coolWater1属性值:" + field.get(coolWater1));

        //测试method成员方法,静态方法

        Method[] methods = coolWaterClass.getMethods();
        System.out.println("==getMethods==");
        for (Method method : methods) {
            System.out.println(method.toString());
        }
        System.out.println("==getMethods==");

        //测试method成员方法,静态方法
//        getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
//        getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。

//        同样类似的还有getConstructors()和getDeclaredConstructors()、
//        getMethods()和getDeclaredMethods()，这两者分别表示获取某个类的方法、构造函数。

//        public Method[] getMethods()返回某个类的所有公用（public）方法包括其继承类的公用方法，
//        当然也包括它所实现接口的方法。
//        public Method[] getDeclaredMethods()对象表示的类或接口声明的所有方法,
//        包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。当然也包括它所实现接口的方法。

        Method[] declaredMethods = coolWaterClass.getDeclaredMethods();

        System.out.println("==getDeclaredMethods==");
        for (Method method : declaredMethods) {
            System.out.println(method.toString());
            if(method.getName().equals("print")){
                method.invoke(coolWater,new String("coolWater"));
            }
            if(method.getName().equals("print1")){
                method.invoke(coolWater,(Object) new String[]{"4324234"});
                method.invoke(coolWater,new Object[]{new String[]{"4324234"}});
            }
        }
        System.out.println("==getDeclaredMethods==");

        System.out.println("====");
        for (Class cls : classes) {
            System.out.println(cls.getTypeName());
        }
        System.out.println("====");

    }
}
