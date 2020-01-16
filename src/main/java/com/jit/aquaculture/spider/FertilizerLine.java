package com.jit.aquaculture.spider;


import com.jit.aquaculture.domain.knowledge.Fertilizer;
import com.jit.aquaculture.mapper.knowledge.FertilizerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class FertilizerLine implements Pipeline {

    @Autowired
    private FertilizerMapper fertilizerMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        Fertilizer fertilizer = resultItems.get("fertilizer");
//        System.out.println("1  "+ resultItems.getAll());
//        System.out.println("fertilizer.name   " + fertilizer.getName());
        if (fertilizer!=null){
            Fertilizer isExist = fertilizerMapper.selectName(fertilizer.getName());
            if (isExist != null){
                fertilizerMapper.updateFertilizer(fertilizer);
            }
            fertilizerMapper.insert(fertilizer);
        }
    }
}
