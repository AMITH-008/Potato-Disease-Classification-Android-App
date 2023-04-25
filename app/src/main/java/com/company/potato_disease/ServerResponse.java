package com.company.potato_disease;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    @SerializedName("Class")
    String classType;

    @SerializedName("Confidence")
    String confidence;

    public String getClassType() {
        return classType;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "classType='" + classType + '\'' +
                ", confidence='" + confidence + '\'' +
                '}';
    }
}
