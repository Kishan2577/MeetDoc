package com.example.meetdoc;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meetdoc.adapter.DoctorAdapter;
import com.example.meetdoc.viewmodel.DoctorViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private DoctorAdapter adapter;
    private DoctorViewModel viewModel;
    private ProgressBar progressBar;

    private TextView seeAll;
    private Button btnGetStarted;

    private CircleImageView imgDoctor;

    private ImageView btncardiology,btndental,btnpediatric,btneyespecialist;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.doctorList);
        progressBar = view.findViewById(R.id.progressBar); // Make sure this ID exists in your layout
        btnGetStarted = view.findViewById(R.id.btnGetStarted);
        imgDoctor = view.findViewById(R.id.imgDoctor);
        seeAll = view.findViewById(R.id.seeAll);
        btncardiology = view.findViewById(R.id.Cardiologist);
        btndental = view.findViewById(R.id.Dental);
        btnpediatric = view.findViewById(R.id.Pediatric);
        btneyespecialist = view.findViewById(R.id.Eye_Specialist);
        adapter = new DoctorAdapter(); // Initialize before observing
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(DoctorViewModel.class);
        viewModel.getDoctors(getContext()).observe(getViewLifecycleOwner(), doctors -> {
            if (doctors != null) {
                Log.e("BTESTING", "HERE 2");
                Glide.with(this)
                        .load(doctors.get(0).getImageUrl())
                        .placeholder(R.drawable.doctor_banner) // optional placeholder
                        .error(R.drawable.doctor_banner) // optional fallback
                        .into(imgDoctor);
                adapter.setDoctorList(doctors);
            } else {
                Log.e("BTESTING", "Doctors data is null");
            }

            progressBar.setVisibility(View.GONE); // Hide progress bar in both cases
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllDoctorActivity.class);
                intent.putExtra("speciality", "all");
                startActivity(intent);
            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllDoctorActivity.class);
                intent.putExtra("speciality", "all");
                startActivity(intent);
            }
        });

        btncardiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllDoctorActivity.class);
                intent.putExtra("speciality", "Cardiologist");
                startActivity(intent);
            }
        });

        btndental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllDoctorActivity.class);
                intent.putExtra("speciality", "Dental");
                startActivity(intent);
            }
        });


        btnpediatric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllDoctorActivity.class);
                intent.putExtra("speciality", "Pediatrician");
                startActivity(intent);
            }
        });

        btneyespecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllDoctorActivity.class);
                intent.putExtra("speciality", "Eye Specialist");
                startActivity(intent);
            }
        });
        return view;
    }

}