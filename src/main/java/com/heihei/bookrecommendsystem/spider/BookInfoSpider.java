package com.heihei.bookrecommendsystem.spider;


import com.heihei.bookrecommendsystem.entity.BookDO;
import com.heihei.bookrecommendsystem.util.DBConnectionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.HashMap;

public class BookInfoSpider implements Runnable{
    private int curRetryTimes = 1;
    public static int maxRetryTimes = 3;
    private String bookInfoAddr = null;
    private String webAddr = null;
    private static HashMap<String,Integer> map = new HashMap<>();
    static {
        map.put("人文社科",1);
        map.put("小说",2);
        map.put("少儿",3);
        map.put("成功励志",4);
        map.put("教育考试",5);
        map.put("文学",6);
        map.put("杂志",7);
        map.put("漫画绘本",8);
        map.put("生活",9);
        map.put("科技科普",10);
        map.put("经济管理",11);
        map.put("艺术设计",12);
        map.put("计算机与互联网",13);
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
        Connection conn = null;
        while (this.curRetryTimes <= maxRetryTimes) {
            BookDO book = new BookDO();
            book.setInfoAddr(this.webAddr);
            //主键
            //查询是否存在
            try {
                initDoc(this.webAddr);
                System.out.println(doc.toString());
                System.out.println(doc.text());
                Elements classElements = doc.select("[class=breadcrumbs]");
                Elements e = classElements.select("[itemprop=name]");
                String className = e.get(1).text();
                Integer classId = map.get(className);
                book.setClassifyMainId(classId);
                Elements articleElements = doc.select("[class=article-profile-section article-profile-primary]");
                if (articleElements == null || articleElements.size() <= 0) {
                    break;
                }
                //获取img的地址
                Element elementDiv = articleElements.get(0);
                String imgAddr = elementDiv.select("img").attr("src");
                book.setImgAddr(imgAddr);
                String bookName = elementDiv.select("h1").text();
                book.setName(bookName);
                Elements infoElement = elementDiv.select("[class=article-meta]");
                Elements elements = infoElement.select("[class=author]");
                String author = elements.select("[class=labeled-text]").text();
                book.setAuthor(author);
                String translator = infoElement.select("[class=translator]").select("[class=labeled-text]").text();
                book.setTranslator(translator);
                String category = infoElement.select("[class=category]").select("[class=labeled-text]").text();
                book.setCategory(category);
                Elements pElements = infoElement.select("p");
                for (Element element : pElements) {
                    String tip = element.select("[class=label]").text();
                    if ("出版社".equals(tip)) {
                        String[] work = element.select("[class=labeled-text]").select("span").text().split("[ ]");
                        String publishHourse = null;
                        if(work.length >= 2){
                            publishHourse = work[0] + "/" + work[2];
                        }else{
                            publishHourse = work[0];
                        }
                        book.setPublishHourse(publishHourse);
                        continue;
                    }
                    if ("提供方".equals(tip)) {
                        String provider = null;
                        provider = element.select("[class=labeled-text]").select("a").text();
                        book.setProvider(provider);
                        continue;
                    }
                    if ("字数".equals(tip)) {
                        String[] wordCountsStr = null;
                        wordCountsStr = element.select("[class=labeled-text]").select("span").text().split("[ ]");
                        String words = wordCountsStr[1].replace(",", "");
                        int wordCount = Integer.valueOf(words);
                        book.setWordsCount(wordCount);
                        continue;
                    }
                    if ("ISBN".equals(tip)) {
                        String ISBN = null;
                        ISBN = element.select("[class=labeled-text]").select("a").text();
                        book.setISBN(ISBN);
                        continue;
                    }
                }
                //获取简介
                Elements bookInfoElements = doc.select("[class=article-profile-intro collapse-context]");
                Elements pInfoElements = bookInfoElements.select("p");
                StringBuffer sb = new StringBuffer();
                for (Element element: pInfoElements) {
                    String str = proventSQLInject(element.text());
                    sb.append(str+"\n");
                }
                book.setBookInfo(sb.toString());
                System.out.println(book.toString());
                conn = DBConnectionUtil.getConn();
                //插入数据库
                String sql = "INSERT INTO book(ISBN,`name`,author,translator,category,publish_hourse,provider,words_count,img_addr,info_addr,score_addr,book_info,avg_rating_val,classify_main_id,rate_count)"+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1,book.getISBN());
                preparedStatement.setString(2,book.getName());
                preparedStatement.setString(3,book.getAuthor());
                preparedStatement.setString(4,book.getTranslator());
                preparedStatement.setString(5,book.getCategory());
                preparedStatement.setString(6,book.getPublishHourse());
                preparedStatement.setString(7,book.getProvider());
                preparedStatement.setInt(8,book.getWordsCount());
                preparedStatement.setString(9,book.getImgAddr());
                preparedStatement.setString(10,book.getInfoAddr());
                preparedStatement.setString(11,book.getScoreAddr());
                preparedStatement.setString(12,book.getBookInfo());
                preparedStatement.setDouble(13,0.0);
                preparedStatement.setInt(14,book.getClassifyMainId());
                preparedStatement.setInt(15,0);
                preparedStatement.executeUpdate();
                System.out.println("爬取图书成功：" + book.toString());
            }catch (SQLException e) {
                curRetryTimes++;
                e.printStackTrace();
            }catch(Exception  e){
                curRetryTimes++;
                e.printStackTrace();
            }finally {
               try {
                   conn.close();
               }catch (Exception e) {
                   e.printStackTrace();
               }
            }
            if (doc == null) {
                curRetryTimes++;
                continue;
            }
        }
    }
    public String proventSQLInject(String str){
        if(str != null){
            String resultString = str.replace("'", "\\'").replace("`", "\\`").replace("\\", "\\\\").replace(";", "\\;");
            if(resultString.length()>=4096){
                resultString = resultString.substring(0, 4095);
            }
            return resultString;
        }
        return null;
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
        BookInfoSpider.maxRetryTimes = maxRetryTimes;
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
        BookInfoSpider.getHtmlOverTime = getHtmlOverTime;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
//        Connection conn = DBConnectionUtil.getConn();
//        if (conn == null) {
//            System.out.println("获取数据库连接失败");
//        }else{
//            System.out.println("获取数据库连接成功" + conn.toString());
//        }
        BookInfoSpider spider = new BookInfoSpider();
        spider.setWebAddr("https://read.douban.com/ebook/2015692/");
        spider.run();
    }
}
