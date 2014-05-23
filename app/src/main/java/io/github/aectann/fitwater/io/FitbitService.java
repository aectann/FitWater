package io.github.aectann.fitwater.io;


import io.github.aectann.fitwater.model.GoalResponse;
import io.github.aectann.fitwater.model.Intake;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by aectann on 23/05/14.
 */
public interface FitbitService {

    @GET("/user/-/foods/log/water/goal.json")
    Observable<GoalResponse> getGoal();

    @GET("/user/-/foods/log/water/date/{date}.json")
    Observable<Intake> getIntake(@Path("date") String date);

}
