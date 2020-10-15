/*
 Navicat Premium Data Transfer

 Source Server         : localDataBase
 Source Server Type    : PostgreSQL
 Source Server Version : 90619
 Source Host           : localhost:5432
 Source Catalog        : finalproject
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90619
 File Encoding         : 65001

 Date: 15/10/2020 12:24:58
*/


-- ----------------------------
-- Sequence structure for categories_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."categories_id_seq";
CREATE SEQUENCE "public"."categories_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."categories_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for products_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."products_id_seq";
CREATE SEQUENCE "public"."products_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."products_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for receipts_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."receipts_id_seq";
CREATE SEQUENCE "public"."receipts_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."receipts_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."user_id_seq";
CREATE SEQUENCE "public"."user_id_seq"
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "public"."user_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS "public"."categories";
CREATE TABLE "public"."categories" (
  "id" int4 NOT NULL DEFAULT nextval('categories_id_seq'::regclass),
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."categories" OWNER TO "postgres";

-- ----------------------------
-- Records of categories
-- ----------------------------
BEGIN;
INSERT INTO "public"."categories" VALUES (1, 'crypto');
INSERT INTO "public"."categories" VALUES (3, 'bulochki');
INSERT INTO "public"."categories" VALUES (7, 'alcohol');
COMMIT;

-- ----------------------------
-- Table structure for databasechangelog
-- ----------------------------
DROP TABLE IF EXISTS "public"."databasechangelog";
CREATE TABLE "public"."databasechangelog" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "author" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "filename" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dateexecuted" timestamp(6) NOT NULL,
  "orderexecuted" int4 NOT NULL,
  "exectype" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "md5sum" varchar(35) COLLATE "pg_catalog"."default",
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "comments" varchar(255) COLLATE "pg_catalog"."default",
  "tag" varchar(255) COLLATE "pg_catalog"."default",
  "liquibase" varchar(20) COLLATE "pg_catalog"."default",
  "contexts" varchar(255) COLLATE "pg_catalog"."default",
  "labels" varchar(255) COLLATE "pg_catalog"."default",
  "deployment_id" varchar(10) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."databasechangelog" OWNER TO "postgres";

-- ----------------------------
-- Records of databasechangelog
-- ----------------------------
BEGIN;
INSERT INTO "public"."databasechangelog" VALUES ('1', 't.hryniuk', 'liquibase/v-1.0.64/2020-09-20-add-main-tables.xml', '2020-10-11 00:55:42.264479', 1, 'EXECUTED', '7:34e8cefb3fc0fae29c1e8881695a3d5a', 'createTable tableName=categories; createTable tableName=products; createTable tableName=terminals; createTable tableName=users; createTable tableName=receipts; sql', '', NULL, '3.5.1', NULL, NULL, '2366942233');
INSERT INTO "public"."databasechangelog" VALUES ('2', 't.hryniuk', 'liquibase/v-1.0.64/2020-09-30-add-sequence.xml', '2020-10-11 00:55:42.276062', 2, 'EXECUTED', '7:da390b9e2ad99d552192f3e65feb4ce9', 'sql', '', NULL, '3.5.1', NULL, NULL, '2366942233');
COMMIT;

-- ----------------------------
-- Table structure for databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS "public"."databasechangeloglock";
CREATE TABLE "public"."databasechangeloglock" (
  "id" int4 NOT NULL,
  "locked" bool NOT NULL,
  "lockgranted" timestamp(6),
  "lockedby" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."databasechangeloglock" OWNER TO "postgres";

-- ----------------------------
-- Records of databasechangeloglock
-- ----------------------------
BEGIN;
INSERT INTO "public"."databasechangeloglock" VALUES (1, 'f', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS "public"."products";
CREATE TABLE "public"."products" (
  "id" int4 NOT NULL DEFAULT nextval('products_id_seq'::regclass),
  "active" bool NOT NULL,
  "name" varchar COLLATE "pg_catalog"."default" NOT NULL,
  "price" int8 NOT NULL,
  "weight" int8 NOT NULL,
  "date_of_adding" timestamp(6) NOT NULL,
  "categories_id" int4 NOT NULL,
  "count" int4
)
;
ALTER TABLE "public"."products" OWNER TO "postgres";

-- ----------------------------
-- Records of products
-- ----------------------------
BEGIN;
INSERT INTO "public"."products" VALUES (1, 't', 'xrp', 25, 0, '2020-10-11 01:05:13.79', 1, 96);
INSERT INTO "public"."products" VALUES (3, 't', 'bulka', 1000, 15, '2020-10-11 18:52:13.329', 3, 1998);
INSERT INTO "public"."products" VALUES (5, 't', 'sdfdsf', 1, 1000, '2020-10-13 18:50:34.435', 3, 0);
INSERT INTO "public"."products" VALUES (2, 't', 'etc', 12345, 0, '2020-10-11 15:56:43.899', 1, 0);
INSERT INTO "public"."products" VALUES (4, 't', 'Vickie', 150000, 1000, '2020-10-12 15:18:31.303', 7, 12);
COMMIT;

-- ----------------------------
-- Table structure for receipts
-- ----------------------------
DROP TABLE IF EXISTS "public"."receipts";
CREATE TABLE "public"."receipts" (
  "id" int4 NOT NULL DEFAULT nextval('receipts_id_seq'::regclass),
  "id_product" int4 NOT NULL,
  "receipt_id" int4,
  "user_id" int4 NOT NULL,
  "cancel_user_id" int4,
  "count" int4 NOT NULL,
  "price" int8 NOT NULL,
  "status" int2 NOT NULL,
  "processing_time" timestamp(6) NOT NULL,
  "cancel_time" timestamp(6)
)
;
ALTER TABLE "public"."receipts" OWNER TO "postgres";

-- ----------------------------
-- Records of receipts
-- ----------------------------
BEGIN;
INSERT INTO "public"."receipts" VALUES (2, 1, 2, 1, 1, 12, 25, 212, '2020-10-11 01:05:39.207', '2020-10-11 13:00:40.937');
INSERT INTO "public"."receipts" VALUES (3, 1, 3, 1, 1, 12, 25, 212, '2020-10-11 14:23:07.707', '2020-10-11 15:52:27.657');
INSERT INTO "public"."receipts" VALUES (4, 1, 3, 1, 1, 125, 25, 212, '2020-10-11 14:23:07.707', '2020-10-11 15:52:27.657');
INSERT INTO "public"."receipts" VALUES (5, 1, 4, 1, 1, 12, 25, 212, '2020-10-11 15:56:53.231', '2020-10-11 15:57:26.185');
INSERT INTO "public"."receipts" VALUES (6, 2, 4, 1, 1, 15, 4500, 212, '2020-10-11 15:56:53.231', '2020-10-11 15:57:26.185');
INSERT INTO "public"."receipts" VALUES (7, 1, 5, 1, 1, 1, 25, 212, '2020-10-11 15:59:38.38', '2020-10-11 16:09:41.547');
INSERT INTO "public"."receipts" VALUES (8, 2, 5, 1, 1, 12, 4500, 212, '2020-10-11 15:59:38.38', '2020-10-11 16:09:41.547');
INSERT INTO "public"."receipts" VALUES (9, 1, 6, 1, 1, 12, 25, 212, '2020-10-11 16:20:18.7', '2020-10-11 16:21:51.343');
INSERT INTO "public"."receipts" VALUES (10, 2, 7, 1, 1, 16, 4500, 212, '2020-10-11 16:24:50.553', '2020-10-11 16:24:53.893');
INSERT INTO "public"."receipts" VALUES (11, 1, 8, 1, 1, 1, 25, 212, '2020-10-11 16:26:01.696', '2020-10-11 16:26:04.307');
INSERT INTO "public"."receipts" VALUES (12, 2, 9, 1, 1, 1, 4500, 212, '2020-10-11 16:26:49.859', '2020-10-11 16:26:54.462');
INSERT INTO "public"."receipts" VALUES (13, 1, 10, 1, 1, 12, 25, 212, '2020-10-11 16:27:11.513', '2020-10-11 16:27:14.357');
INSERT INTO "public"."receipts" VALUES (14, 2, 10, 1, 1, 123, 4500, 212, '2020-10-11 16:27:11.513', '2020-10-11 16:27:14.357');
INSERT INTO "public"."receipts" VALUES (15, 1, 11, 1, 1, 12, 25, 212, '2020-10-11 16:28:29.321', '2020-10-11 16:30:06.915');
INSERT INTO "public"."receipts" VALUES (16, 2, 11, 1, 1, 123, 4500, 212, '2020-10-11 16:28:29.321', '2020-10-11 16:30:06.915');
INSERT INTO "public"."receipts" VALUES (17, 1, 12, 1, 1, 1, 25, 212, '2020-10-11 16:32:37.242', '2020-10-11 16:32:39.572');
INSERT INTO "public"."receipts" VALUES (18, 2, 13, 1, 1, 123, 4500, 212, '2020-10-11 16:32:58.393', '2020-10-11 16:33:00.925');
INSERT INTO "public"."receipts" VALUES (19, 1, 13, 1, 1, 12, 25, 212, '2020-10-11 16:32:58.393', '2020-10-11 16:33:00.925');
INSERT INTO "public"."receipts" VALUES (20, 2, 14, 1, NULL, 12, 4500, 3, '2020-10-11 16:46:02.246', NULL);
INSERT INTO "public"."receipts" VALUES (21, 1, 14, 1, NULL, 123342, 25, 3, '2020-10-11 16:46:02.246', NULL);
INSERT INTO "public"."receipts" VALUES (22, 2, 15, 1, NULL, 123, 4500, 3, '2020-10-11 17:51:04.267', NULL);
INSERT INTO "public"."receipts" VALUES (23, 1, 16, 1, NULL, 34324, 25, 3, '2020-10-11 17:51:11.754', NULL);
INSERT INTO "public"."receipts" VALUES (24, 2, 17, 1, NULL, 5, 4500, 3, '2020-10-11 17:51:22.867', NULL);
INSERT INTO "public"."receipts" VALUES (25, 1, 18, 1, NULL, 5, 25, 3, '2020-10-11 17:51:31.152', NULL);
INSERT INTO "public"."receipts" VALUES (26, 2, 19, 1, NULL, 123, 4500, 3, '2020-10-11 17:51:38.454', NULL);
INSERT INTO "public"."receipts" VALUES (27, 1, 20, 1, NULL, 1233123, 25, 3, '2020-10-11 17:51:46.169', NULL);
INSERT INTO "public"."receipts" VALUES (28, 2, 21, 1, NULL, 123213, 4500, 3, '2020-10-11 17:51:52.761', NULL);
INSERT INTO "public"."receipts" VALUES (29, 1, 22, 1, NULL, 72, 25, 3, '2020-10-11 17:52:11.425', NULL);
INSERT INTO "public"."receipts" VALUES (32, 3, 26, 1, 1, 1, 1000, 212, '2020-10-11 18:52:41.624', '2020-10-11 18:52:56.541');
INSERT INTO "public"."receipts" VALUES (34, 3, 28, 1, 1, 1, 1000, 212, '2020-10-11 18:53:21.436', '2020-10-11 18:53:32.311');
INSERT INTO "public"."receipts" VALUES (35, 1, 29, 1, NULL, 2, 25, 3, '2020-10-12 01:12:50.615', NULL);
INSERT INTO "public"."receipts" VALUES (38, 1, 31, 1, NULL, 2, 25, 3, '2020-10-12 01:15:02.683', NULL);
INSERT INTO "public"."receipts" VALUES (39, 1, 31, 1, NULL, 3, 25, 3, '2020-10-12 01:15:02.683', NULL);
INSERT INTO "public"."receipts" VALUES (40, 1, 32, 1, NULL, 2, 25, 3, '2020-10-12 01:17:22.669', NULL);
INSERT INTO "public"."receipts" VALUES (41, 1, 32, 1, NULL, 3, 25, 3, '2020-10-12 01:17:22.669', NULL);
INSERT INTO "public"."receipts" VALUES (42, 1, 33, 1, NULL, 4, 25, 3, '2020-10-12 01:33:22.106', NULL);
INSERT INTO "public"."receipts" VALUES (44, 1, 34, 1, NULL, 1, 25, 3, '2020-10-12 13:42:01.218', NULL);
INSERT INTO "public"."receipts" VALUES (45, 1, 35, 1, NULL, 1, 25, 3, '2020-10-12 13:43:22.838', NULL);
INSERT INTO "public"."receipts" VALUES (46, 1, 36, 1, NULL, 1, 25, 3, '2020-10-12 13:44:18.78', NULL);
INSERT INTO "public"."receipts" VALUES (48, 1, 37, 1, NULL, 2, 25, 3, '2020-10-12 13:44:34.161', NULL);
INSERT INTO "public"."receipts" VALUES (49, 2, 38, 1, NULL, 1, 12345, 3, '2020-10-12 13:59:25.677', NULL);
INSERT INTO "public"."receipts" VALUES (50, 1, 39, 1, NULL, 1, 25, 3, '2020-10-12 14:06:15.854', NULL);
INSERT INTO "public"."receipts" VALUES (52, 2, 40, 1, 1, 2, 12345, 212, '2020-10-12 15:35:20.976', '2020-10-12 15:39:07.708');
INSERT INTO "public"."receipts" VALUES (53, 2, 41, 1, 1, 13, 12345, 212, '2020-10-13 19:07:23.339', '2020-10-13 19:08:50.744');
INSERT INTO "public"."receipts" VALUES (54, 1, 42, 1, NULL, 1, 25, 3, '2020-10-14 14:54:24.398', NULL);
INSERT INTO "public"."receipts" VALUES (55, 1, 43, 1, NULL, 1, 25, 3, '2020-10-14 17:17:55.764', NULL);
INSERT INTO "public"."receipts" VALUES (56, 2, 43, 1, NULL, 1, 12345, 3, '2020-10-14 17:17:55.764', NULL);
INSERT INTO "public"."receipts" VALUES (57, 4, 43, 1, NULL, 14, 150000, 3, '2020-10-14 17:17:55.764', NULL);
INSERT INTO "public"."receipts" VALUES (58, 1, 43, 1, NULL, 99999999, 25, 3, '2020-10-14 17:17:55.764', NULL);
INSERT INTO "public"."receipts" VALUES (60, 1, 44, 1, 1, 1, 25, 212, '2020-10-14 17:25:12.459', '2020-10-14 17:25:39.473');
INSERT INTO "public"."receipts" VALUES (61, 1, 44, 1, 1, 1, 25, 212, '2020-10-14 17:25:12.459', '2020-10-14 17:25:39.473');
INSERT INTO "public"."receipts" VALUES (62, 1, 45, 1, 1, 1, 25, 212, '2020-10-14 19:28:49.071', '2020-10-14 19:28:52.554');
INSERT INTO "public"."receipts" VALUES (63, 1, 46, 1, NULL, 1, 25, 3, '2020-10-14 19:48:27.988', NULL);
INSERT INTO "public"."receipts" VALUES (64, 1, 47, 2, NULL, 1, 25, 3, '2020-10-14 21:22:34.092', NULL);
INSERT INTO "public"."receipts" VALUES (65, 1, 48, 1, NULL, 1, 25, 3, '2020-10-15 12:17:08.213', NULL);
INSERT INTO "public"."receipts" VALUES (66, 1, 49, 2, NULL, 1, 25, 3, '2020-10-15 12:18:00.805', NULL);
COMMIT;

-- ----------------------------
-- Table structure for terminals
-- ----------------------------
DROP TABLE IF EXISTS "public"."terminals";
CREATE TABLE "public"."terminals" (
  "id" int4 NOT NULL,
  "active" bool NOT NULL,
  "address" varchar COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."terminals" OWNER TO "postgres";

-- ----------------------------
-- Records of terminals
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "public"."users";
CREATE TABLE "public"."users" (
  "id" int4 NOT NULL DEFAULT nextval('user_id_seq'::regclass),
  "active" bool NOT NULL,
  "login" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "auth_code" varchar(60) COLLATE "pg_catalog"."default" NOT NULL,
  "full_name" varchar(64) COLLATE "pg_catalog"."default",
  "role" int4 NOT NULL
)
;
ALTER TABLE "public"."users" OWNER TO "postgres";

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO "public"."users" VALUES (1, 't', 'login', '0e09eec281c19182b350a470282308e7', 'Admin', 2);
INSERT INTO "public"."users" VALUES (2, 't', 'user', 'f76806404126e786dab333f061d50bc1', 'User', 0);
COMMIT;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."categories_id_seq"', 8, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."products_id_seq"', 6, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."receipts_id_seq"', 67, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."user_id_seq"', 3, true);

-- ----------------------------
-- Uniques structure for table categories
-- ----------------------------
ALTER TABLE "public"."categories" ADD CONSTRAINT "categories_name_key" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table categories
-- ----------------------------
ALTER TABLE "public"."categories" ADD CONSTRAINT "pk_categories" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table databasechangeloglock
-- ----------------------------
ALTER TABLE "public"."databasechangeloglock" ADD CONSTRAINT "pk_databasechangeloglock" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table products
-- ----------------------------
CREATE INDEX "products_lower_idx" ON "public"."products" USING btree (
  lower(name::text) COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Uniques structure for table products
-- ----------------------------
ALTER TABLE "public"."products" ADD CONSTRAINT "products_name_key" UNIQUE ("name");

-- ----------------------------
-- Primary Key structure for table products
-- ----------------------------
ALTER TABLE "public"."products" ADD CONSTRAINT "pk_products" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table receipts
-- ----------------------------
ALTER TABLE "public"."receipts" ADD CONSTRAINT "pk_receipts" PRIMARY KEY ("id", "user_id");

-- ----------------------------
-- Primary Key structure for table terminals
-- ----------------------------
ALTER TABLE "public"."terminals" ADD CONSTRAINT "pk_terminals" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table users
-- ----------------------------
CREATE UNIQUE INDEX "users_idx" ON "public"."users" USING btree (
  "login" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "public"."users" ADD CONSTRAINT "pk_users" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table products
-- ----------------------------
ALTER TABLE "public"."products" ADD CONSTRAINT "categories_id_fkey" FOREIGN KEY ("categories_id") REFERENCES "public"."categories" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table receipts
-- ----------------------------
ALTER TABLE "public"."receipts" ADD CONSTRAINT "products_id_fkey" FOREIGN KEY ("id_product") REFERENCES "public"."products" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."receipts" ADD CONSTRAINT "users_id_fkey" FOREIGN KEY ("user_id") REFERENCES "public"."users" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
