package club.dev.mobile.ksu.clickermonkey3_android;

import com.google.firebase.database.Exclude;

public class Player {
    String name;
    int score;
    String key;

    public Player() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Player(String s, int i, String k)
    {
        name = s;
        score = i;
        key = k;
    }
public Player() {}
    int score;
    String name;
    int firebaseKey;

    public Player(int score, String name) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFirebaseKey() {
        return firebaseKey;
    }

    public void setFirebaseKey(int firebaseKey) {
        this.firebaseKey = firebaseKey;
    }
}
