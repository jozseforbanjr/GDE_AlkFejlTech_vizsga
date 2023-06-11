package hu.gde.runnersdemo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RunnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long runnerId;
    private String runnerName;
    private long averagePace;
    private double runnerHeight; //feladat 1, +getter/setter

    @OneToMany(mappedBy = "runner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LapTimeEntity> laptimes = new ArrayList<>();
    @ManyToOne //(mappedBy = "runner", cascade = CascadeType.ALL, orphanRemoval = true)//
    private SponsorEntity sponsor; //f#7

    public SponsorEntity getSponsor() {
        return sponsor;
    }

    public void setSponsor(SponsorEntity sponsor) {
        this.sponsor = sponsor;
    }

    public RunnerEntity() {
    }

    public long getRunnerId() {
        return runnerId;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public long getAveragePace() {
        return averagePace;
    }

    public void setRunnerId(long runnerId) {
        this.runnerId = runnerId;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public void setAveragePace(long averagePace) {
        this.averagePace = averagePace;
    }

    public List<LapTimeEntity> getLaptimes() {
        return laptimes;
    }

    //1. feladat
    public double getRunnerHeight() { return runnerHeight; }

    public void setRunnerHeight(double runnerHeight) { this.runnerHeight = runnerHeight; }
}
