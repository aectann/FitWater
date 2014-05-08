package io.github.aectann.fitwater.model;

import java.util.List;

/**
 * Created by aectann on 7/05/14.
 */
public class Intake {

  private Summary summary;
  private List<WaterEntry> water;

  public Intake(Summary summary, List<WaterEntry> water) {
    this.summary = summary;
    this.water = water;
  }

  public double getTotal() {
    return summary.getWater();
  }

  public Summary getSummary() {
    return summary;
  }

  public void setSummary(Summary summary) {
    this.summary = summary;
  }

  public List<WaterEntry> getWater() {
    return water;
  }

  public void setWater(List<WaterEntry> water) {
    this.water = water;
  }

  @Override
  public String toString() {
    return "Intake{" +
            "summary=" + summary +
            ", water=" + water +
            '}';
  }

  static class Summary {

    private double water;

    public Summary(int water) {
      this.water = water;
    }

    public double getWater() {
      return water;
    }

    public void setWater(double water) {
      this.water = water;
    }

    @Override
    public String toString() {
      return "Summary{" +
              "water=" + water +
              '}';
    }
  }

  static class WaterEntry {
    private String logId;
    private double amount;

    public WaterEntry(String logId, int amount) {
      this.logId = logId;
      this.amount = amount;
    }

    public double getAmount() {
      return amount;
    }

    public void setAmount(double amount) {
      this.amount = amount;
    }

    public String getLogId() {
      return logId;
    }

    public void setLogId(String logId) {
      this.logId = logId;
    }

    @Override
    public String toString() {
      return "WaterEntry{" +
              "logId='" + logId + '\'' +
              ", amount=" + amount +
              '}';
    }
  }
}
