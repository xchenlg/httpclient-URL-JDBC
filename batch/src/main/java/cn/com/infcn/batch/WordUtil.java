package cn.com.infcn.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import cn.com.infcn.batch.util.OfficeUtil;

public class WordUtil {

    private static String exceptionWord = "附注";
    private static String titles = "项目,附注,期末余额,期初余额,本期发生额,上期发生额,主要财务指标 ";

    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream(new File("C:\\Users\\chenlige\\Desktop\\pdf\\1.docx"));

        Map<String, List<Money>> map = new HashMap<>();

        XWPFDocument doc = new XWPFDocument(is);

        // List<IBodyElement> ele = doc.getBodyElements();
        //
        // for (IBodyElement iBodyElement : ele) {
        //
        //
        // if (BodyElementType.TABLE.equals(iBodyElement.getElementType())) {
        //
        // List<XWPFTable> tables = iBodyElement.getBody().getTables();
        // for(XWPFTable table : tables){
        // System.out.println(table.getText());
        // }
        // } else if
        // (BodyElementType.PARAGRAPH.equals(iBodyElement.getElementType())) {
        // List<XWPFParagraph> graphs = iBodyElement.getBody().getParagraphs();
        //
        // for (XWPFParagraph xwpfParagraph : graphs) {
        //
        // // xwpfParagraph.get
        //// System.out.println(xwpfParagraph.getStyle() + ":" +
        // xwpfParagraph.getText());
        // }
        // System.out.println("=====================================================");
        // }
        //
        // }

        // for (XWPFParagraph p : doc.getParagraphs()) {
        // System.out.println(p.getText());
        // }
        // return;
        // 获取文档中所有的表格

        List<XWPFTable> tables = doc.getTables();
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        Integer table_num = 0;
        for (XWPFTable table : tables) {

            if (table.getRow(0).getTableCells().size() <= 1) {
                continue;
            }

            // 是否有没用的列
            boolean isNullCol = false;
            rows = table.getRows();
            String firstrow = table.getText().substring(0, table.getText().indexOf("\t"));
            // 如果当前表格没有表头，找上一个的
            if (!titles.contains(table.getRow(0).getTableCells().get(0).getText()) && !firstrow.contains("年")) {
                isNullCol = findTitle(tables, table_num);
            } else if (table.getRow(0).getTableCells().size() > 1 && !firstrow.contains("年")) {
                isNullCol = exceptionWord.contains(table.getRow(0).getTableCells().get(1).getText());
            }
            table_num++;
            // 获取表格对应的行
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                // 获取行对应的单元格
                cells = row.getTableCells();

                String cell0 = cells.get(0).getText();
                // 基本每股收益
                calculateCell(map, "basicEarnings", cells, isNullCol, cell0, Type.TYPE_BASICEARNINGS);
                // 稀释每股收益
                calculateCell(map, "dilutedEarnings", cells, isNullCol, cell0, Type.TYPE_DILUTEDEARNINGS);
                // 加权平均净资产收益率
                calculateCell(map, "weightedAverage", cells, isNullCol, cell0, Type.TYPE_JQ);
                // 归属于上市公司股东的净利润
                calculateCell(map, "gs", cells, isNullCol, cell0, Type.TYPE_GS);
                // 营业总收入
                calculateCell(map, "zsr", cells, isNullCol, cell0, Type.TYPE_ZSR);
                // 营业总成本
                calculateCell(map, "zcb", cells, isNullCol, cell0, Type.TYPE_ZCB);
                // 营业利润
                calculateCell(map, "yylr", cells, isNullCol, cell0, Type.TYPE_YYLR);
                // 利润总额
                calculateCell(map, "lrze", cells, isNullCol, cell0, Type.TYPE_LRZE);
                // 净利润
                calculateCell(map, "jlr", cells, isNullCol, cell0, Type.TYPE_JLR);
                // 流动资产合计
                calculateCell(map, "ldzc", cells, isNullCol, cell0, Type.TYPE_LDZC);
                // 非流动资产合计
                calculateCell(map, "fldzc", cells, isNullCol, cell0, Type.TYPE_FLDZC);
                // 资产总计
                calculateCell(map, "zczj", cells, isNullCol, cell0, Type.TYPE_ZCZJ);
                // 流动负债
                calculateCell(map, "ldfz", cells, isNullCol, cell0, Type.TYPE_LDFZ);
                // 非流动负债
                calculateCell(map, "fldfz", cells, isNullCol, cell0, Type.TYPE_FLDFZ);
                // 负债合计
                calculateCell(map, "fzhj", cells, isNullCol, cell0, Type.TYPE_FZHJ);
                // 所有者权益合计
                calculateCell(map, "totalequity", cells, isNullCol, cell0, Type.TYPE_TOTALEQUITY);
                // 经营活动产生的现金流量净额
                calculateCell(map, "manage", cells, isNullCol, cell0, Type.TYPE_MANAGE);
                // 投资活动产生的现金流量净额
                calculateCell(map, "invest", cells, isNullCol, cell0, Type.TYPE_INVEST);
                // 筹资活动产生的现金流量净额
                calculateCell(map, "financing", cells, isNullCol, cell0, Type.TYPE_FINANCING);
                // 现金及现金等价物的净增加额
                calculateCell(map, "cash", cells, isNullCol, cell0, Type.TYPE_CASH);
            }
        }

        // for (Map.Entry<String, List<Money>> entry : map.entrySet()) {
        //
        // List<Money> list = entry.getValue();
        //
        // if (list.size() > 0) {
        //
        // Collections.sort(list, new Comparator<Money>() {
        //
        // @Override
        // public int compare(Money o1, Money o2) {
        // // 全词匹配的优先
        // if (o2.isIsequal() == true && o1.isIsequal() == false) {
        // return 1;
        // // 包含关键性词的优先
        // } else if (o2.isIscontainsKey() == true && o1.isIscontainsKey() ==
        // false) {
        // return 1;
        // } else if (o2.getMoney() < 0 && o1.getMoney() < 0) {
        // return o1.getMoney().compareTo(o2.getMoney());
        // } else {
        // return o2.getMoney().compareTo(o1.getMoney());
        // }
        // }
        // });
        //
        // DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//
        // 格式化设置
        //
        // System.out.println(entry.getKey() + "获取到的值为：" +
        // decimalFormat.format(list.get(0).getMoney()));
        // }
        // }

        FileOutputStream fo = new FileOutputStream("C:\\Users\\chenlige\\Desktop\\pdf\\1.xls");
        HSSFWorkbook work = OfficeUtil.generateExcel(map);
        work.write(fo);
        fo.close();

        doc.close();
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 循环找上个表的表头，确认是不是没用的列
     *
     * @param tables
     * @param table_num
     * @return
     */
    private static boolean findTitle(List<XWPFTable> tables, Integer table_num) {

        if (table_num <= 0) {
            return false;
        }

        // 表头匹配
        if (!titles.contains(tables.get(--table_num).getRow(0).getTableCells().get(0).getText())) {
            return findTitle(tables, table_num);
        }

        // 列明是否是没有用的头
        if (tables.get(table_num).getRow(0).getTableCells().size() > 1) {
            return exceptionWord.contains(tables.get(table_num).getRow(0).getTableCells().get(1).getText());
        }

        return false;
    }

    /**
     * 从表格中获取需要的内容
     *
     * @param basicEarnings
     * @param cells
     * @param isNullCol
     * @param cell0
     */
    private static void calculateCell(Map<String, List<Money>> map, String name, List<XWPFTableCell> cells,
            boolean isNullCol, String cell0, String content) {
        List<Money> list = new ArrayList<>();
        if (map.get(name) != null) {
            list = map.get(name);
        }
        if (cell0 != null && cell0.replace(" ", "").contains(content)) {
            // 如果是没有用的列，取下一列的值
            Money m = new Money();
            m.setIsequal(content.equals(cell0) || cell0.contains("（净亏损"));
            m.setIsjlr(cell0.contains("（净亏损"));
            m.setIscontainsKey(cell0.contains("(") || cell0.contains("/") || cell0.contains("（"));
            if (isNullCol && cells.size() > 2 && StringUtils.isNotBlank(cells.get(2).getText())) {
                try {
                    m.setMoney(new Double(cells.get(2).getText().replaceAll(",|%|）", "").trim()));
                    list.add(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (cells.size() > 1 && StringUtils.isNotBlank(cells.get(1).getText())) {
                try {
                    m.setMoney(new Double(cells.get(1).getText().replaceAll(",|%|）", "").trim()));
                    list.add(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        map.put(name, list);
    }

}
