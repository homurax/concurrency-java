package com.homurax.chapter03.nearest.knn.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BankMarketing extends Sample {

    private byte age;
    private byte jobAdmin;
    private byte jobBlueCollar;
    private byte jobEntrepreneur;
    private byte jobHousemaid;
    private byte jobManagement;
    private byte jobRetired;
    private byte jobSelfEmployed;
    private byte jobServices;
    private byte jobStudent;
    private byte jobTechnician;
    private byte jobUnemployed;
    private byte jobUnknown;
    private byte maritalDivorced;
    private byte maritalMarried;
    private byte maritalSingle;
    private byte maritalUnknown;
    private byte educationBasic4y;
    private byte educationBasic6y;
    private byte educationBasic9y;
    private byte educationHighSchool;
    private byte educationIlliterate;
    private byte educationProfessionalCourse;
    private byte educationUniversityDegree;
    private byte educationUnknown;
    private byte creditNo;
    private byte creditYes;
    private byte creditUnknown;
    private byte housingNo;
    private byte housingYes;
    private byte housingUnknown;
    private byte loanNo;
    private byte loanYes;
    private byte loanUnknown;
    private byte contactCellular;
    private byte contactTelephone;
    private byte contactJan;
    private byte contactFeb;
    private byte contactMar;
    private byte contactApr;
    private byte contactMay;
    private byte contactJun;
    private byte contactJul;
    private byte contactAug;
    private byte contactSep;
    private byte contactOct;
    private byte contactNov;
    private byte contactDec;
    private byte contactMon;
    private byte contactTue;
    private byte contactWed;
    private byte contactThu;
    private byte contactFri;
    private int duration;
    private byte campaign;
    private int pdays;
    private byte pdaysNever;
    private byte previous;
    private byte poutcomeFailure;
    private byte poutcomeNonexistent;
    private byte poutcomeSuccess;
    private float empVarRate;
    private float consPriceIdx;
    private float consConfIdx;
    private float euribor3m;
    private float nrEmployed;
    private String target;

    public void setData(String[] data) throws Exception {

        if (data.length != 67) {
            throw new Exception("Wrong data length: " + data.length);
        }
        age = Byte.valueOf(data[0]);
        jobAdmin = Byte.valueOf(data[1]);
        jobBlueCollar = Byte.valueOf(data[2]);
        jobEntrepreneur = Byte.valueOf(data[3]);
        jobHousemaid = Byte.valueOf(data[4]);
        jobManagement = Byte.valueOf(data[5]);
        jobRetired = Byte.valueOf(data[6]);
        jobSelfEmployed = Byte.valueOf(data[7]);
        jobServices = Byte.valueOf(data[8]);
        jobStudent = Byte.valueOf(data[9]);
        jobTechnician = Byte.valueOf(data[10]);
        jobUnemployed = Byte.valueOf(data[11]);
        jobUnknown = Byte.valueOf(data[12]);
        maritalDivorced = Byte.valueOf(data[13]);
        maritalMarried = Byte.valueOf(data[14]);
        maritalSingle = Byte.valueOf(data[15]);
        maritalUnknown = Byte.valueOf(data[16]);
        educationBasic4y = Byte.valueOf(data[17]);
        educationBasic6y = Byte.valueOf(data[18]);
        educationBasic9y = Byte.valueOf(data[19]);
        educationHighSchool = Byte.valueOf(data[20]);
        educationIlliterate = Byte.valueOf(data[21]);
        educationProfessionalCourse = Byte.valueOf(data[22]);
        educationUniversityDegree = Byte.valueOf(data[23]);
        educationUnknown = Byte.valueOf(data[24]);
        creditNo = Byte.valueOf(data[25]);
        creditYes = Byte.valueOf(data[26]);
        creditUnknown = Byte.valueOf(data[27]);
        housingNo = Byte.valueOf(data[28]);
        housingYes = Byte.valueOf(data[29]);
        housingUnknown = Byte.valueOf(data[30]);
        loanNo = Byte.valueOf(data[31]);
        loanYes = Byte.valueOf(data[32]);
        loanUnknown = Byte.valueOf(data[33]);
        contactCellular = Byte.valueOf(data[34]);
        contactTelephone = Byte.valueOf(data[35]);
        contactJan = Byte.valueOf(data[36]);
        contactFeb = Byte.valueOf(data[37]);
        contactMar = Byte.valueOf(data[38]);
        contactApr = Byte.valueOf(data[39]);
        contactMay = Byte.valueOf(data[40]);
        contactJun = Byte.valueOf(data[41]);
        contactJul = Byte.valueOf(data[42]);
        contactAug = Byte.valueOf(data[43]);
        contactSep = Byte.valueOf(data[44]);
        contactOct = Byte.valueOf(data[45]);
        contactNov = Byte.valueOf(data[46]);
        contactDec = Byte.valueOf(data[47]);
        contactMon = Byte.valueOf(data[48]);
        contactTue = Byte.valueOf(data[49]);
        contactWed = Byte.valueOf(data[50]);
        contactThu = Byte.valueOf(data[51]);
        contactFri = Byte.valueOf(data[52]);
        duration = Integer.valueOf(data[53]);
        campaign = Byte.valueOf(data[54]);
        pdays = Integer.valueOf(data[55]);
        pdaysNever = Byte.valueOf(data[56]);
        previous = Byte.valueOf(data[57]);
        poutcomeFailure = Byte.valueOf(data[58]);
        poutcomeNonexistent = Byte.valueOf(data[59]);
        poutcomeSuccess = Byte.valueOf(data[60]);
        empVarRate = Float.valueOf(data[61]);
        consPriceIdx = Float.valueOf(data[62]);
        consConfIdx = Float.valueOf(data[63]);
        euribor3m = Float.valueOf(data[64]);
        nrEmployed = Float.valueOf(data[65]);
        target = data[66];
    }

    @Override
    public String getTag() {
        return target;
    }

    @Override
    public double[] getExample() {
        double[] result = new double[66];
        result[0] = age;
        result[1] = jobAdmin;
        result[2] = jobBlueCollar;
        result[3] = jobEntrepreneur;
        result[4] = jobHousemaid;
        result[5] = jobManagement;
        result[6] = jobRetired;
        result[7] = jobSelfEmployed;
        result[8] = jobServices;
        result[9] = jobStudent;
        result[10] = jobTechnician;
        result[11] = jobUnemployed;
        result[12] = jobUnknown;
        result[13] = maritalDivorced;
        result[14] = maritalMarried;
        result[15] = maritalSingle;
        result[16] = maritalUnknown;
        result[17] = educationBasic4y;
        result[18] = educationBasic6y;
        result[19] = educationBasic9y;
        result[20] = educationHighSchool;
        result[21] = educationIlliterate;
        result[22] = educationProfessionalCourse;
        result[23] = educationUniversityDegree;
        result[24] = educationUnknown;
        result[25] = creditNo;
        result[26] = creditYes;
        result[27] = creditUnknown;
        result[28] = housingNo;
        result[29] = housingYes;
        result[30] = housingUnknown;
        result[31] = loanNo;
        result[32] = loanYes;
        result[33] = loanUnknown;
        result[34] = contactCellular;
        result[35] = contactTelephone;
        result[36] = contactJan;
        result[37] = contactFeb;
        result[38] = contactMar;
        result[39] = contactApr;
        result[40] = contactMay;
        result[41] = contactJun;
        result[42] = contactJul;
        result[43] = contactAug;
        result[44] = contactSep;
        result[45] = contactOct;
        result[46] = contactNov;
        result[47] = contactDec;
        result[48] = contactMon;
        result[49] = contactTue;
        result[50] = contactWed;
        result[51] = contactThu;
        result[52] = contactFri;
        result[53] = duration;
        result[54] = campaign;
        result[55] = pdays;
        result[56] = pdaysNever;
        result[57] = previous;
        result[58] = poutcomeFailure;
        result[59] = poutcomeNonexistent;
        result[60] = poutcomeSuccess;
        result[61] = empVarRate;
        result[62] = consPriceIdx;
        result[63] = consConfIdx;
        result[64] = euribor3m;
        result[65] = nrEmployed;
        return result;
    }


}
