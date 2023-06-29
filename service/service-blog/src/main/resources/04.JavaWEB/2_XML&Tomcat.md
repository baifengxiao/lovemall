# 2_XML&Tomcat

## 学习目标

## 1. xml解析(了解)

### 1.1 配置文件

#### 1.1.1 配置文件的作用

配置文件是用于给应用程序提供配置参数以及初始化设置的一些有特殊格式的文件

#### 1.1.2 常见的配置文件类型

1. properties文件,例如druid连接池就是使用properties文件作为配置文件
2. XML文件,例如Tomcat就是使用XML文件作为配置文件
3. YAML文件,例如SpringBoot就是使用YAML作为配置文件
4. json文件,通常用来做文件传输，也可以用来做前端或者移动端的配置文件

### 1.2 properties文件

#### 1.2.1 文件示例

```properties
atguigu.jdbc.url=jdbc:mysql://192.168.198.100:3306/bj1026
atguigu.jdbc.driver=com.mysql.cj.jdbc.Driver
atguigu.jdbc.username=root
atguigu.jdbc.password=root
```

#### 1.2.2 语法规范

- 由键值对组成
- 键和值之间的符号是等号
- 每一行都必须顶格写，前面不能有空格之类的其他符号

### 1.3 XML文件

#### 1.3.1 文件示例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- 配置SpringMVC前端控制器 -->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

        <!-- 在初始化参数中指定SpringMVC配置文件位置 -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>

        <!-- 设置当前Servlet创建对象的时机是在Web应用启动时 -->
        <load-on-startup>1</load-on-startup>

    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>

        <!-- url-pattern配置斜杠表示匹配所有请求 -->
        <!-- 两种可选的配置方式：
                1、斜杠开头：/
                2、包含星号：*.atguigu
             不允许的配置方式：前面有斜杠，中间有星号
                /*.app
         -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

#### 1.3.2 概念介绍

XML是e==X==tensible ==M==arkup ==L==anguage的缩写，翻译过来就是==可扩展标记语言==。所以很明显，XML和HTML一样都是标记语言，也就是说它们的基本语法都是标签。

**可扩展**

可扩展三个字表面上的意思是XML允许自定义格式。但是不代表可以随便写。

在XML基本语法规范的基础上，你使用的那些第三方应用程序、框架会通过设计==『XML约束』==的方式==『强制规定』==配置文件中可以写什么和怎么写，规定之外的都不可以写。

XML基本语法这个知识点的定位是：我们不需要从零开始，从头到尾的一行一行编写XML文档，而是在第三方应用程序、框架==已提供的配置文件==的基础上==修改==。要改成什么样取决于你的需求，而怎么改取决于XML基本语法和具体的XML约束。

#### 1.3.3 XML的基本语法

- XML文档声明

这部分基本上就是固定格式，要注意的是文档声明一定要从第一行第一列开始写

```xml
<?xml version="1.0" encoding="UTF-8"?>
```

- 根标签

根标签有且只能有一个。

- 标签关闭 
   - 双标签：开始标签和结束标签必须成对出现。
   - 单标签：单标签在标签内关闭。
- 标签嵌套 
   - 可以嵌套，但是不能交叉嵌套。
- 注释不能嵌套
- 标签名、属性名建议使用小写字母
- 属性 
   - 属性必须有值
   - 属性值必须加引号，单双都行

看到这里大家一定会发现XML的基本语法和HTML的基本语法简直如出一辙。其实这不是偶然的，XML基本语法+HTML约束=HTML语法。在逻辑上HTML确实是XML的子集。

```html
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
```

从HTML4.01版本的文档类型声明中可以看出，这里使用的DTD类型的XML约束。也就是说http://www.w3.org/TR/html4/loose.dtd这个文件定义了HTML文档中可以写哪些标签，标签内可以写哪些属性，某个标签可以有什么样的子标签。

#### 1.3.4 XML的约束(稍微了解)

将来我们主要就是根据XML约束中的规定来编写XML配置文件，而且会在我们编写XML的时候根据约束来提示我们编写, 而XML约束主要包括DTD和Schema两种。

-  DTD 
-  Schema 

Schema约束要求我们一个XML文档中，所有标签，所有属性都必须在约束中有明确的定义。

下面我们以web.xml的约束声明为例来做个说明：

```xml
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
```

#### 1.3.5 XML解析

##### ① XML解析的作用

用Java代码读取xml中的数据

##### ② DOM4J的使用步骤

1. 导入jar包 dom4j.jar
2. 创建解析器对象(SAXReader)
3. 解析xml 获得Document对象
4. 获取根节点RootElement
5. 获取根节点下的子节点

##### ③ DOM4J的API介绍

1. 创建SAXReader对象

```java
SAXReader saxReader = new SAXReader();
```

2. 解析XML获取Document对象: 需要传入要解析的XML文件的字节输入流

```java
Document document = reader.read(inputStream);
```

3. 获取文档的根标签

```java
Element rootElement = documen.getRootElement()
```

4. 获取标签的子标签

```java
//获取所有子标签
List<Element> sonElementList = rootElement.elements();
//获取指定标签名的子标签
List<Element> sonElementList = rootElement.elements("标签名");
```

5. 获取标签体内的文本

```java
String text = element.getText();
```

6. 获取标签的某个属性的值

```java
String value = element.AttributeValue("属性名");
```

## 2. Tomcat(最重要)

### 2.1 Web服务器

-  Web服务器通常由硬件和软件共同构成。 
   -  硬件：电脑，提供服务供其它客户电脑访问
   -  软件：电脑上安装的服务器软件，安装后能提供服务给网络中的其他计算机，==**将本地文件映射成一个虚拟的url地址供网络中的其他人访问。**== 
-  Web服务器主要用来接收客户端发送的请求和响应客户端请求。 
-  ==常见的JavaWeb服务器：== 
   - **Tomcat（Apache）**：当前应用最广的JavaWeb服务器
   - JBoss（Redhat红帽）：支持JavaEE，应用比较广EJB容器 –> SSH轻量级的框架代替
   - GlassFish（Orcale）：Oracle开发JavaWeb服务器，应用不是很广
   - Resin（Caucho）：支持JavaEE，应用越来越广
   - Weblogic（Orcale）：要钱的！支持JavaEE，适合大型项目
   - Websphere（IBM）：要钱的！支持JavaEE，适合大型项目

### 2.2 Tomcat服务器

#### 2.2.1 Tomcat简介

Tomcat是Apache 软件基金会（Apache Software Foundation）的Jakarta 项目中的一个核心项目，由Apache、Sun 和其他一些公司及个人共同开发而成。由于有了Sun 的参与和支持，最新的Servlet 和JSP 规范总是能在Tomcat 中得到体现，因为Tomcat 技术先进、性能稳定，而且免费，因而深受Java 爱好者的喜爱并得到了部分软件开发商的认可，成为目前比较流行的Web 应用服务器。

#### 2.2.2 Tomcat下载

- Tomcat官方网站：[http://tomcat.apache.org/](http://tomcat.apache.org/)
- 安装版：需要安装，一般不考虑使用。
- 解压版: 直接解压缩使用，我们使用的版本。
- **因为tomcat服务器软件需要使用java环境，所以需要正确配置JAVA_HOME**。

#### 2.2.3 Tomcat的版本

- 版本：企业用的比较广泛的是7.0和8.0的。授课我们使用8.0。
- tomcat6以下都不用了，所以我们从tomcat6开始比较： 
   - tomcat6 	支持servlet2.5、jsp2.1、el
   - tomcat7 	支持servlet3.0、jsp2.2、el2.2、websocket1.1
   - tomcat8	 支持servlet3.1、jsp2.3、el3.0、websocket1.1
   - tomcat9	 支持servlet4.0、jsp2.3、el3.0、websocket1.1

#### 2.2.4 安装

解压apache-tomcat-8.5.88.zip到**非中文无空格**目录中

D:\SDK_YPT\Apache\apache-tomcat-8.5.88，这个目录下直接包含Tomcat的bin目录，conf目录等，我们称之为**Tomcat的安装目录或根目录**。

- bin：该目录下存放的是二进制可执行文件，如果是安装版，那么这个目录下会有两个exe文件：tomcat6.exe、tomcat6w.exe，前者是在控制台下启动Tomcat，后者是弹出GUI窗口启动Tomcat；如果是解压版，那么会有startup.bat和shutdown.bat文件，startup.bat用来启动Tomcat，但需要先配置JAVA_HOME环境变量才能启动，shutdawn.bat用来停止Tomcat；
- conf：这是一个非常非常重要的目录，这个目录下有四个最为重要的文件： 
   - **server.xml：配置整个服务器信息。例如修改端口号。默认HTTP请求的端口号是：8080**
   - tomcat-users.xml：存储tomcat用户的文件，这里保存的是tomcat的用户名及密码，以及用户的角色信息。可以按着该文件中的注释信息添加tomcat用户，然后就可以在Tomcat主页中进入Tomcat Manager页面了；
   - web.xml：部署描述符文件，这个文件中注册了很多MIME类型，即文档类型。这些MIME类型是客户端与服务器之间说明文档类型的，如用户请求一个html网页，那么服务器还会告诉客户端浏览器响应的文档是text/html类型的，这就是一个MIME类型。客户端浏览器通过这个MIME类型就知道如何处理它了。当然是在浏览器中显示这个html文件了。但如果服务器响应的是一个exe文件，那么浏览器就不可能显示它，而是应该弹出下载窗口才对。MIME就是用来说明文档的内容是什么类型的！
   - context.xml：对所有应用的统一配置，通常我们不会去配置它。
- lib：Tomcat的类库，里面是一大堆jar文件。如果需要添加Tomcat依赖的jar文件，可以把它放到这个目录中，当然也可以把应用依赖的jar文件放到这个目录中，这个目录中的jar所有项目都可以共享之，但这样你的应用放到其他Tomcat下时就不能再共享这个目录下的jar包了，所以建议只把Tomcat需要的jar包放到这个目录下；
- logs：这个目录中都是日志文件，记录了Tomcat启动和关闭的信息，如果启动Tomcat时有错误，那么异常也会记录在日志文件中。
- temp：存放Tomcat的临时文件，这个目录下的东西可以在停止Tomcat后删除！
- **webapps：存放web项目的目录，其中每个文件夹都是一个项目**；如果这个目录下已经存在了目录，那么都是tomcat自带的项目。其中ROOT是一个特殊的项目，在地址栏中访问：http://127.0.0.1:8080，没有给出项目目录时，对应的就是ROOT项目。[http://localhost:8080/examples，进入示例项目。其中examples](http://localhost:8080/examples%EF%BC%8C%E8%BF%9B%E5%85%A5%E7%A4%BA%E4%BE%8B%E9%A1%B9%E7%9B%AE%E3%80%82%E5%85%B6%E4%B8%ADexamples)就是项目名，即文件夹的名字。
- work：运行时生成的文件，最终运行的文件都在这里。通过webapps中的项目生成的！可以把这个目录下的内容删除，再次运行时会生再次生成work目录。当客户端用户访问一个JSP文件时，Tomcat会通过JSP生成Java文件，然后再编译Java文件生成class文件，生成的java和class文件都会存放到这个目录下。
- LICENSE：许可证。
- NOTICE：说明文件。

#### 2.2.5 配置

启动Tomcat前，需要配置如下的环境变量

① 配置JAVA_HOME环境变量

② 在Path环境变量中加入JAVA_HOME\bin目录

#### 2.2.6 启动

在命令行中运行**catalina run**或者 Tomcat解压目录下**双击startup.bat** 启动Tomcat服务器，在浏览器地址栏访问如下地址进行测试

[**http://localhost:8080**](http://localhost:8080)

如果启动失败，查看如下的情况：

情况一：如果双击startup.bat后窗口一闪而过，请查看JAVA_HOME是否配置正确。

==startup.bat会调用catalina.bat，而catalina.bat会调用setclasspath.bat，setclasspath.bat会使用JAVA_HOME环境变量，所以我们必须在启动Tomcat之前把JAVA_HOME配置正确。==

情况二：如果启动失败，提示端口号被占用，则将默认的8080端口修改为其他未使用的值，例如8989等。

【方法】 打开：解压目录\conf\server.xml，找到第一个Connector标签，修改port属性

> web服务器在启动时，实际上是监听了本机上的一个端口，当有客户端向该端口发送请求时，web服务器就会处理请求。但是如果不是向其所监听的端口发送请求，web服务器不会做任何响应。例如：Tomcat启动监听了8989端口，而访问的地址是[http://localhost:8080](http://localhost:8080)，将不能正常访问。


#### 2.2.7 在IDEA中创建Tomcat

在IDEA中配置好Tomcat后，可以直接通过IDEA控制Tomcat的启动和停止，而不用再去操作startup.bat和shutdown.bat。

### 2.3 动态Web工程部署与测试

#### 2.3.1 创建**动态**Web工程

#### 2.3.2 开发项目目录结构说明

-  **src：存放Java源代码的目录。** 
-  web：存放的是需要部署到服务器的文件 
   - ==WEB-INF：**这个目录下的文件，是不能被客户端直接访问的。**== 
      - **lib：用于存放该工程用到的库。粘贴过来以后**
      - **web.xml：web工程的配置文件，完成用户请求的逻辑名称到真正的servlet类的映射。**
> ==凡是客户端能访问的资源(_.html或 _.jpg)必须跟WEB-INF在同一目录，即放在Web根目录下的资源，从客户端是可以通过URL地址直接访问的。==

#### 2.3.3 tomcat实例的基础设置

由于每次创建项目随之创建的tomcat实例名字都类似，所以建议修改一下tomcat实例的名称

........
