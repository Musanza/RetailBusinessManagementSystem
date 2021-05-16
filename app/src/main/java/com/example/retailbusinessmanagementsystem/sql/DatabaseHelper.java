package com.example.retailbusinessmanagementsystem.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.models.Cash;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.models.Users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static android.provider.CallLog.Calls.DATE;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rbms.db";
    private static final String TABLE_USER = "users";
    private static final String TABLE_PRODUCT = "products";
    private static final String TABLE_EXPENSE = "expenses";
    private static final String TABLE_PURCHASE = "purchases";
    private static final String TABLE_SALE = "sales";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_ANSWER = "answer";
    private static final String COLUMN_BARCODE = "barcode";
    //private static final String COLUMN_OPRICE = "o_price";
    private static final String COLUMN_SPRICE = "s_price";
    private static final String COLUMN_XPDATE = "xp_date";
    private static final String COLUMN_COST = "cost";
    private static final String COLUMN_QUANTITY= "qty";

    private Context context;

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_QUESTION + " TEXT,"
            + COLUMN_ANSWER + " TEXT" +")";

    private String CREATE_PROUCT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BARCODE + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_SPRICE + " TEXT,"
            + COLUMN_XPDATE + " TEXT" +")";

    private String CREATE_SALE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SALE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BARCODE + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_SPRICE + " TEXT,"
            + COLUMN_XPDATE + " TEXT" +")";

    private String CREATE_EXPENSES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_COST + " TEXT,"
            + COLUMN_XPDATE + " TEXT" +")";

    private String CREATE_PURCHASES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PURCHASE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_COST + " TEXT,"
            + COLUMN_QUANTITY + " INTEGER,"
            + COLUMN_XPDATE + " TEXT" +")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_PRODUCT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
    private String DROP_SALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_SALE;
    private String DROP_EXPENSE_TABLE = "DROP TABLE IF EXISTS " + TABLE_EXPENSE;
    private String DROP_PURCHASE_TABLE = "DROP TABLE IF EXISTS " + TABLE_PURCHASE;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PROUCT_TABLE);
        db.execSQL(CREATE_SALE_TABLE);
        db.execSQL(CREATE_EXPENSES_TABLE);
        db.execSQL(CREATE_PURCHASES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUCT_TABLE);
        db.execSQL(DROP_SALE_TABLE);
        db.execSQL(DROP_EXPENSE_TABLE);
        db.execSQL(DROP_PURCHASE_TABLE);
        onCreate(db);
    }

    public DatabaseHelper(Context context1, String s, Context context, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_QUESTION, user.getQuestion());
        values.put(COLUMN_ANSWER, user.getAnswer());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void updateUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_QUESTION, user.getQuestion());
        values.put(COLUMN_ANSWER, user.getAnswer());
        db.update(TABLE_USER, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String username) {
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public List<Users> getAllUser() {
        String[] columns = {
                COLUMN_ID,
                COLUMN_USERNAME,
                COLUMN_NAME,
                COLUMN_PASSWORD,
                COLUMN_QUESTION,
                COLUMN_ANSWER
        };

        String sortOrder =
                COLUMN_NAME + " ASC";
        List<Users> userList = new ArrayList<Users>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                user.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                user.setAnswer(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER)));

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public void addProduct(Products product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BARCODE, product.getBarcode());
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_SPRICE, product.getSprice());
        values.put(COLUMN_XPDATE, product.getDate());
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }

    public void updateProducts(Products product, int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BARCODE, product.getBarcode());
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_SPRICE, product.getSprice());
        values.put(COLUMN_XPDATE, product.getDate());
        db.update(TABLE_PRODUCT, values, COLUMN_ID + " = " + rowId, null);
        db.close();
    }

    public void deleteProduct(int rowId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PRODUCT, COLUMN_ID + "=" + rowId, null);
        db.close();
    }

    public boolean checkProduct(String barcode) {
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_BARCODE + " = ?";
        String[] selectionArgs = {barcode};
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public Cursor getProduct(String barcode) {
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_BARCODE + " = ?";
        String[] selectionArgs = {barcode};
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        List<Products> productList = new ArrayList<Products>();
        if (cursor.moveToFirst()) {
            do {
                Products product = new Products();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                product.setBarcode(cursor.getString(cursor.getColumnIndex(COLUMN_BARCODE)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                product.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_SPRICE)));
                product.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_XPDATE)));
                product.setCount(cursor.getCount());
                productList.add(product);
            } while (cursor.moveToNext());
        }
        return cursor;
    }

    public Cursor getProducts(String name) {
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        List<Products> productList = new ArrayList<Products>();
        if (cursor.moveToFirst()) {
            do {
                Products product = new Products();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                product.setBarcode(cursor.getString(cursor.getColumnIndex(COLUMN_BARCODE)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                product.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_SPRICE)));
                product.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_XPDATE)));
                product.setCount(cursor.getCount());
                productList.add(product);
            } while (cursor.moveToNext());
        }
        return cursor;
    }


    public void addSale(Products product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BARCODE, product.getBarcode());
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_SPRICE, product.getSprice());
        values.put(COLUMN_XPDATE, product.getDate());
        db.insert(TABLE_SALE, null, values);
        db.close();
    }

    public List<Products> getAllSales() {
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };

        String sortOrder = COLUMN_XPDATE + " ASC";
        List<Products> productList = new ArrayList<Products>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SALE,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Products product = new Products();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                product.setBarcode(cursor.getString(cursor.getColumnIndex(COLUMN_BARCODE)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                product.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_SPRICE)));
                product.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_XPDATE)));
                product.setCount(cursor.getCount());
                productList.add(product);
            } while (cursor.moveToNext());
        }
        //int count = cursor.getCount();
        cursor.close();
        db.close();
        return productList;
    }

    public void deleteSale(int rowId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SALE, COLUMN_ID + "=" + rowId, null);
        db.close();
    }

    public List<Products> getAllProducts() {
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };

        String sortOrder = COLUMN_ID + " DESC";
        List<Products> productList = new ArrayList<Products>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                null,
                null,
                COLUMN_NAME,
                null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Products product = new Products();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                product.setBarcode(cursor.getString(cursor.getColumnIndex(COLUMN_BARCODE)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                //product.setOprice(cursor.getString(cursor.getColumnIndex(COLUMN_OPRICE)));
                product.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_SPRICE)));
                product.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_XPDATE)));
                product.setCount(cursor.getCount());
                productList.add(product);
            } while (cursor.moveToNext());
        }
        //int count = cursor.getCount();
        cursor.close();
        db.close();
        return productList;
    }
    public List<Products> getReportProducts() {
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };

        String sortOrder = COLUMN_XPDATE + " ASC";
        List<Products> productList = new ArrayList<Products>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Products product = new Products();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                product.setBarcode(cursor.getString(cursor.getColumnIndex(COLUMN_BARCODE)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                //product.setOprice(cursor.getString(cursor.getColumnIndex(COLUMN_OPRICE)));
                product.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_SPRICE)));
                product.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_XPDATE)));
                product.setCount(cursor.getCount());
                productList.add(product);
            } while (cursor.moveToNext());
        }
        //int count = cursor.getCount();
        cursor.close();
        db.close();
        return productList;
    }

    public Cursor countExpiredD(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                //COLUMN_OPRICE,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };
        String selection = COLUMN_XPDATE+ " = Date('now')";
        String[] selectionArgs = {"Date('now')"};
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                null,
                null,
                null,
                null);
        return  cursor;
    }

    public Cursor countExpiredW(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                //COLUMN_OPRICE,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };
        String selection = "strftime('%W', xp_date) = strftime('%W', 'now')";
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                null,
                null,
                null,
                null);
        return  cursor;
    }

    public Cursor countExpiredM(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                //COLUMN_OPRICE,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };
        String selection = COLUMN_XPDATE+ " BETWEEN Date('now','start of month') AND Date('now','start of month','+1 month','-1 day')";
        String[] selectionArgs = {"Date('now')"};
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                null,
                null,
                null,
                null);
        return  cursor;
    }

    public Cursor countExpiredY(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID,
                COLUMN_BARCODE,
                COLUMN_NAME,
                //COLUMN_OPRICE,
                COLUMN_SPRICE,
                COLUMN_XPDATE
        };
        String selection = "strftime('%Y', xp_date) == strftime('%Y', 'now')";
        String[] selectionArgs = {"Date('now')"};
        Cursor cursor = db.query(TABLE_PRODUCT,
                columns,
                selection,
                null,
                null,
                null,
                null);
        return  cursor;
    }

    public int sumPurchasedD(){
        int sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM("+COLUMN_QUANTITY+") as Total FROM "+TABLE_PURCHASE+" WHERE "+COLUMN_XPDATE+ " = Date('now')");
        Cursor cursor= db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }

    public int sumPurchasedW(){
        int sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        //String sumQuery=String.format("SELECT SUM("+COLUMN_QUANTITY+") as Total FROM "+TABLE_PURCHASE+" WHERE strftime('%W', xp_date) = strftime('%W', 'now')");
        Cursor cursor= db.rawQuery("SELECT SUM("+COLUMN_QUANTITY+") as Total FROM "+TABLE_PURCHASE+" WHERE strftime('%W', xp_date) = strftime('%W', 'now')",null);
        if(cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }

    public int sumPurchasedM(){
        int sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM("+COLUMN_QUANTITY+") as Total FROM "+TABLE_PURCHASE+" WHERE "+COLUMN_XPDATE+ " BETWEEN Date('now','start of month') AND Date('now','start of month','+1 month','-1 day')");
        Cursor cursor= db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }

    public int sumPurchasedY(){
        int sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM("+COLUMN_QUANTITY+") as Total FROM "+TABLE_PURCHASE);
        Cursor cursor= db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }

    public void addPuchase(Cash cash) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, cash.getName());
        values.put(COLUMN_COST, cash.getCost());
        values.put(COLUMN_QUANTITY, cash.getQty());
        values.put(COLUMN_XPDATE, cash.getDate());
        db.insert(TABLE_PURCHASE, null, values);
        db.close();
    }

    public List<Cash> getAllPurchases() {
        String[] columns = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_COST,
                COLUMN_QUANTITY,
                COLUMN_XPDATE
        };

        String sortOrder = COLUMN_ID + " DESC";
        List<Cash> cashList = new ArrayList<Cash>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PURCHASE,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Cash cash = new Cash();
                cash.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                cash.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                cash.setCost(cursor.getString(cursor.getColumnIndex(COLUMN_COST)));
                cash.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
                cash.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_XPDATE)));
                cash.setCount(cursor.getCount());
                cashList.add(cash);
            } while (cursor.moveToNext());
        }
        //int count = cursor.getCount();
        cursor.close();
        db.close();
        return cashList;
    }

    public void deletePurchase(int rowId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PURCHASE, COLUMN_ID + "=" + rowId, null);
        db.close();
    }


    public void addExpense(Cash cash) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, cash.getName());
        values.put(COLUMN_COST, cash.getCost());
        values.put(COLUMN_XPDATE, cash.getDate());
        db.insert(TABLE_EXPENSE, null, values);
        db.close();
    }

    public List<Cash> getAllExpenses() {
        String[] columns = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_COST,
                COLUMN_XPDATE
        };

        String sortOrder = COLUMN_ID + " DESC";
        List<Cash> cashList = new ArrayList<Cash>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSE,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);
        if (cursor.moveToFirst()) {
            do {
                Cash cash = new Cash();
                cash.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                cash.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                cash.setCost(cursor.getString(cursor.getColumnIndex(COLUMN_COST)));
                cash.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_XPDATE)));
                cash.setCount(cursor.getCount());
                cashList.add(cash);
            } while (cursor.moveToNext());
        }
        //int count = cursor.getCount();
        cursor.close();
        db.close();
        return cashList;
    }

    public void deleteExpense(int rowId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_EXPENSE, COLUMN_ID + "=" + rowId, null);
        db.close();
    }

    public double expenseCost(){
        double sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM("+COLUMN_COST+") as Total FROM "+TABLE_EXPENSE);
        Cursor cursor= db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst()) {
            sum = cursor.getDouble(cursor.getColumnIndex("Total"));
        }
        return sum;
    }

    public double purchaseCost(){
        double sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM("+COLUMN_COST+") as Total FROM "+TABLE_PURCHASE);
        Cursor cursor= db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst()) {
            sum = cursor.getDouble(cursor.getColumnIndex("Total"));
        }
        return sum;
    }

    public double sellPrice(){
        double sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM("+COLUMN_SPRICE+") as Total FROM "+TABLE_PRODUCT);
        Cursor cursor= db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst()) {
            sum = cursor.getDouble(cursor.getColumnIndex("Total"));
        }
        return sum;
    }

    public void backup(String outFileName) {
        final String inFileName = context.getDatabasePath(DATABASE_NAME).toString();
        try {
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);
            OutputStream output = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
            Toast.makeText(context, "Backup Completed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Unable to backup database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void importDB(String inFileName) {
        final String outFileName = context.getDatabasePath(DATABASE_NAME).toString();
        try {
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);
            OutputStream output = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
            Toast.makeText(context, "Import Completed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Unable to import database. Retry", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
