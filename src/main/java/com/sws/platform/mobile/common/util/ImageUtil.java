package com.sws.platform.mobile.common.util;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * 图片处理工具类
 */
public class ImageUtil {
    /**
     * 把二进制数组的背景转为背景透明的图片
     */
    public static boolean transparentImage(byte[] imageBytes, File desFile) {
        if (null == imageBytes || imageBytes.length == 0 || desFile == null) {
            return false;
        }

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
//                ImageIcon imageIcon = new ImageIcon(imageBytes);
//                image = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//                Graphics2D g2D = (Graphics2D) image.getGraphics();
//                g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
////                g2D.drawImage(image, 0, 0, null);
                return false;
            }

//            int imgWidth = image.getWidth();
//            int imgHeight = image.getHeight();
//            int c = image.getRGB(3, 3);
//            BufferedImage bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_4BYTE_ABGR);
//            int alpha = 10;
//            for (int i = 0; i < imgWidth; ++i) {
//                for (int j = 0; j < imgHeight; ++j) {
//                    int rgb = image.getRGB(i, j);
//                    if (rgb == c) {
//                        bufferedImage.setRGB(i, j, c & 0x00ffffff);
//                    } else {
//                        rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);
//                        bufferedImage.setRGB(i, j, rgb);
//                    }
//                }
//            }

            BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            int alpha = 0;
//            int range = 30;
            int range = 90;

            for (int j1 = image.getMinY(); j1 < image.getHeight(); j1++) {
                for (int j2 = image.getMinX(); j2 < image.getWidth(); j2++) {
                    int rgb = image.getRGB(j2, j1);
                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    if (((255 - R) < range) && ((255 - G) < range) && ((255 - B) < range)) {
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                    }
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }

            if (!desFile.getParentFile().exists()) {
                desFile.getParentFile().mkdirs();
            }

            return ImageIO.write(bufferedImage, "png", desFile);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 把byte数组存为图片文件
     *
     * @param imageBytes 图片byte数组
     * @param desFile    目标文件
     */
    public static boolean saveImage(byte[] imageBytes, File desFile) {
        if (null == imageBytes || imageBytes.length == 0 || desFile == null) {
            return false;
        }
        if (!desFile.getParentFile().exists()) {
            desFile.getParentFile().mkdirs();
        }
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(desFile);
            imageOutput.write(imageBytes, 0, imageBytes.length);
            imageOutput.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
