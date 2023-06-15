package demo.gui.tasks;

import task.scheduler.tasks.ITask;
import task.scheduler.tasks.TaskContext;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class ImageSorter implements ITask {

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
        File file = new File(sourcePath);
        image = ImageIO.read(file);
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
