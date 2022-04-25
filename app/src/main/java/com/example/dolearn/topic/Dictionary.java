package com.example.dolearn.topic;

import static android.content.Context.MODE_PRIVATE;

import android.media.Image;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Dictionary extends AppCompatActivity {
    public static ArrayList<Item> listItem = new ArrayList<Item>();
}
