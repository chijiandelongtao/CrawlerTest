import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * Created by zhuqiuhui on 2017/3/4.
 */
public class TestEbook implements PageProcessor {
    //    public static final String Ebook_POST = "https://api-mit\\.sankuai\\.com/library/ebook/105.*";
    private Site site = Site
            .me()
            .setSleepTime(3000)
//        .addCookie()
            .addHeader("authorization", "Bearer eyJ0eXAiOiJKbGciOiJIUzI1NiJ9.eODAwMSwic3NvX2lkIjoyMDY2NjM1LCJsb2dpbiI6InpoWh1aSIsIm5hbWUiOiLmnLHnp4vovokiLCJleHBpcmVzX2F0IjoxNDg5NzQ5MDAyLCJpYXQiOjE0ODg1Mzk0M7fCnZDxoSxSMUkT5kt7ujtgn7oG0K3Ec1M")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

    public void process(Page page) {
        String ebookUrl = new JsonPathSelector("$.url").select(page.getRawText());
//        System.out.println("ebookUrl: " + ebookUrl);
        page.putField("ebook_url", ebookUrl);


//      从资源中download pdf
        GetPdf getPdf = new GetPdf();
        String fileName = getPdf.getFileName(ebookUrl) + ".pdf";
        try {
            getPdf.downLoadPdf(ebookUrl, fileName, "/Users/zhuqiuhui/zqh/Project/pdf");
            System.out.println("下载：" + fileName + " 完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        BasicConfigurator.configure();
//        for (int i = 1; i <= 107; ++i) {
            int i = 11;
            System.out.println("第 " + i + "本开始下载......");
            Spider.create(new TestEbook()).addUrl("https://api-mit.sankuai.com/library/ebook/" + String.valueOf(i) + "?_include%5B0%5D%5Bassociation%5D=book")
                    .run();
        }
//    }
}
