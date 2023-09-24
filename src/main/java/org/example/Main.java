package org.example;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> queue3 = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) throws InterruptedException {

        Thread findMostA = new FindMostLetter(queue1, 'a');
        Thread findMostB = new FindMostLetter(queue2, 'b');
        Thread findMostC = new FindMostLetter(queue3, 'c');
        findMostA.start();
        findMostB.start();
        findMostC.start();



        Thread filler = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String text = generateText("abc", 100_000);
                try {
                    queue1.put(text);
                    queue2.put(text);
                    queue3.put(text);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        filler.start();
        filler.join();
        findMostA.interrupt();
        findMostB.interrupt();
        findMostC.interrupt();

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}