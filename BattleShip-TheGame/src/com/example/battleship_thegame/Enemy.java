package com.example.battleship_thegame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class Enemy extends Activity {
	public int[][] P1= new int[10][10];
    public int[][] P2= new int[10][10];
	public String fname = "battleship"; 
    public void writeToFile(){
		try{
			DataOutputStream out = new DataOutputStream(openFileOutput(fname, Context.MODE_WORLD_WRITEABLE));
			for (int i=0; i<10; i++) {
				for (int j=0; j<10;j++){
					out.writeUTF(Integer.toString(P1[i][j]));
				}
			}
			for (int i=0; i<10; i++) {
				for (int j=0; j<10;j++){
					out.writeUTF(Integer.toString(P2[i][j]));
				}
			}
			out.close();
		}
		catch (IOException e){
			Log.i("Data Input Sample","I/O Error");
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		try{ //try to get matrix config
			String fname="battleship";
			File file = getBaseContext().getFileStreamPath(fname);
		    if(file.exists()){ //read from file
		    	DataInputStream in = new DataInputStream(openFileInput(fname));
		        for(int i=0;i<10;i++){
		           for(int j=0;j<10;j++){
		        	   P1[i][j]=Integer.parseInt(in.readUTF());
		           }
		        }
		        for(int i=0;i<10;i++){
		        	for(int j=0;j<10;j++){
		        		P2[i][j]=Integer.parseInt(in.readUTF());
		        		Log.i(fname, Integer.toString(P2[i][j]));
		        	}
		        }
		        in.close();
		    }
		}
		catch (IOException e){
			Log.i("Data Input Sample","I/O Error");
		}

		super.onCreate(savedInstanceState);
		int id;
		int i,j;
		int ok=0;
		for(i=0;i<=9;i++)
			for(j=0;j<=9;j++)
				if(P1[i][j]==99){//we have a hit;
					if(i-1>=0 && P1[i-1][j]!=99 && P1[i-1][j]!=88){ //North is not hit && North is not miss
						if(P1[i-1][j]!=0) //North is not water
							P1[i-1][j]=99; //North is hit
						else P1[i-1][j]=88; //North is miss
						ok=1;
						i=10; //exit
						j=10;
					}
					else if(i+1<=9 && P1[i+1][j]!=99 && P1[i+1][j]!=88){ //South is not hit && South is not miss
						if(P1[i+1][j]!=0) //South is not water
							P1[i+1][j]=99; //South is hit
						else P1[i+1][j]=88; //South is miss
						ok=1;
						i=10; //exit
						j=10;
					}
					else if(j-1>=0 && P1[i][j-1]!=99 && P1[i][j-1]!=88){ //West is not hit && West is not miss
						if(P1[i][j-1]!=0) //West is not water
							P1[i][j-1]=99; //West is hit
						else P1[i][j-1]=88; //West is miss
						ok=1;
						i=10; //exit
						j=10;
					}
					else if(j+1<=9 && P1[i][j+1]!=99 && P1[i][j+1]!=88){ //East is not hit && East is not miss
						if(P1[i][j+1]!=0) //East is not water
							P1[i][j+1]=99; //East is hit
						else P1[i][j+1]=88; //East is miss
						ok=1;
						i=10; //exit
						j=10;
					}
				}
		if(ok==0){			
			id=new Random().nextInt(82);
			while(P1[id/10][id%10]==88 || P1[id/10][id%10]==99) //while random is on Hit/Miss
				id=new Random().nextInt(82);
			if(P1[id/10][id%10]==0){
				P1[id/10][id%10]=88; //miss
			}
			else P1[id/10][id%10]=99; //hit
		}
		int counter=0;
		for(i=0;i<=9;i++)
			for(j=0;j<=9;j++)
				if(P1[i][j]==99)
					counter++;
		if(counter==12){
			Toast.makeText(getApplicationContext(), "BOOOOOOOOOOOOO!!!", Toast.LENGTH_LONG).show();
			finish();
		}
		else{
			Intent intent = new Intent(this, Player1.class);
			writeToFile();
			startActivity(intent);
			finish();
		}
	}
}