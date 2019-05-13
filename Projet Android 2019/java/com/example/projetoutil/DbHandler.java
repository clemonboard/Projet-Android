package com.example.projetoutil;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;


import com.example.projetoutil.fraguement.NearestTools;
import com.google.android.gms.maps.model.LatLng;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import static com.example.projetoutil.BlankFragment.latit;
import static com.example.projetoutil.BlankFragment.longu;

public class DbHandler extends SQLiteOpenHelper {
    //all constants as they are static and final(Db=Database)
    //Db Version
    private static final int Db_Version=5;
    Context context;
    //Db Name
    private static final String Db_Name="users";
    //table name
    private static final String Table_Name1="user";
    //Creating mycontacts Columns
    private static final String User_id="id";
    private static final String User_name="name";
    private static final String User_password="password";
    private  static  final  String User_email = "email";
    private static final String User_firstname = "firstname";
    private static final String User_country ="country";
    private static final String User_image = "image";


    private static final String Table_Name2="annonce";

    private static final String Annonce_id="id";
    private static final String Outil_name="name";
    private static final String Annonce_description = "description";
    private static final String Annonce_ville = "ville";
    private static final String Annonce_telephone ="telephone";
    private static final String Annonce_image = "image";
    private static final String Annonce_No_User = "NoUser";
    //constructor here
    public DbHandler(Context context)
    {
        super(context,Db_Name,null,Db_Version);
    }
    //creating table
    private static final String Create_Table_User="CREATE TABLE " + Table_Name1 + "(" + User_id
            + " INTEGER PRIMARY KEY," + User_name + " TEXT," + User_email + " TEXT," + User_firstname + " TEXT,"+ User_country + " TEXT," + User_image + " BLOB," + User_password + " TEXT" + ")";


    private static final String Create_Table_Annonce="CREATE TABLE " + Table_Name2 + "(" + Annonce_id
            + " INTEGER PRIMARY KEY," + Outil_name + " TEXT," + Annonce_description + " TEXT," + Annonce_ville + " TEXT,"+ Annonce_telephone + " TEXT,"+ Annonce_No_User + " INTEGER," + Annonce_image + " BLOB"+ ")";
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(Create_Table_User);
        db.execSQL(Create_Table_Annonce);
    }
    //Upgrading the Db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name1);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name2);
        //create the table again
        onCreate(db);
    }
    //Add new User by calling this method
    public void addUser(String Nom , String Prenom, String mdp, String Email, String Pays,byte[] img)
    {
        // getting db instance for writing the user
        SQLiteDatabase db=this.getWritableDatabase();


            ContentValues cv = new ContentValues();
            // cv.put(User_id,usr.getId());
            cv.put(User_name, Nom);
            cv.put(User_password, mdp);
            cv.put(User_email, Email);
            cv.put(User_firstname, Prenom);
            cv.put(User_country, Pays);
            cv.put(User_image,img);
            db.insert(Table_Name1, null, cv);

        //close the database to avoid any leak
        db.close();
    }
    public int checkUser(User us)
    {
        int id=-1;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT id FROM user WHERE name=? AND password=?",new String[]{us.getName(),us.getPassword()});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
        }
        return id;
    }


    public void addAnnonce(String Nom , String ville, String description, String telephone, byte[] img,int num_uti)
    {
        // getting db instance for writing the user
        SQLiteDatabase db=this.getWritableDatabase();


            ContentValues cv = new ContentValues();
            // cv.put(User_id,usr.getId());
            cv.put(Outil_name, Nom);
            cv.put(Annonce_description, description);
            cv.put(Annonce_telephone, telephone);
            cv.put(Annonce_ville, ville);
            cv.put(Annonce_image,img);
            cv.put(Annonce_No_User,num_uti);
            db.insert(Table_Name2, null, cv);


        //close the database to avoid any leak
        db.close();
    }


    public User getCurrentUser(String email){
        User CurentUser;

         String Nom,Prenom,password,mail,pays;
         int id;

        byte[] img;
        SQLiteDatabase db = this.getReadableDatabase();
    /*Cursor cursor = db.query(TABLE, new String[] {COLUMN_USERNAME, COLUMN_PASSWORD}, COLUMN_USERNAME , null, null, null, null);
    cursor.moveToFirst();*/
        String selectQuery = "SELECT id , name, password, email , firstname, country , image FROM user where email = '" + email +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        id= cursor.getInt(cursor.getColumnIndex("id"));
        Nom= cursor.getString((cursor.getColumnIndex((User_name))));
        Prenom =  cursor.getString((cursor.getColumnIndex(User_firstname)));
        password = cursor.getString((cursor.getColumnIndex((User_password))));
        img  = cursor.getBlob(cursor.getColumnIndex((User_image)));
        pays= cursor.getString(cursor.getColumnIndex(User_country));
        mail = cursor.getString(cursor.getColumnIndex(User_email));
      CurentUser=  new User(id,Nom,password,mail,Prenom,pays,img);
        return CurentUser;
    }

    public List<Annonce> Annonce_Par_Ville(String Nom_Ville)
    {
        List<Annonce> lstAnnonce =new ArrayList<Annonce>();;

    String NOM,DESCRIPTION,VILLE,TELEPHONE;
    int NOANNONCE,NOUSERS;
    byte[]img;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT id , name, description, ville , telephone, image, NoUser FROM annonce where ville = '" + Nom_Ville +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);

      if(  cursor.moveToFirst())
      {
          do
              {

                  NOANNONCE = cursor.getInt(cursor.getColumnIndex("id"));
                  NOM = cursor.getString((cursor.getColumnIndex("name")));
                  DESCRIPTION = cursor.getString((cursor.getColumnIndex("description")));
                  TELEPHONE = cursor.getString((cursor.getColumnIndex("telephone")));
                  img = cursor.getBlob(cursor.getColumnIndex("image"));
                  NOUSERS = cursor.getInt(cursor.getColumnIndex("NoUser"));
                  VILLE = cursor.getString(cursor.getColumnIndex("ville"));
                  lstAnnonce.add(new Annonce(NOANNONCE,NOM,TELEPHONE,VILLE,DESCRIPTION,img));
              }while (cursor.moveToNext());

      }

        return lstAnnonce;
    }

    public User GetUser(String email, String passwd)
    {
        User Loginuser;
        String Nom,Prenom,password,mail,pays;
        int id;

        byte[] img;
        SQLiteDatabase db = this.getReadableDatabase();
    /*Cursor cursor = db.query(TABLE, new String[] {COLUMN_USERNAME, COLUMN_PASSWORD}, COLUMN_USERNAME , null, null, null, null);
    cursor.moveToFirst();*/
        String selectQuery = "SELECT id , name, password, email , firstname, country , image FROM user where email = '" + email +"'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        id= cursor.getInt(cursor.getColumnIndex("id"));
        Nom= cursor.getString((cursor.getColumnIndex((User_name))));
        Prenom =  cursor.getString((cursor.getColumnIndex(User_firstname)));
        password = cursor.getString((cursor.getColumnIndex((User_password))));
        img  = cursor.getBlob(cursor.getColumnIndex((User_image)));
        pays= cursor.getString(cursor.getColumnIndex(User_country));
        mail = cursor.getString(cursor.getColumnIndex(User_email));
        Loginuser=  new User(id,Nom,password,mail,Prenom,pays,img);
        return Loginuser;

    }


    public List<Annonce> Mes_annonce(int currentid)
    {
        List<Annonce> lstAnnonce =new ArrayList<Annonce>();;

        String NOM,DESCRIPTION,VILLE,TELEPHONE;
        int NOANNONCE,NOUSERS;
        byte[]img;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT id , name, description, ville , telephone, image, NoUser FROM annonce where NoUser = "+ currentid ;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(  cursor.moveToFirst())
        {
            do
            {
                NOANNONCE = cursor.getInt(cursor.getColumnIndex("id"));
                NOM = cursor.getString((cursor.getColumnIndex("name")));
                DESCRIPTION = cursor.getString((cursor.getColumnIndex("description")));
                TELEPHONE = cursor.getString((cursor.getColumnIndex("telephone")));
                img = cursor.getBlob(cursor.getColumnIndex("image"));
                NOUSERS = cursor.getInt(cursor.getColumnIndex("NoUser"));
                VILLE = cursor.getString(cursor.getColumnIndex("ville"));
                lstAnnonce.add(new Annonce(NOANNONCE,NOM,TELEPHONE,VILLE,DESCRIPTION,img));
            }while (cursor.moveToNext());

        }

        return lstAnnonce;
    }







    public List<Annonce> Annonce_ALL()
    {
        List<Annonce> lstAnnonce =new ArrayList<Annonce>();;

        String NOM,DESCRIPTION,VILLE,TELEPHONE;
        int NOANNONCE,NOUSERS;
        byte[]img;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT id , name, description, ville , telephone, image, NoUser FROM annonce ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(  cursor.moveToFirst())
        {
            do
            {

                NOANNONCE = cursor.getInt(cursor.getColumnIndex("id"));
                NOM = cursor.getString((cursor.getColumnIndex("name")));
                DESCRIPTION = cursor.getString((cursor.getColumnIndex("description")));
                TELEPHONE = cursor.getString((cursor.getColumnIndex("telephone")));
                img = cursor.getBlob(cursor.getColumnIndex("image"));
                NOUSERS = cursor.getInt(cursor.getColumnIndex("NoUser"));
                VILLE = cursor.getString(cursor.getColumnIndex("ville"));
                lstAnnonce.add(new Annonce(NOANNONCE,NOM,TELEPHONE,VILLE,DESCRIPTION,img));
            }while (cursor.moveToNext());

        }

        return lstAnnonce;
    }




    public List<Annonce> Annonce_Search(String Query)
    {
        List<Annonce> lstAnnonce =new ArrayList<Annonce>();;

        String NOM,DESCRIPTION,VILLE,TELEPHONE;
        int NOANNONCE,NOUSERS;
        byte[]img;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT id , name, description, ville , telephone, image, NoUser FROM annonce where name like '"+Query+"%'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(  cursor.moveToFirst())
        {
            do
            {

                NOANNONCE = cursor.getInt(cursor.getColumnIndex("id"));
                NOM = cursor.getString((cursor.getColumnIndex("name")));
                DESCRIPTION = cursor.getString((cursor.getColumnIndex("description")));
                TELEPHONE = cursor.getString((cursor.getColumnIndex("telephone")));
                img = cursor.getBlob(cursor.getColumnIndex("image"));
                NOUSERS = cursor.getInt(cursor.getColumnIndex("NoUser"));
                VILLE = cursor.getString(cursor.getColumnIndex("ville"));
                lstAnnonce.add(new Annonce(NOANNONCE,NOM,TELEPHONE,VILLE,DESCRIPTION,img));
            }while (cursor.moveToNext());

        }

        return lstAnnonce;
    }










    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // haversine great circle distance approximation, returns meters
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60; // 60 nautical miles per degree of seperation
        dist = dist * 1852; // 1852 meters per nautical mile
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
















    public List<Annonce> Annonce_Nears(int dist,double userlon, double userlat, Context contexte) {
        List<Annonce> lstAnnonce = new ArrayList<Annonce>();
        ;

        String NOM, DESCRIPTION, VILLE, TELEPHONE;
        int NOANNONCE, NOUSERS;
        byte[] img;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT id , name, description, ville , telephone, image, NoUser FROM annonce ";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if (Geocoder.isPresent()) {
                    try {
                        String location = cursor.getString(cursor.getColumnIndex("ville"));
                        Geocoder gc = new Geocoder(contexte);
                        List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects

                        List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                        for (Address a : addresses) {
                            if (a.hasLatitude() && a.hasLongitude()) {
                                if (distance(userlat, userlat, a.getLatitude(), a.getLongitude()) <= dist) {

                                    NOANNONCE = cursor.getInt(cursor.getColumnIndex("id"));
                                    NOM = cursor.getString((cursor.getColumnIndex("name")));
                                    DESCRIPTION = cursor.getString((cursor.getColumnIndex("description")));
                                    TELEPHONE = cursor.getString((cursor.getColumnIndex("telephone")));
                                    img = cursor.getBlob(cursor.getColumnIndex("image"));
                                    NOUSERS = cursor.getInt(cursor.getColumnIndex("NoUser"));
                                    VILLE = cursor.getString(cursor.getColumnIndex("ville"));
                                    lstAnnonce.add(new Annonce(NOANNONCE, NOM, TELEPHONE, VILLE, DESCRIPTION, img));
                                }

                            }
                        }
                    } catch (IOException e) {
                        // handle the exception
                    }

                }

            }while (cursor.moveToNext()) ;
        }


            return lstAnnonce;

    }}



