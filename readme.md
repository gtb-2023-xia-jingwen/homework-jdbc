# 1 工程结构

本题目的难度并不大，但是这种工程结构确是第一次见到。有必要进行一下说明。

```
src
 |-main          # 这是功能代码模块
 |  |-java       # Java 代码目录
 |  |-resources  # 这个目录会添加到 CLASS_PATH 中。一般情况
 |    |          # 下会存放资源（其他在程序运行过程中需要访
 |    |          # 问的文件）和配置文件
 |    |-db/migration     # 存放数据库迁移脚本
 |    |  |-V001_....sql  # 数据库迁移脚本 SQL 文件
 |    |-simplelogger.properties # 日志记录器的配置文件
 |-test/java     # Java 测试代码目录  
```

其中 *resource* 目录会存放一些配置文件和其他运行时需要访问的资源。这些文件在运行时会添加到 `CLASS_PATH` 下。因此 Java 程序可以很容易的访问到这些文件。

在本题目中，最重要的资源文件就是在 *db/migration* 文件目录下的文件了。这些文件是一个一个的 SQL 脚本。我们书写了程序在每一次运行相关测试之前都会自动运行这些 SQL 脚本从而为我们的测试创建（内存）数据库。

# 2 我们的程序架构是怎样的

我们的应用程序并没有访问任何线上的数据库，因此即使是断开网络，本程序也能够执行。这是因为我们没有使用真正的 MySQL，而是使用 H2 内存数据库。H2 有 MySQL 兼容的语法，完全可以达到练习的目的。

```
+--------------------+     +--------+      +------------------------+
|  Java Application  +---->+ JDBC   +----->+ H2 In-Memory Database  |
+--------------------+     +--------+      +------------------------+
```

# 3 要求

阅读代码，掌握需求。通过所有的测试。

## 3.1 告诉我，我到底要干什么

在工程中找到 **TODO**，并将其全部实现。包括：

* 书写创建数据表的 SQL 脚本
* 按照要求实现方法

注意

* 只允许修改书写了 *TODO* 的文件。
* 不得更改测试中的任何内容。但是如果想添加测试那没有问题。

## 3.2 这些测试长的和以前不一样，是如何运行的

大家可以看到我们的测试类都标记了 `@InMemoryDbSupport` Annotation。凡标记该注解的类中的每一个测试，在运行之前都会自动创建内存数据库，在测试之后内存数据库都会自动销毁。因此确保了每一次运行测试你的数据库都是全新的。并且测试和测试之间不会互相影响。它的大致流程是：

1. JUnit 5 开始运行测试
1. 准备创建测试类型，例如 `TodoItemRepositoryTest`。
1. 发现这个类已经被 `@InMemoryDbSupport` 标记。
1. 从 `@InMemoryDbSupport` 标记中找到扩展点：`DatabaseMigrationExtension` 创建这个类的实例。
1. 准备运行该类型中的一个测试，例如 `should_get_todo_item`
1. 创建运行该测试的上下文：`ExtensionContext`
1. 执行 `DatabaseMigrationExtension.beforeTestExecution` 方法
    1. 创建内存数据库，并创建 `Connection`。
    1. 将内存数据库的 `Connection` 保存在 `ExtensionContext` 对象中。（这是因为内存数据库会在最后一个链接关闭时自动销毁，因此 **不要忘记关闭你自己在测试中创建的 `Connection` 对象**）
    1. 执行 *main/resources/db/migration* 目录下的所有的 SQL 文件。创建数据表（**这是大家需要完成的任务**）
1. 执行实际的测试（测试中如果需要创建一个数据库 Connection，请参见 `DatabaseMigrationExtension` 类）
1. 执行 `DatabaseMigrationExtension.afterTestExecution` 方法
    1. 得到之前保存在 `ExtensionContext` 对象中的最后一个 `Connection`
    1. 关闭该 `Connection` 对象。销毁数据库。
1. 测试执行完毕
1. 开始执行下一个测试，重复 *5 - 10* 的过程。

## 3.3 JOOQ 是个什么鬼

因为我需要让大家使用 JDBC 操作数据库。因此如果在测试中我也使用 JDBC 来进行断言那么几乎就是把参考答案都给出来了，因此我用了另外一个库访问数据库。这个库就是 `JOOQ`。大家完全不要在意这个库的存在。因为你们也不会去使用它。
