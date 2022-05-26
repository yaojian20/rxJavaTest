package com.yao.chapter6;

import com.yao.superheroes.Helpers;
import com.yao.superheroes.SuperHeroesService;
import io.reactivex.Observable;
import io.vertx.core.json.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Code7 {

    private static final int[] SUPER_HEROES_BY_ID = {641, 65, 37, 142};

    public static void main(String[] args) {

        SuperHeroesService.run(false);

        Observable<String> observable = Observable.<String>create(emitter -> {
            for (int superHeroId : SUPER_HEROES_BY_ID) {

                // Load a super hero using the blocking URL connection
                URL url = new URL("http://localhost:8080/heroes/" + superHeroId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                rd.close();
                String superHero = new JsonObject(result.toString()).getString("name");

                Helpers.log("Emitting: " + superHero);
                emitter.onNext(superHero);
            }
            Helpers.log("Completing");
            emitter.onComplete();
        })
            // USe the subscrbeOn operator to use the io scheduler.

            ;

        Helpers.log("---------------- Subscribing");
        observable
            .subscribe(
                item -> {
                    Helpers.log("Received " + item);
                }, error -> {
                        Helpers.log("Error");
                }, () -> {
                        Helpers.log("Complete");
                });
        Helpers.log("---------------- Subscribed");
    }
}
