package com.example.codeshastrahealthcarev1;

public class PatientDetails {
    //    private String name_s, date_s, height_s, weight_s, bg_s, allergies_s, familyhistory_s, ongoingmed_s, chronicillness_s;
    private String mPatientName;
    private String mDateS;
    private String mHeight;
    private String mWeight;

    public PatientDetails(String patientName, String Date, String Height, String Weight)
    {
        this.mPatientName = patientName;
    }

    public PatientDetails()
    {

    }

    public String getmPatientName() {
        return mPatientName;
    }

    public void setmPatientName(String mPatientName) {
        this.mPatientName = mPatientName;
    }
}
