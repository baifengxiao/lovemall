## Axios

#### 1 Axios简介

使用原生的JavaScript程序执行Ajax极其繁琐，所以一定要使用框架来完成。而Axios就是目前最流行的前端Ajax框架。

Axios官网：http://www.axios-js.com/

![images](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/img005.png)

使用Axios和使用Vue一样，导入对应的*.js文件即可。官方提供的script标签引入方式为：

```html
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
```

我们可以把这个axios.min.js文件下载下来保存到本地来使用。

#### 2 Axios基本用法

##### 2.1 在前端页面引入开发环境

```html
<script type="text/javascript" src="/demo/static/vue.js"></script>
<script type="text/javascript" src="/demo/static/axios.min.js"></script>
```

##### 2.2 发送普通请求参数

###### ① 前端代码

HTML标签：

```html
<div id="app">
    <button @click="commonParam">普通请求参数</button>
</div>
```

Vue+axios代码：

```javascript
var vue = new Vue({
    "el":"#app",
    "data":{
        "message":""
    },
    "methods":{
        commonParam(){
            //使用axios发送异步请求
            axios({
                "method":"post",
                "url":"demo01",
                "params":{
                    "userName":"tom",
                    "userPwd":"123456"
                }
            }).then(response => {
                //then里面是处理请求成功的响应数据
                //response就是服务器端的响应数据,是json类型的
                //response里面的data就是响应体的数据
                this.message = response.data
            }).catch(error => {
                //error是请求失败的错误描述
                console.log(error)
            })
        }
    }
})
</script>
```

 ==**注意：所有请求参数都被放到URL地址后面了，哪怕我们现在用的是POST请求方式。**==

###### ② 后端代码

```java

public class ServletDemo01 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //1. 接收请求参数userName和userPwd
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        System.out.println(userName + ":" + userPwd);

        //模拟出现异常
        //int num = 10/0;

        //2. 向浏览器响应数据
        response.getWriter().write("hello world!!!");
    }
}
```

###### ③ 服务器端处理请求失败后

```javascript
catch(error => {     // catch()服务器端处理请求出错后，会调用
    console.log(error);         // error就是出错时服务器端返回的响应数据
});
```

在给catch()函数传入的回调函数中，error对象封装了服务器端处理请求失败后相应的错误信息。其中，axios封装的响应数据对象，是error对象的response属性。response属性对象的结构如下图所示：

![images](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/img012.png)

可以看到，response对象的结构还是和then()函数传入的回调函数中的response是一样的：

> 回调函数：开发人员声明，但是调用时交给系统来调用。像单击响应函数、then()、catch()里面传入的都是回调函数。回调函数是相对于普通函数来说的，普通函数就是开发人员自己声明，自己调用：
>
> ```javascript
> function sum(a, b) {
> return a+b;
> }
> 
> var result = sum(3, 2);
> console.log("result="+result);
> ```

#### 3 发送请求体JSON

##### 3.1 前端代码

HTML代码：

```html
<button @click="sendJsonBody()">请求体JSON</button>
```

Vue+axios代码：

```javascript
<script>
    var vue = new Vue({
        "el":"#app",
        "data":{
            "message":""
        },
        "methods":{
            sendJsonBody(){
                //使用axios发送异步请求，要携带Json请求体的参数
                axios({
                    "method":"post",
                    "url":"demo01",
                    //携带Json请求体参数
                    "data":{
                        "userName":"aobama",
                        "userPwd":"999999"
                    }
                }).then(response => {
                    this.message = response.data
                })
            }
        }
    })
</script>
```

##### 3.2 后端代码

###### ① 加入Gson包

Gson是Google研发的一款非常优秀的<span style="color:blue;font-weight:bold;">JSON数据解析和生成工具</span>，它可以帮助我们将数据在JSON字符串和Java对象之间互相转换。

![images](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/img009.png)

###### ② User类

```java
package com.atguigu.bean;


public class User {
    private String userName;
    private String userPwd;

    public User() {
    }

    public User(String userName, String userPwd) {
        this.userName = userName;
        this.userPwd = userPwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
```

###### ③ Servlet代码

```java
package com.atguigu.servlet;

import com.atguigu.bean.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


public class ServletDemo01 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            //request.getParameter(name),request.getParameterValues(name),request.getParameterMap()这仨方法只能获取普通参数
            //什么是普通参数:1. 地址后面携带的参数  2. 表单提交的参数
            /*String userName = request.getParameter("userName");
            String userPwd = request.getParameter("userPwd");
            System.out.println("客户端传入的参数userName的值为:" + userName + ",传入的userPwd的值为:" + userPwd);*/

            //要获取Json请求体的参数，就必须得进行Json解析:可用来做Json解析的工具jar包有gson、fastjson、jackson(SpringMVC以及SpringBoot默认支持的)
            //做json解析其实就是:1. 将Java对象转成json字符串  2. 将json字符串转成Java对象

            //我们要获取json请求体的参数，其实就是将json请求体的参数封装到User对象中
            //1. 获取Json请求体的内容
            BufferedReader requestReader = request.getReader();
            //2. 从requestReader中循环读取拼接字符串
            StringBuilder stringBuilder = new StringBuilder();
            String buffer = "";
            while ((buffer = requestReader.readLine()) != null) {
                stringBuilder.append(buffer);
            }

            //3. 将stringBuilder转成字符串，这个字符串就是Json请求体
            String jsonBody = stringBuilder.toString();
            //4. 将jsonBody通过Json解析转成User对象
            Gson gson = new Gson();
            User user = gson.fromJson(jsonBody, User.class);

            System.out.println("客户端传入的参数userName的值为:" + user.getUserName() + ",传入的userPwd的值为:" + user.getUserPwd());
            //模拟服务器出现异常
            //int num = 10/0;

            response.getWriter().write("你好世界");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

> ==P.S.：看着很麻烦是吧？别担心，将来我们有了<span style="color:blue;font-weight:bold;">SpringMVC</span>之后，一个<span style="color:blue;font-weight:bold;">@RequestBody</span>注解就能够搞定，非常方便！==

#### 4 服务器端返回JSON数据

##### 4.1 前端代码

```javascript
sendJsonBody(){
    //使用axios发送异步请求，要携带Json请求体的参数
    axios({
        "method":"post",
        "url":"demo01",
        //携带Json请求体参数
        "data":{
            "userName":"aobama",
            "userPwd":"999999"
        }
    }).then(response => {
        //目标是获取响应数据中的用户的用户名或者密码
        this.message = response.data.userName
    })
}
```

##### 4.2 后端代码

###### ① 加入Gson包

仍然需要Gson支持，不用多说

![images](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/img009.png)

###### ② Servlet代码

```java
package com.atguigu.servlet;

import com.atguigu.bean.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


public class ServletDemo01 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            //request.getParameter(name),request.getParameterValues(name),request.getParameterMap()这仨方法只能获取普通参数
            //什么是普通参数:1. 地址后面携带的参数  2. 表单提交的参数
            /*String userName = request.getParameter("userName");
            String userPwd = request.getParameter("userPwd");
            System.out.println("客户端传入的参数userName的值为:" + userName + ",传入的userPwd的值为:" + userPwd);*/

            //要获取Json请求体的参数，就必须得进行Json解析:可用来做Json解析的工具jar包有gson、fastjson、jackson(SpringMVC以及SpringBoot默认支持的)
            //做json解析其实就是:1. 将Java对象转成json字符串  2. 将json字符串转成Java对象

            //我们要获取json请求体的参数，其实就是将json请求体的参数封装到User对象中
            //1. 获取Json请求体的内容
            BufferedReader requestReader = request.getReader();
            //2. 从requestReader中循环读取拼接字符串
            StringBuilder stringBuilder = new StringBuilder();
            String buffer = "";
            while ((buffer = requestReader.readLine()) != null) {
                stringBuilder.append(buffer);
            }

            //3. 将stringBuilder转成字符串，这个字符串就是Json请求体
            String jsonBody = stringBuilder.toString();
            //4. 将jsonBody通过Json解析转成User对象
            Gson gson = new Gson();
            User user = gson.fromJson(jsonBody, User.class);

            System.out.println("客户端传入的参数userName的值为:" + user.getUserName() + ",传入的userPwd的值为:" + user.getUserPwd());
            //模拟服务器出现异常
            //int num = 10/0;

            //服务器端向客户端响应普通字符串
            //response.getWriter().write("你好世界");

            //在实际开发中服务器端向客户端响应的99%都会是Json字符串
            User responseUser = new User("周杰棍","ggggggg");

            //将responseUser转成json字符串
            String responseJson = gson.toJson(responseUser);

            response.getWriter().write(responseJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
