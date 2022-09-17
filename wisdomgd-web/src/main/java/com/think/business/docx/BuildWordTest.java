package com.think.business.docx;

import java.util.ArrayList;
import java.util.List;

public class BuildWordTest {


    public static void main(String[] args) {
        BuildWordTest bt = new BuildWordTest();
        CreateDocxHelper helper = new CreateDocxHelper();
        String path = "D:\\docx\\" + System.currentTimeMillis() + ".docx";

        helper.createDocx(bt.createData(), path);
    }

    private DocxVo createData () {
        List<RowVo> rowList = new ArrayList<>();
        RowVo row = new RowVo();
        List<CellVo> list = new ArrayList<>();
        list.add(new CellVo().setText("舆情信息").setWidth("761").setAlign("center").setVMerge(true).setStartMerge(true));
        list.add(new CellVo().setText("标题").setWidth("1332").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("《小朋友中午未午睡被老师要求自扇耳光》").setWidth("7135").setGridSpan("4"));
        row.setCells(list);
        rowList.add(row);

        row = new RowVo();
        list = new ArrayList<>();
        list.add(new CellVo().setText("").setWidth("761").setVMerge(true));
        list.add(new CellVo().setText("日期").setWidth("1332").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("2019年12月23日").setWidth("7135").setGridSpan("4"));
        row.setCells(list);
        rowList.add(row);

        row = new RowVo();
        list = new ArrayList<>();
        list.add(new CellVo().setText("").setWidth("761").setVMerge(true));
        list.add(new CellVo().setText("数据来源").setWidth("1332").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("微博").setWidth("7135").setGridSpan("4"));
        row.setCells(list);
        rowList.add(row);

        row = new RowVo();
        list = new ArrayList<>();
        list.add(new CellVo().setText("").setWidth("761").setVMerge(true));
        list.add(new CellVo().setText("舆情类别").setWidth("1332").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("突发事件").setWidth("7135").setGridSpan("4"));
        row.setCells(list);
        rowList.add(row);

        row = new RowVo();
        list = new ArrayList<>();
        list.add(new CellVo().setText("").setWidth("761").setVMerge(true));
        list.add(new CellVo().setText("主要内容").setWidth("1332").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("转发微博索拉卡打飞机爱丽丝的甲方拉水电费按时地方拉三等奖发牢骚地方了sad发了啥地方").setWidth("7135").setGridSpan("4"));
        row.setCells(list);
        rowList.add(row);

        row = new RowVo();
        list = new ArrayList<>();
        list.add(new CellVo().setText("处置流程").setWidth("9227").setAlign("center").setColor("C7DAF1").setGridSpan("6"));
        row.setCells(list);
        rowList.add(row);

        row = new RowVo();
        list = new ArrayList<>();
        list.add(new CellVo().setText("序号").setWidth("761").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("时间").setWidth("1332").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("流程节点").setWidth("1134").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("操作").setWidth("709").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("接收单位").setWidth("1134").setAlign("center").setColor("D7D7D7"));
        list.add(new CellVo().setText("说明").setAlign("center").setColor("D7D7D7").setWidth("4158"));
        row.setCells(list);
        rowList.add(row);

        row = new RowVo();
        list = new ArrayList<>();
        list.add(new CellVo().setText("1").setWidth("761").setAlign("center"));
        list.add(new CellVo().setText("2020-02-02 23:23:12").setWidth("1332"));
        list.add(new CellVo().setText("江苏省教育厅").setWidth("1134"));
        list.add(new CellVo().setText("分发").setWidth("709"));
        list.add(new CellVo().setText("南京市教育局").setWidth("1134"));
        list.add(new CellVo().setText("处置说明四六级发牢骚剪短发洛杉矶的福利卡坚实的罚款水电费拉会计师对伐啦就是登陆发觉上当了开发健身房\r附件：").setWidth("4158"));
        row.setCells(list);
        rowList.add(row);

        row = new RowVo();
        list = new ArrayList<>();
        list.add(new CellVo().setText("2").setWidth("761").setAlign("center"));
        list.add(new CellVo().setText("2020-02-04 23:23:12").setWidth("1332"));
        list.add(new CellVo().setText("南京市教育局").setWidth("1134"));
        list.add(new CellVo().setText("处置").setWidth("709"));
        list.add(new CellVo().setText("江苏省教育厅").setWidth("1134"));
        list.add(new CellVo().setText("处置说明四六级发牢骚剪短发洛杉矶的福利卡坚实的罚款水电费拉会计师对伐啦就是登陆发觉上当了开发健身房").setWidth("4158"));
        row.setCells(list);
        rowList.add(row);

        DocxVo vo = new DocxVo();
        vo.setRowList(rowList).setTitle("舆情通知单").setSubTitle("江苏省教育厅办公室                                                                                2020年2月12日")
                .setWitdh("9227");
        return vo;
    }
}