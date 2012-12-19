package com.example.battleship_thegame;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ViewMyMap
extends Activity
implements View.OnClickListener {
	public int[][] P1= new int[10][10];
    public int[][] P2= new int[10][10];
	public String fname = "battleship"; 
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
				if(P1[i][j]==0){
					b.setImageResource(R.drawable.water);
				}
				else if(P1[i][j]==99){
					b.setImageResource(R.drawable.explosion);
				}
				else if(P1[i][j]==88){
					b.setImageResource(R.drawable.x);
				}
				else if(P1[i][j]==11){
					b.setImageResource(R.drawable.e1);
				}
				else if(P1[i][j]==12){
					b.setImageResource(R.drawable.e2);
				}
				else if(P1[i][j]==13){
					b.setImageResource(R.drawable.e3);
				}
				else if(P1[i][j]==14){
					b.setImageResource(R.drawable.e4);
				}
				else if(P1[i][j]==21){
					b.setImageResource(R.drawable.n1);
				}
				else if(P1[i][j]==22){
					b.setImageResource(R.drawable.n2);
				}
				else if(P1[i][j]==23){
					b.setImageResource(R.drawable.n3);
				}
				else if(P1[i][j]==24){
					b.setImageResource(R.drawable.n4);
				}
				b.setId(i*10+j);
				tr.addView(b, 72,72);
			} // for
			layout.addView(tr);
		} // for
		largelay.addView(layout);
		hugelay.setBackgroundResource(R.drawable.battleshipbg);
		Button btn=new Button(this);
		btn.setText("BACK");
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
			Intent intent = new Intent(this, Player1.class);
			startActivity(intent);
			finish();
		}
	
	}
}