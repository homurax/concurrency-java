package com.homurax.knn.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
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
        age = Byte.parseByte(data[0]);
        jobAdmin =  Byte.parseByte(data[1]);
        jobBlueCollar =  Byte.parseByte(data[2]);
        jobEntrepreneur =  Byte.parseByte(data[3]);
        jobHousemaid =  Byte.parseByte(data[4]);
        jobManagement =  Byte.parseByte(data[5]);
        jobRetired =  Byte.parseByte(data[6]);
        jobSelfEmployed =  Byte.parseByte(data[7]);
        jobServices =  Byte.parseByte(data[8]);
        jobStudent =  Byte.parseByte(data[9]);
        jobTechnician =  Byte.parseByte(data[10]);
        jobUnemployed =  Byte.parseByte(data[11]);
        jobUnknown =  Byte.parseByte(data[12]);
        maritalDivorced =  Byte.parseByte(data[13]);
        maritalMarried =  Byte.parseByte(data[14]);
        maritalSingle =  Byte.parseByte(data[15]);
        maritalUnknown =  Byte.parseByte(data[16]);
        educationBasic4y =  Byte.parseByte(data[17]);
        educationBasic6y =  Byte.parseByte(data[18]);
        educationBasic9y =  Byte.parseByte(data[19]);
        educationHighSchool =  Byte.parseByte(data[20]);
        educationIlliterate =  Byte.parseByte(data[21]);
        educationProfessionalCourse =  Byte.parseByte(data[22]);
        educationUniversityDegree =  Byte.parseByte(data[23]);
        educationUnknown =  Byte.parseByte(data[24]);
        creditNo =  Byte.parseByte(data[25]);
        creditYes =  Byte.parseByte(data[26]);
        creditUnknown =  Byte.parseByte(data[27]);
        housingNo =  Byte.parseByte(data[28]);
        housingYes =  Byte.parseByte(data[29]);
        housingUnknown =  Byte.parseByte(data[30]);
        loanNo =  Byte.parseByte(data[31]);
        loanYes =  Byte.parseByte(data[32]);
        loanUnknown =  Byte.parseByte(data[33]);
        contactCellular =  Byte.parseByte(data[34]);
        contactTelephone =  Byte.parseByte(data[35]);
        contactJan =  Byte.parseByte(data[36]);
        contactFeb =  Byte.parseByte(data[37]);
        contactMar =  Byte.parseByte(data[38]);
        contactApr =  Byte.parseByte(data[39]);
        contactMay =  Byte.parseByte(data[40]);
        contactJun =  Byte.parseByte(data[41]);
        contactJul =  Byte.parseByte(data[42]);
        contactAug =  Byte.parseByte(data[43]);
        contactSep =  Byte.parseByte(data[44]);
        contactOct =  Byte.parseByte(data[45]);
        contactNov =  Byte.parseByte(data[46]);
        contactDec =  Byte.parseByte(data[47]);
        contactMon =  Byte.parseByte(data[48]);
        contactTue =  Byte.parseByte(data[49]);
        contactWed =  Byte.parseByte(data[50]);
        contactThu =  Byte.parseByte(data[51]);
        contactFri =  Byte.parseByte(data[52]);
        duration = Integer.parseInt(data[53]);
        campaign =  Byte.parseByte(data[54]);
        pdays = Integer.parseInt(data[55]);
        pdaysNever =  Byte.parseByte(data[56]);
        previous =  Byte.parseByte(data[57]);
        poutcomeFailure =  Byte.parseByte(data[58]);
        poutcomeNonexistent =  Byte.parseByte(data[59]);
        poutcomeSuccess =  Byte.parseByte(data[60]);
        empVarRate = Float.parseFloat(data[61]);
        consPriceIdx = Float.parseFloat(data[62]);
        consConfIdx = Float.parseFloat(data[63]);
        euribor3m = Float.parseFloat(data[64]);
        nrEmployed = Float.parseFloat(data[65]);
        target = data[66];
    }

    @Override
    public String getTag() {
        return target;
    }

    @Override
    public double[] getExample() {
        double[] ret = new double[66];
        ret[0] = age;
        ret[1] = jobAdmin;
        ret[2] = jobBlueCollar;
        ret[3] = jobEntrepreneur;
        ret[4] = jobHousemaid;
        ret[5] = jobManagement;
        ret[6] = jobRetired;
        ret[7] = jobSelfEmployed;
        ret[8] = jobServices;
        ret[9] = jobStudent;
        ret[10] = jobTechnician;
        ret[11] = jobUnemployed;
        ret[12] = jobUnknown;
        ret[13] = maritalDivorced;
        ret[14] = maritalMarried;
        ret[15] = maritalSingle;
        ret[16] = maritalUnknown;
        ret[17] = educationBasic4y;
        ret[18] = educationBasic6y;
        ret[19] = educationBasic9y;
        ret[20] = educationHighSchool;
        ret[21] = educationIlliterate;
        ret[22] = educationProfessionalCourse;
        ret[23] = educationUniversityDegree;
        ret[24] = educationUnknown;
        ret[25] = creditNo;
        ret[26] = creditYes;
        ret[27] = creditUnknown;
        ret[28] = housingNo;
        ret[29] = housingYes;
        ret[30] = housingUnknown;
        ret[31] = loanNo;
        ret[32] = loanYes;
        ret[33] = loanUnknown;
        ret[34] = contactCellular;
        ret[35] = contactTelephone;
        ret[36] = contactJan;
        ret[37] = contactFeb;
        ret[38] = contactMar;
        ret[39] = contactApr;
        ret[40] = contactMay;
        ret[41] = contactJun;
        ret[42] = contactJul;
        ret[43] = contactAug;
        ret[44] = contactSep;
        ret[45] = contactOct;
        ret[46] = contactNov;
        ret[47] = contactDec;
        ret[48] = contactMon;
        ret[49] = contactTue;
        ret[50] = contactWed;
        ret[51] = contactThu;
        ret[52] = contactFri;
        ret[53] = duration;
        ret[54] = campaign;
        ret[55] = pdays;
        ret[56] = pdaysNever;
        ret[57] = previous;
        ret[58] = poutcomeFailure;
        ret[59] = poutcomeNonexistent;
        ret[60] = poutcomeSuccess;
        ret[61] = empVarRate;
        ret[62] = consPriceIdx;
        ret[63] = consConfIdx;
        ret[64] = euribor3m;
        ret[65] = nrEmployed;
        return ret;
    }


}
