package com.andoidproject.culluze_app.Activity.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.andoidproject.culluze_app.Activity.model.OfflineSales_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitOrder_Model;
import com.andoidproject.culluze_app.Activity.model.SubmitSales_Model;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.andoidproject.culluze_app.Activity.activity.LoginActivity.loginModel;

public class DataBase extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION=100;
    private static final String DATABASE_NAME="MySQL";
    public static final String TABLE_NAME = "users";
    public static final String TABLE_ORDERSALSE = "ordersalse";
    public static final String TABLE_SALESSAVE = "freqitem";

    public static final String NAME = "name";
    public static final  String SAVEORDER="saveorder";
    public static final  String ORDERITEM="orderitem";
    public static final  String SAVEItem="saveitem";

    public static final  String ID="id";
    public static final  String ID2="id2";
    public static final  String ID3="id3";
    public static final  String USERSAVE="usersave";
    public static final  String USERORDER="userorder";

    public static Object Pair;

    public DataBase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , password TEXT)");
        db.execSQL("create table "+TABLE_ORDERSALSE +"(id2 INTEGER PRIMARY KEY AUTOINCREMENT,  orderitem TEXT, saveitem TEXT , userorder TEXT )");
        db.execSQL("create table "+TABLE_SALESSAVE +"(id3 INTEGER PRIMARY KEY AUTOINCREMENT,saveorder TEXT , saveitem TEXT , usersave TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String user)
    {
        long result=-1;
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv= new ContentValues();
            cv.put(NAME,user);
            result=db.insert(TABLE_NAME,null,cv);
            db.close();
        }
        catch (Exception e)
        {
            Log.e("exception",e.getMessage());
        }
        if(result==-1)

            return false;
        else
            return true;
    }

   /* public boolean addSales(String user)
    {
        long result=-1;
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv= new ContentValues();
            cv.put(SAVEORDER,user);
            result=db.insert(TABLE_SAVESALSE,null,cv);
            db.close();
        }
        catch (Exception e)
        {
            Log.e("exception",e.getMessage());
        }
        if(result==-1)

            return false;
        else
            return true;
    }*/

    public boolean addSalesSave( String user, String item , int userName)
    {
        long result=-1;
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv= new ContentValues();
            cv.put(SAVEORDER,user);
            cv.put(SAVEItem,item);
            cv.put(USERSAVE,userName);
            result=db.insert(TABLE_SALESSAVE,null,cv);
            db.close();
        }
        catch (Exception e)
        {
            Log.e("exception",e.getMessage());
        }
        if(result==-1)

            return false;
        else
            return true;
    }


    public boolean addOrderITem( String apiList, String orders, int username)
    {
        long result=-1;
        try{
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv= new ContentValues();
            cv.put(ORDERITEM,apiList);
            cv.put(SAVEItem,orders);
            cv.put(USERORDER,username);
            result=db.insert(TABLE_ORDERSALSE,null,cv);
            db.close();
        }
        catch (Exception e)
        {
            Log.e("exception",e.getMessage());
        }
        if(result==-1)

            return false;
        else
            return true;
    }

    public boolean deleteRow_Order_Save(int name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_ORDERSALSE, ID2 + "=?", new String[]{Integer.toString(name)}) > 0;
    }


    public boolean deleteRow_SaveSales(int name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_SALESSAVE, ID3 + "=?", new String[]{Integer.toString(name)}) > 0;
    }

    public Cursor fetch() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID,NAME}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }


    public Order saveOrderList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SubmitOrder_Model> list1=new ArrayList<>();
        ArrayList<OfflineSales_Model> list2=new ArrayList<>();
        ArrayList<Integer>idd=new ArrayList<>();
        int user = 0;

        Cursor cursor = db.query(TABLE_ORDERSALSE, new String[]{ID2,ORDERITEM,SAVEItem,USERORDER}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do
            {
                Gson gson1 = new Gson();
                int id =  cursor.getColumnIndex(ID2);
                idd.add(cursor.getInt(id));
                int usrInt=cursor.getColumnIndex(USERORDER);
                int  index1 =  cursor.getColumnIndex(ORDERITEM);
                int  index2 =  cursor.getColumnIndex(SAVEItem);
                user=cursor.getInt(usrInt);
                String value = cursor.getString(index1);
                String value2 = cursor.getString(index2);
                SubmitOrder_Model modelObject = gson1.fromJson(value, SubmitOrder_Model.class);
                OfflineSales_Model itemObject = gson1.fromJson(value2, OfflineSales_Model.class);
                list1.add(modelObject);
                list2.add(itemObject);
            }
            while(cursor.moveToNext());
            {

            }
        }
        cursor.close();
        return new Order(list1,list2,idd);
    }



    public Pair saveAllList() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SubmitSales_Model> list1=new ArrayList<>();
        ArrayList<OfflineSales_Model> list2=new ArrayList<>();
        ArrayList<Integer>idd=new ArrayList<>();
        int user=0;

        Cursor cursor = db.query(TABLE_SALESSAVE, new String[]{ID3,SAVEORDER,SAVEItem, USERSAVE}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do
            {
                Gson gson1 = new Gson();
                int id =  cursor.getColumnIndex(ID3);
                idd.add(cursor.getInt(id));
                int usrInt=cursor.getColumnIndex(USERSAVE);
                int  index1 =  cursor.getColumnIndex(SAVEORDER);
                int  index2 =  cursor.getColumnIndex(SAVEItem);
                user=cursor.getInt(usrInt);
                String value = cursor.getString(index1);
                String value2 = cursor.getString(index2);
                SubmitSales_Model modelObject = gson1.fromJson(value, SubmitSales_Model.class);
                OfflineSales_Model itemObject = gson1.fromJson(value2, OfflineSales_Model.class);
                list1.add(modelObject);
                list2.add(itemObject);
            }
            while(cursor.moveToNext());
            {

            }
        }
        cursor.close();
        return new Pair(list1,list2,idd);
    }


    public boolean deleteTitle(int id )
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE_NAME, "id = ?", new String[]{id+""}) > 0;
    }

    public boolean getValue()
    {   boolean haveData = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,null);
        if(cursor.getCount() > 0) {
            haveData=true;
        }
        else {
          haveData=false;
        }
        return haveData;
    }

    public class Pair
    {
        ArrayList<Integer> id;
        ArrayList<SubmitSales_Model> Saveorder;
        ArrayList<OfflineSales_Model> SaveIteam;
        //Integer user;
        public Pair(ArrayList<SubmitSales_Model> Saveorder, ArrayList<OfflineSales_Model> SaveIteam,  ArrayList<Integer> id)
        {
            this.Saveorder = Saveorder;
            this.SaveIteam = SaveIteam;
            this.id=id;
           // this.user=user;
        }

        public ArrayList<SubmitSales_Model> Saveorder() { return Saveorder; }
        public ArrayList<OfflineSales_Model> SaveItem() { return SaveIteam; }
        public  ArrayList<Integer> passId()
        {return id;}
      /*  public  Integer UserName()
        {
            return user;
        }*/
    }


    public class Order
    {
        ArrayList<Integer> id;
        ArrayList<SubmitOrder_Model> submitOrder;
        ArrayList<OfflineSales_Model> SaveIteam;
       // Integer user;
        public Order(ArrayList<SubmitOrder_Model> submitOrder,ArrayList<OfflineSales_Model> saveOffline, ArrayList<Integer> ids/*, Integer user*/)
        {
            this.submitOrder = submitOrder;
            this.SaveIteam = saveOffline;
            this.id=ids;
         //   this.user=user;
        }
        public ArrayList<SubmitOrder_Model> submitOrders() { return submitOrder; }
        public ArrayList<OfflineSales_Model> SaveItem() { return SaveIteam; }
        public  ArrayList<Integer> passId()
        {return id;}
     /*   public  Integer UserName()
        {
            return user;
        }*/
    }


    public Order getOrderSpecific (String className) {
        String query = "SELECT id2 , orderitem , saveitem FROM " + TABLE_ORDERSALSE + " WHERE " + USERORDER + " = '" + className + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<SubmitOrder_Model> list1=new ArrayList<>();
        ArrayList<OfflineSales_Model> list2=new ArrayList<>();
        ArrayList<Integer>id=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                id.add(cursor.getInt(cursor.getColumnIndex("id2")));
                Log.e("77777777",id+"");
                Gson gson1 = new Gson();
                int  index1 =  cursor.getColumnIndex("orderitem");
                int  index2 =  cursor.getColumnIndex("saveitem");
                String value = cursor.getString(index1);
                String value2 = cursor.getString(index2);
                SubmitOrder_Model modelObject = gson1.fromJson(value, SubmitOrder_Model.class);
                OfflineSales_Model itemObject = gson1.fromJson(value2, OfflineSales_Model.class);
                list1.add(modelObject);
                list2.add(itemObject);

                Log.e("777777777",list1.size()+"");
                Log.e("777777777",list2.size()+"");

            }
            while (cursor.moveToNext());
            {

            }
        }
        cursor.close();
        return new Order(list1,list2,id);
    }

    public Pair getSaveSpecific (String className) {
        String query = "SELECT id3 , saveorder , saveitem FROM " + TABLE_SALESSAVE + " WHERE " + USERSAVE + " = '" + className + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<SubmitSales_Model> list1=new ArrayList<>();
        ArrayList<OfflineSales_Model> list2=new ArrayList<>();
        ArrayList<Integer>id=new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {

                id.add(cursor.getInt(cursor.getColumnIndex("id3")));
                Log.e("sdjfhs",id+"");
                Gson gson1 = new Gson();
                int  index1 =  cursor.getColumnIndex("saveorder");
                int  index2 =  cursor.getColumnIndex("saveitem");
                String value = cursor.getString(index1);
                String value2 = cursor.getString(index2);
                SubmitSales_Model modelObject = gson1.fromJson(value, SubmitSales_Model.class);
                OfflineSales_Model itemObject = gson1.fromJson(value2, OfflineSales_Model.class);
                list1.add(modelObject);
                list2.add(itemObject);

                Log.e("sdjfhs",list1.size()+"");
                Log.e("sdjfhs",list2.size()+"");

            }
            while (cursor.moveToNext());
            {

            }
        }
        cursor.close();
        return new Pair(list1,list2,id);
    }



}
