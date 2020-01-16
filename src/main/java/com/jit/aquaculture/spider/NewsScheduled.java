package com.jit.aquaculture.spider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class NewsScheduled {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private KnowledgeLine knowledgeLine;


    @Scheduled(cron = "0 0 0/2 * * ? ")//从0点开始,每2个小时执行一次
    public void jianShuScheduled() {
//        Specification<Config> specification = new Specification<Config>() {
//            @Override
//            public Predicate toPredicate(Root<Config> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                criteriaQuery.where(criteriaBuilder.equal(root.get("code"), "JianShuScheduled"));
//                return null;
//            }
//        };
//        Config config = configService.findOne(specification);
//        if (config != null && config.getContent().equals("1")) {
//            System.out.println("----开始执行简书定时任务");
//            Spider spider = Spider.create(new KnowledgeProcessor());
//            spider.addUrl("http://www.jianshu.com");
//            spider.addPipeline(newsPipeline);
//            spider.thread(5);
//            spider.setExitWhenComplete(true);
//            spider.start();
//            spider.stop();
//        }
    }



}
