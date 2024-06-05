# 智能AI测试平台

> 作者：[墨染青衣](https://github.com/asdrfdc)

## 项目介绍 📢
本项目是基于Spring BootAIGC的智能AI测试平台

> AIGC ：Artificial Intelligence Generation Content(AI 生成内容)


## 项目技术栈和特点 ❤️‍🔥
### 后端
1. Spring Boot 2.7.2
2. MyBatis + MyBatis Plus 
5. Spring AOP 切面编程
6. 智谱AI接口调用
7. RxJava流式处理
8. SSE服务器推送技术
9. ShardingSphere分表
10. Caffeine本地缓存
11. Swagger + Knife4j 项目文档
12. Hutool工具库 、Lombok 注解

### 项目特性
- Spring Session Redis 分布式登录
- 基于智谱AI实现题目生成与题目测评
- 基于策略模式管理测评类型与评判方法
- 基于RxJava流式处理数据，结合SSE服务器推送技术增强用户体验
- 基于UUID和数据库唯一索引实现插入操作的幂等性
- 基于ShardingSphere实现分表
- 基于Caffeine实现本地缓存，减少不必要的AI调用和加快响应，提高用户体验
- 基于自定义线程池实现线程池隔离，提高VIP用户的用户体验
- 统计后台数据在前端生成可视化图表

### 项目功能 🎊
1. 可生成两种类型的测试：评分类与测评类
2. 可选择一个测试进行答题，得到测评结果










