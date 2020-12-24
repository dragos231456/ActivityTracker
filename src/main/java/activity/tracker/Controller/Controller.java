package activity.tracker.Controller;

import activity.tracker.Controller.Action.*;
import activity.tracker.Controller.Validator.IValidator;
import activity.tracker.Controller.Validator.Validator;
import activity.tracker.Model.Activities.*;
import activity.tracker.Model.DataStructures.Activities;
import activity.tracker.Model.DataStructures.IUndoStack;
import activity.tracker.Model.DataStructures.UndoStack;
import activity.tracker.Repository.IRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public final class Controller {
    private final IRepository repository;
    private final IUndoStack undoStack;
    private final IValidator validator;

    @Autowired
    public Controller(@Qualifier("fileRepo") IRepository repository) throws IOException, ClassNotFoundException {
        this.repository = repository;
        undoStack = new UndoStack();
        validator = new Validator();
    }

    public void addActivity(Activity activity) throws IOException {
        validator.valid(activity);
        undoStack.addAction(new Add(repository, repository.addActivity(activity)));
    }

    public void removeActivity(Activity activity) throws IOException {
        validator.valid(activity);
        undoStack.addAction(new Remove(repository, repository.removeActivity(activity)));
    }

    public void updateActivity(Activity activity) throws IOException {
        validator.valid(activity);
        undoStack.addAction(new Update(repository, repository.updateActivity(activity), activity));
    }

    public Activity searchActivity(Activity activity) {
        validator.valid(activity);
        return repository.searchActivity(activity);
    }

    public Activity getActivityByIndex(int index) {
        return repository.getActivityByIndex(index);
    }

    public boolean existsActivity(Activity activity) {
        validator.valid(activity);
        return repository.exists(activity);
    }

    public int repositorySize() {
        return repository.size();
    }

    public List<Activity> getAllActivities() {
        return repository.getAllActivities();
    }

    public void saveContentToFile() throws IOException {
        repository.writeAllToFile(repository.getAllActivities());
    }

    public void undo() throws IOException {
        undoStack.undo();
    }

    public void redo() throws IOException {
        undoStack.redo();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean instanceOfActivity(Activities activities, Activity act) {
        switch (activities) {
            case Creative:
                return (act instanceof Creative);
            case Cycling:
                return (act instanceof Cycling);
            case Driving:
                return (act instanceof Driving);
            case Football:
                return (act instanceof Football);
            case Hiking:
                return (act instanceof Hiking);
            case OtherSports:
                return (act instanceof OtherSports);
            case Reading:
                return (act instanceof Reading);
            case Tennis:
                return (act instanceof Tennis);
            case Volley:
                return (act instanceof Volley);
            case Walking:
                return (act instanceof Walking);
        }
        return false;
    }

    //General
    public Long getMinutesSpentOnActivity(Activities activities) {
        return repository.getAllActivities()
                .stream()
                .filter(x -> instanceOfActivity(activities,x))
                .map(x ->x.getDuration().toMinutes())
                .reduce(0L, Long::sum);
    }

    public Long getMinutesSpentOnActivityInInterval(Activities activities, LocalDateTime startTime,LocalDateTime finishTime) {
        return repository.getAllActivities()
                .stream()
                .filter(x->instanceOfActivity(activities,x))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x ->x.getDuration().toMinutes())
                .reduce(0L, Long::sum);
    }

    public long getCountOfActivity(Activities activities) {
        return repository.getAllActivities()
                .stream()
                .filter(x -> instanceOfActivity(activities, x))
                .count();
    }

    public long getCountOfActivityOnInterval(Activities activities, LocalDateTime startTime, LocalDateTime finishTime) {
        return repository.getAllActivities()
                .stream()
                .filter(x->instanceOfActivity(activities,x))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .count();
    }

    public Long getMostMinutesSpentOnActivity() {
        return Stream.of(Activities.values())
                .map(this::getMinutesSpentOnActivity)
                .reduce(0L, Math::max);
    }

    public Long getMostPracticedActivity() {
        return Stream.of(Activities.values())
                .map(this::getCountOfActivity)
                .reduce(0L,Math::max);


    }

    public Activities getActivityByMinutesSpent(Long minutes) {
        return Stream.of(Activities.values())
                .filter(x -> getMinutesSpentOnActivity(x).equals(minutes))
                .findFirst().get();
    }

    public Activities getActivityByFrequency(Long frequency) {
        return Stream.of(Activities.values())
                .filter(x -> getCountOfActivity(x) == frequency)
                .findFirst().get();
    }

    //Cycling
    public double getCyclingTotalDistanceInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Cycling))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x -> ((Cycling) x).getDistance())
                .reduce((double) 0,Double::sum);
    }

    public double getCyclingMaxSpeedInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Cycling))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x -> ((Cycling) x).getMaxSpeed())
                .reduce((double) 0,Math::max);
    }


    //Driving
    public int getDrivingTotalDistanceInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Driving))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x -> ((Driving) x).getDistance())
                .reduce(0,Integer::sum);
    }

    public int getDrivingMaxSpeedInterval(LocalDateTime startTime, LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Driving))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x -> ((Driving) x).getMaxSpeed())
                .reduce( 0,Math::max);
    }

    //Football
    public int getFootballGoalsScoredInterval(LocalDateTime startTime, LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Football))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x -> ((Football) x).getGoalsScored())
                .reduce(0,Integer::sum);
    }

    public Long getFootballNumberOfMostVisitedPitchInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Football))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x -> ((Football) x).getPitch())
                .distinct()
                .map(y -> getAllActivities()
                        .stream()
                        .filter(x -> (x instanceof Football))
                        .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                        .map(x -> ((Football) x).getPitch())
                        .filter(x-> x.equals(y))
                        .count())
                .reduce(0L,Math::max);
    }

    public Optional<String> getFootballMostVisitedPitchInterval(LocalDateTime startTime,LocalDateTime finishTime, Long visits) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Football))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x -> ((Football) x).getPitch())
                .distinct()
                .filter(y -> getAllActivities()
                        .stream()
                        .filter(x -> (x instanceof Football))
                        .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime)))
                        .map(x -> ((Football) x).getPitch())
                        .filter(x -> x.equals(y))
                        .count() == visits)
                .findFirst();
    }

    //Hiking
    public double getHikingTotalDistanceInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Hiking))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x ->((Hiking) x).getDistance())
                .reduce((double) 0,Double::sum);
    }

    public double getHikingLongestDistanceAtOnceInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Hiking))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x ->((Hiking) x).getDistance())
                .reduce((double) 0,Math::max);
    }

    public Optional<Activity> getHikingbyDistanceInterval(LocalDateTime startTime, LocalDateTime finishTime, double distance) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Hiking))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .filter(x -> ((Hiking) x).getDistance() == distance)
                .findFirst();
    }

    //Reading
    public int getReadingTotalPagesReadInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Reading))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x -> ((Reading) x).getPagesRead())
                .reduce(0,Integer::sum);
    }


    //Tennis
    public Long getTennisNrtMostMatchesPlayedAgainstOpponentInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Tennis))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                .map(x ->((Tennis) x).getAgainst())
                .distinct()
                .map(y ->  getAllActivities()
                        .stream()
                        .filter(x -> (x instanceof Tennis))
                        .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime) ))
                        .map(x ->((Tennis) x).getAgainst())
                        .filter(x -> x.equals(y))
                        .count())
                .reduce(0L,Math::max);
    }

    public Optional<String> getTennisOpponentByTimesPlayed(LocalDateTime startTime,LocalDateTime finishTime, long times) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Tennis))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime)))
                .map(x -> ((Tennis) x).getAgainst())
                .distinct()
                .filter(y -> getAllActivities()
                        .stream()
                        .filter(x -> (x instanceof Tennis))
                        .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime)))
                        .map(x -> ((Tennis) x).getAgainst())
                        .filter(x -> x.equals(y))
                        .count() == times)
                .findFirst();
    }


    //Walking
    public double getWalkingTotalDistanceInterval(LocalDateTime startTime,LocalDateTime finishTime) {
        return getAllActivities()
                .stream()
                .filter(x -> (x instanceof Walking))
                .filter(x -> (startTime.isBefore(x.getStartTime()) && x.getFinishTime().isBefore(finishTime)))
                .map(x -> ((Walking) x).getDistance())
                .reduce((double) 0,Double::sum);
    }
}
