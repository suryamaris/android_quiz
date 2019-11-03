package com.example.uts;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

public class Multiple_Choice extends AppCompatActivity {
    private RequestQueue requestQueue;
    private List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>();
    private TextView question;
    private Button ansA, ansB, ansC, ansD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_choice);
        question = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansC = findViewById(R.id.ans_c);
        ansD = findViewById(R.id.ans_d);
        requestQueue = Volley.newRequestQueue(this);
        getQuestionFromJsonRandomly();
    }

    private void setQuestionToLayout(MultipleChoiceQuestion mcq) {
        question.setText(mcq.getQuestion());
        ansA.setText(mcq.getOption_1());
        ansB.setText(mcq.getOption_2());
        ansC.setText(mcq.getOption_3());
        ansD.setText(mcq.getOption_4());
    }

    private void getQuestionFromJsonRandomly() {
        String url = "https://api.myjson.com/bins/19rsrw";
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
                                mcq.setOption_3(jsonObject.getString("option_3"));
                                mcq.setOption_4(jsonObject.getString("option_4"));
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
}
