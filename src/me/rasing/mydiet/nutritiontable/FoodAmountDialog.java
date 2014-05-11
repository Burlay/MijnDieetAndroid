package me.rasing.mydiet.nutritiontable;

import me.rasing.mydiet.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class FoodAmountDialog extends DialogFragment implements OnClickListener {
	public static final String DIALOG_TITLE = "title";
	public static final String FOOD_URI = "uri";
	
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(int id, CharSequence foodAmount);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    
    NoticeDialogListener mListener;
	private TextView mFoodAmount;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View rootView = inflater.inflate(R.layout.foodamountdialog, null);
	    mFoodAmount = (TextView) rootView.findViewById(R.id.foodAmount);
	    
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    builder.setView(rootView);
		builder.setTitle(this.getArguments().getString(DIALOG_TITLE));
		builder.setPositiveButton(R.string.done, this);
		builder.setNegativeButton(R.string.discard,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						mListener.onDialogNegativeClick(FoodAmountDialog.this);
					}
				});
		
		return builder.create();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		mListener.onDialogPositiveClick(
				this.getArguments().getInt(FOOD_URI),
				mFoodAmount.getText());
	}

}
