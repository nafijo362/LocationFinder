package com.Naftanan.locationfinderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "location";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String[][] GTA_LOCATIONS = {
            {"Oshawa", "43.8971", "-78.8658"},
            {"Ajax", "43.8500", "-79.0204"},
            {"Pickering", "43.8353", "-79.0890"},
            {"Scarborough", "43.7731", "-79.2578"},
            {"Downtown Toronto", "43.6510", "-79.3470"},
            {"Mississauga", "43.5890", "-79.6441"},
            {"Brampton", "43.7315", "-79.7624"},
            {"Markham", "43.8561", "-79.3370"},
            {"Richmond Hill", "43.8828", "-79.4403"},
            {"Vaughan", "43.8372", "-79.5083"},
            {"Etobicoke", "43.6542", "-79.5678"},
            {"North York", "43.7615", "-79.4111"},
            {"East York", "43.7130", "-79.2995"},
            {"York", "43.6890", "-79.4771"},
            {"Newmarket", "44.0592", "-79.4613"},
            {"Aurora", "44.0065", "-79.4504"},
            {"King City", "43.9250", "-79.5313"},
            {"Nobleton", "43.9013", "-79.6465"},
            {"Bolton", "43.8748", "-79.7357"},
            {"Woodbridge", "43.7895", "-79.6162"},
            {"Concord", "43.7948", "-79.4699"},
            {"Kleinburg", "43.8390", "-79.6258"},
            {"Thornhill", "43.8074", "-79.4199"},
            {"Maple", "43.8555", "-79.5070"},
            {"Stouffville", "43.9703", "-79.2447"},
            {"Uxbridge", "44.1095", "-79.1200"},
            {"Port Perry", "44.1037", "-78.9445"},
            {"Whitby", "43.8971", "-78.9429"},
            {"Brooklin", "43.9617", "-78.9464"},
            {"Burlington", "43.3255", "-79.7990"},
            {"Oakville", "43.4675", "-79.6877"},
            {"Milton", "43.5183", "-79.8774"},
            {"Georgetown", "43.6487", "-79.9215"},
            {"Acton", "43.6296", "-80.0455"},
            {"Caledon", "43.8620", "-79.9676"},
            {"Orangeville", "43.9192", "-80.0943"},
            {"Erin", "43.7734", "-80.0673"},
            {"Halton Hills", "43.6302", "-79.9500"},
            {"Campbellville", "43.4970", "-79.9518"},
            {"Alton", "43.8618", "-80.0678"},
            {"Inglewood", "43.7916", "-79.9269"},
            {"Cheltenham", "43.7527", "-79.9118"},
            {"Belfountain", "43.7937", "-80.0171"},
            {"Kinghorn", "43.9380", "-79.5578"},
            {"Gormley", "43.9641", "-79.3834"},
            {"Goodwood", "44.0437", "-79.1877"},
            {"Claremont", "43.9936", "-79.1471"},
            {"Greenwood", "43.9144", "-79.0596"},
            {"Mount Albert", "44.1456", "-79.3223"},
            {"Pefferlaw", "44.3052", "-79.2414"},
            {"Sutton", "44.3008", "-79.3677"},
            {"Keswick", "44.2149", "-79.4625"},
            {"Beaverton", "44.4298", "-79.1436"},
            {"Cannington", "44.3514", "-79.0306"},
            {"Sunderland", "44.2048", "-79.0688"},
            {"Holland Landing", "44.0994", "-79.4886"},
            {"Queensville", "44.1411", "-79.4461"},
            {"Sharon", "44.1181", "-79.4323"},
            {"Mount Pleasant", "43.7887", "-79.7262"},
            {"Hornby", "43.5186", "-79.8928"},
            {"Moffat", "43.5065", "-79.9895"},
            {"Glen Williams", "43.6486", "-79.9265"},
            {"Norval", "43.6376", "-79.9150"},
            {"Limehouse", "43.6492", "-79.9818"},
            {"Terra Cotta", "43.7373", "-79.9783"},
            {"Ballinafad", "43.7068", "-80.0028"},
            {"Scotsdale", "43.7466", "-79.8995"},
            {"Palgrave", "43.9720", "-79.8365"},
            {"Tottenham", "44.0225", "-79.8071"},
            {"Bond Head", "44.0912", "-79.6774"},
            {"Bradford", "44.1140", "-79.5591"},
            {"Alliston", "44.1516", "-79.8660"},
            {"Angus", "44.3246", "-79.8845"},
            {"Cookstown", "44.1704", "-79.7006"},
            {"Innisfil", "44.3001", "-79.6113"},
            {"Barrie", "44.3894", "-79.6903"},
            {"Orillia", "44.6082", "-79.4207"},
            {"Wasaga Beach", "44.5186", "-80.0160"},
            {"Collingwood", "44.5008", "-80.2165"},
            {"Stayner", "44.4124", "-80.0851"},
            {"Elmvale", "44.5788", "-79.8665"},
            {"Midland", "44.7496", "-79.8890"},
            {"Penetanguishene", "44.7698", "-79.9333"},
            {"Victoria Harbour", "44.7571", "-79.7418"},
            {"Waubaushene", "44.7688", "-79.7194"},
            {"Port McNicoll", "44.7436", "-79.8001"},
            {"Hawkestone", "44.4858", "-79.4853"},
            {"Brechin", "44.5528", "-79.1808"},
            {"Lagoon City", "44.5530", "-79.2148"},
            {"Rama", "44.6582", "-79.3383"},
            {"Washago", "44.7593", "-79.3435"},
            {"Cumberland Beach", "44.7373", "-79.4001"},
            {"Severn Bridge", "44.8127", "-79.3440"},
            {"Gravenhurst", "44.9187", "-79.3667"},
            {"Bracebridge", "45.0362", "-79.3033"}
    };


    private void insertInitialLocations(SQLiteDatabase db) {
        for (String[] location : GTA_LOCATIONS) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ADDRESS, location[0]);
            values.put(COLUMN_LATITUDE, Double.parseDouble(location[1]));
            values.put(COLUMN_LONGITUDE, Double.parseDouble(location[2]));
            db.insert(TABLE_NAME, null, values);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ADDRESS + " TEXT NOT NULL, "
                + COLUMN_LATITUDE + " REAL NOT NULL, "
                + COLUMN_LONGITUDE + " REAL NOT NULL)";
        db.execSQL(createTable);

        insertInitialLocations(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CRUD methods below

public long addLocation(String address, double latitude, double longitude) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_ADDRESS, address);
    values.put(COLUMN_LATITUDE, latitude);
    values.put(COLUMN_LONGITUDE, longitude);
    return db.insert(TABLE_NAME, null, values);
}

public int deleteLocation(String address) {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.delete(TABLE_NAME, COLUMN_ADDRESS + "=?", new String[]{address});
}

public int updateLocation(String address, double latitude, double longitude) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_LATITUDE, latitude);
    values.put(COLUMN_LONGITUDE, longitude);
    return db.update(TABLE_NAME, values, COLUMN_ADDRESS + "=?", new String[]{address});
}

public Cursor queryLocation(String address) {
    SQLiteDatabase db = this.getReadableDatabase();
    return db.query(TABLE_NAME, new String[]{COLUMN_LATITUDE, COLUMN_LONGITUDE},
            COLUMN_ADDRESS + "=?", new String[]{address}, null, null, null);
}
}
