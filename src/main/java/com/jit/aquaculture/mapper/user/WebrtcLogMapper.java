package com.jit.aquaculture.mapper.user;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.domain.user.WebrtcLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface WebrtcLogMapper extends BaseMapper<WebrtcLog> {

    @Insert("INSERT INTO webrtc_log(id,from_user,to_user,invite_time,invite_result,start_time,end_time,length,connect_state) VALUES(#{webrtcLog.id}, #{webrtcLog.fromUser},"+
            "#{webrtcLog.toUser}, #{webrtcLog.inviteTime}, #{webrtcLog.inviteResult}, #{webrtcLog.startTime}, #{webrtcLog.endTime}, #{webrtcLog.length}, #{webrtcLog.connectState})")
    Integer insert(@Param("webrtcLog") WebrtcLog webrtcLog);


    @Select("SELECT * FROM webrtc_log WHERE id = #{webrtcLogId}")
    WebrtcLog findById(@Param("webrtcLogId") String webrtcLogId);

    @Update("UPDATE webrtc_log set start_time = #{webrtcLog.startTime}, invite_result = #{webrtcLog.inviteResult}, connect_state = #{webrtcLog.connectState} WHERE id = #{webrtcLog.id}")
    void updateAnswerInvite(@Param("webrtcLog") WebrtcLog webrtcLog);

    @Update("UPDATE webrtc_log set invite_result = #{inviteResult}, connect_state = #{connectState} WHERE id = #{webrtcLogId}")
    void updateHandup(@Param("inviteResult") String inviteResult, @Param("connectState") Boolean connectState, @Param("webrtcLogId") String webrtcLogId);

    @Update("UPDATE webrtc_log set invite_result = #{webrtcLog.inviteResult}, end_time = #{webrtcLog.endTime}, length = #{webrtcLog.length} WHERE id = #{webrtcLog.id}")
    void updateOverVideo(@Param("webrtcLog") WebrtcLog webrtcLog);
}
