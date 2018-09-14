package com.xin.seckill.pojo;

import java.util.Date;
import java.util.List;

/**
 * @author Luchaoxin
 * @Description: ${TODO}
 * @date 2018-09-14 0:15
 */
public class ReservationInfo {

    private Date startTime;

    private Date endTime;

    private String meetingName;

    private List<String> members;

    private String place;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
