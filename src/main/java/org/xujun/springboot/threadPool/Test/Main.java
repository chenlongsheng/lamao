package org.xujun.springboot.threadPool.Test;

import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) throws IOException {
        peessed();
    }


    public static void peessed() throws IOException {
        String imagePath = "D:\\111.jpg"; // 待压缩的图片路径
        String compressedPath = "D:\\example_compressed.jpg"; // 压缩后的文件路径
        int targetWidth = 1600; // 目标宽度
        int targetHeight = 1280; // 目标高度
        float quality = 0.7f; // 图片质量
        System.out.println("===");
        // 读取原始图片====
        File imageFile = new File(imagePath);
        BufferedImage originalImage = ImageIO.read(imageFile);

        // 计算目标尺寸
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        double ratio = Math.min((double) targetWidth / originalWidth, (double) targetHeight / originalHeight);
        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);

        // 压缩图片
        Image compressedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(compressedImage, 0, 0, null);

        // 保存压缩后的图片
        File compressedFile = new File(compressedPath);
        ImageIO.write(outputImage, "jpg", compressedFile);
    }


    public static void read() throws IOException {
        String compressedPath = "example_compressed.jpg"; // 压缩文件路径
        String decompressedPath = "example_decompressed.jpg"; // 解压后的文件路径

        // 读取压缩后的图片
        File compressedFile = new File(compressedPath);
        BufferedImage compressedImage = ImageIO.read(compressedFile);

        // 解压图片
        BufferedImage outputImage = new BufferedImage(compressedImage.getWidth(), compressedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(compressedImage, 0, 0, null);

        // 保存解压后的图片
        File decompressedFile = new File(decompressedPath);
        ImageIO.write(outputImage, "jpg", decompressedFile);
    }


}

