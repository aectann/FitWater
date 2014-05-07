package io.github.aectann.fitwater.model;

import java.util.Date;

/**
 * Created by aectann on 7/05/14.
 */
public class Goal {

  private int goal;

  private Date startDate;

  public Goal(int goal, Date startDate) {
    this.goal = goal;
    this.startDate = startDate;
  }

  public int getGoal() {
    return goal;
  }

  public void setGoal(int goal) {
    this.goal = goal;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
}
