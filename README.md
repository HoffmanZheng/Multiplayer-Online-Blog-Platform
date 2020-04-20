# Multiplayer-Online-Blog-Platform
[![](https://img.shields.io/badge/Travis-CI-brightgreen)](https://travis-ci.com/NervousOrange/Multiplayer-Online-Blog-Platform) [![](https://img.shields.io/badge/微信-联系作者-brightgreen)](https://s2.ax1x.com/2020/03/06/3bV0L8.jpg)

这是基于 Spring Boot 的多人在线博客平台的后端实现

* 效果展示：[在线预览](https://jirengu-inc.github.io/vue-blog-preview/)

* 从零开始搭建所需材料：[后端接口文档](interface-doc.md) + [前端代码](https://github.com/jirengu-inc/vue-blog-preview)

### 项目结构

- controller层：处理接收到的 HTTP 请求，对获取的请求参数进行验证和清洗，并将参数传递给业务逻辑 service 层。
- service层：主要处理业务逻辑的方法实现，依赖于 dao 层对数据库的操作
- dao 层：提供访问数据库所需操作的方法，dao 模式的优势就在于它实现了两次隔离：隔离了数据访问代码和业务逻辑代码，隔离了不同数据库实现。
- entity：主要用于存放实体类
- configuration：用于存放 web 安全配置

### 自动化测试

* 代码质量

  在 Maven 的 verify 周期绑定了 Checkstyle 和 SpotBugs 插件，以保证代码质量。

* 单元测试

  提供对登录模块 UserService 及 AuthController 类的 JUnit 单元测试，使用 Mockito mock 相关依赖对象的行为，实现在不涉及依赖关系的情况下对单 元代码的测试，并配置 travisCI 实现对 Gtihub 项目的自动化测试。

* 集成测试

  对整个项目对外暴露的登录接口进行集成测试，使用 httpClient 模拟发送 http 请求，验证用户的正常登录操作及登录状态的维持情况。使用 `maven exec` 执行外部命令，实现在测试前自动启动数据库，Flyway 自动建表及插入初始数据，测试后自动销毁测试数据 库，为项目对外接口的功能提供保障。

* 自动化测试

  配置 TravisCI 对 Github 仓库进行管理，使每次 commit 都自动进行以上测试，保证项目可演进性。

## How to build

clone 项目至本地目录：

```shell
git clone https://github.com/NervousOrange/Multiplayer-Online-Blog-Platform.git
```

从 Docker 启动 MySQL 数据库：

* [Docker 下载地址](https://www.docker.com/)
* 如果需要持久化数据需要配置 -v 磁盘文件映射

```shell
docker run --name springboot-blog -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0
```

使用 IDEA 打开项目，刷新 Maven，再使用开源数据库迁移工具 Flyway 完成自动建表工作：

```shell
mvn flyway:migrate
```

项目测试：

* 由于测试日志过长会使进程崩坏，需要将日志重定向到本地文件
* 此过程会生成下一步运行所需的 jar 包

```shell
mvn verify > testLog.txt
```

运行项目：

* Run Application 类

* 访问 [localhost:8080/index.html](localhost:8080/index.html) 就可以开始玩耍啦！

