package site.wayblog.com.lamdba;

import java.util.function.*;

/**
 * Stream前置知识 - Lamdba
 *
 * Lambda 是一个匿名函数，我们可以把 Lambda表达式理解为是一段可以传递的代码
 * 可以写出更简洁、风格更紧凑的代码
 *
 * @author aidem
 * @date 2021-10-12 22:05
 */
public class Lamdba {


    // ************************ 1. 匿名内部类 ---> Lamdba实现 ************************
    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            System.out.println("通过匿名内部类实现");
        }
    };
    Runnable r2 = ()-> System.out.println("通过Lamdba实现");

    // 解密: 其实，能写成Lamdba的，可以认为都是对原本写成匿名内部类的另一种实现。
    // 要求：接口只有一个方法，且不能是Object的方法，就可以用Lamdba替代书写。
    // 规约：可以在这类接口上添加注解 @FunctionalInterface 它会在编写代码的时候提醒是否满足条件。
    // 技巧：对着上方例子，Lamdba的写法，只需要把run方法整一段代码拷贝，去掉方法名，就是Lamdba写法！
    //        当然，还有很多简化的写法，具体可以看下方枚举。但万变不离其中，认准技巧 ↑↑↑↑↑↑
    // 枚举1: 无参，无返回值，Lambda体只需一条语句  Runnable r2 = ()-> System.out.println("通过Lamdba实现");
    // 枚举2: Lambda需要一个参数  Consumer<String> fun = (arg) -> System.out.println(arg);
    // 枚举3: Lambda只需要一个参数时，参数的小括号可以省略   Consumer<String> fun = arg -> System.out.println(arg);
    // 枚举4: Lambda需要两个参数，并且有返回值
    BinaryOperator<Long> b0 = (x,y) -> {
        System.out.println("Lambda需要两个参数，并且有返回值  ");
        return x + y;
    };
    // 枚举5: 当 Lambda 体只有一条语句时，return 与大括号可以省略
	BinaryOperator<Long> b1 = (x, y) -> x + y;
    // 枚举6: 可以发现，上方其实都基于类型推断，入参都是没声明参数的，但是也可以显示声明。
    BinaryOperator<Long> b3 = (Long x,Long y) -> {
        System.out.println("Lambda需要两个参数，并且有返回值  ");
        return x + y;
    };


	// ************************ 2.Java 内置四大核心函数式接口 ************************
	// 消费型接口
	Consumer<String> c0 = System.out::println;
	// 供给型接口
	Supplier<String> c1 = () -> "1";
	// 供给型接口
    Function<String,Integer> c2 = Integer::valueOf;
    // 断定型接口
	Predicate<Integer> c3 = (a) -> a > 2;

}
