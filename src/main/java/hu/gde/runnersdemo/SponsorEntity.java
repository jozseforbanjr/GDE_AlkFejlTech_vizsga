package hu.gde.runnersdemo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class SponsorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sponsorID;
    private String sponsorName;

    @JsonIgnore
    @OneToMany //(mappedBy = "runner", cascade = CascadeType.ALL, orphanRemoval = true)//
    private List<RunnerEntity> runners = new ArrayList<>();

    public Long getSponsorID() {
        return sponsorID;
    }

    public void setSponsorID(long sponsorID) {
        this.sponsorID = sponsorID;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
    public List<RunnerEntity> getRunners() {
        return runners;
    }
    //setRunners nem celszeru, nem kell

    //ures contructor
    public SponsorEntity() {
    }
}
