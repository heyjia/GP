package com.heihei.bookrecommendsystem.spider;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookPageSpider implements Runnable{
    private int curRetryTimes = 1;
    public static int maxRetryTimes = 3;
    private String bookInfoAddr = null;
    private String webAddr = null;
    private int pageNums = 5;
    private static final String baseWebAddr = "https://read.douban.com/category?";
    public static Map<String,Integer> map = new HashMap<String,Integer>();

    static {
        map.put("小说",101);
        map.put("文学",101);
        map.put("人文社科",102);
        map.put("经济管理",103);
        map.put("科技科普",104);
        map.put("计算机与互联网",105);
        map.put("成功励志",106);
        map.put("生活",107);
        map.put("少儿",108);
        map.put("艺术设计",109);
        map.put("漫画绘本",110);
        map.put("教育考试",111);
        map.put("杂志",2);
        map.put("英文出版",300);
    }
    public BookPageSpider() {
    }

    public int getCurRetryTimes() {
        return curRetryTimes;
    }

    public void setCurRetryTimes(int curRetryTimes) {
        this.curRetryTimes = curRetryTimes;
    }

    public static int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public static void setMaxRetryTimes(int maxRetryTimes) {
        BookPageSpider.maxRetryTimes = maxRetryTimes;
    }

    public String getBookInfoAddr() {
        return bookInfoAddr;
    }

    public void setBookInfoAddr(String bookInfoAddr) {
        this.bookInfoAddr = bookInfoAddr;
    }

    public String getWebAddr() {
        return webAddr;
    }

    public void setWebAddr(String webAddr) {
        this.webAddr = webAddr;
    }

    public static int getGetHtmlOverTime() {
        return getHtmlOverTime;
    }

    public static void setGetHtmlOverTime(int getHtmlOverTime) {
        BookPageSpider.getHtmlOverTime = getHtmlOverTime;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    /**
     * 页面获取超时时间MS
     */
    public static int getHtmlOverTime = 20 * 1000;
    //详情页结构化html文件
    private transient Document doc = null;

    //将页面转换成document
    private Document getHtmlDoc(String webAddr) throws IOException {
        return Jsoup.connect(webAddr).userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0").timeout(getHtmlOverTime).get();
    }
    public void initDoc (String webAddr) throws IOException {
        this.doc = this.getHtmlDoc(webAddr);
    }

    @Override
    public void run() {
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Integer kind = (Integer) entry.getValue();
            System.out.println("kend = " + kind);
            webAddr = baseWebAddr + "kind=" + kind;
            String tempAddr = "";
            //每种种类遍历5页
            for (int i = 1;i <=5;i++) {
                tempAddr = webAddr;
                tempAddr += "&page=" + i;
                System.out.println("查找页面地址为：" + tempAddr);
                try {
                    WebClient webClient = new WebClient(BrowserVersion.FIREFOX_68);
                    webClient.setJavaScriptErrorListener(new MyJSErrorListener());
                    webClient.getOptions().setUseInsecureSSL(true);
                    webClient.getOptions().setJavaScriptEnabled(true);
                    webClient.getOptions().setCssEnabled(false);
                    webClient.getOptions().setThrowExceptionOnScriptError(false);
                    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
                    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
                    webClient.getOptions().setActiveXNative(false);
                    webClient.getOptions().setTimeout(10*1000);
                    HtmlPage page = webClient.getPage(tempAddr);
                    webClient.waitForBackgroundJavaScript(20*1000*1000);
                    String pageAsXml = page.asXml();
                    System.out.println(pageAsXml);
                    doc = Jsoup.parse(pageAsXml);
                    Elements elementH4 = doc.select("h4");
                    for (Element element : elementH4) {
                        String href = element.select("a").attr("href");
                        BookInfoSpider spider = new BookInfoSpider();
                        spider.setWebAddr("https://read.douban.com"+href);
                        spider.run();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BookPageSpider spider = new BookPageSpider();
        spider.run();
    }
}
