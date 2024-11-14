package com.example.proyecto013;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyAdapter adapter;
    Intent it;
    Context context;
    Establecimiento e = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Establecimiento> est = new ArrayList<Establecimiento>();
        RecyclerView recyclerView = findViewById(R.id.rvEst);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.collection("Establecimientos")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        Log.d("Main", document.getId() + " => " + document.getData());
                                        String name = document.getString("name");
                                        String owner = document.getString("owner");
                                        String id = document.getId();
                                        e = new Establecimiento(name,owner,id);
                                        est.add(e);
                                    }
                                    adapter = new MyAdapter(getApplicationContext(),est);
                                    adapter.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("MainActivity", "Presionando: " + est.get(recyclerView.getChildAdapterPosition(v)).getName());
                                            it = new Intent(context, EstablecimientoAct.class);
                                            it.putExtra("eId",est.get(recyclerView.getChildAdapterPosition(v)).getId());
                                            it.putExtra("eName",est.get(recyclerView.getChildAdapterPosition(v)).getName());
                                            it.putExtra("eOwner",est.get(recyclerView.getChildAdapterPosition(v)).getOwner());
                                            startActivity(it);
                                        }
                                    });
                                    recyclerView.setAdapter(adapter);
                                }else {
                                    Log.w("Main", "Error getting documents",task.getException());
                                }
                            }
                        });
        FloatingActionButton btnNew = findViewById(R.id.fbBtn);
        btnNew.setOnClickListener(evNuevo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    View.OnClickListener evNuevo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent in = new Intent(context, NuevoAct.class);
            startActivity(in);
        }
    };
}