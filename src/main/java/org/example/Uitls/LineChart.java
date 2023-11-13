package org.example.Uitls;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LineChart extends JPanel {

    private ArrayList<Integer> dataSizes;
    private Map<Integer, Long> runTimes;

    public LineChart(ArrayList<Integer> dataSizes, Map<Integer, Long> runTimes) {
        this.dataSizes = dataSizes;
        this.runTimes = runTimes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // 计算y轴的最大值和最小值
        long minTime = Long.MAX_VALUE;
        long maxTime = 0;
        for (long time : runTimes.values()) {
            minTime = Math.min(minTime, time);
            maxTime = Math.max(maxTime, time);
        }

        // 设置坐标轴线宽度
        g2.setStroke(new BasicStroke(2));  // 设置线条宽度为2

        // 绘制坐标轴
        g2.drawLine(50, height - 50, 50, 50);  // 垂直轴
        g2.drawLine(50, height - 50, width - 50, height - 50);  // 水平轴

        // 恢复默认线宽
        g2.setStroke(new BasicStroke(1));  // 恢复默认线条宽度

        // 设置字体大小
        Font baseFont = g2.getFont();
        Font font = baseFont.deriveFont(12.0f);  // 设置字体大小为12

//        // 绘制y轴刻度，从零开始
//        int numTicks = 6; // 调整刻度数量
//        for (int i = 0; i < numTicks; i++) {
//            int y = height - 50 - i * (height - 100) / (numTicks - 1);
//            long value = i * (maxTime / (numTicks - 1));
//
//            // 坐标轴刻度字体大小增大为25
//            g2.setFont(baseFont.deriveFont(25.0f));  // 设置字体大小为25
//            g2.drawString(String.valueOf(value), 10, y + 5);
//
//            // 添加横线
//            g2.drawLine(50, y, width - 50, y);
//        }


        // 绘制y轴刻度，从零开始
        int numTicks = 6; // 调整刻度数量
        int yOffset = 10; // 数字向下偏移的像素值
        int stepSize = 500; // 刻度递增的单位
        for (int i = 0; i < numTicks; i++) {
            int y = height - 50 - i * (height - 100) / (numTicks - 1);
            long value = i * (maxTime / (numTicks - 1));
//            long value = i * stepSize;

            // 坐标轴刻度字体大小增大为25
            g2.setFont(baseFont.deriveFont(20.0f));  // 设置字体大小为25
            g2.drawString(String.valueOf(value), 10-yOffset, y ); // 调整数字的绘制位置

            // 添加横线
            g2.drawLine(50, y, width - 50, y);
        }


        // 恢复默认字体大小
        g2.setFont(font);

        // 设置字体大小为25用于绘制横轴刻度单位
        font = baseFont.deriveFont(25.0f);  // 设置字体大小为25
        g2.setFont(font);

        // 绘制x轴刻度单位
        g2.setFont(new Font("华文仿宋", Font.PLAIN, 25));

        int textWidth = g2.getFontMetrics().stringWidth("数据量（万次）");
        int xx = width  - textWidth + 10; // 调整 10 像素的偏移量
        int yy = height - 10;
        g2.drawString("数据量（万次）", xx, yy);

//        g2.drawString("数据量（万次）", width - 150, height - 10);
        // 绘制y轴刻度单位



        g2.setFont(new Font("华文仿宋", Font.PLAIN, 25));

        int xxx = 5;  // 0.02表示相对位置，可以根据需要调整
        int yyy = height - (int)(0.95* height);  // 0.1表示相对位置，可以根据需要调整

        g2.drawString("时间（分钟）", xxx, yyy );
//        g2.drawString("时间（毫秒）", 5, height-520 );
        // 恢复默认字体大小
        g2.setFont(baseFont);

        // 添加标题
        g2.setColor(Color.black);
        g2.setFont(new Font("华文仿宋", Font.BOLD, 30));
        g2.drawString("运行时间折线图", width / 2 - 100, 30);

        // 绘制x轴刻度
        int numDataPoints = dataSizes.size();
        for (int i = 0; i < numDataPoints; i++) {
            int x = 50 + (i+1) * (width - 100) / (numDataPoints - 1);
            int dataSize = dataSizes.get(i);

            // 坐标轴刻度字体大小增大为25
            g2.setFont(baseFont.deriveFont(20.0f));  // 设置字体大小为25
            g2.drawString(String.valueOf(dataSize), x, height - 30);

            //竖线
            g2.drawLine(x, height - 50, x, 50);
        }
//        // 绘制y轴刻度，从零开始
//        int numTicks = 6; // 调整刻度数量
//        int yOffset = 10; // 数字向下偏移的像素值
//        int stepSize = 500; // 刻度递增的单位
//        for (int i = 0; i < numTicks; i++) {
//            int y = height - 50 - i * (height - 100) / (numTicks - 1);
//            long value = i * (maxTime / (numTicks - 1));
////            long value = i * stepSize;
//
//            // 坐标轴刻度字体大小增大为25
//            g2.setFont(baseFont.deriveFont(20.0f));  // 设置字体大小为25
//            g2.drawString(String.valueOf(value), 10-yOffset, y ); // 调整数字的绘制位置
//
//            // 添加横线
//            g2.drawLine(50, y, width - 50, y);
//        }


        // 绘制折线
        g2.setColor(Color.blue);
        int prevX = 0, prevY = 0;
        for (int i = 0; i < dataSizes.size(); i++) {
            int x = 50 + i * (width - 100) / (dataSizes.size() - 1);
            long time = runTimes.get(dataSizes.get(i));
            int y = height - 50 - (int) ((time - minTime) * (height - 100) / (maxTime - minTime));
            if (i > 0) {
                g2.drawLine(prevX, prevY, x, y);
            }
            prevX = x;
            prevY = y;
        }
    }

    public static void main(String[] args) {
        // 准备数据
        ArrayList<Integer> dataSizes = new ArrayList<>();
        dataSizes.add(1);
        dataSizes.add(2);
        dataSizes.add(3);
        dataSizes.add(4);
        dataSizes.add(5);
//        dataSizes.add(6); // 新增数据点
//        dataSizes.add(7); // 新增数据点
//        dataSizes.add(8); // 新增数据点
//        dataSizes.add(9); // 新增数据点
//        dataSizes.add(10); // 新增数据点


        Map<Integer, Long> runTimes = new HashMap<>();
        runTimes.put(1, 156157/60000L);
        runTimes.put(2, 313883/60000L);
        runTimes.put(3, 466593/60000L);
        runTimes.put(4, 621016/60000L); // 新增数据点
        runTimes.put(5, 744605/60000L); // 新增数据点
//        runTimes.put(5, 18L); // 新增数据点
//
//        runTimes.put(6, 20L);
//        runTimes.put(7, 23L);
//        runTimes.put(8, 25L);
//        runTimes.put(9, 27L);
//        runTimes.put(10, 31L);

        // 创建GUI窗口并绘制折线图
        JFrame frame = new JFrame("运行时间折线图");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new LineChart(dataSizes, runTimes));
        frame.setVisible(true);
    }
}

