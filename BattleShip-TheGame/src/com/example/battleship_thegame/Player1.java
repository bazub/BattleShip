package com.example.battleship_thegame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Player1
extends Activity
implements View.OnClickListener {
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
		super.onCreate(savedInstanceState);
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
		        	   }
		           }
		           in.close();
		       }
		}
		catch (IOException e){
			Log.i("Data Input Sample","I/O Error");
		}
		RelativeLayout hugelay = new RelativeLayout(this);
		ScrollView largelay = new ScrollView (this);
		largelay.setId(10000);
		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams(300,300) );
		layout.setPadding(1,1,1,1);
		int i,j;
		for (i=0; i<=9; i++) {
			TableRow tr = new TableRow(this);
			for (j=0; j<=9; j++) {
				ImageButton b = new ImageButton (this);
				if(P2[i][j]==0){
					b.setImageResource(R.drawable.water);
				}
				else if(P2[i][j]==99){
					b.setImageResource(R.drawable.explosion);
				}
				else if(P2[i][j]==88){
					b.setImageResource(R.drawable.x);
				}
				else{
					b.setImageResource(R.drawable.water2);
				}
				b.setId(i*10+j);
				b.setOnClickListener(this);
				tr.addView(b, 72,72);
			} // for
			layout.addView(tr);
		} // for
		largelay.addView(layout);
		hugelay.setBackgroundResource(R.drawable.battleshipbg);
		Button btn=new Button(this);
		btn.setText("MY MAP");
		btn.setBackgroundResource(R.color.transparent);
		btn.setHeight(134);
		btn.setWidth(134);
		btn.setId(1000);
		btn.setOnClickListener(this);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
		        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.RIGHT_OF, largelay.getId());
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		hugelay.addView(largelay);
		hugelay.addView(btn,lp);
		super.setContentView(hugelay);
	}	 // ()
    public void onClick(View view) {
		int id=view.getId();
		if(id==1000){
			Intent intent = new Intent(this, ViewMyMap.class);
			startActivity(intent);
			finish();
		}
		else{
			int i=id/10;
			int j=id%10;
			if(P2[i][j]==88 ||P2[i][j]==99) ; //trying to hit an already hit/missed spot
			else{
				if(P2[i][j]==0){
					((ImageButton) view).setImageResource(R.drawable.x);
					P2[i][j]=88;
				}
				else{
					((ImageButton) view).setImageResource(R.drawable.explosion);
					P2[i][j]=99;
				}
				int counter=0;
				for(i=0;i<=9;i++)
					for(j=0;j<=9;j++)
						if(P2[i][j]==99)
							counter++;
				if(counter==12){
					Toast.makeText(getApplicationContext(), "CONGRATS!!!", Toast.LENGTH_LONG).show();
					finish();
				}
				else{
					Intent intent = new Intent(this, Enemy.class);
					writeToFile();
					startActivity(intent);
					finish();
				}
			}
		}
	
	}
}