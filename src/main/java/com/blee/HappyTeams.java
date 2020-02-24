package com.blee;
 
import java.util.*;
import java.io.*;

/******************************************************************************
 *  Author : Team D.A.P.
 *  Class  : Spring 2020 Dr. Reeves CS374
 *  Task   : Do something for the project
 *  Date   : 2/14/2020
 *
 *  Compile: javac HappyTeams.java
 *  Execute: java HappyTeams
 *
 *
 ******************************************************************************/
public class HappyTeams 
{

  public class User
  {
    public int pref[];
    public int id;
    public String name;

    User(String n, int[] p, int c, int h)
    {
      name = n;
      id = h;
      pref = new int[10000];

      int counter = 0;
      int[] values = {10,8,6,3,2,1};

      for(int i = 0; i <= 5; i++)
      {
        pref[p[i+1]] = values[counter];
        counter = counter + 1;
      }


    }
  }

  public User l[];
  public int count;
  public int team_size;
  public int overall_highest;
  public int local_highest;
  public String local_team;
  public String overall_team;


  public int getUserHappiness(int n, int s)
  {
    int c = 0;

    for (int i = 0; i < team_size; i++)
    {
      c = c + l[n-1].pref[s+i-1];
    }
    return c;
  }
  public int getTeamHappiness(int n)//gets 
  {
    int c = 0;

    for(int i = 0; i < team_size; i++)
    {
      //System.out.println("user start: " + (i + n));

      c = c + getUserHappiness(i + n, n);

    }
    return c;
  }
  public int getHappiness(int t)//gets overall happiness by adding 
  {
    int c = 0;
    int people = count;
    int g;
    
    for (int i = 0; i < count; i++)
    {
      if (i%team_size - 1 == 0 && count >= i)
      {
        //System.out.println("team start: " + i);
        g = getTeamHappiness(i);
        c = c + g;

        if (t == 1 && g == 0)
        {
          c = c - 40;
        }

      }
    }


    return c;
  }

  public User processLine(String x, int h)
    {
      int length = x.length();

      String[] temp = x.split(",");

      int[] choices = new int[10000];

      String n = temp[0];


      int count = 0;

      for (String t : temp)
      {

        if(count == 0){}
        else
        {
          choices[count] = Integer.parseInt(t);
        }
        count ++;
      }

      User y = new User(n,choices,count,h);

      return y;
    }

  public void main( String[] args )
  {
    System.out.println(args[0]);
  }

    private void prefs(String f)  throws FileNotFoundException
    {
      String currentDirectory;
      currentDirectory = System.getProperty("user.dir");

      f = "test.csv";
      count = 0;

      FileInputStream fis = new FileInputStream("/"+ currentDirectory + "/src/" + f);
      Scanner scanner = new Scanner(fis);

      l = new User[10000];



      if (scanner.hasNextLine())
      {
        l[count] = processLine(scanner.nextLine(), count);
        count ++;
      }
   
      while(scanner.hasNextLine())
      {
        l[count] = processLine(scanner.nextLine(), count);
        count ++;
      }

      int temp_n = count;

      while (count % team_size != 0)
      {
        l[count] = processLine("", count);
        count++;
      }
   
      scanner.close();
    }

    public int random(int size, int verbose, int numberOfSwaps, int timesRan, int optimal, String fileName) throws FileNotFoundException
    {
      overall_highest = 0;
      overall_team = "";
      team_size = size;
      this.prefs(fileName);
      int counter = 0;
      User temp;
      int swap1;
      int swap2;

            System.out.println(count);      


      for(int i=0; i < timesRan; i++)
      {
        Random rnd = new Random(i);

        local_highest = 0;
        local_team = "";
        for(int j=0; j < numberOfSwaps; j++)
        {
          counter++;
          swap1 = rnd.nextInt(count);
          swap2 = rnd.nextInt(count);

          temp = l[swap1];
          l[swap1] = l[swap2];
          l[swap2] = temp;

          double per = 100 - (optimal/100);
          int value = getHappiness(1);

          if (value > local_highest)
          {
            local_highest = value;
            String temps = "";
            for (int k = 0; k < count; k++)
            {
              if(l[k].name != "")
              {
                temps = temps + l[k].name + " ";
              }
            }
            local_team = temps;
          }
          else if (value < local_highest * per)
          {
            temp = l[swap1];
            l[swap1] = l[swap2];
            l[swap2] = temp;
          }

        }

        if (local_highest > overall_highest)
        {
          overall_highest = local_highest;
          overall_team = local_team;
        }
        System.out.println(local_highest + " " + local_team);

      }
      System.out.println("Highest team happiness: " + overall_highest + "\nOrder: " + overall_team);


      return counter;
    }
}
