package com.amanullah.myapplication98.ui.Learn;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amanullah.myapplication98.R;
import com.amanullah.myapplication98.model.ChapterItem;
import com.amanullah.myapplication98.repository.DataRepository;
import com.amanullah.myapplication98.utils.ChapterItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterActivity extends AppCompatActivity implements ChapterItemAdapter.ChapterItemAdapterListener {
    private static final String TAG = "ChapterActivity";
    private LearnViewModel mViewModel;
    private CardView card_header;
    private ImageView imageView;
    private TextView textView2,textView4;
    private RecyclerView recyclerView;
    private List<ChapterItem> itemList = new ArrayList<>();
    private ChapterItemAdapter mAdapter;
    String chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        chapter = getIntent().getStringExtra("chapter");

        textView2 = findViewById(R.id.textView2);
        textView4 = findViewById(R.id.textView4);
        imageView = findViewById(R.id.imageView);
        if(chapter.equals("2nd")){
            textView2.setText("2nd part");
            imageView.setBackground(getResources().getDrawable(R.drawable.second_part));
        }
        card_header = findViewById(R.id.card_header);
        card_header.setBackgroundResource(R.drawable.header);

        recyclerView = (RecyclerView) findViewById(R.id.rv1);
        mAdapter = new ChapterItemAdapter(this, itemList,this);
        mAdapter.setHasStableIds(true);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initializeRecyclerView();
                recyclerView.setAdapter(mAdapter);
            }
        },00);
    }

    public void initializeRecyclerView(){
        if(chapter.equals("1st")){
            for(int i=0;i<DataRepository.getPart1().size();i++){
                ChapterItem chapterItem = new ChapterItem();
                chapterItem.setId(i);
                chapterItem.setTitle(DataRepository.getPart1().get(i));
                chapterItem.setSubtitle("Total "+i+10+" videos");
                chapterItem.setImgUrl(DataRepository.getImgList1()[i]);
                itemList.add(chapterItem);
            }
        } else {
            for(int i=0;i<DataRepository.getPart2().size();i++){
                ChapterItem chapterItem = new ChapterItem();
                chapterItem.setId(i);
                chapterItem.setTitle(DataRepository.getPart2().get(i));
                chapterItem.setSubtitle("Total "+i+15+" videos");
                chapterItem.setImgUrl(DataRepository.getImgList2()[i]);
                itemList.add(chapterItem);
            }
        }
    }

    @Override
    public void onItemSelected(ChapterItem item) {

    }
}
