使用策略模式，解决过多的if-else
```
原逻辑代码举例如下：
 public String handleOrder(OrderDTO order) {
    String type = order.getType();
    if ("A".equals(type)) {
        return "普通订单";
    } else if ("B".equals(type)) {
        return "团购订单";
    } else if ("C".equals(type)) {
        return "积分订单";
    } else {
        ...
    }
    return null;
}
```

###### spring的@Autowired
```
@Autowired
private Map<String, AbstractHandler> handlerMap;

@Autowired
private List<AbstractHandler> handlerList;

以上2种情况spring都会把所有AbstractHandler的子类注入到集合中；
handlerMap: 默认是LinkedHashMap集合；
handlerList: 默认是ArrayList集合；
```
