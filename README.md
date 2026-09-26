# 商城学习项目

Java 21 / Spring Boot 4.1.1 / MyBatis-Plus / MySQL / Vue 3 / TypeScript。
采用模块化单体，保留现有商品业务，逐步增加用户及鉴权功能。

## 本地配置

复制 `.env.example` 为 `.env`，填写本机数据库账号和密码。
`.env` 已加入 Git 忽略，Spring Boot 不会自动加载它。

```sh
set -a
source .env
set +a
cd mall-server
./mvnw spring-boot:run
```

全新数据库使用 `docs/sql/01_init.sql`，已有数据库不要直接重复执行初始化脚本。

## 验证

加载环境变量后，在 `mall-server` 执行 `./mvnw verify`；在 `mall-web` 执行 `npm ci`、`npm run build`。
前端目前仅有商品列表，尚无前端自动化测试脚本；构建不能替代浏览器交互验收。
