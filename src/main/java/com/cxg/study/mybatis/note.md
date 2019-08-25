### 使用mybatis注意事项
- xml中强制使用resultMap，而不使用resultType
    > resultType会让数据库字段与实体类耦合，不利于后期字段的更改或维护；
    
- Mapper接口入参禁止使用Map等不明确的类型
    > 不知道参数到底是什么东西，不利于后期维护

- sql语句传参，尽量不使用${}，而使用#{}，${}存在sql注入；#{}是预编译，会默认给参数加上单引号；

- 批处理的2种方案
    - foreach动态拼接sql；
    - batch批处理执行器；
