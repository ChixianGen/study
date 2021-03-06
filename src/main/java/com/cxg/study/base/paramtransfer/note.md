### [Java的参数传递](https://blog.csdn.net/bjweimengshu/article/details/79799485)
> - Java 语言的参数传递只有「按值传递」，没有「按引用传递」！
> - 对于对象参数，值的内容是对象的引用（即对象在堆内存中的内存地址）；
---
#### 形参与实参
- 形式参数：是在定义函数名和函数体的时候使用的参数，目的是用来接收调用该函数时传入的参数。
- 实际参数：在调用有参函数时，主调函数和被调函数之间有数据传递关系。在调用一个函数时，函数名后面括号中的参数称为“实际参数”。

#### 值传递与引用传递
- 值传递（pass by value）是指在调用函数时将实际参数复制一份传递到函数中，这样在函数中如果对参数进行修改，将不会影响到实际参数。
- 引用传递（pass by reference）是指在调用函数时将实际参数的地址直接传递到函数中，那么在函数中对参数所进行的修改，将影响到实际参数。