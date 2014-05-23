package io.github.aectann.fitwater.model;

/**
 * Created by aectann on 23/05/14.
 */
public class GoalResponse {

  private Goal goal;

  public Goal getGoal() {
    return goal;
  }

  public void setGoal(Goal goal) {
    this.goal = goal;
  }

  @Override
  public String toString() {
    return "GoalResponse{" +
            "goal=" + goal +
            '}';
  }
}
