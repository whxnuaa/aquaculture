package com.jit.aquaculture.spider;



import com.jit.aquaculture.domain.knowledge.Pesticide;
import com.jit.aquaculture.serviceimpl.knowledge.PesticideServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.Map;

@Component
public class PesticideLine implements Pipeline {

    @Autowired
    PesticideServiceImpl pesticideService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Pesticide pesticide = resultItems.get("pesticide");
//        if (pesticide != null){
//            pesticideService.insert(pesticide);
//        }
        String name = "";//知识库百科名称
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
            if (entry.getKey() == "name"&& resultItems.get("name")==null){
                //防止github可能爬取到topic之类的链接，防止数据行为空
                continue;
            }
            name = entry.getValue().toString();
        }
        if (name!="" && name!=null) {
            Pesticide isExist = pesticideService.selectName(name);
            if (isExist == null) {
                pesticide.setPublishTime(new Date());
                pesticideService.insert(pesticide);
            } else {
                pesticide.setPublishTime(new Date());
                pesticideService.update(pesticide);
            }
        }

    }
}
