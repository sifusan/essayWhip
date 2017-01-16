package essaywhip.util;

/**
 * Created by sifu on 23/12/16.
 */

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class ProgressChecker {
    private int wordGoal;
    private float progress;
    private File file;
    private int wordCount;

    public ProgressChecker() {
    }

    public ProgressChecker(int wordGoal, File file) {
        this.wordGoal = wordGoal;
        this.file = file;
    }

    private String getFileExtension() {
        String fileName = file.getName();
        String extension = "";
        boolean found_extension = false;
        for (int i = 0; i < fileName.length(); i++) {
            if (fileName.charAt(i) == '.') {
                found_extension = true;
            }

            if (found_extension) {
                extension = extension + fileName.charAt(i);
            }
        }
        return extension;
    }

    private String readDoc() {
        try (FileInputStream fi = new FileInputStream(file)) {
            HWPFDocument doc = new HWPFDocument(fi);
            WordExtractor extractor = new WordExtractor(doc);
            String data = extractor.getText();
            return data;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String readDocX() {
        try (FileInputStream fi = new FileInputStream(file)) {
            XWPFDocument doc = new XWPFDocument(fi);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String data = extractor.getText();
            return data;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private int getWordCount(String data) {
        wordCount = 0;
        for (int i = 0; i < data.length(); i++) {
            if ((data.charAt(i) == ' ') && i != data.length() - 1) {
                wordCount += 1;
            }
        }
        return wordCount;
    }

    private int getWordCount() {
        wordCount = 0;
        String cache = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (int r; (r = br.read()) != -1; ) {
                cache = cache + (char) r;
            }

            for (int i = 0; i < cache.length(); i++) {
                if ((cache.charAt(i) == ' ') && i != cache.length() - 1) {
                    wordCount += 1;
                }
            }
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        if (wordCount != 0) {
            wordCount += 1;
        }
        return wordCount;
    }

    private float updateProgress() {
        String extension = getFileExtension();
        switch (extension) {
            case "":
                this.progress = ((float) getWordCount() / wordGoal);
                break;
            case ".doc":
                this.progress = ((float) getWordCount(readDoc()) / wordGoal);
                break;
            case ".docx":
                this.progress = ((float) getWordCount(readDocX()) / wordGoal);
                break;
            default:
                System.out.println("INVALID EXTENSION SUPPLIED");
                break;
        }

        return this.progress;
    }

    public void setWordGoal(int wordGoal) {
        this.wordGoal = wordGoal;
    }


    private String getProgressString() {
        return String.valueOf(progress * 100) + "%";
    }

    public void startProgressTimer(ProgressBar progressBar, Label progressLabel, Label wordCountLabel) {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                progressBar.setProgress(updateProgress());
                progressLabel.setText(getProgressString());
                wordCountLabel.setText(String.valueOf(wordCount));
            }
        }.start();
    }
}
