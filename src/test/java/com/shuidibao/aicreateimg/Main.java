package com.shuidibao.aicreateimg;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Classname ${NAME}
 * @Description TODO
 * @Date 2023/9/15
 * @Author 谢海川
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage img1 = ImageMerge.getBufferedImage("/Users/shuidi/Desktop/pic1.png");
        BufferedImage img2 = ImageMerge.getBufferedImage("/Users/shuidi/Desktop/pic2.png");
        BufferedImage overlyingImage = ImageMerge.overlyingImage(img2, img1, 50, 80, 1f);
        ImageMerge.generateSaveFile(overlyingImage, "/Users/shuidi/Desktop/merge.png");
    }
}