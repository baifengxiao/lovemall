## 第1章 Linux文件与目录结构

### Linux目录结构

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685957995827-d257c5fc-12cb-4218-b50f-a91d4a3434c3.png)

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958004332-b8c33d45-53a7-42d9-af1d-dd2fc668d6ea.png)

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958013702-73d499b1-7cbb-42eb-b31b-69f4a9745035.png)

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958027026-eac16b31-478e-4417-af95-60c049d3e832.png)

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958037268-91c56284-8c1e-4bcc-9b29-75415c12645e.png)

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958047048-64826d9f-4e1e-4a80-a006-a251af06eab6.png)

## 第2章 VI/VIM编辑器

### 2.1 一般模式

以vi/vim打开一个档案就直接进入一般模式了（这是**默认的模式**）

表1-1常用语法

| 语法                        | 功能描述                   |
| --------------------------- | -------------------------- |
| yy                          | **复制**光标当前一行       |
| u                           | **撤销上一步**             |
| dd                          | **删除**光标当前行         |
| ^                           | **移动到行头**             |
| $                           | **移动到行尾**             |
| gg或者1+G                   | **移动到页头（第一行）**   |
| G                           | **移动到页尾（最后一行）** |
| 数字+G（先输入数字，在按G） | **移动到目标行**           |

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958372931-f3c10c6c-fc19-4d91-b78b-bf5f5dca52c4.png)

### 2.2 编辑模式

按下『i, I, o, O, a, A』等任何一个字母之后才会进入编辑模式。

表1-2 常用语法

| 按键 | 功能                   |
| ---- | ---------------------- |
| i    | **当前光标前**         |
| a    | 当前光标后             |
| o    | **当前光标行的下一行** |
| I    | 光标所在行最前         |
| A    | 光标所在行最后         |
| O    | 当前光标行的上一行     |

### 2.3 命令模式

**在一般模式当中**，输入『 **: / ?**』3个中的任何一个按钮进入。

| 命令                 | 功能                                                         |
| -------------------- | ------------------------------------------------------------ |
| :w                   | **保存**                                                     |
| :q                   | **退出**                                                     |
| :!                   | **强制执行**                                                 |
| : %s/old字符/new字符 | **批量替换**                                                 |
| / 要查找的词         | n 查找下一个，N 往上查找                                     |
| ? 要查找的词         | n是查找上一个，N是往下查找                                   |
| :set nu              | 显示行号                                                     |
| :set nonu            | 关闭行号                                                     |
| ZZ（shift+zz）:nohl  | **没有修改文件直接退出，如果修改了文件保存后退****去除高亮显示** |

### 2.4 模式间转换

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958509549-50914995-d3ed-446b-affd-e76085afaff3.png)

## 第3章 网络配置和系统管理操作

### 3.1 配置子网IP和网关

#### 1）查看虚拟网络编辑器

#### 2）修改ip地址

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958788501-272eb16f-6151-4940-ba74-f44b630159cb.png)



#### 3）查看网关

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958811598-05e1ba02-482e-4416-86e0-1e43666977b9.png)

### 3.2 配置虚拟机ip地址

#### 3.2.1 ifconfig 查询ip地址

ifconfig :network interfaces configuring网络接口配置

##### 基本语法

ifconfig

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685958709091-e37b91da-f4f5-4dbb-b000-89dc42456807.png)

#### 3.2.2 修改IP地址

##### 1）修改IP地址

vim /etc/sysconfig/network-scripts/ifcfg-ens33

以下标红的项必须修改，有值的按照下面的值修改，没有该项的要增加。

\#IP的配置方法[none|static|bootp|dhcp]（引导时不使用协议|静态分配IP|BOOTP协议|DHCP协议）

BOOTPROTO=static 

\#IP地址

IPADDR=192.168.2.128   

\#网关  

GATEWAY=192.168.2.2      

\#域名解析器

DNS1=192.168.2.2

​	修改后

：wq  保存退出

即：

```plain
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stabe-privacy
NAME=ens33
UUID=5ce9e724-d53a-44d5-95e7-b7e95dd4d804
ONBOOT=yes

#IP地址
IPADDR=192.168.2.128
#网关  
GATEWAY=192.168.2.2      
#域名解析器
DNS1=192.168.2.2
```



##### 2） 执行systemctl restart network命令

##### 3） 修改windows的VMnet8网卡信息（也可以选择自动获取）

#### 3.2.3 ping 测试主机之间网络连通性

1）基本语法

​	ping 目的主机	（功能描述：测试当前服务器是否可以连接目的主机）

2）测试当前服务器是否可以连接公网

 ping www.baidu.com

### 3.3 配置主机名

#### 3.3.1 hostname 显示和设置系统的主机名称

1）基本语法

hostname		（功能描述：查看当前服务器的主机名称）

#### 3.3.2 修改主机名称

### 3.4 服务管理

#### 3.4.1 临时后台服务管理

基本语法

systemctl  start	服务名		（功能描述：开启服务）

systemctl  stop	服务名		（功能描述：关闭服务）

systemctl   restart	 服务名		（功能描述：重新启动服务）

systemctl   status	 服务名		（功能描述：查看服务状态）

systemctl  --type  service		（功能描述：查看正在运行的服务）

#### 3.4.2 设置后台服务的自启配置

基本语法

systemctl  list-unit-files   	（功能描述：查看所有服务器自启配置）

systemctl  disable 服务名   （功能描述：关掉指定服务的自动启动）

systemctl  enable  服务名  （功能描述：开启指定服务的自动启动）

systemctl  is-enabled 服务名（功能描述：查看服务开机启动状态）

### 3.5 关机重启命令

**正确的关机流程为**：sync > shutdown > reboot >poweroff

#### 3.5.1 基本语法

##### （1）sync  			（功能描述：将数据由内存同步到硬盘中）

##### （2）poweroff		（功能描述：关闭系统，等同于shutdown -h now）

##### （3）reboot 			（功能描述：就是重启，等同于 shutdown -r now）

##### （4）shutdown [选项] 时间	

表1-4

| 选项 | 功能          |
| ---- | ------------- |
| -h   | -h=halt关机   |
| -r   | -r=reboot重启 |

| 参数 | 功能                                   |
| ---- | -------------------------------------- |
| now  | 立刻关机                               |
| 时间 | 等待多久后关机（时间单位是**分钟**）。 |

#### 3.5.2 经验技巧

​	Linux系统中为了提高磁盘的读写效率，对磁盘采取了 “预读迟写”操作方式。

#### 3.5.3 案例实操

##### （1）将数据由内存同步到硬盘中

sync  

##### （2）重启

 reboot 

##### （3）关机

poweroff 

##### （4）关机并输出提示

shutdown -h 1 ‘This server will shutdown after 1 mins’

##### （5）立马关机（等同于 halt）

 shutdown -h now 

##### （6）系统立马重启（等同于 reboot）

 shutdown -r now

## 第4章 远程登录

### 4.1开启sshd服务

systemctl enable --now sshd (设置sshd开机自启，可选)

### 4.2远程连接

通常情况下，使用ssh root@ip连接远程主机。

如果被禁止在root用户下连接，需要在配置文件中更改，将

PermitRootLogin no 改为 PermitRootLogin yes（可选）

## 第5章 常用基本命令

### 5.1 帮助命令

#### 5.1.1 man 获得帮助信息

##### 1）基本语法

​	man [命令或配置文件]		（功能描述：获得帮助信息）

##### 2）显示说明

​	表1-6

| 信息        | 功能                     |
| ----------- | ------------------------ |
| NAME        | 命令的名称和单行描述     |
| SYNOPSIS    | 怎样使用命令             |
| DESCRIPTION | 命令功能的深入讨论       |
| EXAMPLES    | 怎样使用命令的例子       |
| SEE ALSO    | 相关主题（通常是手册页） |

#### 5.1.2 help获得shell内置命令的帮助信息

##### 1）基本语法

​	help 命令	（功能描述：获得shell内置命令的帮助信息）

##### 2）案例实操

查看cd命令的帮助信息

 help cd

#### 5.1.3 常用快捷键

​		表1-7 常用快捷键

| 常用快捷键  | 功能                         |
| ----------- | ---------------------------- |
| ctrl + c    | 停止进程                     |
| ctrl+l      | 清屏；彻底清屏是：reset      |
| ctrl + q    | 退出                         |
| 善于用tab键 | 提示(更重要的是可以防止敲错) |
| 上下键      | 查找执行过的命令             |
| ctrl +alt   | linux和Windows之间切换       |

### **5.2** 文件目录类

#### 5.2.1 pwd 显示当前工作目录的绝对路径

pwd:print working directory 打印工作目录

##### 1）基本语法

pwd		（功能描述：显示当前工作目录的绝对路径）

##### 2）案例实操

​	（1）显示当前工作目录的绝对路径

[root@hadoop101 ~]# pwd

/root

#### **5.2.2 ls** **列出目录的内容**

ls:list 列出目录内容

##### 1）基本语法

ls [选项] [目录或是文件]

##### 2）选项说明

​	表1-8 选项说明

| 选项 | 功能                                                      |
| ---- | --------------------------------------------------------- |
| -a   | 全部的文件，连同隐藏档( 开头为 . 的文件) 一起列出来(常用) |
| -l   | 长数据串列出，包含文件的属性与权限等等数据；(常用)        |

##### 3）显示说明

每行列出的信息依次是： 文件类型与权限  链接数  文件属主  文件属组  文件大小用byte来表示   建立或最近修改的时间   名字 

`ls -al`

drwx------. 5 rggdf dfg 4096 5月  27 15:15 .

drwxr-xr-x. 3 root    root    4096 5月  27 14:03 ..

drwxrwxrwx. 2 root    root    4096 5月  27 14:14 hello

-rwxrw-r--. 1 dsg gdfg   34 5月  27 14:20 test.txt

#### **5.2.3 cd** **切换目录**

cd:Change Directory切换路径

##### 1）基本语法

​	cd  [参数]

##### 2）参数说明

表1-9 参数说明

| 参数        | 功能                                 |
| ----------- | ------------------------------------ |
| cd 绝对路径 | **切换路径**                         |
| cd相对路径  | **切换路径**                         |
| cd ~或者cd  | 回到自己的家目录                     |
| cd -        | 回到上一次所在目录                   |
| cd ..       | 回到当前目录的上一级目录             |
| cd -P       | 跳转到实际物理路径，而非快捷方式路径 |

##### 3）案例实操

###### （1）使用绝对路径切换到root目录

 cd /root/

###### （2）使用相对路径切换到“公共的”目录

cd 公共的/

#### **5.2.4 mkdir** **创建一个新的目录**

mkdir:Make directory 建立目录

##### 1）基本语法

​	mkdir [选项] 要创建的目录

##### 2）选项说明

表1-10 选项说明

| 选项 | 功能         |
| ---- | ------------ |
| -p   | 创建多层目录 |

##### 3）案例实操

###### （1）创建一个目录

 mkdir xiyou

 mkdir xiyou/mingjie

###### （2）创建一个多级目录

 mkdir -p xiyou/dssz/meihouwang

#### **5.2.5 rmdir** **删除一个空的目录**

rmdir:Remove directory 移动目录

##### 1）基本语法：

​	rmdir 要删除的**空目录**

##### 2）案例实操

删除一个空的文件夹

rmdir xiyou/dssz/meihouwang

#### **5.2.6 touch** **创建空文件**

##### 1）基本语法

​	touch 文件名称

##### 2）案例实操

 touch xiyou/dssz/sunwukong.txt

#### 5.2.7 cp **复制文件或目录**

##### 1）基本语法

cp [选项] source dest 				（功能描述：复制source文件到dest）

##### 2）选项说明

表1-11 选项说明

| 选项 | 功能               |
| ---- | ------------------ |
| -r   | 递归复制整个文件夹 |

##### 3）参数说明

表1-12 参数说明

| 参数   | 功能     |
| ------ | -------- |
| source | 源文件   |
| dest   | 目标文件 |

##### 4）经验技巧

​	强制覆盖不提示的方法：\cp

##### 5）案例实操

###### （1）复制文件

cp xiyou/dssz/suwukong.txt xiyou/mingjie/

###### （2）递归复制整个文件夹

 cp -r xiyou/dssz/ ./

#### 5.2.8 **rm** 移除文件或目录

##### 1）基本语法

rm [选项] deleteFile			（功能描述：递归删除目录中所有内容）

##### 2）选项说明

表1-13 选项说明

| 选项 | 功能                                     |
| ---- | ---------------------------------------- |
| -r   | 递归删除目录中所有内容                   |
| -f   | 强制执行删除操作，而不提示用于进行确认。 |
| -v   | 显示指令的详细执行过程                   |

##### 3）案例实操

###### （1）删除目录中的内容

[root@hadoop101 ~]# rm xiyou/mingjie/sunwukong.txt

###### （2）递归删除目录中所有内容

[root@hadoop101 ~]# rm **-rf** dssz/

#### **5.2.9 mv** **移动文件与目录或重命名**

##### 1）基本语法

###### 	（1）mv oldNameFile newNameFile	（功能描述：重命名）

###### 	（2）mv /temp/movefile /targetFolder	（功能描述：移动文件）

##### 2）案例实操

###### （1）重命名

[root@hadoop101 ~]# mv xiyou/dssz/suwukong.txt xiyou/dssz/houge.txt

###### （2）移动文件

[root@hadoop101 ~]# mv xiyou/dssz/houge.txt ./

#### **5.2.10 cat****查看文件内容**

查看文件内容，从第一行开始显示。

##### 1）基本语法

​	cat  [选项] 要查看的文件

##### 2）选项说明

表1-14

| 选项 | 功能描述                     |
| ---- | ---------------------------- |
| -n   | 显示所有行的行号，包括空行。 |

##### 3）经验技巧

一般查看比较小的文件，**一屏幕能显示全的**。

##### 4）案例实操

​	查看文件内容并显示行号

 cat -n houge.txt 

#### **5.2.11 more** **文件内容分屏查看器**

more指令是一个基于VI编辑器的文本过滤器，它以全屏幕的方式按页显示文本文件的内容。more指令中内置了若干快捷键，详见操作说明。

##### 1）基本语法

​	more 要查看的文件

##### 2）操作说明

表1-15 操作说明

| 操作          | 功能说明                                |
| ------------- | --------------------------------------- |
| 空白键(space) | 代表向下翻一页；                        |
| Enter         | 代表向下翻『一行』；                    |
| q             | 代表立刻离开more ，不再显示该文件内容。 |
| Ctrl+F        | 向下滚动一屏                            |
| Ctrl+B        | 返回上一屏                              |
| =             | 输出当前行的行号                        |
| :f            | 输出文件名和当前行的行号                |

##### 3）案例实操

采用more查看文件

more smartd.conf

#### **5.2.12 less** **分屏显示文件内容**

​	less指令用来分屏查看文件内容，它的功能与more指令类似，但是比more指令更加强大，支持各种显示终端。less指令在显示文件内容时，并不是一次将整个文件加载之后才显示，而是根据显示需要加载内容，对于显示**大型文件具有较高的效率**。

##### 1）基本语法

​	less 要查看的文件

##### 2）操作说明

表1-16 操作说明

| 操作       | 功能说明                                           |
| ---------- | -------------------------------------------------- |
| 空白键     | 向下翻动一页；                                     |
| [pagedown] | 向下翻动一行                                       |
| [pageup]   | 向上翻动一行；                                     |
| /字串      | 向下搜寻『字串』的功能；n：向下查找；N：向上查找； |
| ?字串      | 向上搜寻『字串』的功能；n：向上查找；N：向下查找； |
| q          | 离开less 这个程序；                                |

##### 3）案例实操

​	采用less查看文件

 less smartd.conf

#### **5.2.13** **echo**

echo输出内容到控制台

##### 1）基本语法

​	echo [选项] [输出内容]

选项：

​	 -e：  支持反斜线控制的字符转换

| 控制字符 | 作用                |
| -------- | ------------------- |
| \\       | 输出\本身           |
| \n       | 换行符              |
| \t       | 制表符，也就是Tab键 |

##### 2）案例实操

echo "hello\tworld"

hello\tworld

 echo -e "hello\tworld"

hello		world

#### 5.2.14 **head** **显示文件头部内容**

head用于显示文件的开头部分内容，默认情况下head指令显示文件的前10行内容。

##### 1）基本语法

head 文件	      （功能描述：查看文件头10行内容）

head -n 5 文件      （功能描述：查看文件头5行内容，5可以是任意行数）

##### 2）选项说明

表1-18

| 选项      | 功能                   |
| --------- | ---------------------- |
| -n <行数> | 指定显示头部内容的行数 |

##### 3）案例实操

​	查看文件的头2行

[root@hadoop101 ~]# head -n 2 smartd.conf

#### 5.2.15 **tail** **输出文件尾部内容**

tail用于输出文件中尾部的内容，默认情况下tail指令显示文件的后10行内容。

1）基本语法

（1）tail  文件 			（功能描述：查看文件后10行内容）

（2）tail  -n 5 文件 		（功能描述：查看文件后5行内容，5可以是任意行数）

（3）tail  -f  文件		（功能描述：实时追踪该文档的所有更新）

2）选项说明

表1-19

| 选项     | 功能                                 |
| -------- | ------------------------------------ |
| -n<行数> | 输出文件尾部n行内容                  |
| -f       | 显示文件最新追加的内容，监视文件变化 |

3）案例实操

（1）查看文件头1行内容

[root@hadoop101 ~]# tail -n 1 smartd.conf 

（2）实时追踪该档的所有更新

[root@hadoop101 ~]# tail -f houge.txt

#### **5.2.16 >** **覆盖****和** **>>** **追加**

##### 1）基本语法

###### （1）ll >文件		（功能描述：列表的内容写入文件a.txt中（**覆盖写**））

###### （2）ll >>文件		（功能描述：列表的内容**追加**到文件aa.txt的末尾）

###### （3）cat 文件1 > 文件2	（功能描述：将文件1的内容覆盖到文件2）

###### （4）echo “内容” >> 文件

##### 2）案例实操

###### （1）将ls查看信息写入到文件中

[root@hadoop101 ~]# ls -l>houge.txt

###### （2）将ls查看信息追加到文件中

[root@hadoop101 ~]# ls -l>>houge.txt

###### （3）采用echo将hello单词追加到文件中

[root@hadoop101 ~]# echo hello>>houge.txt

#### **5.2.17 ln** **软链接**

软链接也称为符号链接，类似于windows里的快捷方式，有自己的数据块，主要存放了链接其他文件的路径。

##### 1）基本语法

ln -s [原文件或目录] [软链接名]		（功能描述：给原文件创建一个软链接）

##### 2）经验技巧

删除软链接： rm -rf 软链接名，而不是rm -rf 软链接名/

查询：通过ll就可以查看，列表属性第1位是l，尾部会有位置指向。

##### 3）案例实操

###### 	（1）创建软连接

[root@hadoop101 ~]# mv houge.txt xiyou/dssz/

[root@hadoop101 ~]# ln -s xiyou/dssz/houge.txt ./houzi

[root@hadoop101 ~]# ll

lrwxrwxrwx. 1 root    root      20 6月  17 12:56 houzi -> xiyou/dssz/houge.txt

###### （2）删除软连接

[root@hadoop101 ~]# rm -rf houzi

###### （3）进入软连接实际物理路径

[root@hadoop101 ~]# ln -s xiyou/dssz/ ./dssz

[root@hadoop101 ~]# cd -P dssz/

#### **5.2.18 history** **查看已经执行过历史命令**

​	history						（功能描述：查看已经执行过历史命令）

### **5.3 时间日期类**

### **5.4** **用户管理命令**

#### **5.4.1 useradd** **添加新用户**

##### 1）基本语法

​	useradd 用户名			（功能描述：添加新用户）

​	useradd -g 组名 用户名	（功能描述：添加新用户到某个组）

##### 2）案例实操

[root@hadoop101 ~]# useradd tangseng

[root@hadoop101 ~]# ll /home/

#### **5.4.2 passwd** **设置用户密码**

##### 1）基本语法

​	passwd 用户名	（功能描述：设置用户密码）

##### 2）案例实操

设置用户的密码

[root@hadoop101 ~]# passwd tangseng

#### **5.4.3 id** **查看****用户是否存在**

##### 1）基本语法

​	id 用户名

##### 2）案例实操

查看用户是否存在

[root@hadoop101 ~]#id tangseng

#### **5.4.4 **cat  /etc/passwd** **查看创建了哪些用户**

基本语法

[root@hadoop101 ~]# cat  /etc/passwd

#### **5.4.**5 **su** **切换用户**

su: swith user 切换用户

##### 1）基本语法

su 用户名称   （功能描述：切换用户，只能获得用户的执行权限，不能获得当前用户环境变量，而是获取原用户的环境变量）

su - 用户名称		（功能描述：切换到用户并获得该用户的环境变量及执行权限）

##### 2）案例实操

​	（1）切换用户

[root@hadoop101 ~]#su tangseng

[root@hadoop101 ~]#echo $PATH

/usr/lib64/qt-3.3/bin:/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin

[root@hadoop101 ~]#exit

[root@hadoop101 ~]#su - tangseng

[root@hadoop101 ~]#echo $PATH

/usr/lib64/qt-3.3/bin:/usr/local/bin:/bin:/usr/bin:/usr/local/sbin:/usr/sbin:/sbin:/home/tangseng/bin

#### **5.4.6 userdel** **删除用户**

##### 1）基本语法

​	（1）userdel  用户名		（功能描述：删除用户但保存用户主目录）

（2）userdel -r 用户名		（功能描述：用户和用户主目录，都删除）

##### 2）选项说明

表1-23

| 选项 | 功能                                       |
| ---- | ------------------------------------------ |
| -r   | 删除用户的同时，删除与用户相关的所有文件。 |

##### 3）案例实操

###### （1）删除用户但保存用户主目录

[root@hadoop101 ~]#userdel tangseng

[root@hadoop101 ~]#ll /home/

###### （2）删除用户和用户主目录，都删除

[root@hadoop101 ~]#useradd zhubajie

[root@hadoop101 ~]#ll /home/

[root@hadoop101 ~]#userdel -r zhubajie

[root@hadoop101 ~]#ll /home/

#### **5.4.7 who** **查看登录用户信息**

##### 1）基本语法

###### （1）whoami			（功能描述：显示自身用户名称）

###### （2）who am i		（功能描述：显示**登录用户**的用户名）

##### 2）案例实操

###### （1）显示自身用户名称

[root@hadoop101 opt]# whoami

###### （2）显示登录用户的用户名

[root@hadoop101 opt]# who am i

#### **5.4.8** **sudo** **设置普通用户具有****root****权限**

##### 1）添加atguigu用户，并对其设置密码。

[root@hadoop101 ~]#useradd atguigu

[root@hadoop101 ~]#passwd atguigu

##### 2）修改配置文件

[root@hadoop101 ~]#vi /etc/sudoers

修改 /etc/sudoers 文件，找到下面一行(91行)，在root下面添加一行，如下所示：

\## Allow root to run any commands anywhere

root    ALL=(ALL)     ALL

atguigu   ALL=(ALL)     ALL

或者配置成采用sudo命令时，不需要输入密码

\## Allow root to run any commands anywhere

root      ALL=(ALL)     ALL

atguigu   ALL=(ALL)     NOPASSWD:ALL

修改完毕，现在可以用atguigu帐号登录，然后用命令 sudo ，即可获得root权限进行操作。

##### 3）案例实操

​	用普通用户在/opt目录下创建一个文件夹

[atguigu@hadoop101 opt]$ sudo mkdir module

[root@hadoop101 opt]# chown atguigu:atguigu module/

#### **5.4.9 usermod** **修改用户**

##### 1）基本语法

usermod -g 用户组 用户名

##### 2）选项说明

表1-24

| 选项 | 功能                                   |
| ---- | -------------------------------------- |
| -g   | 修改用户的初始登录组，给定的组必须存在 |

##### 3）案例实操

将用户加入到用户组

[root@hadoop101 opt]#usermod -g root zhubajie

### **5.5** **用户组管理命令**

每个用户都有一个用户组，系统可以对一个用户组中的所有用户进行集中管理。不同Linux 系统对用户组的规定有所不同，

如Linux下的用户属于与它同名的用户组，这个用户组在创建用户时同时创建。

用户组的管理涉及用户组的添加、删除和修改。组的增加、删除和修改实际上就是对/etc/group文件的更新。

#### **5.5.1 groupadd** **新增组**

##### 1）基本语法

groupadd 组名

##### 2）案例实操

​	添加一个xitianqujing组

[root@hadoop101 opt]#groupadd xitianqujing

#### **5.5.2 groupdel** **删除组**

##### 1）基本语法

groupdel 组名

##### 2）案例实操

​	删除xitianqujing组

[root@hadoop101 opt]# groupdel xitianqujing

#### **5.5.3 groupmod** **修改组**

##### 1）基本语法

groupmod -n 新组名 老组名

##### 2）选项说明

表1-25

| 选项       | 功能描述           |
| ---------- | ------------------ |
| -n<新组名> | 指定工作组的新组名 |

##### 3）案例实操

​	修改atguigu组名称为atguigu1

[root@hadoop101 ~]#groupadd xitianqujing

[root@hadoop101 ~]#groupmod -n xitian xitianqujing

#### **5.5.4 cat  /etc/group** **查看创建了哪些组**

基本操作

[root@hadoop101 atguigu]# cat  /etc/group

### **5.6** **文件权限类**

#### **5.6.1** **文件属性**

Linux系统是一种典型的多用户系统，不同的用户处于不同的地位，拥有不同的权限。为了保护系统的安全性，Linux系统对不同的用户访问同一文件（包括目录文件）的权限做了不同的规定。在Linux中我们可以使用ll或者ls -l命令来显示一个文件的属性以及文件所属的用户和组。

##### 1**）从左到右的****10****个字符表示**

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685988695026-db94cd84-07eb-4680-bb97-58ff17f71f23.png)

图文件属性

如果没有权限，就会出现减号[ - ]而已。从左至右用0-9这些数字来表示:

###### （1）0首位表示类型

在Linux中第一个字符代表这个文件是目录、文件或链接文件等等

\- 代表文件

d 代表目录

l 链接文档(link file)；

###### （2）第1-3位确定属主（该文件的所有者）拥有该文件的权限。---User

###### （3）第4-6位确定属组（所有者的同组用户）拥有该文件的权限，---Group

###### （4）第7-9位确定其他用户拥有该文件的权限 ---Other

##### **2****）****rxw****作用文件和目录的不同解释**

###### （1）作用到文件：

[ r ]代表可读(read): 可以读取，查看

[ w ]代表可写(write): 可以修改，但是不代表可以删除该文件，删除一个文件的前提条件是对该文件所在的目录有写权限，才能删除该文件.

[ x ]代表可执行(execute):可以被系统执行

###### （2）作用到目录：

[ r ]代表可读(read): 可以读取，ls查看目录内容

[ w ]代表可写(write): 可以修改，目录内创建+删除+重命名目录

[ x ]代表可执行(execute):可以进入该目录

##### **3****）****案例实操**

[root@hadoop101 ~]# ll

总用量104

-rw-------. 1 root root  1248 1月   8 17:36 anaconda-ks.cfg

drwxr-xr-x. 2 root root  4096 1月  12 14:02 dssz

lrwxrwxrwx. 1 root root    20 1月  12 14:32 houzi -> xiyou/dssz/houge.tx

文件基本属性介绍

###### （1）如果查看到是文件：链接数指的是硬链接个数。创建硬链接方法

ln [原文件] [目标文件]	 

[root@hadoop101 ~]# ln xiyou/dssz/houge.txt ./hg.txt

###### （2）如果查看的是文件夹：链接数指的是子文件夹个数。

[root@hadoop101 ~]# ls -al xiyou/

总用量16

drwxr-xr-x.  4 root root 4096 1月  12 14:00 .

dr-xr-x---. 29 root root 4096 1月  12 14:32 ..

drwxr-xr-x.  2 root root 4096 1月  12 14:30 dssz

drwxr-xr-x.  2 root root 4096 1月  12 14:04 mingjie

#### **5.6.2 chmod** **改变权限**

##### 1）基本语法

![img](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685988807333-68667df1-6f06-4eac-8a4f-41b00f3c8a54.png)

第一种方式变更权限

​		chmod  [{ugoa}{+-=}{rwx}] 文件或目录

第二种方式变更权限

​	chmod  [mode=421 ]  [文件或目录]

##### 2）经验技巧

​	u:所有者  g:所有组  o:其他人  a:所有人(u、g、o的总和)

r=4 w=2 x=1        rwx=4+2+1=7

##### 3）案例实操

###### 	（1）修改文件使其所属主用户具有执行权限

[root@hadoop101 ~]# cp xiyou/dssz/houge.txt ./

[root@hadoop101 ~]# chmod u+x houge.txt

###### （2）修改文件使其所属组用户具有执行权限

[root@hadoop101 ~]# chmod g+x houge.txt

###### （3）修改文件所属主用户执行权限,并使其他用户具有执行权限

[root@hadoop101 ~]# chmod u-x,o+x houge.txt

###### （4）采用数字的方式，设置文件所有者、所属组、其他用户都具有可读可写可执行权限。

[root@hadoop101 ~]# chmod 777 houge.txt

###### （5）修改整个文件夹里面的所有文件的所有者、所属组、其他用户都具有可读可写可执行权限。

[root@hadoop101 ~]# chmod -R 777 xiyou/

#### **5.6.3 chown** **改变所有者**

##### 1）基本语法

chown [选项] [最终用户] [文件或目录]		（功能描述：改变文件或者目录的所有者）

##### 2）选项说明

表1-26

| 选项 | 功能     |
| ---- | -------- |
| -R   | 递归操作 |

##### 3）案例实操

###### 	（1）修改文件所有者

[root@hadoop101 ~]# chown atguigu houge.txt 

[root@hadoop101 ~]# ls -al

-rwxrwxrwx. 1 atguigu root 551 5月  23 13:02 houge.txt

###### （2）递归改变文件所有者和所有组

[root@hadoop101 xiyou]# ll

drwxrwxrwx. 2 root root 4096 9月   3 21:20 xiyou

[root@hadoop101 xiyou]# chown -R atguigu:atguigu xiyou/

[root@hadoop101 xiyou]# ll

drwxrwxrwx. 2 atguigu atguigu 4096 9月   3 21:20 xiyou

#### **5.6.4 chgrp** **改变所属组**

##### 1）基本语法

​	chgrp [最终用户组] [文件或目录]	（功能描述：改变文件或者目录的所属组）

##### 2）案例实操

​	修改文件的所属组

[root@hadoop101 ~]# chgrp root houge.txt

[root@hadoop101 ~]# ls -al

-rwxrwxrwx. 1 atguigu root 551 5月  23 13:02 houge.txt

### **5.7** **搜索查找类**

#### **5.7.1 find** **查找文件或者目录**

find指令将从指定目录向下递归地遍历其各个子目录，将满足条件的文件显示在终端。

##### 1）基本语法

​	find [搜索范围] [选项]

##### 2）选项说明

表1-27

| 选项            | 功能                             |
| --------------- | -------------------------------- |
| -name<查询方式> | 按照指定的文件名查找模式查找文件 |
| -user<用户名>   | 查找属于指定用户名所有文件       |
| -size<文件大小> | 按照指定的文件大小查找文件。     |

##### 3）案例实操

###### （1）按文件名：根据名称查找/目录下的filename.txt文件。

[root@hadoop101 ~]# find xiyou/ -name “*.txt”

###### （2）按拥有者：查找/opt目录下，用户名称为-user的文件

[root@hadoop101 ~]# find opt/ -user atguigu

###### （3）按文件大小：在/home目录下查找大于200m的文件（+n 大于  -n小于   n等于）

[root@hadoop101 ~]find /home -size +204800

#### **5.7.2 grep** **过滤查找及****“|”****管道符**

管道符，“|”，表示将前一个命令的处理结果输出传递给后面的命令处理

##### 1）基本语法

grep 选项 查找内容 源文件

##### 2）选项说明

表1-28

| 选项 | 功能               |
| ---- | ------------------ |
| -n   | 显示匹配行及行号。 |

##### 3）案例实操

​	查找某文件在第几行

[root@hadoop101 ~]# ls | grep -n test

#### **5.7.3** **which** **查找命令**

​	查找命令在那个目录下

##### 1）基本语法

which 命令

##### 2）案例实操

[root@hadoop101 ~]# which ll

### **5.8** **压缩和解压类**

​	最小化安装（纯命令行模式）需要先执行以下命令

​		yum -y install tar

#### 1）基本语法

tar  [选项]  XXX.tar.gz  将要打包进去的内容		（功能描述：打包目录，压缩后的文件格式.tar.gz）

#### 2）选项说明

表1-31

| 选项   | 功能                         |
| ------ | ---------------------------- |
| -z     | 打包同时压缩                 |
| **-c** | **产生****.tar****打包文件** |
| -v     | 显示详细信息                 |
| -f     | 指定压缩后的文件名           |
| **-x** | **解包****.tar****文件**     |

#### 3）案例实操

##### （1）压缩多个文件

[root@hadoop101 opt]# tar -zcvf houma.tar.gz houge.txt bailongma.txt 

houge.txt

bailongma.txt

[root@hadoop101 opt]# ls

houma.tar.gz houge.txt bailongma.txt 

##### （2）压缩目录

[root@hadoop101 ~]# tar -zcvf xiyou.tar.gz xiyou/

xiyou/

xiyou/mingjie/

xiyou/dssz/

xiyou/dssz/houge.txt

##### （3）解压到当前目录

[root@hadoop101 ~]# tar **-zxvf** houma.tar.gz

##### （4）解压到指定目录

[root@hadoop101 ~]# tar -zxvf xiyou.tar.gz **-C** /opt

[root@hadoop101 ~]# ll /opt/

### **5.9** **磁盘分区类**

#### **5.9.1 df** **查看磁盘空间使用情况** 

df: disk free 空余硬盘

##### 1）基本语法

​	df  选项	（功能描述：列出文件系统的整体磁盘使用量，检查文件系统的磁盘空间占用情况）

##### 2）选项说明

表1-32

| 选项 | 功能                                                    |
| ---- | ------------------------------------------------------- |
| -h   | 以人们较易阅读的GBytes, MBytes, KBytes 等格式自行显示； |

##### 3）案例实操

​	查看磁盘使用情况

[root@hadoop101 ~]# df -h

Filesystem      Size  Used Avail Use% Mounted on

/dev/sda2        15G  3.5G   11G  26% /

tmpfs           939M  224K  939M   1% /dev/shm

/dev/sda1       190M   39M  142M  22% /boot

#### **5.9.2 fdisk** **查看分区** 

##### 1）基本语法

​	fdisk -l			（功能描述：查看磁盘分区详情）

##### 2）选项说明

表1-33

| 选项 | 功能                   |
| ---- | ---------------------- |
| -l   | 显示所有硬盘的分区列表 |

##### 3）经验技巧

该命令必须在root用户下才能使用

##### 4）功能说明

​	（1）Linux分区

Device：分区序列

Boot：引导

Start：从X磁柱开始

End：到Y磁柱结束

Blocks：容量

Id：分区类型ID

System：分区类型

##### 5）案例实操

​	（1）查看系统分区情况

[root@hadoop101 /]# fdisk -l

Disk /dev/sda: 21.5 GB, 21474836480 bytes

255 heads, 63 sectors/track, 2610 cylinders

Units = cylinders of 16065 * 512 = 8225280 bytes

Sector size (logical/physical): 512 bytes / 512 bytes

I/O size (minimum/optimal): 512 bytes / 512 bytes

Disk identifier: 0x0005e654



Device Boot      Start         End      Blocks   Id  System

/dev/sda1   *           1          26      204800   83  Linux

Partition 1 does not end on cylinder boundary.

/dev/sda2              26        1332    10485760   83  Linux

/dev/sda3            1332        1593     2097152   82  Linux swap / Solaris

#### **5.9.3** **mount/umount** **挂载****/****卸载**

对于Linux用户来讲，不论有几个分区，分别分给哪一个目录使用，它总归就是一个根目录、一个独立且唯一的文件结构。

Linux中每个分区都是用来组成整个文件系统的一部分，它在用一种叫做“挂载”的处理方法，它整个文件系统中包含了一整套的文件和目录，并将一个分区和一个目录联系起来，要载入的那个分区将使它的存储空间在这个目录下获得。

##### **1****）****挂载前准备（必须要有光盘或者已经连接镜像文件）**

图 挂载镜像文件

##### 2）基本语法

mount [-t vfstype] [-o options] device dir	（功能描述：挂载设备）

umount 设备文件名或挂载点			（功能描述：卸载设备）

##### 3）参数说明

表1-34

| 参数       | 功能                                                         |
| ---------- | ------------------------------------------------------------ |
| -t vfstype | 指定文件系统的类型，通常不必指定。mount 会自动选择正确的类型。常用类型有：光盘或光盘镜像：iso9660DOS fat16文件系统：msdos[Windows](http://blog.csdn.net/hancunai0017/article/details/6995284) 9x fat32文件系统：vfatWindows NT ntfs文件系统：ntfsMount Windows文件[网络](http://blog.csdn.net/hancunai0017/article/details/6995284)共享：smbfs[UNIX](http://blog.csdn.net/hancunai0017/article/details/6995284)(LINUX) 文件网络共享：nfs |
| -o options | 主要用来描述设备或档案的挂接方式。常用的参数有：loop：用来把一个文件当成硬盘分区挂接上系统ro：采用只读方式挂接设备rw：采用读写方式挂接设备iocharset：指定访问文件系统所用字符集 |
| device     | 要挂接(mount)的设备                                          |
| dir        | 设备在系统上的挂接点(mount point)                            |

##### 4）案例实操

###### （1）挂载光盘镜像文件

[root@hadoop101 ~]# mkdir /mnt/cdrom/						建立挂载点

[root@hadoop101 ~]# mount -t iso9660 /dev/cdrom /mnt/cdrom/	设备/dev/cdrom挂载到 挂载点 ：  /mnt/cdrom中

[root@hadoop101 ~]# ll /mnt/cdrom/

###### （2）卸载光盘镜像文件

[root@hadoop101 ~]# umount /mnt/cdrom

##### 5）设置开机自动挂载

vi /etc/fstab

添加红框中内容，保存退出。

### **5.10** **进程线程类**

进程是正在执行的一个程序或命令，每一个进程都是一个运行的实体，都有自己的地址空间，并占用一定的系统资源。

#### **5.10.1 ps** **查看****当前系统进程状态**

ps:process status 进程状态

##### 1）基本语法

​	**ps** **-****aux** | grep xxx		（功能描述：查看系统中所有进程）

​	**ps -ef** **| grep xxx**		（功能描述：可以查看子父进程之间的关系）

##### 2）选项说明

表1-35

| 选项 | 功能                   |
| ---- | ---------------------- |
| -a   | 选择所有进程           |
| -u   | 显示所有用户的所有进程 |
| -x   | 显示没有终端的进程     |

##### 3）功能说明

###### 	（1）ps -aux显示信息说明

​	USER：该进程是由哪个用户产生的

​	PID：进程的ID号

%CPU：该进程占用CPU资源的百分比，占用越高，进程越耗费资源；

%MEM：该进程占用物理内存的百分比，占用越高，进程越耗费资源；

VSZ：该进程占用虚拟内存的大小，单位KB；

RSS：该进程占用实际物理内存的大小，单位KB；

TTY：该进程是在哪个终端中运行的。其中tty1-tty7代表本地控制台终端，tty1-tty6是本地的字符界面终端，tty7是图形终端。pts/0-255代表虚拟终端。

STAT：进程状态。常见的状态有：R：运行、S：睡眠、T：停止状态、s：包含子进程、+：位于后台

START：该进程的启动时间

TIME：该进程占用CPU的运算时间，注意不是系统时间

COMMAND：产生此进程的命令名

###### （2）ps -ef显示信息说明

UID：用户ID 

PID：进程ID 

PPID：父进程ID 

C：CPU用于计算执行优先级的因子。数值越大，表明进程是CPU密集型运算，执行优先级会降低；数值越小，表明进程是I/O密集型运算，执行优先级会提高 

STIME：进程启动的时间 

TTY：完整的终端名称 

TIME：CPU时间 

CMD：启动进程所用的命令和参数

##### 4）经验技巧

​	如果想查看进程的**CPU****占用率和内存占用率**，可以使用aux;

如果想查看**进程的父进程****ID**可以使用ef;

##### 5）案例实操

[root@hadoop101 datas]# ps aux



图  查看进程的CPU占用率和内存占用率

[root@hadoop101 datas]# ps -ef



图 查看进程的父进程ID

#### **5.10.2 kill** **终止进程**

##### 1）基本语法

​	kill  [选项] 进程号		（功能描述：通过进程号杀死进程）

​	killall 进程名称			（功能描述：通过进程名称杀死进程，也支持通配符，这在系统因负载过大而变得很慢时很有用）	

##### 2）选项说明

表1-36

| 选项 | 功能                 |
| ---- | -------------------- |
| -9   | 表示强迫进程立即停止 |

##### 3）案例实操

###### 	（1）杀死浏览器进程

[root@hadoop101 桌面]# kill -9 5102

###### 	（2）通过进程名称杀死进程

[root@hadoop101 桌面]# killall firefox

### **5.11 crond** **系统定时任务**

#### **5.11.1 crond** **服务管理**

##### 1）重新启动crond服务

[root@hadoop101 ~]# systemctl restart crond

#### **5.11.2 crontab** **定时任务设置**

##### 1）基本语法

crontab [选项]

##### 2）选项说明

表1-46

| 选项 | 功能                          |
| ---- | ----------------------------- |
| -e   | 编辑crontab定时任务           |
| -l   | 查询crontab任务               |
| -r   | 删除当前用户所有的crontab任务 |

##### 3）参数说明

[root@hadoop101 ~]# crontab -e 

###### （1）进入crontab编辑界面。会打开vim编辑你的工作。

\* * * * * 执行的任务

表1-47

| 项目      | 含义                 | 范围                    |
| --------- | -------------------- | ----------------------- |
| 第一个“*” | 一小时当中的第几分钟 | 0-59                    |
| 第二个“*” | 一天当中的第几小时   | 0-23                    |
| 第三个“*” | 一个月当中的第几天   | 1-31                    |
| 第四个“*” | 一年当中的第几月     | 1-12                    |
| 第 五个*” | 一周当中的星期几     | 0-7（0和7都代表星期日） |

###### （2）特殊符号

表1-48

| 特殊符号 | 含义                                                         |
| -------- | ------------------------------------------------------------ |
| *        | 代表任何时间。比如第一个“*”就代表一小时中每分钟都执行一次的意思。 |
| ，       | 代表不连续的时间。比如“0 8,12,16 * * * 命令”，就代表在每天的8点0分，12点0分，16点0分都执行一次命令 |
| -        | 代表连续的时间范围。比如“0 5  *  *  1-6命令”，代表在周一到周六的凌晨5点0分执行命令 |
| */n      | 代表每隔多久执行一次。比如“*/10  *  *  *  *  命令”，代表每隔10分钟就执行一遍命令 |

###### （3）特定时间执行命令

表1-49

| 时间              | 含义                                                         |
| ----------------- | ------------------------------------------------------------ |
| 45 22 * * * 命令  | 在22点45分执行命令                                           |
| 0 17 * * 1 命令   | 每周1 的17点0分执行命令                                      |
| 0 5 1,15 * * 命令 | 每月1号和15号的凌晨5点0分执行命令                            |
| 40 4 * * 1-5 命令 | 每周一到周五的凌晨4点40分执行命令                            |
| */10 4 * * * 命令 | 每天的凌晨4点，每隔10分钟执行一次命令                        |
| 0 0 1,15 * 1 命令 | 每月1号和15号，每周1的0点0分都会执行命令。注意：星期几和几号最好不要同时出现，因为他们定义的都是天。非常容易让管理员混乱。 |

##### 4）案例实操

​	（1）每隔1分钟，向/root/bailongma.txt文件中添加一个11的数字

*/1 * * * * /bin/echo ”11” >> /root/bailongma.txt

## 第6章 软件包管理

### 6.1 RPM

#### 6.1.1 RPM概述

RPM（RedHat Package Manager），RedHat软件包管理工具，类似windows里面的setup.exe

#### 6.1.2 RPM查询命令（rpm -qa）

##### 1）基本语法

rpm -qa				（功能描述：查询所安装的所有rpm软件包）

##### 2）经验技巧

由于软件包比较多，一般都会采取过滤。**rpm -qa | grep rpm**软件包

##### 3）案例实操

查询firefox软件安装情况

[root@hadoop101 Packages]# rpm -qa |grep firefox 

firefox-45.0.1-1.el6.centos.x86_64

#### 6.1.3 RPM卸载命令（rpm -e）

##### 1）基本语法

###### （1）rpm -e RPM软件包   

###### （2） **rpm -e --nodeps** **软件包**  

##### 2）选项说明

表1-50

| 选项     | 功能                                                         |
| -------- | ------------------------------------------------------------ |
| -e       | 卸载软件包                                                   |
| --nodeps | 卸载软件时，不检查依赖。这样的话，那些使用该软件包的软件在此之后可能就不能正常工作了。 |

##### 3）案例实操

​	卸载firefox软件

rpm -e firefox

#### 6.1.4 RPM安装命令（rpm -ivh）

##### 1）基本语法

​	**rpm -ivh RPM****包全名**

##### 2）选项说明

表1-51

| 选项     | 功能                     |
| -------- | ------------------------ |
| -i       | -i=install，安装         |
| -v       | -v=verbose，显示详细信息 |
| -h       | -h=hash，进度条          |
| --nodeps | --nodeps，不检测依赖进度 |

##### 3）案例实操

​	安装firefox软件

[root@hadoop101 Packages]# pwd

/media/CentOS_6.8_Final/Packages

[root@hadoop101 Packages]# rpm -ivh firefox-45.0.1-1.el6.centos.x86_64.rpm 

### 6.2 YUM仓库配置

#### 6.2.1 YUM概述

YUM（全称为 Yellow dog Updater, Modified）是一个在Fedora和RedHat以及CentOS中的Shell前端软件包管理器。基于RPM包管理，能够从指定的服务器自动下载RPM包并且安装，可以自动处理依赖性关系，并且一次安装所有依赖的软件包，无须繁琐地一次次下载、安装。 

图 YUM概述

#### 6.2.2 YUM的常用命令

##### 1）基本语法

​	yum [选项] [参数]

##### 2）选项说明

表1-52

| 选项 | 功能                  |
| ---- | --------------------- |
| -y   | 对所有提问都回答“yes” |

##### 3）参数说明

表1-53

| 参数         | 功能                          |
| ------------ | ----------------------------- |
| install      | 安装rpm软件包                 |
| update       | 更新rpm软件包                 |
| check-update | 检查是否有可用的更新rpm软件包 |
| remove       | 删除指定的rpm软件包           |
| list         | 显示软件包信息                |
| clean        | 清理yum过期的缓存             |
| deplist      | 显示yum软件包的所有依赖关系   |

#### 6.2.3 案例实操

采用yum方式安装firefox

yum -y install firefox.x86_64

## **第7章 软件安装**

### **7.1 安装JDK**

#### 7.1.1使用下面的命令查询系统中自带的jdk

rpm -qa | grep jdk

#### 7.1.2使用下面的命令卸载自带的jdk

rpm -e --nodeps jdk的rpm软件包的名字

#### 7.1.3将jdk的tar.gz的压缩包上传到/opt目录下

将jdk压缩包解压缩

#### 7.1.4在/etc/profile.d目录下创建my_env.sh文件

#### 7.1.5在my_env.sh文件中配置JAVA_HOME环境变量

#JAVA_HOME

JAVA_HOME=/opt/jdk1.8.0_152

PATH=$PATH:$JAVA_HOME/bin

export PATH JAVA_HOME

#### 7.1.6使环境变量立即生效，如果不生效就重启XShell

执行source /etc/profile.d/my_env.sh

### **7.2 安装Tomcat**

#### 7.2.1将tomcat的压缩包上传到/opt目录下

#### 7.2.2解压缩tomcat的压缩包

#### 7.2.3进入tomcat的bin目录执行./startup.sh启动tomcat服务器

#### 7.2.4也可以配置tomcat的环境变量，这样就可以在任意目录下执行startup.sh启动tomcat了

#### 7.2.5关闭防火墙或开放端口：

### **7.3 安装MySQL**

#### 7.3.1卸载自带的Mysql-libs（如果之前安装过mysql，要全都卸载掉）

##### a) rpm -qa | grep mariadb

##### b) rpm -e --nodeps mariadb-libs

#### 7.3.2在/opt目录下创建MySQL目录

#### 7.3.3上传MySQL的rpm安装包到/opt/MySQL目录下

#### 7.3.4按照标号依次安装rpm软件包

rpm -ivh mysql-community-common-5.7.28-1.el7.x86_64.rpm

rpm -ivh mysql-community-libs-5.7.28-1.el7.x86_64.rpm

rpm -ivh mysql-community-libs-compat-5.7.29-1.el7.x86_64.rpm

rpm -ivh mysql-community-client-5.7.28-1.el7.x86_64.rpm

rpm -ivh mysql-community-server-5.7.28-1.el7.x86_64.rpm

#### 7.3.5安装mysql-server时有可能出现以下异常:

##### a) 错误：依赖检测失败：

libaio.so.1()(64bit) 被 mysql-community-server-5.7.29-1.el7.x86_64 需要

##### b) 通过yum安装缺少的依赖：yum install -y libaio

#### 7.3.6初始化MySQL：mysqld --initialize --user=mysql

#### 7.3.7查看MySQL的临时密码：cat /var/log/mysqld.log

#### 7.3.8启动MySQL服务：systemctl start mysqld

#### 7.3.9连接MySQL：mysql -uroot -p’临时密码’

#### 7.3.10使用临时密码连接MySQL之后修改密码：

set password=password('你的密码');

#### 7.3.11编辑/etc/my.cnf配置文件设置MySQL客户端和服务端的字符集为utf8

 [mysqld]

**character-set-server=utf8**

12. #### 重启MySQL的服务

systemctl restart mysqld

13. #### 设置root远程权限

利用root账号本地登录，修改mysql数据库,user表的root用户的远程访问权限

update user set host='%' where user='root';

flush privileges;

### 7.4代理服务器搭建

代理服务器安装

1.搭建v2ray

bash <(wget -qO- -o- https://git.io/v2ray.sh)

2.测试端口连通性

打开：https://ping.sx/check-port

3.关闭防火墙

systemctl stop firewalld; systemctl disable firewalld;

## 第8章.防火墙篇

### 1，检查防火墙状态

sudo firewall-cmd --state

如果防火墙处于活动状态，您将看到“running”（正在运行）的输出；否则，您将看到“not running”（未运行）的输出。

### 2，如果防火墙未运行，则可以使用以下命令启动它：

sudo systemctl start firewalld

### 3.如果您想在系统启动时自动启动防火墙，请使用以下命令：

sudo systemctl enable firewalld

### 4.编辑自定义端口

vim /etc/ssh/sshd_config	

### 5.重新加载使生效

systemctl restart sshd.service

### 6.添加防火墙规则

#### 开启端口

sudo firewall-cmd --zone=public --add-port=10240/tcp --permanent

#### 关闭端口

sudo firewall-cmd --zone=public --remove-port=80/tcp --permanent

#### 使防火墙端口生效

sudo firewall-cmd --reload

### 7.查看开放端口

firewall-cmd --list-ports --zone=public