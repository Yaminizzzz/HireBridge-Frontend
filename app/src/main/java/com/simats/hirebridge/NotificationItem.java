package com.simats.hirebridge;

public class NotificationItem {
    private String company;
    private String message;
    private int iconRes;

    public NotificationItem(String company, String message, int iconRes) {
        this.company = company;
        this.message = message;
        this.iconRes = iconRes;
    }

    public String getCompany() { return company; }
    public String getMessage() { return message; }
    public int getIconRes() { return iconRes; }
}
