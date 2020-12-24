package activity.tracker.Model.Activities;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.jni.Local;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Reading implements Activity {
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private String title;
    private String author;
    private int pagesRead;

    public Reading setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public Reading setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Reading setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Reading setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getPagesRead() {
        return pagesRead;
    }

    public Reading setPagesRead(int pagesRead) {
        this.pagesRead = pagesRead;
        return this;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Duration getDuration() {
        return Duration.between(startTime,finishTime);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Activity) {
            return startTime.equals(((Activity) obj).getStartTime());
        }
        return false;
    }

    @Override
    public boolean before(Activity activity) {
        if(startTime.isEqual(activity.getStartTime())) {
            return finishTime.isBefore(activity.getFinishTime());
        }
        else
            return startTime.isBefore(activity.getStartTime());
    }

    @Override
    public Activity deepcopy() {
        return new Reading()
                .setStartTime(startTime)
                .setFinishTime(finishTime)
                .setAuthor(author)
                .setPagesRead(pagesRead)
                .setTitle(title);
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isValid() {
        return pagesRead > 0;
    }
}
