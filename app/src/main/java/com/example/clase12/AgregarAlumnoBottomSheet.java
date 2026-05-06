package com.example.clase12;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AgregarAlumnoBottomSheet extends BottomSheetDialogFragment {

    interface Listener {
        void onGuardar(String nombre, String primerApellido, String segundoApellido);
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_agregar_alumno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout tilNombre = view.findViewById(R.id.tilNombre);
        TextInputLayout tilPrimerApellido = view.findViewById(R.id.tilPrimerApellido);
        TextInputEditText etNombre = view.findViewById(R.id.etNombre);
        TextInputEditText etPrimerApellido = view.findViewById(R.id.etPrimerApellido);
        TextInputEditText etSegundoApellido = view.findViewById(R.id.etSegundoApellido);
        Button btnGuardar = view.findViewById(R.id.btnGuardarAlumno);
        Button btnCancelar = view.findViewById(R.id.btnCancelarAlumno);

        etNombre.requestFocus();
        etNombre.post(() -> {
            InputMethodManager imm = (InputMethodManager) requireContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etNombre, InputMethodManager.SHOW_IMPLICIT);
        });

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText() != null ? etNombre.getText().toString().trim() : "";
            String primerAp = etPrimerApellido.getText() != null ? etPrimerApellido.getText().toString().trim() : "";
            String segundoAp = etSegundoApellido.getText() != null ? etSegundoApellido.getText().toString().trim() : "";

            boolean valido = true;
            if (nombre.isEmpty()) {
                tilNombre.setError("Ingresa el nombre");
                valido = false;
            } else {
                tilNombre.setError(null);
            }
            if (primerAp.isEmpty()) {
                tilPrimerApellido.setError("Ingresa el primer apellido");
                valido = false;
            } else {
                tilPrimerApellido.setError(null);
            }
            if (!valido) return;

            if (listener != null) listener.onGuardar(nombre, primerAp, segundoAp);
            dismiss();
        });

        btnCancelar.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) requireDialog();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        FrameLayout bottomSheet = dialog.findViewById(
            com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet == null) return;

        ViewGroup.LayoutParams lp = bottomSheet.getLayoutParams();
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        bottomSheet.setLayoutParams(lp);

        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setSkipCollapsed(true);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
