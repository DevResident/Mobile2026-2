package com.example.actividad4;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ViewModel viewModel = new ViewModel();

    private String inputActual = "";
    private Double primerElemento = null;
    private OperationType operacionPendiente = null;
    private boolean calcular = false;

    private TextView txvOperation;
    private TextView txvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txvOperation = findViewById(R.id.txvOperation);
        txvResult    = findViewById(R.id.txvResult);

        findViewById(R.id.btnCero).setOnClickListener(v  -> appendDigit("0"));
        findViewById(R.id.btnUno).setOnClickListener(v   -> appendDigit("1"));
        findViewById(R.id.btnDos).setOnClickListener(v   -> appendDigit("2"));
        findViewById(R.id.btnTres).setOnClickListener(v  -> appendDigit("3"));
        findViewById(R.id.btnCuatro).setOnClickListener(v -> appendDigit("4"));
        findViewById(R.id.btnCinco).setOnClickListener(v -> appendDigit("5"));
        findViewById(R.id.btnSeis).setOnClickListener(v  -> appendDigit("6"));
        findViewById(R.id.btnSiete).setOnClickListener(v -> appendDigit("7"));
        findViewById(R.id.btnOcho).setOnClickListener(v  -> appendDigit("8"));
        findViewById(R.id.btnNueve).setOnClickListener(v -> appendDigit("9"));

        findViewById(R.id.btnPoint).setOnClickListener(v -> {
            if (!inputActual.contains(".")) {
                inputActual = inputActual.isEmpty() ? "0." : inputActual + ".";
                txvResult.setText(inputActual);
            }
        });

        findViewById(R.id.btnSumar).setOnClickListener(v    -> setOperator(OperationType.ADD));
        findViewById(R.id.btnRestar).setOnClickListener(v   -> setOperator(OperationType.MINUS));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator(OperationType.MULTIPLY));
        findViewById(R.id.btnDivide).setOnClickListener(v   -> setOperator(OperationType.DIVIDE));

        findViewById(R.id.btnIgual).setOnClickListener(v -> resultado());

        findViewById(R.id.btnAC).setOnClickListener(v -> {
            inputActual = "";
            primerElemento = null;
            operacionPendiente        = null;
            calcular = false;
            txvOperation.setText("");
            txvResult.setText("0");
        });

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            if (!inputActual.isEmpty()) {
                inputActual = inputActual.substring(0, inputActual.length() - 1);
                txvResult.setText(inputActual.isEmpty() ? "0" : inputActual);
            }
        });

        findViewById(R.id.btnPlus).setOnClickListener(v -> {
            if (!inputActual.isEmpty() && !inputActual.equals("-")) {
                if (inputActual.startsWith("-")) {
                    inputActual = inputActual.substring(1);
                } else {
                    inputActual = "-" + inputActual;
                }
                txvResult.setText(inputActual);
            }
        });

        findViewById(R.id.btnPorcentaje).setOnClickListener(v -> {
            if (!inputActual.isEmpty()) {
                double val = Double.parseDouble(inputActual) / 100.0;
                inputActual = formato(val);
                txvResult.setText(inputActual);
            }
        });
    }

    private void appendDigit(String digit) {
        if (calcular) {
            inputActual = "";
            calcular = false;
        }
        inputActual += digit;
        txvResult.setText(inputActual);
    }

    private void setOperator(OperationType type) {
        if (!inputActual.isEmpty()) {
            // (jerarquía encadenada)
            if (primerElemento != null && operacionPendiente != null) {
                CalculadoraInput input = new CalculadoraBuilder()
                        .addOperationInitial(new Operation(primerElemento, Double.parseDouble(inputActual), operacionPendiente))
                        .build();
                primerElemento = viewModel.makeOperation(input);
                txvResult.setText(formato(primerElemento));
            } else {
                primerElemento = Double.parseDouble(inputActual);
            }
            operacionPendiente    = type;
            inputActual = "";
            txvOperation.setText(formato(primerElemento) + " " + simbolo(type));
        }
    }

    // resultado final
    private void resultado() {
        if (primerElemento == null || operacionPendiente == null || inputActual.isEmpty()) return;

        double segundoElemento = Double.parseDouble(inputActual);

        CalculadoraInput input = new CalculadoraBuilder()
                .addOperationInitial(new Operation(primerElemento, segundoElemento, operacionPendiente))
                .build();

        Double result = viewModel.makeOperation(input);

        txvOperation.setText(formato(primerElemento) + " " + simbolo(operacionPendiente) + " " + formato(segundoElemento) + " =");
        txvResult.setText(formato(result));

        // Reiniciar estado
        primerElemento = result;
        inputActual = formato(result);
        operacionPendiente = null;
        calcular = true;
    }

    // Muestra entero si no tiene decimales (ej: 4.0 → "4")
    private String formato(Double val) {
        if (val == Math.floor(val) && !Double.isInfinite(val)) {
            return String.valueOf(val.longValue());
        }
        return String.valueOf(val);
    }

    private String simbolo(OperationType type) {
        switch (type) {
            case ADD:      return "+";
            case MINUS:    return "-";
            case MULTIPLY: return "×";
            case DIVIDE:   return "÷";
            default:       return "";
        }
    }
}