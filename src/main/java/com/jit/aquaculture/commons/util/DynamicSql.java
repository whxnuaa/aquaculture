package com.jit.aquaculture.commons.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class DynamicSql {
    /**
     * 批量删除疾病
     * @param para
     * @return
     */
    public String deleteDiseaseBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from disease where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除农药
     * @param para
     * @return
     */
    public String deletePesticideBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from pesticide where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除肥料
     * @param para
     * @return
     */
    public String deleteFertilizerBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from fertilizer where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除百科知识
     * @param para
     * @return
     */
    public String deleteKnowledgeBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from knowledge where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除综合知识
     * @param para
     * @return
     */
    public String deleteTechnologyBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from technology where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 获取要删除用户的id
     * @param para
     * @return
     */
    public String getUserIds(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        Integer type = (Integer) para.get("type");
        String ids = (String) para.get("ids");
        sql.append("select id as userId from user where username in (select username from ");
        if (type == 0){
            sql.append(" customer ");
        } else if (type == 1){
            sql.append(" expert ");
        }
        sql.append("where id in (" + ids + "))");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }


    /**
     * 批量删除用户
     * @param para
     * @return
     */
    public String deleteUserBatch(Map<String,Object> para){

        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("usernames");
        sql.append("delete from user where id in (" + ids  + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除问题
     * @param para
     * @return
     */
    public String deleteQuestionBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from question where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }


    /**
     * 批量问题
     * @param para
     * @return
     */
    public String deleteThrowBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("delete from daily_throw where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除观察记录
     * @param para
     * @return
     */
    public String deleteObserveBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");

        sql.append("delete from daily_observe where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除日志模板
     * @param para
     * @return
     */
    public String deleteTemplateBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");

        sql.append("delete from daily_template where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量更新日志模板启用状态
     * @param para
     * @return
     */
    public String updateTemplateBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");
        sql.append("update daily_template set status = 0 where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }

    /**
     * 批量删除经济效益
     * @param para
     * @return
     */
    public String deleteIncomeBatch(Map<String,Object> para){
        StringBuilder sql = new StringBuilder();
        String ids = (String) para.get("ids");

        sql.append("delete from daily_income where id in (" + ids + ") ");
        log.info("sql = {}",sql.toString());
        return sql.toString();
    }
}
