### final 关键字
> 使用的三种场合：变量、方法以及类。 

- 修饰变量
> - 普通成员变量要么在定义字段时使用一个表达式（初始化值），要么在构建器中初始化值。
> - 静态类变量必须在申明时就初始化值；
> - 当变量是对象引用时，对象不能变（对象的内存地址），对象的属性可以修改；
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

- 修饰参数
> 一种是变量的实际值不会被修改，另一种是在方法内部不能被修改。