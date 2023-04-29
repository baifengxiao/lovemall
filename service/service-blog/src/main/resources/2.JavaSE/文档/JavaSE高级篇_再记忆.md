## 专题一，自定义注解

如何自定义注解：参照@SuppressWarnings定义

> ==① 注解声明为：@interface==		
> ==② 内部定义成员，通常使用value表示==		
> ==③ 可以指定成员的默认值，使用default定义==
> ==④ 如果自定义注解没成员，表明是一个标识作用。==

要结合反射，先感受一下
46.
说明：
如果注解有成员，在使用注解时，需要指明成员的值。
自定义注解必须配上注解的信息处理流程(使用反射)才意义。
自定义注解通过都会指明两个元注解：Retention、Target
47.
Retention：指定所修饰的 Annotation 的生命周期：
SOURCE\CLASS（默认行为\RUNTIME
只有声明为RUNTIME生命周期的注解，才能通过反射获取。
Target:用于指定被修饰的 Annotation 能用于修饰哪些程序元素
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
48.

1. 如何获取注解信息:通过发射来进行获取、调用。

前提：要求此注解的元注解Retention中声明的生命周期状态为：RUNTIME.
6.JDK8中注解的新特性：可重复注解、类型注解
6.1 可重复注解：
49.
6.2 类型注解：
ElementType.TYPE_PARAMETER 表示该注解能写在类型变量的声明语句中（如：泛型声明。
50.

45.
创建Java类，进行单元测试。
此时的Java类要求：① 此类是public的 ②此类提供公共的无参的构造器(只能有一个？)
如果测试类需要进行依赖注入等复杂操作，则可以使用带参数的构造函数，并在测试方法上使用注解来指定运行器应该使用哪个构造函数进行实例化。例如，使用JUnit 4框架时，可以使用@RunWith(Parameterized.class)注解来指定带参数的构造函数。
46.
String 与基本数据类型、包装类间的转换及练习 

```shell
// String ， 基本数据类型 ， 包装类
// 1，转成包装类
String str = "123";
int num = 123;

Integer.valueOf(123);
Integer.valueOf(num);

// 2，转成String
int num1 = 123;
Integer integer1 = 123;

String.valueOf(num1);
String.valueOf(integer1);

// 3，转成基本数据类型
String str2 = "123";
Integer integer2 = 123;

Integer.parseInt(str2);
// Integer.parseInt(String.valueOf(integer2));
Integer.valueOf(integer2);
```

注意：转换时，可能会报NumberFormatException
![](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1678460825424-33dc4267-a2bf-4570-866d-fb4662f2819e.png)
应用场景举例：
Vector类中关于添加元素，只定义了形参为Object类型的方法：
v.addElement(Object obj); //基本数据类型 --->包装类 --->使用多态
47.
  ==catch中的异常类型如果满足子父类关系，则要求子类一定声明在父类的上面。否则，报错==
:::info
Java的异常处理机制中，catch块的异常类型是按照从上到下的顺序进行匹配的，只有第一个匹配的catch块会执行，后续的catch块会被忽略。
:::
48.
throw 和 throws
Collection 和 Collections
49.
JVM是不能自动的回收的，我们需要自己手动的进行资源的释放。此时的资源释放，就需要声明在finally中。
50.
1.开发中应该如何选择两种处理方式？
2.==如果父类中被重写的方法没throws方式处理异常，则子类重写的方法也不能使用throws==，意味着如果子类重写的方法中异常，必须使用try-catch-finally方式处理。
==3.执行的方法a中，先后又调用了另外的几个方法，这几个方法是递进关系执行的。我们建议这几个方法使用throws的方式进行处理。而执行的方法a可以考虑使用try-catch-finally方式进行处理。==
51.
异常处理-使用 throw 手动抛出异常对象 
1.使用说明
在程序执行中，除了自动抛出异常对象的情况之外，我们还可以手动的throw一个异常类的对象。
2.[面试题] 
throw 和 throws区别：
throw 表示抛出一个异常类的对象，生成异常对象的过程。声明在方法体内。
throws 属于异常处理的一种方式，声明在方法的声明处。
3.throw new MyException("不能输入负数");
52.
如何自定义异常类？

1. 继承于现的异常结构：RuntimeException 、Exception
2. 提供全局常量：serialVersionUID (IO流的时候再说)
3. ==当一个对象被序列化（即转换成字节流以便存储或传输）时，**serialVersionUID**的值会被写入到序列化数据中。当反序列化过程开始时，系统会检查被序列化数据中的**serialVersionUID**与当前代码中的**serialVersionUID**是否匹配。如果两者不匹配，反序列化过程会抛出**InvalidClassException**，指示类的版本不一致==
4. 通常提供几个重载的构造器

53.

## 专题二， 多线程

进程：资源分配的单位
54.
:::info
==每个线程，拥有自己独立的：栈、程序计数器==
==多个线程，共享同一个进程中的结构：方法区、堆。==
:::
55.
多线程实现方式
开发中：优先选择：实现Runnable接口的方式
原因：

1. 实现的方式没类的单继承性的局限性(无法继承其他的)
   2.实现的方式更适合来处理多个线程共享数据的情况（接口实现类天然能共享对象）
   56.
   线程中的方法

2. yield():释放当前cpu的执行权
3. join():在线程a中调用线程b的join(),此时线程a就进入阻塞状态，直到线程b完全执行完以后，线程a才结束阻塞状态。
4. notify() / notifyAll()唤醒

```java
package cn.sc.love.gpt;

public class ThreadCommunicationExample {
    public static void main(String[] args) {
        Message message = new Message();

        Thread producerThread = new Thread(() -> {
            synchronized (message) {
                try {
                    //执行2
                    System.out.println("Producer is producing a message.");
                    message.setMessage("Hello!");
                    message.notify(); // 唤醒等待的线程
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumerThread = new Thread(() -> {
            synchronized (message) {
                try {
                    //执行1,
                    System.out.println("Consumer is waiting for a message.");
                    message.wait(); // 等待消息
                    System.out.println("Consumer received a message: " + message.getMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        consumerThread.start();
        producerThread.start();
    }
}

class Message {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

```

57.
线程状态
新建，runable，死亡
:::info
==runable又可以分为3种，无限等待，有限等待，锁阻塞==
:::
58.
线程通信：wait() / notify() / notifyAll() :此三个方法定义在Object类中的。
59.
同步监视器
在继承Thread类创建多线程的方式中，慎用this充当同步监视器，考虑使用当前类充当同步监视器。
60.
②非静态的同步方法，同步监视器是该方法所属对象的实例（this）
:::info
==③ 静态的同步方法，同步监视器是：当前类的 Class 对象==
:::
61.
多线程-线程间的通信机制与生产者消费者案例 
1.线程通信涉及到的三个方法：
2.说明：
:::info
==2.1.wait()，notify()，notifyAll()三个方法必须使用在同步代码块或同步方法中。==
==2.2.wait()，notify()，notifyAll()三个方法的调用者必须是同步代码块或同步方法中的同步监视器。==
==否则，会出现IllegalMonitorStateException异常==
:::

> ==比较sleep()和wait()==
> ==3.1.相同点：一旦执行方法，都可以使得当前的线程进入阻塞状态。==
> ==3.2.不同点：==
> ==①两个方法声明的位置不同：Thread类中声明sleep() （静态的） , Object类中声明wait()==
> ==②调用的要求不同：sleep()可以在任何需要的场景下调用。 wait()必须使用在同步代码块或同步方法中==
> ==③关于是否释放同步监视器：如果两个方法都使用在同步代码块或同步方法中，sleep()不会释放锁，wait()会释放锁。==

62.
如何理解实现Callable接口的方式创建多线程比实现Runnable接口创建多线程方式强大？
①call()可以返回值的。
②call()可以抛出异常，被外面的操作捕获，获取异常的信息
③Callable是支持泛型的
69.
1.3.String内部定义了final char[] value用于存储字符串数据 （jdk9开始变byte【】了）
63.
String s = new String("abc");方式创建对象，在内存中创建了几个对象？

> ==两个:一个是堆空间中new结构，另一个是char[]对应的常量池中的数据："abc"==

==64.常用方法==
String trim()：返回字符串的副本，忽略前导空白和尾部空白
String concat(String str)：将指定字符串连接到此字符串的结尾。 等价于用“+”

> ==int compareTo(String anotherString)：比较两个字符串的大小==

boolean startsWith(String prefix)：测试此字符串是否以指定的前缀开始
boolean contains(CharSequence s)：当且仅当此字符串包含指定的 char 值序列时，返回 true
int indexOf(String str)：返回指定子字符串在此字符串中第一次出现处的索引
String replaceAll(String regex, String replacement)：使用给定的 replacement 替换此字符串所匹配给定的正则表达式的子字符串。
String replaceFirst(String regex, String replacement)：使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串。
65.
1.获取系统当前时间：System类中的currentTimeMillis()
可变性：像日期和时间这样的类应该是不可变的。
偏移性：Date中的年份是从1900开始的，而月份都从0开始。
格式化：格式化只对Date用，Calendar则不行。
此外，它们也不是线程安全的；不能处理闰秒等
66.
4.2 常用方法：
withXxx,plusXxx,minuXxx
74.
6.日期时间格式化类：DateTimeFormatter
自定义的格式。如：==ofPattern(“yyyy-MM-dd hh:mm:ss”)==

> ==2.2.1.像String、包装类等实现了Comparable接口，重写了compareTo(obj)方法，给出了比较两个对象大小的方式。==
> ==.Comparable接口的排序规则不适合当前的操作，那么可以考虑使用 Comparator 的对象来排序==
> ==1.1.2.重写compare(Object o1,Object o2)方法，比较o1和o2的大小：==

3.BigInteger类、BigDecimal类
说明：
① java.math包的BigInteger可以表示不可变的任意精度的整数。
② 要求数字精度比较高的浮点数，用到java.math.BigDecimal类
75.

## 专题三，集合

5.概览
----Collection接口：单列集合，用来存储一个一个的对象
|----List接口：存储序的、可重复的数据。 -->“动态”数组

> ==|----ArrayList、LinkedList、Vector==

|----Set接口：存储无序的、不可重复的数据 -->高中讲的“集合”

> ==|----HashSet、LinkedHashSet、TreeSet==

java.util.Map:存储一对一对的数据
|----Ｍａｐ接口：存储无序的、不可重复的数据 -->高中讲的“集合”

> ==|----HashMap、LinkedHashMap、TreeMap,Hashtable,Properties==

76.
retainsAll(Collection coll),	//求两个集合的交集

> ==coll.toArray();==
> ==Arrays.asList(new String[]{"AA", "BB", "CC"});==

77.
向Collection接口的实现类的对象中添加数据obj时，要求obj所在类要重写equals(). （ 因为collection中的相关方法contains（）/remove()需要重写）
78.
155-集合框架-List 接口常用方法的测试 
常用方法：(记住)
:::info
==增：add(Object obj)==
==删：remove(int index) / remove(Object obj)==
==改：set(int index, Object ele)==
==查：get(int index)==
==插：add(int index, Object ele)==
:::
79.List
|----ArrayList：作为List接口的主要实现类；线程不安全的，效率高 ==遇到线程安全问题用collection自己包一下==
:::info
List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
:::
|----LinkedList：对于频繁的插入、删除操作，使用此类效率比ArrayList高；底层使用双向链表存储
|----Vector：作为List接口的古老实现类；线程安全的，效率低；底层使用Object[] elementData存储 彻底失宠==，已经不用了==
80.

1. Set

|----Set接口：存储无序的、不可重复的数据 -->高中讲的“集合”
|----HashSet：作为Set接口的主要实现类；底层用==HashMap，即使用数组+单向链表+红黑树结构进行存储，==		
|----LinkedHashSet：作为HashSet的子类；在现有基础上，又添加了一组双向链表，用于记录添加元素的先后顺序
|----TreeSet：==底层用红黑树存储，可以按照添加的元素的指定的属性的大小顺序进行遍历==
81.
==什么是无序性？与添加的元素的位置有关，不像ArrayList一样是一次紧密排列的。==

> ==这里是根据添加的元素的哈希值，计算的其在数组中的存储位置。此位置不是依次排列的，表现为无序性（LinkedHashSet也是无序，不过用了链表指向顺序）==

5.2.不可重复性：添加到Set的元素是不能相同的，比较的标准，需要判断hashCode（）				得到的哈希值与equals()得到的boolean型结果，哈希值相同且					equals()返回true，则认为元素是相同的。
6.添加到hashset/linkedhashset中元素的要求：

> ==要求元素所在的类要重写两个方法: equals()和hashCode()。==

同时，要求equals()和 hashCode()要保持一致性!我们只需要在IDEA中自动生成两个方法的重写即可，即能保证
82.
1.向TreeSet中添加的元素的要求
要求是相同类型的对象，否则报异常。

> ==2.判断数据是否相同的标准==
> ==2.1.TreeSet中的元素所在的内不需要重写hashCode（）和equal方法==

83.
双列集合框架：Map
1.常用实现类结构
|----Map:双列数据，存储key-value对的数据 ---类似于高中的函数：y = f(x)
|----HashMap:作为Map的主要实现类；线程不安全的，效率高；==存储null的key和value==
|----LinkedHashMap:保证在遍历map元素时，可以照添加的顺序实现遍历。
|----Hashtable:作为==古老的实现类；线程安全的，效率低；不能存储null的key和value==

> ==|----TreeMap:保证照添加的key-value对进行排序，实现排序遍历。此时考虑key的自然排序或定制排序==

底层使用红黑树 
|----Properties:常用来处理配置文件。key和value都是String类型

> ==HashMap的底层：数组+链表 （jdk7及之前)==
> ==数组+链表+红黑树 （jdk 8)==

84.
2.存储结构的理解：
Map中的key:无序的、不可重复的，使用==Set存储所的key ---> key所在的类要重写equals()和hashCode() （以HashMap为例)==

Map中的value:无序的、可重复的，使用Collection存储所有的value --->value所在的类要重写equals()
有的通用方法要调用equals
**equals()**方法在Java中被广泛使用，并且与其他一些通用方法存在关联。以下是一些常见的与**equals()**方法相关的方法：

1. **contains(Object obj)**：该方法用于检查集合中是否包含指定对象。在检查过程中，会使用对象的**equals()**方法来比较集合中的元素与指定对象是否相等。
2. **remove(Object obj)**：该方法用于从集合中移除指定对象。在移除过程中，会使用对象的**equals()**方法来判断集合中的元素与指定对象是否相等。
3. **indexOf(Object obj)**：该方法用于返回指定对象在集合中的索引位置。在查找过程中，会使用对象的**equals()**方法来比较集合中的元素与指定对象是否相等。
4. **lastIndexOf(Object obj)**：该方法用于返回指定对象在集合中最后出现的索引位置。同样，在查找过程中，会使用对象的**equals()**方法来比较集合中的元素与指定对象是否相等。

一个键值对：key-value构成了一个Entry对象。
Map中的entry:无序的、不可重复的，使用Set存储所的entry
85.
Object max(Collection，Comparator)：根据 Comparator 指定的顺序，返回给定集合中的最大元素
Object min(Collection)
Object min(Collection，Comparator)
int frequency(Collection，Object)：返回指定集合中指定元素的出现次数
85.
泛型方法：在方法中出现了泛型的结构，泛型参数与类的泛型参数没任何关系。
换句话说，==泛型方法所属的类是不是泛型类都没关系。==
泛型方法，可以声明为静态的。原因：泛型参数是在调用方法时确定的。并非在实例化类时确定。
86.
:::tips
==由于子类在继承带泛型的父类时，指明了泛型类型。则实例化子类对象时，不再需要指明泛型。如果子类没有指定泛型类型，或者指定了不同的泛型类型，那么在实例化子类对象时仍然需要指定泛型类型。==
:::
87.
2.注意点：
2.1. 泛型类可能有多个参数，此时应将多个参数一起放在尖括号内。
比如: <E1,E2,E3>

> ==2.2. 泛型类的构造器如下: public GenericClass(){}。==
> ==而下面是错误的: public GenericClass<E>(){}==
> ==2.3. 实例化后，操作原来泛型位置的结构必须与指定的泛型类型一致。==

2.4. 泛型不同的引用不能相互赋值。

> ==尽管在编译时ArrayList<String>和ArrayList<Integer>是两种类型，但是，在运行时只有==
> ==一个ArrayList被加载到JVM中。==

2.5.泛型如果不指定，将被擦除，==泛型对应的类型均按照Object处理，但不等价==
==于Object。==经验:泛型要使用一路都用。要不用，一路都不要用。
2.6.如果泛型结构是--个接口或抽象类，则不可创建泛型类的对象。
2.7. jdk1.7，泛型的简化操作: ArrayList<Fruit> flist = new ArrayList<>();
2.8.泛型的指定中不能使用基本数据类型，可以使用包装类替换。
2.9.在类/接口.上声明的泛型，在本类或本接口中即代表某种类型，==可以作为非静态==
==属性的类型、非静态方法的参数类型、非静态方法的返回值类型。==但在静态方法
中不能使用类的泛型。
2.10.异常类不能是泛型的
2.11.==不能使用new E[]。==但是可以:印elements = (E[])new Object[capacity];
参考: ArrayList源码中声明: Object] elementData,而非泛型参数类型数组。
==2.12.父类有泛型，子类可以选择保留泛型也可以选择指定泛型类型:==

> ==●子类不保留父类的泛型:按需实现==
> ==➢没有类型擦除==
> ==➢具体类型==
> ==●子类保留父类的泛型:泛型子类==
> ==➢全部保留==
> ==➢部分保留==

结论:子类必须是“富二代”，子类除了指定或保留父类的泛型，还可以增加自
己的泛型
88.
.泛型方法
//举例：获取表中一共有多少条记录？获取最大的员工入职时间？

```shell
public <E> E getValue(){
return null;
}
```

89.
泛型在继承方面的体现
虽然类A是类B的父类，但是G<A> 和G<B>二者不具备子父类关系，二者是并列关系。
List<Object> list1 = null;
List<String> list2 = new ArrayList<String>();
此时的list1和list2的类型不具子父类关系
90.
:::info
==2.通配符的使用==
==通配符的使用==
==限制条件的通配符的使用。==
==? extends A:==
==G<? extends A> 可以作为G<A>和G<B>的父类，其中B是A的子类==
==? super A:==
==G<? super A> 可以作为G<A>和G<B>的父类，其中B是A的父类==
:::
添加(写入)：对于List<?>就不能向其内部添加数据。	//（因为里面类型不确定） //除了添加null之外。
获取(读取)：允许读取数据，读取的数据类型为Object （因为通配符不知道具体类型，当object）。
91.
数据的逻辑结构指反映数据元素之间的逻辑关系，而与数据的存储无关，是独立于计算机的
92.
数据的物理结构/存储结构：包括数据元素的==表示和关系的表示==。==数据的存储结构是逻辑结构用计算机语言的实现，它依赖于计算机语言。

## 专题四，数据结构

**结构1：顺序结构**

- 顺序结构就是使用一组连续的存储单元依次存储逻辑上相邻的各个元素。 
- 优点： 只需要申请存放数据本身的内存空间即可，支持下标访问，也可以实现随机访问。 
- 缺点： 必须静态分配连续空间，内存空间的利用率比较低。插入或删除可能需要移动大量元素，效率比较低 

**结构2：链式结构**

- 不使用连续的存储空间存放结构的元素，而是为每一个元素构造一个节点。节点中除了存放数据本身以外，还需要存放指向下一个节点的指针。一次申请一小块内存
- 优点：不采用连续的存储空间导致内存空间利用率比较高，克服顺序存储结构中预知元素个数的缺点。插入或删除元素时，不需要移动大量的元素。
- 缺点：需要额外的空间来表达数据之间的逻辑关系，不支持下标访问和随机访问。

**结构3：索引结构**

- 除建立存储节点信息外，还建立附加的索引表来记录每个元素节点的地址。索引表由若干索引项组成。索引项的一般形式是：（关键字，地址）。
- 优点：用节点的索引号来确定结点存储地址，检索速度快。
- 缺点： 增加了附加的索引表，会占用较多的存储空间。在增加和删除数据时要修改索引表，因而会花费较多的时间。

**结构4：散列结构**

- 根据元素的关键字直接计算出该元素的存储地址，又称为Hash存储。
- 优点：检索、增加和删除结点的操作都很快。
- 缺点：不支持排序，一般比用线性表存储需要更多的空间，并且记录的关键字不能重复。

2. 一维数组
   2.1 数组的特点

- 在Java中，数组是用来存放同一种数据类型的集合，注意只能存放同一种数据类型
- 94.
  :::info
  ==结点的度数：每个结点所拥有的子树的个数称之为结点的度，如结点B的度为3==
  ==：度数为0的结点，也叫作终端结点，图中D、K、F、L、H、I、J都是树叶==
  ==同代：在同一棵树中具有相同层数的节点==
  :::
  94.
  而ArrayList在JDK 6.0 及之前的版本也是10
  链表底层的物理结构是链表，因此根据索引访问的效率不高，即查找元素慢。但是插入和删除不需要移动元素，只需要修改前后元素的指向关系即可，所以插入、删除元素快。而且链表的添加不会涉及到扩容问题。
  95.
  为啥不直接用hash值1？
  都不是直接用key的hashCode值直接与table.length-1计算求下标的，而是先对key的hashCode值进行了一个运算，JDK1.7和JDK1.8关于hash()的实现代码不一样，但是不管怎么样都是为了提高hash code值与 (table.length-1)的按位与完的结果，尽量的均匀分布。
  96.
  ①hash 值 % table.length会得到一个[0,table.length-1]范围的值，正好是下标范围，但是用%运算效率没有位运算符&高。
  ②hash 值 & (table.length-1)，任何数 & (table.length-1)的结果也一定在[0, table.length-1]范围
  97.
  table.length-1的二进制就是一个高位全是0，低位全是1的数字，这样才能保证每一个下标位置都有机会被用到。
  98.
  static final int TREEIFY_THRESHOLD = 8;//树化阈值static final int UNTREEIFY_THRESHOLD = 6;//反树化阈值static final int MIN_TREEIFY_CAPACITY = 64;//最小树化容量
  99.
  3.9 key-value中的key是否可以修改？
  key-value存储到HashMap中会存储key的hash值，这样就不用在每次查找时重新计算每一个Entry或Node（TreeNode）的hash值了，因此如果已经put到Map中的key-value，再修改key的属性，而这个属性又参与hashcode值的计算，那么会导致匹配不上。

## 专题五，IO流

蓝框的流需要大家重点关注。
101.
File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建此文件。
File对应的硬盘中的文件如果存在：
如果流使用的构造器是：FileWriter(file,false) / FileWriter(file):对原文件的覆盖
如果流使用的构造器是：FileWriter(file,true):不会对原文件覆盖，而是在原文件基础上追加内容
102.
（字节流可以处理一切文件）
103.
作用：提供流的读取、写入的速度
提高读写速度的原因：内部提供了一个缓冲区。默认情况下是8kb
104.
要求：先关闭外层的流，再关闭内层的流
//说明：关闭外层流的同时，内层流也会自动的进行关闭。关于内层流的关闭，我们可以省略.
while((data = br.readLine()) != null){
105.
1.转换流涉及到的类：属于字符流
InputStreamReader：将一个字节的输入流转换为字符的输入流
OutputStreamWriter：将一个字符的输出流转换为字节的输出流
106.
ObjectOutputStream:内存中的对象--->存储中的文件、通过网络传输出去：序列化过程
ObjectInputStream:存储中的文件、通过网络接收过来 --->内存中的对象：反序列化过程
==oos.flush();//刷新操作，将内存中的数据写入文件==
107.
. 数据流：
DataInputStream 和 DataOutputStream
作用：
用于读取或写出基本数据类型的变量或字符串
108.
.随机存取文件流：RandomAccessFile
raf1.seek(3);//将指针调到角标为3的位置
109.
如果端口号被另外一个服务或应用所占用，会导致当前程序启动失败。

## 专题六，网络编程

**TCP协议：**

- TCP协议进行通信的两个应用进程：客户端、服务端。
- 使用TCP协议前，==须先`建立TCP连接==`，形成基于字节流的传输数据通道
- 传输前，采用“三次握手”方式，点对点通信，是可靠的
  - TCP协议==使用`重发机制==`，当一个通信实体发送一个消息给另一个通信实体后，需要收到另一个通信实体确认信息，如果没有收到另一个通信实体确认信息，则会再次重复刚才发送的消息。
- 在连接中可==进行`大数据量的传输`==
- 传输完毕，==需`释放已建立的连接，效率低`==
- UDP协议进行通信的两个应用进程：发送端、接收端。
- 将数据、源、目的封装成数据包（传输的基本单位）==，`不需要建立连接==`
- 发送不管对方是否准备好，接收方收到也不确认，不能保证数据的完整性，==故是`不可靠的==`
- 每个数据报的大小限制在64K内
- 发送数据==结束时`无需释放资源，开销小，通信效率高==`
- 适用场景：音频、视频和普通数据的传输。例如视频会议
- 第一次握手，客户端向服务器端发起TCP连接的请求
- 第二次握手，服务器端发送针对客户端TCP连接请求的确认
- 第三次握手，客户端发送确认的确认
- 第一次挥手：客户端向服务器端提出结束连接，==让服务器做最后的准备工作==。此时，客户端处于半关闭状态，即表示不再向服务器发送数据了，但是还可以接受数据。
- 第二次挥手：服务器接收到客户端释放连接的请求==后，`会将最后的数据发给客户端==`。并告知上层的应用进程不再接收数据。
- 第三次挥手：服务器发送完数据后，==会给客户端发送一个释放连接的报文==。那么客户端接收后就知道可以正式释放连接了。
- 第四次挥手：客户端接收到服务器最后的释放连接报文后，==要`回复一个彻底断开的报文==`

通常情况下，服务器不应该只接受一个客户端请求，而应该不断地接受来自客户端的所有请求，所以Java程序通常会通过循环，不断地调用ServerSocket的accept()方法。
如果服务器端要“同时”处理多个客户端的请求，因此服务器端需要为**每一个客户端单独分配一个线程**来处理，否则无法实现“同时”。
咱们之前学习IO流的时候，提到过装饰者设计模式，该设计使得不管底层IO流是怎样的节点流：文件流也好，网络Socket产生的流也好，程序都可以将其包装成处理流，甚至可以多层包装，从而提供更多方便的处理
103
DatagramSocket 和 DatagramPacket 实现了基于 UDP 协议网络程序。
DatagramPacket 对象封装了UDP数据报，在数据报中包含了发送端的IP地址和端口号以及接收端的IP地址和端口号。
UDP协议中每个数据报都给出了完整的地址信息，因此无须建立发送方和接收方的连接。如同发快递包裹一样。

### 6.3 针对HTTP协议的URLConnection类

105.
方案1：在编译和运行时都完全知道类型的具体信息，在这种情况下，我们可以直接先使用运算符进行判断，再利用强制类型转换符将其转换成运行时类型的变量即可。instanceof
方案2：编译时根本无法预知该对象和类的真实信息，程序只能依靠来发现该对象和类的真实信息，这就必须使用反射。运行时信息

## 专题七，反射

**从内存加载上看反射：**

- 生成动态代理

所谓反射从程序的运行结果来看也很好理解，即：可以通过对象反射求出类的名称。

### 2.2 获取Class类的实例(四种方法)

:::info
==方式1：要求编译期间已知类型==
==方式2：获取对象的运行时类型==
==方式3：可以获取编译期间未知的类型==
==前提：已知一个类的全类名，且该类在类路径下，可通过Class类的静态方法forName()获取，可能抛出ClassNotFoundException==
:::
方式4：其他方式(不做要求)
前提：可以用系统类加载对象或自定义加载器对象加载指定路径下的类型
类在内存中完整的生命周期：加载-->使用-->卸载。其中加载过程又分为：装载、链接、初始化三个阶段。
类的加载又分为三个阶段：
（1）装载（Loading）
将类的class文件读入内存，并为之创建一个java.lang.Class对象。此过程由类加载器完成
（2）链接（Linking）
①验证Verify：确保加载的类信息符合JVM规范，例如：以cafebabe开头，没有安全方面的问题。
②准备Prepare：正式为类变量（static）分配内存并设置类变量默认初始值的阶段，这些内存都将在方法区中进行分配。
③解析Resolve：虚拟机常量池内的符号引用（常量名）替换为直接引用（地址）的过程。
（3）初始化（Initialization）

- 执行类构造器<clinit>()方法的过程。类构造器<clinit>()方法是由编译期自动收集类中所有类变量的赋值动作和静态代码块中的语句合并产生的。（类构造器是构造类信息的，不是构造该类对象的构造器）。
- 当初始化一个类的时候，如果发现其父类还没有进行初始化，则需要先触发其父类的初始化。
- 虚拟机会保证一个类的<clinit>()方法在多线程环境中被正确加锁和同步。
  :::info

#### ==3.3.1 类加载器的作用==

:::
将class文件字节码内容加载到内存中，并将这些静态数据转换成方法区的运行时数据结构，然后在堆中生成一个代表这个类的java.lang.Class对象，作为方法区中类数据的访问入口。
类缓存：标准的JavaSE类加载器可以按要求查找类，但一旦某个类被加载到类加载器中，它将维持加载（缓存）一段时间。不过JVM垃圾回收机制可以回收这些Class对象。
JVM支持两种类型的类加载器，分别为引导类加载器（Bootstrap ClassLoader）和自定义类加载器（User-Defined ClassLoader）。
**（1）启动类加载器（引导类加载器，Bootstrap ClassLoader）**

- 这个类加载使用C/C++语言实现的，嵌套在JVM内部。获取它的对象时往往返回null
- 它用来加载Java的核心库（JAVA_HOME/jre/lib/rt.jar或sun.boot.class.path路径下的内容）。用于提供JVM自身需要的类。
- 并不继承自java.lang.ClassLoader，没有父加载器。
- 出于安全考虑，Bootstrap启动类加载器只加载包名为java、javax、sun等开头的类
- 加载扩展类和应用程序类加载器，并指定为他们的父类加载器。

**（2）扩展类加载器（Extension ClassLoader）**

- Java语言编写，由sun.misc.Launcher$ExtClassLoader实现。
- 继承于ClassLoader类
- 父类加载器为启动类加载器
- 从java.ext.dirs系统属性所指定的目录中加载类库，或从JDK的安装目录的jre/lib/ext子目录下加载类库。如果用户创建的JAR放在此目录下，也会自动由扩展类加载器加载。

**（3）应用程序类加载器（系统类加载器，AppClassLoader）**

- java语言编写，由sun.misc.Launcher$AppClassLoader实现
- 继承于ClassLoader类
- 父类加载器为扩展类加载器
- 它负责加载环境变量classpath或系统属性 java.class.path 指定路径下的类库
- 应用程序中的类加载器默认是系统类加载器。
- 它是用户自定义类加载器的默认父加载器
- 通过ClassLoader的getSystemClassLoader()方法可以获取到该类加载器

**（4）用户自定义类加载器（了解）**

- 在Java的日常应用程序开发中，类的加载几乎是由上述3种类加载器相互配合执行的。在必要时，我们还可以自定义类加载器，来定制类的加载方式。
- 体现Java语言强大生命力和巨大魅力的关键因素之一便是，Java开发者可以自定义类加载器来实现类库的动态加载，加载源可以是本地的JAR包，也可以是网络上的远程资源。
- 同时，自定义加载器能够实现应用隔离，例如 Tomcat，Spring等中间件和组件框架都在内部实现了自定义的加载器，并通过自定义加载器隔离不同的组件模块。这种机制比C/C程序要好太多，想不修改C/C程序就能为其新增功能，几乎是不可能的，仅仅一个兼容性便能阻挡住所有美好的设想。
- 自定义类加载器通常需要继承于ClassLoader。

3.3.4 使用ClassLoader获取流
关于类加载器的一个主要方法：getResourceAsStream(String str):获取类路径下的指定文件的输入流
Java
复制代码
1
2
3
InputStream in = null;
in = this.getClass().getClassLoader().getResourceAsStream("exer2\test.properties");
System.out.println(in);

## 专题八，Java新特性

#### 4.2.5 获取泛型父类信息（选讲）

:::info

### ==什么是函数式接口==

:::

- 只包含一个抽象方法（Single Abstract Method，简称SAM）的接口，称为函数式接口

作为参数传递 Lambda 表达式：
**练习2：消费型接口**
在JDK1.8中Collection集合接口的父接口Iterable接口中增加了一个默认方法：
public default void forEach(Consumer<? super T> action)遍历Collection集合的每个元素，执行“xxx消费型”操作。
在JDK1.8中Map集合接口中增加了一个默认方法：
public default void forEach(BiConsumer<? super K,? super V> action)遍历Map集合的每对映射关系，执行“xxx消费型”操作。
**练习3：供给型接口**
public static <T> Stream<T> generate(Supplier<T> s)可以创建Stream的对象。
Stream的generate方法，来产生一个流对象，
102
方法引用就是Lambda表达式，也就是函数式接口的一个实例，通过方法的名字来指向一个方法，可以认为是Lambda表达式的一个语法糖。
语法糖（Syntactic sugar），也译为糖衣语法，是由英国计算机科学家彼得·约翰·兰达（Peter J. Landin）发明的一个术语，指计算机语言中添加的某种语法，这种语法对语言的功能并没有影响，但是更方便程序员使用。通常来说使用语法糖能够增加程序的可读性，从而减少程序代码出错的机会。
108
:::info
**==要求2：（没例子想不出来了）==**
:::
针对情况1：函数式接口中的抽象方法a在被重写时使用了某一个对象的方法b。如果方法a的形参列表、返回值类型与方法b的形参列表、返回值类型都相同，则我们可以使用方法b实现对方法a的重写、替换。
针对情况2：函数式接口中的抽象方法a在被重写时使用了某一个类的静态方法b。如果方法a的形参列表、返回值类型与方法b的形参列表、返回值类型都相同，则我们可以使用方法b实现对方法a的重写、替换。
针对情况3：函数式接口中的抽象方法a在被重写时使用了某一个对象的方法b。如果方法a的返回值类型与方法b的返回值类型相同，同时方法a的形参列表中有n个参数，方法b的形参列表有n-1个参数，且方法a的第1个参数作为方法b的调用者，且方法a的后n-1参数与方法b的n-1参数匹配（类型相同或满足多态场景也可以）
109

### 4.2 构造器引用

当Lambda表达式是创建一个对象，并且满足Lambda表达式形参，正好是给创建这个对象的构造器的实参列表，就可以使用构造器引用。
数组构造引用
当Lambda表达式是创建一个数组对象，并且满足Lambda表达式形参，正好是给创建这个数组对象的长度，就可以数组构造引用。
**方式四：创建无限流(了解)**
2-归约

| **方法**                                 | **描述**                                                 |
| ---------------------------------------- | -------------------------------------------------------- |
| **reduce(T identity, BinaryOperator b)** | 可以将流中元素反复结合起来，得到一个值。返回 T           |
| **reduce(BinaryOperator b)**             | 可以将流中元素反复结合起来，得到一个值。返回 Optional<T> |

111
:::info

### ==7.1 Optional类==

:::

**JDK8的新特性**

到目前为止，臭名昭著的空指针异常是导致Java应用程序失败的最常见原因。以前，为了解决空指针异常，Google在著名的Guava项目引入了Optional类，通过检查空值的方式避免空指针异常。受到Google的启发，Optional类已经成为Java 8类库的一部分。

`Optional<T>` 类(java.util.Optional) 是一个容器类，它可以保存类型T的值，代表这个值存在。或者仅仅保存null，表示这个值不存在。如果值存在，则isPresent()方法会返回true，调用get()方法会返回该对象。

Optional提供很多有用的方法，这样我们就不用显式进行空值检测。

- `创建Optional类对象的方法：`
- static  Optional empty() ：用来创建一个空的Optional实例 
  - ==static  Optional of(T value) ：用来创建一个Optional实例====，value必须非空==
  - `static <T> Optional<T> ofNullable(T value)` ：用来创建一个Optional实例，value可能是空，也可能非空
- `判断Optional容器中是否包含对象：` 
  - boolean isPresent() : 判断Optional容器中的值是否存在
  - void ifPresent(Consumer<? super T> consumer) ：判断Optional容器中的值是否存在，如果存在，就对它进行Consumer指定的操作，如果不存在就不做
- `获取Optional容器的对象：`
- T get(): 如果调用对象包含值，返回该值。否则抛异常。T get()与of(T value)配合使用
- `T orElse(T other)`：orElse(T other) 与ofNullable(T value)配合使用，如果Optional容器中非空，就返回所包装值，如果为空，就用orElse(T other)other指定的默认值（备胎）代替
- T orElseGet(Supplier<? extends T> other) ：如果Optional容器中非空，就返回所包装值，如果为空，就用Supplier接口的Lambda表达式提供的值代替
- T orElseThrow(Supplier<? extends X> exceptionSupplier) ：如果Optional容器中非空，就返回所包装值，如果为空，就抛出你指定的异常类型代替原来的NoSuchElementException

举例：
package com.atguigu.optional;

import java.util.Optional;

import org.junit.Test;

public class TestOptional {
	@Test
    public void test1(){
        String str = "hello";
        Optional<String> opt = Optional.of(str);
        System.out.println(opt);
    }
    @Test
    public void test2(){
        Optional<String> opt = Optional.empty();
        System.out.println(opt);
    }
    @Test
    public void test3(){
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);
        System.out.println(opt);
    }
    @Test
    public void test4(){
        String str = "hello";
        Optional<String> opt = Optional.of(str);

        String string = opt.get();
        System.out.println(string);
    }
    @Test
    public void test5(){
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);

//		System.out.println(opt.get());//java.util.NoSuchElementException: No value present
    }
    @Test
    public void test6(){
        String str = "hello";
        Optional<String> opt = Optional.ofNullable(str);
        String string = opt.orElse("atguigu");
        System.out.println(string);
    }
    @Test
    public void test7(){
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);
        String string = opt.orElseGet(String::new);
        System.out.println(string);
    }
    @Test
    public void test8(){
        String str = null;
        Optional<String> opt = Optional.ofNullable(str);
        String string = opt.orElseThrow(()->new RuntimeException("值不存在"));
        System.out.println(string);
    }
    @Test
    public void test9(){
        String str = "Hello1";
        Optional<String> opt = Optional.ofNullable(str);
        //判断是否是纯字母单词，如果是，转为大写，否则保持不变
        String result = opt.filter(s->s.matches("[a-zA-Z]+"))
                .map(s -> s.toUpperCase()).orElse(str);
        System.out.println(result);
    }
}