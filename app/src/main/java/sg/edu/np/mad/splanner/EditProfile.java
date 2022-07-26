package sg.edu.np.mad.splanner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    Button saveButton;
    EditText editName, editEmail;
    ImageView profileImage;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String Name = data.getStringExtra("name");

        profileImage = findViewById(R.id.ProfPic);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+user.getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        saveButton = findViewById(R.id.saveProf);
        editName = findViewById(R.id.etName);

        ArrayList<String> fcList = new ArrayList();

        editName.setText(Name);
        editName.addTextChangedListener(tw);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().length() == 0 || editEmail.getText().toString().length() == 0) {
                    Toast.makeText(EditProfile.this, "A Field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = editName.getText().toString();
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                user.updateProfile(profileChangeRequest);
                finish();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
                ProfImgResultLauncher.launch(openGalleryIntent);
            }
        });


    }
    ActivityResultLauncher<Intent> ProfImgResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri imageUri = data.getData();
                        //profileImage.setImageURI(imageUri);
                        uploadImage(imageUri);
                    }
                }
            });
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1000){
//            if(resultCode == Activity.RESULT_OK){
//                Uri imageUri = data.getData();
//                profileImage.setImageURI(imageUri);
//
//                uploadImage(imageUri);
//
//            }
//        }
//    }

    private void uploadImage(Uri imageUri){
        Toast.makeText(this, "uploading...", Toast.LENGTH_SHORT).show();
        //Upload Image to Firebase Storage
        StorageReference fileRef = storageReference.child("users/"+user.getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = editName.getText().toString();

            if (username.trim().length() == 0) {
                editName.setError("Name is Required");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
