package com.cditer.poi.word;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.cditer.export.word.model.QuestionPaper;
import com.cditer.export.word.model.Topic;
import com.cditer.free.core.util.PkIdUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToFoConverter;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WordTest {

    /**
     * Word转Fo
     * @throws Exception
     */
    @Test
    public void testWordToFo() throws Exception {
        InputStream is = new FileInputStream("E:\\document\\testfile\\export\\word\\test.doc");
        HWPFDocument wordDocument = new HWPFDocument(is);

        WordToFoConverter converter = new WordToFoConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

        //对HWPFDocument进行转换
        converter.processDocument(wordDocument);
        Writer writer = new FileWriter(new File("E:\\document\\testfile\\export\\word\\converter.xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty( OutputKeys.ENCODING, "utf-8" );
        //是否添加空格
        transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
//     transformer.setOutputProperty( OutputKeys.METHOD, "html" );
        transformer.transform(
                new DOMSource(converter.getDocument() ),
                new StreamResult( writer ) );
    }

    private List<Topic> queryTopics() {
        String path = WordTest.class.getClassLoader().getResource(String.format("question.json")).getPath();
        String navJson = FileUtil.readString(path, Charset.defaultCharset());
        List<Topic> topics = JSONUtil.toList(JSONUtil.parseArray(navJson), Topic.class);

        return topics;
    }

    private QuestionPaper buildPager() {
        QuestionPaper pager = new QuestionPaper();
        pager.setTitle("期末考试严选测试");
        pager.setSubTitle("数学");
        pager.setHeaderTitle("熟尔教育");

        List<Topic> topics = queryTopics();
        topics.forEach(consumerWithIndex((item, index) -> {
            item.setSeq(++index);
        }));
        pager.setTopics(topics);

        return pager;
    }

    // 工具方法
    public static <T> Consumer<T> consumerWithIndex(BiConsumer<T, Integer> consumer) {
        class Obj {
            int i;
        }
        Obj obj = new Obj();
        return t -> {
            int index = obj.i++;
            consumer.accept(t, index);
        };
    }

    @Test
    public void testExportWord() throws Exception {
        createWordExport(buildPager());
    }

    private void createWordExport(QuestionPaper pager) throws Exception {
        List<Topic> topics = pager.getTopics();
        if (CollectionUtils.isEmpty(topics)) {
            return;
        }
        XWPFDocument doc = new XWPFDocument();
        createHeader(doc, pager.getHeaderTitle());

        insertTitle(doc, pager.getTitle());

        for (Topic topic : topics) {
            createTopic(topic, doc);
        }

        saveDocToDisk(doc);
    }

    private void createTopic(Topic topic, XWPFDocument doc) {
        XWPFParagraph paragraph = doc.createParagraph();//新建一个标题段落对象（就是一段文字）
        paragraph.setAlignment(ParagraphAlignment.LEFT);//样式居中
        XWPFRun coverRun0 = paragraph.createRun();    //创建文本对象
        coverRun0.setBold(true); //加粗
        coverRun0.setFontSize(12);    //字体大小
        coverRun0.setText(String.format("%d. %s", topic.getSeq(), topic.getTitle()));

        XWPFRun separtor = paragraph.createRun();
        /**两段之间添加换行*/
        separtor.setText("\r");

        XWPFRun separtor2 = paragraph.createRun();
        /**两段之间添加换行*/
        separtor2.setText("\r");
    }


    //插入大标题
    public void insertTitle(XWPFDocument doc, String title) {
        XWPFParagraph paragraph = doc.createParagraph();

        /**3.插入新的Run即将新的文本插入段落*/
        XWPFRun createRun = paragraph.insertNewRun(0);
        createRun.setStyle("标题");
        createRun.setText(title);
        XWPFRun separtor = paragraph.insertNewRun(1);
        /**两段之间添加换行*/
        separtor.setText("\r\n");
        //设置字体大小
        createRun.setFontSize(22);
        //是否加粗
        createRun.setBold(true);
        //设置字体
        createRun.setFontFamily("宋体");
        //设置居中
        paragraph.setAlignment(ParagraphAlignment.CENTER);
    }

    public void createHeader(XWPFDocument doc, String orgFullName) throws Exception {
        createHeader(doc, orgFullName, null);
    }

    public void createHeader(XWPFDocument doc, String orgFullName, String logoFilePath) throws Exception {
        /* * 对页眉段落作处理，使公司logo图片在页眉左边，公司全称在页眉右边 * */
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = header.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setBorderBottom(Borders.THICK);

        CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
        tabStop.setVal(STTabJc.RIGHT);
        int twipsPerInch = 1440;
        tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

        XWPFRun run = paragraph.createRun();
        setXWPFRunStyle(run, "新宋体", 10);

        /* * 根据公司logo在ftp上的路径获取到公司到图片字节流 * 添加公司logo到页眉，logo在左边 * */
        if (StringUtils.hasText(logoFilePath)) {

        }

        /* * 添加字体页眉，公司全称 * 公司全称在右边 * */
        if (StringUtils.hasText(orgFullName)) {
            run = paragraph.createRun();
            run.setText(orgFullName);
            setXWPFRunStyle(run, "新宋体", 10);
        }
    }

    private void setXWPFRunStyle(XWPFRun r1, String font, int fontSize) {
        r1.setFontSize(fontSize);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setEastAsia(font);
        fonts.setHAnsi(font);
    }

    private void saveDocToDisk(XWPFDocument doc) {
        String fileName = PkIdUtils.getId() + ".doc";
        String rootPath = "E:\\document\\testfile\\export\\word";
        String path = rootPath + File.separator + fileName;
        File storeDirectory = new File(rootPath);
        if (!storeDirectory.exists() && !storeDirectory.isDirectory()) {
            storeDirectory.mkdirs();
        }
        File file = new File(path);
        try {
            FileOutputStream os = new FileOutputStream(file);
            doc.write(os);
        } catch (Exception ex) {

        }
    }
}
