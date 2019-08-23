### [final 关键字](https://www.cnblogs.com/xiaoxiaoyihan/p/4974273.html)
> 使用的三种场合：变量、方法以及类。 

- 修饰变量
> - 普通成员变量要么在定义字段时使用一个表达式（初始化值），要么在构建器中初始化值。
> - 静态类变量必须在申明时就初始化值；
> - 当变量（包括参数变量）是对象引用时，对象不能变（对象的内存地址），对象的属性可以修改；
```
public class Person {
    private static final String NICK = "cxg"; // 静态类变量只能在申明变量时初始化值；
    private final String name = "hello";
}

public class Person {
    private final String name;
    public Person() {
        this.name = "hello";
    }
    public Person(String name) {
        this.name = name;
    }
}
```

- 修饰方法
> 类内所有的private方法都默认为final；
> - 防止任何继承类覆盖方法，不可被覆盖和改写；
> - 提高程序执行的效率

- 修饰类
> 类定义成final后，结果只是禁止被继承。由于禁止了继承，所以一个final类中的所有方法都默认为final。 