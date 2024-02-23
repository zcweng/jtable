package com.suke.example;

import com.suke.jtable.Border;
import com.suke.jtable.Rect;
import com.suke.jtable.Table;
import com.suke.jtable.TextAlign;
import com.suke.jtable.graphics.FontStyle;
import com.suke.jtable.graphics.skija.SkijaGraphicsEnv;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

public class SkijaMain {

    @SneakyThrows
    public static void main(String[] args) {
        SkijaGraphicsEnv.load();
        final Table table = new Table();

        // 表格字体大小14px
        table.setFontSize(14)
                // 表格单元格内边距10px
                .setCellPadding(new Rect(10))
                // 表格单元格边框1px，颜色#dadcdd
                .setBorder(new Border(1, new Color(0xdadcdd), 0));
        // 表格第1行字体加粗
        table.getRow(0).setFontStyle(FontStyle.BOLD);
        // 表格第2行背景色#f5f5f5
        table.getRow(1).setCellBackground(new Color(0xf5f5f5)).setTextAlign(TextAlign.CENTER);

        // 表格第1行第1列到第1行第7列合并
        table.getCell(0, 0, 1, 7).setText("商家1(批次xx)\n(2024-02-05 02:09:00)");

        table.getCell(1, 0).setText("任务类型");
        table.getCell(1, 1).setText("MQ消息\r\n(已收/总量)");
        table.getCell(1, 2).setText("完成/执行中/失败");
        table.getCell(1, 3).setText("任务执行时间(分钟)");
        table.getCell(1, 4).setText("读取条数");
        table.getCell(1, 5).setText("过滤条数");
        table.getCell(1, 6).setText("写入条数");

        table.getCell(2, 0).setText("商品分类商品");

        final File file = new File("testskija.png");
        table.savePng(new FileOutputStream(file));
    }
}