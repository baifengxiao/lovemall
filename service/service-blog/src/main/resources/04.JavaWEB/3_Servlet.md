# 第五章 Servlet

## 1 我们为什么需要Servlet？

### 1.1 Web应用基本运行模式

### 1.2 Web服务器中Servlet作用举例

- 举例一：插入数据

- 举例二：查询数据

## 2 什么是Servlet？

**如果把Web应用比作一个餐厅，Servlet就是餐厅中的服务员**——负责接待顾客、上菜、结账。

- 从广义上来讲，Servlet规范是Sun公司制定的一套技术标准，包含与Web应用相关的一系列接口，是Web应用实现方式的宏观解决方案。而具体的Servlet容器负责提供标准的实现。
- 从狭义上来讲，Servlet指的是javax.servlet.Servlet接口及其子接口，也可以指实现了Servlet接口的实现类。
- Servlet（**Server Applet**）作为服务器端的一个组件，它的本意是“服务器端的小程序”。 
  - **Servlet的实例对象由Servlet容器负责创建；**
  - **Servlet的方法由容器在特定情况下调用；**
  - **Servlet容器会在Web应用卸载时销毁Servlet对象的实例。**

## 3 如何使用Servlet？

### 3.1 案例—获取servlet参数

-  使用Servlet接口的方式：
   ① 搭建Web开发环境
   ② 创建动态Web工程

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.sc.love</groupId>
    <artifactId>javaweb</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>Servlet</name>
    <packaging>war</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.source>1.8</maven.compiler.source>
        <junit.version>5.9.1</junit.version>
    </properties>

    <dependencies>
        <!--        servlet依赖-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
        <!--        单元测试-->
        <!--        <dependency>-->
        <!--            <groupId>org.junit.jupiter</groupId>-->
        <!--            <artifactId>junit-jupiter-api</artifactId>-->
        <!--            <version>${junit.version}</version>-->
        <!--            <scope>test</scope>-->
        <!--        </dependency>-->
        <!--        <dependency>-->
        <!--            <groupId>org.junit.jupiter</groupId>-->
        <!--            <artifactId>junit-jupiter-engine</artifactId>-->
        <!--            <version>${junit.version}</version>-->
        <!--            <scope>test</scope>-->
        <!--        </dependency>-->
    </dependencies>

</project>
```

③AddServlet
servlet类

```
package cn.sc.love.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YPT
 * @create 2023-04-20-22:36
 */
public class TestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        Integer price = Integer.valueOf(req.getParameter("price"));
        Integer fcount = Integer.valueOf(req.getParameter("fcount"));
        String remark = req.getParameter("remark");

        System.out.println("fname=" + fname);
        System.out.println("price=" + price);
        System.out.println("fcount=" + fcount);
        System.out.println("remark=" + remark);
    }
}

```

④ index.html 
使用servlet的页面

```java
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="fruit" method="post">
    <table>
        名称：<input type="text" name="fname"/><br/>
        价格：<input type="text" name="price"/><br/>
        库存：<input type="text" name="fcount"/><br/>
        备注：<input type="text" name="remark"/><br/>
        <input type="submit" value="添加"/><br/>
    </table>
</form>
</body>
</html>
```

	⑤ 在web.xml配置文件中**注册**AddServlet

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>TestServlet</servlet-name>
        <servlet-class>cn.sc.love.servlet.TestServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>TestServlet</servlet-name>
        <url-pattern>/fruit</url-pattern>
    </servlet-mapping>

</web-app>
```

> 说明：
>
> -  ：这个url-pattern可以配置多个，这时表示的就是访问这些url都会触发这个Servlet进行响应，运行浏览器，访问刚才配置的url路径，Servlet的service方法就会被调用。 
> -  中的文本内容必须以 / 或 *. 开始书写路径。相当于将资源映射到项目根目录下形成虚拟的资源文件。 
> -  中的可以声明多个，可以通过任意一个都可以访问。但是开发中一般只会配置一个。 


### 3.2 运行分析(执行原理)

- index.html ：通过页面找web.xml里的配置参数
- web.xml：通过配置里的url-pattern找到使用的类，然后调用该类
- 如果配置文件一旦修改，需要重启服务器来重新部署web项目。

### 3.3 Servlet作用总结

-  接收请求 【解析请求报文中的数据：请求参数】 
-  处理请求 【DAO和数据库交互】 
-  完成响应 【设置响应报文】 

## 4.编码设置

    tomcat8之前，设置编码：
      1)get请求方式：
        //get方式目前不需要设置编码（基于tomcat8）
        //如果是get请求发送的中文数据，转码稍微有点麻烦（tomcat8之前）
        String fname = request.getParameter("fname");
        //1.将字符串打散成字节数组
        byte[] bytes = fname.getBytes("ISO-8859-1");
        //2.将字节数组按照设定的编码重新组装成字符串
        fname = new String(bytes,"UTF-8");
      2)post请求方式：
        request.setCharacterEncoding("UTF-8");
    tomcat8开始，设置编码，只需要针对post方式
        request.setCharacterEncoding("UTF-8");
    注意：
        需要注意的是，设置编码(post)这一句代码必须在所有的获取参数动作之前

## 5.servlet继承关系以及service方法

1. 继承关系
   javax.servlet.Servlet接口
       javax.servlet.GenericServlet抽象类
           javax.servlet.http.HttpServlet抽象子类

   2. 相关方法
      javax.servlet.Servlet接口:
      void init(config) - 初始化方法
      void service(request,response) - 服务方法
      void destory() - 销毁方法

     javax.servlet.GenericServlet抽象类：
       void service(request,response) - 仍然是抽象的

     javax.servlet.http.HttpServlet 抽象子类：
       void service(request,response) - 不是抽象的
       1. String method = req.getMethod(); 获取请求的方式
       2. 各种if判断，根据请求方式不同，决定去调用不同的do方法
           if (method.equals("GET")) {
               this.doGet(req,resp);
           } else if (method.equals("HEAD")) {
               this.doHead(req, resp);
           } else if (method.equals("POST")) {
               this.doPost(req, resp);
           } else if (method.equals("PUT")) {
       3. 在HttpServlet这个抽象类中，do方法都差不多:
       protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           String protocol = req.getProtocol();
           String msg = lStrings.getString("http.method_get_not_supported");
           if (protocol.endsWith("1.1")) {
               resp.sendError(405, msg);
           } else {
               resp.sendError(400, msg);
           }
       }
   3.小结：

   1) 继承关系： HttpServlet -> GenericServlet -> Servlet
   2) Servlet中的核心方法： init() , service() , destroy()
   3) 服务方法： 当有请求过来时，service方法会自动响应（其实是tomcat容器调用的）
          在HttpServlet中我们会去分析请求的方式：到底是get、post、head还是delete等等
          然后再决定调用的是哪个do开头的方法
          那么在HttpServlet中这些do方法默认都是405的实现风格-要我们子类去实现对应的方法，否则默认会报405错误
   4) 因此，我们在新建Servlet时，我们才会去考虑请求方法，从而决定重写哪个do方法

## 6 Servlet生命周期

### 1 Servlet生命周期概述

- 应用程序中的对象不仅在空间上有层次结构的关系，在时间上也会因为处于程序运行过程中的不同阶段而表现出不同状态和不同行为——这就是对象的生命周期。
- 简单的叙述生命周期，就是对象在容器中从开始创建到销毁的过程。

### 2 Servlet容器

Servlet对象是Servlet容器创建的，生命周期方法都是由容器调用的。这一点和我们之前所编写的代码有很大不同。在今后的学习中我们会看到，越来越多的对象交给容器或框架来创建，越来越多的方法由容器或框架来调用，开发人员要尽可能多的将精力放在业务逻辑的实现上。

### 3 Servlet生命周期的主要过程

#### ① Servlet对象的创建：构造器

- 默认情况下，==**Servlet容器第一次收到HTTP请求时创建对应Servlet对象。**==
- ==容器之所以能做到这一点是由于我们在注册Servlet时提供了全类名，容器使用反射技术创建了Servlet的对象。==

#### ② Servlet对象初始化：init()

- ==Servlet容器**创建Servlet对象之后，会调用init(ServletConfig config)**方法。==
- 作用：是在Servlet对象创建后，执行一些初始化操作。例如，读取一些资源文件、配置文件，或建立某种连接（比如：数据库连接）
- ==init()方法只在创建对象时执行一次，以后再接到请求时，就不执行了==
- 在javax.servlet.Servlet接口中，public void init(ServletConfig config)方法要求容器将ServletConfig的实例对象传入，这也是我们获取ServletConfig的实例对象的根本方法。

#### ③ 处理请求：service()

- 在javax.servlet.Servlet接口中，定义了**service(ServletRequest req, ServletResponse res)**方法处理HTTP请求。
- 在每次接到请求后都会执行。
- 上一节提到的Servlet的作用，主要在此方法中体现。
- 同时要求容器将ServletRequest对象和ServletResponse对象传入。

#### ④ Servlet对象销毁：destroy()

- 服务器重启、服务器停止执行或Web应用卸载时会销毁Servlet对象，会调用public void destroy()方法。
- 此方法用于销毁之前执行一些诸如释放缓存、关闭连接、保存内存数据持久化等操作。

### 4 Servlet请求过程

- 第一次请求执行：
  - 调用构造器，创建对象
  - 执行init()方法
  - 执行service()方法
- 后面请求执行：
  - 执行service()方法
- 对象销毁前 
  - 执行destroy()方法

代码示例

```
package cn.sc.love.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * @author YPT
 * @create 2023-04-21-4:54
 */
public class LifeCycle extends HttpServlet {

    public LifeCycle() {
        System.out.println("正在实例化");
    }


    @Override
    public void init() throws ServletException {
        System.out.println("周期初始化");

    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("正在服务");
    }

    @Override
    public void destroy() {
        System.out.println("正在销毁");
    }
}

```

## 7. HTTP协议简介

-  **HTTP 超文本传输协议** (HTTP-Hypertext transfer protocol)，是一个属于应用层的面向对象的协议，由于其简捷、快速的方式，适用于分布式超媒体信息系统。它于1990年提出，经过十几年的使用与发展，得到不断地完善和扩展。**它是一种详细规定了浏览器和万维网服务器之间互相通信的规则**，通过因特网传送万维网文档的数据传送协议。 
-  客户端与服务端通信时传输的内容我们称之为**报文**。**HTTP协议就是规定报文的格式。** 
-  HTTP就是一个通信规则，这个规则规定了客户端发送给服务器的报文格式，也规定了服务器发送给客户端的报文格式。实际我们要学习的就是这两种报文。客户端发送给服务器的称为”**请求报文**“，服务器发送给客户端的称为”**响应报文**“。 
-  类比于生活中案例：① 毕业租房，签署租房协议，规范多方需遵守的规则。②与远方的朋友的写信。信封的规范。
   实际互联网： 
   - 客户端 与 服务端进行通信。比如：用户 ---> 访问京东（就是一个数据传输的过程），数据传输需要按照一种协议去传输。就如，用户给服务器写信；服务器给用户回信。有格式：协议。HTTP协议规定通信规则。规定互联网之间如何传输数据。 
     - 信：报文。
     - 写信：用户给服务器写信，用户给服务器发请求。把发的请求所有数据，请求报文
     - 回信：服务器回信给用户，回给浏览器。把服务器响应浏览器的所有数据，响应报文

### 1 HTTP协议的发展历程

- 超文本传输协议的前身是世外桃源(Xanadu)项目，超文本的概念是泰德·纳尔森(Ted Nelson)在1960年代提出的。进入哈佛大学后，纳尔森一直致力于超文本协议和该项目的研究，但他从未公开发表过资料。1989年，**蒂姆·伯纳斯·李**(Tim Berners Lee)在CERN(欧洲原子核研究委员会 = European Organization for Nuclear Research)担任软件咨询师的时候，开发了一套程序，**奠定了万维网(WWW = World Wide Web)**的基础。1990年12月，超文本在CERN首次上线。1991年夏天，继Telnet等协议之后，超文本转移协议成为互联网诸多协议的一分子。
- 当时，**Telnet协议**解决了一台计算机和另外一台计算机之间一对一的控制型通信的要求。邮件协议解决了一个发件人向少量人员发送信息的通信要求。**文件传输协议**解决一台计算机从另外一台计算机批量获取文件的通信要求，但是它不具备一边获取文件一边显示文件或对文件进行某种处理的功能。**新闻传输协议**解决了一对多新闻广播的通信要求。而**超文本要解决的通信要求是**：在一台计算机上获取并显示存放在多台计算机里的文本、数据、图片和其他类型的文件；它包含两大部分：超文本转移协议和超文本标记语言(HTML)。HTTP、HTML以及浏览器的诞生给互联网的普及带来了飞跃。

### 2 HTTP1.0和HTTP1.1的区别

==在HTTP1.0版本中，浏览器请求一个带有图片的网页，会由于下载图片而与服务器之间开启一个新的连接；但在HTTP1.1版本中，允许浏览器在拿到当前请求对应的全部资源后再断开连接，提高了效率==。

### 3 报文

#### 1 报文格式

![](D:/Downloads/images/1557672592385.png)

- 报文： 
  - 请求报文：浏览器发给服务器
  - 响应报文：服务器发回给浏览器

#### 2 请求报文

##### ① ==报文格式 (4部分)==

- 请求首行（**请求行**）；
- 请求头信息（**请求头**）；
- ==空行；==
- 请求体；

##### ② GET请求

1、==由于请求参数在请求首行中已经携带了，所以没有请求体，也没有请求空行==
2、请求参数拼接在url地址中，地址栏可见[url?name1=value1&name2=value2]，不安全
3、==由于参数在地址栏中携带，所以由大小限制[地址栏数据大小一般限制为4k]，只能携带纯文本==
4、get请求参数只能上传文本数据
5、没有请求体。所以封装和解析都快，效率高， 浏览器默认提交的请求都是get请求[比如：① 地址栏输入url地址回车，②点击超链接a ， ==③ form表单默认方式...]==

- **请求首行**

```http
GET /05_web_tomcat/login_success.html?username=admin&password=123213 HTTP/1.1


```

==请求方式 	访问的服务器中的资源路径？get请求参数	协议版本==

- **请求头**

==Host: localhost:8080   主机虚拟地址==
==Connection: keep-alive 长连接==
==Upgrade-Insecure-Requests: 1  请求协议的自动升级[http的请求，服务器却是https的，浏览器自动会将请求协议升级为https的]==
==User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36==

- ==用户系统信息==
  ==Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8==
- ==浏览器支持的文件类型==
  ==Referer: [http://localhost:8080/05_web_tomcat/login.html](http://localhost:8080/05_web_tomcat/login.html)==
- ==当前页面的上一个页面的路径[当前页面通过哪个页面跳转过来的]：   可以通过此路径跳转回上一个页面， 广告计费，防止盗链==
  ==Accept-Encoding: gzip, deflate, br==
- ==浏览器支持的压缩格式==
  ==Accept-Language: zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7==
- ==浏览器支持的语言==

##### ③ POST请求

- POST请求要求将form标签的method的属性设置为post

![](D:/Downloads/images/1557672877007.png)

==1、POST请求有请求体，而GET请求没有请求体。==
==2、post请求数据在请求体中携带，请求体数据大小没有限制，可以用来上传所有内容[文件、文本]==
==3、只能使用post请求上传文件==
==4、post请求报文多了和请求体相关的配置[请求头]==
==5、地址栏参数不可见，相对安全==
==6、post效率比get低==

- 请求首行

```http
POST /05_web_tomcat/login_success.html HTTP/1.1
```

- 请求头

```http
Host: localhost:8080
Connection: keep-alive
Content-Length: 31 		-请求体内容的长度
Cache-Control: max-age=0  -无缓存
Origin: http://localhost:8080
Upgrade-Insecure-Requests: 1  -协议的自动升级
Content-Type: application/x-www-form-urlencoded   -请求体内容类型[服务器根据类型解析请求体参数]
User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.75 Safari/537.36
Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
Referer: http://localhost:8080/05_web_tomcat/login.html
Accept-Encoding: gzip, deflate, br
Accept-Language: zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7
Cookie:JSESSIONID-
```

- 请求空行
- 请求体：浏览器提交给服务器的内容

```http
username=admin&password=1232131
```

#### ==3 响应报文==

##### ① 报文格式(4部分)

- 响应首行（**响应行**）；
- 响应头信息（**响应头**）；
- 空行；
- **响应体；**

##### ② 具体情况

-  **响应首行：** 

HTTP/1.1 200 OK

说明：==响应协议==为HTTP1.1，==响应状态码==为200，表示请求成功；

-  **响应头：** 

```http
Server: Apache-Coyote/1.1   服务器的版本信息
Accept-Ranges: bytes
ETag: W/"157-1534126125811"
Last-Modified: Mon, 13 Aug 2018 02:08:45 GMT
Content-Type: text/html    响应体数据的类型[浏览器根据类型解析响应体数据]
Content-Length: 157   响应体内容的字节数
Date: Mon, 13 Aug 2018 02:47:57 GMT  响应的时间，这可能会有8小时的时区差
```


-  **响应空行** 
-  **响应体** 

```html
<!--需要浏览器解析使用的内容[如果响应的是html页面，最终响应体内容会被浏览器显示到页面中]-->

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		恭喜你，登录成功了...
	</body>
</html>
```


##### ③ 响应码

响应码对浏览器来说很重要，它告诉浏览器响应的结果。比较有代表性的响应码如下：

-  **200：**请求成功，浏览器会把响应体内容（通常是html）显示在浏览器中； 
-  **404：**请求的资源没有找到，说明客户端错误的请求了不存在的资源；
   ![](D:/Downloads/images/1561905338054.png)
-  **500：**请求资源找到了，但服务器内部出现了错误；

-  **302：**重定向，当响应码为302时，表示服务器要求浏览器重新再发一个请求，服务器会发送一个响应头Location，它指定了新请求的URL地址； 

除此之外，其它一些响应码如下：

```
200 - 服务器成功返回网页 
404 - 请求的网页不存在 
503 - 服务不可用 
详细分解：

1xx（临时响应） 
表示临时响应并需要请求者继续执行操作的状态代码。

代码 说明 
100 （继续） 请求者应当继续提出请求。服务器返回此代码表示已收到请求的第一部分，正在等待其余部分。 
101 （切换协议） 请求者已要求服务器切换协议，服务器已确认并准备切换。

2xx （成功） 
表示成功处理了请求的状态代码。

代码 说明 
200 （成功） 服务器已成功处理了请求。通常，这表示服务器提供了请求的网页。 
201 （已创建） 请求成功并且服务器创建了新的资源。 
202 （已接受） 服务器已接受请求，但尚未处理。 
203 （非授权信息） 服务器已成功处理了请求，但返回的信息可能来自另一来源。 
204 （无内容） 服务器成功处理了请求，但没有返回任何内容。 
205 （重置内容） 服务器成功处理了请求，但没有返回任何内容。 
206 （部分内容） 服务器成功处理了部分 GET 请求。

3xx （重定向） 
表示要完成请求，需要进一步操作。 通常，这些状态代码用来重定向。

代码 说明 
300 （多种选择） 针对请求，服务器可执行多种操作。服务器可根据请求者 (user agent) 选择一项操作，或提供操作列表供请求者选择。 
301 （永久移动） 请求的网页已永久移动到新位置。服务器返回此响应（对 GET 或 HEAD 请求的响应）时，会自动将请求者转到新位置。 
302 （临时移动） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。 
303 （查看其他位置） 请求者应当对不同的位置使用单独的 GET 请求来检索响应时，服务器返回此代码。 
304 （未修改） 自从上次请求后，请求的网页未修改过。服务器返回此响应时，不会返回网页内容。 
305 （使用代理） 请求者只能使用代理访问请求的网页。如果服务器返回此响应，还表示请求者应使用代理。 
307 （临时重定向） 服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。

4xx（请求错误） 
这些状态代码表示请求可能出错，妨碍了服务器的处理。

代码 说明 
400 （错误请求） 服务器不理解请求的语法。 
401 （未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。 
403 （禁止） 服务器拒绝请求。 
404 （未找到） 服务器找不到请求的网页。 
405 （方法禁用） 禁用请求中指定的方法。 
406 （不接受） 无法使用请求的内容特性响应请求的网页。 
407 （需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。 
408 （请求超时） 服务器等候请求时发生超时。 
409 （冲突） 服务器在完成请求时发生冲突。服务器必须在响应中包含有关冲突的信息。 
410 （已删除） 如果请求的资源已永久删除，服务器就会返回此响应。 
411 （需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。 
412 （未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。 
413 （请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。 
414 （请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。 
415 （不支持的媒体类型） 请求的格式不受请求页面的支持。 
416 （请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。 
417 （未满足期望值） 服务器未满足”期望”请求标头字段的要求。

5xx（服务器错误） 
这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。

代码 说明 
500 （服务器内部错误） 服务器遇到错误，无法完成请求。 
501 （尚未实施） 服务器不具备完成请求的功能。例如，服务器无法识别请求方法时可能会返回此代码。 
502 （错误网关） 服务器作为网关或代理，从上游服务器收到无效响应。 
503 （服务不可用） 服务器目前无法使用（由于超载或停机维护）。通常，这只是暂时状态。 
504 （网关超时） 服务器作为网关或代理，但是没有及时从上游服务器收到请求。 
505 （HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本。

HttpWatch状态码Result is

200 - 服务器成功返回网页，客户端请求已成功。 
302 - 对象临时移动。服务器目前从不同位置的网页响应请求，但请求者应继续使用原有位置来进行以后的请求。 
304 - 属于重定向。自上次请求后，请求的网页未修改过。服务器返回此响应时，不会返回网页内容。 
401 - 未授权。请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。 
404 - 未找到。服务器找不到请求的网页。 
2xx - 成功。表示服务器成功地接受了客户端请求。 
3xx - 重定向。表示要完成请求，需要进一步操作。客户端浏览器必须采取更多操作来实现请求。例如，浏览器可能不得不请求服务器上的不同的页面，或通过代理服务器重复该请求。 
4xx - 请求错误。这些状态代码表示请求可能出错，妨碍了服务器的处理。 
5xx - 服务器错误。表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。
```

## 8.会话跟踪技术

==常用的A==PI：==
      ==request.getSession() -> 获取当前的会话，没有则创建一个新的会话==
      ==request.getSession(true) -> 效果和不带参数相同==
      ==request.getSession(false) -> 获取当前会话，没有则返回null，不会创建新的==

1） Http是无状态的

    - HTTP 无状态 ：服务器无法判断这两次请求是同一个客户端发过来的，还是不同的客户端发过来的
        - 无状态带来的现实问题：第一次请求是添加商品到购物车，第二次请求是结账；如果这两次请求服务器无法区分是同一个用户的，那么就会导致混乱
        - 通过会话跟踪技术来解决无状态的问题。

2） 会话跟踪技术
    - 客户端第一次发请求给服务器，服务器获取session，获取不到，则创建新的，然后响应给客户端
        - 下次客户端给服务器发请求时，会把sessionID带给服务器，那么服务器就能获取到了，那么服务器就判断这一次请求和上次某次请求是同一个客户端，从而能够区分开客户端
        - 常用的API：
          request.getSession() -> 获取当前的会话，没有则创建一个新的会话
          request.getSession(true) -> 效果和不带参数相同
          request.getSession(false) -> 获取当前会话，没有则返回null，不会创建新的

      session.getId() -> 获取sessionID
      session.isNew() -> 判断当前session是否是新的
      session.getMaxInactiveInterval() -> session的非激活间隔时长，默认1800秒
      session.setMaxInactiveInterval()
      session.invalidate() -> 强制性让会话立即失效
      ....

3） session保存作用域
  - session保存作用域是和具体的某一个session对应的
  - 常用的API：
    void session.setAttribute(k,v)
    Object session.getAttribute(k)
    void removeAttribute(k)

4）Cookie

1.创建Cookie对象
2.在客户端保存Cookie
3.设置Cookie的有效时长
   cookie.setMaxAge(60)  ， 设置cookie的有效时长是60秒
   cookie.setDomain(pattern);
   cookie.setPath(uri);
4.Cookie的应用：
    4-1: 记住用户名和密码十天 setMaxAge(60 * 60 * 24 * 10)
    4-2: 十天免登录

5)session和cookies关系
Session ID 通常通过 Cookies 在客户端和服务器之间进行传输和管理。通过设置一个包含 Session ID 的 Cookie，服务器可以在客户端的每个请求中识别用户的会话，并检索会话数据。
如果禁用了 Cookies，会话仍然可以存储在服务器端，但服务器无法直接将 Session ID 发送给客户端。
在禁用 Cookies 的情况下，可以使用其他方式来传递 Session ID。以下是一些可能的替代方法：

1. URL 重写：服务器可以通过在每个链接和表单中添加 Session ID 的查询参数或路径参数来传递 Session ID。这样，服务器可以通过解析请求中的 URL 来识别用户的会话。
2. 隐藏表单字段：在包含表单的页面中，可以将 Session ID 添加为一个隐藏的表单字段，并在每次提交表单时将其发送回服务器。
3. HTTP 头：服务器可以通过自定义的 HTTP 头将 Session ID 发送回客户端。例如，可以在响应中包含一个名为 "X-Session-ID" 的头部，并在后续请求中将该头部包含在请求中。

这些替代方法需要在服务器端进行相应的编程和配置，以确保正确地传递和处理会话标识符。它们通常需要更多的开发工作和维护，并且可能对应用程序的性能和安全性产生一些影响。
需要注意的是，如果禁用 Cookies 的同时还禁用了这些替代方法，会话将无法正常工作，因为服务器无法将会话与特定的用户请求关联起来。因此，要在禁用 Cookies 的环境中继续使用会话，必须采取适当的替代方案来传递会话标识符。



## 9. 服务器内部转发以及客户端重定向

一个在==服务端==一个再==客户端==

    1） 服务器内部转发 : request.getRequestDispatcher("...").forward(request,response);
      - 一次请求响应的过程，对于客户端而言，内部经过了多少次转发，客户端是不知道的
      - 地址栏没有变化
    2） 客户端重定向： response.sendRedirect("....");
      - 两次请求响应的过程。客户端肯定知道请求URL有变化
      - 地址栏有变化

## 10.thymeleaf试图模板技术

    1） 添加thymeleaf的jar包
    2） 新建一个Servlet类ViewBaseServlet
    3） 在web.xml文件中添加配置
       - 配置前缀 view-prefix
       - 配置后缀 view-suffix
    4） 使得我们的Servlet继承ViewBaseServlet
    
    5） 根据逻辑视图名称 得到 物理视图名称
    //此处的视图名称是 index
    //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
    //逻辑视图名称 ：   index
    //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
    //所以真实的视图名称是：      /       index       .html
    super.processTemplate("index",request,response);
    6） 使用thymeleaf的标签
      th:if   ,  th:unless   , th:each   ,   th:text

## 11，保存作用域

   原始情况下，保存作用域我们可以认为有四个： page（页面级别，现在几乎不用） , request（一次请求响应范围） , session（一次会话范围） , application（整个应用程序范围）
   1） request：一次请求响应范围
   2） session：一次会话范围有效
   3） application： 一次应用程序范围有效

## 12.Servlet初始化

 1. Servlet生命周期：实例化、初始化、服务、销毁

 2. Servlet中的初始化方法有两个：init() , init(config)

    其中带参数的方法代码如下：
    public void init(ServletConfig config) throws ServletException {
    this.config = config ;
    init();
     }
     另外一个无参的init方法如下：
     public void init() throws ServletException{
     }
     如果我们想要在Servlet初始化时做一些准备工作，那么我们可以重写init方法
     我们可以通过如下步骤去获取初始化设置的数据

   - 获取config对象：ServletConfig config = getServletConfig();
   - 获取初始化参数值： config.getInitParameter(key);

 3. 在web.xml文件中配置Servlet

    ```xml
    <servlet>
        <servlet-name>Demo01Servlet</servlet-name>
        <servlet-class>com.atguigu.servlet.Demo01Servlet</servlet-class>
        <init-param>
            <param-name>hello</param-name>
            <param-value>world</param-value>
        </init-param>
        <init-param>
            <param-name>uname</param-name>
            <param-value>jim</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>Demo01Servlet</servlet-name>
        <url-pattern>/demo01</url-pattern>
    </servlet-mapping>
    ```

 4. 也可以通过注解的方式进行配置：
    @WebServlet(urlPatterns = {"/demo01"} ,
    initParams = {
        @WebInitParam(name="hello",value="world"),
        @WebInitParam(name="uname",value="jim")
    })

5. 学习Servlet中的ServletContext和<context-param>
   1. 获取ServletContext，有很多方法
      在初始化方法中： ServletContxt servletContext = getServletContext();
      在服务方法中也可以通过request对象获取，也可以通过session获取：
      request.getServletContext(); session.getServletContext()
   
   2. 获取初始化值：
      servletContext.getInitParameter();
   
## 13.filter

### 1.1 Filter的概念

Filter：一个实现了特殊接口(Filter)的Java类. 实现对请求资源(jsp,servlet,html,)的过滤的功能.  过滤器是一个运行在服务器的程序, 优先于请求资源(Servlet或者jsp,html)之前执行. 过滤器是javaweb技术中**最为实用**的技术之一

### 1.2 Filter的作用

Filter的作用是对目标资源(Servlet,jsp)进行过滤，其应用场景有: 登录权限检查,解决网站乱码,过滤敏感字符等等

### 1.3 Filter的入门案例

#### 1.3.1 案例目标

实现在请求到达ServletDemo01之前解决请求参数的中文乱码

#### 1.3.2 代码实现

###### ① 创建ServletDemo01

web.xml代码

```xml
<servlet>
    <servlet-name>servletDemo01</servlet-name>
    <servlet-class>com.atguigu.ServletDemo01</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>servletDemo01</servlet-name>
    <url-pattern>/ServletDemo01</url-pattern>
</servlet-mapping>
```

ServletDemo01代码

```java
package com.atguigu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenxin
 * 日期2021-05-18  08:53
 */
public class ServletDemo01 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        System.out.println("ServletDemo01接收到了一个请求..."+username);
    }
}
```

前端页面代码

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
    <form action="/webday12/demo01" method="post">
        用户名<input type="text" name="username"/><br/>
        <input type="submit"/>
    </form>
</body>
</html>
```

如果此时没有Filter，那么客户端发送的请求直接到达ServletDemo01,中文请求参数就会发生乱码

###### ② 创建EncodingFilter

web.xml代码

```xml
<filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>com.atguigu.filter.EncodingFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <!--url-pattern表示指定拦截哪些资源-->
    <url-pattern>/demo01</url-pattern>
</filter-mapping>
```

EncodingFilter代码

```java
package com.atguigu.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author chenxin
 * 日期2021-05-18  08:56
 * 编写过滤器的步骤:
 * 1. 写一个类实现Filter接口，并且重写方法
 * 2. 在web.xml中配置该过滤器的拦截路径
 */
public class EncodingFilter implements Filter {
    @Override
    public void destroy() {
        
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //解决请求参数的乱码
        HttpServletRequest request = (HttpServletRequest) req;
        request.setCharacterEncoding("UTF-8");

        //每次有请求被当前filter接收到的时候，就会执行doFilter进行过滤处理
        System.out.println("EncodingFilter接收到了一个请求...");

        //这句代码表示放行
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        
    }

}
```

### 1.4 Filter的生命周期

#### 1.4.1 回顾Servlet生命周期

###### ① Servlet的创建时机

Servlet默认在第一次接收请求的时候创建，我们可以通过`<load-on-startup>`标签配置Servlet在服务器启动的时候创建

###### ② Servlet的销毁时机

Servlet会在服务器关闭或者将项目从服务器上移除的时候销毁

#### 1.4.2 Filter的生命周期和生命周期方法

| 生命周期阶段 | 执行时机         | 生命周期方法                             |
| ------------ | ---------------- | ---------------------------------------- |
| 创建对象     | Web应用启动时    | init方法，通常在该方法中做初始化工作     |
| 拦截请求     | 接收到匹配的请求 | doFilter方法，通常在该方法中执行拦截过滤 |
| 销毁         | Web应用卸载前    | destroy方法，通常在该方法中执行资源释放  |

### 1.5 过滤器匹配规则

#### 1.5.1 过滤器匹配的目的

过滤器匹配的目的是指定当前过滤器要拦截哪些资源

#### 1.5.2 四种匹配规则

###### ① 精确匹配

指定被拦截资源的完整路径：

```xml
<!-- 配置Filter要拦截的目标资源 -->
<filter-mapping>
    <!-- 指定这个mapping对应的Filter名称 -->
    <filter-name>FilterDemo01</filter-name>

    <!-- 通过请求地址模式来设置要拦截的资源 -->
    <url-pattern>/demo01</url-pattern>
</filter-mapping>
```

上述例子表示要拦截映射路径为`/demo01`的这个资源

###### ② 模糊匹配

相比较精确匹配，使用模糊匹配可以让我们创建一个Filter就能够覆盖很多目标资源，不必专门为每一个目标资源都创建Filter，提高开发效率。

在我们配置了url-pattern为/user/*之后，请求地址只要是/user开头的那么就会被匹配。

```xml
<filter-mapping>
    <filter-name>Target02Filter</filter-name>

    <!-- 模糊匹配：前杠后星 -->
    <!--
        /user/demo01
        /user/demo02
        /user/demo03
		/demo04
    -->
    <url-pattern>/user/*</url-pattern>
</filter-mapping>
```

<span style="color:blue;font-weight:bold;">极端情况：/*匹配所有请求</span>

###### ③ 扩展名匹配

```xml
<filter>
    <filter-name>Target04Filter</filter-name>
    <filter-class>com.atguigu.filter.filter.Target04Filter</filter-class>
</filter>
<filter-mapping>
    <filter-name>Target04Filter</filter-name>
    <url-pattern>*.png</url-pattern>
</filter-mapping>
```

上述例子表示拦截所有以`.png`结尾的请求

#### 1.6 过滤器链

##### 1.6.1 过滤链的概念

一个请求可能被多个过滤器所过滤，==只有当所有过滤器都放行，请求才能到达目标资源，==如果有某一个过滤器没有放行，那么请求则无法到达后续过滤器以及目标资源，多个过滤器组成的链路就是过滤器链

![images](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/img004.png)

##### 1.6.2 过滤器链的顺序

过滤器链中每一个Filter执行的<span style="color:blue;font-weight:bold;">顺序是由web.xml中filter-mapping配置的顺序决定</span>的。如果某个Filter是使用ServletName进行匹配规则的配置，那么这个Filter执行的优先级要更低

##### 1.6.3 过滤器链案例

###### ① 创建ServletDemo01

web.xml代码

```xml
<servlet>
    <servlet-name>servletDemo01</servlet-name>
    <servlet-class>com.atguigu.ServletDemo01</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>servletDemo01</servlet-name>
    <url-pattern>/ServletDemo01</url-pattern>
</servlet-mapping>
```

ServletDemo01代码

```java
public class ServletDemo01 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ServletDemo01接收到了请求...");
    }
}
```

###### ② 创建多个Filter拦截Servlet

```xml
<filter-mapping>
    <filter-name>TargetChain03Filter</filter-name>
    <url-pattern>/Target05Servlet</url-pattern>
</filter-mapping>
<filter-mapping>
    <filter-name>TargetChain02Filter</filter-name>
    <url-pattern>/Target05Servlet</url-pattern>
</filter-mapping>
<filter-mapping>
    <filter-name>TargetChain01Filter</filter-name>
    <url-pattern>/Target05Servlet</url-pattern>
</filter-mapping>
```

## 14.事务管理引入

引入：解决事务，要解决多个事务公用一个连接的问题。

1) 涉及到的组件：
  - OpenSessionInViewFilter
    - 过滤器，将开启事务和提交事务放在try中，回滚放在catch中，一旦出错就会执行回滚。

  - TransactionManager
    - 执行具体的事务操作

  - ThreadLocal
    - 将同1个连接给多个事务用

  - ConnUtil
    - 重构数据库连接

  - BaseDAO
    - 剥离出数据库连接

## 15.ThreadLocal

  - get() , set(obj)

  - ThreadLocal称之为本地线程 。 我们可以通过set方法在当前线程上存储数据、通过get方法在当前线程上获取数据

  - set方法源码分析：

  - ```java
    public void set(T value) {
        Thread t = Thread.currentThread(); //获取当前的线程
        ThreadLocalMap map = getMap(t);    //每一个线程都维护各自的一个容器（ThreadLocalMap）
        if (map != null)
            map.set(this, value);          //这里的key(this)对应的是ThreadLocal，因为我们的组件中需要传输（共享）的对象可能会有多个（不止Connection）
        else
            createMap(t, value);           //默认情况下map是没有初始化的，那么第一次往其中添加数据时，会去初始化
    }
    ```
    
    -get方法源码分析：
    
    ```java
    public T get() {
        Thread t = Thread.currentThread(); //获取当前的线程
        ThreadLocalMap map = getMap(t);    //获取和这个线程（企业）相关的ThreadLocalMap（也就是工作纽带的集合）
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);   //this指的是ThreadLocal对象，通过它才能知道是哪一个工作纽带
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;     //entry.value就可以获取到工具箱了
                return result;
            }
        }
        return setInitialValue();
    }
    ```
    
## 16.Listener监听器

### 1 监听器的简介

####  监听器的概念

监听器：专门用于对其他对象身上发生的事件或状态改变进行监听和相应处理的对象，当被监视的对象发生情况时，立即采取相应的行动。
<span style="color:blue;font-weight:bold;">Servlet监听器</span>：Servlet规范中定义的一种特殊类，它用于监听Web应用程序中的ServletContext，HttpSession 和HttpServletRequest等域对象的创建与销毁事件，以及监听这些域对象中的属性发生修改的事件。

### 2 Servlet监听器的分类(了解)

1) ServletContextListener - 监听ServletContext对象的创建和销毁的过程
2) HttpSessionListener - 监听HttpSession对象的创建和销毁的过程
3) ServletRequestListener - 监听ServletRequest对象的创建和销毁的过程

4) ServletContextAttributeListener - 监听ServletContext的保存作用域的改动(add,remove,replace)
5) HttpSessionAttributeListener - 监听HttpSession的保存作用域的改动(add,remove,replace)
6) ServletRequestAttributeListener - 监听ServletRequest的保存作用域的改动(add,remove,replace)

7) HttpSessionBindingListener - 监听某个对象在Session域中的创建与移除
8) HttpSessionActivationListener - 监听某个对象在Session域中的序列化和反序列化

### 3 ServletContextListener的使用

#### 3.1 作用

ServletContextListener是监听ServletContext对象的创建和销毁的，因为ServletContext对象是在服务器启动的时候创建、在服务器关闭的时候销毁，所以ServletContextListener也可以监听服务器的启动和关闭

#### 3.2 使用场景

==将来学习SpringMVC的时候，会用到一个ContextLoaderListener，这个监听器就实现了ServletContextListener接口，表示对ServletContext对象本身的生命周期进行监控。==

#### 3.3 代码实现

###### ① 创建监听器类

```java
package com.atguigu.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 包名:com.atguigu.listener
 *
 * @author chenxin
 * 日期2021-06-19  10:26
 * 编写监听器的步骤:
 * 1. 写一个类实现对应的：Listener的接口(我们这里使用的是ServletContextListener),并且实现它里面的方法
 *    1.1 contextInitialized()这个方法在ServletContext对象被创建出来的时候执行，也就是说在服务器启动的时候执行
 *    1.2 contextDestroyed()这个方法会在ServletContext对象被销毁的时候执行，也就是说在服务器关闭的时候执行
 *
 * 2. 在web.xml中注册(配置)监听器
 */
public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("在服务器启动的时候，模拟创建SpringMVC的核心容器...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("在服务器启动的时候，模拟销毁SpringMVC的核心容器...");
    }
}
```

###### ② 注册监听器

```xml
<listener>
    <listener-class>com.atguigu.listener.ContextLoaderListener</listener-class>
</listener>
```

