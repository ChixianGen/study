### 常用通配符 T，E，K，V，?
- ? 无界通配符，表示不确定的 java 类型
- T (type) 表示具体的一个java类型
- K V (key value) 分别代表java键值中的Key Value
- E (element) 代表Element
---
- ##### 上界通配符 < ? extends E>
> -用 extends 关键字声明，表示参数化的类型可能是所指定的类型，或者是此类型的子类。
上界类型通配符add方法受限，但可以获取列表中的各种类型的数据，并赋值给父类型的引用。
因此如果你想从一个数据类型里获取数据，使用 ? extends 通配符。限定通配符总是包括自己。

- ##### 下界通配符 < ? super E>
> 用 super 进行声明，表示参数化的类型可能是所指定的类型，或者是此类型的父类型，直至 Object
下界类型通配符get方法受限，但可以往列表中添加各种数据类型的对象。因此如果你想把对象写入一个数据结构里，使用 ? super 通配符。
限定通配符总是包括自己。


- ### 总结
- 限定通配符总是包括自己；
- 上界类型通配符：add方法受限；
- 下界类型通配符：get方法受限；
- 如果你想从一个数据类型里获取数据，使用 ? extends 通配符；
- 如果你想把对象写入一个数据结构里，使用 ? super 通配符；
- 如果你既想存，又想取，那就别用通配符；
- 不能同时声明泛型通配符上界和下界；
---
### ? 和 T 的区别
- 可以通过 T 来 确保泛型参数的一致性
```
// 通过 T 来 确保泛型参数的一致性
public <T extends Number> void test(List<T> dest, List<T> src);

//通配符 ? 是不确定的，所以这个方法不能保证两个 List 具有相同的元素类型
public void test(List<? extends Number> dest, List<? extends Number> src);
```
- 类型参数可以多重限定而通配符不行
> 使用 & 符号设定多重边界（Multi Bounds)，指定泛型类型 T 必须是 InterfaceA 和 InterfaceB 的共有子类型，
此时变量 t 就具有了所有限定的方法和属性。对于通配符来说，因为它不是一个确定的类型，所以不能进行多重限定。
```
public <T extends InterfaceA & InterfaceB> void test(T t);
```
- 通配符可以使用超类限定而类型参数不行
    - 类型参数 T 只具有 一种 类型限定方式：`T extends A`
    - 通配符 ? 可以进行 两种限定：
    ```
    ? extends A
    ? super A
    ```
                       
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    