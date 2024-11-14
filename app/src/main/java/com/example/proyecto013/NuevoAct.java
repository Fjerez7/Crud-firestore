package com.example.proyecto013;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NuevoAct extends AppCompatActivity {
    TextView tvName, tvOwner;
    Button btn1;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo);

        db = FirebaseFirestore.getInstance();
        tvName = findViewById(R.id.tvNName);
        tvOwner = findViewById(R.id.tvNOwner);
        btn1 = findViewById(R.id.btnSave);
        btn1.setOnClickListener(ev);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    View.OnClickListener ev = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = tvName.getText().toString();
            String owner = tvOwner.getText().toString();
            Map<String, Object> e = new HashMap<>();
            e.put("name", name);
            e.put("owner", owner);
            db.collection("Establecimientos")
                    .add(e)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(NuevoAct.this, "Establecimiento" + name + "Almacenado con Exito", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(NuevoAct.this, MainActivity.class);
                            startActivity(it);
                        }
                    });

        }
    };
}