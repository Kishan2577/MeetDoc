package com.example.meetdoc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookSlot#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookSlot extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WebView webView;

    public BookSlot() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookSlot.
     */
    // TODO: Rename and change types and number of parameters
    public static BookSlot newInstance(String param1, String param2) {
        BookSlot fragment = new BookSlot();
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
        View view = inflater.inflate(R.layout.fragment_book_slot, container, false);

        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        Log.e("CHAT","HERE 0");
        webSettings.setJavaScriptEnabled(true);
        Log.e("CHAT","HERE 1");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("URL FOR CHAT");

        return view;
    }

}