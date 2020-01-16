package com.jit.aquaculture.spider;


import com.jit.aquaculture.domain.knowledge.Technology;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Date;
import java.util.List;

public class TechnologyProcessor implements PageProcessor {
    //农业图谱
    //http://sc.zgny.com.cn/Techs/Page_1_NodeId_sc_js_zpjs.shtml
//    public static final String URL_LIST="http://sc.zgny.com.cn/";
    public static final  String URL_LIST="http://sc\\.zgny\\.com\\.cn/Techs/";
    //栽培技术
    public static final String URL_LIST_ZP = "http://sc\\.zgny\\.com\\.cn/Techs/Page_1_NodeId_sc_js_\\w{4}\\.shtml";
    //育苗技术
//    public static final String URL_LIST_YM = "http://sc\\.zgny\\.com\\.cn/Techs/Page_1_NodeId_sc_js_ymjs\\.shtml";

    public static final String URL_POST = "http://sc\\.zgny\\.com\\.cn/Tech_\\d{6}\\.shtml";

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setSleepTime(100)
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

    private static int size = 0;// 共抓取到的文章数量
    Technology technology = new Technology();

    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    @Override
    public void process(Page page) {
        //栽培技术列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            List<String> str = page.getHtml().links().regex(URL_POST).all();
            page.addTargetRequests(str);
            page.addTargetRequests(page.getHtml().links().regex(URL_LIST_ZP).all());
            //文章页
        } else {
            page.putField("name",page.getHtml().xpath("//div[@class='conLeft']/h1/text()"));
            page.putField("content",page.getHtml().xpath("//div[@class='conLeft']//div[@class='wenZi_02']/text()"));
//            page.putField("image",page.getHtml().xpath("//div[@class='conLeft']//div[@class='wenZi_02']/p[1]/img/@src"));
            page.putField("source",page.getHtml().xpath("//div[@class='conLeft']/p[1]/text()"));
            page.putField("category",page.getHtml().xpath("//div[@class='weiZhi']/text()"));
        }

        String source = page.getHtml().xpath("//div[@class='conLeft']/p[1]/text()").toString();

        //只保留文章来源
        if(source!=null){
            System.out.println("============:"+source.length());
            source = source.substring(38);
        }
        String name = page.getHtml().xpath("//div[@class='conLeft']/h1/text()").toString();
        String content = page.getHtml().xpath("//div[@class='conLeft']//div[@class='wenZi_02']").toString();
        if (content!=null){
            content = content.replace("<p>","");
            content = content.replace("</p>","");
            content = content.replace("<strong>","");
            content = content.replace("</strong>","");
            content = content.replace("</div>","");
            content = content.replace("<div class=\"wenZi_02\">","");
            content = content.replace(" <div style=\"COLOR: red\">","");
            content = content.replace("</div>","");
            content = content.replace("<span>","");
            content = content.replace("</span>","");
            content = content.replace("<span style=\"FONT-FAMILY: 宋体, SimSun\">","");
            content = content.replace("<span style=\"FONT-FAMILY: 宋体, SimSun; FONT-SIZE: 13px\">","");
            content = content.replace("<div class=\"TRS_Editor\">","");
            content = content.replace("&nbsp;","");
            content = content.replace("<div class=\"Custom_UnionStyle\">","");
            content = content.replace("<div id=\"fontzoom\" class=\"font_bottom\">","");
            content = content.replace("<p align=\"justify\">","");
            content = content.replace("<font face=\"宋体\">","");
            content = content.replace("<font face=\"Verdana\">","");
            content = content.replace("<font face=\"\">","");
            content = content.replace("</font>","");
            content = content.replace("<p style=\"TEXT-ALIGN: justify; TEXT-TRANSFORM: none; " +
                    "BACKGROUND-COLOR: rgb(255,255,255); MARGIN-TOP: 0px; TEXT-INDENT: 0px;" +
                    " PADDING-LEFT: 15px; LETTER-SPACING: normal; PADDING-RIGHT: 15px; FONT: 15px/26px Verdana, " +
                    "Arial, Helvetica, sans-serif; WHITE-SPACE: normal; COLOR: rgb(0,0,0); WORD-SPACING: 0px; " +
                    "-webkit-text-stroke-width: 0px\">","");
            content = content.replace("<ishtml>","");
            content = content.replace("</ishtml>","");
            content = content.replace("<span style=\"TEXT-ALIGN: justify; TEXT-TRANSFORM: none;" +
                    " BACKGROUND-COLOR: rgb(255,255,255); TEXT-INDENT: 0px; LETTER-SPACING: normal;" +
                    " DISPLAY: inline !important; FONT: 15px/26px 宋体; WHITE-SPACE: normal; FLOAT: none; COLOR: rgb(0,0,0);" +
                    " WORD-SPACING: 0px; -webkit-text-stroke-width: 0px\"> ","");


            content = content.replace("声明：本网部分文章转自互联网，如涉及第三方合法权利，请告知本网处理。电话：010-62110034","");
        }

//        System.out.println("content=========:"+content);
        String category = page.getHtml().xpath("//div[@class='weiZhi']/text()").toString();
        if (category!=null){
            category = category.substring(14);
        }
        technology.setCategory(category);
        technology.setName(name);
        technology.setContent(content);
        technology.setSource(source);
        technology.setPublish_time(new Date());
        page.putField("technology",technology);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
