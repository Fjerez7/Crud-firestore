package com.example.proyecto013;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class EstablecimientoAct extends AppCompatActivity {
    TextView tvName, tvOwner, tvId;
    Button btn1, btn2;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_establecimiento);

        tvName = findViewById(R.id.tvEstName);
        tvOwner = findViewById(R.id.tvEstOwner);
        tvId = findViewById(R.id.tvEstId);
        context = getApplicationContext();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            String id = bundle.getString("eId");
            String name = bundle.getString("eName");
            String owner = bundle.getString("eOwner");
            tvName.setText(name);
            tvOwner.setText(owner);
            tvId.setText(id);

        }

        Establecimiento e = new Establecimiento();
        e.setId(e.Id);
        e.setName(e.name);
        e.setOwner(e.owner);
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        tvId = findViewById(R.id.tvEstId);

        View.OnClickListener ev = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Establecimientos")
                        .document(tvId.getText().toString())
                        .update("name",tvName.getText().toString(), "owner", tvOwner.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EstablecimientoAct.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EstablecimientoAct.this, "Hubo un problema con la actualizacion", Toast.LENGTH_SHORT).show();
                                Log.w("MainActivity","Error",e);
                            }
                        });

            }
        };

        View.OnClickListener evE = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EstablecimientoAct.this);
                builder.setMessage("Desea Eliminar este Establecimiento")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("MainActivity","Eliminar...");
                                db.collection("Establecimientos")
                                        .document(tvId.getText().toString()).delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(getApplicationContext(),"Establecimiento Eliminado",Toast.LENGTH_SHORT).show();
                                                Intent it = new Intent(context, MainActivity.class);
                                                startActivity(it);
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                builder.create().show();
            }
        };

        btn1 = findViewById(R.id.btnUpdate);
        btn1.setOnClickListener(ev);
        btn2 = findViewById(R.id.btnDelete);
        btn2.setOnClickListener(evE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}