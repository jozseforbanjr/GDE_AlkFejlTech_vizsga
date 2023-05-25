package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/runner")
public class RunnerRestController {

    @Autowired
    private LapTimeRepository lapTimeRepository;
    private RunnerRepository runnerRepository;
    private SponsorRepository sponsorRepository; //f#9

    @Autowired
    public RunnerRestController(RunnerRepository runnerRepository, LapTimeRepository lapTimeRepository) {
        this.runnerRepository = runnerRepository;
        this.lapTimeRepository = lapTimeRepository;
    }

    @GetMapping("/{id}")
    public RunnerEntity getRunner(@PathVariable Long id) {
        return runnerRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/averagelaptime")
    public double getAverageLaptime(@PathVariable Long id) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            List<LapTimeEntity> laptimes = runner.getLaptimes();
            int totalTime = 0;
            for (LapTimeEntity laptime : laptimes) {
                totalTime += laptime.getTimeSeconds();
            }
            double averageLaptime = (double) totalTime / laptimes.size();
            return averageLaptime;
        } else {
            return -1.0;
        }
    }

    @GetMapping("")
    public List<RunnerEntity> getAllRunners() {
        return runnerRepository.findAll();
    }

    @GetMapping("/highest")
    public String getHighest() {
        List<RunnerEntity> runners = runnerRepository.findAll();
        RunnerEntity higherRunner;
        //model.addAttribute("runners", runners);//
        String highestName = "Highest not found";

        //Arrays.stream(runners.runnerHeight.toArray()).max();
        double current_highest_value = 0;
        long counter = 0;
        for (RunnerEntity runner : runners) {
            if (runner != null) {
                if (current_highest_value < runner.getRunnerHeight()) {
                    current_highest_value = runner.getRunnerHeight(); //feluliras
                    highestName = runner.getRunnerName();
                }
            }
            counter += 1;
        }
        highestName = "Highest runner: " + highestName;
        return highestName;
        /*} else {
            return "Error: highest could not be determined";
        }*/
    }

    @PostMapping("/{id}/addlaptime")
    public ResponseEntity addLaptime(@PathVariable Long id, @RequestBody LapTimeRequest lapTimeRequest) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            LapTimeEntity lapTime = new LapTimeEntity();
            lapTime.setTimeSeconds(lapTimeRequest.getLapTimeSeconds());
            lapTime.setLapNumber(runner.getLaptimes().size() + 1);
            lapTime.setRunner(runner);
            lapTimeRepository.save(lapTime);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }
    public static class LapTimeRequest {
        private int lapTimeSeconds;

        public int getLapTimeSeconds() {
            return lapTimeSeconds;
        }

        public void setLapTimeSeconds(int lapTimeSeconds) {
            this.lapTimeSeconds = lapTimeSeconds;
        }
    }

    // F10.: REST vegpont: cipo tipus modositashoz
    @PostMapping("/{id}/setsSponsor")
    public ResponseEntity setSponsor(@PathVariable Long id, @RequestBody SponsorRequest sponsorRequest) {
        // a kod nem tartalmazza a Runner.Id <-> Sponsor.Id kapcsolatot!
        // Az adatbetoltes logikaja (Runner.Id = Sponsor.Id) alapjan azonban OK
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        SponsorEntity sponsor = sponsorRepository.findById(sponsorRequest.getSponsorID()).orElse(null);
        if (runner != null && sponsor != null) {
            //nem kell uj elem, csak feluliras
            runner.setSponsorEntity(sponsor);
            runnerRepository.save(runner);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Runner with ID " + id + " not found");
        }
    }

    public static class SponsorRequest {

        private long sponsorID;
        public long getSponsorID() { return sponsorID; }
        public void setSponsorID(long sponsorID) { this.sponsorID = sponsorID; }


    }

}
