几个开源的java状态机
https://github.com/hekailiang/squirrel
http://unimod.sourceforge.net/fsm-framework.pdf

 线程安全 https://github.com/NameOfTheDragon/Java-Finite-State-Machine

http://projects.spring.io/spring-statemachine/

https://github.com/doridori/Engine

git clone https://gerrit.onosproject.org/onos
http://www.liclipse.com/

当前位置: 主页 > Linux视频下载 >
Linux集群应用实战视频（含PPT下载）
官方网站：http://软件热度：618℃报告：报告问题
软件介绍
下面是视频内容介绍

课程内容简介
      网络的飞速发展，给网络带宽和服务器性能带来了巨大的挑战，基于互联网的应用系统越来越多的瓶颈出现在服务器端，这就对服务器提出了更高的要求，同时， 在互联网运营和管理上，也要求更加的智能和灵活，如何能够实时的了解每个服务器的运行状态，并在应用出现故障的第一时间内来处理问题，成为互联网运维中的 一个重中之重。
      本课程就重点介绍这方面的应用和案例，首先介绍开源的网络存储应用ISCSI， 接着介绍nagios监控系统，Nagios是系统管理人员和运维监控人员必须的工具之一，使用nagios可以监控本地或远程主机资源，例如磁盘空间、 系统负载等信息，也可以监控各种应用服务，例如httpd服务、ftp服务等，当主机或者服务出现故障时，nagios还可以通过邮件、手机短信等方式在 第一时间内通知系统维护人员。利用nagios可以以低廉的成本达到商业监控系统所完成的功能。
      最后，讲解了集群系统在企业级方面的应用，主要讲述了红帽集群套件RHCS开源HA heartbeat、负载均衡器LVS、Oracle集群数据库RAC、Mysql+DRBD集群等方面的应用案例，通过使用Linux搭建集群，可以用 较低的价格来实现高可伸缩、高可用的网络服务，弥补单一服务器无法达到的性能。

课程目录一览

第1讲 iscsi的概念、组成和工作原理 
课程目标：了解iscsi的组成和工作原理。
课程内容：
iscsi的概念。
iscsi的组成。
iscsi的工作原理。
第2-3讲 基于iscsi的ip san存储系统
课程目标：
能熟练搭建基于iscsi的ip san存储系统
安装iscsi target软件
配置一个简单的iscsi target
在windows上配置iscsi initiator
在linux上配置iscsi initiator
第4讲 基于iscsi 在安全方面相关设定
课程目标：
如何管理和使用iscsi存储网络
initiator主机以ip认证方式获取iscsi target资源
initiator主机以密码认证方式获取iscsi target资源
.
第5讲 nagios综述
课程目标：了解nagios的概念和组成以及特点
课程内容：
什么是nagios
nagios的组成和特点。
iscsi的工作原理。
第6-9讲 安装与配置nagios监控系统
课程目标：快速搭建一个基于nagios的网络监控系统
安装nagios软件
安装nagios插件
nagios默认配置文件结束
nagios各个配置文件之间的关系
详细介绍nagios每个配置文件
第10讲 nagios的日常维护和管理 
课程目标：
能够熟练运用和管理nagios监控系统
验证nagios配置文件的正确性
如何启动与停止nagios监控系统
第11-12讲 利用插件扩展nagios的监控功能
课程目标：
通过nagios的扩展插件补充nagios的监控功能
利用nrpe外部构件监控远程主机
利用飞信实现nagios短信报警功能
第13讲 heartbeat的概念组成以及工作原理
课程目标：
熟悉heartbeat的概念、组成和工作原理
课程内容：
heartbeat的概念
ha集群相关术语
heartbeat的组成和工作原理
第14-15讲 安装与配置heartbeat集群系统
课程目标：
熟练掌握heartbeat的安装、配置和管理
安装heartbeat前的准备工作
安装heartbeat
详细介绍heartbeat相关配置文件
维护heartbeat
第16讲 测试heartbeat的ha功能 
课程目标：
灵活掌握heartbeat在各种情况下的工作状态和切换流程
正常关闭和重启主节点的heartbeat
在主节点上拔去网线
在主节点上拔去电源线
切断主节点的所有网络连接
在主节点上非正常关闭heartbeat守护进程
第17讲 drbd的概念和运行原理
课程目标：
了解drbd的概念和实现原理以及应用范围
课程内容：
什么是drbd
drbd是如何工作的
drbd的应用领域
第18讲 安装与配置drbd 
课程目标：
熟练掌握drbd配置文件各个参数的含义，并能够迅速搭建一套drbd系统
安装drbd
配置drbd
启动并管理drbd系统
测试drbd的数据镜像功能
drbd在主备节点间的切换
第19讲 lvs的体系结构和特点
课程目标：
了解lvs集群的特点以及负载均衡调度算法
课程内容：
lvs简介
lvs集群的体系结构
lvs集群的负载均衡方式与调度算法
第20-21讲 lvs的安装与配置
课程目标：
熟练掌握lvs的安装方式以及基于dr/tun模式的配置方式
安装lvs管理软件ipvs
ipvsadm的用法
lvs在负载调度器上的配置
lvs在后端realserver节点上的配置
配置lvs冗余策略
第22讲 测试lvs负载集群功能
课程目标：
通过测试了解lvs负载均衡集群的工作机制和切换原理
启动lvs集群系统
测试lvs的负载均衡集群功能
测试lvs的故障透明转移功能
第23讲 drbd+mysql+heartbeat+lvs可行性分析
课程目标：
了解mysql与drbd+lvs+heartbeat整合的必要性
课程内容：
mysql应用需求现状与存在问题
drbd+mysql+heartbeat+lvs方案实现原理
第24讲 mysql主从复制的实现 
课程目标：
掌握mysql主从复制的配置方式
mysql主从复制的实现原理
实例演示mysql主从复制的配置过程
配置mysql主从复制需要注意的问题
第25讲 配置mysql+heartbeat+drbd实现mysql写操作高可用
课程目标：
掌握mysql+heartbeat+drbd的配置方式，并可灵活运用
配置mysql+drbd实现数据镜像
配置mysql+heartbeat实现mysql数据库的高可用
测试数据镜像功能
测试mysql高可用功能
第26讲 配置mysql+lvs+keeplived实现mysql读操作的负载均衡
课程目标：
熟练掌握mysql+lvs+keeplived的部署方式
keeplived简介
通过配置lvs+keeplived实现mysql读操作的负载均衡
测试mysql+lvs+keeplived负载均衡功能
第27讲 rhcs集群概述
课程目标：
了解rhcs集群的组成与结构以及运行原理
课程内容：
rhcs的组成
rhcs整体结构
rhcs实现原理以及各功能模块介绍
第28讲 安装rhcs 
课程目标：
熟练掌握rhcs的安装方式
配置共享存储和rhcs管理端luci
在集群节点安装rhcs软件包
在集群节点安装配置iscsi客户端
第29-30讲 配置rhcs
课程目标：
可以通过字符界面和web界面熟练配置一个集群系统
通过luci创建一个cluster
创建failover domain
创建resources
创建service
配置存储集群gfs
配置表决磁盘
配置fence设备
第31-32讲 管理和维护rhcs集群系统
课程目标：
熟练掌握rhcs集群的启动/关闭方式，并能解决常见rhcs问题
启动rhcs集群
关闭rhcs集群
管理应用服务
监控rhcs集群状态
管理和维护gfs2文件系统
第33讲 测试rhcs集群功能
课程目标：
通过测试rhcs的集群功能，验证rhcs是否已经正常工作
高可用集群测试
存储集群测试
第34讲 oracle集群方案体系结构
课程目标：
了解oracle集群的组成和实现机制
课程内容：
oracle clusterware体系结构与进程介绍
rac数据库体系机构与进程
oracle rac的特点
rac数据库存储规划
第35-37讲 安装oracle rac数据库
课程目标：
能够熟练完成oracle rac数据库安装前的准备工作，并成功安装oracle rac
安装rac数据库前的准备工作
安装oracle clusterware
安装oracle rac数据库软件
配置oracle rac监听
安装oracle rac数据库
第38讲 oracle crs的管理与维护
课程目标：
熟练掌握crs的的安装配置和日常使用
查看rac数据库运行状态
启动和关闭oracle rac集群数据库资源
启动与关闭crs
管理和维护表决磁盘
管理ocr
如何安全卸载crs
第39-40讲 asm基本操作与使用 
课程目标：
能够熟练掌握oracle asm存储管理方式
asm的特点
asm的体系结构与后台进程
管理asm实例
asmcmd命令的使用
第41-42讲 使用srvctl管理rac数据库
课程目标：
熟练掌握通过srvctl管理rac数据库的方法和技巧
通过srvctl查看rac数据库实例状态
通过srvctl查看rac数据库配置信息
通过srvctl启动/关闭rac数据库实例
通过srvctl添加/修改/删除rac数据库实例
第43讲 测试oracle rac数据库集群功能 
课程目标：
通过测试oracle rac数据库集群功能，验证rac数据库是否已经正常工作
rac数据库负载均衡功能测试
故障透明切换功能测试


链接：http://pan.baidu.com/s/1qXrJ4LY 密码：x4jv

http://www.cs.umd.edu/~ayewah/web/pubs/Google-ISSTA2010.pdf
