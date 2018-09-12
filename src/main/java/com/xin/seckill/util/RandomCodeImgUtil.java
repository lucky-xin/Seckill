package com.xin.seckill.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description:图片随机码
 * @date 2018-08-13 20:04
 * @Copyright (C)2018 , Luchaoxin
 */
public class
RandomCodeImgUtil {

    public static final int WIDTH = 120;
    public static final int HEIGHT = 25;
    private Color[] color = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GREEN,
            Color.MAGENTA, Color.RED};

    public void generatePicture(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Color textColor = color[new Random().nextInt(color.length)];
        Graphics g = image.getGraphics();
        // 设置背景色
        setBackGround(g);

        // 设置边框
        setBorder(g);

        // 画干扰线
        drawRandomLine(g, textColor);

        // 写随机数
        String checkCode = drawRandomNum((Graphics2D) g, textColor);
        request.getSession().setAttribute("imageCheckCode", checkCode);

        response.setContentType("image/jpeg");
        // 设置不要缓存
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-control");
        response.setHeader("prama", "no-control");
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    private String drawRandomNum(Graphics2D g, Color textColor) {
        g.setColor(textColor);
        g.setFont(new Font("宋体", Font.BOLD, 20));
        int x = 10;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            int degree = new Random().nextInt() % 45;
            int num = new Random().nextInt(9);
            sb.append(num);
            String str = num + "";
            g.rotate(degree * Math.PI / 180, x, 20);
            g.drawString(str, x, 20);
            g.rotate(-degree * Math.PI / 180, x, 20);
            x += 20;
        }
        return sb.toString();
    }

    private void drawRandomLine(Graphics g, Color textColor) {
        g.setColor(textColor);
        for (int i = 0; i < 5; i++) {
            int x1 = new Random().nextInt(WIDTH - 4) + 2;
            int y1 = new Random().nextInt(HEIGHT - 4) + 2;

            int x2 = new Random().nextInt(WIDTH - 2);
            int y2 = new Random().nextInt(HEIGHT - 2);

            g.drawLine(x1, y1, x2, y2);
        }

    }

    private void setBorder(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
    }

    private void setBackGround(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }
}
