package co.edu.eafit.yomas10.Partidos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

import co.edu.eafit.yomas10.Jugador.Jugador;
import co.edu.eafit.yomas10.Jugador.SeleccionarAmigosActivity;
import co.edu.eafit.yomas10.MainActivity;
import co.edu.eafit.yomas10.R;

public class CrearPartidoActivity extends AppCompatActivity {

    private static TextView fechaPartido, horaPartido;
    private Button cambiarFecha, cambiarHora;
    private EditText canchaPartido;
    private ListView jugadoresLV;
    private ArrayList<String> jugadores;
    private ArrayAdapter<String> mAdapter;
    private final static int REQUEST_AMIGOS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partido);

        fechaPartido = (TextView) findViewById(R.id.fechaPartido);
        horaPartido = (TextView) findViewById(R.id.horaPartido);
        canchaPartido = (EditText) findViewById(R.id.canchaPartido);

        cambiarFecha = (Button) findViewById(R.id.cambiarFecha);
        cambiarHora = (Button) findViewById(R.id.cambiarHora);

        jugadoresLV = (ListView) findViewById(R.id.jugadores);

        jugadores = new ArrayList<>();

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, jugadores);
        //jugadoresLV.setEmptyView((TextView) findViewById(R.id.emptyText));


        Button btn = new Button(getApplicationContext());
        btn.setText("Agregar Jugadores");
        jugadoresLV.addFooterView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), SeleccionarAmigosActivity.class);
                startActivityForResult(in, REQUEST_AMIGOS);

            }
        });
        jugadoresLV.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            ArrayList<String> nuevosJugadores = data.getExtras().getStringArrayList("JUGADORES");

            for (int i = 0; i<nuevosJugadores.size();i++){
                mAdapter.add(nuevosJugadores.get(i));
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_partido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == R.id.crearPartido){
            Partido partido = new PartidoCasual(fechaPartido.getText().toString(),
                    horaPartido.getText().toString(), canchaPartido.getText().toString());

            MainActivity.getUser().agregarPartido(partido);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void elegirFecha(View view){
        DialogFragment selFecha = new DatePickerFragment();
        selFecha.show(getFragmentManager(), "datePicker");
    }

    public void elegirHora(View view){
        DialogFragment selHora = new TimePickerFragment();
        selHora.show(getFragmentManager(), "timePicker");
    }

    //Clases para elegir fecha y hora

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        private int hora, minuto;

        @Override
        public TimePickerDialog onCreateDialog(Bundle savedInstace){
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minutes, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            horaPartido.setText(hourOfDay + ":" + minute);
            hora = hourOfDay;
            minuto = minute;
        }

        public int getHora() {
            return hora;
        }

        public int getMinuto(){
            return minuto;
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        private int year, month, day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstance){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            fechaPartido.setText(dayOfMonth + "/" + monthOfYear + "/" +  year);
            this.year = year;
            this.month = monthOfYear;
            this.day = dayOfMonth;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }
}
