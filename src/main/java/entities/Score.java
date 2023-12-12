package entities;

public class Score {
    static boolean revive;
    static int currentscore=0;
    static int score;
    static int highscore;

    public static boolean isRevive() {
        if (score < 0) {
            revive = false;
        }
        else {
            revive = true;
        }
        return revive;
    }

    public static void setRevive(boolean revive1) {
        revive = revive1;
    }

    public static int getScore() {
        return score;
    }

    public static int getCurrentscore() {
        if(revive){
            System.out.println("revived");
            System.out.println(currentscore);
            return currentscore;
        }
        else{
            currentscore=0;
            return 0;
        }
    }

    public static void setCurrentscore(int currentscore1) {
        System.out.println(currentscore);
        currentscore = currentscore1;
    }

    public static void setScore(int score) {
        Score.score = score;
    }

    public static int getHighscore() {
        return highscore;
    }

    public static void setHighscore(int highscore1) {
        highscore = highscore1;
    }
    static public void highscore(){
        if(score>highscore){
            highscore=score;
        }
    }
    static public void revive(){
        score=currentscore;
    }
}
