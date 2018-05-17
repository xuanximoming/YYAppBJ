package com.yy.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.yy.app.R;


public class MyDialog extends Dialog implements
		android.view.View.OnClickListener {

	private Context context;
	private Button btnok;
	private LeaveMyDialogListener listener;

	public interface LeaveMyDialogListener {
		public void onClick(View view);
	}

	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public MyDialog(Context context, int theme, int mydialog,
			LeaveMyDialogListener listener) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.setContentView(mydialog);
		this.context = context;
		this.listener = listener;
		btnok = (Button) findViewById(R.id.dialog_btnok);
		btnok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		listener.onClick(v);
	}
}
