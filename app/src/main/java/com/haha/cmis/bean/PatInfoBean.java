package com.haha.cmis.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Haha on 2017/10/17.
 */

public class PatInfoBean implements Parcelable {
    public static final String COLUMN_NAME_INP_NO = "inp_no";
    public static final String COLUMN_NAME_PATIENT_ID = "patient_id";
    public static final String COLUMN_NAME_VISIT_ID = "visit_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_SEX = "sex";
    public static final String COLUMN_NAME_BIRTH_DATE = "birth_date";
    public static final String COLUMN_NAME_CHARGE_TYPE = "charge_type";
    public static final String COLUMN_NAME_IDENTITY = "identity";
    public static final String COLUMN_NAME_DIAGNOSIS = "diagnosis";
    public static final String COLUMN_NAME_ADMISSION_DATE_TIME = "admission_date_time";
    public static final String COLUMN_NAME_OPERATING_DATE = "operating_date";
    public static final String COLUMN_NAME_ROOM_NO = "room_no";
    public static final String COLUMN_NAME_BED_NO = "bed_no";
    public static final String COLUMN_NAME_BODY_WEIGHT = "body_weight";
    public static final String COLUMN_NAME_BODY_HEIGHT = "body_height";
    public static final String COLUMN_BLOOD_TYPE = "blood_type";
    public static final String COLUMN_NAME_DEPT_CODE = "dept_code";
    public static final String COLUMN_NAME_DEPT_NAME = "dept_name";
    public static final String COLUMN_NAME_WARD_CODE = "ward_code";
    public static final String COLUMN_NAME_WARD_NAME = "ward_name";
    public static final String COLUMN_NAME_PATIENT_CLASS = "patient_class";
    public static final String COLUMN_NAME_PATIENT_COND = "patient_cond";
    public static final String COLUMN_NAME_DOCTOR_NAME = "doctor_name";
    public static final String COLUMN_NAME_PATIENT_IMAGE = "patient_image";
    public static final String COLUMN_NAME_ALERGY_DRUGS = "alergy_drugs";
    public static final String COLUMN_NAME_PREDISCHARGE_DATE = "predischarge_date";
    public static final String COLUMN_NAME_BED_APPROVED_TYPE = "bed_approved_type";
    public static final String COLUMN_NAME_NATION = "nation";
    public static final String COLUMN_NAME_MAILING_ADDRESS = "mailing_address";
    public static final String COLUMN_NAME_NEXT_OF_KIN = "next_of_kin";
    public static final String COLUMN_NAME_NEXT_OF_KIN_PHONE = "next_of_kin_phone";
    public static final String COLUMN_NAME_PHONE_NUMBER_HOME = "phone_number_home";
    public static final String COLUMN_NAME_PHONE_NUMBER_BUSINESS = "phone_number_business";
    public static final String COLUMN_NAME_PREPAYMENTS = "prepayments";
    public static final String COLUMN_NAME_TOTAL_COSTS = "total_costs";
    public static final String COLUMN_NAME_SETTLED_FLAG = "settled_flag";

    private String inp_no;
    private String patient_id;
    private Integer visit_id;
    private String name;
    private String sex;
    private String birth_date;
    private String charge_type;
    private String identity;
    private String diagnosis;
    private String admission_date_time;
    private String operating_date;
    private String bed_no;
    private String body_weight;
    private String body_height;
    private String blood_type;
    private String dept_code;
    private String dept_name;
    private String ward_code;
    private String ward_name;
    private String patient_class;
    private String patient_cond;
    private String doctor_name;
    private String patient_image;
    private String alergy_drugs;
    private String predischarge_date;
    private String room_no;
    private String bed_approved_type;
    private String nation;
    private String mailing_address;
    private String next_of_kin;
    private String next_of_kin_phone;
    private String phone_number_home;
    private String phone_number_business;
    private String prepayments;
    private String total_costs;
    private Integer settled_flag;


    public String getPrepayments() {
        return prepayments;
    }

    public void setPrepayments(String prepayments) {
        this.prepayments = prepayments;
    }

    public String getTotal_costs() {
        return total_costs;
    }

    public void setTotal_costs(String total_costs) {
        this.total_costs = total_costs;
    }

    public Integer getSettled_flag() {
        return settled_flag;
    }

    public void setSettled_flag(Integer settled_flag) {
        this.settled_flag = settled_flag;
    }

    public String getInp_no() {
        return inp_no;
    }

    public void setInp_no(String inp_no) {
        this.inp_no = inp_no;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(Integer visit_id) {
        this.visit_id = visit_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getAdmission_date_time() {
        return admission_date_time;
    }

    public void setAdmission_date_time(String admission_date_time) {
        this.admission_date_time = admission_date_time;
    }

    public String getOperating_date() {
        return operating_date;
    }

    public void setOperating_date(String operating_date) {
        this.operating_date = operating_date;
    }

    public String getBed_no() {
        return bed_no;
    }

    public void setBed_no(String bed_no) {
        this.bed_no = bed_no;
    }

    public String getBody_weight() {
        return body_weight;
    }

    public void setBody_weight(String body_weight) {
        this.body_weight = body_weight;
    }

    public String getBody_height() {
        return body_height;
    }

    public void setBody_height(String body_height) {
        this.body_height = body_height;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getWard_code() {
        return ward_code;
    }

    public void setWard_code(String ward_code) {
        this.ward_code = ward_code;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getPatient_class() {
        return patient_class;
    }

    public void setPatient_class(String patient_class) {
        this.patient_class = patient_class;
    }

    public String getPatient_cond() {
        return patient_cond;
    }

    public void setPatient_cond(String patient_cond) {
        this.patient_cond = patient_cond;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPatient_image() {
        return patient_image;
    }

    public void setPatient_image(String patient_image) {
        this.patient_image = patient_image;
    }

    public String getAlergy_drugs() {
        return alergy_drugs;
    }

    public void setAlergy_drugs(String alergy_drugs) {
        this.alergy_drugs = alergy_drugs;
    }

    public String getPredischarge_date() {
        return predischarge_date;
    }

    public void setPredischarge_date(String predischarge_date) {
        this.predischarge_date = predischarge_date;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getBed_approved_type() {
        return bed_approved_type;
    }

    public void setBed_approved_type(String bed_approved_type) {
        this.bed_approved_type = bed_approved_type;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMailing_address() {
        return mailing_address;
    }

    public void setMailing_address(String mailing_address) {
        this.mailing_address = mailing_address;
    }

    public String getNext_of_kin() {
        return next_of_kin;
    }

    public void setNext_of_kin(String next_of_kin) {
        this.next_of_kin = next_of_kin;
    }

    public String getNext_of_kin_phone() {
        return next_of_kin_phone;
    }

    public void setNext_of_kin_phone(String next_of_kin_phone) {
        this.next_of_kin_phone = next_of_kin_phone;
    }

    public String getPhone_number_home() {
        return phone_number_home;
    }

    public void setPhone_number_home(String phone_number_home) {
        this.phone_number_home = phone_number_home;
    }

    public String getPhone_number_business() {
        return phone_number_business;
    }

    public void setPhone_number_business(String phone_number_business) {
        this.phone_number_business = phone_number_business;
    }


    public PatInfoBean(String inp_no, String patient_id, Integer visit_id, String name, String sex, String birth_date, String charge_type, String identity, String diagnosis, String admission_date_time, String operating_date, String bed_no, String body_weight, String body_height, String blood_type, String dept_code, String dept_name, String ward_code, String ward_name, String patient_class, String patient_cond, String doctor_name, String patient_image, String alergy_drugs, String predischarge_date, String room_no, String bed_approved_type, String nation, String mailing_address, String next_of_kin, String next_of_kin_phone, String phone_number_home, String phone_number_business, String prepayments, String total_costs, Integer settled_flag) {
        this.inp_no = inp_no;
        this.patient_id = patient_id;
        this.visit_id = visit_id;
        this.name = name;
        this.sex = sex;
        this.birth_date = birth_date;
        this.charge_type = charge_type;
        this.identity = identity;
        this.diagnosis = diagnosis;
        this.admission_date_time = admission_date_time;
        this.operating_date = operating_date;
        this.bed_no = bed_no;
        this.body_weight = body_weight;
        this.body_height = body_height;
        this.blood_type = blood_type;
        this.dept_code = dept_code;
        this.dept_name = dept_name;
        this.ward_code = ward_code;
        this.ward_name = ward_name;
        this.patient_class = patient_class;
        this.patient_cond = patient_cond;
        this.doctor_name = doctor_name;
        this.patient_image = patient_image;
        this.alergy_drugs = alergy_drugs;
        this.predischarge_date = predischarge_date;
        this.room_no = room_no;
        this.bed_approved_type = bed_approved_type;
        this.nation = nation;
        this.mailing_address = mailing_address;
        this.next_of_kin = next_of_kin;
        this.next_of_kin_phone = next_of_kin_phone;
        this.phone_number_home = phone_number_home;
        this.phone_number_business = phone_number_business;
        this.prepayments = prepayments;
        this.total_costs = total_costs;
        this.settled_flag = settled_flag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.inp_no);
        dest.writeString(this.patient_id);
        dest.writeValue(this.visit_id);
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeString(this.birth_date);
        dest.writeString(this.charge_type);
        dest.writeString(this.identity);
        dest.writeString(this.diagnosis);
        dest.writeString(this.admission_date_time);
        dest.writeString(this.operating_date);
        dest.writeString(this.bed_no);
        dest.writeString(this.body_weight);
        dest.writeString(this.body_height);
        dest.writeString(this.blood_type);
        dest.writeString(this.dept_code);
        dest.writeString(this.dept_name);
        dest.writeString(this.ward_code);
        dest.writeString(this.ward_name);
        dest.writeString(this.patient_class);
        dest.writeString(this.patient_cond);
        dest.writeString(this.doctor_name);
        dest.writeString(this.patient_image);
        dest.writeString(this.alergy_drugs);
        dest.writeString(this.predischarge_date);
        dest.writeString(this.room_no);
        dest.writeString(this.bed_approved_type);
        dest.writeString(this.nation);
        dest.writeString(this.mailing_address);
        dest.writeString(this.next_of_kin);
        dest.writeString(this.next_of_kin_phone);
        dest.writeString(this.phone_number_home);
        dest.writeString(this.phone_number_business);
        dest.writeString(this.prepayments);
        dest.writeString(this.total_costs);
        dest.writeValue(this.settled_flag);
    }

    public PatInfoBean() {
    }

    protected PatInfoBean(Parcel in) {
        this.inp_no = in.readString();
        this.patient_id = in.readString();
        this.visit_id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.sex = in.readString();
        this.birth_date = in.readString();
        this.charge_type = in.readString();
        this.identity = in.readString();
        this.diagnosis = in.readString();
        this.admission_date_time = in.readString();
        this.operating_date = in.readString();
        this.bed_no = in.readString();
        this.body_weight = in.readString();
        this.body_height = in.readString();
        this.blood_type = in.readString();
        this.dept_code = in.readString();
        this.dept_name = in.readString();
        this.ward_code = in.readString();
        this.ward_name = in.readString();
        this.patient_class = in.readString();
        this.patient_cond = in.readString();
        this.doctor_name = in.readString();
        this.patient_image = in.readString();
        this.alergy_drugs = in.readString();
        this.predischarge_date = in.readString();
        this.room_no = in.readString();
        this.bed_approved_type = in.readString();
        this.nation = in.readString();
        this.mailing_address = in.readString();
        this.next_of_kin = in.readString();
        this.next_of_kin_phone = in.readString();
        this.phone_number_home = in.readString();
        this.phone_number_business = in.readString();
        this.prepayments = in.readString();
        this.total_costs = in.readString();
        this.settled_flag = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<PatInfoBean> CREATOR = new Creator<PatInfoBean>() {
        @Override
        public PatInfoBean createFromParcel(Parcel source) {
            return new PatInfoBean(source);
        }

        @Override
        public PatInfoBean[] newArray(int size) {
            return new PatInfoBean[size];
        }
    };
}
