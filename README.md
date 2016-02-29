## 简介

> web前端开发入群测试

### Requirement

  - jdk1.8
  - gradle(prefer > 2.9)
  - postgresql(must > 9.4) for `jsonb` column type support
  - memcached(prefer latest 2.4.11)
  
### Technicals

  - Spring Boot(including web-mvc,aop,jpa modules)
  - PostgreSQL(internal json support)
  - Memcached(Cache)
  - Guava Toolkit and Java8 features
  
### Install

```
$ gradle build
$ gradle bootRun
```

We suggest you to develop with IDEA IDE

### DB migration

```sql
/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 90501
 Source Host           : 127.0.0.1
 Source Database       : chosen
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90501
 File Encoding         : utf-8

 Date: 02/29/2016 20:11:54 PM
*/

-- ----------------------------
--  Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS "public"."question";
CREATE TABLE "public"."question" (
	"id" int4 NOT NULL DEFAULT nextval('question_id_seq'::regclass),
	"content" varchar NOT NULL COLLATE "default",
	"answer" int2 NOT NULL,
	"choices" jsonb NOT NULL
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."question" OWNER TO "megrez";

-- ----------------------------
--  Records of question
-- ----------------------------
BEGIN;
INSERT INTO "public"."question" VALUES ('1', '下列选项中,属于"10.174.20.176/28"该网段的有效IP地址是:', '1', '["10.174.20.174", "10.174.20.186", "10.174.20.191", "10.174.20.192"]');
INSERT INTO "public"."question" VALUES ('2', '下列事件哪个不是由鼠标触发的事件：', '1', '["click", "contextmenu", "mouseout", "keydown"]');
INSERT INTO "public"."question" VALUES ('3', '下面关于CSS布局的描述，不正确的是？', '1', '["块级元素实际占用的宽度与它的 width 属性有关；", "块级元素实际占用的宽度与它的 border 属性有关；", "块级元素实际占用的宽度与它的 padding 属性有关；", "块级元素实际占用的宽度与它的 background 属性有关。"]');
INSERT INTO "public"."question" VALUES ('4', 'What is the result of this expression? (or multiple ones)\n```js\nvar val = ''smtg'';\nconsole.log(‘Value is ‘ + (val === ‘smtg’) ? ‘Something’ : ‘Nothing’);\n```', '1', '["Value is Something", "Value is Nothing", "NaN", "other"]');
COMMIT;

-- ----------------------------
--  Primary key structure for table question
-- ----------------------------
ALTER TABLE "public"."question" ADD PRIMARY KEY ("id") NOT DEFERRABLE INITIALLY IMMEDIATE;

```

### TODO

  - data migration for production
  - ready for production(jndi lookup,war)
  - More Tests
