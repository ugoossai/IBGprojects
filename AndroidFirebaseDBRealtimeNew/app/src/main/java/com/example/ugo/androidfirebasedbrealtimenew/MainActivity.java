package com.example.ugo.androidfirebasedbrealtimenew;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText edt_title;
    EditText edt_content;
    Button btn_post, btn_update, btn_delete;
    RecyclerView recyclerView;
    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Post> options;
    FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder> adapter;

    Post selectedPost;
    String selectedKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity.main);

        edt_content = (EditText) findViewById(edt_content);
        edt_title = (EditText) findViewById(edt_title);
        btn_post = (Button) findViewById(btn_post);
        btn_update = (Button) findViewById(btn_update);
        btn_delete = (Button) findViewById(btn_delete);
        recyclerView = (RecyclerView) findViewById(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReferenceFromUrl("https://androidfirebasedbrealtim-84f14.firebaseio.com/");

        databaseReference.addValueEventListener(new ValueEventListener);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();

            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.child(selectedKey)
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            private Object findViewById(EditText recyclerView) {

            }

            private void postComment() {
                String title = edt_title.getText().toString();
                String content = edt_content.getText().toString();

                Post post = new Post(title, content);

                databaseReference.push()//use this method to create unique id of comment
                        .setValue(post);
                adapter.notifyDataSetChanged();

            }

            private void displayComment() {
                options =
                        new FirebaseRecyclerOptions.Builder<Post>()
                                .setQuery(databaseReference, Post.class)
                                .build();
                adapter =
                        new FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull final Post model) {
                                holder.txt_title.setText(model.getTitle());
                                holder.txt_comment.setText(model.getContent());

                                holder.setItemClickListener(new ItemClickListener() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        selectedPost = model;

                                        selectedKey = getSnapshots().getSnapshot(position).getKey();

                                        Log.d("Key Item", "" + selectedKey);

                                        //bind data
                                        edt_content.setText(model.getContent());
                                        edt_title.setText(model.getTitle());
                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                View itemView = LayoutInflater.from(getBaseContext()).inflate(Resources.layout.post_item, viewGroup, false);
                                return new MyRecyclerViewHolder(itemView);
                            }
                        };
                adapter.startListening();
                recyclerView.setAdapter(adapter);
            }
        });
    }
}