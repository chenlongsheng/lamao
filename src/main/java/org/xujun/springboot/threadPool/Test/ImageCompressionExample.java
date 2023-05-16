package org.xujun.springboot.threadPool.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.ServerSocket;
import java.net.Socket;

public class ImageCompressionExample {
    public static void main(String[] args) throws IOException {
        // 压缩图片
        String imagePath = "example.jpg"; // 待压缩的图片路径
        int targetWidth = 800; // 目标宽度
        int targetHeight = 600; // 目标高度
        float quality = 0.7f; // 图片质量
        BufferedImage compressedImage = compressImage(imagePath, targetWidth, targetHeight, quality);

        // 保存压缩后的图片
        String compressedPath = "example_compressed.jpg"; // 压缩后的文件路径
        saveImage(compressedImage, compressedPath);

        // 解压图片
        String decompressedPath = "example_decompressed.jpg"; // 解压后的文件路径
        BufferedImage decompressedImage = decompressImage(compressedPath);
        saveImage(decompressedImage, decompressedPath);

        // 发送压缩后的图片到网络上
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Waiting for connection on port " + port);
        Socket socket = serverSocket.accept();
        OutputStream outputStream = socket.getOutputStream();
        sendImage(compressedImage, "jpg", outputStream);
        outputStream.flush();
        outputStream.close();
        socket.close();
        serverSocket.close();
    }

    private static BufferedImage compressImage(String imagePath, int targetWidth, int targetHeight, float quality) throws IOException {
        // 读取原始图片
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

        return outputImage;
    }

    private static BufferedImage decompressImage(String compressedPath) throws IOException {
        // 读取压缩后的图片
        File compressedFile = new File(compressedPath);
        BufferedImage compressedImage = ImageIO.read(compressedFile);

        // 解压图片
        BufferedImage outputImage = new BufferedImage(compressedImage.getWidth(), compressedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(compressedImage, 0, 0, null);

        return outputImage;
    }

    private static void saveImage(BufferedImage image, String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "jpg", file);
    }

    private static void sendImage(BufferedImage image, String format, OutputStream outputStream1) throws IOException {
        String imagePath = "example.jpg"; // 图像路径
        int port = 8080; // 目标端口号
        String host = "example.com"; // 目标主机名

        // 读取图像
        File imageFile = new File(imagePath);
         image = ImageIO.read(imageFile);

        // 将图像编码为 JPEG 格式
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        // 创建 Socket 并发送数据
        Socket socket = new Socket(host, port);
        OutputStream out = socket.getOutputStream();
        out.write(outputStream.toByteArray());
        out.flush();

        // 关闭连接
        out.close();
        socket.close();
    }
}
