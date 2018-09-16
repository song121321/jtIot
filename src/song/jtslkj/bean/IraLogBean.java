package song.jtslkj.bean;

import java.io.Serializable;

public class IraLogBean implements Serializable {

    private String updateTime;
    private String position;
    private String lastStartTime;
    private String lastEndTime;
    private String lastCardNumber;
    private double lastElec;
    private double lastWater;
    private double lastStartMoney;
    private double lastEndMoney;
    private double lastConsumeMoney;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(String lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public String getLastCardNumber() {
        return lastCardNumber;
    }

    public void setLastCardNumber(String lastCardNumber) {
        this.lastCardNumber = lastCardNumber;
    }

    public double getLastElec() {
        return lastElec;
    }

    public void setLastElec(double lastElec) {
        this.lastElec = lastElec;
    }

    public double getLastWater() {
        return lastWater;
    }

    public void setLastWater(double lastWater) {
        this.lastWater = lastWater;
    }

    public double getLastStartMoney() {
        return lastStartMoney;
    }

    public void setLastStartMoney(double lastStartMoney) {
        this.lastStartMoney = lastStartMoney;
    }

    public double getLastEndMoney() {
        return lastEndMoney;
    }

    public void setLastEndMoney(double lastEndMoney) {
        this.lastEndMoney = lastEndMoney;
    }

    public double getLastConsumeMoney() {
        return lastConsumeMoney;
    }

    public void setLastConsumeMoney(double lastConsumeMoney) {
        this.lastConsumeMoney = lastConsumeMoney;
    }

    public String getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(String lastEndTime) {
        this.lastEndTime = lastEndTime;
    }
}
