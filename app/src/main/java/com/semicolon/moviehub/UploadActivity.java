package com.semicolon.moviehub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.semicolon.moviehub.models.Video;

public class UploadActivity extends AppCompatActivity {
    private Uri file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Button choose = findViewById(R.id.chooseFile);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("videos/*");
                startActivityForResult(Intent.createChooser(intent, "Choose a video"), 42);

            }
        });

        Button upload = findViewById(R.id.Upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.title);
                final String title = editText.getText().toString();
                editText = findViewById(R.id.description);
                final String descr = editText.getText().toString();
                RadioGroup group = findViewById(R.id.typesVideo);
                RadioButton button = findViewById(group.getCheckedRadioButtonId());
                final String type = button.getText().toString();

                final StorageReference ref = FirebaseStorage.getInstance().getReference("Uploaded");
                final ProgressDialog dialog = ProgressDialog.show(v.getContext(), "", "Uploading. Please wait..");
                ref.putFile(file).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Videos").child(type);
                        reference = reference.push();
                        final DatabaseReference finalReference = reference;
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                finalReference.setValue(new Video(finalReference.getKey(), title, descr, uri.toString()));
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "File uploaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "File not uploaded", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 42 && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                file = data.getData();
            }
        }
    }
}
