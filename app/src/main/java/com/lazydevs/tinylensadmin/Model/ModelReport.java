package com.lazydevs.tinylensadmin.Model;

import com.google.firebase.database.Exclude;

public class ModelReport {

    String imageUrl;
    String reporterId;
    String reportedId;


    @Exclude
    String reporterName, reportedName;

    @Exclude
    public String getReporterName() {
        return reporterName;
    }

    @Exclude
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    @Exclude
    public String getReportedName() {
        return reportedName;
    }

    @Exclude
    public void setReportedName(String reportedName) {
        this.reportedName = reportedName;
    }

    public ModelReport() {
    }

    public ModelReport(String imageUrl, String reporterId, String reportedId) {
        this.imageUrl = imageUrl;
        this.reporterId = reporterId;
        this.reportedId = reportedId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getReportedId() {
        return reportedId;
    }

    public void setReportedId(String reportedId) {
        this.reportedId = reportedId;
    }
}
