package org.example;

import java.util.concurrent.BlockingQueue;

public class FindMostLetter extends Thread {

    private char letter;
    private BlockingQueue<String> blockingQueue;

    public FindMostLetter(BlockingQueue<String> blockingQueue, char letter) {
        this.blockingQueue = blockingQueue;
        this.letter = letter;
    }


    @Override
    public void run() {
        int max = 0;
        String maxString = "";
        while (!Thread.currentThread().isInterrupted() || !blockingQueue.isEmpty()) {
            try {
                int count = 0;
                String text = blockingQueue.take();
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == letter) {
                        count++;
                    }
                }
                if (count > max) {
                    max = count;
                    maxString = text;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Text, v kotorom maksimalnoe kolichestvo simvolov " + letter + " " + max + ":\n" + maxString);
    }
}
