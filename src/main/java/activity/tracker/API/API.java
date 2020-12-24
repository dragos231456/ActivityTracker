package activity.tracker.API;


import activity.tracker.Controller.Controller;
import activity.tracker.Model.Activities.*;
import activity.tracker.Model.DataStructures.Activities;
import activity.tracker.Model.DataStructures.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/activity")
@RestController
public class API {
    private final Controller service;

    @Autowired
    public API(Controller service) {
        this.service = service;
    }


    @ExceptionHandler
    public ResponseEntity<String> handleException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @GetMapping
    public List<Activity> getAllActivities() {
        return service.getAllActivities();
    }


    @PostMapping(path = "/creative")
    public void addCreativeActivity(@RequestBody Creative creative) throws IOException {
        service.addActivity(creative);
    }

    @PostMapping(path = "/cycling")
    public void addCyclingActivity(@RequestBody Cycling cycling) throws IOException {
        service.addActivity(cycling);
    }

    @PostMapping(path = "/driving")
    public void addDrivingActivity(@RequestBody Driving driving) throws IOException {
        service.addActivity(driving);
    }

    @PostMapping(path = "/football")
    public void addFootballActivity(@RequestBody Football football) throws IOException {
        service.addActivity(football);
    }

    @PostMapping(path = "/hiking")
    public void addHikingActivity(@RequestBody Hiking hiking) throws IOException {
        service.addActivity(hiking);
    }

    @PostMapping(path = "/othersports")
    public void addOtherSportsActivity(@RequestBody OtherSports otherSports) throws IOException {
        service.addActivity(otherSports);
    }

    @PostMapping(path = "/reading")
    public void addReadingActivity(@RequestBody Reading reading) throws IOException {
        service.addActivity(reading);
    }

    @PostMapping(path = "/tennis")
    public void addTennisActivity(@RequestBody Tennis tennis) throws IOException {
        service.addActivity(tennis);
    }

    @PostMapping(path = "/volley")
    public void addVolleyActivity(@RequestBody Volley volley) throws IOException {
        service.addActivity(volley);
    }

    @PostMapping(path = "/walking")
    public void addWalkingActivity(@RequestBody Walking walking) throws IOException {
        service.addActivity(walking);
    }


    @DeleteMapping(path = "/undo")
    public void undoLastAction() throws IOException {
        service.undo();
    }

    @PostMapping(path = "/redo")
    public void redoLastAction() throws IOException {
        service.redo();
    }



    @GetMapping(path = "/hoursSpent/{act}")
    public String getHoursSpentOnActivity(@PathVariable("act") Activities act) {
        return "Hours spent on "+act+": "+service.getMinutesSpentOnActivity(act)/60;
    }

    @GetMapping(path = "/hoursSpent/interval/{act}")
    public String getHoursSpentOnActivityInInterval(@PathVariable("act") Activities act,@RequestBody Interval interval) {
        System.out.println(interval);
        return "Hours spent on "+act+" in interval: "+service.getMinutesSpentOnActivityInInterval(act,interval.getStartTime(),interval.getFinishTime())/60;
    }

    @GetMapping(path = "/count/{act}")
    public String getCountOfActivity(@PathVariable("act") Activities act) {
        return "I've been "+act+" "+service.getCountOfActivity(act)+" times so far";
    }

    @GetMapping(path = "/count/interval/{act}")
    public String getCountOfActivityOnInterval(@PathVariable("act") Activities act, @RequestBody Interval interval) {
        System.out.println(interval);
        return "I've been "+act+" "+service.getCountOfActivityOnInterval(act,interval.getStartTime(),interval.getFinishTime())+" in the interval";
    }

    @GetMapping(path = "/maxHours")
    public String getMaxTotalHoursInActivities() {
        long minutes = service.getMostMinutesSpentOnActivity();
        Activities activity = service.getActivityByMinutesSpent(minutes);
        return "Max time spent on an activity is "+minutes/60+" hours in "+activity;
    }

    @GetMapping(path = "/maxFrequency")
    public String getMaxFrequencyInActivities() {
        long frequency = service.getMostPracticedActivity();
        Activities activity = service.getActivityByFrequency(frequency);
        return "Most frequent activity is "+activity+", "+frequency+" times";
    }


    private void careInterval(Interval interval) {
        if(interval.getStartTime() == null) interval.setStartTime(LocalDateTime.MIN);
        if(interval.getFinishTime() == null) interval.setFinishTime(LocalDateTime.MAX);
    }



    //Cycling
    @GetMapping(path = "/cycling/maxSpeed")
    public String getCyclingMaxSpeedInterval(@RequestBody Interval interval) {
        careInterval(interval);
        return "Max Speed: "+service.getCyclingMaxSpeedInterval(interval.getStartTime(),interval.getFinishTime())+" km/h";
    }

    @GetMapping(path = "/cycling/totalDistance")
    public String getCyclingTotalDistanceInterval(@RequestBody Interval interval) {
        careInterval(interval);
        return "Total Distance: "+service.getCyclingTotalDistanceInterval(interval.getStartTime(),interval.getFinishTime())+" km";
    }


    //Driving
    @GetMapping(path = "/driving/maxSpeed")
    public String getDrivingMaxSpeedInterval(@RequestBody Interval interval) {
        careInterval(interval);
        return "Max Speed: "+service.getDrivingMaxSpeedInterval(interval.getStartTime(),interval.getFinishTime())+" km/h";
    }

    @GetMapping(path = "/driving/totalDistance")
    public String getDrivingTotatlDistanceInterval(@RequestBody Interval interval) {
        careInterval(interval);
        return "Total Distance: "+service.getDrivingTotalDistanceInterval(interval.getStartTime(),interval.getFinishTime())+" km";
    }


    //Footbal
    @GetMapping(path = "/football/goalsScored")
    public String getFootballGoalsScoredInterval(@RequestBody Interval interval) {
        careInterval(interval);
        return "Goals Scored: "+service.getFootballGoalsScoredInterval(interval.getStartTime(),interval.getFinishTime());
    }

    @GetMapping(path = "/football/mostVisitedPitch")
    public String getFootballMostVisitedPitchInterval(@RequestBody Interval interval) {
        careInterval(interval);
        long visits = service.getFootballNumberOfMostVisitedPitchInterval(interval.getStartTime(),interval.getFinishTime());
        Optional<String> pitch = service.getFootballMostVisitedPitchInterval(interval.getStartTime(),interval.getFinishTime(),visits);

        if (pitch.isPresent()) return "Most visited pitch is: "+pitch.get()+" with "+visits+" times";
        else throw new RuntimeException("number of visits got doesn't exist(internal error)!");
    }


    //Hiking
    @GetMapping(path = "/hiking/totalDistance")
    public String getHikingTotalDistanceInterval(@RequestBody Interval interval) {
        careInterval(interval);
        return "Total Distance: "+service.getHikingTotalDistanceInterval(interval.getStartTime(),interval.getFinishTime())+" km";
    }

    @GetMapping(path = "/hiking/maxDistance")
    public String getHikingMaxDistanceInterval(@RequestBody Interval interval) {
        careInterval(interval);
        double distance = service.getHikingLongestDistanceAtOnceInterval(interval.getStartTime(),interval.getFinishTime());
        Optional<Activity> activityOptional = service.getHikingbyDistanceInterval(interval.getStartTime(),interval.getFinishTime(),distance);

        if(activityOptional.isPresent()) {
            Hiking h = (Hiking) activityOptional.get();
            return "Longest hiking trip was in: "+h.getLocation()+" with: "+h.getElevation()+" m in elevation and "+distance+" km total distance";
        }
        else throw new RuntimeException("Couldnt find longest trip(internal error)");
    }


    //Reading
    @GetMapping(path = "/reading/pagesRead")
    public String getReadingTotalPagesReadInterval(@RequestBody Interval interval) {
        careInterval(interval);
        return "Total Pages Read: "+service.getReadingTotalPagesReadInterval(interval.getStartTime(),interval.getFinishTime());
    }


    //Tennis
    @GetMapping(path = "/tennis/mostPlayedOpponent")
    public String getTennisMostPlayedAgainstOpponentInterval(@RequestBody Interval interval) {
        careInterval(interval);
        long times = service.getTennisNrtMostMatchesPlayedAgainstOpponentInterval(interval.getStartTime(),interval.getFinishTime());
        Optional<String> stringOptional = service.getTennisOpponentByTimesPlayed(interval.getStartTime(),interval.getFinishTime(),times);

        if(stringOptional.isPresent()) {
            return "I've palyed the most against: "+stringOptional.get()+", "+times+" times";
        }
        else throw new RuntimeException("Couldnt find most played against opponent(internal error)");
    }


    //Walking
    @GetMapping(path = "/walking/totalDistance")
    public String getWalkingTotalDistanceInterval(@RequestBody Interval interval) {
        careInterval(interval);
        return "Total distance: "+service.getWalkingTotalDistanceInterval(interval.getStartTime(),interval.getFinishTime())+" km";
    }
}
