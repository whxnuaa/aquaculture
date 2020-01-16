package com.jit.aquaculture.mapper.user;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.domain.user.LeaveMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface LeaveMsgMapper extends BaseMapper<LeaveMsg>{

    @Insert("INSERT INTO leave_msg(id,content,from_username,to_username,sysTime) VALUES(#{leaveMsg.id},#{leaveMsg.content},#{leaveMsg.fromUsername},#{leaveMsg.toUsername},#{leaveMsg.sysTime})")
    Integer insert(@Param("leaveMsg") LeaveMsg leaveMsg);
}
