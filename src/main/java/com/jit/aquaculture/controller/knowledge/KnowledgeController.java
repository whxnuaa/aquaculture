package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.spider.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

@Api(value = "knowledge", description = "爬虫相关")
@ResponseResult
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private PesticideLine pesticideLine;

    @Autowired
    private FertilizerLine fertilizerLine;
	
    @Autowired
    private KnowledgeLine knowledgeLine;

    @Autowired
    private TechnologyLine technologyLine;
    @ApiOperation(value = "爬取网站数据",notes = "knowledge")

//    @RequestMapping(value="/crawl",method = RequestMethod.POST)
//    public  void crawInfo(){
//        SpiderKnowledge();
//        SpiderTechnology();
//        SpiderPesticide();
//        SpiderFertilizer();
//
//    }


//    @ApiOperation(value = "农药库爬虫",notes = "农药类爬虫")
//    @RequestMapping(value="/pesticide",method = RequestMethod.GET)
    public void SpiderPesticide(){
        long startTime, endTime;
        System.out.println("【爬虫开始】...");
        startTime = System.currentTimeMillis();
        Spider spider=Spider.create(new PesticideProcessor());
        spider.addUrl("http://tupu.zgny.com.cn/list_nytp.aspx");
//        spider.addUrl("http://tupu.zgny.com.cn/nongyaotp_js_scj/List.shtml");
        spider.addPipeline(pesticideLine);
        spider.thread(5);
//        spider.setExitWhenComplete(true);
        spider.run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");

    }

//    @ApiOperation(value = "肥料库爬虫",notes = "肥料类爬虫")
//    @RequestMapping(value="/fertilizer",method = RequestMethod.GET)
    public void SpiderFertilizer(){
        long startTime, endTime;
        System.out.println("【爬虫开始】...");
        startTime = System.currentTimeMillis();
        Spider spider=Spider.create(new FertilizerProcessor());
//        spider.addUrl("http://feiliao.aweb.com.cn/pzcpdq-1-14.shtml");
        spider.addUrl("http://feiliao.aweb.com.cn/pzcpdq.shtml");
//        spider.addUrl("http://tupu.zgny.com.cn/nongyaotp_js_scj/List.shtml");
        spider.addPipeline(fertilizerLine);
        spider.thread(5);
//        spider.setExitWhenComplete(true);
        spider.run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");

    }
	
	    /**
     * 百科知识爬取
     */
    public void SpiderKnowledge(){
        long startTime, endTime;
        System.out.println("【爬虫开始】...");
        startTime = System.currentTimeMillis();

        Spider spider=Spider.create(new KnowledgeProcessor());
        spider.addUrl("http://tupu.zgny.com.cn/zhongyetp_js_sucaizz/List.shtml");
        spider.addPipeline(knowledgeLine);
        spider.thread(5);
        spider.run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");

    }

    /**
     * 技术文章爬取
     */
    public void SpiderTechnology(){
        long startTime, endTime;
        System.out.println("【爬虫开始】...");
        startTime = System.currentTimeMillis();

        Spider spider=Spider.create(new TechnologyProcessor());
        spider.addUrl("http://sc.zgny.com.cn/Techs/");
        spider.addPipeline(technologyLine);
        spider.thread(5);
        spider.run();
        endTime = System.currentTimeMillis();
        System.out.println("【爬虫结束】，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");

    }
}
