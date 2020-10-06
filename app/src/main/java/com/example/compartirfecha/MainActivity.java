package com.example.compartirfecha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView textViewFecha;
    public int AÑO,MES,DIA;
    Button buttonCompartir;
    Calendar calendario;

    //Este escuchador lo que hace es indicar que el usuario ha terminado de seleccionar una fecha
    private DatePickerDialog.OnDateSetListener escuchadorDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
            //Guardar en estas variables globales la fecha de nacimiento seleccionada
            AÑO=año;
            MES=mes;
            DIA=dia;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewFecha=findViewById(R.id.textViewFecha);
        buttonCompartir=findViewById(R.id.buttonCompartir);

        calendario=Calendar.getInstance();
        AÑO=calendario.get(Calendar.YEAR);
        MES=calendario.get(Calendar.MONTH);
        DIA=calendario.get(Calendar.DAY_OF_MONTH);

        textViewFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialogoFecha=new DatePickerDialog(MainActivity.this,escuchadorDatePicker,AÑO,MES,DIA);
                dialogoFecha.show();//Esto es para que se muestre el DataPickerDialog para seleccionar la Fecha
            }
        });

        buttonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String fechaintroducida = String.format(Locale.getDefault(), "%02d-%02d-%02d",AÑO, MES+1, DIA);
                textViewFecha.setText(fechaintroducida);

               /*Date fechaactual=new Date();
               DIAACTUAL=fechaactual.getDay();
               MESACTUAL=fechaactual.getMonth();
               AÑOACTUAL=fechaactual.getYear();*/

               Calendar calendarioactual=Calendar.getInstance();


               //Si el mes de nacimiento es menor que el mes actual, pues aumento el año, porque
                //mi mes del cumpleaños de este año ha pasado y es el año que viene mi cumpleaños
                if(MES<calendarioactual.get(Calendar.MONTH)){

                        calendario.set(calendarioactual.get(Calendar.YEAR)+1,MES,DIA);

                }
                else if(DIA==calendarioactual.get(Calendar.DAY_OF_MONTH) && MES==calendarioactual.get(Calendar.MONTH)){
                    Toast.makeText(getApplicationContext(),"Hoy es su cumpleaños",Toast.LENGTH_SHORT).show();

                }
                else{
                     calendario.set(calendarioactual.get(Calendar.YEAR), MES, DIA);
                }

            //Diferencia en dias entre dos fechas
                long miliscalendaractual=calendarioactual.getTimeInMillis();
                long miliscalendarioinsertado=calendario.getTimeInMillis();

                long diferenciaenms=miliscalendaractual-miliscalendarioinsertado;
               long dias=diferenciaenms / (24*60*60*1000);

                  /* System.out.println("Fecha Introducida: "+calendario.getTime());
                   System.out.println("Fecha Actual: "+calendarioactual.getTime());

                   System.out.println("Dias transcurridos: "+dias);

                   System.out.println("Mes Introducido: "+calendario.get(Calendar.MONTH));
                   System.out.println("MES: "+MES);*/

                //Compartir por Whatsapp
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setPackage("com.whatsapp");
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Me quedan "+dias+" dias para mi cumpleaños");


                if (getPackageManager().getLaunchIntentForPackage("com.whatsapp")!=null){
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Instala Whatsapp", Toast.LENGTH_LONG).show();
                }


            }
        });

    }
}