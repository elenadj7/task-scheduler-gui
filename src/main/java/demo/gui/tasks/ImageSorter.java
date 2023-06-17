package demo.gui.tasks;

import task.scheduler.tasks.ITask;
import task.scheduler.tasks.TaskContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import static java.lang.Thread.sleep;

public class ImageSorter implements ITask {

    private final File sourceFile;
    private final String name;
    private int priority = 0;
    private boolean immediateStart;
    private double progress  = 0.0;
    private final BufferedImage image;
    private final String destPath;
    private final boolean column;

    public BufferedImage getImage() {
        return image;
    }

    public boolean isColumn() {
        return column;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    private final Criteria criteria;
    public ImageSorter(String sourcePath, String destPath, boolean column, Criteria criteria, String name) throws IOException {
        sourceFile = new File(sourcePath);
        image = ImageIO.read(sourceFile);
        this.destPath = destPath;
        this.criteria = criteria;
        this.column = column;
        this.name = name;
    }
    @Override
    public String toString(){
        return name;
    }
    @Override
    public void run(TaskContext taskContext) {
        int width = image.getWidth();
        int height = image.getHeight();
        progress += 0.1;
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        progress += 0.1;

        Color[][] pixels = convertImageToColorArray(width, height);
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        progress += 0.1;

        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        progress += 0.1;

        if(column){

            for(int column = 0; column < width; column++){
                Color[] col = getColumn(pixels, column);
                sort(col);
                setColumn(pixels, column, col);
            }
        }
        else {

            for(int row = 0; row < height; row++){
                Color[] rowPixels = pixels[row];
                sort(rowPixels);
            }
        }

        try {
            taskContext.checkForPause();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        progress += 0.1;
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        progress += 0.1;

        BufferedImage outputImage = convertColorArrayToImage(pixels, width, height);
        try {
            ImageIO.write(outputImage, "bmp", new File(destPath + File.separator + "sorted" + criteria + "-" + sourceFile.getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        progress += 0.2;

        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        progress += 0.2;
    }

    private Color[][] convertImageToColorArray(int width, int height){
        Color[][] pixels = new Color[height][width];
        for(int row = 0; row < height; ++row){
            for(int column = 0; column < width; ++column){
                pixels[row][column] = new Color(image.getRGB(column, row));
            }
        }
        return pixels;
    }
    private BufferedImage convertColorArrayToImage(Color[][] pixels, int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int row = 0; row < height; row++){
            for(int column = 0; column < width; column++){
                image.setRGB(column, row, pixels[row][column].getRGB());
            }
        }
        return image;
    }
    private void setColumn(Color[][] pixels, int col, Color[] column){
        int height = pixels.length;

        for(int row = 0; row < height; row++){
            pixels[row][col] = column[row];
        }
    }
    private Color[] getColumn(Color[][] pixels, int col){
        int height = pixels.length;
        Color[] column = new Color[height];

        for(int row = 0; row < height; row++){
            column[row] = pixels[row][col];
        }

        return column;
    }
    private void sort(Color[] colors){
        switch (criteria){
            case GREEN -> {
                Arrays.sort(colors, Comparator.comparingInt(Color::getGreen));
            }
            case RED -> {
                Arrays.sort(colors, Comparator.comparingInt(Color::getRed));
            }
            case BLUE -> {
                Arrays.sort(colors, Comparator.comparingInt(Color::getBlue));
            }
        }
    }
    @Override
    public double getProgress() {
        return progress;
    }

    @Override
    public void setImmediateStart(boolean b) {
        immediateStart = b;
    }

    @Override
    public void setPriority(int i) {
        priority = i;
    }

    @Override
    public boolean getImmediateStart() {
        return immediateStart;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
