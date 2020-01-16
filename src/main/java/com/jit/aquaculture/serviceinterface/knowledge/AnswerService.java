package com.jit.aquaculture.serviceinterface.knowledge;


import com.jit.aquaculture.domain.knowledge.Answer;

public interface AnswerService {
    Answer insertAnswer(String content, Integer question_id);
    Answer updateAnswer(String content, Integer id);
    Boolean deleteAnswer(Integer id);

}
