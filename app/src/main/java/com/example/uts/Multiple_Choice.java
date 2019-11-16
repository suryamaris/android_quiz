package com.example.uts;
master
import android.content.DialogInterface;
application1
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Multiple_Choice extends AppCompatActivity implements View.OnClickListener {
    private RequestQueue requestQueue;
    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();
    private TextView question;
    private Button ansA, ansB, ansC;
    private int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_choice);
        question = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansC = findViewById(R.id.ans_c);
        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
        getQuestionFromJsonRandomly();
    }

    private void setQuestionToLayout(MultipleChoiceQuestion mcq) {
        question.setText(mcq.getQuestion());
        List<String> option = new ArrayList<>();
        option.add(mcq.getAnswer());
        option.add(mcq.getOption_1());
        option.add(mcq.getOption_2());
        Collections.shuffle(option);
        ansA.setText(option.get(0));
        ansB.setText(option.get(1));
        ansC.setText(option.get(2));
    }

    private void getQuestionFromJsonRandomly() {
        String url = "https://api.myjson.com/bins/110iuy";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("multiple_choice");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
                                mcq.setId(jsonObject.getInt("id"));
                                mcq.setGroup_id(jsonObject.getString("group_id"));
                                mcq.setQuestion(jsonObject.getString("question"));
                                mcq.setAnswer(jsonObject.getString("answer"));
                                mcq.setOption_1(jsonObject.getString("option_1"));
                                mcq.setOption_2(jsonObject.getString("option_2"));
                                mcq.setDescription(jsonObject.getString("description"));
                                multipleChoiceQuestions.add(mcq);
                            }
                            Collections.shuffle(multipleChoiceQuestions);
                            setQuestionToLayout(multipleChoiceQuestions.get(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        Button beenClicked = (Button) v;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mc_description, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        TextView value = dialogView.findViewById(R.id.value);
        if (beenClicked.getText().toString().equals(multipleChoiceQuestions.get(i).getAnswer())) {
            value.setText("Correct");
        } else {
            value.setText("Incorrect");
        }
        TextView description = dialogView.findViewById(R.id.description);
        description.setText(multipleChoiceQuestions.get(i).getDescription());
        dialog.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i++;
                setQuestionToLayout(multipleChoiceQuestions.get(i));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

