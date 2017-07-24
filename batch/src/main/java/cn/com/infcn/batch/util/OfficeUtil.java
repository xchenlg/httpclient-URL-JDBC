package cn.com.infcn.batch.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import cn.com.infcn.batch.Money;

/**
 * 关于office的帮助类<br>
 * 提供了word和excel的生成功能
 * 
 * @author NOLY DAKE
 * 
 */
public class OfficeUtil {

    /**
     * 将图书信息生成到excel中
     * 
     * @param map
     * 
     * @return
     * @throws IOException
     */
    public static HSSFWorkbook generateExcel(Map<String, List<Money>> map) throws IOException {

        HSSFWorkbook doc = new HSSFWorkbook();
        // 样式1
        HSSFFont titleFont = doc.createFont();
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        HSSFCellStyle titleCellStyle = doc.createCellStyle();
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
        titleCellStyle.setWrapText(false);
        warpCellStyleButton(titleCellStyle); // 为单元格添加边框
        // -----------------------标题的样式开始
        HSSFCellStyle normalCellStyle = doc.createCellStyle();
        normalCellStyle.setWrapText(false);
        normalCellStyle.setVerticalAlignment((short) 1);
        warpCellStyleButton(normalCellStyle); // 为单元格添加边框
        // -----------------------标题的样式结束

        HSSFSheet sheet = doc.createSheet("财务情况");
        sheet.setColumnWidth((short) 0, (short) 9000);
        sheet.setColumnWidth((short) 1, (short) 6000);
        sheet.setColumnWidth((short) 2, (short) 7000);
        // ---------------------------------初始化标题栏开始
        HSSFRow titleRowRow = sheet.createRow(0);
        titleRowRow.setHeight((short) 400);//
        createStyleCell(titleRowRow, 0, titleCellStyle).setCellValue("项目");
        createStyleCell(titleRowRow, 1, titleCellStyle).setCellValue("金额");
        createStyleCell(titleRowRow, 2, titleCellStyle).setCellValue("来源");

        HSSFRow row = sheet.createRow(1);
        row.setHeight((short) 400);//
        createStyleCell(row, 0, normalCellStyle).setCellValue("基本");

        createRow(map, normalCellStyle, sheet, 2, "基本每股收益", "basicEarnings", "主要会计数据和财务指标（表）");
        createRow(map, normalCellStyle, sheet, 3, "稀释每股收益", "dilutedEarnings", "主要会计数据和财务指标（表）");
        createRow(map, normalCellStyle, sheet, 4, "加权平均净资产收益率(%)", "weightedAverage", "主要会计数据和财务指标（表）");
        createRow(map, normalCellStyle, sheet, 5, "归属于上市公司股东的净利润", "gs", "主要会计数据和财务指标（表）");

        HSSFRow row6 = sheet.createRow(6);
        row6.setHeight((short) 400);//
        createStyleCell(row6, 0, normalCellStyle).setCellValue("毛利率");

        // 计算毛利率
        String zsr = getMoneyValue(map, "zsr");
        String zcb = getMoneyValue(map, "zcb");
        BigDecimal sr = new BigDecimal(zsr.replace(",", ""));
        BigDecimal cb = new BigDecimal(zcb.replace(",", ""));
        BigDecimal yl = sr.subtract(cb).divide(sr, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));

        createStyleCell(row6, 1, normalCellStyle).setCellValue(yl.setScale(2, BigDecimal.ROUND_HALF_UP) + "%");
        createStyleCell(row6, 2, normalCellStyle).setCellValue("主要会计数据和财务指标（表）");

        HSSFRow row7 = sheet.createRow(7);
        row7.setHeight((short) 400);//
        createStyleCell(row7, 0, normalCellStyle).setCellValue("利润");

        createRow(map, normalCellStyle, sheet, 8, "营业总收入", "zsr", "利润（表）");
        createRow(map, normalCellStyle, sheet, 9, "营业总成本", "zcb", "利润（表）");
        createRow(map, normalCellStyle, sheet, 10, "营业利润", "yylr", "利润（表）");
        createRow(map, normalCellStyle, sheet, 11, "利润总额", "lrze", "利润（表）");
        createRow(map, normalCellStyle, sheet, 12, "净利润", "jlr", "利润（表）");

        HSSFRow row13 = sheet.createRow(13);
        row13.setHeight((short) 400);
        createStyleCell(row13, 0, normalCellStyle).setCellValue("资产负债");

        createRow(map, normalCellStyle, sheet, 14, "流动资产合计", "ldzc", "资产负债（表）");
        createRow(map, normalCellStyle, sheet, 15, "非流动资产合计", "fldzc", "资产负债（表）");
        createRow(map, normalCellStyle, sheet, 16, "资产总计", "zczj", "资产负债（表）");
        createRow(map, normalCellStyle, sheet, 17, "流动负债", "ldfz", "资产负债（表）");
        createRow(map, normalCellStyle, sheet, 18, "非流动负债", "fldfz", "资产负债（表）");
        createRow(map, normalCellStyle, sheet, 19, "负债合计", "fzhj", "资产负债（表）");
        createRow(map, normalCellStyle, sheet, 20, "所有者权益合计", "totalequity", "资产负债（表）");

        HSSFRow row21 = sheet.createRow(21);
        row21.setHeight((short) 400);
        createStyleCell(row21, 0, normalCellStyle).setCellValue("现金流量");
        createRow(map, normalCellStyle, sheet, 22, "经营活动产生的现金流量净额", "manage", "现金流量（表）");
        createRow(map, normalCellStyle, sheet, 23, "投资活动产生的现金流量净额", "invest", "现金流量（表）");
        createRow(map, normalCellStyle, sheet, 24, "筹资活动产生的现金流量净额", "financing", "现金流量（表）");
        createRow(map, normalCellStyle, sheet, 25, "现金及现金等价物净增加额", "cash", "现金流量（表）");

        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(2, 6, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(8, 12, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(13, 13, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(14, 20, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(21, 21, 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(22, 25, 2, 2));

        // ---------------------------------初始化标题栏结束
        return doc;
    }

    private static void createRow(Map<String, List<Money>> map, HSSFCellStyle normalCellStyle, HSSFSheet sheet,
            Integer num, String first, String key, String three) {
        HSSFRow row = sheet.createRow(num);
        row.setHeight((short) 400);
        createStyleCell(row, 0, normalCellStyle).setCellValue(first);
        createStyleCell(row, 1, normalCellStyle).setCellValue(nullToSpace(getMoneyValue(map, key)));
        createStyleCell(row, 2, normalCellStyle).setCellValue(three);
    }

    private static String getMoneyValue(Map<String, List<Money>> map, String key) {

        List<Money> list = map.get(key);

        if (list.size() > 0) {

            Collections.sort(list, new Comparator<Money>() {

                @Override
                public int compare(Money o1, Money o2) {
                    // 全词匹配的优先
                    if (o2.isIsequal() == true && o1.isIsequal() == false && o1.isIscontainsKey() == false) {
                        return 1;
                    } else if (o1.isIsequal() == true && o2.isIsequal() == false && o2.isIscontainsKey() == false) {
                        return -1;
                    } else if (o2.isIsjlr() == true && o1.isIsjlr() == false) {
                        return 1;
                    } else if (o2.isIsjlr() == false && o1.isIsjlr() == true) {
                        return -1;
                    } else if (o2.isIscontainsKey() == true && o1.isIscontainsKey() == false) {
                        return 1;
                    } else if (o1.isIscontainsKey() == true && o2.isIscontainsKey() == false) {
                        return -1;
                    } else if (o2.getMoney() < 0 && o1.getMoney() < 0) {
                        return o1.getMoney().compareTo(o2.getMoney());
                    } else {
                        return o2.getMoney().compareTo(o1.getMoney());
                    }
                }
            });

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");// 格式化设置

            return decimalFormat.format(list.get(0).getMoney());
        }

        return "";
    }

    /**
     * 将null的字符转换为空格
     * 
     * @param value
     * @return
     */
    private static String nullToSpace(String value) {
        return value == null ? " " : value;
    }

    /**
     * 创建的带样式的单元格
     * 
     * @param row
     *            要创建的行
     * @param i
     *            第几列，从0开始
     * @param titleCellStyle
     *            单元格的样式
     * @return 单元格对象
     */
    private static HSSFCell createStyleCell(HSSFRow row, int i, HSSFCellStyle titleCellStyle) {
        HSSFCell cell = row.createCell(i);
        cell.setCellStyle(titleCellStyle);
        return cell;
    }

    /**
     * 为单元格样式添加边框
     * 
     * @param titleCellStyle
     */
    private static void warpCellStyleButton(HSSFCellStyle titleCellStyle) {
        titleCellStyle.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
        titleCellStyle.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
        titleCellStyle.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
        titleCellStyle.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    }

    /**
     * 在表格中查找指定的行，如果行不存在，则创建
     * 
     * @param table
     *            word的表格
     * @param i
     *            指定的行，第一行为0
     * @return
     */
    private static XWPFTableRow createOrGetRow(XWPFTable table, int i) {

        XWPFTableRow crtRow = null;
        crtRow = table.getRow(i);
        if (crtRow == null) {
            crtRow = table.createRow();
        }

        return crtRow;
    }

    /**
     * 在行中查找指定的列，如果列不存在，则创建
     * 
     * @param crtRow
     *            word的行
     * @param i
     *            指定的列，第一列为0
     * @return
     */
    private static XWPFTableCell createOrGetCell(XWPFTableRow crtRow, int i) {

        XWPFTableCell cell = crtRow.getCell(i);
        if (cell == null) {
            cell = crtRow.createCell();
        }

        return cell;
    }

}
