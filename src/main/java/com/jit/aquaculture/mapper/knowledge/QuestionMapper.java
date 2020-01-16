package com.jit.aquaculture.mapper.knowledge;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.knowledge.Question;
import com.jit.aquaculture.dto.QuestionDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("questionMapper")
public interface QuestionMapper extends BaseMapper<Question> {
    @Insert("insert into question(username,type,description,image,publishTime) " +
            "values(#{question.username},#{question.type},#{question.description}," +
            "#{question.image},#{question.publishTime})")
    @Options(useGeneratedKeys = true,keyProperty = "question.id")
    Integer insert(@Param("question") Question question);

    @Update("update question set username=#{question.username},type=#{question.type}," +
            "description=#{question.description},image=#{question.image},publishTime=#{question.publishTime} where id=#{question.id}")
    int update(@Param("question") Question question);

    @Delete("delete from question where id=#{id}")
    int delete(@Param("id") Integer id);

    @DeleteProvider(type = DynamicSql.class, method = "deleteQuestionBatch")
    void deleteQuestionBatch(@Param("ids") String ids);

    @Select("select * from question where id=#{id}")
    QuestionDto getOne(@Param("id") Integer id);

    @Select("SELECT q.*, u.image AS userImage FROM question q LEFT JOIN user u ON u.`username` = q.`username` WHERE q.`id`=#{id}")
    QuestionDto findById(@Param("id") Integer id);

    @Select("select q.*,u.image AS userImage from question q left join user u on u.username = q.username order by publishTime DESC")
    List<QuestionDto> getAll();

    @Select("select q.*,u.image AS userImage from question q left join user u on u.username = q.username where type=#{type} order by publishTime DESC")
    List<QuestionDto> getByType(@Param("type") String type);

    @Select("select q.*,u.image As userImage from question q left join user u on u.username = q.username where q.username=#{username}")
    List<QuestionDto> getByUsername(@Param("username") String username);

    @Update("update question set type=#{type} where id = #{id}")
    void updateType(@Param("type") Integer type, @Param("id") Integer id);

}
