package application.io;

import UI.app.view.ApplicationPanel;
import UI.app.view.ApplicationWindow;
import UI.pages.LoadPage.LoadPage;
import UI.panels.loadPanel.LoadPanel;

public class LoadModel {
    private long totalBytes = 0;
    private long readBytes = 0;
    private String prompt = "Loading...";
    private LoadPage observer = null;

    public String getProgressString(int decimalPlaces){
        String format = "%."+decimalPlaces+"f";
        if(decimalPlaces==0){
            return (100*getProgress())+"";
        }
        String str = String.format(format+" ",100*getProgress());
        return str+"%";
    }

    public double getProgress(){
        return ((readBytes + 0.0)/(totalBytes + 0.0));
    }

    public LoadPage getObserver() {
        return observer;
    }

    public void setObserver(LoadPage observer) {
        this.observer = observer;
    }

    public void incrementTotalBytes(int inc){
        totalBytes += inc;
    }

    public void incrementReadBytes(int inc){
        readBytes += inc;
        if(observer != null && readBytes >= totalBytes && totalBytes > 0){
            observer.onLoad();
        }
    }

    public void finish(){
        readBytes = totalBytes;
        if(observer != null && readBytes >= totalBytes && totalBytes > 0){
            observer.onLoad();
        }
    }

    public void removeSelf(){
        if(observer!=null){
            observer.getParent().remove(observer);
        }
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getReadBytes() {
        return readBytes;
    }

    public void setReadBytes(long readBytes) {
        this.readBytes = readBytes;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
