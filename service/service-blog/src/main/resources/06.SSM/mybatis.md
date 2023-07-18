源码sqlsession

mybatis认参数

#		和		$
1.占位符				字符串拼接
$要手动加'${username}'，另一个自动加

底层
获取参数不直接通过参数名，是把参数放在map集合中，以两种方式存储数据
a》以arg0，arg1。。。。。。。。为键，参数为值
b>以param1，param2.。。。。。

第一种，单个参数，任意字符都能获取
第2种，多个字面量参数，用arg0，arg1

3 种  参数为map
手动放到map中，能根据自己设置的键访问

4种 
放到实体类中
 通过#{} 和${}就可以获取相对应的值，注意${}单引号问题，
ps:属性名：get,set去掉，首字母变小写，这个才是属性名。而不是看private String xxx 的xxx

第5种，最常用的
@Param 注解 
@Param(value="username1") String username2 
放进map集合里面，键是value属性值username1值是username2
param1 param2也可以

经验
参数少用param
参数多用实体

map
map集合
mapkey

1.模糊查询

？在模糊查询中被当作问号，不会被当做占位符
因为#是会被当做'%?%'，而不是占位符

第二种方法用字符串拼接。concat (‘%’，#{mohu},'%')

第三种最常用 	"%"#{mohu}"%"

2.批量删除
#{ids},如果参数是9，10，那就是'9,10'
所以报错
因此只能用${ids}，动态sql能解决

3.动态设置表名也用$
4.自动获取自增的主键
	usergeneratekeys
leyproperty

字段名和属性名不一致的情况：（下划线对应驼峰）
1，别名
2.全局配置，自动将下划线映射为驼峰

1对多，
	级联：	  ->  emp.id  
	associatioin标签
	分布查询

动态sql 
	and解决方案
	1.	1=1
	2.	where标签（后面的and不能去掉）
	3.	trim标签
	4.	choss标签	（就像if else  类比 chose when）

	5.	foreach	（批量删除，批量添加）	配合open close seprareat

批量3种写法
	第一种自己写小括号
	第二种open和close实现
	第3种	foreach标签用 or