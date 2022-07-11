package sg.edu.np.mad.splanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class TimerFragment extends Fragment {
    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button mRecord;
    private TextView mTimesUp;
    private String inputTime;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimerLeftInMillis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        mEditTextInput = view.findViewById(R.id.edit_text_input);
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);

        mButtonSet = view.findViewById(R.id.button_set);
        mButtonStartPause = view.findViewById(R.id.button_start_pause);
        mButtonReset = view.findViewById(R.id.button_reset);
        mRecord = view.findViewById(R.id.button_recordList);
        mTimesUp = view.findViewById(R.id.times_up_text);


        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTime = mEditTextInput.getText().toString();
                if (inputTime.length() == 0) {
                    Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                long millisInput = Long.parseLong(inputTime) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(getActivity(), "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerLeftInMillis == 0){
                    inputTime = mEditTextInput.getText().toString();
                    if (inputTime.length() == 0) {
                        Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    long millisInput = Long.parseLong(inputTime) * 60000;
                    if (millisInput == 0) {
                        Toast.makeText(getActivity(), "Please enter a positive number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    setTime(millisInput);
                    mEditTextInput.setText("");
                }

                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecordDetailsActivity.class);
                startActivity(intent);
            }
        });
        updateCountDownText();
        return view;
    }

    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimerLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimerLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning= false;
                mButtonStartPause.setText("Start");
                MediaPlayer music = MediaPlayer.create(getActivity(), R.raw.times_up);
                music.start();
                updateWatchInterface();
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Save Record?");
                alert.setMessage("Would you like to save your record for this practice?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent myIntent = new Intent(getActivity(), AddRecordActivity.class);
                        Bundle timeContent = new Bundle();
                        timeContent.putInt("timeTaken", Integer.valueOf(inputTime));
                        myIntent.putExtras(timeContent);
                        startActivity(myIntent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Time not recorded", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimerLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimerLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimerLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimerLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, seconds);
        }
        else {
            timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setEnabled(false);
            mButtonSet.setEnabled(false);
            mButtonSet.setBackgroundColor(Color.GRAY);
            mButtonReset.setEnabled(false);
            mButtonReset.setBackgroundColor(Color.GRAY);
            mTimesUp.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Pause");
        }
        else {
            mEditTextInput.setEnabled(true);
            mButtonSet.setEnabled(true);
            mButtonSet.setBackgroundColor(Color.parseColor("#9365E8"));
            mTimesUp.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimerLeftInMillis < 1000) {
                mButtonStartPause.setEnabled(false);
                mButtonStartPause.setBackgroundColor(Color.GRAY);
                mTimesUp.setVisibility(View.VISIBLE);
            }
            else {
                mButtonStartPause.setEnabled(true);
                mButtonStartPause.setBackgroundColor(Color.parseColor("#3CB043"));
            }

            if (mTimerLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setEnabled(true);
                mButtonReset.setBackgroundColor(Color.parseColor("#E3242B"));
            }
            else {
                mButtonReset.setEnabled(false);
                mButtonReset.setBackgroundColor(Color.GRAY);
            }
        }
    }
}