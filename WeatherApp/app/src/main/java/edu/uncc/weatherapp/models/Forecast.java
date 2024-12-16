package edu.uncc.weatherapp.models;

//Assignment15
//Forecast.java
//Ethan Fowler and Raziuddin Syed Khaja
public class Forecast
{
    String startTime;
    int temperature;
    String windSpeed;
    String weatherForecast;
    String icon;

    //Sadia helped fix this
    String precepitationChance;


    public Forecast()
    {

    }

    public Forecast(String startTime, int temperature, String precepitationChance, String windSpeed, String weatherForecast, String icon)
    {
        this.startTime = startTime;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.weatherForecast = weatherForecast;
        this.icon = icon;

        //Sadia helped fix this
        this.precepitationChance = precepitationChance;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    //Sadia helped fix these
    public String getPrecepitationChance() {
        return precepitationChance;
    }
    public void setPrecepitationChance(String precepitationChance) {
        this.precepitationChance = precepitationChance;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(String weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Forecast{" + "startTime='" + startTime + '\'' + ", temperature=" + temperature + ", precepitationChance=" + precepitationChance + ", windSpeed='" + windSpeed + '\'' + ", weatherForecast='" + weatherForecast + '\'' + ", icon='" + icon + '\'' + '}';
    }
}
