package com.jit.aquaculture.spider;



import com.jit.aquaculture.domain.knowledge.Pesticide;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class PesticideProcessor implements PageProcessor {

    //起始URL
    private static final String BASE_URL = "http://tupu\\.zgny\\.com\\.cn/list_nytp.aspx";
    //详情URL
    private static final String URL_POST = "http://www\\.zgny\\.com\\.cn/ifm/tech/\\d{4}-\\d{1,2}-\\d{1,2}/\\d{5}\\.shtml";
    //分类URL
    private static final String CATEGORY_PAGE_URL = "http://tupu\\.zgny\\.com\\.cn/nongyaotp_js_\\w+/List\\.shtml";

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setSleepTime(1000)
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");


    Pesticide pesticide = new Pesticide();

    @Override
    public void process(us.codecraft.webmagic.Page page) {
        Selectable url = page.getUrl();
        if (url.regex(BASE_URL).match()){
            processCategory(page);
        }else if (url.regex(CATEGORY_PAGE_URL).match()){
            processList(page);
        }else if(url.regex(URL_POST).match()){
            processDetail(page);
        }else {
            page.setSkip(true);
        }
    }

    //获取分类及url地址
    public void processCategory(Page page){
        List<Selectable> typeList = page.getHtml().xpath("//div[@class='bigzhans_list_right_txt']/ul[@class='mc3']/li").nodes();
        for (Selectable s: typeList){
            String type = s.xpath("//a/text()").toString();
            String detailUrl = s.xpath("//a[@href]").links().toString();
            Request request = new Request(detailUrl).putExtra("type",type);
            page.addTargetRequest(request);
        }
    }

    public void processList(Page page){
        List<String> urls = page.getHtml().links().regex(URL_POST).all();
        for (int i=0;i<urls.size();i++){
            Request request = new Request(urls.get(i)).putExtra("type",page.getRequest().getExtra("type"));
//            System.out.println(request);
            page.addTargetRequest(request);
        }
    }

    //爬取具体的信息过程
    public void processDetail(Page page){
        String  type = page.getRequest().getExtra("type").toString();
        pesticide.setType(type.trim());
        String content = null;
        String contentString = page.getHtml().xpath("//*[@id=\"Center\"]/div[2]/div[1]/div[1]").toString();
        if (contentString!=null){
             content = contentString .replaceAll("<div class=\"wenZi_02\">","")
                     .replaceAll("</div>","")
                     .replaceAll("<br>","")
                     .replaceAll("<p>","").replaceAll("</p>","");
        }
        pesticide.setContent(content);
        pesticide.setName(page.getHtml().xpath("//div[@class='conLeft']/h1/text()").toString());
        String from = null;
        String fromString = page.getHtml().xpath("//div[@class='conLeft']/p[@class='xinXi']/text()").toString();
        if(fromString!=null){
            String[] s = fromString.split( " ");
            for (int i =0; i<s.length;i++){
                if (s[i].contains("信息来源")){
                    from = s[i].split("：")[1];
                }
            }
        }
        pesticide.setFromSource(from);
//        System.out.println(pesticide);
        page.putField("pesticide",pesticide);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
