package com.heshun.crash.crash;

import android.app.Activity;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ActivityStack
{
  public static List<Activity> activityList = new LinkedList();

  public static void addActivity(Activity paramActivity)
  {
    activityList.add(paramActivity);
  }

  public static void exic()
  {
    if (activityList.size() > 0)
    {
      Iterator localIterator = activityList.iterator();
      while (localIterator.hasNext())
      {
        Activity localActivity = (Activity)localIterator.next();
        try
        {
          localActivity.finish();
        }
        catch (Exception localException)
        {
        }
      }
    }
    System.exit(0);
  }

  public static void removeActivity(Activity paramActivity)
  {
    if (activityList != null)
      for (boolean bool = activityList.remove(paramActivity); bool; bool = activityList.remove(paramActivity));
  }
}

/* Location:           C:\Users\Administrator\Desktop\文档大全\反编译\jd-gui\jd-gui\classes-dex2jar.jar
 * Qualified Name:     com.heshun.newcrash.crash.ActivityStack
 * JD-Core Version:    0.6.2
 */