package com.example.salehe.kigambonibridge;

/**
 * Created by Salehe on 6/29/2016.
 */
public class Config {
    public static final String URL_GET_SITUATION = "http://192.168.43.20/android/bridge_situation.php";
    public static final String URL_GET_NEWS = "http://192.168.43.20/android/get_news.php";
    public static final String URL_GET_COST = "http://192.168.43.20/android/get_cost.php?id=";


    //JSON Tags
    /*data for bridge situation*/
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_TIME = "time";
    public static final String TAG_STATUS = "status";
    public static final String TAG_STATUS_ID = "id";


    /*home page status*/
    public static final String TAG_HOME_STAT = "home_status";


    /*data for price*/
    public static final String TAG_VEHICLE_TYPE = "name";
    public static final String TAG_COST = "price";
    public static final String TAG_STATUS_COST = "status";

    /*data for traffic news*/
    public static final String TAG_DESC = "description";
    public static final String TAG_INCTYPE = "incident_type";
    public static final String TAG_IMAGE = "image";
    public static final String TAG_UPDATEDTIME = "updated_time";

    public static final String STATUS_ID = "id";

    //employee id to pass with intent
//    public static final String EMP_ID = "emp_id";
}
