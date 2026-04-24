# Spring AI Project

一个基于 Spring Boot 3 和 Spring AI 的多模块示例工程，覆盖了：

- OpenAI 兼容接口调用
- 阿里云 DashScope 调用
- `ChatClient` 基础用法
- 流式输出与 SSE 示例
- 多模态图片理解
- 带会话列表与历史记录的聊天机器人页面

## 项目结构

```text
spring-ai-project
├─ spring-ai-demo
├─ spring-alibaba-demo
├─ spring-chat-robot
└─ pom.xml
```

### `spring-ai-demo`

演示 Spring AI 基础能力，主要使用 OpenAI 兼容接口方式接入模型。

主要内容：

- `OpenAiChatModel` 基础调用
- `ChatClient` 同步调用
- `ChatClient` 流式调用
- SSE 示例

核心接口：

- `GET /qwen/chat`
- `GET /qwen/chatWithPrompt`
- `GET /qwen/chatWithRole`
- `GET /qwen/stream`
- `GET /chat/call`
- `GET /chat/entity`
- `GET /chat/stream`
- `GET /sse/data`
- `GET /sse/retry`
- `GET /sse/event`
- `GET /sse/end`
- `GET /sse/stream`

### `spring-alibaba-demo`

演示 DashScope 直连方式，以及 `ChatClient`、`ChatModel`、多模态能力。

主要内容：

- `ChatModel` 基础调用
- `ChatClient` 基础调用
- 关键字约束回复
- 图片理解示例

核心接口：

- `GET /ali/chat`
- `GET /chat/call`
- `GET /chat/entity`
- `GET /chat/stream`
- `GET /chat/word`
- `GET /multi/image`

### `spring-chat-robot`

一个带前端页面的聊天机器人模块，风格参考 GitHub，支持：

- 新建会话
- 会话列表
- 历史记录查询
- 删除会话
- 流式回复
- 基于 `ChatMemory` 的上下文记忆

前端页面：

- `GET /`

核心接口：

- `GET /chat/stream`
- `GET /chat/getChatIds`
- `GET /chat/getChatHistory`
- `GET /chat/deleteChat`

## 技术栈

- Java 17
- Spring Boot 3.5.3
- Maven
- Spring AI
- Spring Web
- Reactor
- Lombok

## 环境要求

启动前请确认本地具备：

- JDK 17
- Maven 3.9+
- 可用的模型 API Key

## 配置说明

仓库中各模块默认提供的是模板配置文件：

- [spring-ai-demo/src/main/resources/application-template.yml](D:/code/spring-ai-project/spring-ai-demo/src/main/resources/application-template.yml)
- [spring-alibaba-demo/src/main/resources/application-template.yml](D:/code/spring-ai-project/spring-alibaba-demo/src/main/resources/application-template.yml)
- [spring-chat-robot/src/main/resources/application-template.yml](D:/code/spring-ai-project/spring-chat-robot/src/main/resources/application-template.yml)

使用时建议：

1. 复制 `application-template.yml`
2. 重命名为 `application.yml`
3. 填入你自己的 `api-key`、`base-url` 和模型名

### OpenAI 兼容接口示例

适用于 `spring-ai-demo`、`spring-chat-robot`：

```yml
spring:
  ai:
    openai:
      api-key: sk-xxxx
      base-url: https://dashscope.aliyuncs.com/compatible-mode/v1
      chat:
        options:
          model: qwen3-max
          temperature: 0.7
```

### DashScope 示例

适用于 `spring-alibaba-demo`：

```yml
spring:
  ai:
    dashscope:
      api-key: sk-xxxx
      chat:
        options:
          model: qwen-vl-max-latest
          multi-model: true
          temperature: 0.7
```

## 启动方式

### 启动整个工程

```bash
mvn clean install
```

### 启动指定模块

```bash
mvn -pl spring-ai-demo spring-boot:run
mvn -pl spring-alibaba-demo spring-boot:run
mvn -pl spring-chat-robot spring-boot:run
```

如果你使用 IDEA，也可以直接运行对应模块中的启动类：

- [SpringAiDemoApplication.java](D:/code/spring-ai-project/spring-ai-demo/src/main/java/com/example/springaidemo/SpringAiDemoApplication.java)
- [SpringAlibabaDemoApplication.java](D:/code/spring-ai-project/spring-alibaba-demo/src/main/java/com/example/springalibabademo/SpringAlibabaDemoApplication.java)
- [SpringChatRobotApplication.java](D:/code/spring-ai-project/spring-chat-robot/src/main/java/com/example/springchatrobot/SpringChatRobotApplication.java)

## 访问方式

默认启动后访问：

- `http://localhost:8080`

如果你同时启动多个模块，请为不同模块配置不同端口。

## `spring-chat-robot` 使用说明

启动 `spring-chat-robot` 后，浏览器访问：

- `http://localhost:8080/`

页面提供：

- 左侧历史会话列表
- 主聊天窗口
- 流式响应展示
- 会话删除

接口交互方式：

- 首次发送消息时自动创建 `chatId`
- 前端通过 `/chat/stream` 获取流式回复
- 通过 `/chat/getChatIds` 获取会话列表
- 通过 `/chat/getChatHistory` 获取历史记录
- 通过 `/chat/deleteChat` 删除会话

相关文件：

- [spring-chat-robot/src/main/resources/static/index.html](D:/code/spring-ai-project/spring-chat-robot/src/main/resources/static/index.html)
- [spring-chat-robot/src/main/java/com/example/springchatrobot/controller/ChatController.java](D:/code/spring-ai-project/spring-chat-robot/src/main/java/com/example/springchatrobot/controller/ChatController.java)
- [spring-chat-robot/src/main/java/com/example/springchatrobot/config/CommonConfiguration.java](D:/code/spring-ai-project/spring-chat-robot/src/main/java/com/example/springchatrobot/config/CommonConfiguration.java)

## 调试建议

这类 Spring AI 项目在调试时，优先关注两件事：

- 配置是否和依赖对应
  - 例如用了 `spring-ai-openai-spring-boot-starter`，就要检查 `spring.ai.openai.*`
- Spring AI 版本是否一致
  - 不要混用不同主线或 milestone/正式版组件，否则容易出现 `NoSuchMethodError`、`AbstractMethodError`

排查顺序建议：

1. 先看 `pom.xml`
2. 再看 `application.yml`
3. 再看 `CommonConfiguration`
4. 最后再看业务控制器

## 备注

- 当前仓库里存在一个临时文件 `inspect.java`，它不是业务代码的一部分。
- 模板中的 API Key 仅作占位示例，提交前应改为本地私有配置或环境变量方式管理。

