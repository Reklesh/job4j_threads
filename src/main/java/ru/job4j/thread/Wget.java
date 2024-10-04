package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private static final long PAUSE = 1000;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String[] arr = url.split("/");
        String filePath = "data/" + arr[arr.length - 1];
        var file = new File(filePath);
        try (var input = new URL(url).openStream(); var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            int bytesRead;
            int bytesDownloaded = 0;
            var startAt = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                bytesDownloaded += bytesRead;
                if (bytesDownloaded >= speed) {
                    var downloadTime = System.currentTimeMillis() - startAt;
                    if (downloadTime < PAUSE) {
                        Thread.sleep(PAUSE - downloadTime);
                    }
                    bytesDownloaded = 0;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean urlValidator(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException exception) {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Указаны не все входные параметры");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        if (speed < 0) {
            throw new IllegalArgumentException("Скорость скачивания не может быть отрицательной");
        }
        if (urlValidator(url)) {
            Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        }
    }
}