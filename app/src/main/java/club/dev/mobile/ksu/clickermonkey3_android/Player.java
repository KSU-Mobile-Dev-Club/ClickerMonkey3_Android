package club.dev.mobile.ksu.clickermonkey3_android;

public class Player {
    public Player(String s, int i, String k)
    {
        String name = s;
        int score = i;
        String key = k;
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
