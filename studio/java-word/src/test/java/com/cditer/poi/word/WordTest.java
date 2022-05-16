package com.cditer.poi.word;

import com.cditer.free.core.util.PkIdUtils;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;

public class WordTest {
    @Test
    public void testExportWord() throws Exception {
        createWordExport();
    }

    private void createWordExport() throws Exception {
        XWPFDocument doc = new XWPFDocument();
//        XWPFParagraph para;  //代表文档、表格、标题等种的段落，由多个XWPFRun组成
//        XWPFRun run;  //代表具有同样风格的一段文本
//        XWPFTableRow row;//代表表格的一行
//        XWPFTableCell cell;//代表表格的一个单元格
//        CTTcPr cellPr; //单元格属性


        XWPFParagraph paragraph = doc.createParagraph();//新建一个标题段落对象（就是一段文字）
        paragraph.setAlignment(ParagraphAlignment.LEFT);//样式居中
        XWPFRun coverRun0 = paragraph.createRun();    //创建文本对象
        coverRun0.setBold(true); //加粗
        coverRun0.setFontSize(12);    //字体大小
        coverRun0.setText("这是一个测试");

        saveDocToDisk(doc);
    }

    public void createHeader(XWPFDocument doc, String orgFullName, String logoFilePath) throws Exception {
        /* * 对页眉段落作处理，使公司logo图片在页眉左边，公司全称在页眉右边 * */
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = header.getParagraphArray(0);
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
//            String imgFile = FileUploadUtil.getLogoFilePath(logoFilePath);
//            byte[] bs = FtpUtil.downloadFileToIo(imgFile);
//            InputStream is = new ByteArrayInputStream(bs);
//
//            XWPFPicture picture = run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(80), Units.toEMU(45));
//
//            String blipID = "";
//            for(XWPFPictureData picturedata : header.getAllPackagePictures()) { //这段必须有，不然打开的logo图片不显示
//                blipID = header.getRelationId(picturedata);
//            }
//            picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
//            run.addTab();
//            is.close();
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
