package com.example.hybridblowfishrsa.ui.dekripsi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hybridblowfishrsa.R;
import com.example.hybridblowfishrsa.utils.FileUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DekripsiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DekripsiFragment extends Fragment {

    private ImageView imgpilihfiledekripsi;
    private TextView tvlokasiinputfiledekripsi;
    private EditText etpasswordblowfishdekripsi;
    private EditText etkunciprivatersadekripsi;
    private EditText etkuncipublikrsadekripsi;
    private Button btndekripsi;
    private EditText ethasildekripsiblowfishdekripsi;
    private TextView tvfilehasildekripsidekripsi;

    private static final int MY_REQUEST_CODE_PERMISSION = 1000;
    private static final int MY_RESULT_CODE_FILECHOOSER = 2000;
    private static final String LOG_TAG = "AndroidExample";

    public DekripsiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DekripsiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DekripsiFragment newInstance(String param1, String param2) {
        DekripsiFragment fragment = new DekripsiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dekripsi, container, false);
        initView(view);

        imgpilihfiledekripsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deteksiPermissionandroid();
            }
        });

        btndekripsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordenkripsiblowfish = etpasswordblowfishdekripsi.getText().toString();
                String kunciprivatersa = etkunciprivatersadekripsi.getText().toString();
                String kuncipublikrsa = etkuncipublikrsadekripsi.getText().toString();


            }
        });

        return view;
    }

    private void initView(View view){
        imgpilihfiledekripsi = (ImageView) view.findViewById(R.id.imgfiledekripsi);
        tvlokasiinputfiledekripsi = (TextView) view.findViewById(R.id.txtlokasifileinputdekripsi);
        etpasswordblowfishdekripsi = (EditText) view.findViewById(R.id.etpasswordblowfishdekripsi);
        etkunciprivatersadekripsi = (EditText) view.findViewById(R.id.etkunciprivatersadekripsi);
        etkuncipublikrsadekripsi = (EditText) view.findViewById(R.id.etkuncipublikrsadekripsi);
        btndekripsi = (Button) view.findViewById(R.id.btndekripsi);
        ethasildekripsiblowfishdekripsi = (EditText) view.findViewById(R.id.etenkripsipasswordblowfishdekripsi);
        tvfilehasildekripsidekripsi = (TextView) view.findViewById(R.id.txtlokasifiledekripsi);
    }

    private void deteksiPermissionandroid()  {
        // With Android Level >= 23, you have to ask the user
        // for permission to access External Storage.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // Level 23

            // Check if we have Call permission
            int permisson = ActivityCompat.checkSelfPermission(this.getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_REQUEST_CODE_PERMISSION
                );
                return;
            }
        }
        this.pilihFile();
    }

    private void pilihFile()  {
        Intent chooseFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFileIntent.setType("application/*");
        // Only return URIs that can be opened with ContentResolver
        chooseFileIntent.addCategory(Intent.CATEGORY_OPENABLE);

        chooseFileIntent = Intent.createChooser(chooseFileIntent, "Choose a file");
        startActivityForResult(chooseFileIntent, MY_RESULT_CODE_FILECHOOSER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_REQUEST_CODE_PERMISSION: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (CALL_PHONE).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
                    Toast.makeText(this.getContext(), "Permission granted!", Toast.LENGTH_SHORT).show();

                    this.pilihFile();
                }
                // Cancelled or denied.
                else {
                    Log.i(LOG_TAG,"Permission denied!");
                    Toast.makeText(this.getContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MY_RESULT_CODE_FILECHOOSER:
                if (resultCode == Activity.RESULT_OK ) {
                    if(data != null)  {
                        Uri fileUri = data.getData();
                        Log.i(LOG_TAG, "Uri: " + fileUri);

                        String filePath = null;
                        try {
                            filePath = FileUtils.getPath(this.getContext(),fileUri);
                        } catch (Exception e) {
                            Log.e(LOG_TAG,"Error: " + e);
                            Toast.makeText(this.getContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
                        }
                        this.tvlokasiinputfiledekripsi.setText(filePath);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}