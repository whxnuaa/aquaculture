package com.jit.aquaculture.spider;


import com.jit.aquaculture.domain.knowledge.Fertilizer;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class FertilizerProcessor implements PageProcessor {

    //详情URL
    private static final String URL_POST = "http://feiliao\\.aweb\\.com\\.cn/pzcpdq-1-\\d+\\.shtml";
    //起始URL
    private static final String BASE_URL = "http://feiliao\\.aweb\\.com\\.cn/pzcpdq\\.shtml";


    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setSleepTime(5000)
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");

    @Override
    public void process(Page page) {
        //获取肥料详情的url地址
        List<Selectable> list = page.getHtml().xpath("//div[@class='content']/div[@class='newsList']/ul/li").nodes();

        if (list.size() == 0){
            //如果为空，表示这是肥料详情页，获取肥料相关信息，保存数据
            this.saveFertilizer(page);
        }else {
            //如果不为空，表示是列表页，解析出详情url地址，放到任务队列中
            for (Selectable selectable: list){
                String detailUrl = selectable.links().toString();
                System.out.println("detailUrl " + detailUrl);
                page.addTargetRequest(detailUrl);
            }
        }
//        String nextUrl = page.getHtml().xpath("//ul[@class='pages']/li/a[@href]").nodes().get(1).links().toString();
//        System.out.println("nextUrl " + nextUrl);
//        page.addTargetRequest(nextUrl);

    }

//    private void getTime(Selectable selectable){
//        String timeString = selectable.xpath("//div[#id='list']/div[@class='newsList']/ul/li/span[@class='right']").toString();
//        System.out.println("time String" + timeString);
//        String str = new java.text.SimpleDateFormat("yyyy年MM月dd日").format(timeString);
//        String time = str.substring(0,4) + "-" + str.substring(str.indexOf("年")+1,str.indexOf("月")) + "-"
//                + str.substring(str.indexOf("月")+1,str.indexOf("日"));
//        System.out.println(time);
//        try {
//            fertilizer.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    private void saveFertilizer(Page page){
        Fertilizer fertilizer = new Fertilizer();
        Html html = page.getHtml();
        fertilizer.setName(html.xpath("//*[@id=\"content\"]/div[1]/div[1]/div/ul/li[1]/text()").toString().trim());
        fertilizer.setCompany(html.xpath("//*[@id=\"content\"]/div[1]/div[1]/div/ul/li[2]/text()").toString());
        String type = html.xpath("//*[@id=\"content\"]/div[1]/div[1]/div/ul/li[3]/text()").toString();
        fertilizer.setType(type.trim());
//        System.out.println("name "+ html.xpath("//*[@id=\"content\"]/div[1]/div[1]/div/ul/li[1]/text()").toString());
        Document doc = html.getDocument();
        String content = doc.getElementById("content").getElementsByTag("p").text();
        fertilizer.setContent(content);
        List<Element> elements = doc.getElementById("content").getElementsByTag("p");
        if (!elements.get(elements.size()-1).hasText()){
            fertilizer.setCrop_use(elements.get(elements.size()-2).text());
        }
        fertilizer.setCrop_use(elements.get(elements.size()-1).text());
        page.putField("fertilizer",fertilizer);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
