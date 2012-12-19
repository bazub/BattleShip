package com.example.battleship_thegame;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class FirstScreen 
extends Activity 
implements View.OnClickListener{
	public int[][] P2=new int[10][10];
	public String fname = "battleship";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first_screen);
		Button btnew = (Button) findViewById(R.id.newgame);
		btnew.setOnClickListener(this);
		Button btload = (Button) findViewById(R.id.loadgame);
		btload.setOnClickListener(this);
		Button btquit = (Button) findViewById(R.id.quitgame);
		btquit.setOnClickListener(this);
	}

	public void onClick(View view) {
		int buttonType=view.getId();
		if(buttonType==R.id.newgame){ //clearmap
			P2[0][0]=11;
			P2[0][1]=12;
			P2[0][2]=13;
			P2[0][3]=14;
			P2[1][9]=21;
			P2[2][9]=22;
			P2[3][9]=23;
			P2[4][9]=24;
			P2[5][3]=11;
			P2[5][4]=12;
			P2[5][5]=13;
			P2[5][6]=14;
			
			try{
				DataOutputStream out = new DataOutputStream(openFileOutput(fname, Context.MODE_WORLD_WRITEABLE));
				for (int i=0; i<10; i++) {
					   for (int j=0; j<10;j++){
						   out.writeUTF(Integer.toString(0));
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
			Intent intent = new Intent(this, LoadingScreen.class);
			startActivity(intent);
			//finish();
		}
		else if(buttonType==R.id.loadgame){
			Intent intent = new Intent(this, Player1.class);
			startActivity(intent);
			
		}
		else if(buttonType==R.id.quitgame)
			finish();	
			
	}
}
