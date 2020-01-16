package com.jit.aquaculture.spider;

import com.jit.aquaculture.domain.knowledge.Knowledge;
import com.jit.aquaculture.mapper.knowledge.KnowledgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Component
public class KnowledgeLine implements Pipeline {
    @Autowired
    private KnowledgeMapper knowledgeMapper;

    public volatile int count = 0;
    @Override
    public void process(ResultItems resultItems, Task task) {
        Knowledge knowledge = resultItems.get("knowledge");
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        String name = "";//知识库百科名称
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
            if (entry.getKey() == "name"&& resultItems.get("name")==null){
                //防止github可能爬取到topic之类的链接，防止数据行为空
                continue;
            }
            name = knowledge.getName();
            if (name!="" && name!=null) {
                Knowledge isExist = knowledgeMapper.select(name);
                if (isExist == null) {
                    knowledgeMapper.insert(knowledge);
                    count++;
                } else {
                    // 更新知识库
                    knowledgeMapper.update(knowledge);

                }
            }
        }
        System.out.println("已经插入第"+count+"条数据");
    }
}
