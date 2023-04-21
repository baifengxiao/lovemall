# ArchLinux安装

![yuque_mind](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/yuque_mind.jpeg)

## 关键词：ArchLinux安装

> 使用官方提供的安装脚本：archinstall，快速安装ArchLinux

## 1. 镜像制作

### 1.1 镜像下载

[Arch Linux - Downloads](https://archlinux.org/download/)

### 1.2 镜像制作工具下载

[Index of /downloads](http://rufus.ie/downloads/)

### 1.3 使用Rufus制作启动U盘

#### 1.3.1 选择镜像文件

#### 1.3.2 改分区类型为GPT

![image.png](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685162071621-71f1538b-7e8a-4f0b-8e4c-a9adffa044d8.png)
图1:改分区类型为GPT

#### 1.3.3 dd模式写入镜像

![image.png](https://yuling-1318764606.cos.ap-chengdu.myqcloud.com/blog/1685162144109-d1039252-7442-4346-a2f6-73420bece90f.png)
图2:dd模式写入镜像

## 2. BIOS设置

### 2.1 进入BIOS设置

### 2.2 禁用安全模式

### 2.3 修改启动顺序，将U盘启动顺序放在第一个

## 3. 使用archinstall安装

## 4. 基本设置

### 4.1 安装中文字体

```bash
pacman -S ttf-hannom noto-fonts noto-fonts-extra noto-fonts-emoji noto-fonts-cjk adobe-source-code-pro-fonts adobe-source-sans-fonts adobe-source-serif-fonts adobe-source-han-sans-cn-fonts adobe-source-han-sans-hk-fonts adobe-source-han-sans-tw-fonts adobe-source-han-serif-cn-fonts wqy-zenhei wqy-microhei
```

### 4.2 开启sshd服务，远程连接Linux

### 4.3 连接过变更密码导致报错解决方法：进入  C:\Users\用户名\.ssh，删除其中文件

### 4.4 将PermitRootLogin no 改为 PermitRootLogin yes

### 4.5 systemctl enable --now sshd    (设置sshd开机自启，可选)

## 5. 显卡驱动

### 5.1 安装显卡驱动

pacman -S xf86-video-intel
												（Intel核心显卡驱动，用Intel核显就装，否则不用装）
pacman -S mesa nvidia(-lts) nvidia-settings nvidia-dkms nvidia-utils nvidia-prime
												（nvidia显卡驱动，用nvidia显卡就装，否则不用装）
pacman -S xf86-video-amdgpu
												  (AMD显卡驱动，用amd显卡的就装)

### 5.2 举个例子

我的笔记本，AMD核显以及3060显卡，所以安装后两个。
nvidia-dkms 与 nvidia-lts 不兼容，如果装lts驱动的话无需安装dkms 。
pacman -S mesa nvidia-lts nvidia-settings  nvidia-utils nvidia-prime xf86-video-amdgpu

### 5.3 双显卡切换(可选)

#### 5.3.1 安装（英伟达）双显卡切换工具命令：

(需要先安装yay,参考本文第7节)

yay -S optimus-manager optimus-manager-qt

#### 5.3.2 添加自启动

sudo systemctl enable optimus-manager

## 6. 添加存储库

### 6.1 ArchLinuxCN

vim /etc/pacman.conf
[archlinuxcn]
Server = https://mirrors.ustc.edu.cn/archlinuxcn/$arch

​	这是中科大的源，你也可以选择清华、阿里等

更新GPG密钥
pacman -Sy archlinuxcn-keyring

### 6.2 添加pacman.ltd源

[Clansty]
SigLevel = Never
Server = https://repo.lwqwq.com/archlinux/$arch
Server = https://pacman.ltd/archlinux/$arch
Server = https://repo.clansty.com/archlinux/$arch

## 7. 重要软件

### 7.1 安装git

pacman -S git

### 7.2 安装 AUR工具

pacman -S --needed git base-devel
git clone https://aur.archlinux.org/yay-bin.git
cd yay-bin
makepkg -si

### 7.3 输入法

#### 7.3.1. 安装fcitx，fcitx-im，fcitx-configtool包

pacman -S fcitx fcitx-im fcitx-configtool

fcitx：小企鹅输入法，搜狗输入法的依赖
fcitx-configtool：小企鹅输入法设置的界面
fcitx-im：这个软件包组包含：fcitx-gtk2，fcitx-gtk3，fcitx-qt4，fcitx-qt5

#### 7.3.2.在/etc/environment文件中加入以下内容

    	GTK_IM_MODULE=fcitx
    	QT_IM_MODULE=fcitx
    	XMODIFIERS=@im=fcitx

### 7.4蓝牙

#### 7.4.1安装蓝牙服务

sudo pacman -S bluez bluez-utils pulseaudio-bluetooth

#### 7.4.2设置蓝牙自启动

    	sudo systemctl enable --now bluetooth

#### 7.4.3重启蓝牙

pulseaudio -k

## 8.常用软件

谷歌
yay -S google-chrome
网易云音乐
netease-cloud-music
百度网盘
yay -S baidunetdisk-bin
微信
yay -S deepin-wine-wechat
企业微信(xorg)
yay -S com.qq.weixin.work.deepin-x11
腾讯会议
yay -S wemeet-bin
steam
sudo pacman -S steam
Qv2ray3.0（桌面客户端）
安装v2ray服务
sudo pacman -S v2ray 	
可视化客户端
sudo pacman -S qv2ray
