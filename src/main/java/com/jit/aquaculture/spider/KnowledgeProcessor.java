package com.jit.aquaculture.spider;


import com.jit.aquaculture.domain.knowledge.Knowledge;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;
import java.util.List;


public class KnowledgeProcessor implements PageProcessor {

   //农业图谱
    //http://tupu.zgny.com.cn/zhongyetp_js_sucaizz/List.shtml
    public static final String URL_LIST = "http://tupu\\.zgny\\.com\\.cn/zhongyetp_js_sucaizz/List\\.shtml";
    public static final String URL_POST = "http://www\\.zgny\\.com\\.cn/ifm/tech/\\d{4}-\\d{1,2}-\\d{1,2}/\\d{5}\\.shtml";

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setSleepTime(100)
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

    private static int size = 0;// 共抓取到的文章数量
    Knowledge knowledge = new Knowledge();

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    @Override
    public void process(Page page) {
        //列表页
        if (page.getUrl().regex(URL_LIST).match()){
            List<String> str = page.getHtml().links().regex(URL_POST).all();
            page.addTargetRequests(str);
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
        //文章页
        }else {
            page.putField("name",page.getHtml().xpath("//div[@class='conLeft']/h1/text()"));
            page.putField("content",page.getHtml().xpath("//div[@class='conLeft']//div[@class='wenZi_02']"));
            page.putField("image",page.getHtml().xpath("//div[@class='conLeft']//div[@class='wenZi_02']/p[1]/img/@src"));
            page.putField("source",page.getHtml().xpath("//div[@class='conLeft']/p[1]/text()"));
        }
//
        knowledge.setName(page.getHtml().xpath("//div[@class='conLeft']/h1/text()").toString());
        String content = page.getHtml().xpath("//div[@class='conLeft']/div[@class='wenZi_02']").toString();
        String img =  page.getHtml().xpath("//div[@class='conLeft']/div[@class='wenZi_02']").regex("<img src=\"/eweb/uploadfile/\\w+\\.\\w+\" border=\"0\">").toString();


        if (content!=null) {
            content = content.replace("<p>", "");
            content = content.replace("</p>", "");
            content = content.replace("<strong>", "");
            content = content.replace("</strong>", "");
            content = content.replace("</div>", "");
            content = content.replace("<div class=\"wenZi_02\">", "");
            if (img!=null){
                content = content.replace(img,"");
            }

        }
        knowledge.setContent(content);
        String image_src = page.getHtml().xpath("//div[@class='conLeft']//div[@class='wenZi_02']//img/@src").toString();
        String image = "";
        if (image_src!=null){
            image = "http://www.zgny.com.cn"+image_src;//图片源
        }
        knowledge.setImage(image);
        String source = page.getHtml().xpath("//div[@class='conLeft']/p[1]/text()").toString();

        //只保留文章来源
        if(source!=null){
            System.out.println("============:"+source.length());
            source = source.substring(38);
        }

        knowledge.setSource(source);
        System.out.println(knowledge.getName() + " ----- "+knowledge.getContent()+"-----"+knowledge.getImage());
        knowledge.setPublish_time(new Date());
        page.putField("knowledge",knowledge);
    }

    @Override
    public Site getSite() {
        return site;
    }

}
