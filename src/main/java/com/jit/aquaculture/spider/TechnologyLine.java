package com.jit.aquaculture.spider;


import com.jit.aquaculture.domain.knowledge.Technology;
import com.jit.aquaculture.mapper.knowledge.TechnologyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Component
public class TechnologyLine implements Pipeline {
    @Autowired
    private TechnologyMapper technologyMapper;
    public volatile int count = 0;
    @Override
    public void process(ResultItems resultItems, Task task) {
        Technology technology = resultItems.get("technology");
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        String name = "";//技术库
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
            if (entry.getKey() == "name"&& resultItems.get("name")==null){
                //防止数据行为空
                continue;
            }
            if (entry.getKey()=="name"){
                name = entry.getValue().toString();
            }
            if (name!="" && name!=null && technology.getContent()!=null && technology.getContent()!="") {//标题和内容都不为空,则插入数据库
                Technology isExist = technologyMapper.select(name);
                if (isExist == null) {
                    technologyMapper.insert(technology);
                    count++;
                } else {
                    // 更新知识库
                    technologyMapper.update(technology);

                }
            }
        }
        System.out.println("已经插入第"+count+"条数据");
    }
}
