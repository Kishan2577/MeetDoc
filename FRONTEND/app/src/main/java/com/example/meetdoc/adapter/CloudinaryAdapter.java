package com.example.meetdoc.adapter;

import android.util.Log;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.meetdoc.helper.CloudinaryAPI;
import com.ocetnik.timer.BuildConfig;

import java.io.File;
import java.util.Map;

public class CloudinaryAdapter {
    private static Cloudinary cloudinary;
    static  String cloud_name = "ddkblq1nk";
    static String apiKey = CloudinaryAPI.CLOUDINARY_API_KEY;
    static String secretKey = CloudinaryAPI.CLOUDINARY_SECRET;

    public static void init() {
        if (cloudinary == null) {
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloud_name,
                    "api_key", apiKey,
                    "api_secret", secretKey
            ));
        }
    }

    public static String uploadImage(String userId, String filePath) {
        try {
            File file = new File(filePath);
            Map uploadResult = cloudinary.uploader().upload(file,
                    ObjectUtils.asMap("public_id", "users/" + userId));

            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            Log.e("Cloudinary", "Upload failed: " + e.getMessage());
            return null;
        }
    }
}
