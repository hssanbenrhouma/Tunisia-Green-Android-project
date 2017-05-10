package com.example.dell.tunisiagreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by dell on 29/04/2016.
 */
public class UserDB {
    public static final String dbname="TunisiaGreen.db";
    public static final int version=1;
    private SQLiteDatabase db;
    private DBHelper helper;

    public UserDB(Context context) {
helper=new DBHelper(context,dbname,null,version)  ;

    }
    public void open(){
        db=helper.getWritableDatabase();
    }
    public void close(){
        db.close();
    }
    public long insertintop(User user)
    {
        Cursor cursor=db.rawQuery("select MAX(id) from User",null);
        if(cursor.moveToFirst())
        {
            user.setId(cursor.getInt(0)+1);
        }
        else
        {
            user.setId(1);
        }
        ContentValues Val=new ContentValues();
        Val.put("id",user.getId());

        Val.put("username",user.getUsername());
        Val.put("email",user.getEmail());
        Val.put("type",user.getType());
        Val.put("region",user.getRegion());
        Val.put("password",user.getPassword());
        return db.insert("User",null,Val);
    }
    public ArrayList<User> selectionfromuser()
    { ArrayList<User> arrayList=new ArrayList<User>();
        Cursor cursor=db.query("User",new String[]{"*"},null,null,null,null,null);
      if(  cursor.moveToFirst()){
          do {
              User U=new User(cursor.getString(cursor.getColumnIndex("username")),cursor.getString(cursor.getColumnIndex("email")),cursor.getString(cursor.getColumnIndex("type")),cursor.getString(cursor.getColumnIndex("region")),cursor.getString(cursor.getColumnIndex("password")));
              U.setId(cursor.getInt(cursor.getColumnIndex("id")));
              arrayList.add(U);
          }while (cursor.moveToNext());
      }
        if (!cursor.isClosed()&&cursor!=null)
        {
            cursor.close();
        }


        return arrayList;
    }

    public SQLiteDatabase getDb() {
            return db;
    }
    public String Login(String email,String pass)
    {

       // Cursor cursor=db.rawQuery(Select * from User where(username LIKE '"+username+"' and password LIKE '"+pass+"'

ArrayList<User> arrayList=new ArrayList<User>();
       arrayList=selectionfromuser();

        for (int i=0;i<arrayList.size();i++)
        {
            if(arrayList.get(i).getEmail().equals(email)&&(arrayList.get(i).getPassword().equals(pass)))
            {System.out.println("email db: "+arrayList.get(i).getEmail());
                System.out.println("email: "+email);
                System.out.println("PASS db: "+arrayList.get(i).getPassword());
                System.out.println("pass: "+pass);
                System.out.println("type db"+arrayList.get(i).getType());

                if (arrayList.get(i).getType().equals("ADMIN")) {
                    return "ADMIN";
                } else {
                    if ((arrayList.get(i).getType()).equals("CITOYEN"))
                    {
                        return "CITOYEN";
                    } else {
                        if ((arrayList.get(i).getType()).equals("RESPONSABLE"))
                            return "RESPONSABLE";
                    }
                }
            }

        }
            return "";
        }
    public int removeAll(){
        return db.delete("User",null,null);

    }
}
