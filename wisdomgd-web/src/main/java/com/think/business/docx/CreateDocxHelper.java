package com.think.business.docx;

import cn.hutool.core.util.StrUtil;
import com.think.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

@Slf4j
@Component
public class CreateDocxHelper {

    private MainDocumentPart mainDocumentPart;
    private ObjectFactory factory;

    public void createDocx(DocxVo docxVo, String docxPath) {
        WordprocessingMLPackage wordMLPackage = null;
        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();
        } catch (InvalidFormatException e) {
            log.error("生成word失败", e);
            throw new CustomException("生成word文档失败");
        }

        factory = Context.getWmlObjectFactory();
        mainDocumentPart = wordMLPackage.getMainDocumentPart();

        P title = createTitle(docxVo.getTitle());
        P subTitle = createSubTitle(docxVo.getSubTitle());
        Tbl table = createTable(docxVo.getRowList(), docxVo.getWitdh());
        mainDocumentPart.addObject(title);
        mainDocumentPart.addObject(subTitle);
        mainDocumentPart.addObject(table);
        try {
            wordMLPackage.save(new File(docxPath));
        } catch (Docx4JException e) {
            log.error("保存word失败", e);
            throw new CustomException("保存word文档失败");
        }
    }

    private P createTitle(String title) {
        P p = factory.createP();
        R run = factory.createR();
        RPr runProperties = factory.createRPr();

        Text text = factory.createText();
        text.setValue(title);
        run.getContent().add(text);
        // 字体
        RFonts rf = new RFonts();
        rf.setEastAsia("宋体");
        runProperties.setRFonts(rf);
        // 字号
        HpsMeasure size = new HpsMeasure();
        size.setVal(new BigInteger("50"));
        runProperties.setSz(size);
        runProperties.setSzCs(size);
        // 加粗
        BooleanDefaultTrue b = new BooleanDefaultTrue();
        b.setVal(true);
        runProperties.setB(b);
        // 水平居中
        PPr ppr = new PPr();
        Jc jc = new Jc();
        jc.setVal(JcEnumeration.CENTER);
        ppr.setJc(jc);
        p.setPPr(ppr);

        run.setRPr(runProperties);
        p.getContent().add(run);
        return p;
    }

    public P createSubTitle(String subTitle) {
        P p = factory.createP();
        R run = factory.createR();
        RPr runProperties = factory.createRPr();

        Text text = factory.createText();
        text.setValue(subTitle);
        run.getContent().add(text);
        // 水平居中
        PPr ppr = new PPr();
        Jc jc = new Jc();
        jc.setVal(JcEnumeration.CENTER);
        ppr.setJc(jc);
        p.setPPr(ppr);
        // 字体
        RFonts rf = new RFonts();
        rf.setEastAsia("宋体");
        runProperties.setRFonts(rf);
        run.setRPr(runProperties);
        p.getContent().add(run);
        return p;
    }

    public Tbl createTable(List<RowVo> rowList, String width) {
        Tbl table = factory.createTbl();
        rowList.forEach(r -> {
            Tr row = createRow(r.getCells());
            table.getContent().add(row);
        });
        // 表格宽度
        TblPr tblPr = new TblPr();
        TblWidth tblw = new TblWidth();
        tblw.setW(new BigInteger(width));
        tblw.setType("dxa");
        tblPr.setTblW(tblw);
        // 表格边框
        TblBorders borders = new TblBorders();
        CTBorder bd = new CTBorder();
        bd.setVal(STBorder.SINGLE);
        borders.setTop(bd);
        borders.setBottom(bd);
        borders.setLeft(bd);
        borders.setRight(bd);
        borders.setInsideH(bd);
        borders.setInsideV(bd);
        tblPr.setTblBorders(borders);

        table.setTblPr(tblPr);
        return table;
    }

    public Tr createRow(List<CellVo> cells) {
        Tr row = factory.createTr();
        cells.forEach(c -> {
            Tc cell = createCell(c);
            row.getContent().add(cell);
        });
        return row;
    }

    public Tc createCell(CellVo vo) {
        Tc cell = factory.createTc();
        P paragraph = factory.createP();
        Text text = factory.createText();
        text.setValue(vo.getText());
        R run = factory.createR();
        run.getContent().add(text);
        paragraph.getContent().add(run);

        TcPr tcPr = new TcPr();
        PPr ppr = new PPr();

        // 文本对齐类型
        if (StrUtil.isNotBlank(vo.getAlign())) {
            Jc textJc = new Jc();
            if ("center".equals(vo.getAlign())) {
                textJc.setVal(JcEnumeration.CENTER);
            } else if ("left".equals(vo.getAlign())) {
                textJc.setVal(JcEnumeration.LEFT);
            } else if ("right".equals(vo.getAlign())) {
                textJc.setVal(JcEnumeration.RIGHT);
            }
            ppr.setJc(textJc);
        }
        // 字体
        RPr rPr = new RPr();
        RFonts font = new RFonts();
        font.setEastAsia("楷体");
        HpsMeasure measure = new HpsMeasure();
        measure.setVal(new BigInteger("21"));
        rPr.setSz(measure);
        rPr.setSzCs(measure);
        rPr.setRFonts(font);
        run.setRPr(rPr);

        // 设置段后 0 磅
        PPrBase.Spacing spacing = new PPrBase.Spacing();
        spacing.setAfter(new BigInteger("0"));
        ppr.setSpacing(spacing);
        paragraph.setPPr(ppr);
        // 垂直居中
        CTVerticalJc verticalJc = new CTVerticalJc();
        verticalJc.setVal(STVerticalJc.CENTER);
        tcPr.setVAlign(verticalJc);
        // 设置宽度
        if (StrUtil.isNotBlank(vo.getWidth())) {
            TblWidth tblw = new TblWidth();
            tblw.setW(new BigInteger(vo.getWidth()));
            tcPr.setTcW(tblw);
        }
        // 设置背景颜色
        if (StrUtil.isNotBlank(vo.getColor())) {
            CTShd shd = new CTShd();
            shd.setColor("auto");
            shd.setFill(vo.getColor());
            tcPr.setShd(shd);
        }
        // 纵向合并单元格
        if (vo.isVMerge()) {
            TcPrInner.VMerge vMerge = new TcPrInner.VMerge();
            if (vo.isStartMerge()) {
                vMerge.setVal("restart");
            } else {
                vMerge.setVal("continue");
            }
            tcPr.setVMerge(vMerge);
        }
        // 横向合并单元格
        if (StrUtil.isNotBlank(vo.getGridSpan())) {
            TcPrInner.GridSpan span = new TcPrInner.GridSpan();
            span.setVal(new BigInteger(vo.getGridSpan()));
            tcPr.setGridSpan(span);
        }
        cell.setTcPr(tcPr);
        cell.getContent().add(paragraph);
        return cell;
    }
}
