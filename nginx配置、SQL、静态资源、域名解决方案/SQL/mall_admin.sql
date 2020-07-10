/*
 Navicat Premium Data Transfer

 Source Server         : 10.43.1.52
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 10.43.1.52:3306
 Source Schema         : mall_admin

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 10/07/2020 22:20:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', NULL, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000172687412E87874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RenrenScheduler', 'LAPTOP-1IOHSLBR1594385595491', 1594389562774, 15000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', NULL, 1594389600000, 1594387800000, 5, 'WAITING', 'CRON', 1590919162000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000172687412E87874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000A66697265666C796E617974000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, 'testTask', 'fireflynay', '0 0/30 * * * ?', 0, '参数测试', '2020-05-31 09:58:57');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE,
  INDEX `job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 308 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES (1, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 18:00:00');
INSERT INTO `schedule_job_log` VALUES (2, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 18:30:00');
INSERT INTO `schedule_job_log` VALUES (3, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 19:00:00');
INSERT INTO `schedule_job_log` VALUES (4, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 19:30:00');
INSERT INTO `schedule_job_log` VALUES (5, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 20:00:00');
INSERT INTO `schedule_job_log` VALUES (6, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 20:30:00');
INSERT INTO `schedule_job_log` VALUES (7, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 21:00:00');
INSERT INTO `schedule_job_log` VALUES (8, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 21:30:00');
INSERT INTO `schedule_job_log` VALUES (9, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 22:00:00');
INSERT INTO `schedule_job_log` VALUES (10, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 22:30:00');
INSERT INTO `schedule_job_log` VALUES (11, 1, 'testTask', 'renren', 0, NULL, 1, '2020-05-31 23:00:00');
INSERT INTO `schedule_job_log` VALUES (12, 1, 'testTask', 'renren', 0, NULL, 0, '2020-05-31 23:30:00');
INSERT INTO `schedule_job_log` VALUES (13, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 10:30:00');
INSERT INTO `schedule_job_log` VALUES (14, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 11:00:00');
INSERT INTO `schedule_job_log` VALUES (15, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 11:30:00');
INSERT INTO `schedule_job_log` VALUES (16, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 12:00:00');
INSERT INTO `schedule_job_log` VALUES (17, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 12:30:00');
INSERT INTO `schedule_job_log` VALUES (18, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 13:00:00');
INSERT INTO `schedule_job_log` VALUES (19, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 13:30:00');
INSERT INTO `schedule_job_log` VALUES (20, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 14:00:00');
INSERT INTO `schedule_job_log` VALUES (21, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 14:30:00');
INSERT INTO `schedule_job_log` VALUES (22, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 15:00:00');
INSERT INTO `schedule_job_log` VALUES (23, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 15:30:00');
INSERT INTO `schedule_job_log` VALUES (24, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 16:00:00');
INSERT INTO `schedule_job_log` VALUES (25, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 16:30:00');
INSERT INTO `schedule_job_log` VALUES (26, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 17:00:00');
INSERT INTO `schedule_job_log` VALUES (27, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 17:30:00');
INSERT INTO `schedule_job_log` VALUES (28, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 18:00:00');
INSERT INTO `schedule_job_log` VALUES (29, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 18:30:00');
INSERT INTO `schedule_job_log` VALUES (30, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 19:00:00');
INSERT INTO `schedule_job_log` VALUES (31, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 19:30:00');
INSERT INTO `schedule_job_log` VALUES (32, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 20:00:00');
INSERT INTO `schedule_job_log` VALUES (33, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 20:30:00');
INSERT INTO `schedule_job_log` VALUES (34, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 21:00:00');
INSERT INTO `schedule_job_log` VALUES (35, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 21:30:00');
INSERT INTO `schedule_job_log` VALUES (36, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 22:00:00');
INSERT INTO `schedule_job_log` VALUES (37, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-01 23:00:00');
INSERT INTO `schedule_job_log` VALUES (38, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-01 23:30:00');
INSERT INTO `schedule_job_log` VALUES (39, 1, 'testTask', 'renren', 0, NULL, 6, '2020-06-02 00:00:00');
INSERT INTO `schedule_job_log` VALUES (40, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 11:00:00');
INSERT INTO `schedule_job_log` VALUES (41, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 11:30:00');
INSERT INTO `schedule_job_log` VALUES (42, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 12:00:00');
INSERT INTO `schedule_job_log` VALUES (43, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 12:30:00');
INSERT INTO `schedule_job_log` VALUES (44, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 13:00:00');
INSERT INTO `schedule_job_log` VALUES (45, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 13:30:00');
INSERT INTO `schedule_job_log` VALUES (46, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 14:00:00');
INSERT INTO `schedule_job_log` VALUES (47, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 14:30:00');
INSERT INTO `schedule_job_log` VALUES (48, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 15:00:00');
INSERT INTO `schedule_job_log` VALUES (49, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 15:30:00');
INSERT INTO `schedule_job_log` VALUES (50, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 16:00:00');
INSERT INTO `schedule_job_log` VALUES (51, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 16:30:00');
INSERT INTO `schedule_job_log` VALUES (52, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 17:00:00');
INSERT INTO `schedule_job_log` VALUES (53, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 17:30:00');
INSERT INTO `schedule_job_log` VALUES (54, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 18:00:00');
INSERT INTO `schedule_job_log` VALUES (55, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 18:30:00');
INSERT INTO `schedule_job_log` VALUES (56, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 19:30:00');
INSERT INTO `schedule_job_log` VALUES (57, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 20:00:00');
INSERT INTO `schedule_job_log` VALUES (58, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 20:30:00');
INSERT INTO `schedule_job_log` VALUES (59, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 21:00:00');
INSERT INTO `schedule_job_log` VALUES (60, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 21:30:00');
INSERT INTO `schedule_job_log` VALUES (61, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 22:00:00');
INSERT INTO `schedule_job_log` VALUES (62, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 22:30:00');
INSERT INTO `schedule_job_log` VALUES (63, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-02 23:00:00');
INSERT INTO `schedule_job_log` VALUES (64, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-02 23:30:00');
INSERT INTO `schedule_job_log` VALUES (65, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 15:00:00');
INSERT INTO `schedule_job_log` VALUES (66, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 15:30:00');
INSERT INTO `schedule_job_log` VALUES (67, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 16:00:00');
INSERT INTO `schedule_job_log` VALUES (68, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 16:30:00');
INSERT INTO `schedule_job_log` VALUES (69, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 17:00:00');
INSERT INTO `schedule_job_log` VALUES (70, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 17:30:00');
INSERT INTO `schedule_job_log` VALUES (71, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 18:00:00');
INSERT INTO `schedule_job_log` VALUES (72, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 18:30:00');
INSERT INTO `schedule_job_log` VALUES (73, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 19:00:00');
INSERT INTO `schedule_job_log` VALUES (74, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 19:30:00');
INSERT INTO `schedule_job_log` VALUES (75, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 20:00:00');
INSERT INTO `schedule_job_log` VALUES (76, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 20:30:00');
INSERT INTO `schedule_job_log` VALUES (77, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 21:00:00');
INSERT INTO `schedule_job_log` VALUES (78, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 21:30:00');
INSERT INTO `schedule_job_log` VALUES (79, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 22:00:00');
INSERT INTO `schedule_job_log` VALUES (80, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-03 23:00:00');
INSERT INTO `schedule_job_log` VALUES (81, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-03 23:30:00');
INSERT INTO `schedule_job_log` VALUES (82, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 13:00:00');
INSERT INTO `schedule_job_log` VALUES (83, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 13:30:00');
INSERT INTO `schedule_job_log` VALUES (84, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 14:00:00');
INSERT INTO `schedule_job_log` VALUES (85, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 14:30:00');
INSERT INTO `schedule_job_log` VALUES (86, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 15:00:00');
INSERT INTO `schedule_job_log` VALUES (87, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 15:30:00');
INSERT INTO `schedule_job_log` VALUES (88, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 16:00:00');
INSERT INTO `schedule_job_log` VALUES (89, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 16:30:00');
INSERT INTO `schedule_job_log` VALUES (90, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 17:00:00');
INSERT INTO `schedule_job_log` VALUES (91, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 17:30:00');
INSERT INTO `schedule_job_log` VALUES (92, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 18:00:00');
INSERT INTO `schedule_job_log` VALUES (93, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 18:30:00');
INSERT INTO `schedule_job_log` VALUES (94, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 19:30:00');
INSERT INTO `schedule_job_log` VALUES (95, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 20:00:00');
INSERT INTO `schedule_job_log` VALUES (96, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 20:30:00');
INSERT INTO `schedule_job_log` VALUES (97, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 21:00:00');
INSERT INTO `schedule_job_log` VALUES (98, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 21:30:00');
INSERT INTO `schedule_job_log` VALUES (99, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-04 22:00:00');
INSERT INTO `schedule_job_log` VALUES (100, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 22:30:00');
INSERT INTO `schedule_job_log` VALUES (101, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 23:00:00');
INSERT INTO `schedule_job_log` VALUES (102, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-04 23:30:00');
INSERT INTO `schedule_job_log` VALUES (103, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 11:00:00');
INSERT INTO `schedule_job_log` VALUES (104, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 11:30:00');
INSERT INTO `schedule_job_log` VALUES (105, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 12:00:00');
INSERT INTO `schedule_job_log` VALUES (106, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 12:30:00');
INSERT INTO `schedule_job_log` VALUES (107, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 13:00:00');
INSERT INTO `schedule_job_log` VALUES (108, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 13:30:00');
INSERT INTO `schedule_job_log` VALUES (109, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 14:00:00');
INSERT INTO `schedule_job_log` VALUES (110, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 14:30:00');
INSERT INTO `schedule_job_log` VALUES (111, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 15:00:00');
INSERT INTO `schedule_job_log` VALUES (112, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 15:30:00');
INSERT INTO `schedule_job_log` VALUES (113, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 16:00:00');
INSERT INTO `schedule_job_log` VALUES (114, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 16:30:00');
INSERT INTO `schedule_job_log` VALUES (115, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 17:00:00');
INSERT INTO `schedule_job_log` VALUES (116, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 17:30:00');
INSERT INTO `schedule_job_log` VALUES (117, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 18:00:00');
INSERT INTO `schedule_job_log` VALUES (118, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 18:30:00');
INSERT INTO `schedule_job_log` VALUES (119, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 19:00:00');
INSERT INTO `schedule_job_log` VALUES (120, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 19:30:00');
INSERT INTO `schedule_job_log` VALUES (121, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-05 20:00:00');
INSERT INTO `schedule_job_log` VALUES (122, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 20:30:00');
INSERT INTO `schedule_job_log` VALUES (123, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 21:00:00');
INSERT INTO `schedule_job_log` VALUES (124, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 21:30:00');
INSERT INTO `schedule_job_log` VALUES (125, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 22:30:00');
INSERT INTO `schedule_job_log` VALUES (126, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 23:00:00');
INSERT INTO `schedule_job_log` VALUES (127, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-05 23:30:00');
INSERT INTO `schedule_job_log` VALUES (128, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 11:00:00');
INSERT INTO `schedule_job_log` VALUES (129, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 11:30:00');
INSERT INTO `schedule_job_log` VALUES (130, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 12:00:00');
INSERT INTO `schedule_job_log` VALUES (131, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 12:30:00');
INSERT INTO `schedule_job_log` VALUES (132, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-06 13:00:00');
INSERT INTO `schedule_job_log` VALUES (133, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-06 19:00:00');
INSERT INTO `schedule_job_log` VALUES (134, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 19:30:00');
INSERT INTO `schedule_job_log` VALUES (135, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 20:00:00');
INSERT INTO `schedule_job_log` VALUES (136, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-06 20:30:00');
INSERT INTO `schedule_job_log` VALUES (137, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 21:00:00');
INSERT INTO `schedule_job_log` VALUES (138, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 21:30:00');
INSERT INTO `schedule_job_log` VALUES (139, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-06 22:00:00');
INSERT INTO `schedule_job_log` VALUES (140, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-06 23:30:00');
INSERT INTO `schedule_job_log` VALUES (141, 1, 'testTask', 'fireflynay', 0, NULL, 7, '2020-06-07 00:00:00');
INSERT INTO `schedule_job_log` VALUES (142, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 00:30:00');
INSERT INTO `schedule_job_log` VALUES (143, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 01:00:00');
INSERT INTO `schedule_job_log` VALUES (144, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 10:30:00');
INSERT INTO `schedule_job_log` VALUES (145, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 11:00:00');
INSERT INTO `schedule_job_log` VALUES (146, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 11:30:00');
INSERT INTO `schedule_job_log` VALUES (147, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 12:00:00');
INSERT INTO `schedule_job_log` VALUES (148, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 12:30:00');
INSERT INTO `schedule_job_log` VALUES (149, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 13:00:00');
INSERT INTO `schedule_job_log` VALUES (150, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 13:30:00');
INSERT INTO `schedule_job_log` VALUES (151, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 14:00:00');
INSERT INTO `schedule_job_log` VALUES (152, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 14:30:00');
INSERT INTO `schedule_job_log` VALUES (153, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 15:00:00');
INSERT INTO `schedule_job_log` VALUES (154, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 15:30:00');
INSERT INTO `schedule_job_log` VALUES (155, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 16:00:00');
INSERT INTO `schedule_job_log` VALUES (156, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 16:30:00');
INSERT INTO `schedule_job_log` VALUES (157, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 17:00:00');
INSERT INTO `schedule_job_log` VALUES (158, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 17:30:00');
INSERT INTO `schedule_job_log` VALUES (159, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 18:00:00');
INSERT INTO `schedule_job_log` VALUES (160, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 19:30:00');
INSERT INTO `schedule_job_log` VALUES (161, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 20:00:00');
INSERT INTO `schedule_job_log` VALUES (162, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 20:30:00');
INSERT INTO `schedule_job_log` VALUES (163, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 21:00:00');
INSERT INTO `schedule_job_log` VALUES (164, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 21:30:00');
INSERT INTO `schedule_job_log` VALUES (165, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-07 22:00:00');
INSERT INTO `schedule_job_log` VALUES (166, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 22:30:00');
INSERT INTO `schedule_job_log` VALUES (167, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-07 23:00:00');
INSERT INTO `schedule_job_log` VALUES (168, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 17:00:00');
INSERT INTO `schedule_job_log` VALUES (169, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 17:30:00');
INSERT INTO `schedule_job_log` VALUES (170, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 18:00:00');
INSERT INTO `schedule_job_log` VALUES (171, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 18:30:00');
INSERT INTO `schedule_job_log` VALUES (172, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 19:00:00');
INSERT INTO `schedule_job_log` VALUES (173, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 19:30:00');
INSERT INTO `schedule_job_log` VALUES (174, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 20:00:00');
INSERT INTO `schedule_job_log` VALUES (175, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 20:30:00');
INSERT INTO `schedule_job_log` VALUES (176, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 21:00:00');
INSERT INTO `schedule_job_log` VALUES (177, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 21:30:00');
INSERT INTO `schedule_job_log` VALUES (178, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 22:00:00');
INSERT INTO `schedule_job_log` VALUES (179, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 22:30:00');
INSERT INTO `schedule_job_log` VALUES (180, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-08 23:00:00');
INSERT INTO `schedule_job_log` VALUES (181, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-08 23:30:00');
INSERT INTO `schedule_job_log` VALUES (182, 1, 'testTask', 'fireflynay', 0, NULL, 5, '2020-06-09 00:00:00');
INSERT INTO `schedule_job_log` VALUES (183, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 00:30:00');
INSERT INTO `schedule_job_log` VALUES (184, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 01:00:00');
INSERT INTO `schedule_job_log` VALUES (185, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 13:00:00');
INSERT INTO `schedule_job_log` VALUES (186, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 13:30:00');
INSERT INTO `schedule_job_log` VALUES (187, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 14:00:00');
INSERT INTO `schedule_job_log` VALUES (188, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 14:30:00');
INSERT INTO `schedule_job_log` VALUES (189, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES (190, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES (191, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES (192, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES (193, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES (194, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES (195, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES (196, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES (197, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES (198, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 19:30:00');
INSERT INTO `schedule_job_log` VALUES (199, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 20:00:00');
INSERT INTO `schedule_job_log` VALUES (200, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 20:30:00');
INSERT INTO `schedule_job_log` VALUES (201, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 21:00:00');
INSERT INTO `schedule_job_log` VALUES (202, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 21:30:00');
INSERT INTO `schedule_job_log` VALUES (203, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 22:00:00');
INSERT INTO `schedule_job_log` VALUES (204, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 22:30:00');
INSERT INTO `schedule_job_log` VALUES (205, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-09 23:00:00');
INSERT INTO `schedule_job_log` VALUES (206, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-09 23:30:00');
INSERT INTO `schedule_job_log` VALUES (207, 1, 'testTask', 'fireflynay', 0, NULL, 5, '2020-06-10 00:00:00');
INSERT INTO `schedule_job_log` VALUES (208, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-10 16:00:00');
INSERT INTO `schedule_job_log` VALUES (209, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 18:30:00');
INSERT INTO `schedule_job_log` VALUES (210, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 19:00:00');
INSERT INTO `schedule_job_log` VALUES (211, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-12 19:30:00');
INSERT INTO `schedule_job_log` VALUES (212, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 20:00:00');
INSERT INTO `schedule_job_log` VALUES (213, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 20:30:00');
INSERT INTO `schedule_job_log` VALUES (214, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 21:00:00');
INSERT INTO `schedule_job_log` VALUES (215, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-12 21:30:00');
INSERT INTO `schedule_job_log` VALUES (216, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 22:00:00');
INSERT INTO `schedule_job_log` VALUES (217, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 22:30:00');
INSERT INTO `schedule_job_log` VALUES (218, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-12 23:30:00');
INSERT INTO `schedule_job_log` VALUES (219, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-13 11:30:00');
INSERT INTO `schedule_job_log` VALUES (220, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-13 12:30:00');
INSERT INTO `schedule_job_log` VALUES (221, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-13 13:00:00');
INSERT INTO `schedule_job_log` VALUES (222, 1, 'testTask', 'fireflynay', 0, NULL, 3, '2020-06-15 00:00:00');
INSERT INTO `schedule_job_log` VALUES (223, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-15 10:30:00');
INSERT INTO `schedule_job_log` VALUES (224, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-15 11:00:00');
INSERT INTO `schedule_job_log` VALUES (225, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-15 11:30:00');
INSERT INTO `schedule_job_log` VALUES (226, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 13:30:00');
INSERT INTO `schedule_job_log` VALUES (227, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 14:00:00');
INSERT INTO `schedule_job_log` VALUES (228, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 14:30:00');
INSERT INTO `schedule_job_log` VALUES (229, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 15:00:00');
INSERT INTO `schedule_job_log` VALUES (230, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 15:30:00');
INSERT INTO `schedule_job_log` VALUES (231, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 16:00:00');
INSERT INTO `schedule_job_log` VALUES (232, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 16:30:00');
INSERT INTO `schedule_job_log` VALUES (233, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 17:00:00');
INSERT INTO `schedule_job_log` VALUES (234, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 17:30:00');
INSERT INTO `schedule_job_log` VALUES (235, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 18:00:00');
INSERT INTO `schedule_job_log` VALUES (236, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 18:30:00');
INSERT INTO `schedule_job_log` VALUES (237, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 19:00:00');
INSERT INTO `schedule_job_log` VALUES (238, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 19:30:00');
INSERT INTO `schedule_job_log` VALUES (239, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 20:30:00');
INSERT INTO `schedule_job_log` VALUES (240, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 21:00:00');
INSERT INTO `schedule_job_log` VALUES (241, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 21:30:00');
INSERT INTO `schedule_job_log` VALUES (242, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 22:00:00');
INSERT INTO `schedule_job_log` VALUES (243, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 22:30:00');
INSERT INTO `schedule_job_log` VALUES (244, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-22 23:00:00');
INSERT INTO `schedule_job_log` VALUES (245, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-22 23:30:00');
INSERT INTO `schedule_job_log` VALUES (246, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-06-23 13:30:00');
INSERT INTO `schedule_job_log` VALUES (247, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-06-23 15:30:00');
INSERT INTO `schedule_job_log` VALUES (248, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 15:30:00');
INSERT INTO `schedule_job_log` VALUES (249, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 16:00:00');
INSERT INTO `schedule_job_log` VALUES (250, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 16:30:00');
INSERT INTO `schedule_job_log` VALUES (251, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 17:00:00');
INSERT INTO `schedule_job_log` VALUES (252, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 17:30:00');
INSERT INTO `schedule_job_log` VALUES (253, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 18:00:00');
INSERT INTO `schedule_job_log` VALUES (254, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 18:30:00');
INSERT INTO `schedule_job_log` VALUES (255, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 19:00:00');
INSERT INTO `schedule_job_log` VALUES (256, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 19:30:00');
INSERT INTO `schedule_job_log` VALUES (257, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 20:00:00');
INSERT INTO `schedule_job_log` VALUES (258, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 20:30:00');
INSERT INTO `schedule_job_log` VALUES (259, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 21:00:00');
INSERT INTO `schedule_job_log` VALUES (260, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 21:30:00');
INSERT INTO `schedule_job_log` VALUES (261, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-06 22:00:00');
INSERT INTO `schedule_job_log` VALUES (262, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 22:30:00');
INSERT INTO `schedule_job_log` VALUES (263, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 23:00:00');
INSERT INTO `schedule_job_log` VALUES (264, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-06 23:30:00');
INSERT INTO `schedule_job_log` VALUES (265, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 13:00:00');
INSERT INTO `schedule_job_log` VALUES (266, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 13:30:00');
INSERT INTO `schedule_job_log` VALUES (267, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 14:00:00');
INSERT INTO `schedule_job_log` VALUES (268, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 14:30:00');
INSERT INTO `schedule_job_log` VALUES (269, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES (270, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES (271, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES (272, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES (273, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES (274, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES (275, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES (276, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES (277, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES (278, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 19:30:00');
INSERT INTO `schedule_job_log` VALUES (279, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 20:00:00');
INSERT INTO `schedule_job_log` VALUES (280, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 20:30:00');
INSERT INTO `schedule_job_log` VALUES (281, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 21:00:00');
INSERT INTO `schedule_job_log` VALUES (282, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 21:30:00');
INSERT INTO `schedule_job_log` VALUES (283, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 22:00:00');
INSERT INTO `schedule_job_log` VALUES (284, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-09 22:30:00');
INSERT INTO `schedule_job_log` VALUES (285, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 23:00:00');
INSERT INTO `schedule_job_log` VALUES (286, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-09 23:30:00');
INSERT INTO `schedule_job_log` VALUES (287, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 11:00:00');
INSERT INTO `schedule_job_log` VALUES (288, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 12:00:00');
INSERT INTO `schedule_job_log` VALUES (289, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 12:30:00');
INSERT INTO `schedule_job_log` VALUES (290, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 13:00:00');
INSERT INTO `schedule_job_log` VALUES (291, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 13:30:00');
INSERT INTO `schedule_job_log` VALUES (292, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 14:00:00');
INSERT INTO `schedule_job_log` VALUES (293, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 14:30:00');
INSERT INTO `schedule_job_log` VALUES (294, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 15:00:00');
INSERT INTO `schedule_job_log` VALUES (295, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 15:30:00');
INSERT INTO `schedule_job_log` VALUES (296, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 16:00:00');
INSERT INTO `schedule_job_log` VALUES (297, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 16:30:00');
INSERT INTO `schedule_job_log` VALUES (298, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 17:00:00');
INSERT INTO `schedule_job_log` VALUES (299, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 17:30:00');
INSERT INTO `schedule_job_log` VALUES (300, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 18:00:00');
INSERT INTO `schedule_job_log` VALUES (301, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 18:30:00');
INSERT INTO `schedule_job_log` VALUES (302, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 19:00:00');
INSERT INTO `schedule_job_log` VALUES (303, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 19:30:00');
INSERT INTO `schedule_job_log` VALUES (304, 1, 'testTask', 'fireflynay', 0, NULL, 2, '2020-07-10 20:00:00');
INSERT INTO `schedule_job_log` VALUES (305, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 20:30:00');
INSERT INTO `schedule_job_log` VALUES (306, 1, 'testTask', 'fireflynay', 0, NULL, 1, '2020-07-10 21:00:00');
INSERT INTO `schedule_job_log` VALUES (307, 1, 'testTask', 'fireflynay', 0, NULL, 0, '2020-07-10 21:30:00');

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '验证码',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统验证码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
INSERT INTO `sys_captcha` VALUES ('027ea200-df77-4fd4-8eea-cf78f7fb2023', 'pwexg', '2020-06-04 18:34:49');
INSERT INTO `sys_captcha` VALUES ('03324fa8-36af-439e-840b-30fcad65f6b1', 'e568f', '2020-05-31 19:33:35');
INSERT INTO `sys_captcha` VALUES ('0c159600-76da-49c7-8313-872d6d2802b7', '7wyxw', '2020-06-04 12:52:51');
INSERT INTO `sys_captcha` VALUES ('1341f11a-9044-4a17-8e10-6200c1a6c4f9', '3wym7', '2020-05-31 19:54:24');
INSERT INTO `sys_captcha` VALUES ('142702cc-b370-4ee3-83de-9c10cdefe938', 'wwdng', '2020-06-23 15:56:11');
INSERT INTO `sys_captcha` VALUES ('14fd5939-5b26-4489-8d56-c46a2e4b28f9', 'xwnp2', '2020-06-23 15:34:57');
INSERT INTO `sys_captcha` VALUES ('1b10ff7f-d12e-4381-8cc9-79febd353970', '7n7gm', '2020-05-31 19:33:27');
INSERT INTO `sys_captcha` VALUES ('1d406841-28f0-4ad0-8042-07417a62e1fd', 'c8axn', '2020-06-04 18:34:15');
INSERT INTO `sys_captcha` VALUES ('294b1c97-f8df-4244-88c4-47141c8d56af', 'bgcyw', '2020-07-09 13:18:19');
INSERT INTO `sys_captcha` VALUES ('3c82fc6b-31f5-48a5-83a4-ceca5f598179', '6w52n', '2020-06-23 15:56:14');
INSERT INTO `sys_captcha` VALUES ('42f6f840-fc40-4e8a-8528-b79a4a2b8f2a', '654ed', '2020-06-04 18:34:33');
INSERT INTO `sys_captcha` VALUES ('43cd5131-d40b-4a0e-8ba8-f7f68df6f849', '4dem4', '2020-06-04 18:34:49');
INSERT INTO `sys_captcha` VALUES ('55779b39-e638-4a2f-8462-c20297531925', 'x3bpb', '2020-06-02 22:50:47');
INSERT INTO `sys_captcha` VALUES ('640dbcfb-f3cd-421c-8d81-eeac7b11bb87', '27n4g', '2020-05-31 19:55:20');
INSERT INTO `sys_captcha` VALUES ('73031619-e44a-40f7-8ac8-baf960e8df6d', 'gcawe', '2020-06-04 12:55:50');
INSERT INTO `sys_captcha` VALUES ('737d045c-d156-46f9-899a-83aec5846c69', 'gdaxy', '2020-06-22 13:39:52');
INSERT INTO `sys_captcha` VALUES ('7bdfb155-9e27-446c-8e0d-89bc1ff519ed', '54dfb', '2020-05-31 20:27:33');
INSERT INTO `sys_captcha` VALUES ('9b2990ef-98c2-4865-83d3-c04286488498', 'npec2', '2020-06-03 15:05:49');
INSERT INTO `sys_captcha` VALUES ('a0c259cd-9a98-4e6f-893b-f0d203803548', 'xbm2n', '2020-06-04 18:34:49');
INSERT INTO `sys_captcha` VALUES ('a3ae36dd-0cc3-434c-870e-692785911564', 'c664y', '2020-06-13 12:31:03');
INSERT INTO `sys_captcha` VALUES ('a6819c8e-3e7e-44de-8721-0db9d915fbf1', '7n246', '2020-06-04 18:34:49');
INSERT INTO `sys_captcha` VALUES ('ac99ae8c-ec94-4c23-8cce-550570983503', 'fe7en', '2020-06-04 18:34:46');
INSERT INTO `sys_captcha` VALUES ('b8ffa0df-2964-4929-8cec-b7c20f3117fe', 'y6e73', '2020-05-31 18:04:52');
INSERT INTO `sys_captcha` VALUES ('bc588733-8369-4fe0-84d3-ec536a3304a4', 'n5cbf', '2020-05-31 19:53:44');
INSERT INTO `sys_captcha` VALUES ('c2b49735-63aa-4d13-8a8f-f3f325e94f38', 'dbmb8', '2020-06-03 15:05:42');
INSERT INTO `sys_captcha` VALUES ('c79c06bf-1724-4c16-8172-b78106fb86bf', 'f8db4', '2020-06-13 11:06:43');
INSERT INTO `sys_captcha` VALUES ('dd3ab546-a671-4fcc-8b9c-11c0d6254b72', '8g5f6', '2020-06-23 13:39:27');
INSERT INTO `sys_captcha` VALUES ('f4c57fb0-a2cb-4355-8198-3d4ba215c575', 'ancc5', '2020-06-12 18:30:33');
INSERT INTO `sys_captcha` VALUES ('f69bd42b-4f2a-4d96-8176-6701e3621b13', '6g237', '2020-05-31 19:55:17');
INSERT INTO `sys_captcha` VALUES ('ff8267ba-eb4b-49f1-8fe5-62188f93b8cb', 'abxcn', '2020-06-09 00:30:58');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `param_key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":31,\"parentId\":0,\"name\":\"商品系统\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"zhedie\",\"orderNum\":0}]', 16, '0:0:0:0:0:0:0:1', '2020-05-31 18:33:19');
INSERT INTO `sys_log` VALUES (2, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"分类维护\",\"url\":\"product/category\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0}]', 15, '0:0:0:0:0:0:0:1', '2020-05-31 18:34:36');
INSERT INTO `sys_log` VALUES (3, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":33,\"parentId\":31,\"name\":\"品牌管理\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"system\",\"orderNum\":0}]', 18, '0:0:0:0:0:0:0:1', '2020-06-01 14:23:02');
INSERT INTO `sys_log` VALUES (4, 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '[{\"userId\":2,\"username\":\"fireflynay\",\"password\":\"f35b148a296ded29376259f9e7e57650a606be557973c67e7e6807a612d3fdfd\",\"salt\":\"18lAY9Gul41yGzBkSTpT\",\"email\":\"1046762075s@gmail.com\",\"mobile\":\"18156475879\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Jun 6, 2020 11:17:16 PM\"}]', 127, '0:0:0:0:0:0:0:1', '2020-06-06 23:17:16');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO `sys_menu` VALUES (5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', NULL, 1, 'sql', 4);
INSERT INTO `sys_menu` VALUES (6, 1, '定时任务', 'job/schedule', NULL, 1, 'job', 5);
INSERT INTO `sys_menu` VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
INSERT INTO `sys_menu` VALUES (31, 0, '商品系统', '', '', 0, 'editor', 0);
INSERT INTO `sys_menu` VALUES (32, 31, '分类维护', 'product/category', '', 1, 'menu', 0);
INSERT INTO `sys_menu` VALUES (34, 31, '品牌管理', 'product/brand', '', 1, 'editor', 0);
INSERT INTO `sys_menu` VALUES (37, 31, '平台属性', '', '', 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (38, 37, '属性分组', 'product/attrgroup', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (39, 37, '规格参数', 'product/baseattr', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (40, 37, '销售属性', 'product/saleattr', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (41, 31, '商品维护', 'product/spu', '', 0, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (42, 0, '优惠营销', '', '', 0, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (43, 0, '库存系统', '', '', 0, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (44, 0, '订单系统', '', '', 0, 'config', 0);
INSERT INTO `sys_menu` VALUES (45, 0, '用户系统', '', '', 0, 'admin', 0);
INSERT INTO `sys_menu` VALUES (46, 0, '内容管理', '', '', 0, 'sousuo', 0);
INSERT INTO `sys_menu` VALUES (47, 42, '优惠券管理', 'coupon/coupon', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (48, 42, '发放记录', 'coupon/history', '', 1, 'sql', 0);
INSERT INTO `sys_menu` VALUES (49, 42, '专题活动', 'coupon/subject', '', 1, 'tixing', 0);
INSERT INTO `sys_menu` VALUES (50, 42, '秒杀活动', 'coupon/seckill', '', 1, 'daohang', 0);
INSERT INTO `sys_menu` VALUES (51, 42, '积分维护', 'coupon/bounds', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (52, 42, '满减折扣', 'coupon/full', '', 1, 'shoucang', 0);
INSERT INTO `sys_menu` VALUES (53, 43, '仓库维护', 'ware/wareinfo', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (54, 43, '库存工作单', 'ware/task', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (55, 43, '商品库存', 'ware/sku', '', 1, 'jiesuo', 0);
INSERT INTO `sys_menu` VALUES (56, 44, '订单查询', 'order/order', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (57, 44, '退货单处理', 'order/return', '', 1, 'shanchu', 0);
INSERT INTO `sys_menu` VALUES (58, 44, '等级规则', 'order/settings', '', 1, 'system', 0);
INSERT INTO `sys_menu` VALUES (59, 44, '支付流水查询', 'order/payment', '', 1, 'job', 0);
INSERT INTO `sys_menu` VALUES (60, 44, '退款流水查询', 'order/refund', '', 1, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (61, 45, '会员列表', 'member/member', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (62, 45, '会员等级', 'member/level', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (63, 45, '积分变化', 'member/growth', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (64, 45, '统计信息', 'member/statistics', '', 1, 'sql', 0);
INSERT INTO `sys_menu` VALUES (65, 46, '首页推荐', 'content/index', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (66, 46, '分类热门', 'content/category', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (67, 46, '评论管理', 'content/comments', '', 1, 'pinglun', 0);
INSERT INTO `sys_menu` VALUES (68, 41, 'spu管理', 'product/spu', '', 1, 'config', 0);
INSERT INTO `sys_menu` VALUES (69, 41, '发布商品', 'product/spuadd', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (70, 43, '采购单维护', '', '', 0, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (71, 70, '采购需求', 'ware/purchaseitem', '', 1, 'editor', 0);
INSERT INTO `sys_menu` VALUES (72, 70, '采购单', 'ware/purchase', '', 1, 'menu', 0);
INSERT INTO `sys_menu` VALUES (73, 41, '商品管理', 'product/manager', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (74, 42, '会员价格', 'coupon/memberprice', '', 1, 'admin', 0);
INSERT INTO `sys_menu` VALUES (75, 42, '每日秒杀', 'coupon/seckillsession', '', 1, 'job', 0);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件上传' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色与菜单对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', '1046762075@qq.com', '18173516309', 1, 1, '2020-05-20 05:20:00');
INSERT INTO `sys_user` VALUES (2, 'fireflynay', 'f35b148a296ded29376259f9e7e57650a606be557973c67e7e6807a612d3fdfd', '18lAY9Gul41yGzBkSTpT', '1046762075s@gmail.com', '18156475879', 1, 1, '2020-06-06 23:17:16');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与角色对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户Token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES (1, 'db8c608f439fda16db8551f485ff1790', '2020-07-10 01:13:50', '2020-07-09 13:13:50');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
