package com.example.dolearn.translate;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dolearn.HandleClass;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.topic.Item;

public class AddItemDialog extends AppCompatDialogFragment {
    private EditText engNameED, vieNameED, typeED, pronED, engExampleED, vieExampleED;
    String engName, vieName, fromText, type, engExample, vieExample, pron;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_item_dialog, null);
        Bundle bundle = getArguments();
        engName = bundle.getString("engName");
        vieName = bundle.getString("vieName");
        fromText = bundle.getString("FromText");

        engNameED = view.findViewById(R.id.engNameED);
        vieNameED = view.findViewById(R.id.vieNameED);
        typeED = view.findViewById(R.id.typeED);
        pronED = view.findViewById(R.id.pronED);
        engExampleED = view.findViewById(R.id.engExampleED);
        vieExampleED = view.findViewById(R.id.vieExampleED);

        if (fromText.equals("eng")) {
            engNameED.setText(engName);
            vieNameED.setText(vieName);
        } else {
            engNameED.setText(vieName);
            vieNameED.setText(engName);
        }


        builder.setView(view)
                .setTitle("Thêm từ vào Note")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmAddItem();
                    }
                });

        return builder.create();
    }


    public void confirmAddItem() {
        engName = engNameED.getText().toString();
        vieName = vieNameED.getText().toString();

        engName = engName.equals("") ? "" : HandleClass.upperCaseFirst(engNameED.getText().toString());
        type = typeED.getText().toString();
        vieName = vieName.equals("") ? "" : HandleClass.upperCaseFirst(vieNameED.getText().toString());
        pron = pronED.getText().toString().equals("") ? "-" : pronED.getText().toString();
        engExample = engExampleED.getText().toString().equals("") ? "-" : engExampleED.getText().toString();
        vieExample = vieExampleED.getText().toString().equals("") ? "-" : vieExampleED.getText().toString();

        if (engName.equals("") || vieName.equals("") || type.equals("")) {
            Toast.makeText(getContext(), "Vui lòng điền ít nhất các trường *", Toast.LENGTH_LONG).show();
            FragmentActivity intent = getActivity();
            intent.recreate();
        } else if (engName.split(" ").length > 3) {
            Toast.makeText(getContext(), "Trường 1 không quá 3 từ", Toast.LENGTH_LONG).show();
            FragmentActivity intent = getActivity();
            intent.recreate();
        } else {
            Item item = new Item(engName + " (" + type + ")", vieName, pron, engExample, vieExample);
            if (!HandleClass.checkExistInNote(item)) {
                NoteActivity.listNote.add(item);
                HandleClass.loadDataToFile(getContext());
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}