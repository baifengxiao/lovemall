Huawei Cloud EulerOS 2.0服务器安装jdk

1，下载jdk上传到服务器

​	（略，windows安装篇有下载链接）

2，解压到指定路径（以/usr/local 为例）

​	tar -zxvf jdk-8u201-linux-x64.tar.gz  -C /usr/local/

3.改个简短的名字（可选）

mv jdk1.8.0_201/ jdk

4.修改配置文件

vim /etc/profile

```xml
export JAVA_HOME=/usr/local/jdk
export PATH=$PATH:$JAVA_HOME/bin
```

5.使配置文件立即生效

```xml
source /etc/profile
```

6.测试安装结果

java -version

![image-20230802014322693](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/image-20230802014322693.png)

如图则安装成功！