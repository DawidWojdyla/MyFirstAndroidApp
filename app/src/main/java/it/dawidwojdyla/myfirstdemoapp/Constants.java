package it.dawidwojdyla.myfirstdemoapp;

public class Constants {


    public static final int HTTP_REQUEST_CONNECT_RETRIES_NUMBER = 3;
    public static final String USERNAME = "uzytkownik";
    public static final String PASSWORD = "haslodostepudozewnetrznegoapi";
    public static final String API_HOST = "http://192.168.0.11/androiddemo/";
    public static final String GET_DATA_ACTION = "get_last_inserted_data" ;
    public static final String SEND_DATA_ACTION = "send_new_data" ;

    public static final String GET_DATA_ERROR_MESSAGE = "Nie udało się pobrać danych";
    public static final String SEND_DATA_ERROR_MESSAGE = "błąd";
    public static final String SEND_DATA_SUCCESS_MESSAGE = "zapisane";
    public static final String NICKNAME_LENGTH_ERROR_MESSAGE = "Nick może zawierać od 1 do 30 znaków (wycinane białe znaki na początku i na końcu)";
    public static final int MIN_NICKNAME_LENGTH = 1;
    public static final int MAX_NICKNAME_LENGTH = 30;
}
