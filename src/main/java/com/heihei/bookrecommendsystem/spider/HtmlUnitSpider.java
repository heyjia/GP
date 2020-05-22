package com.heihei.bookrecommendsystem.spider;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class HtmlUnitSpider {
    public static void main(String[] args) throws IOException {
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
        HtmlPage page = webClient.getPage("https://read.douban.com/category?kind=100");
        webClient.waitForBackgroundJavaScript(20*1000*1000);
        String pageAsXml = page.asXml();
        System.out.println(pageAsXml);
//        Document doc = Jsoup.parse(pageAsXml);
//        Elements a = doc.select("[class=info]");
//        System.out.println(a.size());
    }
}
