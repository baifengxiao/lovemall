# Huawei Cloud EulerOS 2.0服务器安装mysql8.0.34

### 1.下载（519.8M那个）

![image-20230725074332695](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/image-20230725074332695.png)

### 2.创建目录并解压

```
#创建目录
mkdir /usr/local/SDK_YPT/mysql

#进入安装目录
cd  /usr/local/SDK_YPT/mysql/

#解压文件
tar -zxvf mysql-8.0.34-linux-glibc2.28-x86_64.tar.gz

#并重命名
mv mysql-8.0.34-linux-glibc2.28-x86_64.tar.gz mysql-8.0.34
```

### 3.添加mysql用户及对应的组　

```
#添加用户组
groupadd mysql

#useradd -r参数表示mysql用户是系统用户，不可用于登录系统
useradd -r -g mysql mysql

#创建data目录
mkdir /usr/local/SDK_YPT/mysql/data

#将/usr/local/mysql/的所有者及所属组改为mysql
chown -R mysql.mysql /usr/local/SDK_YPT/mysql/
```

### 4.创建配置文件

```
vim /etc/my.cnf
```

```cnf
[mysqld]
## 基础位置
basedir = /usr/local/SDK_YPT/mysql/mysql-8.0.34
## 数据存放位置
datadir = /usr/local/SDK_YPT/mysql/data
## 端口
port = 10241
  
socket = /tmp/mysql.sock
## 字符集
character-set-server=utf8
  
log-error = /usr/local/SDK_YPT/mysql/data/mysqld.log
pid-file = /usr/local/SDK_YPT/mysql/data/mysqld.pid

```

### 5.进行初始化

```
/usr/local/SDK_YPT/mysql/mysql-8.0.34/bin/mysqld --initialize --user=mysql --basedir=/usr/local/SDK_YPT/mysql/mysql-8.0.34 --datadir=/usr/local/SDK_YPT/mysql/data/ 
#在日志文件中找到密码
cat /usr/local/SDK_YPT/mysql/data/mysqld.log
```

###  6.执行启动命令

```
/usr/local/SDK_YPT/mysql/mysql-8.0.34/support-files/mysql.server start
```

###  7.添加全局mysql环境变量

```
echo 'export PATH="$PATH:/usr/local/SDK_YPT/mysql/mysql-8.0.34/bin"' >>  /etc/profile 

#使立即生效
source /etc/profile

#如果报错，修改/etc/profile配置文件，将
export TMOUT=600
readonly TMOUT
#这两行注释掉

#解释：export TMOUT=600 :是用户在指定秒数内没有活动（操作）时间
#readonly TMOUT: 是防止用户在命令行执行TMOUT=0操作
```

　　

### 8.登录并修改密码

```
mysql -u root -p
```

1. 修改root密码

```
ALTER USER 'root'@'localhost' IDENTIFIED BY 'your_strong_password';
```

   

2. 创建新用户：

```
CREATE USER 'abcd'@'%' IDENTIFIED BY '123456';
```

3. 授予用户全部权限：

```
GRANT ALL PRIVILEGES ON *.* TO 'abcd'@'%';
```

4. 刷新权限以应用更改：

```
FLUSH PRIVILEGES;
```

### 9.设置机器启动时候自动启动mysql

```
## 复制到自启动路径下
cp /usr/local/SDK_YPT/mysql/mysql-8.0.34/support-files/mysql.server /etc/init.d/mysqld
## 显示服务列表
chkconfig --list
## 添加服务
chkconfig --add mysqld
## 重新查看显示服务列表
chkconfig --list
## 如果是关闭的话，使用下面命令将其开启
chkconfig --level 345 mysqld on
```

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/2020241-20230612164252190-1060039843.png)

 

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/2020241-20230612164316620-756314218.png)

 

### 10.安全设置

1. 删除测试数据库：MySQL安装后，可能会自带一些测试数据库，如`test`，`mysql`等。这些数据库对于生产环境没有必要，可以将其删除。

   ```
   sqlCopy code
   DROP DATABASE IF EXISTS test;
   ```

2. 移除匿名用户：MySQL默认创建了一个允许匿名用户访问的账户，为了安全起见，应该将其删除。

   ```
   sqlCopy code
   DELETE FROM mysql.user WHERE User='';
   ```