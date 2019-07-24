package com.example.ToDoApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class Dashboard extends AppCompatActivity  {
   static int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Bundle b= getIntent().getExtras();
        id=Integer.parseInt(b.getString("id"));
        Realm realm=Realm.getDefaultInstance();
        RealmResults<Task> task=realm.where(Task.class).equalTo("userId",id).sort("dueDate", Sort.ASCENDING).findAll();
        if(task.size()>0)
            Log.i("dashboard","task exist");
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(task.size()==0)
        { ft.add(R.id.frame_container,new Fragment2());}
        else
        {
            RealmResults<Task> t1=realm.where(Task.class).equalTo("userId",id).and().equalTo("checked","true").findAll();
            if(t1.size()>0)
            {   realm.beginTransaction();
                t1.deleteAllFromRealm();
                realm.commitTransaction();
                task = realm.where(Task.class).equalTo("userId", id).findAll();
            }

            ft.add(R.id.frame_container,new Fragment1(task));


        }
        ft.commit();
        realm.close();

    }

    @Override
    public void onResume() {
        super.onResume();
        Realm realm=Realm.getDefaultInstance();
        RealmResults<Task> task=realm.where(Task.class).equalTo("userId",id).sort("dueDate", Sort.ASCENDING).findAll();
        if(task.size()>0)
            Log.i("dashboard","task exist");
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(task.size()==0)
            { ft.add(R.id.frame_container,new Fragment2());}
        else
        {
            ft.replace(R.id.frame_container,new Fragment1(task));
        }
        ft.commit();
   realm.close();

    }

    public void createTask(View view)
    {
        Intent intent=new Intent(this, CreateTask.class);
        intent.putExtra("userId",id);
        startActivity(intent);
    }
   public void logOut(View view)
   {
       Realm realm=Realm.getDefaultInstance();
       realm.beginTransaction();
       try {
           RealmResults<Task> r = realm.where(Task.class).equalTo("userId", id).and().equalTo("checked", "true").findAll();
           if(r.size()>0)
           r.deleteAllFromRealm();
           realm.commitTransaction();
       }catch (Exception e)
       {
          realm.cancelTransaction();
       }
       finally {
           realm.close();
       }

     onBackPressed();


   }
   public void allDone(View view) {
       Realm realm = Realm.getDefaultInstance();
       RealmResults<Task> t = realm.where(Task.class).equalTo("userId", id).and().equalTo("checked", "false").findAll();

       try {
           realm.beginTransaction();
           for (Task t1 : t) {
               t1.setChecked("true");
           }
           realm.commitTransaction();

       } catch (Exception e) {
           realm.cancelTransaction();
       } finally {
           realm.close();
       }
     t=realm.where(Task.class).equalTo("userId", id).findAll();
       if(t.size()>0)
         getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Fragment1(t)).commit();
   }

}
