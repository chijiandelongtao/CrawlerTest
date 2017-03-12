import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by zhuqiuhui on 2017/3/4.
 */
public class TestCSDN implements PageProcessor{
    //博客列表
    public static final String URL_LIST = "http://blog\\.csdn\\.net/zhuqiuhui/article/list/\\d+";
    //具体博客
    public static final String URL_POST = "http://blog\\.csdn\\.net/zhuqiuhui/article/details/\\d+";

//    private Site site = Site
//            .me()
//            .setDomain("blog.csdn.net")
//            .setSleepTime(3000)
//            .setUserAgent(
//                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private Site site = Site
            .me()
            .setRetryTimes(3)
            .setSleepTime(3000);

    public void process(Page page) {
        if (page.getUrl().regex(URL_LIST).match()) {
            //选取含有属性class是"articleList"的所有div结点，获取文章链接
            page.addTargetRequests(page.getHtml().xpath("//div[@id='article_list']").links().regex(URL_POST).all());
            //获取其他文章列表
            page.addTargetRequests(page.getHtml().xpath("//div[@id='papelist']").links().regex(URL_LIST).all());
        } else {
            //文章页
            page.putField("title", page.getHtml().xpath("//div[@class='article_title']//span[@class='link_title']/a/text()"));
//            page.putField("content", page.getHtml().xpath("//div[@id='article_details']//div[@class='article_content']"));
//            page.putField("date", page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new TestCSDN()).addUrl("http://blog.csdn.net/zhuqiuhui/article/list/2")
                .run();
    }
}
