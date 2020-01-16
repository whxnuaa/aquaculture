package com.jit.aquaculture.mapper.knowledge;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.domain.knowledge.Answer;
import com.jit.aquaculture.dto.AnswerDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("answerMapper")
public interface AnswerMapper extends BaseMapper<Answer>{
    @Insert("insert into answer(question_id,content,username,publishTime) values(#{answer.question_id},#{answer.content},#{answer.username},#{answer.publishTime})")
    @Options(useGeneratedKeys = true,keyProperty = "answer.id")
    Integer insert(@Param("answer") Answer answer);

    @Update("update answer set question_id=#{answer.question_id},content=#{answer.content},username=#{answer.username},publishTime=#{answer.publishTime} where id=#{answer.id}")
    int update(@Param("answer") Answer answer);

    @Delete("delete from answer where id=#{id}")
    int delete(@Param("id") Integer id);

    @Select("select * from answer where id=#{id}")
    Answer getOne(@Param("id") Integer id);

    @Select("select * from answer where question_id=#{question_id}")
    List<Answer> getAllAnswers(@Param("question_id") Integer question_id);

//    @Select("SELECT B.*, C.role_id FROM " +
//            "(SELECT a.*,u.image AS userImage, u.id AS userId FROM answer a LEFT JOIN user u ON a.`username` = u.`username` WHERE question_id=#{question_id})AS B LEFT JOIN " +
//            "(SELECT * FROM user_role ur )AS C ON B.userId = C.user_id")
    @Select("SELECT a.*,u.image AS userImage, u.id AS userId FROM answer a LEFT JOIN user u ON a.`username` = u.`username` WHERE question_id=#{question_id}")
    List<AnswerDto> getAnswers(@Param("question_id") Integer question_id);

}
