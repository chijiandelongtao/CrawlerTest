import org.apache.log4j.BasicConfigurator;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by zhuqiuhui on 2017/3/4.
 */
public class GithubRepoPageProcessor implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);



    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());

    }

    //@Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").addPipeline(new JsonFilePipeline("/")).thread(5).run();
//        Spider.create(new GithubRepoPageProcessor()).addUrl("https://book.sankuai.com/#/ebooks/1").thread(5).run();

    }

}
