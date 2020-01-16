package com.jit.aquaculture.mapper.user;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.domain.user.LeaveMsgAnswer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LeaveMsgAnswerMapper extends BaseMapper<LeaveMsgAnswer>{

    @Insert("INSERT INTO leave_msg_answer(id,leave_msgId,content,from_username,to_username,sysTime) VALUES(#{leaveMsgAnswer.id},#{leaveMsgAnswer.leaveMsgId},"+
            "#{leaveMsgAnswer.content},#{leaveMsgAnswer.fromUsername},#{leaveMsgAnswer.toUsername},#{leaveMsgAnswer.sysTime})")
    Integer insert(@Param("leaveMsgAnswer") LeaveMsgAnswer leaveMsgAnswer);
}
