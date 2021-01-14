public class Time {

    int hour;
    int minute;

    int second;


    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }


    public void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;

    }

    public int getHour() {
        return hour;
    }


    public int getMinute() {
        return minute;
    }


    public int getSecond() {
        return second;
    }


    @Override
    public String toString() {
        return "Time [hour=" + hour + ", minute=" + minute + ", second=" + second + "]";
    }





}